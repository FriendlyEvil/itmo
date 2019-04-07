module Deduction where

import Syntax
import HelpStructure
import Axioms

import Data.Maybe
import qualified Data.Map.Strict as Map

deductList::Expression->[Line]->Map.Map Int Expression->[Expression]
deductList a lns mp = deductHelp a (reverse lns) mp []

deductHelp::Expression->[Line]->Map.Map Int Expression->[Expression]->[Expression]
deductHelp a lns mp ans = if (length lns == 0) then ans else deductHelp a (tail lns) mp ((deduct a (head lns) mp) ++ ans)

deduct::Expression->Line->Map.Map Int Expression->[Expression]
deduct a ln mp = if (expression ln == a || isAxiomOrHypothesis ln) then deductNotMP a (expression ln) else
  deductMP a (fromJust $ Map.lookup (snd $ numModPon ln) mp) (expression ln)

deductNotMP::Expression->Expression->[Expression]
deductNotMP a b = let proof1 = toAxiom1 b a
                      proof2 = getMP proof1 in
                      if (a == b) then proofAToA a else [b, proof1, proof2]

deductMP::Expression->Expression->Expression->[Expression]
deductMP a b c = let proof1 = toAxiom2 a b c
                     proof2 = getMP proof1
                     proof3 = getMP proof2 in
                     [(Calc Impl a b), (Calc Impl a (Calc Impl b c)),proof1, proof2, proof3]
