SELECT concat_ws(' ', first_name, last_name)
FROM (
	SELECT first_name, last_name
	FROM accts
	WHERE state = 'CO'
	GROUP BY first_name, last_name) unnecessary_subquery
ORDER BY last_name, first_name;
