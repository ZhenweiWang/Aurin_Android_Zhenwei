package com.android.aurin.aurin_android_zhenwei;

import java.util.HashMap;

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
        city_bbox.put("Greater Sydney", new BBOX(149.9719, 151.6305, -34.3312, -32.9961));

        city_bbox.put("Greater Darwin", new BBOX(130.8151, 131.3967, -12.8619, -12.0010));

        city_bbox.put("Greater Brisbane", new BBOX(152.0734, 153.5467, -28.3640, -26.4519));

        city_bbox.put("Greater Adelaide", new BBOX(138.4362, 139.0440, -35.3503, -34.5002));

        city_bbox.put("Greater Hobart", new BBOX(147.0267, 147.9369, -43.1213, -42.6554));

        city_bbox.put("Greater Melbourne", new BBOX(144.9514, 144.9901, -37.8555, -37.7997));
        city_bbox.put("Ballarat", new BBOX(143.0630,144.4906,-37.9885,-36.6988));
        city_bbox.put("Bendigo", new BBOX(143.3152,144.8536,-37.4589,-35.9059));
        city_bbox.put("Geelong", new BBOX(143.6221,-144.7202,-38.5794,-37.7812));
        city_bbox.put("Hume", new BBOX(144.5304,148.2207,-37.8280,-35.9285));
        city_bbox.put("Melbourne Inner", new BBOX(144.8889,145.0453,-37.8917,-37.7325));
        city_bbox.put("Melbourne Inner East", new BBOX(144.9993,145.1841,-37.8759,-37.7339));
        city_bbox.put("Melbourne Inner South", new BBOX(144.9834,145.1563,-38.0850,-37.8374));
        city_bbox.put("Melbourne North East", new BBOX(144.8807,145.5800,-37.7851,-37.2629));
        city_bbox.put("Melbourne North West", new BBOX(144.4577,144.9853,-37.7761,-37.1751));
        city_bbox.put("Melbourne Outer East", new BBOX(145.1569,145.8784,-37.9750,-37.5260));
        city_bbox.put("Melbourne South East", new BBOX(145.0795,145.7651,-38.3325,-37.8533));
        city_bbox.put("Melbourne West", new BBOX(144.3336,144.9165,-38.0046,-37.5464));
        city_bbox.put("Shepparton", new BBOX(144.2593,146.2465,-36.7626,-35.8020));
        city_bbox.put("North West", new BBOX(140.9617,144.4182,-37.8366,-33.9804));
        city_bbox.put("Mornington Peninsula", new BBOX(144.6514,145.2617,-38.5030,-38.0674));
        city_bbox.put("Warrnambool and South West", new BBOX(140.9657,143.9461,-38.8577,-37.0870));
        city_bbox.put("Latrobe Gippsland", new BBOX(145.1094,149.9767,-39.1592,-36.6124));

        city_bbox.put("Greater Perth", new BBOX(115.4495, 116.4151, -32.8019, -31.4551));

        city_bbox.put("Australian Capital Territory", new BBOX(148.7627, 149.3993, -35.9208, -35.1245));
    }




}
