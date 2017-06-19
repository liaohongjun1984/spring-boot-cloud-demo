#! /bin/bash
# this file should be put in: /data/AppSource/项目组/工程名/script/
# sample: /data/AppSource/Act/act-api/script/start.sh

sh /data/AppSource/script/_core.sh deploy

sh /data/AppSource/script/_core.sh start -Xms64m -Xmx128m