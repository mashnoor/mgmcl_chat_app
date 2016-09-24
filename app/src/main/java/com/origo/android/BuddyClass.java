package com.origo.android;

/**
 * Created by Menon on 8/26/16.
 */
public class BuddyClass {

    String buddyName;
    String buddyNumber;

    public BuddyClass(String buddyName, String buddyNumber) {
        this.buddyName = buddyName;
        this.buddyNumber = buddyNumber;
    }

    public String getBuddyName() {
        return buddyName;
    }

    public String getBuddyNumber() {
        return buddyNumber;
    }
}
