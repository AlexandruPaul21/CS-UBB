namespace model;

[Serializable]
public class Entity<TID>
{
    public TID Id { get; set; }
}