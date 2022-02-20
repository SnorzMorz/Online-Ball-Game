# Online Ball Game
A socket-based client-server system to play virtual ball. Each client application is a player. Once a player receives a ball, it prompts the user whom to pass the ball to. The clients can connect and disconnect from the server at any time.


| Function  |  |
| ----------- | ----------- |
| Client establishes a connection with the server	Yes, the client connects to the server using sockets.   | Yes, the client connects to the server using sockets.   |
| Client displays up-to-date information about the game state   | Mostly, the update() function in the Client class will let the player know when a player joins, when a player leaves or when a different player has the ball. It will also let the player know when they have the ball and give the option to throw the ball to themselves or another player. However, while the player is picking who to throw the ball to they will not get updated about players joining or leaving        |
|Client allows passing the ball to another player|Yes, on the Client side the throwBall() function sends the command when the user enters an id and on the server side the throwBall() function in the Game class changes the playerWithBall value. The player can throw the ball to  themselves, unless they are the only player in the server. If the player with the ball leaves the ball is thrown to another player (if the serve is empty, give the ball to the next player that joins)|
|Server manages multiple client connections|Yes, for each player a new ClientHandler is made on a new Thread (See RunServer()).|
|Server accepts connections during the game|Yes, new players can join any time during the game (and other players will be informed)|
|Server correctly handles clients leaving the game|Mostly, when a client leaves the other players will be informed and the ball will be given to another player if necessary (and the ball can’t be thrown to a player that has left). However, there is a bug that sometimes when the player with the ball leaves the new player with the ball won’t be informed, but this doesn’t occur always|


## Protocol

On launch the client will be given an ID, after which a new socket will be made and the ID will be sent to the server. If the user has requested a random id(by entering -1), the server will send an ID back to the client as confirmation. If the client writes the id they want, that id is sent to the server where it is checked for availability and if successful the id is returned. After successfully joining the client will now request information about the current state of the game. By sending the command PLAYERS the server will return the amount of players and all the current players in the game one by one, after this the client will request the id of the player with the ball by sending the command BALL. If the client is the one with the ball, the user will be able to write the id of who they want to throw the ball to (unless there is only one player, In which case the PLAYER command will be sent until there are at least two players.). When the ball is thrown the THROW command is called together with the throwers and throwees ids (for example THROW 7 20), in which case if successful ‘success’ is returned, otherwise ‘unsuccessful’. Now that the Player doesn’t have the ball the PLAYER AND BALL commands are called continuously for updates, until the player has the ball again. 

## Client Threads

The Client has the Main Thread that handles the all the outputs to the terminal and communicates with the server process. 
Server Threads  
The server as the client has a Main Thread that handles new connections. For each client that joins there is also a new thread that handles communication with that client and that client only. This thread is terminated when the client disconnects.


## Project review

Altogether I’m pleased with how the project has went. Having the bank account application as a baseline really helped understand how to properly use sockets in Java and how the classes should be laid out as well as how the client should interact with the server and vice versa. If I could do something differently, I would like to do more the processing on the server side instead of the client side, but with a program like this it doesn’t interfere with performance it would just make more sense in a real-world situation. Also, when coding I started with the client side first, which in hindsight wasn’t the best option, because it opened the door to a lot more bugs. The most challenging part of the project was ironing out the bugs when the main program was finished. Having to have multiple processes running has made debugging more difficult This was a lot more time consuming than expected, because the code in can be hard to understand at times and I didn’t bother adding comments. There really isn’t a specific part of the program I’m proud of but I’m proud that for the first time using sockets I’ve made a program that functions well. There are some bugs that I couldn’t iron out, for example sometimes there will be a Concurrent Modification error, but I’ve tried to minimize this by using synchronized functions and a synchronized array. 

Project SDK - jdk-17.0.1
Project Language level – SDK default
