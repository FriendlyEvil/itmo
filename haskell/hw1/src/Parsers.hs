module Parsers (ok, eof, satisfy, element) where

import Control.Applicative (Alternative (..))

data Parser s a = Parser { runParser :: [s] -> Maybe (a, [s]) }

instance Functor (Parser s) where
  fmap f (Parser parser) = Parser (fmap (first f) . parser) where
    first f (a, b) = (f a, b)

instance Applicative (Parser s) where
  pure a = Parser $ \str -> Just (a, str)
  Parser pf <*> Parser pa = Parser $ \str -> case pf str of
    Nothing -> Nothing
    Just (f, t) -> case pa t of
      Nothing -> Nothing
      Just (a, r) -> Just (f a, r)

instance Monad (Parser s) where
  (>>=) (Parser parser) f = Parser $ \str -> do
    (res, remainder) <- (parser str)
    let (Parser p) = f res
    p remainder

instance Alternative (Parser s) where
  empty = Parser $ \_ -> Nothing
  (<|>) (Parser pa) (Parser pb) = Parser $ \str -> pa str <|> pb str

eof, ok :: (Parser s) ()
ok = return ()
eof = Parser isEnd where
  isEnd [] = Just((), [])
  isEnd _ = Nothing


satisfy :: (s -> Bool) -> Parser s s
satisfy expr = Parser isPred where
  isPred (x : xs)
    | expr x = Just(x, xs)
  isPred _ = Nothing


element :: (Eq s) => s -> Parser s s
element el = satisfy ((==) el)