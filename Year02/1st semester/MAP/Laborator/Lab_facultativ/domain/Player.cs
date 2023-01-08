namespace Lab_facultativ.domain;

public class Player : Student
{
    private string TeamId;

    public Player(string name, string schoolId, string teamId) : base(name, schoolId)
    {
        TeamId = teamId;
    }

    public string getTeamId()
    {
        return TeamId;
    }

    public void setTeamId(string id)
    {
        TeamId = id;
    }
    
}