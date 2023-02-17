package Week4.service;

import Week4.model.Match;

import java.util.ArrayList;
import java.util.Optional;

public interface MatchService {
    Match addMatch(Match match);
    void addAllMatches(ArrayList<Match> matches);
    void addWinnerIDToMatch(int matchID,int winnerID);
    Optional<Match> getMatch(int matchID);

}
