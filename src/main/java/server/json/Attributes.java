package server.json;

public class Attributes
{
    private String port;

    private String maxPlayers;

    private String[] location;

    private String status;

    private String country;

    private String ip;

    private String id;

    private String updatedAt;

    private String queryStatus;

    private String portQuery;

    private String rank;

    private Details details;

    private String createdAt;

    private String name;

    private String players;

    public String getPort ()
    {
        return port;
    }

    public void setPort (String port)
    {
        this.port = port;
    }

    public String getMaxPlayers ()
    {
        return maxPlayers;
    }

    public void setMaxPlayers (String maxPlayers)
    {
        this.maxPlayers = maxPlayers;
    }

    public String[] getLocation ()
    {
        return location;
    }

    public void setLocation (String[] location)
    {
        this.location = location;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getIp ()
    {
        return ip;
    }

    public void setIp (String ip)
    {
        this.ip = ip;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public String getQueryStatus ()
    {
        return queryStatus;
    }

    public void setQueryStatus (String queryStatus)
    {
        this.queryStatus = queryStatus;
    }

    public String getPortQuery ()
    {
        return portQuery;
    }

    public void setPortQuery (String portQuery)
    {
        this.portQuery = portQuery;
    }

    public String getRank ()
    {
        return rank;
    }

    public void setRank (String rank)
    {
        this.rank = rank;
    }

    public Details getDetails ()
    {
        return details;
    }

    public void setDetails (Details details)
    {
        this.details = details;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getPlayers ()
    {
        return players;
    }

    public void setPlayers (String players)
    {
        this.players = players;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [port = "+port+", maxPlayers = "+maxPlayers+", location = "+location+", status = "+status+", country = "+country+", ip = "+ip+", id = "+id+", updatedAt = "+updatedAt+", queryStatus = "+queryStatus+", portQuery = "+portQuery+", rank = "+rank+", details = "+details+", createdAt = "+createdAt+", name = "+name+", players = "+players+"]";
    }
}