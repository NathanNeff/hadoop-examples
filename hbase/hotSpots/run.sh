#!/bin/bash
groovy --classpath `hbase classpath` ./TransactionImporter.groovy --numRows=10000000
