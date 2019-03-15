package server.json;


public class Details
{
    private String gameMode;

    private String licensedServer;

    private String serverSteamId;

    private String numPrivConn;

    private String numPubConn;

    private String secure;

    private String map;

    private String numOpenPrivConn;

    private String version;

    public String getGameMode ()
    {
        return gameMode;
    }

    public void setGameMode (String gameMode)
    {
        this.gameMode = gameMode;
    }

    public String getLicensedServer ()
    {
        return licensedServer;
    }

    public void setLicensedServer (String licensedServer)
    {
        this.licensedServer = licensedServer;
    }

    public String getServerSteamId ()
    {
        return serverSteamId;
    }

    public void setServerSteamId (String serverSteamId)
    {
        this.serverSteamId = serverSteamId;
    }

    public String getNumPrivConn ()
    {
        return numPrivConn;
    }

    public void setNumPrivConn (String numPrivConn)
    {
        this.numPrivConn = numPrivConn;
    }

    public String getNumPubConn ()
    {
        return numPubConn;
    }

    public void setNumPubConn (String numPubConn)
    {
        this.numPubConn = numPubConn;
    }

    public String getSecure ()
    {
        return secure;
    }

    public void setSecure (String secure)
    {
        this.secure = secure;
    }

    public String getMap ()
    {
        return map;
    }

    public void setMap (String map)
    {
        this.map = map;
    }

    public String getNumOpenPrivConn ()
    {
        return numOpenPrivConn;
    }

    public void setNumOpenPrivConn (String numOpenPrivConn)
    {
        this.numOpenPrivConn = numOpenPrivConn;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [gameMode = "+gameMode+", licensedServer = "+licensedServer+", serverSteamId = "+serverSteamId+", numPrivConn = "+numPrivConn+", numPubConn = "+numPubConn+", secure = "+secure+", map = "+map+", numOpenPrivConn = "+numOpenPrivConn+", version = "+version+"]";
    }
}