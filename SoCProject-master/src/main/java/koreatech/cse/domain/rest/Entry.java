package koreatech.cse.domain.rest;

public class Entry
{
    private String[] string;

    public String[] getString ()
    {
        return string;
    }

    public void setString (String[] string)
    {
        this.string = string;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [string = "+string+"]";
    }
}