module FilesTree (
    FileType(..),
    FileData(..),
    Version(..),
    FileInfo(..),
    DirectoryInfo(..),
    FilesTree(..),
    dumpFileTree,
    saveTree,
    statusIsExist,
    getTreeByPath,
    getFileByPath,
    createDirByPath,
    createFileByPath,
    deleteFileOrFolder,
    findFilesByName,
    initCvsFolderByPath,
    addFolderToCvsByPath,
    addFileToCvsByPath,
    updateCvsFileByPath,
    deleteCvsVersionByPath,
    deleteFileFromCvsByPath,
    mergeFileVersionsByPath,
    isExistByPath,
    isNodeInCvs
) where

import           Control.Monad.Except (Except, runExcept, throwError)
import           Data.List            (intercalate)
import           Data.List.Split      (splitOn)
import           Data.Time.Clock      (UTCTime)
import           Error                (Error (..))
import           Helper
import           System.Directory     (Permissions, createDirectoryIfMissing,
                                       doesDirectoryExist, emptyPermissions,
                                       getFileSize, getModificationTime,
                                       getPermissions, listDirectory,
                                       pathIsSymbolicLink,
                                       removeDirectoryRecursive, removeFile)

data FileType = File
    | Symlink
    deriving (Show)

newtype FileData = FileData String

data Version = Version
    { revisionData :: FileData
    , message      :: String
    }

data Status = Deleted
    | Created
    | Changed
    | Initialized
    deriving (Eq)

data FileInfo = FileInfo
    { filePath        :: FilePath
    , fileName        :: String
    , fileData        :: FileData
    , filePermissions :: Permissions
    , fileType        :: FileType
    , changedTime     :: UTCTime
    , size            :: Integer
    , versions        :: [Version]
    , fileStatus      :: Status
    }

instance Show FileInfo where
    show info = show (filePath info) ++ "\npermissions " ++ show (filePermissions info) ++
        "\nchanged time " ++ show (changedTime info) ++ "\nsize " ++ show (size info)

data DirectoryInfo = DirectoryInfo
    { dirPath        :: FilePath
    , dirName        :: String
    , filesCount     :: Int
    , dirSize        :: Integer
    , dirPermissions :: Permissions
    , dirStatus      :: Status
    , isPatrCvs      :: Bool
    }

instance Show DirectoryInfo where
    show info = show (dirPath info) ++ "\ncount of files " ++ show (filesCount info) ++
        "\ndirectory size " ++ show (dirSize info) ++ "\npermissions " ++ show (dirPermissions info)


data FilesTree = FilesTree
    { dirInfo     :: DirectoryInfo
    , files       :: [FileInfo]
    , directoryes :: [FilesTree]
    }

createEmptyDirectory :: FilePath -> String -> Bool -> FilesTree
createEmptyDirectory path name cvsInit = FilesTree {
    dirInfo = DirectoryInfo {
        dirPath = path,
        dirName = name,
        filesCount = 0,
        dirSize = 0,
        dirPermissions = emptyPermissions,
        dirStatus = Created,
        isPatrCvs = cvsInit
    },
    files = [],
    directoryes = []
}

createNewFile :: FilePath -> String -> FileData -> UTCTime -> FileInfo
createNewFile path name fileData@(FileData content) time = FileInfo {
    filePath = path,
    fileName = name,
    fileData = fileData,
    filePermissions = emptyPermissions,
    fileType = File,
    changedTime = time,
    size = toInteger $ length content,
    versions = [],
    fileStatus = Created
}

updateFile :: FileData -> UTCTime -> Status -> FileInfo -> FileInfo
updateFile fileData time status info = updateFileData fileData time info{fileStatus = status}

updateFileData :: FileData -> UTCTime -> FileInfo -> FileInfo
updateFileData fileData@(FileData content) time info = info {
    fileData = fileData,
    changedTime = time,
    filePermissions = emptyPermissions,
    size = toInteger $ length content
}

--------------------- load file tree to memory --------------------

dumpFileTree :: FilePath -> IO (FilesTree)
dumpFileTree path = getDirInfo path >>= getDirTree

getDirTree :: DirectoryInfo -> IO (FilesTree)
getDirTree dirInfo = do
    let path = dirPath dirInfo
    (files, dirs) <- getFilesAndDir path
    child <- getChildTrees dirs

    return $ updateDirInfo $ FilesTree {
        dirInfo = dirInfo,
        files = files,
        directoryes = child
    }

