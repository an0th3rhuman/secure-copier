#!/bin/bash

export DISPLAY=:0
export XAUTHORITY=/home/hariharan/.Xauthority

java -cp /usr/local/sih/bin/bcpg-jdk15on-156.jar:/usr/local/sih/bin/commons-codec-1.10-test-sources.jar:/usr/local/sih/bin/jpgpj.jar:/usr/local/sih/bin/bcprov-jdk15on-156.jar:/usr/local/sih/bin/commons-io-2.5.jar:/usr/local/sih/bin/commons-codec-1.10.jar:/usr/local/sih/bin/commons-io-2.5-javadoc.jar:/usr/local/sih/bin/commons-codec-1.10-javadoc.jar:/usr/local/sih/bin/commons-codec-1.10-sources.jar:/usr/local/sih/bin/commons-codec-1.10-tests.jar:/usr/local/sih/bin/ init

exit 0
