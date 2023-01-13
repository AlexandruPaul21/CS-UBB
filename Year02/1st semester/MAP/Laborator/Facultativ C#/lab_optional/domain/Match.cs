namespace lab_optional.domain;

public class Match
{
    private String _id;
    private Team _team1;
    private Team _team2;
    private DateTime _date;

    public Match(String id, Team team1, Team team2, DateTime date)
    {
        _id = id;
        _team1 = team1;
        _team2 = team2;
        _date = date;
    }

    public string Id
    {
        get => _id;
        set => _id = value ?? throw new ArgumentNullException(nameof(value));
    }

    public Team Team1
    {
        get => _team1;
        set => _team1 = value ?? throw new ArgumentNullException(nameof(value));
    }

    public Team Team2
    {
        get => _team2;
        set => _team2 = value ?? throw new ArgumentNullException(nameof(value));
    }

    public DateTime Date
    {
        get => _date;
        set => _date = value;
    }

    public override string ToString()
    {
        return _team1 + ";" + _team2 + ";" + _date;
    }
}