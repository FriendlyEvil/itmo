module WeekSpec (spec) where

import Week

import Test.Hspec (Expectation, SpecWith, describe, it, shouldBe)

skipDays :: Day -> Int -> Day
skipDays day 0    = day
skipDays day skip = skipDays (nextDay day) (skip - 1)

testAfterDays :: Day -> Int -> Expectation
testAfterDays day skip = afterDays day skip `shouldBe` skipDays day skip

testDaysToParty :: Day -> Expectation
testDaysToParty day = skipDays day (daysToParty day) `shouldBe` Fri

spec :: SpecWith ()
spec = do
  describe "week test" $ do
    it "test next" $ do
      nextDay Mon `shouldBe` Tue
      nextDay Tue `shouldBe` Wed
      nextDay Wed `shouldBe` Thu
      nextDay Thu `shouldBe` Fri
      nextDay Fri `shouldBe` Sat
      nextDay Sat `shouldBe` Sun
      nextDay Sun `shouldBe` Mon

    it "test after day" $ do
      testAfterDays Mon 0
      testAfterDays Mon 1
      testAfterDays Mon 2
      testAfterDays Mon 3
      testAfterDays Mon 4
      testAfterDays Mon 5
      testAfterDays Mon 6
      testAfterDays Mon 7
      testAfterDays Mon 8
      testAfterDays Sun 15

    it "test days to party" $ do
      testDaysToParty Mon
      testDaysToParty Tue
      testDaysToParty Wed
      testDaysToParty Thu
      testDaysToParty Fri
      testDaysToParty Sat
      testDaysToParty Sun
