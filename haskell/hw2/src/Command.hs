module Command (
    Result(..),
    Command(..),
    cdCommand,
    dirCommand,
    lsCommand,
    createFolderCommand,
    createFileCommand,
    writeFileCommand,
    deleteCommand,
    catCommand,
    findFileCommand,
    showFileCommand,
    showDirectoryCommand,
    initCvs,
    cvsAddFolder,
    cvsAddFile,
    cvsUpdateFile,
    cvsDeleteVersion,
    cvsDeleteFile,
    cvsFileHistory,
    cvsCatFile,
    cvsMerge,
    help
) where

import           Control.Monad.Except (Except)
import           Data.List            (intercalate)
import           Data.Time.Clock      (UTCTime)
import           Error                (Error)
import           FilesTree            (DirectoryInfo (..), FileData (..),
                                       FileInfo (..))
import           FileSystem


data Result = Result
    { system :: FileSystem
    , text   :: [String]
    }

createResult :: FileSystem -> [String] -> Result
createResult system text = Result {
    system = system,
    text = text
}

createResultWithoutMessage :: FileSystem -> Result
createResultWithoutMessage system = Result {
    system = system,
    text = []
}

updateTree :: (String -> FileSystem -> Except Error FileSystem) -> String -> FileSystem -> Except Error Result
updateTree fun name system = createResultWithoutMessage <$> fun name system

data Command = Command (FileSystem -> Except Error Result)
    | Exit

cdCommand :: String -> FileSystem -> Except Error Result
cdCommand name system = case name of
    ".."      -> return $ createResultWithoutMessage $ goUp system
    "."       -> return $ createResultWithoutMessage system
    otherwise -> createResultWithoutMessage <$> addPathToSystem ((tmpPath system) ++ [name]) system

dirCommand :: FileSystem -> Except Error Result
dirCommand system = lsByPath (tmpPath system) system

lsCommand :: String -> FileSystem -> Except Error Result
lsCommand name system = case name of
    "."       -> lsByPath (tmpPath system) system
    otherwise -> lsByPath ((tmpPath system) ++ [name]) system

lsByPath :: [String] -> FileSystem -> Except Error Result
lsByPath path system = createResult system <$> list where
    files = (fmap (fmap fileName) (getFilesByPath path system))
    dirs  = (fmap (fmap dirName) (getDirsByPath path system))
    list = (++) <$> files <*> dirs

createFolderCommand :: String -> FileSystem -> Except Error Result
createFolderCommand = updateTree createFolder

createFileCommand :: UTCTime -> String -> FileSystem -> Except Error Result
createFileCommand time name system = createResultWithoutMessage <$> createFileIfNotExist name (FileData []) time system

writeFileCommand :: UTCTime -> String -> String -> FileSystem -> Except Error Result
writeFileCommand time name fileData system = createResultWithoutMessage <$> createFile name (FileData fileData) time system

deleteCommand :: String -> FileSystem -> Except Error Result
deleteCommand = updateTree deleteFileOrFolderFromSystem

catCommand :: String -> FileSystem -> Except Error Result
catCommand name system = createResult system <$> (:[]) <$> catFile name system

findFileCommand :: String -> FileSystem -> Except Error Result
findFileCommand name system = createResult system <$> fmap (fmap filePath) (findFiles name system)

showFileCommand :: String -> FileSystem -> Except Error Result
showFileCommand name system = createResult system <$> (:[]) . show <$> getFile name system

showDirectoryCommand :: String -> FileSystem -> Except Error Result
showDirectoryCommand name system = createResult system <$> (:[]) . show <$> getDirectory name system

initCvs :: FileSystem -> Except Error Result
initCvs system = createResultWithoutMessage <$> initCvsOperation system

cvsAddFolder :: String -> FileSystem -> Except Error Result
cvsAddFolder = updateTree cvsAddFolderOperation

cvsAddFile :: String -> FileSystem -> Except Error Result
cvsAddFile = updateTree cvsAddFileOperation

cvsUpdateFile :: String -> String -> FileSystem -> Except Error Result
cvsUpdateFile name message system = updateTree (cvsUpdateFileOperation message) name system

cvsDeleteVersion :: String -> Int -> FileSystem -> Except Error Result
cvsDeleteVersion name ind system = updateTree (cvsDeleteVersionOperation ind) name system

cvsDeleteFile :: String -> FileSystem -> Except Error Result
cvsDeleteFile = updateTree cvsDeleteFileOperation

cvsFileHistory :: String -> FileSystem -> Except Error Result
cvsFileHistory name system = createResult system <$> fmap toString <$> zip [0..] <$> (cvsFileHistoryOperation name system) where
    toString :: (Integer, String) -> String
    toString (ind, text) = show ind ++ " " ++ text

cvsCatFile :: String -> Int -> FileSystem -> Except Error Result
cvsCatFile name ind system = createResult system <$> (:[]) <$> cvsCatFileOperation ind name system

cvsMerge :: UTCTime -> String -> Int -> Int -> FileSystem -> Except Error Result
cvsMerge time name f s system = updateTree (cvsMergeOperation f s time) name system

help :: FileSystem -> Except Error Result
help system = return $ createResult system helpMessage

helpMessage :: [String]
helpMessage = "cd <folder | . | .. > -- перейти в директорию" :
    "dir -- показать содержимое текущей директории" :
    "ls <folder | . > -- показать содержимое выбранной директории" :
    "create-folder <folder-name> -- создать директорию в текущей" :
    "cat <file> -- показать содержимое файла" :
    "create-file <file-name> -- создать пустой файл в текущей директории" :
    "delete <folder | file> -- удалить выборанную директорию или файл" :
    "write-file <file> text -- записать текст в файл" :
    "find-file <file-name> --  поиск файла в текущией директории и поддиректориях" :
    "file-info <file> -- показать информацию о файле" :
    "folder-info <folder> -- показать информацию о директории" :
    "cvs-init -- инициализация СКВ в текущей выбранной директории" :
    "cvs-add-file <file> -- добавление файла в СКВ" :
    "cvs-add-folder <folder> -- добавление папки в СКВ" :
    "cvs-update <file> comment -- добавление изменений файла в СКВ" :
    "cvs-history <file> -- просмотр истории изменений файла" :
    "cvs-cat <file> index -- просмотр конкретной ревизии файла" :
    "cvs-merge <file> index1 index2 -- объедиение ревизий. За основу взята ревизия index1" :
    "cvs-delete-version <file> index -- удалить заданную версию файла из ревизий" :
    "cvs-delete <file> -- удалить файл из СКВ" :
    "help --  показать руководство по использованию" :
    "exit | q -- завершение работы программы" : []
