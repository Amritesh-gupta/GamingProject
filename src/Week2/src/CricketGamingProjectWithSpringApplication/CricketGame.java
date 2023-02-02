package CricketGamingProjectWithSpringApplication;




import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;
import java.util.*;

public class CricketGame extends OutdoorGames {
    Team teamA;
    Team teamB;

    Team battingFirstTeam;

    LinkedHashMap<Player,Integer> scoreCardOfTeamA;
    LinkedHashMap<Player, Integer> scoreCardOfTeamB;

    int teamAScore, teamAWickets;
    int teamBScore, teamBWickets;

    @Override
    public void play() {
        int overs = 1;
        Team team = battingFirstTeam;

        playSession(team,overs,1);
        team = (team.equals(teamA)) ? teamB : teamA;
        playSession(team,overs,2);
    }

    int getRandomIndexWithProbability(int[] probabilityArr){
        int n = probabilityArr.length;
        int[] prefixArr = new int[10];
        prefixArr[0] = probabilityArr[0];

        for(int i=1;i<n;i++){
            prefixArr[i] = prefixArr[i-1] + probabilityArr[i];
        }

        int randNum = new Random().nextInt(prefixArr[n-1]+1);
        return getRandomIndex(prefixArr,randNum,0,n-1);
    }

    int getRandomIndex(int[] prefixArr, int randNum,int l, int h){

        while(l < h){
            int mid = l + (h-l)/2;
            if(randNum > prefixArr[mid]){
                l = mid + 1;
            }
            else{
                h = mid;
            }
        }

        return l;
    }

