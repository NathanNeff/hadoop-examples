#!/usr/bin/env python
import sys

if len(sys.argv) > 1:
        num_lines = int(sys.argv[1])
else:
        num_lines = 1000

for i in range(0, num_lines):
        print i
