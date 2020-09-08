module MonadsSpec (spec) where

import Monads

import Test.Hspec (SpecWith, describe, it, shouldBe)

spec :: SpecWith ()
spec = do
  describe "test expressions" $ do
    it "test eval" $ do
      eval (Div (Const 1) (Const 0)) `shouldBe` Left DivisionByZero
      eval (Pow (Const 1) (Const (-8))) `shouldBe` Left NegativePow
      eval (Add (Const 1) (Const 2)) `shouldBe` Right 3
      eval (Sub (Const 1) (Const 2)) `shouldBe` Right (-1)
      eval (Mul (Const 8) (Const 2)) `shouldBe` Right (16)
      eval (Add (Const 2) (Mul (Const 2) (Const 2))) `shouldBe` Right 6

  describe "test calculate average" $ do
    it "test moving" $ do
      moving 4 [1, 5, 3, 8, 7, 9, 6] `shouldBe` [1.0, 3.0, 3.0, 4.25, 5.75, 6.75, 7.5]
      moving 2 [1, 5, 3, 8, 7, 9, 6] `shouldBe` [1.0, 3.0, 4.0, 5.5, 7.5, 8.0, 7.5]
      moving 100 [] `shouldBe` []
      moving 2 [1] `shouldBe` [1.0]
      moving 2 [1, 3] `shouldBe` [1.0, 2.0]

