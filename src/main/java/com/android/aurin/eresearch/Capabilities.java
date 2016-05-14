package com.android.aurin.eresearch;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by apple on 16/4/9.
 */
public class Capabilities implements Serializable {
    public String name = "no name";
    public String title = "no data title";
    public String abstracts = "no abstracts";
    public String organization = "no organization";
//    public String website = "www.aurin.org.au";
    public ArrayList<String> keywords;
    public BBOX bbox;
    public int image_id;

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int flags) {
//        parcel.writeString(title);
//        parcel.writeString(abstracts);
//        parcel.writeString(organization);
//        parcel.writeSerializable(keywords);
//        //parcel.writeParcelable(bbox);
//    }
//
//    private Capabilities(Parcel in) {
//        title = in.readString();
//        abstracts = in.readString();
//        organization = in.readString();
//        keywords = (ArrayList<String>) in.readSerializable();
//
//    }
//
//    public static final Parcelable.Creator<Capabilities> CREATOR =
//            new Parcelable.Creator<Capabilities>() {
//                public Capabilities createFromParcel(Parcel in) {
//                    return new Capabilities(in);
//                }
//                public Capabilities[] newArray(int size) {
//                    return new Capabilities[size];
//                }
//            };

}

