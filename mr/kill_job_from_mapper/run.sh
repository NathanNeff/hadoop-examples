#!/bin/bash
hadoop fs -put -f somedata.txt
rm -f solution/*.class
rm -f TryKill.jar
javac -cp `hadoop classpath` solution/*java
jar cvf TryKill.jar solution/*.class
hadoop jar TryKill.jar solution.TryKill -Dmapred.job.name="Job Kill From Mapper" somedata.txt
