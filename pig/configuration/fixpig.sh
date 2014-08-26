#!/bin/bash
# Script to deploy log4j.properties
sudo cp /etc/pig/conf/log4j.properties /etc/pig/conf/log4j.properties.bak
sudo cp log4j.local /etc/pig/conf/log4j.properties
