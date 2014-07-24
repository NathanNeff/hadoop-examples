#!/bin/bash
# Just compile/syntax check the file
groovyc -cp `hadoop classpath`:`hbase classpath` ./loadRandomData.groovy
