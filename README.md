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
> Important note: This sample is for vSphere clusters. If you are deploying to AWS or another cloud provider, ensure you update the StorageClass to prevent issues with unbound PVCs. 
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
- For the password, enter a password (store the password for later use). 
- Accept the terms and select `Finish with Defaults`.

8. Create a bucket in your new Couchbase cluster.

- Select `Buckets` -> `Add Bucket`.
- For `Bucket Name` enter `DrinkManager`.
- Leave the rest of the fields set to the default and select `Add Bucket`. 

9. Deploy the Spring application. 
Ensure you replace the content in the `{{ }}`, for example `COUCHBASE_PASSWORD={{insert-user-defined-password}}` would become `COUCHBASE_PASSWORD=samplecomplexpassword`.
```
oc new-app --name=spring https://github.com/michaelryanmcneill/spring-couchbase-demo.git --image-stream=ubi8-openjdk-11:1.3 -e COUCHBASE_SERVICE_NAME=db -e COUCHBASE_BUCKET=DrinkManager -e COUCHBASE_PASSWORD={{insert-user-defined-password}} -e COUCHBASE_USERNAME=Administrator
```
10. Monitor the build process to ensure it completes successfully.
```
oc logs -f buildconfig/spring
```
11. Expose the Spring application via HTTP.
```
oc expose service/spring
```

Congratulations! You have successfully deployed the Spring application on OpenShift using Couchbase. Now follow the below guide to add drink recipes to your database!

## How to Use the Application

Using Postman, CURL, or another tool, submit payloads to the API. 

### Add A Drink
POST `http://{{url.to.app}}/drinks/`
```json
{
    "id": "1",
    "name": "Old Fashioned",
    "alcohol": "bourbon",
    "ingredients": [
        "Woodford Reserve",
        "Angostura Bitters",
        "Water",
        "Orange Peel",
        "Sugar"
    ]
}
```

### Get a Drink by ID
GET `http://{{url.to.app}}/drinks/{{id}}`

### Update a Drink by ID
PUT `http://{{url.to.app}}/drinks/{{id}}`
```json
{
    "id": "1",
    "name": "Old Fashioned",
    "alcohol": "bourbon",
    "ingredients": [
        "Woodford Reserve",
        "Angostura Bitters",
        "Water",
        "Orange Peel",
        "Sugar Cube"
    ]
}
```

### Delete a Drink by ID
DELETE `http://{{url.to.app}}/drinks/{{id}}`