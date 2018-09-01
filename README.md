# Setting up MongoDB w/ mLab in Java

Credit: The [original version of this tutorial](https://github.com/Figler/ucsb-cs56-mongodb-mlab-tut) is by [Chandler Forrest](https://github.com/figler), from Summer 2018).

For an introduction to Databases, and their role in webapps, see the article:

* [Webapps: Databases](/extra/databases.md)

# MongoDB on mLab

MongoDB is a particular implementation of a NoSQL database.   There are multiple hosting providers that offer MongoDB implementations "in the cloud" as a service.

For CS56 coursework, we suggest using mLab: (<https://www.mlab.com>).  We are using mLab because:

* there is a free tier
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

# Starting

If you wish to follow this tutorial completely, you should . . .

1. Fork [this demo repo](https://github.com/ucsb-cs56-pconrad/sparkjava-mongodb-mlab-tutorial)
2. Create a new Heroku project using ```heroku create <project-name>```
3. Read all of the following steps throughly, so you don't leak credentials online

# Installing MongoDB

You need the MongoDB Library installed on your computer. Follow one of these depending on which operating system you have.

- [Linux](https://docs.mongodb.com/manual/administration/install-on-linux/)
- [macOS](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-os-x/)
- [Windows](https://docs.mongodb.com/manual/tutorial/install-mongodb-on-windows/)

# MLab Account

- Create a free mLab account at [mLab.com](https://mlab.com)
- Create a new deployment with any hosting service
- Click on your new deployment
- Go the users tab and make a new user
- Take note of the user credentials your just made, your db name, and host


```
mongodb://<dbuser>:<dbpassword>@d<dbhost>/<dbname>
```
# Creating environment variables locally

Since we're going to be logging into a remote database, we have to hide our login credentials from the outside world. To do this, we're going to make a ```.env``` file inside our project directory. It should have a structure similar to this.

```
USER_=<your user's username>
PASS_=<your user's password>
DB_NAME_=<your db name>
HOST_=<your host name> // should be something with mlab in it...
```
__!!! IMPORTANT !!!__

Make sure to run the following to add ```.env``` to your .gitignore! This way our secret credentials won't be pushed into our GitHub repos.
```
echo ".env" >> .gitignore
```

# Creating config variables on heroku

1. Go to your dashboard ```https://dashboard.heroku.com/apps/<project-name>/settings```
2. Click on __Settings__
3. Click __Reveal Config Vars__ inside the __Config Vars__ menu
5. Input your key value pairs for your config variables in the same way as the environment variables

```
USER_        <username>
.
.
.
````

This will allow our live webapp to access our database on mLab.

# Additions to pom.xml

If you forked the project, you must add the following lines to your pom.xml inside the ```<dependencies>``` tag.

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

# Java code

If you take a look at ```Database.java``` you can see how we reference our MongoDB on mLab.

```Java
// Standard URI format: mongodb://[dbuser:dbpassword@]host:port/dbname
       
        String dbUser = System.getenv().get("USER_");
        String dbPassword = System.getenv().get("PASS_");
        String dbName  = System.getenv().get("DB_NAME_");
        String hostName = System.getenv().get("HOST_");

        // Good practice to always obscure sensitive login information

        String request   = "mongodb://" + dbUser + ":" + dbPassword + "@" + hostName + "/" + dbName;

        MongoClientURI uri  = new MongoClientURI(request); 
        MongoClient client = new MongoClient(uri);
        MongoDatabase db = client.getDatabase(uri.getDatabase());

```

If you use the example database setup in ```Database.java```, you should be able to see real updates to your mLab account.

To make changes to the website, have a look at the ```SparkDemo01.java``` code. There we just retreive the data from mLab and put it into some ```html``` tags.

# Running

In order for our database to hook up properly we have to run the following commands.

```
mvn clean install
heroku local        // in order to get our environment variables
```

If you want to push to a live site you would have to add and commit your code then . . .

```
git push heroku
```

You'll know you're in the clear if your live site has the same data present in the database.

# References

* Some information about Databases MongoDB from a summer Program Prof. Conrad and Prof. Diba Miza helped design for UC San Diego:
    * <https://ucsd-cse-spis-2016.github.io/webapps/databases_mongodb/>
    * <https://ucsd-cse-spis-2016.github.io/webapps/databases/>
* MongoDB Installation: <https://docs.mongodb.com/manual/installation/>
* MongoDB Examples: <https://blog.mlab.com/2011/11/ample-mongodb-examples/>