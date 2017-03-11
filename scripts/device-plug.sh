#!/bin/bash
# owner and group should be set to root 

export DISPLAY=:0
export XAUTHORITY=/home/siva/.Xauthority
#zenity --info --text 'USB Inserted'
notify-send -i /home/siva/Desktop/SIh/pirate-sword-skull1-2-256.png "USB Inserted"
value=$(</home/siva/Desktop/SIh/test)
echo 1 > $value
