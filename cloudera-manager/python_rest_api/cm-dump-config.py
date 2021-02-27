#!/usr/bin/env python

from cm_api.api_client import ApiResource

def get_cm_api():
    host = "host-cm.acme.com"
    port = 7183
    username = "admin"
    password = "admin"
    return ApiResource(server_host=host, server_port=7183, username=username, password=password, use_tls=True, version=13)

def find_cluster(api, cluster_name):
    all_clusters = api.get_all_clusters()
    for cluster in all_clusters:
        if (cluster_name and cluster_name == cluster.name) or not cluster_name:
            print "CLUSTER [%s, Running: %s, URL: %s]" % (cluster.displayName, cluster.fullVersion, cluster.clusterUrl)
            return cluster
    # Could not find
    if cluster_name:
        message = "Can not find cluster \"%s\"" % (cluster_name)
    else:
        message = "Cannot find any cluster"

    raise Exception(message)

def print_config_changes(cluster):
    serviceList = cluster.get_all_services()
    # We get the service list first, so that we can sort the list on the fly
    # by sorting it according to the type (ie. HUE, OOZIE, etc..)
    for service in sorted(serviceList, key=lambda x: x.type):
        print "{s:{c}^{n}}".format(s='+', n=130, c='+')
        print "  SERVICE : [%s - %s, URL: %s]" % (service.type, service.displayName, service.serviceUrl)
        print "{s:{c}^{n}}".format(s='=', n=130, c='=')
        #print "====> %-70s VALUE" % ("NAME")
        #print "====> {s:{c}^{n}}".format(s='-', n=100, c='-')

        # Print service-level configuration changes
        serviceNameConfigList = service.get_config(view='full')[0].items()
        # We then sort this by the name itself
        for name, config in sorted(serviceNameConfigList, key=lambda (nm, cf): nm):
            if (config.value):
                print "  %-70s: %s" % (name, config.value)

        # Print role-level configuration changes
        # Sort all the roles, so that the services that have roles like
        # DATANODE, NAMENODE, etc, always appear in the same order if you run
        # this over and over again.
        roleList = service.get_all_roles()
        for role in sorted(roleList, key=lambda role: role.name):
            #print "  ROLE %s ( hostId: %s )" \
            #            % (role.name, role.hostRef.hostId)
            print "  {s:{c}^{n}}".format(s='  ROLE  ', n=130, c='-')
            print "  %-70s: %s" % ("Role name", role.name)
            print "  %-70s: %s" % ("Role hostId", role.hostRef.hostId)
            # Sort the role config properties as well in our output.
            roleNameConfigList = role.get_config(view='full').items()
            for name, config in sorted(roleNameConfigList, key=lambda (nm, cf): nm):
                if (config.value):
                    print "  %-70s: %s" % (name, config.value)

def main():
    api = get_cm_api()
    cluster = find_cluster(api, None)
    print_config_changes(cluster)


if __name__ == "__main__":
    main()
