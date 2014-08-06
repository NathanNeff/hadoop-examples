#!/bin/bash
# Show log4j properties needed for Pig 0.12 CDH *quiet* mode
pig -4 ./conf/log4j.properties -x local sales.pig 
