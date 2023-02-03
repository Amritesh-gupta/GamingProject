package CricketGamingProject;




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

    public HashMap<PossibleOutcomesOfBall,Probability> getProbabilityMapForBatsman(){
        HashMap<PossibleOutcomesOfBall,Probability> probabilityForBatsman = new HashMap<>();
        probabilityForBatsman.put(PossibleOutcomesOfBall.DOTBALL,Probability.MODERATE);
        probabilityForBatsman.put(PossibleOutcomesOfBall.ONERUN,Probability.MODERATE);
        probabilityForBatsman.put(PossibleOutcomesOfBall.TWORUNS,Probability.MODERATE);
        probabilityForBatsman.put(PossibleOutcomesOfBall.THREERUNS,Probability.MODERATE);
        probabilityForBatsman.put(PossibleOutcomesOfBall.FOUR,Probability.HIGH);
        probabilityForBatsman.put(PossibleOutcomesOfBall.FIVERUNS,Probability.MODERATE);
        probabilityForBatsman.put(PossibleOutcomesOfBall.SIX,Probability.MODERATE);
        probabilityForBatsman.put(PossibleOutcomesOfBall.WIDEBALL,Probability.LOW);
        probabilityForBatsman.put(PossibleOutcomesOfBall.NOBALL,Probability.LOW);
        probabilityForBatsman.put(PossibleOutcomesOfBall.WICKET,Probability.LOW);

        return probabilityForBatsman;
    }

    public HashMap<PossibleOutcomesOfBall,Probability> getProbabilityMapForBowler(){
        HashMap<PossibleOutcomesOfBall,Probability> probabilityForBatsman = new HashMap<>();
        probabilityForBatsman.put(PossibleOutcomesOfBall.DOTBALL,Probability.HIGH);
        probabilityForBatsman.put(PossibleOutcomesOfBall.ONERUN,Probability.MODERATE);
        probabilityForBatsman.put(PossibleOutcomesOfBall.TWORUNS,Probability.LOW);
        probabilityForBatsman.put(PossibleOutcomesOfBall.THREERUNS,Probability.LOW);
        probabilityForBatsman.put(PossibleOutcomesOfBall.FOUR,Probability.LOW);
        probabilityForBatsman.put(PossibleOutcomesOfBall.FIVERUNS,Probability.LOW);
        probabilityForBatsman.put(PossibleOutcomesOfBall.SIX,Probability.LOW);
        probabilityForBatsman.put(PossibleOutcomesOfBall.WIDEBALL,Probability.MODERATE);
        probabilityForBatsman.put(PossibleOutcomesOfBall.NOBALL,Probability.LOW);
        probabilityForBatsman.put(PossibleOutcomesOfBall.WICKET,Probability.HIGH);

        return probabilityForBatsman;
    }

    private int[] getProbabilityArray(HashMap<PossibleOutcomesOfBall,Probability> probabilityHashMap){
        int[] probabilityArray = new int[probabilityHashMap.size()];

        probabilityHashMap.keySet().stream().forEach(key->{
            probabilityArray[key.getIndex()] = probabilityHashMap.get(key).getValue();
        });
        return probabilityArray;
    }
    private void playSession(Team team, int overs, int sessionNumber) {
        int totalValidBalls = 0;
        int totalOvers = overs;
        int index = 0;
        int teamScore = 0, teamWickets = 0;


        int numberOfOutcomes = PossibleOutcomesOfBall.values().length;

        PossibleOutcomesOfBall[] possibleOutcomesOfBall = new PossibleOutcomesOfBall[numberOfOutcomes];

        Arrays.stream(PossibleOutcomesOfBall.values()).forEach(outcome -> possibleOutcomesOfBall[outcome.getIndex()] = outcome);


        HashMap<PossibleOutcomesOfBall,Probability> probabilityMapForBatsman = getProbabilityMapForBatsman();
        HashMap<PossibleOutcomesOfBall,Probability> probabilityMapForBowler = getProbabilityMapForBowler();

        int[] probabilityForBatsman = getProbabilityArray(probabilityMapForBatsman);
        int[] probabilityForBowler = getProbabilityArray(probabilityMapForBowler);


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

        while(overs > 0){

            int randomIdx = 0;

            if(playerOnStrike.getRole().compareTo("batsman") == 0){
                randomIdx = getRandomIndexWithProbability(probabilityForBatsman);
            }
            else if(playerOnStrike.getRole().compareTo("bowler") == 0){
                randomIdx = getRandomIndexWithProbability(probabilityForBowler);
            }

            String ballRes = possibleOutcomesOfBall[randomIdx].getValue();

            switch (ballRes){
                case "0":
                    System.out.println("Ohh! A Dot ball");
                    totalValidBalls++;
                    break;
                case "1":
                    System.out.println("Nice drive but only got a single");
                    scoreCard.put(playerOnStrike, scoreCard.get(playerOnStrike)+1);
                    //changeStrike(playerOnStrike,playerOnNonStrike);
                    temp  = playerOnStrike;
                    playerOnStrike = playerOnNonStrike;
                    playerOnNonStrike = temp;
                    teamScore++;
                    totalValidBalls++;
                    break;
                case "2":
                    System.out.println("Nice Shot! can easily take double");
                    scoreCard.put(playerOnStrike, scoreCard.get(playerOnStrike)+2);
                    teamScore += 2;
                    totalValidBalls++;
                    break;
                case "3":
                    System.out.println("Shoot! can it make to four, Ohh! great fielding, saves 1 run for team");
                    scoreCard.put(playerOnStrike, scoreCard.get(playerOnStrike)+3);
                    //changeStrike(playerOnStrike,playerOnNonStrike);
                    temp  = playerOnStrike;
                    playerOnStrike = playerOnNonStrike;
                    playerOnNonStrike = temp;
                    teamScore += 3;
                    totalValidBalls++;
                    break;
                case "4":
                    System.out.println("Lovely straight drive, no-one can stop this four");
                    scoreCard.put(playerOnStrike, scoreCard.get(playerOnStrike)+4);
                    teamScore += 4;
                    totalValidBalls++;
                    break;
                case "5":
                    System.out.println("Super wide, Also wicket keeper can't stop it, Free 5 runs");
                    teamScore += 5;
                    break;
                case "6":
                    System.out.println("Terrific shot! straight out of the ground");
                    scoreCard.put(playerOnStrike, scoreCard.get(playerOnStrike)+6);
                    teamScore += 6;
                    totalValidBalls++;
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
                    totalValidBalls++;
                    break;
                case "N":
                    System.out.println("What a ball but no ball :)");
                    teamScore++;
                    break;
                case "WB":
                    System.out.println("Nice try to trick player but try again, Wide ball");
                    teamScore++;
                    break;
                default:
                    System.out.println("Ball went to heaven or hell :)");
                    break;
            }

            try{
                Thread.sleep(2000);
            }
            catch (InterruptedException ex){
                ex.printStackTrace();
            }

            if(totalValidBalls == 6){
                overs--;
                System.out.println((totalOvers - overs) + " Over Complete!!!");
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
            File file = new File("src/Week2/src/CricketGamingProject/Players.json");
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
