package com.routesounds.routesounds;

/**
 * Created by DepinaG on 24/11/2015.
 */
public class Band {
    private String address;
    private String name;
    private String website;
    private String contact;
    private String imgurl;
    public Band() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    public String getAddress() {
        return address;
    }
    public String getName() {
        return name;
    }

    public String getWebsite() {
        return website;
    }

    public String getContact() {
        return contact;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}
