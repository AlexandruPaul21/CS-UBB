namespace lab_optional.domain;

public class Student
{
    private String _id;
    private String _name;
    private String _school;

    public Student(string id, string name, string school)
    {
        _id = id;
        _name = name;
        _school = school;
    }

    public string Id
    {
        get => _id;
        set => _id = value ?? throw new ArgumentNullException(nameof(value));
    }

    public string Nume
    {
        get => _name;
        set => _name = value ?? throw new ArgumentNullException(nameof(value));
    }

    public string School
    {
        get => _school;
        set => _school = value ?? throw new ArgumentNullException(nameof(value));
    }
    
    public override string ToString()
    {
        return _id + ";" + _name + ";" + _school;
    }
}