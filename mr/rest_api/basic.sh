#!/bin/bash
# http://hadoop.apache.org/docs/r2.3.0/hadoop-yarn/hadoop-yarn-site/WebServicesIntro.html#URIs
RESOURCE_MGR=$1
test -z "$1" && {
        echo "Usage: $0 <resource manager>"
        exit 1
}
curl --compressed -H "Accept: application/json" -X GET "http://$RESOURCE_MGR:8088/ws/v1/cluster"
