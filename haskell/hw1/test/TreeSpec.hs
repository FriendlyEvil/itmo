module TreeSpec (spec) where

import Data.List (sort)
import Data.List.NonEmpty (NonEmpty (..))
import qualified Data.Set as Set
import Tree

import Test.Hspec (Expectation, SpecWith, describe, it, shouldBe)
import Test.QuickCheck (property)


testInsert :: [Int] -> Int -> Expectation
testInsert list val = toList (insert (fromList list) val) `shouldBe` sort (val : list)

testRemove :: [Int] -> Int -> Expectation
testRemove list val = toList (remove (fromList list) val) `shouldBe` sort (removeFisrt list val) where
  removeFisrt :: [Int] -> Int -> [Int]
  removeFisrt [] _ = []
  removeFisrt (x : xs) el
    | x == el = xs
    | otherwise = x : removeFisrt xs el

testSize :: [Int] -> Expectation
testSize list = size (fromList list) `shouldBe` (Set.size $ Set.fromList list)

propertyConvert :: [Int] -> Bool
propertyConvert list = (toList $ fromList list) == sort list

propertySplitJoinString :: Char -> [Char] -> Bool
propertySplitJoinString el list = joinWith el (splitOn el list) == list

spec :: SpecWith ()
spec = do
  describe "tree tests" $ do
    it "tree property test" $ do
      property propertyConvert

    it "tree test add" $ do
      testInsert [] 1
      testInsert [] (-5)
      testInsert [1, 2] 3
      testInsert [-5, 5] 1
      testInsert [1, 8, 19, 20] 100
      testInsert [1, 8, 19, 20] (-1)
      testInsert [1, 8, 19, 20] 5
      testInsert [1, 8, 19, 20] 10
      testInsert [1, 8, 18, 20] 19
      testInsert [1, 8, 18, 20] 1
      testInsert [1, 1, 1, 1] 1
      testInsert [1..20] 20
      testInsert [1..20] 15

    it "tree test remove" $ do
      testRemove [] 1
      testRemove [1, 2, 3] 1
      testRemove [1, 8, 19, 20] 100
      testRemove [1, 8, 19, 20] 1
      testRemove [1, 8, 19, 20] 8
      testRemove [1, 8, 19, 20] 19
      testRemove [1, 8, 19, 20] 20
      testRemove [1..20] (-1)
      testRemove [1..20] 20
      testRemove [1..20] 1
      testRemove [1..20] 5
      testRemove [1..20] 15

    it "tree test size" $ do
      testSize []
      testSize [1, 2, 4, 6]
      testSize [1..20]
      testSize [1, 1, 1, 1]
      testSize [1, 5, 6, 1, 8]
      testSize [5, 1, -1, 8, 1]

  describe "test split on task" $ do
    it "test split on string" $ do
      splitOn '/' "path/to/file" `shouldBe` ("path" :| ["to", "file"])
      splitOn '/' "" `shouldBe` ("" :| [])
      splitOn '/' "/" `shouldBe` ("" :| [""])
      splitOn '/' "/a/b/c/" `shouldBe` ("" :| ["a", "b", "c", ""])
      splitOn '/' "simple test" `shouldBe` ("simple test" :| [])

    it "test joint with string" $ do
      joinWith '/' ("path" :| ["to", "file"]) `shouldBe` "path/to/file"
      joinWith '/' ("" :| []) `shouldBe` ""
      joinWith '/' ("" :| [""]) `shouldBe` "/"
      joinWith '/' ("" :| ["a", "b", "c", ""]) `shouldBe` "/a/b/c/"
      joinWith '/' ("simple test" :| []) `shouldBe` "simple test"

    it "property test split join" $ do
      property propertySplitJoinString

