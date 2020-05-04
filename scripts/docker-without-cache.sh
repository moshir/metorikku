#!/bin/bash
set -e

docker build -t metorikku/spark --build-arg SPARK_VERSION=$SPARK_VERSION --build-arg HADOOP_VERSION=$HADOOP_VERSION -f docker/spark/Dockerfile docker/spark
docker build -t metorikku/hive --build-arg HIVE_VERSION=$HIVE_VERSION --build-arg HUDI_VERSION=$HUDI_VERSION -f docker/hive/Dockerfile docker/hive
docker build -t metorikku/metorikku -f docker/metorikku/Dockerfile .
