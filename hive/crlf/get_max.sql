select max(junk) as the_max, count(junk) as the_count
from dosjunk
group by junk
order by the_max limit 100000
