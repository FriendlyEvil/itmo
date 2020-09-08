module GeometryBench
  (geometryBench
  ) where

import           Geometry       (Point (..), doubleArea, doubleArea2, perimeter,
                                 perimeter2)

import           Criterion.Main (bench, bgroup, defaultMain, nf)

generaPoints :: Int -> [Point]
generaPoints n = fmap (\x -> Point x x) [1..n]

geometryBench :: IO()
geometryBench = defaultMain [
    bgroup "perimeter" [
      bench ("first method") $ nf perimeter points,
      bench ("second method") $ nf perimeter2 points
    ],
    bgroup "area" [
      bench ("first method") $ nf doubleArea points,
      bench ("second method") $ nf doubleArea2 points
    ]
  ] where
      points = generaPoints (10 ^ 7)
      perimeterEval = bench ("first method") $ nf perimeter points
      perimeterEval2 = bench ("second method") $ nf perimeter2 points
