# nyomio-app
Location based resource management and BI app for enterprises. Uses nyomio-framework.

# provide custom values for helm
You can override default values by creating `ci/values_override.yml`. If the file exists
`deploy.sh` will use it automatically.
An example:
```
global:
  clusterType: minikube
  coreMicroserviceEnabled: false
  nyomIoUiEnabled: false
```
