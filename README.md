项目需要，自定义了一个可以无限左右滑动的显示一周的日历控件（产品的灵感来自苹果自带的日历上的某个控件）。

![效果图](https://github.com/anyanyan07/WeekView/blob/master/screenPage/GIF_20181127_175851.gif)


使用：
**1.Add it in your root build.gradle at the end of repositories:**

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
```

**2.Add the dependency：**

```
dependencies {
	    implementation 'com.github.anyanyan07:WeekView:1.0.0'
}
```

**3.布局文件：**

```
<com.xwtec.weekviewlib.view.WeekView
        android:id="@+id/week_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="25dp"
        app:active_color="@color/c_1CD39B"
        app:active_size="18sp"
        app:iv_height="50dp"
        app:iv_padding="18dp"
        app:iv_width="44dp"
        app:month_text_color="@color/c_333333"
        app:month_text_size="23sp"
        app:normal_color="@color/c_333333"
        app:normal_size="15sp"
        app:show_iv="true"
        app:vertical_space="10dp"
        app:week_height="60dp" />
```
	
**4.java代码：**

```
weekView.setFragments(getChildFragmentManager());
weekView.setDayClickListener(this);
```
 

