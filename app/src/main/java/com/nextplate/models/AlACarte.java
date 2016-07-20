package com.nextplate.models;

import com.nextplate.core.util.Utility;
/**
 * Created by AmaN on 3/17/2016.
 */
public class AlACarte
{
    int id;
    String name;
    String imageUrl;
    int price;
    String fromTime;
    String toTime;

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

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public int getPrice()
    {
        return price;
    }

    public void setPrice(int price)
    {
        this.price = price;
    }

    public String getFromTime()
    {
        return Utility.formattedTime(fromTime);
    }

    public void setFromTime(String fromTime)
    {
        this.fromTime = fromTime;
    }

    public String getToTime()
    {
        return Utility.formattedTime(toTime);
    }

    public void setToTime(String toTime)
    {
        this.toTime = toTime;
    }
}
