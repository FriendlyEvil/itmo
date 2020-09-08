module NatSpec (spec) where

import Nat

import Test.Hspec (Expectation, SpecWith, describe, it, shouldBe)

testOp :: (Nat -> Nat -> Nat) -> Nat -> Nat -> Nat -> Expectation
testOp f a b res = f a b `shouldBe` res

testAdd, testSub, testMul, testDiv, testMod :: Nat -> Nat -> Nat -> Expectation
testAdd = testOp add
testSub = testOp sub
testMul = testOp mul
testDiv = testOp Nat.div
testMod = testOp Nat.mod

spec :: SpecWith ()
spec = do
  describe "test nat operations" $ do
    it "test add" $ do
      testAdd zero zero zero
      testAdd zero five five
      testAdd five zero five
      testAdd one one two
      testAdd three two five

    it "test sub" $ do
      testSub zero zero zero
      testSub two five zero
      testSub one one zero
      testSub two one one
      testSub five three two

    it "test mul" $ do
      testMul zero zero zero
      testMul zero two zero
      testMul five zero zero
      testMul two two four
      testMul one five five
      testMul five one five

    it "test div" $ do
      testDiv one one one
      testDiv two one two
      testDiv one two zero
      testDiv four two two
      testDiv two four zero
      testDiv five two two
      testDiv zero two zero

    it "test mod" $ do
      testMod zero one zero
      testMod one one zero
      testMod two five two
      testMod five two one
      testMod five three two

  describe "test nat convert functions" $ do
    it "toInt" $ do
      toInt zero `shouldBe` 0
      toInt one `shouldBe` 1
      toInt two `shouldBe` 2
      toInt three `shouldBe` 3
      toInt four `shouldBe` 4
      toInt five `shouldBe` 5

    it "fromInt" $ do
      fromInt 0 `shouldBe` zero
      fromInt 1 `shouldBe` one
      fromInt 2 `shouldBe` two
      fromInt 3 `shouldBe` three
      fromInt 4 `shouldBe` four
      fromInt 5 `shouldBe` five

  where
      zero = Z
      one = S Z
      two = S $ S Z
      three = S $ S $ S Z
      four = S $ S $ S $ S Z
      five = S $ S $ S $ S $ S Z


