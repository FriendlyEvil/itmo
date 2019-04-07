module Main where

import Lex
import Syntax

main = do
  inputString <- getLine
  putStrLn $ show $ parse $ alexScanTokens inputString
  putStrLn ""
