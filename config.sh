#!/bin/bash

chown root device-plug.sh 
chgrp root device-plug.sh

cp 100-sample.rules /etc/udev/rules.d
sudo service udev restart 

cat lockdown.sh > /etc/rc.local

