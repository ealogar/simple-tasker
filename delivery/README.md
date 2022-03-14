# Delivery on Oracle cloud (OCI)

We plan to deploy our simpler-task microservice application in oracle cloud.

Our microservice based architecture fits well in a [Kubernetes](https://kubernetes.io/es/) cluster to have all the advantadges of this open source platform to automate, scale and manage containerized applications.

Note: we'll be using The VM.Standard.A1.Flex (1 core, 6Bb memory) for deploy our kubernetes nodes and Oracle allows up to 4 machines of this type in always free-eligible mode; this sounds very interesting for small microservices architectures for development.

We based on the work described in [https://docs.oracle.com/en-us/iaas/developer-tutorials/tutorials/node-on-k8s/01oci-node-k8s-summary.htm](https://docs.oracle.com/en-us/iaas/developer-tutorials/tutorials/node-on-k8s/01oci-node-k8s-summary.htm) and [deploy-microservices-in-k8s-cluster](https://docs.oracle.com/en/solutions/deploy-microservices/index.html#GUID-3BB86E87-11C6-4DF1-8CA9-1FD385A9B9E9)


## Creating a k8s cluster in OCI

Following the guide [https://docs.oracle.com/en-us/iaas/developer-tutorials/tutorials/node-on-k8s/01oci-node-k8s-summary.htm](https://docs.oracle.com/en-us/iaas/developer-tutorials/tutorials/node-on-k8s/01oci-node-k8s-summary.htm) you create a simple k8s cluster with 3 nodes in one agent pool. Remember to choose **VM.Standard.A1.Flex** as shape for the instances; 

## Prerequites

Install [oci-cli](https://docs.oracle.com/en-us/iaas/Content/API/SDKDocs/cliinstall.htm).

Install [fn cli](https://docs.oracle.com/en-us/iaas/Content/Functions/Tasks/functionsinstallfncli.htm)

Create fn context (https://docs.oracle.com/en-us/iaas/Content/Functions/Tasks/functionscreatefncontext.htm).

Generate an auth token for login to docker registry (https://docs.oracle.com/en-us/iaas/Content/Functions/Tasks/functionsgenerateauthtokens.htm#Generate_an_Auth_Token_to_Enable_Login_to_Oracle_Cloud_Infrastructure_Registry) and keep it safe.

Install kubectl client if you dont already have it

Follow the instructions in Access YOur cluster in OCI console to get access to your k8s cluster.


## Deploy

Build the services images locally and push to the Oracle container registry

```
COMPOSE_FILE=../../docker-compose.yml docker-compose build
```

Log in to container registry:
```
docker login <region-key>.ocir.io
```
For username you should use <Object Storage Namespace>/user@email and in the password and Auth token previously generated

Tag the local images to point to registry:

```
docker tag <image-id> eu-marseille-1.ocir.io/<namespace>/simple-task/ui-tasker:latest
docker tag <image-id> eu-marseille-1.ocir.io/<namespace>/simple-task/api-rest-tasker:latest
```

Push them to the registry with ```docker push <full-image-tag>```

Create a secret for the application in k8s

```
kubectl create secret docker-registry ocirsecret --docker-server=eu-marseille-1.ocir.io  --docker-username='<namespace>/ealogar@gmail.com' --docker-password='<auth-token>'  --docker-email='ealogar@gmail.com'
```

Finally apply the kubernetes templates!

```
kubectl apply -f templates/load-balancer.yml
kubectl apply -f templates/ui-tasker.yml
kubectl apply -f templates/api-rest-tasker.yml
kubectl apply -f templates/ddbb.yml
```

## Future

We plan to use terraform to create cluster from command line, following https://github.com/oracle-quickstart/oci-arch-microservice-oke
