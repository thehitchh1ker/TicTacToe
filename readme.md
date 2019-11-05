# tic-tac-toe
An implementation of Tic-Tac-Toe game with AI through the Minimax aglorithm.  
All source files (ie ```*.java```) are in the src folder.  
All the generated class files (ie ```*.class```) are seperately kept in the class folder.  

## Authors
* Kumar Aditya (13000117098)
* K Uday Bhargav (13000117100)
* Md. Arsalan (13000117092)
* Prabal  

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
The ```-d``` flag is used to store the output to different directory. If we don’t use this option then the class file will be created in the src directory.  
The ```-cp``` flag is used to specify class path.  The code won't compile without this flag as there are multiple source files in different folder.  
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