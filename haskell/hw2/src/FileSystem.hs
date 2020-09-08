module FileSystem (
    FileSystem(..),
    dumpFileSystem,
    saveFileSystem,
    getRootPath,
    getDirsByPath,
    goUp,
    addPathToSystem,
    getFilesByPath,
    createFolder,
    createFileIfNotExist,
    createFile,
    deleteFileOrFolderFromSystem,
    catFile,
    findFiles,
    getFile,
    getDirectory,
    initCvsOperation,
    cvsAddFolderOperation,
    cvsAddFileOperation,
    cvsUpdateFileOperation,
    cvsDeleteVersionOperation,
    cvsDeleteFileOperation,
    cvsFileHistoryOperation,
    cvsCatFileOperation,
    cvsMergeOperation
) where

import           Control.Monad.Except (Except, throwError)
import           Data.Time.Clock      (UTCTime)
import           Error                (Error (..))
import           FilesTree
import           Helper

data FileSystem = FileSystem
    { filesTree :: FilesTree
    , tmpPath   :: [String]
    }

dumpFileSystem :: FilePath -> IO (FileSystem)
dumpFileSystem path = do
    filesTree <- dumpFileTree path
    return FileSystem {
        tmpPath = [],
        filesTree = filesTree
    }

saveFileSystem :: FileSystem -> IO ()
saveFileSystem system = saveTree $ filesTree system

getRootPath :: FileSystem -> String
getRootPath system = dirPath $ dirInfo $ filesTree system

getDirsByPath :: [String] -> FileSystem -> Except Error [DirectoryInfo]
getDirsByPath path system = filter exist <$> fmap (fmap dirInfo) (directoryes <$> getNodeByPath path system) where
    exist :: DirectoryInfo -> Bool
    exist info = statusIsExist $ dirStatus info

getFilesByPath :: [String] -> FileSystem -> Except Error [FileInfo]
getFilesByPath path system = filter exist <$> files <$> getNodeByPath path system where
    exist :: FileInfo -> Bool
    exist info = statusIsExist $ fileStatus info

getNodeByPath :: [String] -> FileSystem -> Except Error FilesTree
getNodeByPath path system = getTreeByPath path (filesTree system)

addPathToSystem :: [String] -> FileSystem -> Except Error FileSystem
addPathToSystem path system = getNodeByPath path system >> return (createSystem path (filesTree system))

goUp :: FileSystem -> FileSystem
goUp system = system {tmpPath = deleteLast $ tmpPath system} where
    deleteLast :: [String] -> [String]
    deleteLast []     = []
    deleteLast (x:[]) = []
    deleteLast (x:xs) = x : deleteLast xs

createFolder :: String -> FileSystem -> Except Error FileSystem
createFolder name system = case isExistByPath name (tmpPath system) (filesTree system) of
    True -> throwError $ Error $ "file or folder '" ++ name ++ "' already exist"
    False -> changeSystemTree system <$> createDirByPath name (tmpPath system) (filesTree system)

changeSystemTree :: FileSystem -> FilesTree -> FileSystem
changeSystemTree system tree = system {filesTree = tree}

createSystem :: [String] -> FilesTree -> FileSystem
createSystem tmp tree = FileSystem {
    filesTree = tree,
    tmpPath = tmp
}

createFile :: String -> FileData -> UTCTime -> FileSystem -> Except Error FileSystem
createFile name fileData time system = changeSystemTree system <$> createFileByPath name fileData time (tmpPath system) (filesTree system)

createFileIfNotExist :: String -> FileData -> UTCTime -> FileSystem -> Except Error FileSystem
createFileIfNotExist name fileData time system = case isExistByPath name (tmpPath system) (filesTree system) of
    True -> throwError $ Error $ "file or folder '" ++ name ++ "' already exist"
    False -> createFile name fileData time system

deleteFileOrFolderFromSystem :: String -> FileSystem -> Except Error FileSystem
deleteFileOrFolderFromSystem name system = changeSystemTree system <$> deleteFileOrFolder (tmpPath system) name (filesTree system)

