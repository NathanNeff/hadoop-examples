DROP TABLE IF EXISTS debate;
CREATE TABLE debate(debatetext string);

LOAD DATA LOCAL INPATH 'debate.txt' 
INTO TABLE debate;

SELECT EXPLODE(NGRAMS(
SENTENCES(debate.debatetext), 4, 10)) -- <<< Try this with 3 or 4 and see how results change
AS x
FROM debate
