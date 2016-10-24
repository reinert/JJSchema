#!/bin/bash

if [ -n "$1" ]; then
  RV=`echo $1 | sed 's/\./\\\\./g'`
  REP="'0,/<tag>/s:<tag>.*</tag>:<tag>$RV</tag>:g'"
  eval sed -i $REP pom.xml
fi