package Client;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client implements AutoCloseable {
    final int port = 8888;

    private final Scanner reader;
    private final PrintWriter writer;

    ArrayList<Integer> players = new ArrayList<>();
    int hasBallId = 0;
    int clientId = 0;

    public Client(int playerId) throws Exception {

        Socket socket = new Socket("localhost", port);
        reader = new Scanner(socket.getInputStream());

        writer = new PrintWriter(socket.getOutputStream(), true);

        writer.println(playerId);

        String line = reader.nextLine();

        try {
            clientId = Integer.parseInt(line);
        } catch (Exception e) {
            throw new Exception(line);
        }
    }

    public ArrayList<Integer> getListOfPlayers() {
        // Sending command
        writer.println("PLAYERS");

        // Reading the number of accounts
        String line = reader.nextLine();
        int numberOfPlayers = Integer.parseInt(line);

        // Reading the account numbers
        ArrayList<Integer> players = new ArrayList(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            line = reader.nextLine();
            players.add(Integer.parseInt(line));
        }

        return players;
    }

    public void throwBall(int fromId, int toId) throws Exception {
        // Writing the command
        writer.println("THROW " + fromId + " " + toId);

        String line = reader.nextLine();

        if (line.trim().compareToIgnoreCase("success") != 0)
            System.out.println("Cant throw to Player " + toId);
        else {
            System.out.println("Ball thrown to " + toId);
        }

    }

    public int getBallPlayerId() {
        writer.println("BALL");

        String line = reader.nextLine();

        Integer id = Integer.parseInt(line);


        return id;
    }

    public boolean hasBall() {
        return hasBallId == clientId;
    }

    public void update() {
        ArrayList<Integer> updatedPlayers = getListOfPlayers();
        int newBallPlayer = getBallPlayerId();
            for (int id : updatedPlayers) {
                if (!players.contains(id)) {
                    if (id != clientId) {
                        System.out.println("Player " + id + " has joined the game!");
                    }
                }
            }
            for (int id : players) {
                if (!updatedPlayers.contains(id)) {
                    if (id != clientId) {
                        System.out.println("Player " + id + " has left the game!");
                    }
                }
            }
            if (hasBallId != newBallPlayer) {
                if (newBallPlayer != clientId && hasBallId != 0) {
                    System.out.println("Player " + newBallPlayer + " now has the ball!");
                }
            }
        hasBallId = newBallPlayer;
        players = updatedPlayers;

    }


    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }


    public int getClientId() {
        return clientId;
    }
}