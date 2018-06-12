# advertiser-service
1- built with Spring Framework. Used h2 DB
#Following are the end Points.

1- POST /api/advertiser

Sample Request Body
  {
        "advertiserName": "GM",
        "advertiserContactName": "Shyam",
        "advertiserCreditLimit": 100
   }   

2- GET  /api/advertiser

3- GET /api/advertiser/{advertiserId}

4- PUT /api/advertiser/{advertiserId}

Sample Request Body
{
        "advertiserName": "GM",
        "advertiserContactName": "Shyam",
        "advertiserCreditLimit": 100
 }  
   
 5- DELETE /api/advertiser/{advertiserId}
 
 
 #"Build APP"
 ./gradle clean build
 
 build without test cases
 
 ./gradle clean build -x test
 
 command to run the jar
 
 java -jar advertiser-service-0.0.1-SNAPSHOT.jar
 