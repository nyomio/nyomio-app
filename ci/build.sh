#!/usr/bin/env bash
cd "$(dirname "$0")" || exit
# escape sequence to change font style to italic (i) and normal (n)
i=$(tput bold)
n=$(tput sgr0)

printf "${i}# Building core-microservice\n"
cd ../core-microservice
./gradlew clean build jibDockerBuild

printf "${i}\n\n# Building nyomio-ui\n"
cd ../nyomio-ui
./build.sh

