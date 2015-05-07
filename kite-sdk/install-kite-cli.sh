#!/bin/bash
# Download kite-dataset commandline executable
SOME_DIR_ON_PATH=~/bin
cd $SOME_DIR_ON_PATH
curl http://central.maven.org/maven2/org/kitesdk/kite-tools/0.18.0/kite-tools-0.18.0-binary.jar -o $SOME_DIR_ON_PATH/kite-dataset
chmod +x $SOME_DIR_ON_PATH/kite-dataset
