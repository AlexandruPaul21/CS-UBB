namespace Lab_facultativ.domain;

public class Student : Entity<long>
{
    private string Name;
    private string School;

    public Student(string name, string school)
    {
        Name = name;
        School = school;
    }

    public string getName()
    {
        return Name;
    }

    public void setName(string new_name)
    {
        Name = new_name;
    }

    public string getSchoolId()
    {
        return School;
    }

    public void setSchoolId(string id)
    {
        School = id;
    }
}