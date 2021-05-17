package setup;

public class Setup {
    public static void main(String[] args) {
        // Run all sql statements given in query.sql

        System.out.println("Setting up managers");
        new Managers(); // Setup Manager

        System.out.println("Setting up teams");
        new Teams();// Setup Teams

        System.out.println("Setting up players");
        new Players();// Setup Players

        System.out.println("Setting up league standings");
        new LeagueStandings();// Setup Standings

        System.out.println("Setting up top scorers");
        new TopScorers();// Setup TopScorers

        System.out.println("Setting up results");
        new Results();// Setup Results

        System.out.println("Setting up fixtures");
        new Fixtures();// Setup Fixtures

    }
}
