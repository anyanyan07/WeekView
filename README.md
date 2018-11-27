自定义View，可以左右无限滑动。
效果图：
使用：
1.Add it in your root build.gradle at the end of repositories:
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
}
2.Add the dependency：
dependencies {
	    implementation 'com.github.anyanyan07:WeekView:1.0.0'
}
3.布局文件：
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
4.java代码：
   weekView.setFragments(getChildFragmentManager());
   weekView.setDayClickListener(this);
 

