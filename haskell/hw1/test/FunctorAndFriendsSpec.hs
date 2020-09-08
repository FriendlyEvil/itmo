module FunctorAndFriendsSpec (spec) where

import FunctorAndFriends (stringSum)

import Test.Hspec (Expectation, SpecWith, describe, it, shouldBe)

testStringSum :: String -> Maybe Int -> Expectation
testStringSum str expected = stringSum str `shouldBe` expected

spec :: SpecWith ()
spec = do
  describe "test functor friends" $ do
    it "test string sum" $ do
      testStringSum "1 1" (Just 2)
      testStringSum "1  " (Just 1)
      testStringSum "     1" (Just 1)
      testStringSum "   1    " (Just 1)
      testStringSum "1 2 3 4" (Just 10)
      testStringSum "1 2 3 4" (Just 10)
      testStringSum " 12   34 " (Just 46)
      testStringSum "-1" (Just (-1))
      testStringSum "-15 16" (Just 1)
      testStringSum "16-16" (Nothing)

