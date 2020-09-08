{-# LANGUAGE TypeOperators #-}
{-# LANGUAGE ScopedTypeVariables #-}

module Lib
       ( distributivity, associator, eitherAssoc,
       identity, composition, contraction, permutation,
       iterateElement, fibonacci, factorial, mapFix,
       doubleNeg, excludedNeg, pierce, doubleNegElim, thirdNegElim,
       succChurch, churchPlus, churchMult, churchToInt,
       firstTask, secondTask, thirdTask
       ) where

import Data.Void (Void)
import Data.Function (fix)
import Data.Either (lefts, rights)

-- | implementing distribution property
distributivity :: Either a (b, c) -> (Either a b, Either a c)
distributivity (Left a) = (Left a, Left a)
distributivity (Right (b, c)) = (Right b, Right c)

-- | implementing associative property
associator :: (a, (b, c)) -> ((a, b), c)
associator (a, (b, c)) = ((a, b), c)


type (<->) a b = (a -> b, b -> a)

-- | implementing associative property in both directions
eitherAssoc :: Either a (Either b c) <-> Either (Either a b) c
eitherAssoc = (first, second) where
  first (Left a) = Left $ Left a
  first (Right (Left b)) = Left $ Right b
  first (Right (Right c)) = Right c
  second (Left (Left a)) = Left a
  second (Left (Right b)) = Right $ Left b
  second (Right c) = Right $ Right c


type Neg a = a -> Void

doubleNeg :: a -> Neg (Neg a)
doubleNeg a = (\f -> f a)

excludedNeg :: Neg (Neg (Either a (Neg a)))
excludedNeg f = f $ Left undefined

-- | cannot be implemented
pierce :: ((a -> b) -> a) -> a
pierce = undefined

-- | cannot be implemented
doubleNegElim :: Neg (Neg a) -> a
doubleNegElim = undefined

thirdNegElim :: Neg (Neg (Neg a)) -> Neg a
thirdNegElim f = const (f undefined)


s :: (a -> b -> c) -> (a -> b) -> a -> c
s f g x = f x (g x)

-- | implementation of the composition via s k basic
composition :: (b -> c) -> (a -> b) -> a -> c
composition = s (const s) const

-- | implementation of the identity via s k basic
identity :: a -> a
identity = s const const

-- | implementation of the contraction via s k basic
contraction :: (a -> a -> b) -> a -> b
contraction = s s (s const)

-- | implementation of the permutation via s k basic
permutation :: (a -> b -> c) -> b -> a -> c
permutation = (s (s (const (s (const s) const)) s) (const const))


-- | implementing an infinite list of one element via fix function
iterateElement :: a -> [a]
iterateElement a = fix (a:)

-- | implementation of the Fibonacci number calculation via fix function
fibonacci :: Integer -> Integer
fibonacci = fix (\_ -> fib) where
  fib 0 = 1
  fib 1 = 1
  fib n = fib (n - 1) + fib (n - 2)

-- | implementation of the factorial calculation via fix function
factorial :: Integer -> Integer
factorial = fix (\_ -> fact) where
  fact n
    | n <= 1 = 1
    | n > 1 = n * fact (n - 1)

-- | implementation of the map function via fix function
mapFix :: (a -> b) -> [a] -> [b]
mapFix = fix (\_ -> mv) where
  mv _ [] = []
  mv f (x : xs) = f x : mv f xs


type Nat a = (a -> a) -> a -> a

-- | zero function
zero :: Nat a
zero _ x = x

-- | implementation of the calculation of the following number
succChurch :: Nat a -> Nat a
succChurch n = (\f -> f . n f)

-- | implementing addition and multiplication of numbers
churchPlus, churchMult :: Nat a -> Nat a -> Nat a
churchPlus a b = (\f -> (a f) . (b f))
churchMult a b = a . (churchPlus b zero)

-- | implementation transformation of Church numerals to integer
churchToInt :: Nat Integer -> Integer
churchToInt f = f (+1) 0


-- task 7

firstTask :: Bool
firstTask = (((null :: Foldable t => t a -> Bool)
  . (head :: [a] -> a) :: Foldable t => [t a] -> Bool) $
  (((map :: (a -> b) -> [a] -> [b])
  (((uncurry :: (a -> b -> c) -> (a, b) -> c)
  (id :: a -> a)) :: (b -> c, b) -> c) :: [(a -> b, a)] -> [b])
  ([(((((++) :: [a] -> [a] -> [a]) ("Dorian " :: [Char]) :: [Char] -> [Char]),
  (" Grey" :: [Char])) :: ([Char] -> [Char], [Char]))] :: [([Char] -> [Char], [Char])]) :: [[Char]]) :: Bool)

secondTask :: [(Integer, Integer)]
secondTask = (((\(x :: [Either a b]) -> (zip ((lefts :: [Either a b] -> [a])
  (x :: [Either a b]) :: [a]) ((rights :: [Either a b] -> [b]) (x:: [Either a b]) :: [b])
  :: [(a, b)])) :: [Either a b] -> [(a, b)])
  ([(Left (((1 :: Integer) + (2 :: Integer)) :: Integer) :: Either Integer a),
  (Right (((2 :: Integer) ^ (6 :: Integer)) :: Integer) :: Either a Integer)]
  :: [Either Integer Integer]) :: [(Integer, Integer)])

thirdTask :: Integer -> Bool
thirdTask = let impl = \(x :: Bool) (y :: Bool) -> (((not :: Bool -> Bool) (x :: Bool) :: Bool) || (y :: Bool) :: Bool) in
  let isMod2 = ((\(x :: Integer) -> (x :: Integer) `mod` (2 :: Integer) == (0 :: Integer)) :: Integer -> Bool) in
  let isMod4 = ((\(x :: Integer) -> (x :: Integer) `mod` (4 :: Integer) == (0 :: Integer)) :: Integer -> Bool) in
    (\(x :: Integer) -> ((isMod4 :: Integer -> Bool) (x :: Integer)) `impl`
    ((isMod2 :: Integer -> Bool) (x :: Integer))) :: Integer -> Bool
