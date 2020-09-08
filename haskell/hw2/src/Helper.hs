module Helper (
    reversePair,
    getNameByFilePath,
    addToFirst,
    addToSecond,
    deleteAt,
    replaceEnter,
    splitByColon
) where

import           Control.Monad.Except (Except, throwError)
import           Error                (Error (..))

reversePair :: ([a], [b]) -> ([a], [b])
reversePair (a, b) = (reverse a, reverse b)

getNameByFilePath :: FilePath -> String
getNameByFilePath path = reverse $ getFileNameHelper path [] where
    getFileNameHelper :: String -> String -> String
    getFileNameHelper [] res     = res
    getFileNameHelper ('/':xs) _ = getFileNameHelper xs []
    getFileNameHelper (x:xs) res = getFileNameHelper xs (x:res)

addToFirst :: ([a], [b]) -> a -> ([a], [b])
addToFirst (a, b) x = (x : a, b)

addToSecond :: ([a], [b]) -> b -> ([a], [b])
addToSecond (a, b) x = (a, x : b)

deleteAt :: Int -> [a] -> [a]
deleteAt idx xs = lft ++ rgt where
    (lft, (_:rgt)) = splitAt idx xs

replaceEnter :: String -> String
replaceEnter ('\\':'n':xs) = '\n' : replaceEnter xs
replaceEnter (x:xs)        = x : replaceEnter xs
replaceEnter []            = []

splitByColon :: String -> Except Error [String]
splitByColon str = startSplit str []

startSplit :: String -> String -> Except Error [String]
startSplit [] res       = return $ [reverse res]
startSplit ('"':xs) res = (reverse res :) <$> endSplit xs []
startSplit (x:xs) res   = startSplit xs (x : res)

endSplit :: String -> String -> Except Error [String]
endSplit [] _         = throwError $ Error "end '\"' not found"
endSplit ('"':ys) res = (reverse res :) <$> startSplit ys []
endSplit (x:xs) res   = endSplit xs (x : res)
