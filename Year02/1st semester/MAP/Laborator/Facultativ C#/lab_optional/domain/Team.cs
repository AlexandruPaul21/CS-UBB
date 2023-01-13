namespace lab_optional.domain;

public class Team
{
    private String _id;
    private String _name;

    public Team(string id, string name)
    {
        this._id = id;
        this._name = name;
    }

    public string Id
    {
        get => _id;
        set => _id = value ?? throw new ArgumentNullException(nameof(value));
    }

    public string Name
    {
        get => _name;
        set => _name = value ?? throw new ArgumentNullException(nameof(value));
    }

    public override string ToString()
    {
        return _id + ";" + _name;
    }
}