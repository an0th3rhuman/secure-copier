#!/bin/bash

export DISPLAY=:0
export XAUTHORITY=/home/hariharan/.Xauthority
#zenity --info --text 'USB Inserted'

notify-send -i /usr/local/sih/udev/pirate-sword-skull1-2-256.png "USB Removed"

umount  /media/restricted/*
rm -rf /media/restricted

exit 0 


