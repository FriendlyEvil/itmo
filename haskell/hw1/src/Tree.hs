{-# LANGUAGE InstanceSigs        #-}
{-# LANGUAGE ScopedTypeVariables #-}

module Tree (Tree(..), insert, isEmpty, size, find, fromList, remove, toList, splitOn, joinWith) where

import Data.List.NonEmpty (NonEmpty (..))
import qualified Data.List.NonEmpty as NonEmpty (reverse)
import Prelude hiding (fst, minimum)

data Tree a = Leaf | Node (NonEmpty a) (Tree a) (Tree a) deriving Show

instance (Ord a) => Eq (Tree a) where
  (==) Leaf Leaf = True
  (==) (Node list1 left1 right1) (Node list2 left2 right2) = list1 == list2 && left1 == left2 && right1 == right2
  (==) _ _ = False

-- | checks that tree is empty
isEmpty :: (Ord a) => Tree a -> Bool
isEmpty Leaf = True
isEmpty _    = False

-- | returns size of the tree
size :: (Ord a) => Tree a -> Int
size Leaf            = 0
size (Node _ a b) = 1 + size a + size b

-- | returns node with element to search
find :: (Ord a) => Tree a -> a -> Tree a
find Leaf _ = Leaf
find node@(Node (val:|_) left right) key
  | val == key = node
  | val > key = find left key
  | otherwise  = find right key

-- | insert element into tree
insert :: (Ord a) => Tree a -> a -> Tree a
insert Leaf key = Node (key :| []) Leaf Leaf
insert (Node list@(val :| tailList) left right) key
  | val == key = Node (key :| (val : tailList)) left right
  | val > key = Node list (insert left key) right
  | otherwise = Node list left (insert right key)


-- | builds a tree from list
fromList :: (Ord a) => [a] -> Tree a
fromList list = fromListHelper list Leaf where
  fromListHelper :: (Ord a) => [a] -> Tree a -> Tree a
  fromListHelper [] res     = res
  fromListHelper (s:xs) res = fromListHelper xs (insert res s)

-- | convert tree to sorted
toList :: (Ord a) => Tree a -> [a]
toList Leaf                                = []
toList (Node (fst :| tailList) left right) = toList left ++ (fst : tailList) ++ toList right

-- | remove element from tree
remove :: (Ord a) => Tree a -> a -> Tree a
remove Leaf _ = Leaf
remove (Node list@(val :| _) left right) key
  | val < key = Node list left (remove right key)
  | val > key = Node list (remove left key) right
  | otherwise = update left right where
    update :: (Ord a) => Tree a -> Tree a -> Tree a
    update a Leaf = a
    update Leaf b = b
    update a b = Node rightMin a newRight where
      rightMin@(fst :| _) = minimum b
      newRight = remove b fst

      minimum :: (Ord a) => Tree a -> NonEmpty a
      minimum Leaf            = error "Empty tree"
      minimum (Node v Leaf _) = v
      minimum (Node _ l _)    = minimum l


instance Foldable Tree where
  foldMap :: Monoid m => (a -> m) -> Tree a -> m
  foldMap _ Leaf = mempty
  foldMap f (Node list left right) = foldMap f left `mappend` foldMap f list `mappend` foldMap f right

  foldr :: (a -> b -> b) -> b -> Tree a -> b
  foldr _ z Leaf                   = z
  foldr f z (Node list left right) = foldr f (foldr f (foldr f z right) list) left


-- | splits the list into sublists by element
splitOn :: forall a. (Eq a) => a -> [a] -> NonEmpty [a]
splitOn el list = NonEmpty.reverse $ nonEmptyReverseFirst $ foldl subSplit ([] :| []) list where
  subSplit :: NonEmpty [a] -> a -> NonEmpty [a]
  subSplit (x :| xs) cur
    | el == cur = [] :| listReverseFirst (x : xs)
    | otherwise = (cur : x) :| xs

  nonEmptyReverseFirst :: NonEmpty [a] -> NonEmpty [a]
  nonEmptyReverseFirst (x :| xs) = reverse x :| xs

  listReverseFirst :: [[a]] -> [[a]]
  listReverseFirst []       = []
  listReverseFirst (x : xs) = reverse x : xs

-- | combines elements of a non-empty list into one with separator
joinWith :: forall a. a -> NonEmpty [a] -> [a]
joinWith el (x :| xs) = x  ++ foldr subJoin [] xs where
  subJoin :: [a] -> [a] -> [a]
  subJoin f s = el : (f ++ s)
