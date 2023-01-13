using System.Xml;
using lab_optional.domain;

namespace lab_optional.repository;

public class PlayersFileRepository : Repository<String, Player>
{
    private String _fileName;
    private List<Player> _players;

    public PlayersFileRepository(string fileName)
    {
        _fileName = fileName;
        _players = new List<Player>();
        loadData();
    }

    private void loadData()
    {
        String[] lines = File.ReadAllLines(_fileName);
        
        foreach (string line in lines)
        {
            String[] attr = line.Split(";", 4, StringSplitOptions.RemoveEmptyEntries);
            String echipa_id = attr[3];
            TeamFileRepository teamFileRepository =
                new TeamFileRepository(
                    "/Users/alexandrusirbu/RiderProjects/lab_optional/lab_optional/resources/teams.txt");
            Team team = teamFileRepository.findOne(echipa_id);
            Player player = new Player(attr[0], attr[1], attr[2], team);
            _players.Add(player);
        }
    }
    
    public Player findOne(string id)
    {
        foreach (Player player in _players)
        {
            if (player.Id == id)
            {
                return player;
            }
        }

        return null;
    }

    public List<Player> findAll()
    {
        return _players;
    }
}