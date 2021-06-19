#!/usr/bin/env bash

HERE=${BASH_SOURCE%/*}
JAVA_HOME="$HERE/java-home"

"$JAVA_HOME/bin/java" -jar "$HERE/program.jar" "$@"

