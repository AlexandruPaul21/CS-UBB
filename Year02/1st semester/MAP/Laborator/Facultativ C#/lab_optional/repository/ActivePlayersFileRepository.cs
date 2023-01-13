using lab_optional.domain;

namespace lab_optional.repository;

public class ActivePlayersFileRepository : Repository<String,ActivePlayer>
{
    private String _fileName;
    private List<ActivePlayer> _matches;

    public ActivePlayersFileRepository(string fileName)
    {
        _fileName = fileName;
        _matches = new List<ActivePlayer>();
        loadData();
    }

    private void loadData()
    {
        String[] lines = File.ReadAllLines(_fileName);
        
        foreach (string line in lines)
        {
            String[] attr = line.Split(";", 5, StringSplitOptions.RemoveEmptyEntries);
            ActivePlayer activePlayer = new ActivePlayer(attr[0], attr[1], attr[2], Convert.ToInt32(attr[3]), attr[4]);
            _matches.Add(activePlayer);
        }
    }
    
    public ActivePlayer findOne(string id)
    {
        throw new NotImplementedException();
    }

    public List<ActivePlayer> findAll()
    {
        return _matches;
    }
}