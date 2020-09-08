module MonoidsSpec (spec) where

import Monoids (maybeConcat)

import Test.Hspec (Expectation, SpecWith, describe, it, shouldBe)

testMeybeConcatInt :: [Maybe [Int]] -> [Int] -> Expectation
testMeybeConcatInt list expected = maybeConcat list `shouldBe` expected

spec :: SpecWith ()
spec = do
  describe "test monoid tasks" $ do
    it "test maybe concat" $ do
      testMeybeConcatInt [Just [1, 2, 3], Nothing, Just [4, 5]] [1, 2, 3, 4, 5]
      testMeybeConcatInt [] []
      testMeybeConcatInt [Nothing] []
      testMeybeConcatInt [Nothing, Nothing, Nothing] []

