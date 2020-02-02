{
module Syntax where
import Lex
}

%name parse
%tokentype { Token }
%error { parseError }



%token
  Dot {TokenDot}
  Slesh  {TokenSlesh}
  LEFT {TokenLeftBracket}
  RIGHT {TokenRightBracket}
  VARIABLE {TokenVariable $$}
%%


Expression:
  Application {$1}
  | Application Slesh VARIABLE Dot Expression {ApplLambda $1 (Var $3) $5}
  | Slesh VARIABLE Dot Expression {Lambda (Var $2) $4}

Application:
  Application Atom {Appl $1 $2}
  | Atom {$1}

Atom:
  LEFT Expression RIGHT {$2}
  | VARIABLE {Var $1}


{
parseError _ = error "Parse error"

data Expression = ApplLambda Expression Expression Expression
  | Lambda Expression Expression
  | Appl Expression Expression
  | Var String

instance Show Expression where
  show (Var str) = str
  show (ApplLambda ex1 ex2 ex3) = "(" ++ (show ex1) ++ " (\\" ++ (show ex2) ++ "." ++ (show ex3) ++ "))"
  show (Lambda ex1 ex2) = "(\\" ++ (show ex1) ++ "." ++ (show ex2) ++ ")"
  show (Appl ex1 ex2) = "(" ++ (show ex1) ++ " " ++ (show ex2) ++ ")"

}
