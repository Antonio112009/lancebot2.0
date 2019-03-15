package server.json;

public class Data
{
    private String id;

    private Attributes attributes;

    private String type;

    private Relationships relationships;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public Attributes getAttributes ()
    {
        return attributes;
    }

    public void setAttributes (Attributes attributes)
    {
        this.attributes = attributes;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public Relationships getRelationships ()
    {
        return relationships;
    }

    public void setRelationships (Relationships relationships)
    {
        this.relationships = relationships;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", attributes = "+attributes+", type = "+type+", relationships = "+relationships+"]";
    }
}