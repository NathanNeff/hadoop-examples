#!/bin/bash
hadoop jar ~/assets/sleep.jar SleepJob \
    -D pool.name="BallHog" \
    -D mapred.job.name="BallHogJob" \
    -m 10 -r 10 -mt 300000 -rt 300000 &
echo "Just submitted BallHog job"
