# Code101-lambda
## Create AWS IAM ROLE
We should create IAM Role for running AWS Lambda.

```shell
aws --endpoint http://localhost:4566 --profile localstack iam create-role --role-name lambda-execution --assume-role-policy-document "{\"Version\": \"2025-02-07\", \"Statement\": [{\ "Effect\": \"Allow\", \"Principal\": \"lambda.amazonaws.com\"}, \"Action\": \"sts:AssumeRole\"}]}"
```

## Attach role for aws lambda
```shell
aws --endpoint http://localhost:4566 --profile localstack iam attach-role-policy --role-name-lambda-execution --policy-arn arn:aws:iam:policy:/service-role/AWSLambdaBasicExecutionRole 
```

## Deploy AWS Lambda
We should go to project path and run maven command in order to build project.
```shell
mvn clean install
```
Navigate to target path (this directory was created after build by maven) and get jar filename.
For example: HelloWorld-1.0.jar

```shell
aws --endpoint http://localhost:4566 --profile localstack lambda create-function --function-name HelloWorld --zip-file fileb:/HelloWorld-1.0.jar --handler helloworld.App --runtime java21 --role arn:aws:iam::000000000000:role/lambda-execution
```

## Run AWS Lambda 
```shell
aws --endpoint http://localhost:4566 --profile localstack lambda invoke --function-name HelloWorld out.txt --log-type Tail
```
