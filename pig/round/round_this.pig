data = LOAD 'data.txt' AS (name:chararray, amount:float);
round = FOREACH data GENERATE name, (float)(ROUND(amount*10))/10 AS data;
STORE round INTO 'round';
