#!/bin/bash
if [[ -z "$1" ]]; then
        echo "Usage: $0 <impalad>"
        exit 1
fi

impala-shell -i $1 -f find_spark.sql
