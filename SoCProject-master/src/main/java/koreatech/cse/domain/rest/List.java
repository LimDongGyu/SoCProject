package koreatech.cse.domain.rest;

import com.google.gson.internal.LinkedTreeMap;

public class List
{
    private LinkedTreeMap linkedTreeMap;

    public LinkedTreeMap getLinkedTreeMap ()
    {
        return linkedTreeMap;
    }

    public void setLinkedTreeMap (LinkedTreeMap linkedTreeMap)
    {
        this.linkedTreeMap = linkedTreeMap;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [com.google.gson.internal.LinkedTreeMap = "+linkedTreeMap+"]";
    }
}