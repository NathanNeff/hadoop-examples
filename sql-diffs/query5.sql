SELECT sq.name, sq.last_name, sq.first_name 
FROM 
    (SELECT DISTINCT concat_ws(' ', first_name, last_name) AS name, first_name, last_name 
     FROM accts 
     WHERE state='CO') sq
ORDER BY sq.last_name, sq.first_name;
