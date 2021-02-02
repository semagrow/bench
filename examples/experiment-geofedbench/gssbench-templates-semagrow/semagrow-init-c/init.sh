#!/bin/bash

echo processing $DATASET_NAME

if [ `echo $DATASET_NAME | grep lucas-all` ]
then
  echo lucas-all
  echo "http://lucas_all.benchmark.svc:8080/strabon/Query "$DATASET_ENDPOINT > /semagrow/output/$DATASET_NAME.txt 
fi

if [ `echo $DATASET_NAME | grep invekos-all` ]
then
  echo invekos-all
  echo "http://invekos_all.benchmark.svc:8080/strabon/Query "$DATASET_ENDPOINT > /semagrow/output/$DATASET_NAME.txt 
fi

if [ `echo $DATASET_NAME | grep lucas-them` ]
then
  echo lucas-them
  echo "http://lucas_thematic.benchmark.svc:8890/sparql "$DATASET_ENDPOINT > /semagrow/output/$DATASET_NAME.txt 
fi

if [ `echo $DATASET_NAME | grep invekos-them` ]
then
  echo invekos-them
  echo "http://invekos_thematic.benchmark.svc:8890/sparql "$DATASET_ENDPOINT > /semagrow/output/$DATASET_NAME.txt 
fi

if [ `echo $DATASET_NAME | grep lucas-geom` ]
then
  echo lucas-geom
  TEMP=`echo $DATASET_ENDPOINT | sed 's|http://|postgis://|g'`"semdb"
  echo "postgis://lucas_geometric.benchmark.svc:5432/semdb "$TEMP > /semagrow/output/$DATASET_NAME.txt 
fi

if [ `echo $DATASET_NAME | grep invekos-geom` ]
then
  echo invekos-geom
  TEMP=`echo $DATASET_ENDPOINT | sed 's|http://|postgis://|g'`"semdb"
  echo "postgis://invekos_geometric.benchmark.svc:5432/semdb "$TEMP > /semagrow/output/$DATASET_NAME.txt 
fi

echo done

