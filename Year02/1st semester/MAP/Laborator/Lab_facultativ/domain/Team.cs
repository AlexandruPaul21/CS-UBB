namespace Lab_facultativ.domain;

public class Team : Entity<long>
{
    private string name;

    public Team(string name)
    {
        this.name = name;
    }

    public string getName()
    {
        return name;
    }

    public void setName(string newName)
    {
        name = newName;
    }
}