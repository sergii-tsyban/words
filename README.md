### BUILD AND RUN
$ mvn package

$ java -jar target/words.jar <path_to_dictionary>

### TESTING

#### for testing only native command line shell should be used

place words.jar and dictionary.txt (from resources) in the same folder

$ java -jar words.jar dictionary.txt

Loading dictionary ...

Found '279560' words in dictionary

Start word: cat

Target word: dog

{4=[cat, cot, cog, dog], 5=[cat, cot, cog, fog, dog], 6=[cat, cot, cog, fog, wog, dog]}

Exit ? (y/n) : y

