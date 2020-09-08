{-# LANGUAGE BangPatterns #-}

module ConcurrentHashTable (ConcurrentHashTable(..), newCHT, getCHT, putCHT, sizeCHT) where


import           Control.Concurrent.STM (STM, TArray, TMVar, TVar, atomically,
                                         newTMVar, newTVar, readTMVar,
                                         readTVar, writeTVar)
import           Control.Monad          (forM_)
import           Data.Array.MArray      (getBounds, getElems, newArray,
                                         readArray, writeArray)
import           Data.Hashable          (Hashable (..))


data ConcurrentHashTable k v = ConcurrentHashTable
    { size    :: TVar Int
    , content :: TMVar (TArray Int (Maybe (k, v)))
    }


newCHT  :: IO (ConcurrentHashTable k v)
newCHT = atomically $ do
     size <- newTVar 0
     array <- newArray (0, 128) Nothing
     content <- newTMVar array
     return $ ConcurrentHashTable size content


nextInd :: Int -> Int -> Int -> Int
nextInd !i !l !r = case i + 1 == r of
     True  -> l
     False -> i + 1


findElIndex :: (Eq k) => k -> Int -> Int -> Int -> TArray Int (Maybe(k, v)) -> STM(Int)
findElIndex key !i !l !r array = do
     tmp <- readArray array i
     case tmp of
          Nothing -> return i
          Just (k, v) -> case key == k of
               True  -> return i
               False -> findElIndex key (nextInd i l r) l r array


getCHT :: (Hashable k, Eq k) => k -> ConcurrentHashTable k v -> IO (Maybe v)
getCHT key table = atomically $ do
     array <- readTMVar (content table)
     (!left, !right) <- getBounds array
     let !ind = hash key `mod` right
     findEl key ind left right array where
          findEl :: (Eq k) => k -> Int -> Int -> Int -> TArray Int (Maybe(k, v)) -> STM(Maybe v)
          findEl key !i !l !r array = findElIndex key i l r array >>= readArray array >>= \x ->
               case x of
                    Nothing    -> return Nothing
                    Just (k,v) -> return $ Just v


putCHT :: (Hashable k, Eq k) => k -> v -> ConcurrentHashTable k v -> IO ()
putCHT key value table = atomically $ do
     array <- readTMVar (content table)
     tmpSize <- readTVar (size table)
     (left, right) <- getBounds array
     array <- expandArray left right (tmpSize + 1) array

     (left, right) <- getBounds array
     updateArray left right array (Just (key, value))
     writeTVar (size table) (tmpSize + 1)


expandArray :: (Hashable k, Eq k) => Int -> Int -> Int -> TArray Int (Maybe(k, v)) -> STM(TArray Int (Maybe(k, v)))
expandArray !left !right !size oldArray = case (fromIntegral size >= 0.75 * fromIntegral (right - left)) of
     False -> return oldArray
     True -> do
          array <- newArray (left, 2 * right - left) Nothing
          updateHash left (2 * right - left) oldArray array
          return array
     where
          updateHash :: (Hashable k, Eq k) => Int -> Int -> TArray Int (Maybe(k, v)) -> TArray Int (Maybe(k, v)) -> STM ()
          updateHash !left !right old new = do
               elems <- getElems old
               forM_ elems (updateArray left right new)
               return ()

updateArray :: (Hashable k, Eq k) => Int -> Int -> TArray Int (Maybe(k, v)) -> Maybe(k, v) -> STM ()
updateArray _ _ _ Nothing = return ()
updateArray !l !r array pair@(Just (key, value)) = do
     let !ind = hash key `mod` r
     ind <- findElIndex key ind l r array
     writeArray array ind pair


sizeCHT :: ConcurrentHashTable k v -> IO Int
sizeCHT table = atomically $ readTVar $ size table
