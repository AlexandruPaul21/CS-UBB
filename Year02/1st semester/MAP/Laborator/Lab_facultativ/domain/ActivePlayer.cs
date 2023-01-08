namespace Lab_facultativ.domain;

public class ActivePlayer
{
    private long IdPlayer;
    private long IdMatch;
    private int PointsScored;
    private ActivePlayer Type;

    public ActivePlayer(long idPlayer, long idMatch, int pointsScored, ActivePlayer type)
    {
        IdPlayer = idPlayer;
        IdMatch = idMatch;
        PointsScored = pointsScored;
        Type = type;
    }

    public long getIdPlayer()
    {
        return IdPlayer;
    }

    public long getIdMatch()
    {
        return IdMatch;
    }

    public int getPointsScored()
    {
        return PointsScored;
    }

    public ActivePlayer getType()
    {
        return Type;
    }
}