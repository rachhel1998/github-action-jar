import * as cdk from 'aws-cdk-lib';
import { Construct } from 'constructs';
import * as dynamodb from 'aws-cdk-lib/aws-dynamodb';
import * as lambda from 'aws-cdk-lib/aws-lambda';
import * as apigateway from 'aws-cdk-lib/aws-apigateway';
import * as s3 from 'aws-cdk-lib/aws-s3';

export class FormDefinitionServiceCdkStack extends cdk.Stack {
  constructor(scope: Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);

    const table = new dynamodb.TableV2(this, 'FormDefinitionServiceId', {
      tableName : 'FormDefinitionService',
      partitionKey: { 
        name: 'orgId',
        type: dynamodb.AttributeType.STRING 
      },
      sortKey: {
        name: 'formIdVersion',
        type: dynamodb.AttributeType.STRING,
      },
      removalPolicy: cdk.RemovalPolicy.DESTROY
    });

    const s3Bucket = new s3.Bucket(this, 'FormDefinitionS3BucketId',{
      bucketName: 'form-definition-bucket'
    });

    const lambdaFunction = new lambda.Function(this, 'FormDefinitionLambdaId', {
      code: lambda.Code.fromAsset('../FormDefinitionServiceLambda/target/FormDefinitionServiceLambda-1.0-SNAPSHOT.jar'),
      handler: 'com.wazo.services.formdefinition.handler.LambdaMainHandler::handleRequest',
      runtime: lambda.Runtime.JAVA_11,
      memorySize: 2048,
      ephemeralStorageSize: cdk.Size.mebibytes(2048),
      timeout: cdk.Duration.minutes(15),
      environment: {
        TABLE_NAME: table.tableName,
        S3_BUCKET_NAME: s3Bucket.bucketName
      }
    });

    table.grantReadWriteData(lambdaFunction);
    s3Bucket.grantReadWrite(lambdaFunction);

    const api = new apigateway.RestApi(this, 'FormDefinitionServiceApigatewayId', {
      defaultCorsPreflightOptions: {
        allowOrigins: apigateway.Cors.ALL_ORIGINS,
        allowMethods: apigateway.Cors.ALL_METHODS,
      }
    });

    const integration = new apigateway.LambdaIntegration(lambdaFunction);

    const orgResource = api.root.addResource('org');

    // Add the '{orgId}' resource
    const orgIdResource = orgResource.addResource('{orgId}');

    // Add the 'form-definition-service' resource
    const formServiceResource = orgIdResource.addResource('form-definition-service');
    formServiceResource.addMethod('POST', integration);
    formServiceResource.addMethod('GET', integration);

    // Add the '{formId}' resource
    const formIdResource = formServiceResource.addResource('{formId}');
    formIdResource.addMethod('GET', integration);
    //Add the '{formVersion}' resource
    const formVersionResource = formIdResource.addResource('{formVersion}');
    formVersionResource.addMethod('GET', integration);
    formVersionResource.addMethod('PUT', integration);
    formVersionResource.addMethod('DELETE', integration);

    const formVersionCreateActiveResource = formVersionResource.addResource('active');
    formVersionCreateActiveResource.addMethod('PUT', integration);

    // https://nsdxl3teb2.execute-api.us-east-1.amazonaws.com/prod/
    // /org/{orgId}/form-definition-service
    // /org/{orgId}/form-definition-service/{formId}
    // /org/{orgId}/form-definition-service/{formId}/{formVersion}

  }
}
