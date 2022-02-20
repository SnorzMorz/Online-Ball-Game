package Server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Game {

    List<Player> players = Collections.synchronizedList(new ArrayList<>());

    Player playerWithBall;

    List<Integer> usedIds = new ArrayList();


    public synchronized void addPlayer(int playerId) {
        Player player = new Player(playerId);
        if (players.size() == 0) {
            playerWithBall = player;
        }
        players.add(player);
    }


    public List<Player> getListOfPLayers() {
        return players;
    }

    public Player getPlayerWithBall() {
        return playerWithBall;
    }

    public synchronized int getNewId() {
        Random random = new Random();
        int min = 1;
        int max = 10000;
        int id;
        boolean unique;
        while (true) {
            id = random.nextInt(max - min) + min;
            unique = true;
            for (Player player : players) {
                if (player.getId() == id) {
                    unique = false;
                }
            }
            for(int used_id : usedIds){
                if(used_id == id){
                    unique = false;
                }
            }

            if (unique) {
                usedIds.add(id);
                return id;
            }
        }


    }

    public  synchronized void RemovePlayer(int id) {
        Player playerToRemove = new Player(101);
        for (Player player : players) {
            if (player.getId() == id) {
                playerToRemove = player;
                break;
            }
        }
        players.remove(playerToRemove);

        if (playerToRemove == playerWithBall) {
            if (players.size() >= 1) {
                playerWithBall = players.get(0);
            }
        }
    }

    public String ThrowBall(int from, int to) {
        for (Player player : players) {
            if (player.getId() == to) {
                playerWithBall = player;
                System.out.println("Player " + from + " has thrown Ball to Player " + to);
                return "success";
            }
        }

        return "unsuccessful";
    }
}
