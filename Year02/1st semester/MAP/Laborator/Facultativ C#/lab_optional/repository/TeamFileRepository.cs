using System.Net;
using lab_optional.domain;

namespace lab_optional.repository;

public class TeamFileRepository : Repository<String, Team>
{
    private String _fileName;
    private List<Team> _teams;

    public TeamFileRepository(string fileName)
    {
        _fileName = fileName;
        _teams = new List<Team>();
        loadData();
    }

    private void loadData()
    {
        String[] lines = File.ReadAllLines(_fileName);
        
        foreach (string line in lines)
        {
            String[] attr = line.Split(";", 2, StringSplitOptions.RemoveEmptyEntries);
            Team team = new Team(attr[0], attr[1]);
            //Console.WriteLine(echipa);
            _teams.Add(team);
        }
    }
    
    public Team findOne(string id)
    {
        foreach (Team echipa in _teams)
        {
            if (echipa.Id == id)
            {
                return echipa;
            }
        }

        return null;
    }

    public List<Team> findAll()
    {
        return _teams;
    }
}