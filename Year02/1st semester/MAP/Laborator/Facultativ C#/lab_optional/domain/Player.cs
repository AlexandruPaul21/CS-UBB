namespace lab_optional.domain;

public class Player : Student
{
    private Team _team;

    public Player(string id, string name, string school) : base(id, name, school)
    {
    }


    public Player(string id, string name, string school, Team team) : base(id, name, school)
    {
        _team = team;
    }

    public Team Team
    {
        get => _team;
        set => _team = value ?? throw new ArgumentNullException(nameof(value));
    }

    public override string ToString()
    {
        return base.ToString() + ";" + _team;
    }
}