{
module Syntax where
import Lex
}

%name parse
%tokentype { Token }
%error { parseError }



%token
  NOT {TokenNot}
  OR  {TokenOr}
  AND {TokenAnd}
  LEFT {TokenLeftBracket}
  RIGHT {TokenRightBracket}
  IMPLICATION {TokenImplication}
  VARIABLE {TokenVariable $$}
%%


Expression:
  Disjunction {$1}
  | Disjunction IMPLICATION Expression {Calc Impl $1 $3}

Disjunction:
  Conjunction {$1}
  | Disjunction OR Conjunction {Calc Or $1 $3}

Conjunction:
  Negative {$1}
  | Conjunction AND Negative {Calc And $1 $3}

Negative:
  NOT Negative {Not $2}
  | VARIABLE {Var $1}
  | LEFT Expression RIGHT {$2}


{
parseError _ = error "Parse error"

data BiFunction = Or | And | Impl

instance Show BiFunction where
  show Or = "|"
  show And = "&"
  show Impl = "->"

data Expression = Var String | Not Expression | Calc BiFunction Expression Expression

instance Show Expression where
  show (Var str) = str
  show (Not expr) = "(!" ++ (show expr) ++ ")"
  show (Calc f a b) = "(" ++ show f ++ "," ++ show a ++ "," ++ show b ++ ")"
}
