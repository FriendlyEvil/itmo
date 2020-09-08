module Week (Day(..), nextDay, afterDays, isWeekend, daysToParty) where

data Day = Mon | Tue | Wed | Thu | Fri | Sat | Sun deriving (Show)

instance Eq Day where
  (==) Mon Mon = True
  (==) Tue Tue = True
  (==) Wed Wed = True
  (==) Thu Thu = True
  (==) Fri Fri = True
  (==) Sat Sat = True
  (==) Sun Sun = True
  (==) _ _     = False

instance Enum Day where
  fromEnum Mon = 0
  fromEnum Tue = 1
  fromEnum Wed = 2
  fromEnum Thu = 3
  fromEnum Fri = 4
  fromEnum Sat = 5
  fromEnum Sun = 6
  toEnum 0 = Mon
  toEnum 1 = Tue
  toEnum 2 = Wed
  toEnum 3 = Thu
  toEnum 4 = Fri
  toEnum 5 = Sat
  toEnum 6 = Sun
  toEnum d = toEnum (d `mod` 7)

-- | return next day of the week
nextDay :: Day -> Day
nextDay = toEnum . (+1) . fromEnum

-- | return day of the week that will occur in 'count' days
afterDays :: Day -> Int -> Day
afterDays day count = toEnum . (+count) . fromEnum $ day

-- | checks that the day is a weekend
isWeekend :: Day -> Bool
isWeekend Sat = True
isWeekend Sun = True
isWeekend _   = False

-- | returns the number of days before Friday
daysToParty :: Day -> Int
daysToParty day = (fromEnum Fri - fromEnum day + 7 ) `mod` 7
