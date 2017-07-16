package io.zenandroid.fptest.util;

import android.view.View;

/**
 * Created by acristescu on 16/07/2017.
 */

public class Utils {
	public static void setEnabled(View view, boolean enabled) {
		view.setEnabled(enabled);
		if(enabled) {
			view.setAlpha(1);
		} else {
			view.setAlpha(.3f);
		}
	}
}
