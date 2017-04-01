#!/bin/bash

chown root device-plug.sh 
chgrp root device-plug.sh

cp 100-sample.rules /etc/udev/rules.d
cp device-plug.sh  /usr/local/bin
cp device-unplug.sh  /usr/local/bin

service udev restart 

adduser --uid 499 usbsheild
usermod -aG sudo username

mkdir -p /media/restricted/usb
chown usbsheild /media/restricted/usb
chmod 700 /media/restricted/usb

cat lockdown.sh > /etc/rc.local


