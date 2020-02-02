module Main where

import Lex
import Syntax

main = do
  inputString <- getContents
  putStrLn $ show $ parse $ alexScanTokens inputString
  putStrLn ""
