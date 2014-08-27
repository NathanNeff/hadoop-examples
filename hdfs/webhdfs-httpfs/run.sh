#!/bin/bash
set -e
SERVER_AND_PORT=$1
FUNCTION=$2
PREFIX="http://$SERVER_AND_PORT/webhdfs/v1"
HOMEDIR="user/$USER"
TESTDIR="$HOMEDIR/WebHDFSTest"
USAGE="Usage: $0 <NameNode:port or HttpFS server:port> <put|get|mkdir|deleteFile|deleteDir>"

test -z "$SERVER_AND_PORT" && {
        echo $USAGE
        exit 1
}

case $FUNCTION in
        mkdir)
                echo "Will now create the $TESTDIR directory"
                curl -i -X PUT "$PREFIX/$TESTDIR/?op=MKDIRS&user.name=$USER"
        ;;
        put)
                # Some options:
                # curl -i -X PUT "http://<HOST>:<PORT>/webhdfs/v1/<PATH>?op=CREATE
                #                [&overwrite=<true|false>][&blocksize=<LONG>][&replication=<SHORT>]
                #                [&permission=<OCTAL>][&buffersize=<INT>]"
                # TODO: Test that the TestDir already exists
                echo "Will now PUT ./testdata.txt into directory $TESTDIR"
                curl -i -L -X PUT "$PREFIX/$TESTDIR/testdata.txt?op=CREATE&user.name=$USER" -T ./testdata.txt
        ;;
        get)
                echo "Will now GET $TESTDIR/testdata.txt"
                curl -i "$PREFIX/$TESTDIR/testdata.txt?op=OPEN&user.name=$USER"
        ;;
        deleteFile)
                echo "Will now delete $TESTDIR/testdata.txt"
                curl -i -X DELETE "$PREFIX/$TESTDIR/testdata.txt?op=DELETE&user.name=$USER"
        ;;
        deleteDir)
                echo "Will now delete $TESTDIR"
                curl -i -X DELETE "$PREFIX/$TESTDIR?op=DELETE&user.name=$USER"
                # Try &recursive=true if directory non-empty
                # curl -i -X DELETE "$PREFIX/$TESTDIR?op=DELETE&user.name=$USER&recursive=true"
        ;;
        *)
                echo "Command not understood: $FUNCTION"
                echo $USAGE
                exit 1
        ;;
esac

