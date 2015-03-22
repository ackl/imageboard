#clanboard
##self-hosted invite imageboard

###Installation
#####Dependencies:
* MySQL
* Java 7+

#####Create the database
`mysql -u root -p`
`CREATE DATABASE db;`

Required tables will be created as part of the application build process.

#####Compile the application
`mvn clean package` to build the jar and clean up maven generated files/directories during build process.

Build bundled UI (optional)
`cd src/main/webapp/public/`
`npm install`
`bower update`
`gulp`


`mvn spring-boot:run` to build and run the application.


###API docs

####Get all threads in reverse chronological order.
####GET /api/threads
#####Request
`curl localhost:8080/api/threads`
#####Response
```
[
    {
        "content": "finally....................",
        "date": 1426816214066,
        "id": 22,
        "replies": [],
        "subject": "No this will be last sample",
        "user_id": 0
    },
    {
        "content": "i like cats",
        "date": 1426816214065,
        "id": 21,
        "replies": [],
        "subject": "OK i'm going to stop now",
        "user_id": 0
    },
    {
        "content": "This is getting very tedious.",
        "date": 1426816214064,
        "id": 20,
        "replies": [],
        "subject": "I can't be bothered to make any more....",
        "user_id": 0
    },
    ...
```

####Get the i'th page containing j posts
####GET /api/threads?paginate=true&page=i&perpage=j
#####Request
`curl localhost:8080/api/threads?paginate=true&page=2&perpage=2`
#####Response
```
[
    {
        "content": "This is getting very tedious.",
        "date": 1426816214064,
        "id": 20,
        "replies": [],
        "subject": "I can't be bothered to make any more....",
        "user_id": 0
    },
    {
        "content": "Its had resolving otherwise she contented therefore. Afford relied warmth out sir hearts sister use garden. Men day warmth formed admire former simple. Humanity declared vicinity continue supplied no an. He hastened am no property exercise of. Dissimilar comparison no terminated devonshire no literature on. Say most yet head room such just easy.",
        "date": 1426816214063,
        "id": 19,
        "replies": [],
        "subject": "Even more sample threads",
        "user_id": 0
    }
]
```
####Get meta information about threads in db
####GET /api/threads/meta
#####Request
`curl localhost:8080/api/threads/meta`
#####Response
```
{
    "last_active_thread_id": 22,
    "last_active_timestamp": 1426816214066,
    "thread_count": 16
}
```

####Create a new thread
####POST /api/threads
**request-body: {
    subject: *<thread_subject>*
    content: *<thread_content>*
}**

#####Request
`curl -X POST -H 'Content-type: application/json' -d '{"subject": "hello world", "content": "posting via the API"}' localhost:8080/api/threads`
#####Response
```
{
    "message": "Post created.",
    "status": 201
}
```

####Create a new reply to a thread
####POST /api/posts
**request-body: {
    content: *<post_content>*,
    parentId: *<thread_id>*
}**

#####Request
`curl -X POST -H 'Content-type: application/json' -d '{"parentId": "1", "content": "replying via the API"}' localhost:8080/api/threads`
#####Response
```
{
    "message": "Post created.",
    "status": 201
}
```

See files in notes directory for high level overview of application structure.

