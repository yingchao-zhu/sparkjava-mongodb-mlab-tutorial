---
topic: "Databases: MongoDB"
desc: "NoSQL database system"
indent: true
---

# Setting up MongoDB in Java
For an introduction to Databases, and their role in webapps, see the article:

* [Webapps: Databases](/sparkjava-01/extra/databases)

# MongoDB on mLab

MongoDB is a particular implementation of a NoSQL database.   There are multiple hosting providers that offer MongoDB implementations "in the cloud" as a service.

The particular one we'll be using for SPIS 2016 is called mLab (<https://www.mlab.com>.  We are using mLab because:

* there is free tier
* using the free tier does not require entering a credit card

# Use mLab.com *directly*, not via the Heroku mLab add-on

Note that we *NOT* using mLab MongoDB Add-On for Heroku&mdash;instead, we are using mLab directly through its own console at <https://www.mlab.com>. 

# Wait, why aren't we using the mLab add-on.

Although it is slightly more convenient to use the Heroku mLab add-on, that add-on requires entering a credit card into Heroku (even though it is free).      What using the Heroku add-on buys you is that with one click, you can:

* Automatically create the database
* Automatically fill in the five parts of the configuration info for the database directly into Heroku's configuration variables
     * For the record, those are: (host, port, database name, database username, database password) 

When you use mLab directly, you have to do those steps manually.  Fortunately, that isn't very difficult.  It's just slightly tedious, but you typically only have to do it once per application, and then you never have to worry about it again (at least not for that app.)

# As an aside, what *is* free on Heroku?

In fact, the only free services on Heroku that do not require entering a credit card are:

* Up to five running applications (but no more than that)
* The Heroku Postgres add-on (which is for an SQL-based database)
    * We certainly could use Postgres, but its a bit more complicated.
* The Heroku Connect add-on 
    (which is for integration with Salesforce.com, something not of particular interest to us in SPIS.)


# Installing MongoDB
You need the MongoDB Library installed on your computer. Follow one of these depending on which operating system you have.

- [Linux](https://docs.mongodb.com/manual/administration/install-on-linux/)
- [macOS](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/)
- [Windows](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/)

# Additions to pom.xml
If you're starting a new project, you must add the following lines to your pom.xml.
```XML
<!-- MongoDB NoSQL Database -->
 <dependencies>
    ...
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongo-java-driver</artifactId>
        <version>3.8.1</version>
    </dependency>
    ...
</dependencies>
```

# MLab Account
- Create a free mLab account at [mLab.com](https://mlab.com)
- Create a new deployment with any hosting service
- Click on your new deployment
- Go the users tab and make a new user
- Take note of the user credentials your just made, your db name, and host


```
mongodb://<dbuser>:<dbpassword>@d<dbhost>/<dbname>
```
# Creating environment variables
Since we're going to be logging into a remote database, we have to hide our login credentials from the outside world. To do this, we're going to make a ```.env``` file inside our project directory. It should have a structure similar to this.
```
USER_=<your user's username>
PASS_=<your user's password>
DB_NAME_=<your db name>
HOST_=<your host name> // should be something with mlab in it...
```
__!!! IMPORTANT !!!__

Make sure that to add the following to your .gitignore! This way our secret credentials won't be pushed into our GitHub repos.
```
.env
```

# 



# References

* <https://ucsd-cse-spis-2016.github.io/webapps/databases_mongodb/>
* <https://ucsd-cse-spis-2016.github.io/webapps/databases/>
* <https://docs.mongodb.com/manual/installation/>
* <https://mlab.com>
* <https://blog.mlab.com/2011/11/ample-mongodb-examples/>