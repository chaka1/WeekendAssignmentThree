package com.example.chaka.weekendassignmentthree.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Chaka on 05/06/2015.
 */
public class AsosUser implements Parcelable{

    String user_token;
    String name;

    public AsosUser() {
    }

    public AsosUser(String user_token, String name) {
        this.user_token = user_token;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(user_token);
        dest.writeString(name);


    }

    /*
     * Retrieving Employee data from Parcel object
     * This constructor is invoked by the method createFromParcel(Parcel source) of
     * the object CREATOR
     */
    public AsosUser(Parcel in) {
        this.user_token = in.readString();
        this.name = in.readString();


    }

    public static final Parcelable.Creator<AsosUser> CREATOR = new Parcelable.Creator<AsosUser>() {

        @Override
        public AsosUser createFromParcel(Parcel source) {
            return new AsosUser(source);
        }

        @Override
        public AsosUser[] newArray(int size) {
            return new AsosUser[size];
        }
    };
}
