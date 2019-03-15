package server.json;

public class Relationships
{
    private Game game;

    public Game getGame ()
    {
        return game;
    }

    public void setGame (Game game)
    {
        this.game = game;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [game = "+game+"]";
    }
}
