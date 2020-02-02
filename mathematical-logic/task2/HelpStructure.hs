module HelpStructure where

import Axioms
import Syntax

import Data.Map.Strict

data Line = Line {
  numLine::Int,
  expression::Expression,
  numAxiom::Int,
  numHypothesis::Int,
  numModPon::(Int,Int)
}

instance Ord Line where
  (<) a b = (expression a) < (expression b)
  (<=) a b = (expression a) <= (expression b)

instance Eq Line where
  (==) a b = (expression a) == (expression b)



rmdups :: Eq a => [a] -> [a]
rmdups [] = []
rmdups (x:xs)   | x `elem` xs   = rmdups xs
                | otherwise     = x : rmdups xs

setAxiom::Line->Line
setAxiom line = line{numAxiom = (getAxiom $ expression line)}

setHypothesis::Line->Map Expression Int->Line
setHypothesis line mapH =
  let a = getHyp (expression line) mapH in line{numHypothesis = (a)}

getHyp::Expression->Map Expression Int->Int
getHyp ex m = case (Data.Map.Strict.lookup ex m) of
  (Just a) -> a
  Nothing -> (-1)

setMP::Line->Int->Int->Line
setMP line f s = line{numModPon = (f, s)}

isAxiomOrHypothesis::Line->Bool
isAxiomOrHypothesis line = ((numAxiom line) /= (-1)) || ((numHypothesis line) /= (-1))

isCorrect::Line->Bool
isCorrect ln = isAxiomOrHypothesis ln || (fst (numModPon ln) /= (-1) && snd (numModPon ln) /= (-1))

isMP::Line->Bool
isMP ln = fst (numModPon ln) /= (-1) && snd (numModPon ln) /= (-1)

getAnnotation::Line->Map Int Int->String
getAnnotation line mp =
  if (numHypothesis line /= (-1)) then
  ". Hypothesis " ++ show (numHypothesis line)
  else if (numAxiom line /= (-1)) then
    ". Ax. sch. " ++ show (numAxiom line)
    else if (fst (numModPon line) /= (-1) && snd (numModPon line) /= (-1)) then
      ". M.P. " ++ show (mapRes (fst (numModPon line)) mp) ++ ", " ++ show (mapRes (snd (numModPon line)) mp) else
        ""

convertToString::Line->Map Int Int->String
convertToString line numMap = "[" ++ show (mapRes (numLine line) numMap) ++ getAnnotation line numMap ++ "] " ++ show (expression line)

mapRes::Int->Map Int Int->Int
mapRes i mp = case Data.Map.Strict.lookup i mp of
  (Just a) -> a
  Nothing -> (-1)
