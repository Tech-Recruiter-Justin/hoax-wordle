# Hoax Wordle

## Introduction
This is a Wordle-like game, but NOT a normal Wordle. It will try to deceive the player, making them spend more time guessing the “correct” word. The programme has a list of words but DOES NOT have a target answer in the beginning. Everytime the player guesses a word, the program will try to give a bad answer to the player. A bad answer means it contains as few correction positions as possible, or as few correct letters as possible. Check the examples below for better understanding.

## Specification
- The program will read a text file (e.g. words.txt), containing some number of lines. Each
line is a 5-letter word.
- After reading the text file, the program will start the game, just like a normal wordle.
- The player will enter 5 letters each time, and the program will validate if the input is in the
list of words.
  - If the word is not in the list, “Unknown word” will be printed.
- The program will print different letters based on user’s input:
  - ‘X’ means Hit (letter is in the target word, and correct position)
  - ‘?’ means Near hit (letter is in the target word, but not in correct position)
  - ‘_’ means Miss (letter is not in the target word).
- The program will maintain a list of candidate of answer, while trying to provide the worst possible answer with follow priority:
  - Less “Hit” is better. 
  - If they have the same number of “Hit”, less “Near hit” is better.
- After the player guesses the whole word correctly, the program will print the number of
times they tried.
- If the player cannot guess it in 6 rounds, “The correct word is XXXXX” will be
printed. XXXXX is the finalized answer. If the program at that stage has more than 1 word, any of them is OK.

## Preparation

### The JAR
1. Install Java 17 or above
2. Git clone this repo and get the pre-built JAR (hoax-0.0.1-SNAPSHOT.jar in the root project folder)
```
$ cd /your/folder/path/
$ git clone https://github.com/Tech-Recruiter-Justin/hoax-wordle
```

### (OPTIONAL) Build the JAR by Yourself
- If you prefer building the JAR on your own machine, you may use the Maven wrapper:
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

### Word.txt
- This is the list of possible words that you allow the user to input and make guesses, the answer is also chosen from this list
- You may name the txt file as you like, as long as the path you provide is correct when running the application (explained in the next section)
- Three txt samples are already provided to you in this repo under /hoax/src/main/resources
  - empty.txt: if you want to test the empty word list exception handling
  - long.txt: all 5 character long words on the real WORDLE (you will always lose if only 6 attempts are allowed in Hoax WORDLE)
  - word.txt: the example given in the requirements

## How to Run

### Through Command Line

```
$ java -jar /your/folder/path/target/hoax-0.0.1-SNAPSHOT.jar /absolute/path/to/answers.txt "\n" 5 6

###### ARGS EXPLANATION ######

ARGS[0] depends on whether you are using a Unix or Windows machine:
Windows path example - C:\\dev\\hoax\\src\\main\\resources\\word.txt
Unix path example - /Dev/hoax/src/main/resources/word.txt

ARGS[1] the delimiter that separates each word in the txt, recommend to use "\n" (both sample files are separated by new line)

ARGS[2] the length of words in the game, normally this is set to 5 (like the real WORDLE)

ARGS[3] the maximum guesses allowed before the game ends
```

### Through IDE
1. Import the project folder to your preferred IDE i.e. IntelliJ IDEA
2. Import all the Maven dependencies
3. Build and run the application directly from your IDE tools (remember to input the application arguements)

## Considerations Included
- Checks and not allow empty txt list
- Checks and not allow invalid characters in txt (only a-z A-Z characters can pass)
- Checks word length in txt (configurable)
- Checks user input and shows 'Unknown word' whenever the format is incorrect/ when word isn't in the list provided
- Considers all user input and word list will become upper case
- Custom exceptions to remind the user to check input/ word list errors
- Unit tests for the HoaxGuessEngine
- Guess Engine in interface, allows easy swapping of implementation

## Improvements that Could Be Made
- The configs could be made through application.yml and the user experience may be even cleaner without the need to pass in application args.
- Some variables are still hardcoded i.e. the reply / the hints, these can be further refactored as configs.
- When the word list gets longer, it is impossible to beat the game.
- Explore the possibility of complex data structures to improve efficiency of finding the worst words? I thought of Trie. However, seems like in this particular case we must always go through the entire list of words to make sure there isn't a worse answer. Hence,the optimal time complexity seems to be O(number of possible words * 5) * 2 to check all HITS and Near HITS.
- More tests could be written and covering the main class and TxtAnswerReader class (however most code in it is from external Scanner/ File libraries, given the time constraints I skipped writing unit tests for those)