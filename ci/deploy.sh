#!/usr/bin/env bash
cd "$(dirname "$0")" || exit

#helm uninstall dev
helm dependency update helm
helm upgrade --install nyomio-app helm --set global.frontendDevMode=false
