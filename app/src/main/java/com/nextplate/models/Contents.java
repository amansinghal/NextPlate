package com.nextplate.models;

/**
 * Created by AmaN on 3/6/2016.
 */
public class Contents
{
    String name;
    String imageUrl;
    int price;
    String timeFrom;
    String timeTo;

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

    public String getTimeFrom()
    {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom)
    {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo()
    {
        return timeTo;
    }

    public void setTimeTo(String timeTo)
    {
        this.timeTo = timeTo;
    }
}
