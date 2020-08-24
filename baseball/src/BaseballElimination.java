import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

public class BaseballElimination {
    private final Team[] teams;
    private final Map<String, Integer> mapping = new HashMap<>();

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename) {
        In in = new In(filename);
        int count = in.readInt();
        teams = new Team[count];

        int j = 0;
        while (j < count) {
            Team team = new Team(in.readString(), in.readInt(), in.readInt(), in.readInt(), count);
            for (int i = 0; i < count; i++) {
                team.setGames(i, in.readInt());
            }
            teams[j] = team;
            mapping.put(team.getName(), j);
            j++;
        }
    }

    // number of teams
    public int numberOfTeams() {
        return teams.length;
    }

    // all teams
    public Iterable<String> teams() {
        return mapping.keySet();
    }

    // number of wins for given team
    public int wins(String team) {
        if (team == null || !mapping.containsKey(team)) {
            throw new IllegalArgumentException("Invalid team");
        }
        return teams[mapping.get(team)].getWins();
    }

    // number of losses for given team
    public int losses(String team) {
        if (team == null || !mapping.containsKey(team)) {
            throw new IllegalArgumentException("Invalid team");
        }
        return teams[mapping.get(team)].getLoses();
    }

    // number of remaining games for given team
    public int remaining(String team) {
        if (team == null || !mapping.containsKey(team)) {
            throw new IllegalArgumentException("Invalid team");
        }
        return teams[mapping.get(team)].getRemaining();
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2) {
        if (team1 == null || !mapping.containsKey(team1) || team2 == null || !mapping.containsKey(team2)) {
            throw new IllegalArgumentException("Invalid team");
        }
        return teams[mapping.get(team1)].getGames()[mapping.get(team2)];
    }

    // is given team eliminated?
    public boolean isEliminated(String team) {
        return certificateOfElimination(team) != null;
    }

    private int getV() {
        int n = teams.length - 1;
        return n + (n + 1) * (n + 1) + 2;
    }

    private FlowNetwork buildNetwork(Team team) {
        int v = getV();
        FlowNetwork flowNetwork = new FlowNetwork(v);

        int s = v - 2;
        int t = v - 1;
        int max = team.getWins() + team.getRemaining();
        for (int i = 0; i < teams.length; i++) {
            String teamI = teams[i].getName();
            if (teamI.equals(team.getName())) {
                continue;
            }
            for (int j = i + 1; j < teams.length; j++) {
                String teamJ = teams[j].getName();
                if (teamJ.equals(team.getName())) {
                    continue;
                }
                int gameId = teams.length + (i + 1) * (j + 1) - 2;
                flowNetwork.addEdge(new FlowEdge(s, gameId, against(teamI, teamJ)));
                flowNetwork.addEdge(new FlowEdge(gameId, i, Double.POSITIVE_INFINITY));
                flowNetwork.addEdge(new FlowEdge(gameId, j, Double.POSITIVE_INFINITY));
            }
            flowNetwork.addEdge(new FlowEdge(i, t, max - wins(teamI)));
        }
        return flowNetwork;
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team) {
        if (team == null || !mapping.containsKey(team)) {
            throw new IllegalArgumentException("Invalid team");
        }

        Team current = teams[mapping.get(team)];
        int maxWins = current.getWins() + current.getRemaining();
        for (Team t : teams) {
            if (maxWins < t.getWins()) {
                return Collections.singletonList(t.getName());
            }
        }

        FlowNetwork flowNetwork = buildNetwork(current);
        int v = getV();
        int s = v - 2;
        int t = v - 1;
        FordFulkerson fordFulkerson = new FordFulkerson(flowNetwork, s, t);
        Set<String> eliminaters = new HashSet<>();

        for (FlowEdge edge : flowNetwork.adj(s)) {
            if (edge.flow() != edge.capacity()) {
                for (FlowEdge edgeTarget : flowNetwork.adj(t)) {
                    if (fordFulkerson.inCut(edgeTarget.from())) {
                        eliminaters.add(teams[edgeTarget.from()].getName());
                    }
                }
                return eliminaters;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}