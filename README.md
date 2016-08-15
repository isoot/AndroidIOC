# AndroidIOC
-------------------------------------------------------
Java SE5内置了三种注解 ***@Override***,覆盖超类中的方法。***@Deprecated***，不赞成使用代码（过时）编译器会发出警告。***@SUppressWarnings***关闭编译器发出的警告

Java提供的注解：

#[@Target](https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=java%20%40Target&oq=java%20%40Target&rsv_pq=d7e76dd70012532e&rsv_t=ed44lwXD7YwG5xq6Od%2BIxWtJHT7KSFwDqCwuB8%2FpIcRtlJwl%2B9yKTMx1YnM&rqlang=cn&rsv_enter=0)
注解可以用在什么位置 ElementType参数有

1. [CONSTRUCTOR](http://www.cnblogs.com/pepcod/archive/2013/02/16/2913474.html):构造器声明
2. [FIELD](http://www.cnblogs.com/pepcod/archive/2013/02/16/2913474.html):域声明(包括enum实例)
3. [LOCAL_VARIABLE](http://www.cnblogs.com/pepcod/archive/2013/02/16/2913474.html):局部变量声明
4. [METHOD](http://www.cnblogs.com/pepcod/archive/2013/02/16/2913474.html):方法声明
5. [PACHAGE](http://www.cnblogs.com/pepcod/archive/2013/02/16/2913474.html):包声明
6. [PARAMETER](http://www.cnblogs.com/pepcod/archive/2013/02/16/2913474.html):参数声明
7. [TYPE](http://www.cnblogs.com/pepcod/archive/2013/02/16/2913474.html):类、接口（注解类型）enum声明

#[@Retention](https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&wd=java%20%40Retention&oq=java%20%40Target&rsv_pq=aa43bd0d00129216&rsv_t=5dfcBCaSP47xNPW4JhII46PHeLiPrBn1xk2LeNhIR8xCPdtfukM0d6V2Ed0&rqlang=cn&rsv_enter=0&rsv_sug3=8&rsv_sug6=21&rsv_sug1=6&rsv_sug7=100&rsv_n=2&rsv_sug2=0&inputT=591&rsv_sug4=749)
保存注解信息的级别，RetentionPolicy参数有

1. [SOURCE](http://www.cnblogs.com/pepcod/archive/2013/02/16/2913474.html):注解被编译器丢弃
2. [CLASS](http://www.cnblogs.com/pepcod/archive/2013/02/16/2913474.html):注解在class文件中，但会被VM抛弃
3. [RUNTIME](http://www.cnblogs.com/pepcod/archive/2013/02/16/2913474.html):vm运行期间保留注解，可以通过反射读取注解信息。

#[@Document](https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=site888_3_pg&wd=java%20%40Document&oq=java%20%40Document&rsv_pq=dfc9fae00012e7c9&rsv_t=452dMrnEhCsHYYiyHcsISOizhBHgWjVRVOlJwg4irr1SVzYIlTp3Vl5xbjuB8rbBwllE&rqlang=cn&rsv_enter=0)
将注解包含至javadoc中

#[@Inherited](https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=site888_3_pg&wd=java%20%40Inherited&oq=java%20%40Document&rsv_pq=f2167d8b0015ee18&rsv_t=8860BZSyzvjcgH%2BN2SIdRx2LzkzOT8Ua9OXJijitRhfUkixbUuFDkFNeTgJsZVDuxrqB&rqlang=cn&rsv_enter=0&rsv_sug3=18&rsv_sug1=16&rsv_sug7=100&rsv_n=2&rsv_sug2=0&inputT=64143&rsv_sug4=64317)
允许之类继承父类的注解

#code

- @Target(ElementType.FIELD)
- @Retention(RetentionPolicy.RUNTIME)
- public @interface Test {
- int value() default -1;
- String valueString() default "abc";
- }

在Android开发中使用@Retention(RetentionPolicy.RUNTIME)通过反射设置Activity中的控件用布局ID，这样做可以减少代码使得整体看起来清洁漂亮。
AndroidICO是一个使用注解设置获取控件和布局ID的实例，、

1. 获取成员控件View通过fianViewById动态获取

- private static void injectViews(Activity activity) {
- 		Class<? extends Activity> clazz = activity.getClass();
- 		// 获取这个类里面的所有成员属性
- 		Field[] fields = clazz.getDeclaredFields();
- 		for (Field field : fields) {
- 			ViewInJect inJect = field.getAnnotation(ViewInJect.class);
- 			if (inJect != null) {
- 				int viewId = inJect.value();
- 				if (viewId != -1) {
- 					try {
- 						Method method = clazz.getMethod("findViewById",
- 								int.class);
- 						Object resView = method.invoke(activity, viewId);
- 						field.setAccessible(true);
- 						field.set(activity, resView);
- 					} catch (Exception e) {
- 					}
- 				}
- 			}
- 		}
- 	}

2. 动态设置布局Id

- private static void injectContentView(Activity activity) {
- 		Class<? extends Activity> clazz = activity.getClass();
- 		ContentView contentView = clazz.getAnnotation(ContentView.class);
- 		if (contentView != null) {
- 			int contentViewLayoutID = contentView.value();
- 			try {
- 				Method method = clazz.getMethod("setContentView", int.class);
- 				method.setAccessible(true);
- 				method.invoke(activity, contentViewLayoutID);
- 			} catch (Exception e) {
- 			}
- 		}
- 	}
