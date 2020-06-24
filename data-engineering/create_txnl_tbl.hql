USE analyst;

DROP TABLE IF EXISTS products_txnl;
CREATE TABLE `products_txnl`(
`prod_id` int,
`brand` string,
`name` string,
`price` int,
`cost` int,
`shipping_wt` int);

DESCRIBE FORMATTED products_txnl;

