/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package xyz.janficko.teevee.util;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

/**
 * A collection of utility methods, all static.
 */
public class Utils {

	/*
	 * Making sure public utility methods remain static
     */
	private Utils() {
	}

	/**
	 * Returns the screen/display size
	 */
	public static Point getDisplaySize(Context context) {
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		return size;
	}

	/**
	 * Shows a (long) toast
	 */
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Shows a (long) toast.
	 */
	public static void showToast(Context context, int resourceId) {
		Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_LONG).show();
	}

	/**
	 * Formats time in milliseconds to hh:mm:ss string format.
	 */
	public static String formatMillis(int millis) {
		String result = "";
		int hr = millis / 3600000;
		millis %= 3600000;
		int min = millis / 60000;
		millis %= 60000;
		int sec = millis / 1000;
		if (hr > 0) {
			result += hr + ":";
		}
		if (min >= 0) {
			if (min > 9) {
				result += min + ":";
			} else {
				result += "0" + min + ":";
			}
		}
		if (sec > 9) {
			result += sec;
		} else {
			result += "0" + sec;
		}
		return result;
	}

	public static int convertDpToPixel(Context ctx, int dp) {
		float density = ctx.getResources().getDisplayMetrics().density;
		return Math.round((float) dp * density);
	}

	/**
	 * Will read the content from a given {@link InputStream} and return it as a {@link String}.
	 *
	 * @param inputStream The {@link InputStream} which should be read.
	 * @return Returns <code>null</code> if the the {@link InputStream} could not be read. Else
	 * returns the content of the {@link InputStream} as {@link String}.
	 */
	public static String inputStreamToString(InputStream inputStream) {
		try {
			byte[] bytes = new byte[inputStream.available()];
			inputStream.read(bytes, 0, bytes.length);
			String json = new String(bytes);
			return json;
		} catch (IOException e) {
			return null;
		}
	}

	public static Uri getResourceUri(Context context, int resID) {
		return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
				context.getResources().getResourcePackageName(resID) + '/' +
				context.getResources().getResourceTypeName(resID) + '/' +
				context.getResources().getResourceEntryName(resID));
	}

	public static String scoreWithSuffix(Integer score){
		if (score < 1000) return "" + score;
		int exp = (int) (Math.log(score) / Math.log(1000));
		return String.format("%.1f%c",
				score / Math.pow(1000, exp),
				"kMGTPE".charAt(exp-1));
	}

	public static int countLine(int textLength){
		int sign = (textLength > 0 ? 1 : -1) * (45 > 0 ? 1 : -1);

		if (sign > 0) {
			return (textLength + 45 - 1) / 45;
		}
		else {
			return (textLength / 45);
		}
	}

	public static String getDateFromMilliseconds(long milliseconds){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliseconds);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		return day + "/" + (month + 1) + "/" + year;
	}

}
