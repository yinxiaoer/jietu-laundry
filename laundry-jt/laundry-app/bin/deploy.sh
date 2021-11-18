#!/bin/bash
DEPLOY_DIR=`pwd`
APP_NAME="laundry-app"
sh ${DEPLOY_DIR}/shutdown.sh
dataStr=`date +%Y%m%d`
mv -f ${DEPLOY_DIR}/${APP_NAME}.jar ${DEPLOY_DIR}/backup/${APP_NAME}.jar.$dataStr
mv -f ${DEPLOY_DIR}/deploy/${APP_NAME}.jar ${DEPLOY_DIR}
sh ${DEPLOY_DIR}/startup.sh
