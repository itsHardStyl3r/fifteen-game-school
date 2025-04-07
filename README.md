# The Fifteen Puzzle game

Originally planned to use Java 21, but since the provided programs can't run on it, I had to downgrade to Java 8.

---

First, you need to generate the puzzles. You can do so by running

```bash
java -jar ./extras/puzzlegen.jar
```

⚠️ Requires JavaFX 8.

ℹ️ Programs puzzlegen.jar and puzzleval.jar have been provided by the author of the exercise. I do not claim any
ownership over it.

This will open a GUI where you can generate puzzles. It is recommended to create a new folder, e.g. `./games`
and select it as a path in the GUI, because it will generate __copious__ amounts of files. The application should work
with any puzzle size.

You should generate a puzzle with the following parameters:

- size 4x4,
- depth: 7,

to perform analytics later.

## Validating the output

To validate whether the puzzle was solved correctly, use

```bash
java -jar ./extras/puzzleval.jar <game_file> <solution_file>
```

⚠️ Requires JavaFX 8.

Examples:

```bash
java -jar ./extras/puzzleval.jar 4x4_01_00001.txt 4x4_01_00001_astr_hamm_sol.txt
```

```bash
java -jar ./extras/puzzleval.jar 4x4_05_00047.txt 4x4_05_00047_bfs_DRUL_sol.txt
```

```bash
java -jar ./extras/puzzleval.jar 4x4_05_00047.txt  4x4_05_00047_dfs_LUDR_sol.txt
```

There is also a script to do it in batch, but it takes notoriously long time (2+ hours) to check the solutions for the
whole precompiled games. To run it, use:

```bash
./extras/runval.sh | tee runval.txt
```

There is already pregenerated `runval.txt` file in the repo.

## Generating data for a puzzle

To generate data for a puzzle, you can run the following command:

```
java -jar <jar_file> <algorithm> <argument> <input_file> [solution_file] [stats_file]
```

where:

- `input_file` is the path to the puzzle file (e.g. `games/4x4_01_0001.txt`),
- `solution_file` is the path to the solution file (e.g. `4x4_01_0001_bfs_rdul_sol.txt`),
- `stats_file` is the path to the stats file (e.g. `4x4_01_0001_bfs_rdul_stats.txt`).

This will not output to console, but will create two additional files with information.

Supported algorithms:

* `bfs` (breadth-first search),
* `dfs` (depth-first search),
* `astr` (A-star).

Supported arguments:

* BFS & DFS: permutation of letters L, R, U, D  (e.g. `LUDR`, `RDUL`),
* A-star heuristics: `manh` (Manhattan metrics), `hamm` (Hamming metrics).

Solution file format:

* when solution is found:
    * 1st line (int): number of moves
    * 2nd line (string): moves (e.g. "LUDR")
* when solution is not found:
    * 1st line (int): -1

Stats file format:

* 1st line (int): number of moves (or -1 if not found),
* 2nd line (int): number of nodes visited,
* 3rd line (int): number of nodes processed,
* 4th line (int): maximum depth of recursion,
* 5th line (decimal #.###): time in milliseconds.

### Examples:

```bash
java -jar ./target/fifteen-puzzle-1.0.jar bfs RDUL 4x4_01_0001.txt 4x4_01_0001_bfs_rdul_sol.txt 4x4_01_0001_bfs_rdul_stats.txt
```

```bash
java -jar ./target/fifteen-puzzle-1.0.jar dfs LUDR 4x4_01_0001.txt 4x4_01_0001_dfs_ludr_sol.txt 4x4_01_0001_dfs_ludr_stats.txt
```

```bash
java -jar ./target/fifteen-puzzle-1.0.jar astr manh 4x4_01_0001.txt 4x4_01_0001_astr_manh_sol.txt 4x4_01_0001_astr_manh_stats.txt
```

## Generating data for analytics

❗ This will create A LOT of files on your hard drive. For all generated games (size 4x4, depth 7) so 413 files, it will
create additional 14868 files `((413 * 8) + (413 * 8) + (413 * 2)) * 2`. ❗

⚠️ This will take a long time to run, as it sequentially will run all algorithms with all possible configurations on all
puzzles.

To generate data for analytics, you can run the following command:

```bash
java -jar <jar_file>.jar generate <path_to_puzzles>
```

where `<path_to_puzzles>` is the path to the folder where the generated puzzles are stored. Program will override
existing data files, but won't touch the game files. All supported algorithms will be run on each puzzle, and the
results will be stored in the files, following the same naming convention as above in examples.

You could also use the provided script

```bash
./extras/runprog.sh
```

but it simply makes no sense to run it, as it will take a lot longer time to finish, compared to the command above.

Next, you need to create a summary file. To do so, run:

```bash
./extras/extdata.sh | tee summary.txt
```

and you'll end up with a new file `summary.txt` in the root folder. Keep in mind there is an already pregenerated file
currently in the repo.

### Generating plots

You need to have the `summary.txt` file present. It's best to use provided python scripts, so:

```bash
python ./extras/plot.py
```

but you need pandas, matplotlib and seaborn. You can use Java to generate the plots, too, but it looks very outdated and
was made just for fun. Anyway, just run Graph.main() if you need. Plot data was needed in polish, so expect to see it
around here.

