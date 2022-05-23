<h1>Hoax Wordle</h1>

<h2>Preparation</h2>

<h3>The JAR</h3>
1. Install Java 17 or above
2. Download the JAR (hoax-0.0.1-SNAPSHOT.jar in the root project folder)
<br> OR 
3. build it by yourself (optional)
   1. Pull this repo to your preferred location on your machine and the build maven package
```
$ cd /your/folder/path/
$ git clone https://github.com/Tech-Recruiter-Justin/hoax-wordle
$ ./mvnw clean package

======== You should see something like this ========

[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.sandboxvr.wordle.entities.GuessEngineTests
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.047 s - in com.sandboxvr.wordle.entities.GuessEngineTests
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] 
[INFO] --- maven-jar-plugin:3.2.2:jar (default-jar) @ hoax ---
[INFO] Building jar: /your/folder/path/target/hoax-0.0.1-SNAPSHOT.jar
[INFO] 
[INFO] --- spring-boot-maven-plugin:2.6.7:repackage (repackage) @ hoax ---
[INFO] Replacing main artifact with repackaged archive
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  8.307 s
[INFO] Finished at: 2022-05-24T02:06:28+08:00
[INFO] ------------------------------------------------------------------------

```

<h3>Word txt</h3>
- You may name the txt file as you like, as long as the path you provide is correct when running the application (explained in the next section)
- Two samples are already provided to you in this repo under /hoax/src/main/resources, long.txt and word.txt - you may change the content as you see fit

<h2>How to run</h2>

<h3>Through Command Line</h3>
```
$ java -jar /your/folder/path/target/hoax-0.0.1-SNAPSHOT.jar /absolute/path/to/answers.txt "\n" 5 6

###### ARGS EXPLANATION ######

ARGS[0] depends on whether you are using a Unix or Windows machine, this is the path to the txt list of possible words that you allow
Windows path example - C:\\dev\\hoax\\src\\main\\resources\\word.txt
Unix path example - /Dev/hoax/src/main/resources/word.txt

ARGS[1] the delimiter that separates each word in the txt, recommend to use "\n" (both sample files are separated by new line)

ARGS[2] the length of words in the game, normally this is set to 5 (like the real WORDLE)

ARGS[3] the maximum guesses allowed before the game ends
```

<h3>Through IDE</h3>
1. Import the project folder to your preferred IDE i.e. IntelliJ IDEA
2. Import all the Maven dependencies
3. Build and run the application directly from your IDE tools (remember to input the application arguements)