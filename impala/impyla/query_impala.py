#!/bin/env python
import sys
from impala.dbapi import connect

which_db = "tpcds"
impalad = ""

if len(sys.argv) > 1:
            impalad = sys.argv[1]
else: 
            print "Usage: query_impyla.py <impala_daemon_to_connect_to>"
            exit(1)

def message(m):
        print "-" * 20
        print m

def show_tables(db):
        cur.execute('SHOW TABLES IN %s' % db)
        tables = cur.fetchall()
        message("The tables in the %s database are: " % db)
        print tables

def top_five_customers(db):
        # This method is obviously database dependent and assumes the TPCDS-DB

        cur.execute("DESCRIBE %s.customer" % db)

        message("Showing customer schema")
        print "Fields in customer are:"
        for fieldz in cur.fetchall():
                print "%-25s %-25s" % (fieldz[0], fieldz[1])

        message("Customer Data")
        cur.execute("SELECT c_last_name, c_first_name FROM %s.customer WHERE c_last_name IS NOT NULL ORDER BY c_last_name DESC LIMIT 50" % db)
        customers = cur.fetchall()

        print "%-25s %-25s\n%s" % ("Last Name", "First Name", "-" * 50)
        for c in customers:
                print "%-25s %-25s" % c


conn = connect(host=impalad, port = 21050)
cur = conn.cursor()

show_tables(which_db)

top_five_customers(which_db)

