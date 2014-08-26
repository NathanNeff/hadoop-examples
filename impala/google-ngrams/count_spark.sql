CREATE EXTERNAL TABLE IF NOT EXISTS 
        google_ngrams(line STRING)
        LOCATION '/google-ngrams';

REFRESH google_ngrams;

SELECT COUNT(line) 
FROM google_ngrams 
WHERE line LIKE "%spark%";
