# Config Server

## How to Use
* Put the property file with extension `.properties`/`.yml` in the root folder
* Ensure the file name includes the application name(e.g., value of `spring.application.name`). for example, app1.
* Append profile name to the file name if necessary, for example, app1-development.yml

## Check/Access the Configuration
The HTTP service serves the resources in the following form:
```
/{application}/{profile}[/{label}]
/{application}-{profile}.yml
/{label}/{application}-{profile}.yml
/{application}-{profile}.properties
/{label}/{application}-{profile}.properties
```
for example,
```bash
curl localhost:8888/app1/development
```

## Noteworthy

### Property file with only application name will always be returned.
For example, there are 2 files in the root folder:
```bash
app1.yml
app1-development.yml
```
* When execute `curl localhost:8888/app1/default`, contents of `app1.yml` will be returned.
* When execute `curl localhost:8888/app1/development`, contents of both `app1.yml` and `app1-development.yml` will be returned.
