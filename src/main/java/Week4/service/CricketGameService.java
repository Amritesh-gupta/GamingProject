package Week4.service;

import Week4.enums.PossibleOutcomesOfBall;
import Week4.enums.Probability;
import Week4.model.CricketPlayer;
import Week4.model.Series;
import Week4.model.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public interface CricketGameService {
    void startGame(int overs, Series series);
    void startSession(Team battingTeam, Team bowlingTeam, int sessionNumber);
    void playSession(ArrayList<CricketPlayer> battingTeamPlayers, ArrayList<CricketPlayer> bowlingTeamPlayers, AtomicInteger totalBowlers, int sessionNumber);

    void toss(Team teamA, Team teamB);
    HashMap<PossibleOutcomesOfBall, Probability> getProbabilityMapForBatsman();
    HashMap<PossibleOutcomesOfBall,Probability> getProbabilityMapForBowler();
    int[] getProbabilityArray(HashMap<PossibleOutcomesOfBall,Probability> probabilityHashMap);
    int getRandomIndexWithProbability(int[] probabilityArr);
    int getRandomIndex(int[] prefixArr, int randNum,int l, int h);

    void initializeGame();
}
