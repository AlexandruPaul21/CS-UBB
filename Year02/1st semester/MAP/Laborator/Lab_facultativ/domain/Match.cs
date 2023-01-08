namespace Lab_facultativ.domain;

public class Match
{
    private string Team1;
    private string Team2;
    private DateTime DateTime;

    public Match(string team1, string team2, DateTime dateTime)
    {
        Team1 = team1;
        Team2 = team2;
        DateTime = dateTime;
    }

    public string getTeam1()
    {
        return Team1;
    }

    public string getTeam2()
    {
        return Team2;
    }

    public DateTime getDateTime()
    {
        return DateTime;
    }

    public void setTeam1(string new_team)
    {
        Team1 = new_team;
    }
    
    public void setTeam2(string new_team)
    {
        Team2 = new_team;
    }

    public void setDateTime(DateTime dt)
    {
        DateTime = dt;
    }
}