getChildTrees :: [DirectoryInfo] -> IO [FilesTree]
getChildTrees []     = return $ []
getChildTrees (x:xs) = (:) <$> getDirTree x <*> getChildTrees xs

getFilesCount :: [FilesTree] -> Int
getFilesCount []     = 0
getFilesCount (x:xs) = getFilesCount xs + (filesCount . dirInfo) x


getFilesAndDir :: FilePath -> IO ([FileInfo], [DirectoryInfo])
getFilesAndDir path = listDirectory path >>= parseFiles path where
        parseFiles :: FilePath -> [FilePath] -> IO ([FileInfo], [DirectoryInfo])
        parseFiles prefix paths = reversePair <$> parseHelper paths prefix (pure ([], []))

        parseHelper :: [FilePath] -> FilePath -> IO ([FileInfo], [DirectoryInfo]) -> IO ([FileInfo], [DirectoryInfo])
        parseHelper [] _ res = res
        parseHelper (x:xs) prefix res = do
            let realPath = prefix ++ '/' : x
            isDir <- doesDirectoryExist realPath
            case isDir of
                True  -> parseHelper xs prefix (addToSecond <$> res <*> getDirInfo realPath)
                False -> parseHelper xs prefix (addToFirst <$> res <*> getFileInfo realPath)

getDirInfo :: FilePath -> IO (DirectoryInfo)
getDirInfo path = do
    permissions <- getPermissions path
    return DirectoryInfo {
        dirPath = path,
        dirName = getNameByFilePath path,
        filesCount = 0,
        dirSize = 0,
        dirPermissions = permissions,
        dirStatus = Initialized,
        isPatrCvs = False
    }

getFileInfo :: FilePath -> IO (FileInfo)
getFileInfo path = do
    permissions <- getPermissions path
    fileType <- getFileType path
    time <- getModificationTime path
    size <- getFileSize path
    fileData <- readFile path
    return FileInfo {
        filePath = path,
        fileName = getNameByFilePath path,
        filePermissions = permissions,
        fileType = fileType,
        changedTime = time,
        size = size,
        fileData = FileData fileData,
        versions = [],
        fileStatus = Initialized
    }

getFileType :: FilePath -> IO (FileType)
getFileType path = (\b -> if b then Symlink else File) <$> pathIsSymbolicLink path

------------------------- save file tree to disk -----------------------------

saveTree :: FilesTree -> IO ()
saveTree tree = saveDirectory (dirInfo tree) >>= (\b -> if b then
    multi saveFile (files tree) >> multi saveTree (directoryes tree) else return ())

multi :: (a -> IO ()) -> [a] -> IO()
multi f []     = return ()
multi f (x:xs) = f x >> multi f xs

saveDirectory :: DirectoryInfo -> IO (Bool)
saveDirectory dir = case dirStatus dir of
    Deleted -> removeDirectoryRecursive (dirPath dir) >> return False
    Created -> createDirectoryIfMissing False (dirPath dir) >> return True
    _       -> return True

saveFile :: FileInfo -> IO ()
saveFile file = case fileStatus file of
    Deleted     -> removeFile path
    Initialized -> return ()
    _           -> writeFile path newData
    where
        path = filePath file
        (FileData newData) = fileData file

---------------------------- get operations with file tree ---------------------

getTreeByPath :: [String] -> FilesTree -> Except Error FilesTree
getTreeByPath [] tree = case statusIsExist $ dirStatus $ dirInfo tree of
    True  -> return tree
    False -> throwError $ Error $ "folder '" ++ (dirName $ dirInfo tree) ++ "' doesn't exist"
getTreeByPath (x:xs) tree = getTreeByName x (directoryes tree) >>= (getTreeByPath xs)

getTreeByName :: String -> [FilesTree] -> Except Error FilesTree
getTreeByName name [] = throwError $ Error $ "folder '" ++ name ++ "' doesn't exist"
getTreeByName name (x:xs) = case name == (dirName $ dirInfo x) && statusIsExist (dirStatus $ dirInfo x) of
    True  -> return x
    False -> getTreeByName name xs


