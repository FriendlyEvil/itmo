module SpecFileSystem (spec) where

import           FileSystem
import           Lens.Micro ((^..), (^?))
import           Test.Hspec (Expectation, SpecWith, describe, it, shouldBe)

spec :: SpecWith ()
spec = do
    let fs = getDir
    describe "test file system commands" $ do
        it "test file" $ do
            fs ^? file "C" `shouldBe` Just "C"
            fs ^? file "A" `shouldBe` Nothing
        it "test cd" $ do
            fs ^?  cd "A" . cd "B" . file "C" `shouldBe` Just "C"
            fs ^?  cd "B" . file "C" `shouldBe` Nothing
        it "test ls" $ do
            fs ^.. cd "A" . cd "B" . ls `shouldBe` ["C"]
            fs ^.. cd "B" . ls `shouldBe` ["M", "A"]
        it "change functions" $ do
            changeExtention "a" fs ^.. files.name `shouldBe` ["CADS.a","C.a"]
            removeEmptyDir "A" fs ^.. ls `shouldBe` ["A","D","CADS.m","C","B"]
            removeEmptyDir "D" fs ^.. ls `shouldBe` ["A","CADS.m","C","B"]
            getRecusiveContent fs `shouldBe` ["A","D","CADS.m","C","B","B","C","M","A"]

getDir :: FS
getDir = Dir "root" [
        Dir "A" [
            Dir "B" [
                File "C"
            ]
        ],
        Dir "D" [],
        File "CADS.m",
        File "C",
        Dir "B" [
            File "M",
            Dir "A" []
        ]
    ]
