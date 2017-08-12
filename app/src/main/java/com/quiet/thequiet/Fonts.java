package com.quiet.thequiet;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by eka on 2017. 8. 12..
 */

public class Fonts {
    Context context;
    public Typeface CabinRegular;
    public Typeface NanumBarunGothic;
    public Typeface NanumBarunGothicBold;
    public Typeface NanumBarunGothicLight;
    public Typeface NanumBarunGothicUltraLight;

    public Fonts(Context context) {
        this.context = context;
        NanumBarunGothic = Typeface.createFromAsset(context.getAssets(), "fonts/NanumBarunGothic.ttf");
        NanumBarunGothicBold = Typeface.createFromAsset(context.getAssets(), "fonts/NanumBarunGothicBold.ttf");
        NanumBarunGothicLight = Typeface.createFromAsset(context.getAssets(), "fonts/NanumBarunGothicLight.ttf");
        NanumBarunGothicUltraLight = Typeface.createFromAsset(context.getAssets(), "fonts/NanumBarunGothicUltraLight.ttf");
        CabinRegular = Typeface.createFromAsset(context.getAssets(), "fonts/Cabin-Regular.ttf");
    }
}
