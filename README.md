# API_Test

## Usage
- The server listens on port 8282
- The client listens on port 8080
- both projects have a swagger ui on the root endpoint


The insomnia file contains API calls that can be used to request endpoints from both the client and server. 
These requests are organized into two separate folders: one for the client and one for the server. 
Each API technology (REST, GraphQL, and gRPC) is represented by three endpoints provided by both the server and client:

|Method|Description|
|-----|-------------|
|Random|Returns a random beer|
|Amount|Returns the amount of beer|
|ID|Returns a beer with that id|

The data is consumed from the JavaFaker Library: https://github.com/DiUS/java-faker

### Server API
#### REST

Endpoints: 

|Endpoint|Description|
|-----|-------------|
|http://localhost:8282/beer|Returns a random beer|
|http://localhost:8282/beers/{amount}|Amount of Beers to be returned|Returns the amount of beer|
|http://localhost:8282/beer/{id}|Returns a beer with that id|

#### GraphQL
Endpoint: http://localhost:8282/graphql

Returns a random beer
```
query {
	beer{
		id
		name
		yeast
		style
		hop
		malt
	}
}
```

Amount of Beers to be returned|Returns the amount of beer
```
query {
	beerID(amount: 200){
		id
		name
		yeast
		style
		hop
		malt
	}
}
```

Returns a beer with that id
```
query {
	beers(id: 12231) {
		id
		name
		yeast
		style
		hop
		malt
	}
}
```

#### grpc

|Endpoint|Description|
|-----|-------------|
|getBeerWithId|Returns a random beer|
|getBeers|Amount of Beers to be returned|Returns the amount of beer|
|getBeerWithId|Returns a beer with that id|

### Client API

Endpoint: http://localhost:8080/{grpc|graphql|rest}

Payload:

```
{
	"method": "AMOUNT|NOTHING|ID",
	"number": <number>
}
```

|Method|Number|Description|
|------|------|-----------|
|NOTHING|-|Returns a random beer|
|Amount|Amount of Beers to be returned|Returns the amount of beer|
|ID|ID of a Beer|Returns a beer with that id|

## Architecture

The server offers information about beer through REST, GraphQL, and gRPC. The client utilizes these endpoints and records the time it takes to receive a response. 
The client's endpoints initiate a call to the server, during which the client measures the time it takes and records this information, along with the payload from the server.


## Project setup

### Run with Docker

First, you need to build the project with maven:
```
mvn clean install -P api_test
```

Then just run the docker-compose:
```
docker-compose up -d
```
