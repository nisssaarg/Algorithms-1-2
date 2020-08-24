import java.util.Arrays;

final class Team {
    private final String name;
    private final int wins;
    private final int loses;
    private final int remaining;
    private final int[] games;

    Team(String name, int wins, int loses, int remaining, int count) {
        this.name = name;
        this.wins = wins;
        this.loses = loses;
        this.remaining = remaining;
        this.games = new int[count];
    }

    String getName() {
        return name;
    }

    int getWins() {
        return wins;
    }

    int getLoses() {
        return loses;
    }

    int getRemaining() {
        return remaining;
    }

    void setGames(int index, int count) {
        games[index] = count;
    }

    int[] getGames() {
        return Arrays.copyOf(games, games.length);
    }
}