using lab_optional.domain;
using lab_optional.repository;

namespace lab_optional.service;

public class Service
{
    private Repository<String, Team> _teamRepository;
    private Repository<String, Player> _playerRepository;
    private Repository<String, Match> _matchRepository;
    private Repository<String, ActivePlayer> _activePlayerRepository;

    public Service(Repository<string, Team> teamRepository, Repository<String, Player> playerRepository, Repository<String, Match> matchRepository, Repository<String, ActivePlayer> activePlayerRepository)
    {
        _teamRepository = teamRepository;
        _playerRepository = playerRepository;
        _matchRepository = matchRepository;
        _activePlayerRepository = activePlayerRepository;
    }

    public List<Player> getPlayersFromTeam(String id)
    {
        List<Player> players = _playerRepository.findAll();

        IEnumerable<Player> linqPlayer =
            from player in players
            where player.Team.Id == id
            select player;

        List<Player> playersFromTeam = linqPlayer.ToList();

        return playersFromTeam;
    }
    
    public List<ActivePlayer> getActivePlayersFromMatch(String id)
    {
        List<ActivePlayer> activePlayers = _activePlayerRepository.findAll();

        IEnumerable<ActivePlayer> linqPlayers = 
            from activePlayer in activePlayers
            where activePlayer.IdMatch == id
            select activePlayer;

        List<ActivePlayer> activePlayersFromMatch = linqPlayers.ToList();

        return activePlayersFromMatch;
    }

    public Player getPlayerById(String id)
    {
        return _playerRepository.findOne(id);
    }

    public Match getMatchById(String id)
    {
        return _matchRepository.findOne(id);
    }

    public Team getTeamById(String id)
    {
        return _teamRepository.findOne(id);
    }

    public List<Match> getMatchesFromPeriod(DateTime start, DateTime finish)
    {
        List<Match> matches = _matchRepository.findAll();
        List<Match> fromPeriod = new List<Match>();
        foreach (Match match in matches)
        {
            if (match.Date >= start && match.Date <= finish)
            {
                fromPeriod.Add(match);
            }
        }

        return fromPeriod;
    }

    public List<Int32> getMatchScore(String idMatch)
    {
        Match match = getMatchById(idMatch);
        List<ActivePlayer> matchPlayers = getActivePlayersFromMatch(idMatch);
        String team1Id = match.Team1.Id;
        Int32 score1, score2;
        score1 = score2 = 0;
        foreach (ActivePlayer activePlayer in matchPlayers)
        {
            Player player = getPlayerById(activePlayer.Id);
            if (team1Id == player.Team.Id)
            {
                score1 += activePlayer.NrPointsScored;
            }
            else
            {
                score2 += activePlayer.NrPointsScored;
            }
        }

        return new List<Int32>{score1, score2};
    }
}