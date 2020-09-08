module Main where

import           Comonad19


main :: IO ()
main = do
    let start = initSimulation
    let end = nStepSimulation 15 param start
    putStrLn $ showSimulationResult param end

param :: SimulationParameters
param = Params {
        prob = 0.1,
        incubDays = 3,
        illDays = 3,
        immunityDays = 3,
        size = 10
    }
