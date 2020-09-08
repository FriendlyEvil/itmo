module Main
  ( main
  ) where

import Test.Hspec (hspec)

import WeekSpec
import NatSpec
import TreeSpec
import MonoidsSpec
import FunctorAndFriendsSpec
import MonadsSpec

main :: IO ()
main =
  hspec $ do
    NatSpec.spec
    WeekSpec.spec
    TreeSpec.spec
    MonoidsSpec.spec
    FunctorAndFriendsSpec.spec
    MonadsSpec.spec