#!/bin/bash

chown root device-plug.sh 
chgrp root device-plug.sh

cp 100-sample.rules /etc/udev/rules.d
cp device-plug.sh  /usr/local/bin
cp device-unplug.sh  /usr/local/bin
sudo service udev restart 

cat lockdown.sh > /etc/rc.local

