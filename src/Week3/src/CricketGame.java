import com.fasterxml.jackson.databind.ObjectMapper;
import models.*;
import services.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CricketGame extends OutdoorGames {
    CricketTeam teamA;
    CricketTeam teamB;

    int matchID;

    int teamAScore, teamAWickets;
    int teamBScore, teamBWickets;

    Connection con;

    public CricketGame(){

    }

    public CricketGame(Connection con){
        this.con = con;
    }


    @Override
    public void play(Team battingCricketTeam, Team bowlingCricketTeam) {
        double overs = 30;

        playSession(battingCricketTeam,bowlingCricketTeam,overs,1);
        battingCricketTeam = (battingCricketTeam.equals(teamA)) ? teamB : teamA;
        bowlingCricketTeam = (bowlingCricketTeam.equals(teamA)) ? teamB : teamA;
        playSession(battingCricketTeam,bowlingCricketTeam ,overs,2);
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
    private void playSession(Team battingTeam, Team bowlingTeam, double totalOvers, int sessionNumber) {
        int totalValidBalls = 0;
        double overs = 0;
        int index = 0, bowlerIndex = 0;
        int teamScore = 0, teamWickets = 0;
        boolean maidenOverFlag = true;
        AtomicInteger totalBowlers = new AtomicInteger();

        bowlingTeam.getTeam().forEach(player ->{
            if(player.getRole().compareTo("bowler") == 0){
                totalBowlers.getAndIncrement();
            }
        });

        bowlerIndex = 11 - totalBowlers.get();


        int numberOfOutcomes = PossibleOutcomesOfBall.values().length;

        PossibleOutcomesOfBall[] possibleOutcomesOfBall = new PossibleOutcomesOfBall[numberOfOutcomes];

        Arrays.stream(PossibleOutcomesOfBall.values()).forEach(outcome -> possibleOutcomesOfBall[outcome.getIndex()] = outcome);


        HashMap<PossibleOutcomesOfBall,Probability> probabilityMapForBatsman = getProbabilityMapForBatsman();
        HashMap<PossibleOutcomesOfBall,Probability> probabilityMapForBowler = getProbabilityMapForBowler();

        int[] probabilityForBatsman = getProbabilityArray(probabilityMapForBatsman);
        int[] probabilityForBowler = getProbabilityArray(probabilityMapForBowler);


        CricketPlayer player1;
        CricketPlayer player2;

        LinkedHashMap<CricketPlayer,BatsmanStat> scoreCardOfBattingTeam = new LinkedHashMap<>();
        LinkedHashMap<CricketPlayer, BowlerStat> scoreCardOfBowlingTeam = new LinkedHashMap<>();


        player1 = (CricketPlayer) battingTeam.getTeam().get(index);
        index++;
        player2 = (CricketPlayer) battingTeam.getTeam().get(index);

        CricketPlayer playerOnStrike = player1;
        CricketPlayer currentBowler = (CricketPlayer) bowlingTeam.getTeam().get(bowlerIndex);


        BatsmanStat onStrikeBatsmanStat = new BatsmanStat(0,0,0,0);
        BatsmanStat nonStrikeBatsmanStat= new BatsmanStat(0,0,0,0);
        BowlerStat currentBowlerStat = new BowlerStat(0,0,0,0);

        BatsmanScorecardService batsmanScorecardService = new BatsmanScorecardService(con);
        BowlerScorecardService bowlerScorecardService = new BowlerScorecardService(con);

        try{
            batsmanScorecardService.addBatsmanStat(matchID,player1.getPlayerID(),onStrikeBatsmanStat);
            batsmanScorecardService.addBatsmanStat(matchID,player2.getPlayerID(),onStrikeBatsmanStat);
            bowlerScorecardService.addBowlerStat(matchID,currentBowler.getPlayerID(),currentBowlerStat);
        }
        catch (SQLException exception){
            exception.printStackTrace();
        }

        scoreCardOfBattingTeam.put(player1,onStrikeBatsmanStat);
        scoreCardOfBattingTeam.put(player2,nonStrikeBatsmanStat);
        scoreCardOfBowlingTeam.put(currentBowler,currentBowlerStat);



        System.out.println("*** " + battingTeam.getTeamName() + " goes for the batting ***\n\n");

        while(overs != totalOvers){

            int randomIdx = 0;
            int currentBallScore = 0;

            if(playerOnStrike.getRole().compareTo("batsman") == 0){
                randomIdx = getRandomIndexWithProbability(probabilityForBatsman);
            }
            else if(playerOnStrike.getRole().compareTo("bowler") == 0){
                randomIdx = getRandomIndexWithProbability(probabilityForBowler);
            }


            String ballRes = possibleOutcomesOfBall[randomIdx].getValue();

            onStrikeBatsmanStat = scoreCardOfBattingTeam.get(playerOnStrike);
            //onStrikeBatsmanStat = batsmanScorecardService.getBatsmanStat(matchID,playerOnStrike.getPlayerID());
            onStrikeBatsmanStat.setBallsPlayed(onStrikeBatsmanStat.getBallsPlayed()+1);

            currentBowlerStat = scoreCardOfBowlingTeam.get(currentBowler);
            //currentBowlerStat = bowlerScorecardService.getBowlerStat(matchID,currentBowler.getPlayerID());

            switch (ballRes){
                case "0":
                    overs += 0.1;
                    System.out.printf("%.1f  ",overs);
                    System.out.println("Ohh! A Dot ball");
                    totalValidBalls++;
                    currentBowlerStat.setNumberOfOvers(currentBowlerStat.getNumberOfOvers() + 0.1);
                    break;
                case "1":
                    overs += 0.1;
                    System.out.printf("%.1f  ",overs);
                    System.out.println("Nice drive but only got a single");
                    onStrikeBatsmanStat.setRuns(onStrikeBatsmanStat.getRuns()+1);

                    try{
                        batsmanScorecardService.updateBatsmanStat(matchID,playerOnStrike.getPlayerID(),onStrikeBatsmanStat);
                    }
                    catch (SQLException exception){
                        exception.printStackTrace();
                    }

                    scoreCardOfBattingTeam.put(playerOnStrike,onStrikeBatsmanStat);

                    playerOnStrike = (playerOnStrike.equals(player1)) ? player2 : player1;

                    onStrikeBatsmanStat = scoreCardOfBattingTeam.get(playerOnStrike);
                    currentBowlerStat.setNumberOfOvers(currentBowlerStat.getNumberOfOvers() + 0.1);

                    teamScore++;
                    currentBallScore = 1;
                    totalValidBalls++;
                    maidenOverFlag = false;
                    break;
                case "2":
                    overs += 0.1;
                    System.out.printf("%.1f  ",overs);
                    System.out.println("Nice Shot! can easily take double");
                    onStrikeBatsmanStat.setRuns(onStrikeBatsmanStat.getRuns()+2);
                    currentBowlerStat.setNumberOfOvers(currentBowlerStat.getNumberOfOvers() + 0.1);

                    teamScore += 2;
                    totalValidBalls++;
                    currentBallScore = 2;
                    maidenOverFlag = false;
                    break;
                case "3":
                    overs += 0.1;
                    System.out.printf("%.1f  ",overs);
                    System.out.println("Shoot! can it make to four, Ohh! great fielding, saves 1 run for team");
                    onStrikeBatsmanStat.setRuns(onStrikeBatsmanStat.getRuns()+3);

                    try{
                        batsmanScorecardService.updateBatsmanStat(matchID,playerOnStrike.getPlayerID(),onStrikeBatsmanStat);
                    }
                    catch (SQLException exception){
                        exception.printStackTrace();
                    }


                    scoreCardOfBattingTeam.put(playerOnStrike,onStrikeBatsmanStat);
                    currentBowlerStat.setNumberOfOvers(currentBowlerStat.getNumberOfOvers() + 0.1);

                    playerOnStrike = (playerOnStrike.equals(player1)) ? player2 : player1;

                    onStrikeBatsmanStat = scoreCardOfBattingTeam.get(playerOnStrike);

                    teamScore += 3;
                    currentBallScore = 3;
                    totalValidBalls++;
                    maidenOverFlag = false;
                    break;
                case "4":
                    overs += 0.1;
                    System.out.printf("%.1f  ",overs);
                    System.out.println("Lovely straight drive, no-one can stop this four");
                    onStrikeBatsmanStat.setNumberOfFours(onStrikeBatsmanStat.getNumberOfFours()+1);
                    onStrikeBatsmanStat.setRuns(onStrikeBatsmanStat.getRuns()+4);
                    currentBowlerStat.setNumberOfOvers(currentBowlerStat.getNumberOfOvers() + 0.1);

                    teamScore += 4;
                    currentBallScore = 4;
                    totalValidBalls++;
                    maidenOverFlag = false;
                    break;
                case "5":
                    System.out.printf("%.1f  ",overs);
                    System.out.println("Super wide, Also wicket keeper can't stop it, Free 5 runs");
                    teamScore += 5;
                    currentBallScore = 5;
                    maidenOverFlag = false;
                    break;
                case "6":
                    overs += 0.1;
                    System.out.printf("%.1f  ",overs);
                    System.out.println("Terrific shot! straight out of the ground");
                    onStrikeBatsmanStat.setNumberOfSixes(onStrikeBatsmanStat.getNumberOfSixes()+1);
                    onStrikeBatsmanStat.setRuns(onStrikeBatsmanStat.getRuns()+6);
                    currentBowlerStat.setNumberOfOvers(currentBowlerStat.getNumberOfOvers() + 0.1);

                    teamScore += 6;
                    currentBallScore = 6;
                    totalValidBalls++;
                    maidenOverFlag = false;
                    break;
                case "W":
                    overs += 0.1;
                    System.out.printf("%.1f  ",overs);
                    System.out.println("Boooowl, what a yorker, manage to displace bails");
                    teamWickets++;
                    totalValidBalls++;
                    currentBowlerStat.setNumberOfWickets(currentBowlerStat.getNumberOfWickets()+1);
                    currentBowlerStat.setNumberOfOvers(currentBowlerStat.getNumberOfOvers() + 0.1);

                    try{
                        batsmanScorecardService.updateBatsmanStat(matchID,playerOnStrike.getPlayerID(),onStrikeBatsmanStat);
                    }
                    catch (SQLException exception){
                        exception.printStackTrace();
                    }


                    scoreCardOfBattingTeam.put(playerOnStrike,onStrikeBatsmanStat);


                    if(teamWickets == 10){
                        System.out.println("And it was the last wicket, end for this session");
                        System.out.println("Total Score  " + teamScore + "/" + teamWickets);

                        try{
                            batsmanScorecardService.updateBatsmanStat(matchID,playerOnStrike.getPlayerID(),onStrikeBatsmanStat);
                            bowlerScorecardService.updateBowlerStat(matchID,currentBowler.getPlayerID(),currentBowlerStat);
                        }
                        catch (SQLException exception){
                            exception.printStackTrace();
                        }

                        scoreCardOfBattingTeam.put(playerOnStrike,onStrikeBatsmanStat);
                        scoreCardOfBowlingTeam.put(currentBowler,currentBowlerStat);

                        endSession(sessionNumber,battingTeam,bowlingTeam,teamScore,teamWickets,overs);
                        return;
                    }
                    index++;

                    if(playerOnStrike.equals(player1)){
                        player1 = (CricketPlayer) battingTeam.getTeam().get(index);
                        playerOnStrike = player1;
                    }
                    else{
                        player2 = (CricketPlayer) battingTeam.getTeam().get(index);
                        playerOnStrike = player2;
                    }


                    onStrikeBatsmanStat = new BatsmanStat(0,0,0,0);

                    try{
                        batsmanScorecardService.addBatsmanStat(matchID,playerOnStrike.getPlayerID(),onStrikeBatsmanStat);
                    }
                    catch (SQLException exception){
                        exception.printStackTrace();
                    }

                    break;
                case "NB":
                    System.out.printf("%.1f  ",overs);
                    System.out.println("What a ball but no ball :)");
                    teamScore++;
                    currentBallScore = 1;
                    maidenOverFlag = false;
                    break;
                case "WB":
                    System.out.printf("%.1f  ",overs);
                    System.out.println("Nice try to trick player but try again, Wide ball");
                    teamScore++;
                    currentBallScore = 1;
                    maidenOverFlag = false;
                    break;
                default:
                    System.out.println("Ball went to heaven or hell :)");
                    break;
            }

            try{
                batsmanScorecardService.updateBatsmanStat(matchID,playerOnStrike.getPlayerID(),onStrikeBatsmanStat);

            }
            catch (SQLException exception){
                exception.printStackTrace();
            }

            scoreCardOfBattingTeam.put(playerOnStrike,onStrikeBatsmanStat);
            currentBowlerStat.setRunsCost(currentBowlerStat.getRunsCost()+currentBallScore);

            if(battingTeam.equals(teamA) && sessionNumber == 2){
                if(teamScore > teamBScore){
                    try{
                        batsmanScorecardService.updateBatsmanStat(matchID,playerOnStrike.getPlayerID(),onStrikeBatsmanStat);
                        bowlerScorecardService.updateBowlerStat(matchID,currentBowler.getPlayerID(),currentBowlerStat);
                    }
                    catch (SQLException exception){
                        exception.printStackTrace();
                    }

                    endSession(sessionNumber,battingTeam,bowlingTeam,teamScore,teamWickets,overs);
                    return;
                }
            }
            else if(battingTeam.equals(teamB) && sessionNumber == 2){
                if(teamScore > teamAScore){
                    try{
                        batsmanScorecardService.updateBatsmanStat(matchID,playerOnStrike.getPlayerID(),onStrikeBatsmanStat);
                        bowlerScorecardService.updateBowlerStat(matchID,currentBowler.getPlayerID(),currentBowlerStat);
                    }
                    catch (SQLException exception){
                        exception.printStackTrace();
                    }

                    endSession(sessionNumber,battingTeam,bowlingTeam,teamScore,teamWickets,overs);
                    return;
                }
            }


            if(totalValidBalls == 6){
                totalValidBalls = 0;
                bowlerIndex++;

                if(bowlerIndex == 11){
                    bowlerIndex = 11 - totalBowlers.get();
                }

                int temp = (int) currentBowlerStat.getNumberOfOvers();
                int temp2 = (int) overs;
                currentBowlerStat.setNumberOfOvers(temp + 1.0);
                overs = temp2 + 1.0;


                if(maidenOverFlag){
                    currentBowlerStat.setNumberOfMaidenOvers(currentBowlerStat.getNumberOfMaidenOvers()+1);
                }

                maidenOverFlag = true;
                boolean check = playerOnStrike.equals(player1);
                playerOnStrike = (playerOnStrike.equals(player1)) ? player2 : player1;

                try{
                    bowlerScorecardService.updateBowlerStat(matchID,currentBowler.getPlayerID(),currentBowlerStat);
                }
                catch (SQLException exception){
                    exception.printStackTrace();
                }

                scoreCardOfBowlingTeam.put(currentBowler,currentBowlerStat);
                currentBowler = (CricketPlayer) bowlingTeam.getTeam().get(bowlerIndex);

                if(overs != 0 && scoreCardOfBowlingTeam.get(currentBowler) == null){
                    currentBowlerStat = new BowlerStat(0,0,0,0);
                    try{
                        bowlerScorecardService.addBowlerStat(matchID,currentBowler.getPlayerID(),currentBowlerStat);
                    }
                    catch (SQLException exception){
                        exception.printStackTrace();
                    }
                    scoreCardOfBowlingTeam.put(currentBowler,currentBowlerStat);
                }

                System.out.println("\n" + overs + " Over Complete!!!\n");

            }


        }


        endSession(sessionNumber,battingTeam,bowlingTeam,teamScore,teamWickets,overs);

    }

    private void endSession(int sessionNumber, Team battingTeam, Team bowlingTeam, int teamScore, int teamWickets, double overs){

        BatsmanScorecardService batsmanScorecardService = new BatsmanScorecardService(con);
        BowlerScorecardService bowlerScorecardService = new BowlerScorecardService(con);

        LinkedHashMap<CricketPlayer,BatsmanStat> scoreCardOfBattingTeam = batsmanScorecardService.getMapOfBatsmanStat(matchID,battingTeam.getTeamID());
        LinkedHashMap<CricketPlayer, BowlerStat> scoreCardOfBowlingTeam = bowlerScorecardService.getMapOfBowlerStat(matchID,bowlingTeam.getTeamID());



        System.out.println("*** Scorecard of team " + battingTeam.getTeamName() + " ***");
        displayBattingScoreCard(scoreCardOfBattingTeam, teamScore);
        displayBowlingScoreCard(scoreCardOfBowlingTeam);
        System.out.println("\n\n");

        System.out.print("What a session it was," + battingTeam.getTeamName() +  " manage to make " + teamScore + " At " + teamWickets +" wickets in ");
        System.out.printf("%.1f overs\n",overs);

        if(battingTeam.equals(teamA)){
            teamAScore = teamScore;
            teamAWickets = teamWickets;
        }
        else{
            teamBScore = teamScore;
            teamBWickets = teamWickets;
        }

        if(sessionNumber == 1){
            System.out.println("***** Wait for next session to start *****");
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

    private void displayBattingScoreCard(LinkedHashMap<CricketPlayer,BatsmanStat> scoreCard, int teamScore) {

        Set<CricketPlayer> keys = scoreCard.keySet();
        int totalScoreMadeByPlayers =  0, extraRun = 0;


        System.out.println('\n');
        System.out.print("Batsman");
        printOptimalSpace(30,7);
        System.out.print("R");
        printOptimalSpace(5,1);
        System.out.print("B");
        printOptimalSpace(5,1);
        System.out.print("4s");
        printOptimalSpace(5,2);
        System.out.print("6s");
        printOptimalSpace(5,2);
        System.out.print("SR");
        printOptimalSpace(5,2);
        System.out.println();
        System.out.println();

        // printing the elements of LinkedHashMap
        for (CricketPlayer player : keys) {
            double strikeRate = (scoreCard.get(player).getRuns()/(double)scoreCard.get(player).getBallsPlayed())*100;

            System.out.print(player.getName());
            printOptimalSpace(30,player.getName().length());
            System.out.print(scoreCard.get(player).getRuns());
            printOptimalSpace(5,Integer.toString(scoreCard.get(player).getRuns()).length());
            System.out.print(scoreCard.get(player).getBallsPlayed());
            printOptimalSpace(5,Integer.toString(scoreCard.get(player).getBallsPlayed()).length());
            System.out.print(scoreCard.get(player).getNumberOfFours());
            printOptimalSpace(5,Integer.toString(scoreCard.get(player).getNumberOfFours()).length());
            System.out.print(scoreCard.get(player).getNumberOfSixes());
            printOptimalSpace(5,Integer.toString(scoreCard.get(player).getNumberOfSixes()).length());
            System.out.printf("%.2f",strikeRate);
            printOptimalSpace(5,Double.toString(strikeRate).length());
            totalScoreMadeByPlayers += scoreCard.get(player).getRuns();
            System.out.println();
        }

        System.out.println("Extras -- " + Math.abs(totalScoreMadeByPlayers-teamScore));
    }

    private void displayBowlingScoreCard(LinkedHashMap<CricketPlayer,BowlerStat> scoreCard) {

        Set<CricketPlayer> keys = scoreCard.keySet();

        System.out.println('\n');
        System.out.print("Bowler");
        printOptimalSpace(30,6);
        System.out.print("O");
        printOptimalSpace(5,1);
        System.out.print("R");
        printOptimalSpace(5,1);
        System.out.print("M");
        printOptimalSpace(5,1);
        System.out.print("W");
        printOptimalSpace(5,1);
        System.out.print("ECO");
        printOptimalSpace(5,3);
        System.out.println();
        System.out.println();

        // printing the elements of LinkedHashMap
        for (CricketPlayer player : keys) {
            double economy = scoreCard.get(player).getRunsCost() / scoreCard.get(player).getNumberOfOvers();

            System.out.print(player.getName());
            printOptimalSpace(30,player.getName().length());
            System.out.printf("%.1f",scoreCard.get(player).getNumberOfOvers());
            printOptimalSpace(5,3);
            System.out.print(scoreCard.get(player).getRunsCost());
            printOptimalSpace(5,Integer.toString(scoreCard.get(player).getRunsCost()).length());
            System.out.print(scoreCard.get(player).getNumberOfMaidenOvers());
            printOptimalSpace(5,Integer.toString(scoreCard.get(player).getNumberOfMaidenOvers()).length());
            System.out.print(scoreCard.get(player).getNumberOfWickets());
            printOptimalSpace(5,Integer.toString(scoreCard.get(player).getNumberOfWickets()).length());
            System.out.printf("%.2f",economy);
            printOptimalSpace(5,Double.toString(economy).length());
            System.out.println();
        }

    }

    private void printOptimalSpace(int requireLength, int takenSize){

        for(int i=0;i<requireLength-takenSize;i++){
            System.out.print(" ");
        }
    }


    @Override
    public void rules() {
        System.out.println("No rules are defined yet");
    }

    @Override
    public void initializeGame() {

        CricketTeam cricketTeam = null;
        teamA = new CricketTeam();
        teamB = new CricketTeam();

        ArrayList<TeamGame> teams= new TeamService(con).getAllTeams();

        if(teams.size() < 2){
            System.out.println("Not enough teams available for playing cricket.");
            return;
        }

        MatchService matchService = new MatchService(con);

        Integer lastMatchID = matchService.getLastMatchID();
        if(lastMatchID == null){
            matchID = 1;
        }
        else{
            matchID = ++lastMatchID;
        }

        int randomIdxForTeamA = new Random().nextInt(teams.size());
        int randomIdxForTeamB;

        do {
            randomIdxForTeamB = new Random().nextInt(teams.size());
        } while (randomIdxForTeamA == randomIdxForTeamB);

        teamA.setTeamID(teams.get(randomIdxForTeamA).getTeamID());
        teamA.setTeamName(teams.get(randomIdxForTeamA).getTeamName());

        teamB.setTeamID(teams.get(randomIdxForTeamB).getTeamID());
        teamB.setTeamName(teams.get(randomIdxForTeamB).getTeamName());

        try{
            matchService.addMatchID(matchID);
            matchService.addTeamsIDsToMatch(matchID,teamA.getTeamID(),teamB.getTeamID());
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }

        CricketPlayerService cricketPlayerService = new CricketPlayerService(con);

        teamA.setCricketTeam(cricketPlayerService.getAllCricketPlayersByTeamID(teams.get(randomIdxForTeamA).getTeamID()));
        teamB.setCricketTeam(cricketPlayerService.getAllCricketPlayersByTeamID(teams.get(randomIdxForTeamB).getTeamID()));

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
        }
        else{
            System.out.println("Ahhh! Bad luck,you lost");
            play(teamB,teamA);
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
                play(teamA,teamB);
                break;
            case 2:
                play(teamB,teamA);
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
