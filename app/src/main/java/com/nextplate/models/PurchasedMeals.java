package com.nextplate.models;

/**
 * Created by AmaN on 3/13/2016.
 */
public class PurchasedMeals extends Meals
{
    String dateOfPurchase;
    int totalMeals;
    int remainMeals;
    DeliveryDetail[] deliveryDetails;

    public String getDateOfPurchase()
    {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(String dateOfPurchase)
    {
        this.dateOfPurchase = dateOfPurchase;
    }

    public int getTotalMeals()
    {
        return totalMeals;
    }

    public void setTotalMeals(int totalMeals)
    {
        this.totalMeals = totalMeals;
    }

    public int getRemainMeals()
    {
        return remainMeals;
    }

    public void setRemainMeals(int remainMeals)
    {
        this.remainMeals = remainMeals;
    }

    public DeliveryDetail[] getDeliveryDetails()
    {
        return deliveryDetails;
    }

    public void setDeliveryDetails(DeliveryDetail[] deliveryDetails)
    {
        this.deliveryDetails = deliveryDetails;
    }
}
