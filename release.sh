#!/bin/bash

# set version variables
source ./VERSION

MINOR=$((MINOR + 1))

CURRENT="$MAJOR.$MINOR"
SNAPSHOT="$MAJOR.$((MINOR + 1))-SNAPSHOT"

# update version to release version
mvn versions:set -DnewVersion=$CURRENT
mvn versions:commit

# update tag to release version
./tag.sh $CURRENT --readme

# commit
git add .
git commit -m "Release $CURRENT"

# deploy
mvn clean deploy -Prelease

# tag
git tag -a JJSchema-$CURRENT -m "JJSchema v$CURRENT"
git push origin JJSchema-$CURRENT

# update version to next snapshot
mvn versions:set -DnewVersion=$SNAPSHOT
mvn versions:commit

# update tag to next snapshot
./tag.sh HEAD

# update VERSION file
echo "MAJOR=$MAJOR" > ./VERSION
echo "MINOR=$MINOR" >> ./VERSION

# commit
git add .
git commit -m "Start $SNAPSHOT development"
git push origin master