getFileByPath :: String -> [String] -> FilesTree -> Except Error FileInfo
getFileByPath name path tree = files <$> getTreeByPath path tree >>= getFileByName name where
    getFileByName :: String -> [FileInfo] -> Except Error FileInfo
    getFileByName name [] = throwError $ Error $ "file '" ++ name ++ "' doesn't exist"
    getFileByName name (x:xs) = case name == fileName x && statusIsExist (fileStatus x) of
        True  -> return x
        False -> getFileByName name xs

getFolderByPath :: [String] -> String -> FilesTree -> Except Error FilesTree
getFolderByPath path name tree = directoryes <$> getTreeByPath path tree >>= getTreeByName name

---------------------------- update operations with file tree ---------------------

updateNode :: (FilesTree -> [FilesTree]) -> [String] -> FilesTree -> Except Error FilesTree
updateNode fun folders tree = head <$> updateNodeHelper tree fun (directoryes tree) folders where
    updateNodeHelper :: FilesTree -> (FilesTree -> [FilesTree]) -> [FilesTree] -> [String] -> Except Error [FilesTree]
    updateNodeHelper _    _     [] (folder:_) = throwError $ Error $ "folder '" ++ folder ++ "' doesn't exist"
    updateNodeHelper tree fun _ [] = return $ fun tree
    updateNodeHelper tree fun (x:xs) folders@(folder:fs) = case folder == (dirName $ dirInfo x) of
        True  -> (:[]) <$> updateDirInfo . changeTreeDirectoryes tree <$> (++xs) <$> updateNodeHelper x fun (directoryes x) fs
        False -> (x:) <$> updateNodeHelper x fun xs folders


trueUpdateNode :: (FilesTree -> [FilesTree]) -> [String] -> FilesTree -> FilesTree
trueUpdateNode fun folders tree = head $ trueUpdateHelper tree fun (directoryes tree) folders where
    trueUpdateHelper :: FilesTree -> (FilesTree -> [FilesTree]) -> [FilesTree] -> [String] -> [FilesTree]
    trueUpdateHelper tree fun _ [] = fun tree
    trueUpdateHelper tree fun (x:xs) folders@(folder:fs) = case folder == (dirName $ dirInfo x) of
        True  -> [updateDirInfo $ changeTreeDirectoryes tree ((trueUpdateHelper x fun (directoryes x) fs) ++ xs)]
        False -> x : trueUpdateHelper x fun xs folders

updateFileNode :: (FileInfo -> [FileInfo]) -> String -> FilesTree -> FilesTree
updateFileNode fun name tree = updateDirInfo $ tree {files = updateHelper (files tree)} where
    updateHelper :: [FileInfo] -> [FileInfo]
    updateHelper (x:xs) = case fileName x == name of
        True  -> fun x ++ xs
        False -> x : updateHelper xs

changeTreeDirectoryes :: FilesTree -> [FilesTree] -> FilesTree
changeTreeDirectoryes tree dirs = tree {directoryes = dirs}

updateDirInfo :: FilesTree -> FilesTree
updateDirInfo tree = tree {dirInfo = (dirInfo tree) {
    filesCount = length file,
    dirSize = sum (fmap size file) + sum (fmap (dirSize . dirInfo) dirs)
}} where
    file = filter (statusIsExist . fileStatus) (files tree)
    dirs = filter ((statusIsExist . dirStatus) . dirInfo) (directoryes tree)

updateDirectoryStatus :: Status -> Bool -> FilesTree -> FilesTree
updateDirectoryStatus status cvsInit tree = tree {dirInfo = (dirInfo tree) {dirStatus = status, isPatrCvs = cvsInit}}

updateFileStatus :: Status -> FileInfo -> FileInfo
updateFileStatus status info = info {fileStatus = status}

------------------------------ operations with folders -----------------------------

createDirByPath :: String -> [String] -> FilesTree -> Except Error FilesTree
createDirByPath name path tree = updateNode (createFolderIfNeed name (isPatrCvs $ dirInfo tree)) path tree where
    createFolderIfNeed :: String -> Bool -> FilesTree -> [FilesTree]
    createFolderIfNeed name cvsInit tree = case directoryNotDeleted name tree of
        False -> [tree {directoryes = createEmptyDirectory path name cvsInit : directoryes tree}] where
            path = (dirPath $ dirInfo tree) ++ '/' : name
        True  -> [trueUpdateNode ((: []) . (updateDirectoryStatus Changed cvsInit)) [name] tree]

