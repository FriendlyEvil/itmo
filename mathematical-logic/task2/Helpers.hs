module Helpers where

import Lex
import Syntax
import HelpStructure
import Axioms

import Data.Map.Strict

split1 :: String -> [String]
split1 "" = [""]
split1 (',':cs) = "" : split1 cs
split1 (c:cs) = (c:cellCompletion) : otherCells
 where cellCompletion : otherCells = split1 cs

split2 :: String -> [String]
split2 "" = [""]
split2 ('|':'-':cs) = "" : split2 cs
split2 (c:cs) = (c:cellCompletion) : otherCells
  where cellCompletion : otherCells = split2 cs

parseString::String->Expression
parseString s = parse $ alexScanTokens s

parseHypothesis::[String]->Map Expression Int
parseHypothesis ss = fromList $ zip (Prelude.map parseString ss) [1..]

inputToLine::Int->Expression->Line
inputToLine num ex = Line {numLine = num, expression = ex, numAxiom = (getAxiom ex), numHypothesis = (-1), numModPon = ((-1), (-1))}
