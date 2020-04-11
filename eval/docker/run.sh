#!/bin/bash

#define queries
mkdir /etc/querySet

if [ "$CROSS_DOMAIN" == "true" ]; then
    cp /kobe/queries/resources/fedbench/crossdomain/* /etc/querySet/
fi

if [ "$LIFE_SCIENCE" == "true"  ]; then
    cp /kobe/queries/resources/fedbench/lifescience/* /etc/querySet/
fi

if [ "$OPFBENCH" == "true"  ]; then
    cp /kobe/queries/resources/opfbench/* /etc/querySet/
fi

if [ "$SGPILOTS" == "true"  ]; then
    cp /kobe/queries/resources/sgpilots/* /etc/querySet/
fi

#define properties
touch /kobe/run.prop

if [ "$TIMEOUT" ]; then
    echo timeout = "$TIMEOUT" >> /kobe/run.prop
fi

if [ "$EVAL_RUNS" ]; then
    echo evalRuns = "$EVAL_RUNS" >> /kobe/run.prop
fi

cd /kobe/
sh runEval.sh "$ENDPOINT" /kobe/run.prop
cat /kobe/result/result.csv
