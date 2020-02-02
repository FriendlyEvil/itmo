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

main = do
  parseFirstString

parseFirstString::IO()
parseFirstString = do
  str <- getLine
  let pair = parseFirstString1 (split2 str)
  (ex, res, n) <- parseSolution 1 Data.Map.Strict.empty (fst pair) Data.Map.Strict.empty (inputToLine (-1) (snd pair))
  let lss = Prelude.map snd (toList ex)
  let m1 = Prelude.filter isCorrect lss
  if (length m1 /= length lss || (expression res) /= (snd pair) || n == 1) then putStrLn "Proof is incorrect" else do
    let mm = getAns res (reverse $ sortBy (\(x1,x) (y1,y) -> if (numLine x == numLine y) then EQ else if (numLine x > numLine y) then GT else LT) (toList ex)) Data.Map.Strict.empty (fromList [((numLine res), True)])
    let m = Prelude.map fst (toList mm)
    putStrLn str
    let rev = sortBy (\x y -> if (numLine x == numLine y) then EQ else if (numLine x > numLine y) then GT else LT) (rmdups m)
    let mapNum = fromList $ zip (Prelude.map (numLine) rev) [1..]
    putStrLn ""
    putStrLn ""
    putStrLn ""
    putStrLn ""
    putStrLn ""
    putStrLn ""
    mapM_ (\x -> putStrLn $ convertToString x mapNum) (rev)
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

isModusPonens::Expression->Int->Map Expression [(Expression, Int)]->Map Expression [(Expression, Int)]
isModusPonens (Calc  Impl a b) num mp = Data.Map.Strict.insert b ((Calc  Impl a b, num) : (getFindMP b mp)) mp
isModusPonens _ num mp = mp

getFindMP::Expression->Map Expression [(Expression, Int)]->[(Expression, Int)]
getFindMP b mp = let m = Data.Map.Strict.lookup b mp in
  case m of
    (Just a) -> a
    Nothing -> []


f::Maybe [(Expression, Int)]->[(Expression, Int)]
f (Just a) = a
f Nothing = []

flook::Map Int Line->Int->Line
flook rev a = case Data.Map.Strict.lookup a rev of (Just b) -> b

g::Maybe Line->Int
g (Just a) = numLine a
g Nothing = -1

setMPIndex::Line->Map Expression [(Expression, Int)]->Map Expression Line->Line
setMPIndex ln mp ex = let a = f (Data.Map.Strict.lookup (expression ln) mp) in
  if (length a == 0) then ln else
    let p = Prelude.filter helpPred (Prelude.map (mapFunHelp ex) a)
    in if (length p == 0) then ln else setMP ln (snd $ head p) (fst $ head p)

helpPred::(Int, Int)->Bool
helpPred (a, b) = (a /= -1) && (b /= -1)

mapFunHelp::Map Expression Line->(Expression, Int)->(Int, Int)
mapFunHelp ex a = getMPIndex (fst a) (snd a) ex

getMPIndex::Expression->Int->Map Expression Line->(Int, Int)
getMPIndex (Calc  Impl a b) num ex = (g (Data.Map.Strict.lookup a ex), num)
getMPIndex _ num ex = ((-1), (-1))
