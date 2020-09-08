module Main (main) where

import           Command
import           Control.Exception    (IOException, catch)
import           Control.Monad.Except (Except, lift, runExcept, throwError)
import           Control.Monad.State  (StateT, get, put, runStateT)
import           Data.List            (intercalate)
import           Data.List.Split      (splitOn)
import           Data.Time.Clock      (UTCTime, getCurrentTime)
import           Error                (Error (..))
import           FileSystem
import           Helper               (replaceEnter, splitByColon)
import           System.Environment   (getArgs)
import           System.IO            (hFlush, stdout)

main :: IO ()
main = do
    args <- getArgs
    if length args /= 1 then putStrLn "program need one argument" else do
        c <- catch (do
                state <- dumpFileSystem $ head args
                return $ Right state
            )
            (\e -> return $ Left $ show (e :: IOException))
        case c of
            (Left msg)    -> putStrLn msg
            (Right state) -> mainProcess Continue state

data Condition = Continue
    | Break

mainProcess :: Condition -> FileSystem -> IO ()
mainProcess Continue state = do
    (con, st) <- runStateT process state
    mainProcess con st
mainProcess Break system = catch (saveFileSystem system) (\e -> putStrLn $ show (e :: IOException))

process :: StateT FileSystem IO(Condition)
process = do
    tempDir <- get
    lift $ putStr $ getMessage tempDir
    lift $ hFlush stdout
    command <- lift $ getLine
    time <- lift $ getCurrentTime
    case runExcept $ parse command time of
        (Left e) -> (lift $ putStrLn $ show e) >> return Continue
        (Right Exit) -> return Break
        (Right (Command cmd)) -> case runExcept $ cmd tempDir of
            (Left e) -> (lift $ putStrLn $ show e) >> return Continue
            (Right result) -> put (system result) >> (lift $ mapM_ putStrLn (text result)) >> return Continue

getMessage :: FileSystem -> String
getMessage system = intercalate "/" (getRootPath system : tmpPath system) ++ "> "

parse :: String -> UTCTime -> Except Error Command
parse str time = do
    command <- splitByColon str
    case command of
        [] -> throwError $ Error "empty command"
        (x:xs) -> do
            let res = fmap replaceEnter (filter (\s -> s /= "") (splitOn " " x ++ xs))
            mainParse res time

mainParse :: [String] -> UTCTime -> Except Error Command
mainParse [] _ = throwError $ Error "empty command"
mainParse (x:xs) time = case x of
    "q"                  -> return Exit
    "exit"               -> return Exit
    "cd"                 -> parseCdArgs xs
    "dir"                -> parseDirArgs xs
    "ls"                 -> parseLsArgs xs
    "create-folder"      -> parseCreateFolderArgs xs
    "create-file"        -> parseCreateFileArgs time xs
    "write-file"         -> parseWriteFileArgs time xs
    "delete"             -> parseDeleteArgs xs
    "cat"                -> parseCatArgs xs
    "find-file"          -> parseFindFileArgs xs
    "file-info"          -> parseFileInformationArgs xs
    "folder-info"        -> parseDirectoryInformationArgs xs
    "cvs-init"           -> parseCvsInitArgs xs
    "cvs-add-folder"     -> parseCvsAddFolderArgs xs
    "cvs-add-file"       -> parseCvsAddFileArgs xs
    "cvs-update"         -> parseCvsUpdateFileArgs xs
    "cvs-delete-version" -> parseCvsDeleteVersionArgs xs
    "cvs-delete"         -> parseCvsDeleteFileArgs xs
    "cvs-history"        -> parseCvsFileHistoryArgs xs
    "cvs-cat"            -> parseCvsCatFileArgs xs
    "cvs-merge"          -> parseCvsMergeArgs time xs
    "help"               -> parseHelpArgs xs
    otherwise            -> throwError $ Error "unknown command"

parseHelpArgs :: [String] -> Except Error Command
parseHelpArgs = parseZeroArgsCommand "help" help

parseCdArgs :: [String] -> Except Error Command
parseCdArgs = parseOneArgsCommand "cd" cdCommand

parseDirArgs :: [String] -> Except Error Command
parseDirArgs = parseZeroArgsCommand "dir" dirCommand

parseLsArgs :: [String] -> Except Error Command
parseLsArgs = parseOneArgsCommand "ls" lsCommand

parseCreateFolderArgs :: [String] -> Except Error Command
parseCreateFolderArgs = parseOneArgsCommand "create-folder" createFolderCommand

