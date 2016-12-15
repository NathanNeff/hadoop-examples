SELECT concat_ws(' ', first_name, last_name)
FROM accts
WHERE state = 'CO'
GROUP BY first_name, last_name
ORDER BY last_name, first_name
