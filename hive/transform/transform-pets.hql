ADD FILE /home/training/src/training-scripts/hive/transform/legalpets.pl;

FROM pets
SELECT TRANSFORM ( pet )
USING "legalpets.pl" 
AS name, islegal;
