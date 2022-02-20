package Client;

import java.util.Scanner;

public class ClientProgram {
    public static void main(String[] args) {
        System.out.println("Welcome to the Virtual Ball Game");


        try {
            System.out.println("Enter an ID (1 - 10000) or -1 to generate a random available Id");
            Scanner in = new Scanner(System.in);
            int playerId = Integer.parseInt(in.nextLine());

            try (Client client = new Client(playerId)) {
                playerId = client.getClientId();
                System.out.println("Joined as " + playerId + "  successfully.");

                while (true) {
                    client.update();
                    System.out.println("--------------------------------");
                    System.out.println("Current Players: ");
                    for (int id : client.getListOfPlayers())
                        System.out.println(id);

                    System.out.println("Player " + client.getBallPlayerId() + " Has The Ball!");

                    if (client.getBallPlayerId() == playerId) {
                        System.out.println("You have the Ball!");

                        if (client.getListOfPlayers().size() == 1) {
                            System.out.println("Waiting for more players to join...");
                            while (client.getListOfPlayers().size() == 1) {
                                client.update();
                            }
                            client.update();
                        }
                        System.out.println("Write the id of who you want to throw the Ball to!");
                        int toPlayer = Integer.parseInt(in.nextLine());
                        client.throwBall(playerId, toPlayer);
                    } else {
                        System.out.println("You do not have the Ball!");
                        System.out.println("Waiting for Updates...");
                        while (client.getBallPlayerId() != playerId) {
                            client.update();
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}