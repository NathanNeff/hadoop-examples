create table if not exists dates_and_times (actual_value_as_string STRING, t timestamp);

insert into dates_and_times values
  ('1966-07-30', '1966-07-30'), 
  ('1985-09-25 17:45:30.005', '1985-09-25 17:45:30.005'), 
  ('08:30:00', '08:30:00'), 
  (CAST(now() AS STRING), now());

select actual_value_as_string, hour(t) 
from dates_and_times
order by actual_value_as_string;
