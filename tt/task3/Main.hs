module Main where

import Lex
import Syntax
import ImplExpression

main = do
  inputString <- getContents
  let input = parse $ alexScanTokens inputString
  let a = createImpl (ImplExpr input) (ImplExpr input)
  putStr $ show a
