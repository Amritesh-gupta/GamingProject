package Week4.service.Impl;

import Week4.enums.PossibleOutcomesOfBall;
import Week4.enums.Probability;
import Week4.model.*;
import Week4.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CricketGameServiceImpl implements CricketGameService{

    Team battingTeam, teamA;
    Team bowlingTeam, teamB;

    ArrayList<CricketPlayer> team_A_Players;
    ArrayList<CricketPlayer> team_B_Players;

    int[] probabilityForBatsman;
    int[] probabilityForBowler;

    PossibleOutcomesOfBall[] possibleOutcomesOfBall;
    
    int totalOvers;

    Match match;

    private GameService gameService;
    private TeamService teamService;
    private PlayerService playerService;
    private CricketPlayerService cricketPlayerService;
    private BowlerScorecardService bowlerScorecardService;
    private BatsmanScorecardService batsmanScorecardService;
    private MatchService matchService;
    private TeamScorecardService teamScorecardService;

    private SeriesMatchesService seriesMatchService;

    public CricketGameServiceImpl(GameServiceImpl gameService, TeamServiceImpl teamService,
                                  PlayerServiceImpl playerService, CricketPlayerServiceImpl cricketPlayerService,
                                  BowlerScorecardServiceImpl bowlerScorecardService, BatsmanScorecardServiceImpl batsmanScorecardService,
                                  MatchServiceImpl matchService, TeamScorecardServiceImpl teamScorecardService,
                                  SeriesMatchServiceImpl seriesMatchService) {
        this.gameService = gameService;
        this.teamService = teamService;
        this.playerService = playerService;
        this.cricketPlayerService = cricketPlayerService;
        this.bowlerScorecardService = bowlerScorecardService;
        this.batsmanScorecardService = batsmanScorecardService;
        this.matchService = matchService;
        this.teamScorecardService = teamScorecardService;
        this.seriesMatchService = seriesMatchService;
    }


    public void startGame(int overs, Series series) {
        totalOvers = overs;
        initializeGame();


        startSession(battingTeam,bowlingTeam,1);

        Team temp = battingTeam;
        battingTeam = bowlingTeam;
        bowlingTeam = temp;

        startSession(battingTeam,bowlingTeam,2);

        if(series != null){
            SeriesMatches seriesMatches = new SeriesMatches();
            seriesMatches.setSeriesID(series);
            seriesMatches.setMatchID(match);
            seriesMatchService.addSeriesMatch(seriesMatches);
        }
    }

    public void startSession(Team battingTeam, Team bowlingTeam, int sessionNumber) {

        ArrayList<CricketPlayer> battingTeamPlayers;
        ArrayList<CricketPlayer> bowlingTeamPlayers;

        if(battingTeam.equals(teamA)){
            battingTeamPlayers = team_A_Players;
            bowlingTeamPlayers = team_B_Players;
        }
        else{
            battingTeamPlayers = team_B_Players;
            bowlingTeamPlayers = team_A_Players;
        }

        AtomicInteger totalBowlers = new AtomicInteger();

        bowlingTeamPlayers.forEach(player ->{
            if((player.getRole().compareTo("bowler")) == 0){
                totalBowlers.getAndIncrement();
            }
        });


        playSession(battingTeamPlayers,bowlingTeamPlayers,totalBowlers,sessionNumber);
    }

    public void playSession(ArrayList<CricketPlayer> battingTeamPlayers, ArrayList<CricketPlayer> bowlingTeamPlayers, AtomicInteger totalBowlers, int sessionNumber) {
        int totalValidBalls = 0;
        double overs = 0;
        int index = 0, bowlerIndex = 0;
        int teamScore = 0, teamWickets = 0;
        boolean maidenOverFlag = true;

        bowlerIndex = 11 - totalBowlers.get();

        CricketPlayer player1;
        CricketPlayer player2;

        player1 = battingTeamPlayers.get(index);
        index++;
        player2 = battingTeamPlayers.get(index);

        CricketPlayer playerOnStrike = player1;
        CricketPlayer currentBowler = bowlingTeamPlayers.get(bowlerIndex);

        BatsmanStat onStrikeBatsmanStat = new 
                BatsmanStat(playerService.getPlayerByID(player1.getPlayerID().getPlayerID()).get(),match,0,0,0,0);
        BatsmanStat nonStrikeBatsmanStat= new 
                BatsmanStat(playerService.getPlayerByID(player2.getPlayerID().getPlayerID()).get(),match,0,0,0,0);
        BowlerStat currentBowlerStat = new 
                BowlerStat(playerService.getPlayerByID(currentBowler.getPlayerID().getPlayerID()).get(),match,0,0,0,0);


        batsmanScorecardService.addBatsmanStat(onStrikeBatsmanStat);
        batsmanScorecardService.addBatsmanStat(nonStrikeBatsmanStat);
        bowlerScorecardService.addBowlerStat(currentBowlerStat);

        currentBowlerStat = bowlerScorecardService.getBowlerStat(match.getMatchID(), currentBowler.getPlayerID().getPlayerID());


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

            //onStrikeBatsmanStat = scoreCardOfBattingTeam.get(playerOnStrike);
            onStrikeBatsmanStat = batsmanScorecardService.getBatsmanStat(match.getMatchID(),playerOnStrike.getPlayerID().getPlayerID());
            onStrikeBatsmanStat.setBallsPlayed(onStrikeBatsmanStat.getBallsPlayed()+1);

            //currentBowlerStat = scoreCardOfBowlingTeam.get(currentBowler);


            HashSet<String> validBallResponses = new HashSet<>(List.of("0","1","2","3","4","6","W"));

            if(validBallResponses.contains(ballRes)){
                if(ballRes.compareTo("W") == 0){
                    teamWickets++;
                    currentBowlerStat.setNumberOfWickets(currentBowlerStat.getNumberOfWickets()+1);
                    batsmanScorecardService.updateBatsmanStat(onStrikeBatsmanStat);

                    if(teamWickets == 10){
                        bowlerScorecardService.updateBowlerStat(currentBowlerStat);
                        match.setWinnerTeamID(bowlingTeam);
                        TeamStat teamStat = new TeamStat(battingTeam,match,overs,teamScore,teamWickets);
                        teamScorecardService.addTeamStat(teamStat);
                        return;
                    }

                    index++;

                    if(playerOnStrike.equals(player1)){
                        player1 = battingTeamPlayers.get(index);
                        playerOnStrike = player1;
                    }
                    else{
                        player2 = battingTeamPlayers.get(index);
                        playerOnStrike = player2;
                    }

                    onStrikeBatsmanStat = new BatsmanStat(playerService.getPlayerByID(playerOnStrike.getPlayerID().getPlayerID()).get(),match,0,0,0,0);
                    batsmanScorecardService.addBatsmanStat(onStrikeBatsmanStat);
                }
                else{

                    if(ballRes.compareTo("1") == 0 || ballRes.compareTo("3") == 0){
                        batsmanScorecardService.updateBatsmanStat(onStrikeBatsmanStat);
                        playerOnStrike = (playerOnStrike.equals(player1)) ? player2 : player1;
                        onStrikeBatsmanStat = batsmanScorecardService.getBatsmanStat(match.getMatchID(),playerOnStrike.getPlayerID().getPlayerID());
                    }

                    if(ballRes.compareTo("4") == 0){
                        onStrikeBatsmanStat.setNumberOfFours(onStrikeBatsmanStat.getNumberOfFours()+1);
                    }
                    if(ballRes.compareTo("6") == 0){
                        onStrikeBatsmanStat.setNumberOfSixes(onStrikeBatsmanStat.getNumberOfSixes()+1);
                    }

                    currentBallScore = Integer.parseInt(ballRes);
                    onStrikeBatsmanStat.setRuns(onStrikeBatsmanStat.getRuns()+currentBallScore);
                    teamScore += currentBallScore;

                }
                overs += 0.1;
                totalValidBalls++;
                currentBowlerStat.setNumberOfOvers(currentBowlerStat.getNumberOfOvers() + 0.1);

                if(ballRes.compareTo("0") != 0 || ballRes.compareTo("W") != 0){
                    maidenOverFlag = false;
                }
            }
            else{
                if(ballRes.compareTo("5") == 0){
                    teamScore += 5;
                    currentBallScore = 5;
                }
                else{
                    teamScore++;
                    currentBallScore = 1;
                }
                maidenOverFlag = false;
            }

            batsmanScorecardService.updateBatsmanStat(onStrikeBatsmanStat);

            currentBowlerStat.setRunsCost(currentBowlerStat.getRunsCost()+currentBallScore);

            if(sessionNumber == 2){
                if(teamScore > teamScorecardService.getTeamStat(match.getMatchID(),bowlingTeam.getTeamID()).getRunsCost()){
                    batsmanScorecardService.updateBatsmanStat(onStrikeBatsmanStat);
                    bowlerScorecardService.updateBowlerStat(currentBowlerStat);
                    TeamStat teamStat = new TeamStat(battingTeam,match,overs,teamScore,teamWickets);
                    teamScorecardService.addTeamStat(teamStat);
                    match.setWinnerTeamID(battingTeam);
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

                bowlerScorecardService.updateBowlerStat(currentBowlerStat);

                currentBowler = bowlingTeamPlayers.get(bowlerIndex);

                if(overs != 0 && bowlerScorecardService.getBowlerStat(match.getMatchID(),currentBowler.getPlayerID().getPlayerID()) == null){
                    currentBowlerStat = new BowlerStat(playerService.getPlayerByID(currentBowler.getPlayerID().getPlayerID()).get(),match,0,0,0,0);
                    bowlerScorecardService.addBowlerStat(currentBowlerStat);
                }
                else{
                    currentBowlerStat = bowlerScorecardService.getBowlerStat(match.getMatchID(), currentBowler.getPlayerID().getPlayerID());
                }
            }
        }
        TeamStat teamStat = new TeamStat(battingTeam,match,totalOvers,teamScore,teamWickets);
        teamScorecardService.addTeamStat(teamStat);

        if(sessionNumber == 2){
            match.setWinnerTeamID(bowlingTeam);
        }
    }



    public void toss(Team teamA, Team teamB){

        int randomChoice = new Random().nextInt(2);

        int tossRes = new Random().nextInt(2) ;

        // Random choice for 1st bowling or batting
        // 0 - batting , 1 - bowling

        int randomPlayChoice = new Random().nextInt(2);

        if(randomChoice == tossRes){
            if(randomPlayChoice == 0){
                battingTeam = teamA;
                bowlingTeam = teamB;
            }
            else{
                battingTeam = teamB;
                bowlingTeam = teamA;
            }
        }
        else{
            if(randomPlayChoice == 0){
                battingTeam = teamB;
                bowlingTeam = teamA;
            }
            else{
                battingTeam = teamA;
                bowlingTeam = teamB;
            }
        }
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

    public int[] getProbabilityArray(HashMap<PossibleOutcomesOfBall,Probability> probabilityHashMap){
        int[] probabilityArray = new int[probabilityHashMap.size()];

        probabilityHashMap.keySet().stream().forEach(key->{
            probabilityArray[key.getIndex()] = probabilityHashMap.get(key).getValue();
        });
        return probabilityArray;
    }

    public int getRandomIndexWithProbability(int[] probabilityArr){
        int n = probabilityArr.length;
        int[] prefixArr = new int[10];
        prefixArr[0] = probabilityArr[0];

        for(int i=1;i<n;i++){
            prefixArr[i] = prefixArr[i-1] + probabilityArr[i];
        }

        int randNum = new Random().nextInt(prefixArr[n-1]+1);
        return getRandomIndex(prefixArr,randNum,0,n-1);
    }

    public int getRandomIndex(int[] prefixArr, int randNum,int l, int h){

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
    
    public void initializeGame(){

        Game game = gameService.getGameByName("Cricket");

        match = new Match();

        ArrayList<Team> teams = teamService.getAllTeamsByGameID(game.getGameID());

        match = matchService.addMatch(match);

        //Choosing two random teams out list of teams

        int randomIdxForTeamA, randomIdxForTeamB;

        randomIdxForTeamA = new Random().nextInt(teams.size());

        do {
            randomIdxForTeamB = new Random().nextInt(teams.size());
        } while (randomIdxForTeamA == randomIdxForTeamB);

        teamA = teams.get(randomIdxForTeamA);
        teamB = teams.get(randomIdxForTeamB);

        match.setTeamA_ID(teamA);
        match.setTeamB_ID(teamB);

        team_A_Players = cricketPlayerService.getAllCricketPlayersByTeamID(teamA.getTeamID());
        team_B_Players = cricketPlayerService.getAllCricketPlayersByTeamID(teamB.getTeamID());

        int numberOfOutcomes = PossibleOutcomesOfBall.values().length;

        possibleOutcomesOfBall = new PossibleOutcomesOfBall[numberOfOutcomes];

        Arrays.stream(PossibleOutcomesOfBall.values()).forEach(outcome -> possibleOutcomesOfBall[outcome.getIndex()] = outcome);


        HashMap<PossibleOutcomesOfBall,Probability> probabilityMapForBatsman = getProbabilityMapForBatsman();
        HashMap<PossibleOutcomesOfBall, Probability> probabilityMapForBowler = getProbabilityMapForBowler();

        probabilityForBatsman = getProbabilityArray(probabilityMapForBatsman);
        probabilityForBowler = getProbabilityArray(probabilityMapForBowler);

        toss(teamA,teamB);
    }
}
