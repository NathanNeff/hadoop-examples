#!/usr/bin/env python
# Display configuration settings for DataNodes
import sys
from cm_api.api_client import ApiResource

cm_host = None
cluster_name = None

if len(sys.argv) > 2:
        cm_host = sys.argv[1]
        cluster_name = sys.argv[2]
else:
        sys.stderr.write("Usage: simple_config_settings.py <CM_SERVER> <CLUSTER_NAME>")
        sys.exit(1)

api = ApiResource(cm_host, username="admin", password="admin")

# The service api must be retrieved from the cluster api
def printDataNodeConfig():
        c = api.get_cluster(cluster_name)
        dn_groups = []
        hdfs = None
        for service in c.get_all_services():
                if service.type == "HDFS":
                        hdfs = service

        for group in hdfs.get_all_role_config_groups():
                if group.roleType == 'DATANODE':
                        dn_groups.append(group)

        for cg in dn_groups:
                print "Found config group:  " + cg.name

        dn_config = dn_groups[0].get_config(view='full')

        print "Each datanode will store data on these local directories: \n%s" % dn_config['dfs_data_dir_list'].value
        print "Each datanode can use up to this amount on each disk for HDFS: \n%s" % dn_config['dfs_datanode_du_reserved'].value

printDataNodeConfig()
