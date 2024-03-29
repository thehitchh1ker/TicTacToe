# tic-tac-toe
A Tic-Tac-Toe game with AI by implementing the Minimax algorithm.

## How To Play
Make sure you are in the game root directory.
Then run the following command in the terminal:

```console
java -jar tictactoe.jar
```

## Build Instructions
To build the game from source, follow these instructions:  
While in game root directory, execute the following command in terminal.

```console
javac -d class/ src/Game.java -cp src/
```

The ```-d``` flag is used to store the output to different directory. The ```-cp``` flag is used to specify class path. The code won't compile without this flag as there are multiple source files in different folder.  
Next, change to the class directory:

```console
cd class/
```

Now, just execute the game while in class directory:

```console
java Game
```

To pack all classes and resources in a single file, we have to create a jar file.
While in the class directory, execute the following in terminal:

```console
jar -cmvf tictactoe.mf ../tictactoe.jar *.class
```

This will create a jar file in the root directory of the game.
Navigate to root directory:

```console
cd ..
```

To execute the jar file, just run:

```console
java -jar tictactoe.jar
```

This jar file is portable. You can play the game just via this file.

***Enjoy!!***
