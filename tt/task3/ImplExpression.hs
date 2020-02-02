module ImplExpression where

import Syntax

data ImplExpression = IExpression {
  left::ImplExpression,
  right::ImplExpression
} | ImplExpr Expression

createImpl::ImplExpression->ImplExpression->ImplExpression
createImpl a b = IExpression {left = a, right = b}

instance Show ImplExpression where
  show (ImplExpr a) = show a
  show (a) = show (left a) ++ " -> " ++ show (right a)
