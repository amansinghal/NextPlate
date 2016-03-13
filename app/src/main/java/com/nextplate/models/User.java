package com.nextplate.models;

/**
 * Created by AmaN on 3/13/2016.
 */
public class User
{
    String fbId;
    String name;
    String email;
    String phone;
    String createdAt;
    PurchasedMeals[] mealPurchasedId;

    public String getFbId()
    {
        return fbId;
    }

    public void setFbId(String fbId)
    {
        this.fbId = fbId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(String createdAt)
    {
        this.createdAt = createdAt;
    }

    public PurchasedMeals[] getMealPurchasedId()
    {
        return mealPurchasedId;
    }

    public void setMealPurchasedId(PurchasedMeals[] mealPurchasedId)
    {
        this.mealPurchasedId = mealPurchasedId;
    }
}
