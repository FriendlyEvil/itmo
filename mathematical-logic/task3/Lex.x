{
module Lex where
}

%wrapper "basic"

$digit = 0-9
$alpha = [A-Z]

tokens :-
  $white+ ;
  \(      {\s -> TokenLeftBracket}
  \)      {\s -> TokenRightBracket}
  \!       {\s -> TokenNot}
  \|      {\s -> TokenOr}
  \&      {\s -> TokenAnd}
  "->"      {\s -> TokenImplication}
  $alpha [$alpha $digit ’ \']* {\s -> TokenVariable s}

{
data Token = TokenLeftBracket|TokenRightBracket|TokenNot|TokenOr|TokenAnd|TokenImplication|TokenVariable String deriving (Eq, Show)
}
