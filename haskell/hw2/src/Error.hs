module Error (Error(..)) where

newtype Error = Error String

instance Show Error where
    show (Error str) = "Error: " ++ str
