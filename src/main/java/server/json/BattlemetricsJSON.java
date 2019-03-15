package server.json;

public class BattlemetricsJSON {
    private Data data;

    private String[] included;

    public Data getData ()
    {
        return data;
    }

    public void setData (Data data)
    {
        this.data = data;
    }

    public String[] getIncluded ()
    {
        return included;
    }

    public void setIncluded (String[] included)
    {
        this.included = included;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", included = "+included+"]";
    }
}