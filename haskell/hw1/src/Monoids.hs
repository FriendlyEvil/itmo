module Monoids (maybeConcat, NonEmpty(..), ThisOrThat(..)) where

import Data.Semigroup

-- | returns concatenation of 'Just' elements of the list
maybeConcat :: [Maybe [a]] -> [a]
maybeConcat = foldMap mapped where
  mapped :: Maybe [a] -> [a]
  mapped Nothing  = []
  mapped (Just l) = l


data NonEmpty a = a :| [a]

instance Semigroup (NonEmpty a) where
  (<>) (x :| xs) (y :| ys) = x :| (xs ++ (y : ys))


data ThisOrThat a b = This a | That b | Both a b

instance Semigroup (ThisOrThat a b) where
  (<>) first@(This _) (This _) = first
  (<>) first@(That _) (That _) = first
  (<>) first@(Both _ _) _      = first
  (<>) (This a) (That b)       = Both a b
  (<>) (That b) (This a)       = Both a b
  (<>) (This a) (Both _ b)     = Both a b
  (<>) (That b) (Both a _)     = Both a b

