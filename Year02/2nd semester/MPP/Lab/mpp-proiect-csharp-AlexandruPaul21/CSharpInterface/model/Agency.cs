namespace CSharpInterface.model;

public class Agency : Entity<string>
{
    public string Name { get; set; }
    public string Password { get; set; }

    public Agency(string name, string password)
    {
        Name = name;
        Password = password;
    }

    public override string ToString()
    {
        return "Name: " + Name + " Password: " + Password;
    }
}