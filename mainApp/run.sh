#!/bin/bash
set -e

echo [Main App STARTING] running...
(exec "${BASH_SOURCE%/*}/gradlew" clean bootRun --no-daemon)
echo [Main App FINISHED] has been run
