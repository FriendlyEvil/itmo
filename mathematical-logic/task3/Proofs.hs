module Proofs where

import Syntax
import HelpStructure
import Axioms
import Deduction
import Helpers

import qualified Data.Map.Strict as Map
import Data.Maybe

-- a->b |- !b->!a
doConterposition::Expression->[Expression]
doConterposition (Calc Impl a b) = (Calc Impl a b) : proof where
-- a->b, !b |- !a
  proof0 = [setAxiom10 $ toLine(Calc Impl a b), toLine(Not b),setAxiom10 $ toLine(aToB a b),proof1, proof2, proof3, proof4, proof5]
  toLine = inputToLine 0
  proof1 = toLine $ toAxiom9 a b
  proof2 = setMP (toLine $ getMP $ expression proof1) 1 1
  proof3 = toLine $ toAxiom1 (Not b) a
  proof4 = setMP (toLine $ getMP $ expression proof3) 2 2
  proof5 = setMP (toLine $ getMP $ expression proof2) 3 3
  mp = Map.fromList [(1, aToB a b), (2, (Not b)), (3, (aToB a (Not b)))]
-- a->b |- !b->!a
  proof = deductList (Not b) proof0 mp

-- !!a, !!(a->b) |- !!b
proofMP::Expression->[Expression]
proofMP (Calc Impl a b) = proof  where
  toLine = inputToLine 0
  nnA = (Not (Not a))
  nnB = (Not (Not b))
  nnAToB = (Not (Calc Impl a b))
  proof1 = toAxiom2 (Not b) nnA (Not (Calc Impl a b))
-- !b->!!a
  proof2 = toAxiom1 nnA (Not b)
  proof3 = getMP proof2
-- !b->!!a->!(a->b)
  -- !b |- !!a->!(a->b)
    -- !b (a->b) |- !a
  proof12 = toLine $ toAxiom9 a b
  proofmp3 = setAxiom10 $ toLine $ (Calc Impl a b)
  proofmp1 = setAxiom10 $ toLine $ (Not b)
  proof13 = toLine $ toAxiom1 (Not b) a
  proof14 = setMP (toLine $ getMP $ expression proof13) 1 1
  proof15 = setMP (toLine $ getMP $ expression proof12) 3 3
  proof16 = setMP (toLine $ getMP $ expression proof15) 4 4
  mp1 = Map.fromList [(1, Not b), (3, expression proofmp3), (4, expression proof14)]
    --
  -- !b |- (a->b)->!a
  proof17 = deductList (Calc Impl a b) [proof12, proofmp3, proofmp1, proof13, proof14, proof15, proof16] mp1
  -- !b |- !!a -> !(a->b)
  proof18 = doConterposition (last proof17)
  -- |- !b -> !! a -> !(a->b)
  hyp = Map.fromList [(Not b, 1)]
  (proof19, mp2) = expressionsToLines ((Not b) : proof17 ++ proof18) hyp
  proofs20 = deductList (Not b) proof19 mp2
--
  proof8 = getMP proof1
-- !b->!(a->b)
  proof9 = getMP proof8
-- !!(a->b)->!!b
  proofs10 = doConterposition proof9
  proof11 = nnB
  proof = [proof1, proof2, proof3] ++ proofs20 ++ [proof8, proof9] ++ proofs10 ++ [proof11]


-- |- !!(!!a->a)
proofTenAxiom::Expression->[Expression]
proofTenAxiom (Calc Impl b a) = [proof1, proof2, proof3] ++ proofs4 ++ [proof6] ++ proofs7 ++ [proof8, proof9] where
  nnA = (Not (Not a))
  nnAToA = (Calc Impl nnA a)
  proof1 = toAxiom9 (Not nnAToA) (Not a)
-- !(!!a->a)->a  start
  proof2 = toAxiom1 nnAToA a
  proof3 = getMP proof2
  proofs4 = doConterposition proof3
-- end
-- new 10 Axiom   !a->!!a->a
  proof6 = toNewAxiom10 (Not a) a
-- !(!!a->a)->!!a
  proofs7 = doConterposition proof6
  proof8 = getMP proof1
  proof9 = getMP proof8

toNewAxiom10::Expression->Expression->Expression
toNewAxiom10 a b = (Calc Impl a (Calc Impl (Not a) b))

proof::[Line]->Map.Map Int Expression->[Expression]
proof lns mp = smallProof lns [] mp

smallProof::[Line]->[Expression]->Map.Map Int Expression->[Expression]
smallProof lns ans mp = if (length lns == 0) then ans else smallProof (tail lns) (ans ++ (proofLine (head lns) mp)) mp

proofLine::Line->Map.Map Int Expression->[Expression]
proofLine ln mp = if (isAxiomOrHypothesis ln) then
    if ((isHypothesis ln) || (numAxiom ln /= 10)) then proofAxiomOrHyp (expression ln) else proofTenAxiom (expression ln)
  else proofMP (fromJust $ Map.lookup (fst $ numModPon ln) mp)

proofAxiomOrHyp::Expression->[Expression]
proofAxiomOrHyp a = [proof1, proof2, proof3] ++ proofs4 ++ [proof5, proof6, proof7] where
  proof1 = a
  proof2 = toAxiom1 a (Not a)
  proof3 = getMP proof2
  proofs4 = proofAToA (Not a)
  proof5 = toAxiom9 (Not a) a
  proof6 = getMP proof5
  proof7 = getMP proof6
