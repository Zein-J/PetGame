#!/bin/bash
pnpm run watch &

WATCHER_PID=$!

# run maven in development mode
mvn clean javafx:run -Djavafx.compile=true

kill $WATCHER_PID