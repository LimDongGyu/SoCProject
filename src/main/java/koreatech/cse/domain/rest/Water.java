package koreatech.cse.domain.rest;

public class Water
{
    private List list;

    public List getList ()
    {
        return list;
    }

    public void setList (List list)
    {
        this.list = list;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [list = "+list+"]";
    }
}