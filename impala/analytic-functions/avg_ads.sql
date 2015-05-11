SELECT display_date, display_site, n,
		AVG(n) OVER
			   (PARTITION BY display_site 
                ORDER BY display_date
                ROWS BETWEEN 3 PRECEDING AND CURRENT ROW) AS wavg
FROM (
		SELECT display_date, display_site, 
        count(display_date) AS n
		FROM ads GROUP BY display_date, display_site
) ads
ORDER BY display_site, display_date;
