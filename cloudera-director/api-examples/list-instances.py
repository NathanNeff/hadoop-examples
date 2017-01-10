#!/bin/env python
from cloudera.director.common.client import ApiClient
from cloudera.director.latest import AuthenticationApi, EnvironmentsApi, DeploymentsApi, ClustersApi
from cloudera.director.latest.models import Login

client = ApiClient("http://localhost:7189")
AuthenticationApi(client).login(Login(username="admin", password="admin"))
for envName in EnvironmentsApi(client).list():
	print "Environment: %s" % envName
	if DeploymentsApi(client).list(envName):
		for depName in DeploymentsApi(client).list(envName):
			print "\tDeployment: %s" % depName
			if ClustersApi(client).list(envName, depName):
				for clusterName in ClustersApi(client).list(envName, depName):
					print "\t\tCluster: %s" % clusterName
					cluster = ClustersApi(client).get(envName, depName, clusterName)
					if cluster.instances:
						for instance in cluster.instances:
							print "\t\t\tInstance: %s %s" % (instance.properties['publicIpAddress'], instance.health.status)

