SELECT c.cust_id FROM he.customers c
WHERE cust_id IN
(SELECT o.cust_id FROM he.orders o
		WHERE YEAR(o.order_date) = 2012);

SELECT c.cust_id
FROM he.customers c
LEFT SEMI JOIN he.orders o
ON (c.cust_id = o.cust_id
		AND YEAR(o.order_date) = 2012);
