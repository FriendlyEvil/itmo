{-# LANGUAGE DeriveFunctor #-}

module Grid(
    ListZipper(..),
    Grid(..),
    listLeft,
    listRight,
    listWrite,
    toList,
    genericMove,
    gMove,
    up,
    down,
    left,
    right,
    gridRead,
    gridWrite
    ) where

import           Control.Comonad (Comonad (..))

data ListZipper a = LZ [a] a [a]
    deriving Functor

listLeft, listRight :: ListZipper a -> ListZipper a
listLeft  (LZ (a:as) x bs) = LZ as a (x:bs)
listLeft _                 = error "listLeft"

listRight (LZ as x (b:bs)) = LZ (x:as) b bs
listRight _                = error "listRight"

listWrite :: a -> ListZipper a -> ListZipper a
listWrite x (LZ ls _ rs) = LZ ls x rs

toList :: ListZipper a -> Int -> [a]
toList (LZ ls x rs) n = reverse (take n ls) ++ [x] ++ take n rs

iterateTail :: (a -> a) -> a -> [a]
iterateTail f = tail . iterate f

genericMove :: (z a -> z a) -> (z a -> z a) -> z a -> ListZipper (z a)
genericMove f g e = LZ (iterateTail f e) e (iterateTail g e)

gMove :: (a -> a) -> (a -> a) -> a -> ListZipper (a)
gMove f g e = LZ (iterateTail f e) e (iterateTail g e)

instance Comonad ListZipper where
    extract (LZ _ x _) = x
    duplicate = genericMove listLeft listRight

newtype Grid a = Grid { unGrid :: ListZipper (ListZipper a) }
    deriving Functor

up, down :: Grid a -> Grid a
up   (Grid g) = Grid (listLeft  g)
down (Grid g) = Grid (listRight g)

left, right :: Grid a -> Grid a
left  (Grid g) = Grid (fmap listLeft  g)
right (Grid g) = Grid (fmap listRight g)

gridRead :: Grid a -> a
gridRead (Grid g) = extract $ extract g

gridWrite :: a -> Grid a -> Grid a
gridWrite x (Grid g) = Grid $ listWrite newLine g
  where
    oldLine = extract g
    newLine = listWrite x oldLine

horizontal, vertical :: Grid a -> ListZipper (Grid a)
horizontal = genericMove left right
vertical   = genericMove up   down

instance Comonad Grid where
    extract = gridRead
    duplicate = Grid . fmap horizontal . vertical
