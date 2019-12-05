# Getting Started with b-synced API client

Our API client provides a programmatic way to access various functionalities of b-synced API.

## Getting Started

These instructions will get you a copy of the project up and running for testing purposes.

## Prerequisites

1. Make sure you have an valid account for using b-synced. You can self-registered via https://app.b-synced.io/signup.

2. We will perform verification regarding the authenticity of the GLN, before then, you cannot perform GDSN-related actions

3. Once everything is done, you will get a confirmation mail.So that you can activate your account by clicking confirm my account.
   
4. API Documentation ,Detailed description of all available API endpoints can be found at https://app.b-synced.io/documentation/api .

## Installing

1. Fork the following repository: [bctechnologies/b-synced-java-api-client](https://bitbucket.org/bctechnologies/b-synced-java-api-client)
    
2. Clone your resulting remote repository.
    
3. Configure the Git dependencies, i.e. adjust `b-synced-java-api-client/.git/config` as follows:
    
```
    [remote "origin"]
        url = https://<username>@bitbucket.org/bctechnologies/b-synced-java-api-client.git
        fetch = +refs/heads/*:refs/remotes/origin/*
    [remote "<username>"]
        url = https://<username>@bitbucket.org/<username>/b-synced-java-api-client.git
        fetch = +refs/heads/*:refs/remotes/<username>/*
    [branch "master"]
        remote = origin
        merge = refs/heads/master
    [branch "upstream"]
        remote = <username>
        merge = refs/heads/upstream
```
        
4.Build the API client by executing command,generates api-1.0.0.jar in target folder. 
     
```
cd b-synced-java-api-client
mvn package -Dmaven.test.skip=true
```
       
5. Copy jar file to repositories by executing command.
     
```
mvn install:install-file -Dfile=/b-synced-java-api-client/target/api-1.0.0.jar -DgroupId=de.bctechnologies -DartifactId=api -Dversion=1.0.0 -Dpackaging=jar
```

## Run Tests

To run tests for this system ,please provide **login,password and host ** as common parameters.

## Users

###### Retrieve a User

Retrieve a User with the given ID.Value of ID  can be set to myself to retrieve the current User.Login the user and retrieve the Token.

Input :

id : string (required)The ID of the desired User,ID can be set to myself to retrieve the current User.

```
mvn test -Dtest=BSyncedAPIUserTest -Duser=srividya.gangumalla@techwave.net -Dpassword=testtest -Dhost=35.227.254.108
    
```
Output: Displays the Token in console.
```
Token:   4Ix6ALrPMa_IF4rOzCwx-w

```
## Companies

###### Retrieve a Company

Retrieve a Company with the given Company ID.

Input :

id : string (required)The ID of the desired Company,ID can be set to myself to retrieve the current Company
    
```
mvn test -Dtest=BSyncedAPICompaniesTest#testGetCompanyByID -Duser=srividya.gangumalla@techwave.net -Dpassword=testtest -Dhost=35.227.254.108
```

Output: Display the Company Details.
```
 Id: 4641c9c7-0bd8-4a8f-91aa-4a9f29bbf2e8
 Name: ABC GmbH
 Address: Agrippinawerft 30, 50678 Köln
 GLN: 8380160030003

```
###### Retrieve the Name

Retrieve the Name with the given Company ID.

Input :
id : string (required)The ID of the desired Company,ID can be set to myself to retrieve the current Company


    
```
mvn test -Dtest=BSyncedAPICompaniesTest#testGetCompanyNameByID -Duser=srividya.gangumalla@techwave.net -Dpassword=testtest -Dhost=35.227.254.108
```

Output: Display the Company Details.
```
 Id: 4641c9c7-0bd8-4a8f-91aa-4a9f29bbf2e8
 Name: ABC GmbH

```

###### Retrieve the Address

Retrieve the Address with the given Company ID.

Input :
id : string (required)The ID of the desired Company,ID can be set to myself to retrieve the current Company

    
```
mvn test -Dtest=BSyncedAPICompaniesTest#testGetCompanyAddressByID -Duser=srividya.gangumalla@techwave.net -Dpassword=testtest -Dhost=35.227.254.108
```

Output: Display the Company Details.
```
 Id: 4641c9c7-0bd8-4a8f-91aa-4a9f29bbf2e8
 Address: Agrippinawerft 30, 50678 Köln

```

## GDSN Messages

###### Sending Message

Sending CIN/CIP message to b-synced API.Post GDSNMessage by providing cin/cip xml file path.

Input :

filepath: string(required)Example filepath=/home/techwave/cin.xml
    
```
mvn test -Dtest=BSyncedAPIPostMessageTest -Duser=srividya.gangumalla@techwave.net -Dpassword=testtest -Dhost=35.227.254.108 -Dfilepath=/home/techwave/cin.xml
```

Output: Display the success if the Message is created.

```
The message created successfully

```
###### Retrieve Message

Retrieve corresponding GS1Response message from b-synced API by messageid. 

Input :

id : string (required) Example: 8380160030003/cbe4427e-0878-4f8d-b5b4-4522e30c816d the id of message.

```
mvn test -Dtest=BSyncedAPIMessagesTest#testGetMessageByID -Duser=srividya.gangumalla@techwave.net -Dpassword=testtest -Dhost=35.227.254.108 -Did=8380160030003/cbe4427e-0878-4f8d-b5b4-4522e30c816d
```
Output:Displays the GS1Respone Message for the id.

```
***********GS1Respone Message ***********

<?xml version="1.0" encoding="UTF-8"?>
<catalogue_item_notification:catalogueItemNotificationMessage>
    ...
</catalogue_item_notification:catalogueItemNotificationMessage>

``` 
 
##  Retrieve Message Collection
 
Retrieve inbound/outbound messages for the company of the current user.

Input :

```
direction:
    string (required) Example: outbound
    Choices: inbound outbound 
messageType:
    string (optional) Example: catalogueItemNotification
    corresponds to the type of GDSN Messages
    Choices: catalogueItemNotification catalogueItemPublication catalogueItemSubscription 
             catalogueItemRegistrationResponse registryCatalogueItem 
             requestForCatalogueItemNotification catalogueItemConfirmation
             catalogueItemHierarchicalWithdrawal gS1Response:Exception gS1Response:Success 
page:
    number (required) Example: 1
    page number
perPage:
    number (required) Example: 10
    number of records per page
from:
    string (optional) Example: 20160915T155300Z
    the start timestamp (ISO8601 format)
to:
    string (optional) Example: 20161015T155300Z
    the end timestamp (ISO8601 format)
order:
    string (optional) Default: submitted_at Example: submitted_at
    field to be ordered
    Choices: sender receiver submitted_at 
by:
    string (optional) Default: DESC Example: DESC
    use together with order
    Choices: ASC DESC 
query:
    string (optional) Example: "ABC GmbH"
    query string for the GDSN Message content
```   


```
mvn test -Dtest=BSyncedAPIMessagesTest#testGetMessages -Duser=srividya.gangumalla@techwave.net -Dpassword=testtest -Dhost=35.227.254.108
```
Output: Displays all message details.

```
Total Messages : 8
data: [
{
id: 8380160030003/d9f4ee52-6a45-496f-b1ca-fdeb6f68b000
kind: catalogueItemPublication
sender: 8380160030003
receiver: 4049111170017
submittedAt: 2019-03-26T06:15:07.099Z
},
{
id: 8380160030003/cbe4427e-0878-4f8d-b5b4-4522e30c816d
kind: catalogueItemNotification
sender: 8380160030003
receiver: 4260590810014
submittedAt: 2019-03-22T10:27:27.029Z
}
...
]

```
Displays the message details with query contains LOGOCOS.

```
mvn test -Dtest=BSyncedAPIMessagesTest#testGetMessages -Duser=srividya.gangumalla@techwave.net -Dpassword=testtest -Dhost=35.227.254.108 -Dquery=LOGOCOS
```
Output:Display the messages which contains LOGOCOS

```
Total Messages : 1
data: [
{
id: 8380160030003/cbe4427e-0878-4f8d-b5b4-4522e30c816d
type: catalogueItemNotification
sender: 8380160030003
receiver: 4260590810014
submittedAt: 2019-03-22T10:27:27.029Z
}
]

``` 
Similar way we can provide the values for all parameters.

##  Retrieve Message Count

Retrieve the count of inbound/outbound messages for the company of the current user, If both fromTimestamp and toTimestamp are not included, the total inbound/outbound message count is returned.

Input :
```
direction
    string (required) Example: outbound
    Choices: inbound outbound 
from
    string (optional) Example: 20190915T155300Z
    the start timestamp (ISO8601 format)
to
    string (optional) Example: 20190915T155300Z
    the end timestamp (ISO8601 format)
```
    
```
mvn test -Dtest=BSyncedAPIMessageStatisticsTest -Duser=srividya.gangumalla@techwave.net -Dpassword=testtest -Dhost=35.227.254.108 -Ddirection=outbound -Dfrom=20190915T155300Z -Dto=20190915T155300Z
```

Output: Displays the messages count

```
Count: 10
```


