#!/usr/bin/env bash
cd "$(dirname "$0")" || exit

# escape sequence to change font style to italic (i) and normal (n)
i=$(tput bold)
n=$(tput sgr0)

currentK8sContext=$(kubectl config current-context)

case "$currentK8sContext" in
  minikube)
    eval $(minikube docker-env)
    ;;
esac

printf "${i}# Building core-microservice-api and publishing locally\n"
cd ../core-microservice-api
./gradlew clean build publishToMavenLocal

printf "${i}#\n\n Building core-microservice\n"
cd ../core-microservice
./gradlew clean build jibDockerBuild

printf "${i}\n\n# Building nyomio-ui\n"
cd ../nyomio-ui
./build.sh

