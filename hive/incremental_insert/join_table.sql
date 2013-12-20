-- INSERT into join_table the name, title, nickname from the employees and nicknames,
-- but DO NOT duplicate existing data in join_table
INSERT INTO TABLE join_table
SELECT e.name, e.title, n.nickname
        FROM employees e
        JOIN nicknames n ON e.name = n.name
        LEFT OUTER JOIN join_table jt 
        ON (jt.name = e.name AND n.nickname = jt.nickname AND jt.title = e.title)
        WHERE jt.name IS NULL;
