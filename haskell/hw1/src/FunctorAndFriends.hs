module FunctorAndFriends (stringSum, Tree(..), NonEmpty(..)) where

import Text.Read

-- | divides the string by spaces and tries to calculate the sum of the resulting numbers
stringSum :: String -> Maybe Int
stringSum = fmap sum . traverse readMaybe . words


data Tree a = Branch (Tree a) (Tree a) | Leaf a

instance Functor Tree where
  fmap f (Leaf a)            = Leaf (f a)
  fmap f (Branch left right) = Branch (fmap f left) (fmap f right)

instance Applicative Tree where
  pure a = Leaf a
  (<*>) (Leaf f) tree            = f <$> tree
  (<*>) (Branch left right) tree = Branch (left <*> tree) (right <*> tree)

instance Foldable Tree where
  foldr f z (Leaf a)            = f a z
  foldr f z (Branch left right) = foldr f (foldr f z right) left

instance Traversable Tree where
  traverse f (Leaf value)        = Leaf <$> f value
  traverse f (Branch left right) = Branch <$> traverse f left <*> traverse f right


data NonEmpty a = a :| [a]

instance Functor NonEmpty where
  fmap f (x :| xs) = f x :| fmap f xs

instance Applicative NonEmpty where
  pure a = a :| []
  (<*>) (f :| fs) (x :| xs) = f x :| (fmap f xs ++ (fs <*> x : xs))

instance Foldable NonEmpty where
  foldr f z (x :| xs) = f x (foldr f z xs)

instance Traversable NonEmpty where
  traverse f (x :| xs) = (:|) <$> f x <*> traverse f xs
