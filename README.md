# The Fifteen Puzzle game

First, you need to generate the puzzles. You can do so by running

```bash
java -jar puzzlegen.jar
```

⚠️ Requires JavaFX 8, fails to run on JavaFX 21.

ℹ️ This program has been provided by the author of the exercise. I do not claim any ownership over it.

This will open a GUI where you can generate puzzles. It is recommended to create a new folder, e.g. `./games`
and select it as a path in the GUI, because it will generate __copious__ amounts of files. The application should work
with any puzzle size.

You should generate a puzzle with the following parameters:

- size 4x4,
- depth: 7,

to perform analytics later.