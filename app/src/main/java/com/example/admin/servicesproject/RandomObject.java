package com.example.admin.servicesproject;

import java.io.Serializable;

public class RandomObject implements Serializable
{
    int someField;
    int someOtherField;
    int yetAnotherField;
    int lastOneIPromise;

    public RandomObject(int someField, int someOtherField, int yetAnotherField, int lastOneIPromise) {
        this.someField = someField;
        this.someOtherField = someOtherField;
        this.yetAnotherField = yetAnotherField;
        this.lastOneIPromise = lastOneIPromise;
    }

    public int getSomeField() {
        return someField;
    }

    public void setSomeField(int someField) {
        this.someField = someField;
    }

    public int getSomeOtherField() {
        return someOtherField;
    }

    public void setSomeOtherField(int someOtherField) {
        this.someOtherField = someOtherField;
    }

    public int getYetAnotherField() {
        return yetAnotherField;
    }

    public void setYetAnotherField(int yetAnotherField) {
        this.yetAnotherField = yetAnotherField;
    }

    public int getLastOneIPromise() {
        return lastOneIPromise;
    }

    public void setLastOneIPromise(int lastOneIPromise) {
        this.lastOneIPromise = lastOneIPromise;
    }

    @Override
    public String toString() {
        return "RandomObject{" +
                "someField=" + someField +
                ", someOtherField=" + someOtherField +
                ", yetAnotherField=" + yetAnotherField +
                ", lastOneIPromise=" + lastOneIPromise +
                '}';
    }
}
