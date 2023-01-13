namespace lab_optional.domain;

public class ActivePlayer
{
    private String _id;
    private String _idPlayer;
    private String _idMatch;
    private int _nrPointsScored;
    private String _tip;

    public ActivePlayer(string id, string idPlayer, string idMatch, int nrPointsScored, string tip)
    {
        _id = id;
        _idPlayer = idPlayer;
        _idMatch = idMatch;
        _nrPointsScored = nrPointsScored;
        _tip = tip;
    }

    public string Id
    {
        get => _id;
        set => _id = value ?? throw new ArgumentNullException(nameof(value));
    }

    public string IdPlayer
    {
        get => _idPlayer;
        set => _idPlayer = value ?? throw new ArgumentNullException(nameof(value));
    }

    public string IdMatch
    {
        get => _idMatch;
        set => _idMatch = value ?? throw new ArgumentNullException(nameof(value));
    }

    public int NrPointsScored
    {
        get => _nrPointsScored;
        set => _nrPointsScored = value;
    }

    public string Tip
    {
        get => _tip;
        set => _tip = value ?? throw new ArgumentNullException(nameof(value));
    }
    
    public override string ToString()
    {
        return _idPlayer + ";" + _idMatch + ";" + _nrPointsScored + ";" + _tip;
    }
}