package xyz.hanks.library.bang;

import android.content.Context;

/**
 * Created by Miroslaw Stanek on 21.12.2015.
 */
public class Utils {
    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }

    public static int dp2px(Context context, float dp) {
        return Math.round(context.getResources().getDisplayMetrics().density * dp);
    }
}
