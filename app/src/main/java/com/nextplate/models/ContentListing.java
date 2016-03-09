package com.nextplate.models;

/**
 * Created by gspl on 3/9/2016.
 */
public class ContentListing
{
    String heading;
    Contents[] contents;

    public String getHeading()
    {
        return heading;
    }

    public void setHeading(String heading)
    {
        this.heading = heading;
    }

    public Contents[] getContents()
    {
        return contents;
    }

    public void setContents(Contents[] contents)
    {
        this.contents = contents;
    }
}
