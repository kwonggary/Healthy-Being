# Workout Web Application

---

# Set Up
Download a server and set it up for your environment 
```
brew install tomcat
```
Download mySQL and set it up for your environment.
```
brew install mysql
```
* user should be "sqluser"

* password should be "password"

* [Helpful Video for VSCode](https://www.youtube.com/watch?v=bnW1forz4Sw&t=124s)

Make sure you have Apache Maven downloaded.
```
brew install maven
```
Run the sql file in `src/sql` directory to set up your databases.

---

# Running the Application
## From an environment (Text Editor/IDE)
Start your server.

Open another terminal, and go to the project's working directory.

Compile a new war file
```
mvn clean package
```
In your project structure, open the `/target` folder

Right click `workout-webapp.war` and select `Run on Server`

After it successfully deploys, open your browser and go to 
```
localhost:8080/workout-webapp/
```

## From terminal
In terminal, go to where your server was downloaded. 

If you downloaded via Homebrew, go to your homebrew folder (we'll call it `$HOMEBREW_HOME`)

Once you're there,
```
cd Cellar/tomcat/10.1.4/bin
```

Start your server
```
./catalina start
```

Open another terminal, and go to the project's working directory.

Compile a new war file
```
mvn clean package
```
Copy the war file and manually deploy it
```
cp target/workout-webapp.war ~/$HOMEBREW_HOME/Cellar/tomcat/10.1.4/libexec/webapps
```
open your browser and go to 
```
localhost:8080/workout-webapp/
```
