sales = LOAD 'sales.txt' AS (salesperson_id, amount);
sales_tuples = FOREACH sales GENERATE (salesperson_id, amount); -- This generates a bag with a single tuple field

-- This is what you want (No parens)
sales_bag = FOREACH sales GENERATE salesperson_id, amount;
sh echo "This is sales BAG";
DUMP sales_bag;
sh echo "This is sales TUPLE";
DUMP sales_tuples;
