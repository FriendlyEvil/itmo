{
module Lex where
}

%wrapper "basic"

$digit = 0-9
$alpha = [a-z]

tokens :-
  $white+ ;
  \(       {\s -> TokenLeftBracket}
  \)       {\s -> TokenRightBracket}
  \.       {\s -> TokenDot}
  \\       {\s -> TokenSlesh}
  $alpha [$alpha $digit ']* {\s -> TokenVariable s}

{
data Token = TokenLeftBracket|TokenRightBracket|TokenDot|TokenSlesh|TokenVariable String deriving (Eq, Show)
}
