package com.android.aurin.eresearch;

import java.io.Serializable;

/**
 * Created by apple on 16/4/9.
 */
public class BBOX implements Serializable{

    //private String name;
    private double lowerLon = 0.0;
    private double higherLon = 0.0;
    private double lowerLa = 0.0;
    private double higherLa = 0.0;

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getName() {
//        return name;
//    }

    public BBOX(){

    }

    public BBOX(double lowerLon, double higherLon, double lowerLa, double higherLa){
        this.lowerLon = lowerLon;
        this.higherLon = higherLon;
        this.lowerLa = lowerLa;
        this.higherLa = higherLa;
    }

    public void setLowerLon(Double lowerLon) {
        this.lowerLon = lowerLon;
    }

    public Double getLowerLon() {
        return lowerLon;
    }

    public void setHigherLon(Double higherLon) {
        this.higherLon = higherLon;
    }

    public Double getHigherLon() {
        return higherLon;
    }

    public void setLowerLa(Double lowerLa) {
        this.lowerLa = lowerLa;
    }

    public Double getLowerLa() {
        return lowerLa;
    }

    public void setHigherLa(Double higherLa) {
        this.higherLa = higherLa;
    }

    public Double getHigherLa() {
        return higherLa;
    }

//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeDouble(lowerLon);
//        dest.writeDouble(lowerLa);
//        dest.writeDouble(higherLon);
//        dest.writeDouble(higherLa);
//
//    }
//
//    private BBOX(Parcel in) {
//        lowerLon = in.readDouble();
//        lowerLa = in.readDouble();
//        higherLon = in.readDouble();
//        higherLa = in.readDouble();
//    }
//
//    public static final Parcelable.Creator<BBOX> CREATOR =
//            new Parcelable.Creator<BBOX>() {
//                public BBOX createFromParcel(Parcel in) {
//                    return new BBOX(in);
//                }
//                public BBOX[] newArray(int size) {
//                    return new BBOX[size];
//                }
//            };
}
