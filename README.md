phpBB forum checker
===================

A Simple Java based forum checker for phpBB (Tested on V2)

Currently under development. It was created as a way to poll a forum Topic to check for new updates. (Rather than recieve emails for all topics)

It is designed to be run as a CRON task taking in a config file path as it's only argument. (See below for config file example)


Config File
==================
NOTE parts of this config file don't actually do anything yet. These are
# alerts:allType
# alerts:replies
# mailTo:body
# storage:maxTopics

The config file is a JSON file as described below:


```json
{
    "alerts": {
	"active" : true,
        "type": "one",
        "allType": "separate",
        "replies": false
    },
    "debug": {
        "active": true,
        "clearList": true,
        "dumpFileBase": "/tmp",
	"dumpFileName": "forumPage"
    },
    "forum": {
        "baseURL": "http://www.someForum.com/forum",
        "pageID": 24
    },
    "mailFrom": {
        "address": "<username>@gmail.com",
	"name" : "Flight Alert",
        "port": 465,
        "host": "smtp.gmail.com",
        "username": "<username>@gmail.com",
        "password": "<gmail password>"
    },
    "mailTo": {
	"addresses" : [
		"<username>@gmail.com"
	],
        "subject": "New Forum Post : ",
        "body": "<html><body><h1>hello world</h1></body></html>"
    },
    "storage": {
        "redisKey": "forumTopics",
        "client" : "127.0.0.1",
        "maxTopics": 25
    }
}

```

Note that dumpFileName is appended with ".html"
PageId should be the pageID specified in the page's query string e.g. http://www.someForum.com/forum/viewforum.php?f=24 pageID would be 24
(You SHOULD NOT need to specify viewforum.php in the baseURL)


TODO
==================
# use mustache to render the body & subject
# concat multiple topics into 1 message
# allow/disable reply notifications
# clear out old topics using maxTopics (this may change to a date)


Dependencies
==================
# redis

NOTE I'll package up a JAR file with project dependancies such as JavaMail and Jedis once I get through the TODO list above
