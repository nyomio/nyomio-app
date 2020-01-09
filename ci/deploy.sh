#!/usr/bin/env bash
cd "$(dirname "$0")" || exit

paramValues=""
if [[ -a values_override.yml ]]
then
  paramValues="-f values_override.yml"
fi


#helm uninstall dev
kubectl delete configmaps --namespace=kube-system --ignore-not-found=true coredns
helm dependency update helm
helm upgrade --install nyomio-app helm ${paramValues}
