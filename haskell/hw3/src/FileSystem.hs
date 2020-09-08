{-# LANGUAGE Rank2Types      #-}
{-# LANGUAGE RecordWildCards #-}


module FileSystem (
    FS(..),
    getDirectory,
    name,
    contents,
    dirs,
    files,
    cd,
    ls,
    file,
    changeExtention,
    removeEmptyDir,
    getRecusiveContent
    ) where


import           Data.List        (isInfixOf)
import           Lens.Micro       (Lens', Traversal', filtered, lens, traversed,
                                   (%~), (&), (.~), (^.), (^..))
import           System.Directory (doesDirectoryExist, listDirectory)

data FS = Dir
    { _name     :: FilePath
    , _contents :: [FS]
    }
    | File
    { _name :: FilePath
    }

getNameByFilePath :: FilePath -> String     -- from previos hw
getNameByFilePath path = reverse $ getFileNameHelper path [] where
    getFileNameHelper :: String -> String -> String
    getFileNameHelper [] res     = res
    getFileNameHelper ('/':xs) _ = getFileNameHelper xs []
    getFileNameHelper (x:xs) res = getFileNameHelper xs (x:res)

getDirectory :: FilePath -> IO (FS)
getDirectory p = do
    isDir <- doesDirectoryExist p
    case isDir of
        True  -> getDirInfo p
        False -> return $ getFileInfo p
    where

    getFileInfo :: FilePath -> FS
    getFileInfo path = File $ getNameByFilePath path

    getDirInfo :: FilePath -> IO (FS)
    getDirInfo path = do
        let name = getNameByFilePath path
        dirs <- listDirectory path
        fss <- subDirs path dirs
        return $ Dir name fss

    subDirs :: FilePath -> [FilePath] -> IO [FS]
    subDirs _ [] = return []
    subDirs prefix (name:xs) = (:) <$> getDirectory (prefix ++ '/' : name ) <*> subDirs prefix xs


name :: Lens' FS FilePath
name = lens _name setName where
    setName :: FS -> FilePath -> FS
    setName fs name = fs {_name = name}

contents :: Lens' FS [FS]
contents = lens getContents setContents where
    getContents :: FS -> [FS]
    getContents Dir {_contents = content} = content
    getContents File {..}                 = []
    setContents :: FS -> [FS] -> FS
    setContents fs@Dir {..} dirs = fs {_contents = dirs}
    setContents fs@File {..} _   = fs


isDir :: FS -> Bool
isDir Dir {..} = True
isDir _        = False

byName :: FilePath -> FS -> Bool
byName path fs = path == fs ^. name

dirs :: Traversal' FS FS
dirs = contents.traversed.filtered isDir

files :: Traversal' FS FS
files = contents.traversed.filtered (not . isDir)

cd :: FilePath -> Traversal' FS FS
cd path = dirs.filtered (byName path)

ls :: Traversal' FS FilePath
ls = contents.traversed.name

file :: FilePath -> Traversal' FS FilePath
file path = files.filtered(byName path).name

changeExtention :: String -> FS -> FS
changeExtention ext fs = fs & files.name %~ (\x -> changeExt ext x) where
    changeExt, changeExtHelper :: String -> String -> String
    changeExt ext str = case isInfixOf "." str of
        True -> reverse $ changeExtHelper (reverse ('.' : ext)) (reverse str)
        False -> str ++ '.' : ext
    changeExtHelper ext ('.':xs) = ext ++ xs
    changeExtHelper ext (x:xs)   = changeExtHelper ext xs

getRecusiveContent :: FS -> [FilePath]
getRecusiveContent fs = fs ^.. contents . traversed . name ++ (fs ^.. contents . traversed >>= getRecusiveContent)

removeEmptyDir :: FilePath -> FS -> FS
removeEmptyDir path fs = fs & contents .~ (fs ^.. contents.traversed.filtered (not . isEmptyDir path)) where
    isEmptyDir :: FilePath -> FS -> Bool
    isEmptyDir path fs = byName path fs && fs ^.. contents . traversed . name == []

