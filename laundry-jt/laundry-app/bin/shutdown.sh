#!/bin/bash
appName=laundry-app.jar
PID=0

javaps=`jps -l | grep $appName`
if [ -n "$javaps" ] 
then 
    echo $javaps
    PID=`echo $javaps | awk '{print $1}'`
    kill $PID
    #echo $PID
else
    
    exit 1
fi

echo -n "shutdowning..."

while [ true ]
do 
    javaps=`jps -l | grep $appName`
    if [ -n "$javaps" ] 
    then         
        echo -n "."
    else
    echo ""
	exit 1
    fi
    sleep 1
done

