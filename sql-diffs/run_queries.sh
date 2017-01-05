#!/bin/bash
# If no query*sql files are present, don't run anything.
shopt -s nullglob
DB_NAME=$1
USER_ID=$2
PASSWORD=$3
if [[ -z "$DB_NAME" || -z "$USER_ID" || -z "$PASSWORD" ]]; then
    echo "Usage: $0 <DB_NAME> <USER_ID> <password>"
    exit 1
fi
for SQL_QUERY_FILE in query*.sql; do
        impala-shell -d $DB_NAME -f $SQL_QUERY_FILE \
                     -o $SQL_QUERY_FILE-impala-results.txt \
                     --delimited
        mysql -u $USER_ID --password=$PASSWORD $DB_NAME --column-names=false \
		    < $SQL_QUERY_FILE > $SQL_QUERY_FILE-mysql-results.txt

		beeline -u jdbc:hive2://localhost:10000/$DB_NAME --silent --verbose=false \
                 --showHeader=false \
                 --username=$USER_ID --password=$PASSWORD \
                -f $SQL_QUERY_FILE --outputformat=tsv2 | sed -e '/^$d/d' > $SQL_QUERY_FILE-hive-results.txt
done
