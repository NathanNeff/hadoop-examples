#!/bin/bash
export HIVE_HOME=/opt/cloudera/parcels/CDH/lib/hadoop/../hive
debug=true kite-dataset -v create sandwiches -s sandwich.avsc
