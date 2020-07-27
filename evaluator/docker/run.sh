#!/bin/bash

touch /kobe/run.prop

if [ "$TIMEOUT" ]
then
  echo timeout = "$TIMEOUT" >> /kobe/run.prop
fi

if [ "$EVAL_RUNS" ]
then
  echo evalRuns = "$EVAL_RUNS" >> /kobe/run.prop
fi

if [ "$EXPERIMENT" ]
then
  echo experimentName = "$EXPERIMENT" >> /kobe/run.prop
fi

echo template is $TEMPLATE

if [ "$TEMPLATE" ]
then
  mkdir /new_queries
  echo querySet = /new_queries >> /kobe/run.prop
  
  for Q in `ls /queries`
  do
    for T in $TEMPLATE
    do
      sed 's/\$\#\$/'$T'/g' "/queries/"$Q > "/new_queries/"$Q"-"$T
    done
  done
fi

cd /kobe/
sh runEval.sh "$ENDPOINT" /kobe/run.prop
cat /kobe/result/result.csv
