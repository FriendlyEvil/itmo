module Nat (Nat(..), add, mul, sub, Nat.div, Nat.mod, Nat.even, toInt, fromInt) where


data Nat = Z | S Nat deriving Show

instance Eq Nat where
  (==) Z Z         = True
  (==) (S a) (S b) = a == b
  (==) _ _         = False

instance Ord Nat where
  compare Z Z         = EQ
  compare Z _         = LT
  compare _ Z         = GT
  compare (S a) (S b) = compare a b

-- | returns the sum of two numbers
add :: Nat -> Nat -> Nat
add Z b     = b
add (S a) b = a `add` (S b)

-- | returns the product of two numbers
mul :: Nat -> Nat -> Nat
mul a b = mulHelper a b b where
  mulHelper :: Nat -> Nat -> Nat -> Nat
  mulHelper Z _ _                = Z
  mulHelper _ Z _                = Z
  mulHelper (S Z) _ res          = res
  mulHelper (S first) second res = mulHelper first second (second `add` res)

-- | subtracts the second number from the first
sub :: Nat -> Nat -> Nat
sub Z _         = Z
sub a Z         = a
sub (S a) (S b) = a `sub` b

-- | converts 'Nat' number to 'Int'
toInt :: Nat -> Int
toInt Z     = 0
toInt (S a) = 1 + toInt a

-- | converts 'Int' to 'Nat' number
fromInt :: Int -> Nat
fromInt a
  | a > 0 = S $ fromInt (a - 1)
  | otherwise = Z

-- | check that number is even
even :: Nat -> Bool
even Z     = True
even (S Z) = False
even (S a) = Nat.even a

-- | returns the result of dividing the first number by the second
div :: Nat -> Nat -> Nat
div _ Z = error "Division by 0"
div a b = divHelper a b Z where
  divHelper :: Nat -> Nat -> Nat -> Nat
  divHelper f s res
    | f >= s = divHelper (f `sub` s) s (S res)
  divHelper _ _ res = res

-- | returns the result of the comparison module
mod :: Nat -> Nat -> Nat
mod _ Z = error "Division by 0"
mod a b = a `sub` ((a `Nat.div` b) `mul` b)
