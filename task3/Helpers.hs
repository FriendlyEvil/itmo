module Helpers where

import Lex
import Syntax
import HelpStructure
import Axioms
import Data.List

import qualified Data.Map.Strict as Map

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

parseHypothesis::[String]->Map.Map Expression Int
parseHypothesis ss = Map.fromList $ zip (Prelude.map parseString ss) [1..]

expressionsToLines::[Expression]->Map.Map Expression Int->([Line], Map.Map Int Expression)
expressionsToLines ex hyp = (rev, mp) where
  mapAns = exprToLine ex 1 Map.empty hyp Map.empty
  ans = map snd (Map.toList mapAns)
  rev = sortBy (\x y -> if (numLine x == numLine y) then EQ else if (numLine x > numLine y) then GT else LT) ans
  mp = Map.fromList (zip (map (numLine) ans) (map (expression) ans))

exprToLine::[Expression]->Int->Map.Map Expression Line->Map.Map Expression Int->Map.Map Expression [(Expression, Int)]->Map.Map Expression Line
exprToLine ee n ex hyp mp =
  if (length ee == 0) then ex else
    case (Map.lookup (head ee) ex) of
      (Just a) -> exprToLine (tail ee) (n+1) ex hyp mp
      Nothing ->
        exprToLine (tail ee) (n+1)( Map.insert (head ee) (setMPIndex (setHypothesis (inputToLine n (head ee)) hyp) mp ex) ex) hyp (isModusPonens (head ee) n mp)






isModusPonens::Expression->Int->Map.Map Expression [(Expression, Int)]->Map.Map Expression [(Expression, Int)]
isModusPonens (Calc  Impl a b) num mp = Map.insert b ((Calc  Impl a b, num) : (getFindMP b mp)) mp
isModusPonens _ num mp = mp

getFindMP::Expression->Map.Map Expression [(Expression, Int)]->[(Expression, Int)]
getFindMP b mp = let m = Map.lookup b mp in
  case m of
    (Just a) -> a
    Nothing -> []


f::Maybe [(Expression, Int)]->[(Expression, Int)]
f (Just a) = a
f Nothing = []

flook::Map.Map Int Line->Int->Line
flook rev a = case Map.lookup a rev of (Just b) -> b

g::Maybe Line->Int
g (Just a) = numLine a
g Nothing = -1

setMPIndex::Line->Map.Map Expression [(Expression, Int)]->Map.Map Expression Line->Line
setMPIndex ln mp ex = let a = f (Map.lookup (expression ln) mp) in
  if (length a == 0) then ln else
    let p = Prelude.filter helpPred (Prelude.map (mapFunHelp ex) a)
    in if (length p == 0) then ln else setMP ln (snd $ head p) (fst $ head p)

helpPred::(Int, Int)->Bool
helpPred (a, b) = (a /= -1) && (b /= -1)

mapFunHelp::Map.Map Expression Line->(Expression, Int)->(Int, Int)
mapFunHelp ex a = getMPIndex (fst a) (snd a) ex

getMPIndex::Expression->Int->Map.Map Expression Line->(Int, Int)
getMPIndex (Calc  Impl a b) num ex = (g (Map.lookup a ex), num)
getMPIndex _ num ex = ((-1), (-1))
