#!/bin/bash
set -e
mvn package
java -cp target/hbasesandbox-1.0-SNAPSHOT.jar:`hbase classpath` hbasesandbox.StopRowThing
