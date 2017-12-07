# 273 Lab 3 - Spring x React x Dropbox

## 🚀 Client is using React.js

## 💀 Server is using Spring Framework

# Dropbox 🗳️

## How to run the application 🏃‍

### Start all three servers 🏁

1. Spring Backend Server 
```
// Start MongoDB server
$ mongod

$ cd spring_server
$ 

```

2. React.js Client Server
```
$ cd client
$ yarn install

$ yarn start
// open localhost:3001

```


## Testing 🛠️
### Kafka Backend Sever

Change the environment to test
```
// Line: 23, change this value from 'development' to 'test'
const appEnv = 'test';

// then run Kafka Backend
cd kafka-back-end/
yarn start
```

For DB provided connection pooling
```
// use this options on kafka-back-end/server.js
//   see line 24
const options = {
  useMongoClient: true,
  autoIndex: false, // Don't build indexes
  reconnectTries: Number.MAX_VALUE, // Never stop trying to reconnect
  reconnectInterval: 500, // Reconnect every 500ms
  poolSize: 10, // Maintain up to 10 socket connections
  // If not connected, return errors immediately rather than waiting for reconnect
  bufferMaxEntries: 0,
};

```

For custom connection pooling
```
// open kafka-back-end/services/files.js
//  uncomment line 5 to 8
//  and uncomment line 19-21, and use dbFile instead of File
```

### Express.js Backend Server

```
cd server/
yarn test
```



## Database Models

### User Model

| Field         | Data type     |
| --------------|:-------------:|
| _id     | ObjectId        |
| firstname     | String        |
| lastname      | String        |
| email         | String        |
| password      | String        |
| createdAt      | Date        |
| updatedAt      | Date        |

### User About Model

| Field         | Data type     |
| --------------|:-------------:|
| _id     | ObjectId        |
| overview     | String        |
| work      | String        |
| education         | String        |
| contact_info      | String        |
| life_events      | String        |
| user      | ObjectId        |
| createdAt      | Date        |
| updatedAt      | Date        |

### User Interest Model

| Field         | Data type     |
| --------------|:-------------:|
| _id     | ObjectId        |
| music     | String        |
| shows      | String        |
| sports         | String        |
| fav_teams      | String        |
| user      | ObjectId        |
| createdAt      | Date        |
| updatedAt      | Date        |


### File Model
| Field         | Data type     |
| --------------|:-------------:|
| _id     | ObjectId        |
| name     | String        |
| path      | String        |
| full_path         | String        |
| type      | String        |
| size      | Integer        |
| is_starred      | Boolean        |
| is_starred      | Boolean        |
| user      | ObjectId        |
| users      | ObjectId        |
| createdAt      | Date        |
| updatedAt      | Date        |


### Folder Model
| Field         | Data type     |
| --------------|:-------------:|
| _id     | ObjectId        |
| name     | String        |
| path      | String        |
| full_path         | String        |
| is_starred      | Boolean        |
| is_starred      | Boolean        |
| user      | ObjectId        |
| users      | Array of ObjectId        |
| createdAt      | Date        |
| updatedAt      | Date        |

### Activity Model
| Field         | Data type     |
| --------------|:-------------:|
| _id     | ObjectId        |
| action     | String        |
| description      | String        |
| user      | ObjectId        |
| createdAt      | Date        |
| updatedAt      | Date        |


### End Points

### Authorization
#### Sign Up
```
POST - localhost:3000/auth/signup
```
| Field         |      |
| --------------|:-------------:|
| firstname     | required        |
| lastname      | required        |
| email         | required, unique        |
| password      | required        |


#### Sign In
```
POST - localhost:3000/auth/signin
```
| Field         |      |
| --------------|:-------------:|
| email         | required        |
| password      | required        |

Return token, email, and _id

#### User About 
**Need user authentication**
```
GET - localhost:3000/users/about
```
```
PUT - localhost:3000/users/about
```
| Field         |      |
| --------------|:-------------:|
| overview     | optional        |
| work      | optional        |
| education         | optional        |
| contact_info     | optional        |
| life_events     | optional        |


#### User Interest
**Need user authentication**
```
GET - localhost:3000/users/interest
```
```
PUT - localhost:3000/users/interest
```
| Field         |      |
| --------------|:-------------:|
| music     | optional        |
| shows      | optional        |
| sports         | optional        |
| fav_teams     | optional        |
| life_events     | optional        |

#### User Activities
**Need user authentication**
```
GET - localhost:3000/users/activities
```

```
POST - localhost:3000/users/activities
```
| Field         |               |
| --------------|:-------------:|
| action        | required        |
| description   | required        |
| user          | required        |


### Uploading a File
**Need user authentication**
```
Upload a file - POST - localhost:3000/uploads/:currentPath

currentPath is folder id
```

Upload single file at a time.

Will upload to './public/uploads/<user@email.com>'

### Files
**Need user authentication**
```
Fetch user files - GET - localhost:3000/files/root

```

```
Star a file  - PUT - localhost:3000/files/star

```

### Folders
**Need user authentication**
```
Create new folder - POST - localhost:3000/uploads/createfolder
```

```
Fetch user files - GET - localhost:3000/folders/root

```

```
Star a file  - PUT - localhost:3000/folders/star

```



---
## Client

* React.js
* React Semantic UI
* React Redux
* React Router Dom
* Axios


