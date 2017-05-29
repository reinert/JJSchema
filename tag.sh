#!/bin/bash

if [ -n "$1" ]; then
  RV=`echo $1 | sed 's/\./\\\\./g'`
  REP="'0,/<tag>/s:<tag>.*</tag>:<tag>$RV</tag>:g'"
  eval sed -i $REP pom.xml
  if [ "$2" == "--readme" ]; then
    REP="'0,/<version>/s:<version>.*</version>:<version>$RV</version>:g'"
    eval sed -i $REP README.md
  fi
fi
