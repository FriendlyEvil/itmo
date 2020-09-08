module Comonad19 (
    CovidType(..),
    CovidCell(..),
    SimulationParameters(..),
    initSimulation,
    showSimulationResult,
    stepSimulation,
    nStepSimulation
    ) where


import           Control.Comonad (Comonad (..))
import           Control.Monad   (liftM2)
import           Data.List       (intercalate)
import           Grid
import           System.Random   (StdGen, mkStdGen, randomR, split)

data CovidType = Normal
    | Incubation
    | Ill
    | Recovered

instance Show CovidType where
    show Normal     = "_"
    show Incubation = "/"
    show Ill        = "#"
    show Recovered  = "@"

isIll :: CovidCell -> Bool
isIll CovidCell {status = Incubation} = True
isIll CovidCell {status = Ill}        = True
isIll _                               = False

data CovidCell = CovidCell
    { rand   :: StdGen
    , status :: CovidType
    , days   :: Int
    }

instance Show CovidCell where
    show cell = show $ status cell

gefaultCell :: CovidCell
gefaultCell = CovidCell {
        rand = mkStdGen 1,
        status = Normal,
        days = 0
    }

addDay :: CovidCell -> CovidCell
addDay c@CovidCell {days = d} = c {days = d + 1}

data SimulationParameters = Params
    { prob         :: Double
    , incubDays    :: Int
    , illDays      :: Int
    , immunityDays :: Int
    , size         :: Int
    }

neighbours :: [Grid a -> Grid a]
neighbours = horizontals ++ verticals ++ liftM2 (.) horizontals verticals
  where horizontals = [left, right]
        verticals   = [up, down]

countIll :: Grid CovidCell -> Int
countIll g = (length . filter isIll) (fmap (\direction -> extract $ direction g) neighbours)

probabilityIll :: SimulationParameters -> Grid CovidCell -> CovidCell
probabilityIll params g = case p > r of
        True  -> CovidCell {days = 0, status = Incubation, rand = n}
        False -> CovidCell {days = 0, status = Normal, rand = n}
    where
        p = 1 - (1 - prob params) ^ (countIll g)
        (r, n) = randomR (0 :: Double, 1 :: Double) (rand $ extract g)


infect :: SimulationParameters -> Grid CovidCell -> CovidCell
infect params g = let cell = extract g in
    case status cell of
        Normal -> probabilityIll params g
        Incubation -> case days cell == incubDays params of
            True  -> cell {days = 0, status = Ill}
            False -> addDay cell
        Ill -> case days cell == illDays params of
            True  -> cell {days = 0, status = Recovered}
            False -> addDay cell
        Recovered -> case days cell == immunityDays params of
            True  -> cell {days = 0, status = Normal}
            False -> addDay cell

leftSplit, rightSplit :: CovidCell -> CovidCell
leftSplit  c@CovidCell {rand = r} = c {rand = fst $ split r}
rightSplit c@CovidCell {rand = r} = c {rand = snd $ split r}

setFirstIll :: Grid CovidCell -> Grid CovidCell
setFirstIll g = gridWrite (gridRead g){status = Incubation} g

initSimulation :: Grid CovidCell
initSimulation = setFirstIll $ Grid $ genericMove ((fmap rightSplit . listLeft) . listRight)
    ((fmap leftSplit . listRight) . listLeft) (gMove leftSplit rightSplit gefaultCell)

showSimulationResult :: SimulationParameters -> Grid CovidCell -> String
showSimulationResult param g = "|" ++ Data.List.intercalate "|"
                (Data.List.intercalate ["\n"] (map ((flip toList) dist) (toList zipper dist))) ++ "|"
            where
                dist = size param
                (Grid zipper) = fmap show g

stepSimulation :: SimulationParameters -> Grid CovidCell -> Grid CovidCell
stepSimulation param = extend (infect param)

nStepSimulation :: Int -> SimulationParameters -> Grid CovidCell -> Grid CovidCell
nStepSimulation n param g = repeatN n (stepSimulation param) g where
    repeatN :: Int -> (a -> a) -> a -> a
    repeatN 0 _ res = res
    repeatN a f res = repeatN (a - 1) f (f res)
