#!/usr/bin/env bash

HERE=${BASH_SOURCE%/*}

java -jar "$HERE/playmaker_deploy.jar" "$@"

