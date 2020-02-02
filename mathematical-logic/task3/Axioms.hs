module Axioms where

import Syntax

getAxiom::Expression->Int
getAxiom ex = maximum [numOfAxiom1 ex, numOfAxiom2 ex,numOfAxiom3 ex,numOfAxiom4 ex,numOfAxiom5 ex,numOfAxiom6 ex,
  numOfAxiom7 ex,numOfAxiom8 ex,numOfAxiom9 ex,numOfAxiom10 ex]

aToA::Expression->Expression
aToA a = (Calc Impl a a)

aToB::Expression->Expression->Expression
aToB a b = (Calc Impl a b)

proofAToA::Expression->[Expression]
proofAToA a = let proof1 = toAxiom1 a a
                  aToa = aToA a
                  proof2 = toAxiom2 a aToa a
                  proof3 = getMP proof2
                  proof4 = toAxiom1 a aToa
                  proof5 = aToa in
                  [proof1,proof2,proof3,proof4,proof5]

toAxiom1::Expression->Expression->Expression
toAxiom1 a b = (Calc Impl a (Calc Impl b a))

toAxiom2::Expression->Expression->Expression->Expression
toAxiom2 a b c = (Calc Impl (Calc Impl a b) (Calc Impl (Calc Impl a (Calc Impl b c)) (Calc Impl a c)))

toAxiom9::Expression->Expression->Expression
toAxiom9 a b = (Calc Impl (Calc Impl a b) (Calc Impl (Calc Impl a (Not b)) (Not a)))

getMP::Expression->Expression
getMP (Calc Impl a b) = b

numOfAxiom1::Expression->Int
numOfAxiom1 (Calc Impl a (Calc Impl b a1)) =
  if a == a1 then 1
    else (-1)
numOfAxiom1 _ = (-1)
numOfAxiom2::Expression->Int
numOfAxiom2 (Calc Impl (Calc Impl a b) (Calc Impl (Calc Impl a1 (Calc Impl b1 c)) (Calc Impl a2 c1))) =
  if a == a1 && a == a2 && b == b1 && c == c1 then 2
    else (-1)
numOfAxiom2 _ = (-1)
numOfAxiom3::Expression->Int
numOfAxiom3 (Calc Impl a (Calc Impl b (Calc And a1 b1))) =
  if a == a1 && b == b1 then 3
    else (-1)
numOfAxiom3 _ = (-1)
numOfAxiom4::Expression->Int
numOfAxiom4 (Calc Impl (Calc And a b) a1) =
  if a == a1 then 4
    else (-1)
numOfAxiom4 _ = (-1)
numOfAxiom5::Expression->Int
numOfAxiom5 (Calc Impl (Calc And a b) b1) =
  if b == b1 then 5
    else (-1)
numOfAxiom5 _ = (-1)
numOfAxiom6::Expression->Int
numOfAxiom6 (Calc Impl a (Calc Or a1 b)) =
  if a == a1 then 6
    else (-1)
numOfAxiom6 _ = (-1)
numOfAxiom7::Expression->Int
numOfAxiom7 (Calc Impl b (Calc Or a b1)) =
  if b == b1 then 7
    else (-1)
numOfAxiom7 _ = (-1)
numOfAxiom8::Expression->Int
numOfAxiom8 (Calc Impl (Calc Impl a c) (Calc Impl (Calc Impl b c1) (Calc Impl (Calc Or a1 b1) c2))) =
  if a == a1 && b == b1 && c == c1 && c == c2 then 8
    else (-1)
numOfAxiom8 _ = (-1)
numOfAxiom9::Expression->Int
numOfAxiom9 (Calc Impl (Calc Impl a b) (Calc Impl (Calc Impl a1 (Not b1)) (Not a2))) =
  if a == a1 && a == a2 && b == b1 then 9
    else (-1)
numOfAxiom9 _ = (-1)
numOfAxiom10::Expression->Int
numOfAxiom10 (Calc Impl (Not (Not a)) a1) =
  if a == a1 then 10
    else (-1)
numOfAxiom10 _ = (-1)
