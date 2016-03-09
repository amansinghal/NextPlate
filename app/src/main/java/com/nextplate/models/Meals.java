package com.nextplate.models;

/**
 * Created by AmaN on 3/6/2016.
 */
public class Meals
{
    int id;
    String name;
    int rupees;
    String description;
    Contents[] contents;
    Contents[] SUN_OPTION;
    Contents[] MON_OPTION;
    Contents[] TUE_OPTION;
    Contents[] WED_OPTION;
    Contents[] THR_OPTION;
    Contents[] FRI_OPTION;
    Contents[] SAT_OPTION;

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getRupees()
    {
        return rupees;
    }

    public void setRupees(int rupees)
    {
        this.rupees = rupees;
    }

    public Contents[] getContents()
    {
        return contents;
    }

    public void setContents(Contents[] contents)
    {
        this.contents = contents;
    }

    public Contents[] getSUN_OPTION()
    {
        return SUN_OPTION;
    }

    public void setSUN_OPTION(Contents[] SUN_OPTION)
    {
        this.SUN_OPTION = SUN_OPTION;
    }

    public Contents[] getMON_OPTION()
    {
        return MON_OPTION;
    }

    public void setMON_OPTION(Contents[] MON_OPTION)
    {
        this.MON_OPTION = MON_OPTION;
    }

    public Contents[] getTUE_OPTION()
    {
        return TUE_OPTION;
    }

    public void setTUE_OPTION(Contents[] TUE_OPTION)
    {
        this.TUE_OPTION = TUE_OPTION;
    }

    public Contents[] getWED_OPTION()
    {
        return WED_OPTION;
    }

    public void setWED_OPTION(Contents[] WED_OPTION)
    {
        this.WED_OPTION = WED_OPTION;
    }

    public Contents[] getTHR_OPTION()
    {
        return THR_OPTION;
    }

    public void setTHR_OPTION(Contents[] THR_OPTION)
    {
        this.THR_OPTION = THR_OPTION;
    }

    public Contents[] getFRI_OPTION()
    {
        return FRI_OPTION;
    }

    public void setFRI_OPTION(Contents[] FRI_OPTION)
    {
        this.FRI_OPTION = FRI_OPTION;
    }

    public Contents[] getSAT_OPTION()
    {
        return SAT_OPTION;
    }

    public void setSAT_OPTION(Contents[] SAT_OPTION)
    {
        this.SAT_OPTION = SAT_OPTION;
    }
}
