package Server;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final Game game;

    public ClientHandler(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
    }

    @Override
    public void run() {
        int playerId = 0;
        try (
                Scanner scanner = new Scanner(socket.getInputStream());
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            try {
                playerId = Integer.parseInt(scanner.nextLine());
                if (playerId == -1) {
                    playerId = game.getNewId();
                    writer.println(playerId);
                } else {
                    if (playerId <= 10000 && playerId >= 1) {
                        for (Player player : game.getListOfPLayers()) {
                            if (player.getId() == playerId) {
                                playerId = 0;
                                throw new Exception("Already player with Id");
                            }
                        }
                        writer.println(playerId);
                    } else {
                        playerId = 0;
                        throw new Exception("ID Not allowed!");
                    }
                }



                game.addPlayer(playerId);

                System.out.println("New connection with ID: " + playerId);

                System.out.println("Player with ball: " + game.getPlayerWithBall());

                System.out.println(game.getListOfPLayers());

                while (true) {
                    String line = scanner.nextLine();
                    String[] substrings = line.split(" ");
                    switch (substrings[0].toLowerCase()) {
                        case "ball":
                            writer.println(game.getPlayerWithBall().getId());
                            break;

                        case "throw":
                            int from = Integer.parseInt(substrings[1]);
                            int to = Integer.parseInt(substrings[2]);
                            if (from != game.getPlayerWithBall().getId()) {
                                writer.println("noball");
                            } else {
                                writer.println((game.ThrowBall(from, to)));
                            }
                            break;

                        case "players":
                            List<Player> players = game.getListOfPLayers();
                            writer.println(players.size());
                            for (Player player : players) {
                                writer.println(player.getId());
                            }
                            break;
                        default:
                            throw new Exception("Unknown command: " + substrings[0]);
                    }
                }
            } catch (Exception e) {
                writer.println("ERROR " + e.getMessage());
                socket.close();
            }
        } catch (Exception e) {
        } finally {
            if(playerId != 0 ){
                System.out.println("Player " + playerId + " disconnected.");

                game.RemovePlayer(playerId);
            }
        }
    }
}