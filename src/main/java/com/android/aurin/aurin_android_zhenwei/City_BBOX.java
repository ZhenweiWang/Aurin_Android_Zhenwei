package com.android.aurin.aurin_android_zhenwei;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 16/4/10.
 */
public final class City_BBOX {

//    public String name;
//    public BBOX bbox;

    // lowerLon, higherLon, lowerLa, higherLa

    //public  HashMap<String,BBOX> city_bbox = new HashMap<String, BBOX>();

//    //String kmelbourne = "Melbourne";
//   public static BBOX melbourne = new BBOX(0.0, 0.0, 0.0, 0.0);
//
//    //String ksydney = "Sydney";
//    public static BBOX sydney = new BBOX(0.0, 0.0, 0.0, 0.0);
//
//    //String kbrisbane = "Brisbane";
//    public static BBOX brisbane = new BBOX(0.0, 0.0, 0.0, 0.0);
//
//    //String kdarwin = "Darwin";
//    public static BBOX darwin = new BBOX(0.0, 0.0, 0.0, 0.0);
//
//    //String kperth = "Perth";
//    public static BBOX perth = new BBOX(0.0, 0.0, 0.0, 0.0);
//
//    //String kadelaide = "Adelaide";
//    public static BBOX adelaide = new BBOX(0.0, 0.0, 0.0, 0.0);
//
//    //String khobart = "Hobart";
//    public static BBOX hobart = new BBOX(0.0, 0.0, 0.0, 0.0);
//
//    //String kACT = "Australian Capital Territory";
//    public static BBOX ACT = new BBOX(0.0, 0.0, 0.0, 0.0);

    public static HashMap<String,BBOX> city_bbox = new HashMap<>();

    static {
        city_bbox.put("Greater Sydney", new BBOX(0.0, 0.0, 0.0, 0.0));
        city_bbox.put("Greater Darwin", new BBOX(0.0, 0.0, 0.0, 0.0));
        city_bbox.put("Greater Brisbane", new BBOX(0.0, 0.0, 0.0, 0.0));
        city_bbox.put("Greater Adelaide", new BBOX(0.0, 0.0, 0.0, 0.0));
        city_bbox.put("Greater Hobart", new BBOX(0.0, 0.0, 0.0, 0.0));
        city_bbox.put("Greater Melbourne", new BBOX(0.0, 0.0, 0.0, 0.0));
        city_bbox.put("Greater Perth", new BBOX(0.0, 0.0, 0.0, 0.0));
        city_bbox.put("Australian Capital Territory", new BBOX(0.0, 0.0, 0.0, 0.0));
    }




}
