package com.example.androidioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;

public class ViewInJectUtils {

	public static void inJect(Activity activity){
		injectContentView(activity);
		injectViews(activity);
	}
	private static void injectViews(Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		// 获取这个类里面的所有成员属性
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ViewInJect inJect = field.getAnnotation(ViewInJect.class);
			if (inJect != null) {
				int viewId = inJect.value();
				if (viewId != -1) {
					try {
						Method method = clazz.getMethod("findViewById",
								int.class);
						Object resView = method.invoke(activity, viewId);
						field.setAccessible(true);
						field.set(activity, resView);
					} catch (Exception e) {
					}
				}
			}
		}
	}

	private static void injectContentView(Activity activity) {
		Class<? extends Activity> clazz = activity.getClass();
		ContentView contentView = clazz.getAnnotation(ContentView.class);
		if (contentView != null) {
			int contentViewLayoutID = contentView.value();
			try {
				Method method = clazz.getMethod("setContentView", int.class);
				method.setAccessible(true);
				method.invoke(activity, contentViewLayoutID);
			} catch (Exception e) {
			}
		}
	}
}
