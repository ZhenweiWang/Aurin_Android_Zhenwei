package com.android.aurin.aurin_android_zhenwei;

import android.graphics.Color;

import java.util.ArrayList;

/**
 * Created by apple on 16/4/17.
 */
public final class Colors_Collection {
    public static ArrayList<Integer> reds = new ArrayList<>();
    public static ArrayList<Integer> blues = new ArrayList<>();
    public static ArrayList<Integer> greens = new ArrayList<>();
    public static ArrayList<Integer> grays = new ArrayList<>();
    public static ArrayList<Integer> purples = new ArrayList<>();
    public static int transparency = (int) (Map_Setting.opacity * 2.55);

    static int red1 = Color.argb(transparency, 236, 100, 75);
    static int red2 = Color.argb(transparency, 231, 76, 60);
    static int red3 = Color.argb(transparency, 239, 72, 54);
    static int red4 = Color.argb(transparency, 192, 57, 43);
    static int red5 = Color.argb(transparency, 207, 0, 15);
    static int red6 = Color.argb(transparency, 242, 38, 19);
    static int red7 = Color.argb(transparency, 150, 40, 27);
    static int red8 = Color.argb(transparency, 217, 30, 24);

    static int blue1 = Color.argb(transparency, 197, 239, 247);
    static int blue2 = Color.argb(transparency, 129, 207, 224);
    static int blue3 = Color.argb(transparency, 137, 196, 244);
    static int blue4 = Color.argb(transparency, 107, 185, 240);
    static int blue5 = Color.argb(transparency, 52, 152, 219);
    static int blue6 = Color.argb(transparency, 30, 139, 195);
    static int blue7 = Color.argb(transparency, 34, 167, 240);
    static int blue8 = Color.argb(transparency, 75, 119, 190);

    static int green1 = Color.argb(transparency, 162, 222, 208);
    static int green2 = Color.argb(transparency, 144, 198, 149);
    static int green3 = Color.argb(transparency, 102, 204, 153);
    static int green4 = Color.argb(transparency, 107, 185, 240);
    static int green5 = Color.argb(transparency, 63, 195, 128);
    static int green6 = Color.argb(transparency, 46, 204, 113);
    static int green7 = Color.argb(transparency, 0, 177, 106);
    static int green8 = Color.argb(transparency, 30, 130, 76);

    static int gray1 = Color.argb(transparency, 236, 240, 241);
    static int gray2 = Color.argb(transparency, 236, 236, 236);
    static int gray3 = Color.argb(transparency, 210, 215, 211);
    static int gray4 = Color.argb(transparency, 191, 191, 191);
    static int gray5 = Color.argb(transparency, 189, 195, 199);
    static int gray6 = Color.argb(transparency, 171, 183, 183);
    static int gray7 = Color.argb(transparency, 149, 165, 166);
    static int gray8 = Color.argb(transparency, 108, 122, 137);

    static int purple1 = Color.argb(transparency, 220, 198, 224);
    static int purple2 = Color.argb(transparency, 174, 168, 211);
    static int purple3 = Color.argb(transparency, 190, 144, 212);
    static int purple4 = Color.argb(transparency, 191, 85, 236);
    static int purple5 = Color.argb(transparency, 145, 61, 136);
    static int purple6 = Color.argb(transparency, 154, 18, 179);
    static int purple7 = Color.argb(transparency, 102, 51, 153);
    static int purple8 = Color.argb(transparency, 103, 65, 114);


    static {
        reds.add(red1);
        reds.add(red2);
        reds.add(red3);
        reds.add(red4);
        reds.add(red5);
        reds.add(red6);
        reds.add(red7);
        reds.add(red8);
        blues.add(blue1);
        blues.add(blue2);
        blues.add(blue3);
        blues.add(blue4);
        blues.add(blue5);
        blues.add(blue6);
        blues.add(blue7);
        blues.add(blue8);
        greens.add(green1);
        greens.add(green2);
        greens.add(green3);
        greens.add(green4);
        greens.add(green5);
        greens.add(green6);
        greens.add(green7);
        greens.add(green8);
        grays.add(gray1);
        grays.add(gray2);
        grays.add(gray3);
        grays.add(gray4);
        grays.add(gray5);
        grays.add(gray6);
        grays.add(gray7);
        grays.add(gray8);
        purples.add(purple1);
        purples.add(purple2);
        purples.add(purple3);
        purples.add(purple4);
        purples.add(purple5);
        purples.add(purple6);
        purples.add(purple7);
        purples.add(purple8);
    }
}
