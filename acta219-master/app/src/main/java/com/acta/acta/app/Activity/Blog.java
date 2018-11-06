package com.acta.acta.app.Activity;

/**
 * Created by wareja on 16. 12. 5.
 */

public class Blog {
    private String Title;
    private String Description;
    private String Images;
    private String Date_posted;



    public Blog()
    {

    }

    public Blog(String title, String description, String images, String dateTime) {
        Title = title;
        Description = description;
        Images=images;
        Date_posted=dateTime;

    }

    public String getTitle() {

        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImages() {
        return Images;
    }

    public void setImage( String image) {
        Images = image;
    }

    public String getDateTime()
    {
        return Date_posted;
    }

    public void setDateTime(String dateTime)
    {
        Date_posted=dateTime;
    }



}
