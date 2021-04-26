# spring-couchbase-demo
Drink Recipe Manager demo app using Spring and Couchbase

## How to Deploy on OpenShift
1. Create a new project. In my example, I'm using `spring-couchbase-demo`.
```
oc new-project spring-couchbase-demo
```

2. Deploy the Couchbase database. 
```
oc new-app --name=db --docker-image=registry.connect.redhat.com/couchbase/server:6.6.2
```
> Note: You will need to have a pull secret properly configured to pull the Couchbase image. For more information on how to properly configure your pull secret, see [this link](https://catalog.redhat.com/software/containers/couchbase/server/59f051fa2937384ff320d995?container-tabs=gti).
3. Create a route to the Couchbase web service.
```
oc expose svc db --port=8091
```
4. Create a persistent volume claim for your database storage. I have included a sample YAML file for you to use.
```
oc create -f examples/db-pvc.yaml
```
5. Change your application update strategy from rolling to recreate, to prevent issues when you add your persistent storage.
```
oc patch deployments/db --patch "$(cat examples/db-patch-recreate.yaml)"
```
6. Change your Couchbase to use the persistent volume claim that you created. 
```
oc patch deployments/db --patch "$(cat examples/db-patch-volume.yaml)"
```
7. Login to the Couchbase database UI and configure your Couchbase cluster.

- Select `Setup New Cluster`.
- For `Cluster Name` enter `Demo`.
- For `Create Admin Username` leave the default `Administrator`.
- For the password, enter `password`. 
- Accept the terms and select `Finish with Defaults`.

8. Create a bucket in your new Couchbase cluster.

- Select `Buckets` -> `Add Bucket`.
- For `Bucket Name` enter `DrinkManager`.
- Leave the rest of the fields set to the default and select `Add Bucket`. 

9. Deploy the Spring application. 
```
oc new-app --name=spring https://github.com/michaelryanmcneill/spring-couchbase-demo
```