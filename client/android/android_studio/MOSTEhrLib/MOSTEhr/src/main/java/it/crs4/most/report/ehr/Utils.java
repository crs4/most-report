/**
 * Project MOST - Moving Outcomes to Standard Telemedicine Practice
 * http://most.crs4.it/
 * <p>
 * Copyright 2014, CRS4 srl. (http://www.crs4.it/)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * See license-GPLv2.txt or license-MIT.txt
 */

package it.crs4.most.report.ehr;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.Locale;


/**
 * This class contains utility methods internally used by the framework.
 */
public class Utils {


    /**
     * Gets the locale string resource.
     *
     * @param ctx the ctx
     * @param locale the locale
     * @param resourseId the resourse id
     * @return the locale string resource
     */
    public static String getLocaleStringResource(Context ctx, String locale, int resourseId) {
        Configuration conf = ctx.getResources().getConfiguration();
        conf.locale = new Locale(locale);
        DisplayMetrics metrics = new DisplayMetrics();
        //((Activity)ctx).getWindowManager().getDefaultDisplay().getMetrics(metrics);

        Resources resources = new Resources(ctx.getAssets(), metrics, conf);
        return resources.getString(resourseId);
    }
}
