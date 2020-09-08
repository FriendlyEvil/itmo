module Monads (Expr(..), ArithmeticError(..), eval, moving) where

import Control.Monad.State

data Expr = Const Int
  | Add Expr Expr
  | Sub Expr Expr
  | Mul Expr Expr
  | Div Expr Expr
  | Pow Expr Expr

data ArithmeticError = DivisionByZero | NegativePow deriving (Show, Eq)

eval :: Expr -> Either ArithmeticError Int
eval (Const val) = Right val
eval (Add first second) = (+) <$> eval first <*> eval second
eval (Sub first second) = (-) <$> eval first <*> eval second
eval (Mul first second) = (*) <$> eval first <*> eval second

eval (Div first second) = eval second >>= \secondEval -> case secondEval of
  0 -> Left DivisionByZero
  _ -> div <$> eval first <*> pure secondEval

eval (Pow first second) = eval second >>= \secondEval -> case secondEval < 0 of
  True  -> Left NegativePow
  False -> (^) <$> eval first <*> pure secondEval



data SimpleMovingAverageState = SimpleMovingAverageState {
-- i can't add queue to stack
  queue :: [Double],
  sums  :: Double,
  count :: Int
}


moving :: Int -> [Double] -> [Double]
moving _ [] = []
moving n (x : xs) = x : evalState (calculateAverage xs) SimpleMovingAverageState{queue=[x], sums=x, count=1} where

  calculateAverage :: [Double] -> State SimpleMovingAverageState [Double]
  calculateAverage []       = return []
  calculateAverage (x : xs) = (:) <$> (addNewPoint x) <*> (calculateAverage xs)

  addNewPoint :: Double -> State SimpleMovingAverageState Double
  addNewPoint val = do
    curState <- get
    if n == count curState
      then do
        let (el, t) = removeLast (queue curState)
        let s = sums curState + val - el
        put SimpleMovingAverageState{queue=(val:t), sums=s, count=(count curState)}
        return (s / fromIntegral (count curState))
      else do
        let s = sums curState + val
        let c = count curState + 1
        put SimpleMovingAverageState{queue=(val:(queue curState)), sums=s, count=c}
        return (s / fromIntegral c)

    where
      removeLast :: [a] -> (a, [a])
      removeLast (x : []) = (x, [])
      removeLast (x : xs) = (temp, x : l) where
        (temp, l) = removeLast xs

