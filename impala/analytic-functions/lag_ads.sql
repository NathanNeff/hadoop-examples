SELECT display_date, display_site, n,
		LAG(n) OVER
			   (PARTITION BY display_site 
                ORDER BY display_date) AS nprev
FROM (
		SELECT display_date, display_site, 
        count(display_date) AS n
		FROM ads GROUP BY display_date, display_site
) ads
ORDER BY display_site, display_date;
