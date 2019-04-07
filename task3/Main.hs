module Main where

import Data.Map.Strict
import Data.List
import System.IO
import Data.Maybe

import Lex
import Syntax
import HelpStructure
import Helpers
import Axioms
import Deduction
import Proofs

main = do
  parseFirstString

parseFirstString::IO()
parseFirstString = do
  str <- getLine

  let hh = split2 str
  let pair = parseFirstString1 (split2 str)
  (ex, res, n) <- parseSolution 1 Data.Map.Strict.empty (fst pair) Data.Map.Strict.empty (inputToLine (-1) (snd pair))
  let lss = Prelude.map snd (toList ex)
  let rev = sortBy (\x y -> if (numLine x == numLine y) then EQ else if (numLine x > numLine y) then GT else LT) (rmdups lss)
  let mapNum = fromList $ zip (Prelude.map (numLine) rev) (Prelude.map (expression) rev)
  putStrLn ((head hh) ++ "|- !!(" ++ (last hh) ++ ")")
  mapM_ (putStrLn . show) (proof rev mapNum)
  mapM_ (putStrLn . show) (proof [res] mapNum)
  putStrLn ""

parseFirstString1::[String]->(Map Expression Int, Expression)
parseFirstString1 ss = if length ss == 1 then (Data.Map.Strict.empty, parseString (head ss))
  else (parseHypothesis (Prelude.filter (\x -> x/="") ((split1) (head ss))), parseString (last ss))

parseSolution::Int->Map Expression Line->Map Expression Int->Map Expression [(Expression, Int)]->Line->IO(Map Expression Line, Line, Int)
parseSolution n ex hyp mp lst = do
  eof <- isEOF
  if (eof) then return (ex, lst, n) else do
    str <- getLine
    expr <- return $ parseString str
    case (Data.Map.Strict.lookup expr ex) of
      (Just a) -> parseSolution (n+1) ex hyp mp a
      Nothing -> do
      ln <- return $ inputToLine n expr
      ln <- return $ setHypothesis ln hyp
      ln <- return $ setMPIndex ln mp ex

      mp <- return $ isModusPonens expr n mp

      ex <- return $ Data.Map.Strict.insert expr ln ex
      parseSolution (n+1) ex hyp mp ln


getAns::Line->[(Expression, Line)]->Map Line Bool->Map Int Bool->Map Line Bool
getAns ln ex ans mp = if (length ex == 0) then
  if (isJust $ Data.Map.Strict.lookup (numLine ln) mp) then Data.Map.Strict.insert ln True ans else ans
  else
    if (isJust $ Data.Map.Strict.lookup (numLine ln) mp) then getAns (snd $ head ex) (tail ex) (Data.Map.Strict.insert ln True ans) (
      if (isAxiomOrHypothesis ln) then mp else Data.Map.Strict.insert (snd $ numModPon ln) True (Data.Map.Strict.insert (fst $ numModPon ln) True mp))
    else (getAns (snd $ head ex) (tail ex) ans mp)
