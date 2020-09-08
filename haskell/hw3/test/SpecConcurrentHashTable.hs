module SpecConcurrentHashTable (spec) where


import           ConcurrentHashTable      (ConcurrentHashTable (..), getCHT,
                                           newCHT, putCHT, sizeCHT)
import           Control.Concurrent.Async (forConcurrently)
import           Control.Monad            (forM_)
import           Test.Hspec               (Expectation, SpecWith, describe, it,
                                           shouldBe)

putValues :: ConcurrentHashTable String String -> (Int -> Int) -> Int -> IO ()
putValues table f size = forM_ (fmap f [1..size]) $ \i -> putCHT ("key" ++ show (i * 41 + 1337)) ("value" ++ show i) table

mutlyThreadPut :: ConcurrentHashTable String String -> Int -> Int -> IO ()
mutlyThreadPut table th size = forConcurrently [1..th] (\num -> putValues table (\i -> num * size + i) size) >> return ()

multyPut :: Int -> Int -> IO (ConcurrentHashTable String String)
multyPut th size = do
    table <- newCHT
    mutlyThreadPut table th (size `div` th)
    return table

testSize :: IO (ConcurrentHashTable String String) -> Int -> IO ()
testSize f s = do
    table <- f
    size <- sizeCHT table
    size `shouldBe` s

test :: Int -> IO ()
test th = testSize (multyPut th 10000) 10000

testPut :: IO ()
testPut = do
    t <- multyPut 2 1000
    mutlyThreadPut t 2 1000
    testSize (return t) 3000

spec :: SpecWith ()
spec = do
    describe "test 10^5 put" $ do
        it "single thread" $ do
            test 1
        it "two threads" $ do
            test 2
        it "ten threads" $ do
            test 10
        it "one hundred threads" $ do
            test 100
    describe "multy put" $ do
        it "put twice" $ do
            testPut

