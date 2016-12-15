SELECT sorted.name FROM 
    (SELECT DISTINCT concat_ws(' ', first_name, last_name) AS name, first_name, last_name 
     FROM accts 
     WHERE state='CO' ORDER BY last_name, first_name) sorted;