    private void playSession(Team team, int overs, int sessionNumber) {
        int totalBalls = overs*6;
        int index = 0;
        int teamScore = 0, teamWickets = 0;

        String[] possibleOutcomesOfBall = {"0","1","2","3","4","5","6","WB","N","W"};
        int[] probabilityForBatsman = {2,2,2,2,3,2,3,2,2,1};
        int[] probabilityForBowler = {3,1,1,1,1,1,1,2,2,3};



        Player player1;
        Player player2;
        Player temp;

        LinkedHashMap<Player,Integer> scoreCard;

        if(team.equals(teamA)){
            scoreCard = scoreCardOfTeamA;
        }
        else{
            scoreCard = scoreCardOfTeamB;
        }

        player1 = team.getTeam().get(index);
        index++;
        player2 = team.getTeam().get(index);

        Player playerOnStrike = player1;
        Player playerOnNonStrike = player2;

        scoreCard.put(playerOnStrike,0);
        scoreCard.put(playerOnNonStrike,0);

        System.out.println("*** " + team.getTeamName() + " goes for the batting ***");

        while(totalBalls > 0){

            int randomIdx = 0;

            if(playerOnStrike.getRole().compareTo("batsman") == 0){
                randomIdx = getRandomIndexWithProbability(probabilityForBatsman);
            }
            else if(playerOnStrike.getRole().compareTo("bowler") == 0){
                randomIdx = getRandomIndexWithProbability(probabilityForBowler);
            }

            String ballRes = possibleOutcomesOfBall[randomIdx];

            switch (ballRes){
                case "0":
                    System.out.println("Ohh! A Dot ball");
                    break;
                case "1":
                    System.out.println("Nice drive but only got a single");
                    scoreCard.put(playerOnStrike, scoreCard.get(playerOnStrike)+1);
                    //changeStrike(playerOnStrike,playerOnNonStrike);
                    temp  = playerOnStrike;
                    playerOnStrike = playerOnNonStrike;
                    playerOnNonStrike = temp;
                    teamScore++;
                    break;
                case "2":
                    System.out.println("Nice Shot! can easily take double");
                    scoreCard.put(playerOnStrike, scoreCard.get(playerOnStrike)+2);
                    teamScore += 2;
                    break;
                case "3":
                    System.out.println("Shoot! can it make to four, Ohh! great fielding, saves 1 run for team");
                    scoreCard.put(playerOnStrike, scoreCard.get(playerOnStrike)+3);
                    //changeStrike(playerOnStrike,playerOnNonStrike);
                    temp  = playerOnStrike;
                    playerOnStrike = playerOnNonStrike;
                    playerOnNonStrike = temp;
                    teamScore += 3;
                    break;
                case "4":
                    System.out.println("Lovely straight drive, no-one can stop this four");
                    scoreCard.put(playerOnStrike, scoreCard.get(playerOnStrike)+4);
                    teamScore += 4;
                    break;
                case "5":
                    System.out.println("Super wide, Also wicket keeper can't stop it, Free 5 runs");
                    teamScore += 5;
                    continue;
                case "6":
                    System.out.println("Terrific shot! straight out of the ground");
                    scoreCard.put(playerOnStrike, scoreCard.get(playerOnStrike)+6);
                    teamScore += 6;
                    break;
                case "W":
                    System.out.println("Boooowl, what a yorker, manage to displace bails");
                    index++;
                    teamWickets++;
                    if(teamWickets == 10){
                        System.out.println("And it was the last wicket, end for this session");
                        System.out.println("Total Score" + teamScore + "/" + teamWickets);
                        return;
                    }
                    playerOnStrike = team.getTeam().get(index);
                    scoreCard.put(playerOnStrike,0);
                    break;
                case "N":
                    System.out.println("What a ball but no ball :)");
                    teamScore++;
                    continue;
                case "WB":
                    System.out.println("Nice try to trick player but try again, Wide ball");
                    teamScore++;
                    continue;
                default:
                    System.out.println("Ball went to heaven or hell :)");
                    break;
            }
            totalBalls--;
            try{
                Thread.sleep(2000);
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }

            if(totalBalls%6 == 0){
                System.out.println((overs*6 - totalBalls)/6 + " Over Complete!!!");
                try{
                    Thread.sleep(3000);
                }
                catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("What a session it was," + team.getTeamName() +  " manage to make " + teamScore + " At " + teamWickets +" wickets\n");
        System.out.println("*** Scorecard of team " + team.getTeamName() + " ***");
        displayScoreCard(scoreCard, teamScore);
        System.out.println("\n\n");

        if(team.equals(teamA)){
            teamAScore = teamScore;
            teamAWickets = teamWickets;
        }
        else{
            teamBScore = teamScore;
            teamBWickets = teamWickets;
        }

        if(sessionNumber == 1){
            System.out.println("***** Wait for next session to start *****");
            try{
                Thread.sleep(10000);
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        else{
            System.out.println("***** Match End *****");
            Team winningTeam;

            if(teamAScore > teamBScore){
                winningTeam = teamA;
            }
            else if(teamBScore > teamAScore){
                winningTeam = teamB;
            }
            else{
                winningTeam = null;
            }

            System.out.print("And our winner is....");

            if(winningTeam == null){
                System.out.println("No-one it's a tie");
            }
            else{
                System.out.println(winningTeam.getTeamName());
            }
        }
    }

    private void displayScoreCard(LinkedHashMap<Player, Integer> scoreCard, int teamScore) {

        Set<Player> keys = scoreCard.keySet();
        int totalScoreMadeByPlayers =  0, extraRun = 0;

        // printing the elements of LinkedHashMap
        for (Player player : keys) {
            System.out.println(player.getName() + " -- "
                    + scoreCard.get(player));
            totalScoreMadeByPlayers += scoreCard.get(player);
        }

        System.out.println("Extras -- " + Math.abs(totalScoreMadeByPlayers-teamScore));
    }


    @Override
    public void rules() {
        System.out.println("No rules are defined yet");
    }

    @Override
    public void initializeGame() {

        teamA = new CricketTeam();
        teamB = new CricketTeam();
        CricketTeam cricketTeam = null;


        try{
            File file = new File("src/Week2/src/CricketGamingProjectWithSpringApplication/Players.json");
            ObjectMapper objectMapper = new ObjectMapper();
            cricketTeam = objectMapper.readValue(file,CricketTeam.class);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        teamA.makeTeamOf("India",cricketTeam);
        teamB.makeTeamOf("Pakistan",cricketTeam);


        scoreCardOfTeamA = new LinkedHashMap<>();
        scoreCardOfTeamB = new LinkedHashMap<>();

        toss();
    }

    private void toss() {
        System.out.println("***** Ready for Toss *****");
        System.out.println(" 1. Heads");
        System.out.println(" 2. Tails");
        System.out.println(" Choose your option --> ");
        Scanner scanner = new Scanner(System.in);
        int res = scanner.nextInt();


        int tossRes = new Random().nextInt(2) + 1;

        if(res == tossRes){
            System.out.println("It's a Heads, You won the toss");
            playingPreference();
            play();
        }
        else{
            System.out.println("Ahhh! Bad luck,you lost");
            battingFirstTeam = teamB;
            play();
        }


    }

    private void playingPreference() {
        System.out.println("***** What you want to do first? *****");
        System.out.println(" 1. Bat");
        System.out.println(" 2. Bowl");
        System.out.println(" Choose your option --> ");
        Scanner scanner = new Scanner(System.in);
        int res = scanner.nextInt();

        switch (res){
            case 1:
                battingFirstTeam = teamA;
                break;
            case 2:
                battingFirstTeam = teamB;
                break;
            default:
                System.out.println("Choose valid option");
                playingPreference();
                break;
        }
    }

    @Override
    public void matchLocation() {
        System.out.println("Bagmane park, Solarium city, Bangalore");
    }
}
