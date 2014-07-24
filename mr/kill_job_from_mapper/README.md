# Try Kill

This MapReduce job shows how to kill a job from a Mapper / Reducer
using the Context object.

The downgrade() method was the toughest thing to find, and
it helped to look through the source code for JobClient.

# Setup

Simply use ./run.sh to see this job kill itself from a Map.

The job uses NLineInputFormat to create 1 Mapper for each line of
./somedata.txt.  The Maps all read their *one* line of massive data,
and if they don't find a "100", then the Map calls "killJob".

# Challenge

It would be cool for the Mapper who kills the job to report itself to the
master (Application Master or JobTracker).  This would make finding the
"offending" Map task much easier for admins by looking at the Job History log,
instead of scouring through 100 Maps whose state is "FAILED".

The "state" of the Maps in this job is either "SUCCEEDED" or "FAILED".
It would seem that "KILLED" would be a more fitting state for the Maps that
were running when the job was killed.  Hmmmm.
