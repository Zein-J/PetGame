#!/bin/bash
pnpm run watch &

WATCHER_PID=$!

mvn clean javafx:run

kill $WATCHER_PID