catFile :: String -> FileSystem -> Except Error String
catFile name system = getFileData <$> fileData <$> getFile name system where

getFileData :: FileData -> String
getFileData (FileData str) = str

findFiles :: String -> FileSystem -> Except Error [FileInfo]
findFiles name system = findFilesByName name (tmpPath system) (filesTree system)

getFile :: String -> FileSystem -> Except Error FileInfo
getFile name system = getFileByPath name (tmpPath system) (filesTree system)

getDirectory :: String -> FileSystem -> Except Error DirectoryInfo
getDirectory name system = dirInfo <$> getTreeByPath (tmpPath system ++ [name]) (filesTree system)

initCvsOperation :: FileSystem -> Except Error FileSystem
initCvsOperation system = case isNodeInCvs (tmpPath system) (filesTree system) of
    True -> throwError $ Error $ "cvs alredy init"
    False -> changeSystemTree system <$> initCvsFolderByPath (tmpPath system) (filesTree system)

cvsOperation :: ([String] -> FilesTree -> Except Error FilesTree) -> [String] -> FileSystem -> Except Error FilesTree
cvsOperation fun path system = case isNodeInCvs (tmpPath system) (filesTree system) of
    False -> throwError $ Error $ "cvs don't init"
    True  -> fun path (filesTree system)

checkCvs :: FileSystem -> Except Error FilesTree
checkCvs system = cvsOperation (\_ tree -> return tree) (tmpPath system) system

cvsFileOperation :: ([String] -> FilesTree -> Except Error FilesTree) -> FileSystem -> Except Error FileSystem
cvsFileOperation fun system = changeSystemTree system <$> cvsOperation fun (tmpPath system) system

cvsAddFolderOperation :: String -> FileSystem -> Except Error FileSystem
cvsAddFolderOperation name system = changeSystemTree system <$> cvsOperation addFolderToCvsByPath (tmpPath system ++ [name]) system

cvsAddFileOperation :: String -> FileSystem -> Except Error FileSystem
cvsAddFileOperation name = cvsFileOperation $ addFileToCvsByPath name

cvsUpdateFileOperation :: String -> String -> FileSystem -> Except Error FileSystem
cvsUpdateFileOperation comment name = cvsFileOperation $ updateCvsFileByPath comment name

cvsCheckInd :: Int -> String -> FileSystem -> Except Error FileSystem
cvsCheckInd ind name system = (> ind) . length <$> (cvsFileVersionsOperation name system) >>= (\b -> if b then return system else throwError $ Error $ "index out of range")

cvsDeleteVersionOperation :: Int -> String -> FileSystem -> Except Error FileSystem
cvsDeleteVersionOperation ind name system = cvsCheckInd ind name system >> cvsFileOperation (deleteCvsVersionByPath ind name) system

cvsDeleteFileOperation :: String -> FileSystem -> Except Error FileSystem
cvsDeleteFileOperation name = cvsFileOperation $ deleteFileFromCvsByPath name

cvsFileVersionsOperation :: String -> FileSystem -> Except Error [Version]
cvsFileVersionsOperation name system = checkCvs system >> versions <$> getFile name system

cvsFileHistoryOperation :: String -> FileSystem -> Except Error [String]
cvsFileHistoryOperation name system = fmap (fmap message) (cvsFileVersionsOperation name system)

cvsCatFileOperation :: Int -> String -> FileSystem -> Except Error String
cvsCatFileOperation ind name system = cvsCheckInd ind name system >> getFileData . revisionData . (!!ind) <$> cvsFileVersionsOperation name system

cvsMergeOperation :: Int -> Int -> UTCTime -> String -> FileSystem -> Except Error FileSystem
cvsMergeOperation f s time name system = cvsCheckInd f name system >> cvsCheckInd s name system >> cvsFileOperation (mergeFileVersionsByPath f s time name) system

