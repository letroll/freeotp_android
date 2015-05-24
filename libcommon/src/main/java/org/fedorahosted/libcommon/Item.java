package org.fedorahosted.libcommon;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by letroll on 24/05/15.
 */
public class Item implements Parcelable {
    private String icon;
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Item() {

    }

    public Item(String icon, String text) {
        this.icon = icon;
        this.text = text;
    }


    protected Item(Parcel in) {
        icon = in.readString();
        text = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icon);
        dest.writeString(text);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };
}