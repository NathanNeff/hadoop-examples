# Local Mode Hacks

Wanted to find out if Pig can be called:

- Both local execution and local filesystem is easy `$pig -x local`
- Local execution but HDFS filesystem. `$ pig -jt local` seems to work, even on YARN

Yay!
