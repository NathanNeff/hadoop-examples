#!/usr/bin/env python
import sys
from cm_api.api_client import ApiResource

cm_host = ""
if len(sys.argv) > 1:
        cm_host = sys.argv[1]
else:
        sys.stderr.write("Usage: simple_cluster_properties.py <CM_SERVER>")
        sys.exit(1)

api = ApiResource(cm_host, username="admin", password="admin")

def printClusterNames():
        for c in api.get_all_clusters():
                print "Cluster \"%s\" is version %s" % (c.name, c.version)

# Host Object Model http://cloudera.github.io/cm_api/apidocs/v6/ns0_apiHost.html
def printClusterHosts():
        for c in api.get_all_clusters():
                # cluster.get_all_hosts returns ApiHostRefs, which need to be looked up
                print "Hosts in cluster \"%s\" are: " % c.name
                for host_ref in c.list_hosts():
                        host = api.get_host(host_ref.hostId)
                        print host.hostname
                        
def printHostTemplates(host_template_name):                        
        for c in api.get_all_clusters():
            print c.get_all_host_templates()
            host_template = c.get_host_template(host_template_name)
            if host_template is not None:
                print "I found host template \"%s\":" % host_template_name
                print host_template

printClusterNames()
printClusterHosts()
printHostTemplates("ThisGuy")
