TMP_FILE=words_`date "+%F%s"`
hadoop fs -put /usr/share/dict/words $TMP_FILE
hadoop fs -setrep 4 $TMP_FILE
echo "Check out $TMP_FILE in your home dir."
sleep 10
hadoop fs -setrep 3 $TMP_FILE
