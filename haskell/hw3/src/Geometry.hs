{-# LANGUAGE BangPatterns #-}


module Geometry (Point(..),
    plus,
    minus,
    scalarProduct,
    crossProduct,
    perimeter,
    perimeter2,
    doubleArea,
    doubleArea2
    ) where

import           Data.List (foldl')

data Point = Point
    { x :: !Int
    , y :: !Int
    }

lenToPoint :: Point -> Double
lenToPoint (Point x y) = sqrt . fromIntegral $ x * x + y * y

plus :: Point -> Point -> Point
plus (Point x1 y1) (Point x2 y2) = Point (x1 + x2) (y1 + y2)

minus :: Point -> Point -> Point
minus (Point x1 y1) (Point x2 y2) = Point (x1 - x2) (y1 - y2)

scalarProduct :: Point -> Point -> Int
scalarProduct (Point x1 y1) (Point x2 y2) = x1 * x2 + y1 * y2

crossProduct :: Point -> Point -> Int
crossProduct (Point x1 y1) (Point x2 y2) = x1 * y2 - x2 * y1

len :: Point -> Point -> Double
len p1 p2 = lenToPoint $ minus p1 p2

neighboringPairs :: [a] -> [(a, a)]
neighboringPairs xs = helper (head xs) xs where
    helper :: a -> [a] -> [(a, a)]
    helper a (x:[])       = [(x, a)]
    helper a (x:b@(y:xs)) = (x, y) : helper a b

neighboring :: (a -> a -> b) -> [a] -> [b]
neighboring f xs = fmap (uncurry f) (neighboringPairs xs)

perimeter  :: [Point] -> Double
perimeter points = foldl' (+) 0.0 (neighboring len points)

perimeter2  :: [Point] -> Double
perimeter2 [] = 0.0
perimeter2 a@(x:xs) = perimeterHelper x a 0.0 where
    perimeterHelper :: Point -> [Point] -> Double -> Double
    perimeterHelper first [last] !tmp = tmp + len last first
    perimeterHelper first (x:b@(next:xs)) !tmp = perimeterHelper first b (tmp + len x next)

doubleArea :: [Point] -> Int
doubleArea points = foldl' (+) 0 (neighboring crossProduct points)


doubleArea2 :: [Point] -> Int
doubleArea2 [] = 0
doubleArea2 a@(x:xs) = areaHelper x a 0 where
    areaHelper :: Point -> [Point] -> Int -> Int
    areaHelper first [last] !tmp = tmp + crossProduct last first
    areaHelper first (x:b@(next:xs)) !tmp = areaHelper first b (tmp + crossProduct x next)
