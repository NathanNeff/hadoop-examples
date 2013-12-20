wlogs = load 'webcrawl.txt' as (pageid, url, timestamp);
split wlogs into apr03 if timestamp < '20110404',
apr02 if timestamp < '20110403' and timestamp > '20110401',
apr01 if timestamp < '20110402' and timestamp > '20110331';

store apr03 into 'split/20110403';
store apr02 into 'split/20110402';
store apr01 into 'split/20110401';
