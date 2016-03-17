package com.nextplate.models;

/**
 * Created by AmaN on 3/6/2016.
 */
public class Meals
{
    int id;
    String name;
    int rupees;
    int packagePrice = 0;
    int packageDays=0;
    String description;
    Contents[] contents;
    Contents[] sun_option;
    Contents[] mon_option;
    Contents[] tue_option;
    Contents[] wed_option;
    Contents[] thr_option;
    Contents[] fri_option;
    Contents[] sat_option;

    public int getPackagePrice()
    {
        return packagePrice;
    }

    public void setPackagePrice(int packagePrice)
    {
        this.packagePrice = packagePrice;
    }

    public int getPackageDays()
    {
        return packageDays;
    }

    public void setPackageDays(int packageDays)
    {
        this.packageDays = packageDays;
    }

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

    public Contents[] getSun_option()
    {
        return sun_option;
    }

    public void setSun_option(Contents[] sun_option)
    {
        this.sun_option = sun_option;
    }

    public Contents[] getMon_option()
    {
        return mon_option;
    }

    public void setMon_option(Contents[] mon_option)
    {
        this.mon_option = mon_option;
    }

    public Contents[] getTue_option()
    {
        return tue_option;
    }

    public void setTue_option(Contents[] tue_option)
    {
        this.tue_option = tue_option;
    }

    public Contents[] getWed_option()
    {
        return wed_option;
    }

    public void setWed_option(Contents[] wed_option)
    {
        this.wed_option = wed_option;
    }

    public Contents[] getThr_option()
    {
        return thr_option;
    }

    public void setThr_option(Contents[] thr_option)
    {
        this.thr_option = thr_option;
    }

    public Contents[] getFri_option()
    {
        return fri_option;
    }

    public void setFri_option(Contents[] fri_option)
    {
        this.fri_option = fri_option;
    }

    public Contents[] getSat_option()
    {
        return sat_option;
    }

    public void setSat_option(Contents[] sat_option)
    {
        this.sat_option = sat_option;
    }
}
