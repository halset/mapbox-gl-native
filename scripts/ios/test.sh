#!/usr/bin/env bash

set -e
set -o pipefail
set -u

xctool \
    -project ./test/ios/ios-tests.xcodeproj \
    -scheme 'Mapbox GL Tests' \
    -sdk iphonesimulator9.0 \
    test