deleteDirByPath :: String -> [String] ->  FilesTree -> Except Error FilesTree
deleteDirByPath name = updateNode ((:[]) . trueUpdateNode deleteFolder [name])

deleteFolder :: FilesTree -> [FilesTree]
deleteFolder tree = case (dirStatus $ dirInfo tree) of
    Created -> []
    _       -> [tree{dirInfo = info, files = deletedFiles, directoryes = deletedDirs}] where
        info = (dirInfo tree) {dirStatus = Deleted}
        deletedFiles = concat $ fmap deleteFile (files tree)
        deletedDirs = concat $ fmap deleteFolder (directoryes tree)

------------------------------ cvs operations with folders -----------------------------

initCvsFolderByPath :: [String] -> FilesTree -> Except Error FilesTree
initCvsFolderByPath = updateNode initCvsFolder

initCvsFolder :: FilesTree -> [FilesTree]
initCvsFolder tree = case isPatrCvs $ dirInfo tree of
    True -> [tree]
    False -> [tree {
        dirInfo = (dirInfo tree){isPatrCvs = True},
        directoryes = concat $ fmap initCvsFolder (directoryes tree)
    }]


addFolderToCvsByPath :: [String] -> FilesTree -> Except Error FilesTree
addFolderToCvsByPath = updateNode addFolderToCvs

addFolderToCvs :: FilesTree -> [FilesTree]
addFolderToCvs tree = [tree {
        dirInfo = (dirInfo tree){isPatrCvs = True},
        files = concat $ fmap addFileToCvs (files tree),
        directoryes = concat $ fmap addFolderToCvs (directoryes tree)
    }]

------------------------------ operations with files -----------------------------

updateFileByPath :: (FileInfo -> [FileInfo]) -> String -> [String] -> FilesTree -> Except Error FilesTree
updateFileByPath fun name = updateNode ((:[]) . updateFileNode fun name)

createFileByPath ::  String -> FileData -> UTCTime -> [String] -> FilesTree -> Except Error FilesTree
createFileByPath name fileData time = updateNode (createFileIfNeed name fileData time) where
    createFileIfNeed :: String -> FileData -> UTCTime -> FilesTree -> [FilesTree]
    createFileIfNeed name fileData time tree = case fileNotDeleted name tree of
        False -> [updateDirInfo $ tree {files = createNewFile path name fileData time : (files tree)}] where
            path = (dirPath $ dirInfo tree) ++ '/' : name
        True  -> [updateDirInfo $ updateFileNode ((:[]) . updateFile fileData time Changed) name tree]

deleteFileByPath :: String -> [String] -> FilesTree -> Except Error FilesTree
deleteFileByPath = updateFileByPath deleteFile

deleteFile :: FileInfo -> [FileInfo]
deleteFile info = case fileStatus info of
    Created -> []
    _       -> [info{fileStatus = Deleted, fileData = FileData [], size = 0}]

findFilesByName :: String -> [String] -> FilesTree -> Except Error [FileInfo]
findFilesByName name path tree = (findFilesByNameHelper name) <$> getTreeByPath path tree

findFilesByNameHelper :: String -> FilesTree -> [FileInfo]
findFilesByNameHelper name tree = getFileOrEmpty name (files tree) ++ concat (fmap (findFilesByNameHelper name) (directoryes tree))

getFileOrEmpty :: String -> [FileInfo] -> [FileInfo]
getFileOrEmpty name []     = []
getFileOrEmpty name (x:xs) = case fileName x == name && statusIsExist (fileStatus x) of
    True  -> [x]
    False -> getFileOrEmpty name xs

addFileToCvsByPath :: String -> [String] -> FilesTree -> Except Error FilesTree
addFileToCvsByPath = updateFileByPath addFileToCvs

addFileToCvs :: FileInfo -> [FileInfo]
addFileToCvs info = case length $ versions info of
    0 -> [info {
        versions = [Version {
            revisionData = fileData info,
            message = "initial"
        }]}]
    _ -> [info]

------------------------------ cvs operations with files -----------------------------

updateCvsFileByPath :: String -> String -> [String] -> FilesTree -> Except Error FilesTree
updateCvsFileByPath comment = updateFileByPath (updateCvsFile comment)

