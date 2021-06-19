#!/usr/bin/env bash

HERE=${BASH_SOURCE%/*}
JAVA_HOME="$HERE/java-home"

JAVA_BIN="java"
if [ -d "$JAVA_HOME" ]; then
  JAVA_BIN="$JAVA_HOME/bin/java"
fi
"$JAVA_BIN" -Dinstall.dir="$HERE" -jar "$HERE/program.jar" "$@"

