#!/bin/bash
ulimit -s 20280
ulimit -c unlimited
#ulimit -n 20480

cd `dirname $0`
BIN_DIR=/opt/laundry-app/
DEPLOY_DIR=/opt/laundry-app/

APPLICATION_NAME="laundry-app"
RUN_ENVIRONMENT="test"
EXECUTOR_JAR=${DEPLOY_DIR}/"laundry-admin.jar"
LOGS_DIR=${DEPLOY_DIR}"/logs"

PIDS=`ps -ef | grep java | grep -v grep | grep "DEPLOY_DIR" |awk '{print $2}'`
if [ -n "$PIDS" ]; then
    echo "ERROR: The $APPLICATION_NAME already started!"
    echo "PID: $PIDS"
    exit 1
fi

if [ ! -d ${LOGS_DIR} ]; then
    mkdir -p ${LOGS_DIR}
fi
STDOUT_FILE=${DEPLOY_DIR}/console.log

ENVIRONMENT_MEM="-Xms512m -Xmx512m"
PERM_SIZE="128m"
MAX_PERM_SIZE="256m"

#ENVIRONMENT_MEM="-Xms2048m -Xmx2048m"
#PERM_SIZE="256m"
#MAX_PERM_SIZE="512m"

JAVA_OPTS="-XX:+PrintCommandLineFlags -XX:-OmitStackTraceInFastThrow -XX:-UseBiasedLocking -XX:AutoBoxCacheMax=20000"
MEM_OPTS="-server ${ENVIRONMENT_MEM} -XX:+AlwaysPreTouch -XX:MetaspaceSize=${PERM_SIZE} -XX:MaxMetaspaceSize=${MAX_PERM_SIZE}"
CMS_GC_OPTS="-XX:NewRatio=1 -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:MaxTenuringThreshold=6 -XX:+ParallelRefProcEnabled -XX:+ExplicitGCInvokesConcurrent"
GCLOG_OPTS="-Xloggc:${LOGS_DIR}/gc.log -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCApplicationConcurrentTime -XX:+PrintGCDateStamps -XX:+PrintGCDetails"
CRASH_OPTS="-XX:ErrorFile=${LOGS_DIR}/hs_err_%p.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${LOGS_DIR}/"
SPRING_OPTS="--spring.profiles.active=${RUN_ENVIRONMENT}"

echo "Starting the $APPLICATION_NAME ..."
java ${JAVA_OPTS} ${MEM_OPTS} ${GCLOG_OPTS} ${CRASH_OPTS} -jar ${EXECUTOR_JAR} ${SPRING_OPTS} > ${STDOUT_FILE} &
tail -f ${STDOUT_FILE}|sed -e "/on/{/port(s)/q}"