updateCvsFile :: String -> FileInfo -> [FileInfo]
updateCvsFile comment info = [info {
    versions = versions info ++ [Version (fileData info) comment]
}]

deleteCvsVersionByPath :: Int -> String -> [String] -> FilesTree -> Except Error FilesTree
deleteCvsVersionByPath ind = updateFileByPath (deleteCvsVersion ind)

deleteCvsVersion :: Int -> FileInfo -> [FileInfo]
deleteCvsVersion ind info = [info {
    versions = deleteAt ind (versions info)
}]

deleteFileFromCvsByPath :: String -> [String] -> FilesTree -> Except Error FilesTree
deleteFileFromCvsByPath = updateFileByPath deleteFileFromCvs

deleteFileFromCvs :: FileInfo -> [FileInfo]
deleteFileFromCvs info = [info {
    versions = []
}]

mergeFileVersionsByPath :: Int -> Int -> UTCTime -> String -> [String] -> FilesTree -> Except Error FilesTree
mergeFileVersionsByPath f s time = updateFileByPath (mergeFileVersions f s time)

mergeFileVersions :: Int -> Int -> UTCTime -> FileInfo -> [FileInfo]
mergeFileVersions f s time info = [updateFileData (mergeFileData (revisionData $ vers !! f) (revisionData $ vers !! s)) time info] where
    vers = versions info

mergeFileData :: FileData -> FileData -> FileData
mergeFileData (FileData a) (FileData b) = FileData res where
    first = splitOn "\n" a
    second = splitOn "\n" b
    res = intercalate "\n" (mergeStrings first second)

mergeStrings :: [String] -> [String] -> [String]
mergeStrings [] second     = second
mergeStrings first []      = first
mergeStrings (x:xs) (y:ys) = x : mergeStrings xs ys

------------------------------ operations with files and folders -----------------------------

deleteFileOrFolder :: [String] -> String -> FilesTree -> Except Error FilesTree
deleteFileOrFolder path name tree = if isFileExistByPath name path tree then deleteFileByPath name path tree else
    if isDirectoryExistByPath name path tree then deleteDirByPath name path tree else
        throwError $ Error $ "folder or file '" ++ name ++ "' doesn't exist"

------------------------------ test operations -----------------------------

isExist :: String -> FilesTree -> Bool
isExist name tree = isFileExist name tree || isDirectoryExist name tree

isExistByPath :: String -> [String] -> FilesTree -> Bool
isExistByPath name folders tree = isFileExistByPath name folders tree || isDirectoryExistByPath name folders tree

isFile :: Bool -> String -> FilesTree -> Bool
isFile checkDeleted name tree = exist name (files tree) where
    exist :: String -> [FileInfo] -> Bool
    exist _ []        = False
    exist name (x:xs) = if (fileName x) == name then checkDeleted || (statusIsExist $ fileStatus x) else exist name xs

isFileExist :: String -> FilesTree -> Bool
isFileExist = isFile False

fileNotDeleted :: String -> FilesTree -> Bool
fileNotDeleted = isFile True

isFileExistByPath :: String -> [String] -> FilesTree -> Bool
isFileExistByPath name path tree = case runExcept $ getTreeByPath path tree of
    (Left _)  -> False
    (Right t) -> isFileExist name t

isDirectory :: Bool -> String -> FilesTree -> Bool
isDirectory checkDeleted name tree = exist (directoryes tree) name where
    exist :: [FilesTree] -> String -> Bool
    exist [] _        = False
    exist (x:xs) name = if (dirName $ dirInfo x) == name then checkDeleted || statusIsExist (dirStatus $ dirInfo x) else exist xs name

isDirectoryExist :: String -> FilesTree -> Bool
isDirectoryExist = isDirectory False

directoryNotDeleted :: String -> FilesTree -> Bool
directoryNotDeleted = isDirectory True

isDirectoryExistByPath :: String -> [String] -> FilesTree -> Bool
isDirectoryExistByPath name path tree = case runExcept $ getTreeByPath path tree of
    (Left _)  -> False
    (Right t) -> isDirectoryExist name t

statusIsExist :: Status -> Bool
statusIsExist Deleted = False
statusIsExist _       = True

isNodeInCvs :: [String] -> FilesTree -> Bool
isNodeInCvs path tree = case runExcept $ getTreeByPath path tree of
    (Left _)  -> False
    (Right t) -> isPatrCvs $ dirInfo t
