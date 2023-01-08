namespace Lab_facultativ.domain;

public class Entity<T>
{
    private T Id;

    public T getId()
    {
        return Id;
    }

    public void setId(T newId)
    {
        Id = newId;
    }
}