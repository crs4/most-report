package it.crs4.most.ehrlib;

import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;


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
	public static String getLocaleStringResource(Context ctx, String locale, int resourseId)
	{
		Configuration conf = ctx.getResources().getConfiguration();
		conf.locale = new Locale(locale);
		DisplayMetrics metrics = new DisplayMetrics();
		//((Activity)ctx).getWindowManager().getDefaultDisplay().getMetrics(metrics);
		
		Resources resources = new Resources(ctx.getAssets(), metrics, conf);
		String str = resources.getString(resourseId);
		return str;
	}
}
