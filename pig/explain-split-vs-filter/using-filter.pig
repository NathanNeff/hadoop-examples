wlogs = load 'webcrawl.txt' as (pageid, url, timestamp);
apr03 = filter wlogs by timestamp < '20110404';
apr02 = filter wlogs by timestamp < '20110403' and timestamp > '20110401';
apr01 = filter wlogs by timestamp < '20110402' and timestamp > '20110331';
store apr03 into 'filter/20110403';
store apr02 into 'filter/20110402';
store apr01 into 'filter/20110401';
