public class Spiel {

    private int id;
    private Team team1;
    private Team team2;

    public Spiel(int id, Team team1, Team team2) {
        this.id = id;
        this.team1 = team1;
        this.team2 = team2;
    }   

    public int getId() {
        return id;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }
}
