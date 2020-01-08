#!/usr/bin/env bash
cd "$(dirname "$0")" || exit

#helm uninstall dev
helm dependency update helm
helm upgrade --install nyomio-app helm --set global.coreMicroserviceEnabled=false,global.clusterType=minikube,global.devVersion=1