parseCreateFileArgs :: UTCTime -> [String] -> Except Error Command
parseCreateFileArgs time = parseOneArgsCommand "create-file" (createFileCommand time)

parseWriteFileArgs :: UTCTime -> [String] -> Except Error Command
parseWriteFileArgs time = parseTwoArgsCommand "write-file" (writeFileCommand time)

parseDeleteArgs :: [String] -> Except Error Command
parseDeleteArgs = parseOneArgsCommand "delete" deleteCommand

parseCatArgs :: [String] -> Except Error Command
parseCatArgs = parseOneArgsCommand "cat" catCommand

parseFindFileArgs :: [String] -> Except Error Command
parseFindFileArgs = parseOneArgsCommand "find-file" findFileCommand

parseFileInformationArgs :: [String] -> Except Error Command
parseFileInformationArgs = parseOneArgsCommand "file-information" showFileCommand

parseDirectoryInformationArgs :: [String] -> Except Error Command
parseDirectoryInformationArgs = parseOneArgsCommand "folder-information" showDirectoryCommand

parseCvsInitArgs :: [String] -> Except Error Command
parseCvsInitArgs = parseZeroArgsCommand "cvs-init" initCvs

parseCvsAddFolderArgs :: [String] -> Except Error Command
parseCvsAddFolderArgs = parseOneArgsCommand "cvs-add-folder" cvsAddFolder

parseCvsAddFileArgs :: [String] -> Except Error Command
parseCvsAddFileArgs = parseOneArgsCommand "cvs-add-file" cvsAddFile

parseCvsUpdateFileArgs :: [String] -> Except Error Command
parseCvsUpdateFileArgs = parseTwoArgsCommand "cvs-update" cvsUpdateFile

parseCvsDeleteVersionArgs :: [String] -> Except Error Command
parseCvsDeleteVersionArgs = parseTwoArgsSecondIntCommand "cvs-delete-version" cvsDeleteVersion

parseCvsDeleteFileArgs :: [String] -> Except Error Command
parseCvsDeleteFileArgs = parseOneArgsCommand "cvs-delete" cvsDeleteFile

parseCvsFileHistoryArgs :: [String] -> Except Error Command
parseCvsFileHistoryArgs = parseOneArgsCommand "cvs-history" cvsFileHistory

parseCvsCatFileArgs :: [String] -> Except Error Command
parseCvsCatFileArgs = parseTwoArgsSecondIntCommand "cvs-cat" cvsCatFile

parseCvsMergeArgs :: UTCTime -> [String] -> Except Error Command
parseCvsMergeArgs time = parseTwoArgsTwoIntCommand "cvs-merge" (cvsMerge time)

parseZeroArgsCommand :: String -> (FileSystem -> Except Error Result) -> [String] -> Except Error Command
parseZeroArgsCommand _ fun [] = return $ Command fun
parseZeroArgsCommand commandName _ _ = throwError $ Error $ commandName ++ " command don't have arguments"

parseOneArgsCommand :: String -> (String -> FileSystem -> Except Error Result) -> [String] -> Except Error Command
parseOneArgsCommand _ fun (x:[]) = return $ Command $ fun x
parseOneArgsCommand commandName _ _ = throwError $ Error $ commandName ++ " command must have one argument"

parseTwoArgsCommand :: String -> (String -> String -> FileSystem -> Except Error Result) -> [String] -> Except Error Command
parseTwoArgsCommand _ fun (x:y:[]) = return $ Command $ fun x y
parseTwoArgsCommand commandName _ _ = throwError $ Error $ commandName ++ " command must have two argument"

parseTwoArgsSecondIntCommand :: String -> (String -> Int -> FileSystem -> Except Error Result) -> [String] -> Except Error Command
parseTwoArgsSecondIntCommand _ fun (x:y:[]) = return $ Command $ fun x (read y)
parseTwoArgsSecondIntCommand commandName _ _ = throwError $ Error $ commandName ++ " command must have two argument"

parseTwoArgsTwoIntCommand :: String -> (String -> Int -> Int -> FileSystem -> Except Error Result) -> [String] -> Except Error Command
parseTwoArgsTwoIntCommand _ fun (x:y:z:[]) = return $ Command $ fun x (read y) (read z)
parseTwoArgsTwoIntCommand commandName _ _ = throwError $ Error $ commandName ++ " command must have two argument"
