package Server;

public class Player {
    int id;

    public Player(int playerId) {
        this.id = playerId;
    }

    public String toString() {//overriding the toString() method
        return "Player " + this.id;
    }


    public int getId() {
        return id;
    }
}
