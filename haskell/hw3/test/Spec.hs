module Main
  ( main
  ) where


import Test.Hspec (hspec)

import SpecFileSystem (spec)
import SpecConcurrentHashTable (spec)

main :: IO ()
main = hspec $ do
    SpecFileSystem.spec
    SpecConcurrentHashTable.spec
