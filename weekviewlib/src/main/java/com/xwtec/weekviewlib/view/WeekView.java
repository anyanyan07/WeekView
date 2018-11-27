package com.xwtec.weekviewlib.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xwtec.weekviewlib.R;
import com.xwtec.weekviewlib.WeekBean;
import com.xwtec.weekviewlib.WeekFragment;
import com.xwtec.weekviewlib.listener.DayClickListener;
import com.xwtec.weekviewlib.listener.ScrollListener;
import com.xwtec.weekviewlib.listener.UpdateListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author ayy
 * @Date 2018/11/27.
 * Describe:xxx
 */

public class WeekView extends FrameLayout implements ScrollListener, UpdateListener {
    private TextView tvCurrentMonth;
    private WeekViewPager weekViewPager;
    private float normalTextSize;
    private int normalTextColor;
    private float activeTextSize;
    private int activeTextColor;
    private List<WeekBean> weekData;
    private Date zeroDay;
    private String activeDay;
    private List<WeekFragment> weekFragmentList;
    private static final String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private DayClickListener dayClickListener;

    public void setDayClickListener(DayClickListener dayClickListener) {
        this.dayClickListener = dayClickListener;
        if (weekFragmentList != null) {
            for (int i = 0; i < weekFragmentList.size(); i++) {
                weekFragmentList.get(i).setDayClickListener(dayClickListener);
            }
        }
    }

    public WeekView(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public WeekView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public WeekView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WeekView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.week_layout, this, true);
        tvCurrentMonth = view.findViewById(R.id.tv_current_month);
        weekViewPager = view.findViewById(R.id.week_view_pager);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WeekView);
        float monthTextSize = typedArray.getDimensionPixelSize(R.styleable.WeekView_month_text_size, 15);
        int monthTextColor = typedArray.getColor(R.styleable.WeekView_month_text_color, Color.BLACK);
        tvCurrentMonth.setTextSize(TypedValue.COMPLEX_UNIT_PX, monthTextSize);
        tvCurrentMonth.setTextColor(monthTextColor);
        normalTextSize = typedArray.getDimensionPixelSize(R.styleable.WeekView_normal_size, 15);
        normalTextColor = typedArray.getColor(R.styleable.WeekView_normal_color, Color.BLACK);
        activeTextSize = typedArray.getDimensionPixelSize(R.styleable.WeekView_active_size, 18);
        activeTextColor = typedArray.getColor(R.styleable.WeekView_active_color, Color.RED);
        int verticalSpace = typedArray.getDimensionPixelSize(R.styleable.WeekView_vertical_space, 10);
        int weekHeight = typedArray.getDimensionPixelSize(R.styleable.WeekView_week_height, 10);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) weekViewPager.getLayoutParams();
        layoutParams.height = weekHeight;
        layoutParams.topMargin = verticalSpace;
        weekViewPager.setLayoutParams(layoutParams);
        typedArray.recycle();
        initListener();
        initWeekData();
    }

    public void setFragments(FragmentManager fm) {
        weekFragmentList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            WeekFragment weekFragment = new WeekFragment();
            weekFragment.setStyle(normalTextColor, activeTextColor, normalTextSize, activeTextSize);
            weekFragment.setData(weekData.subList(i * 7, i * 7 + 7));
            weekFragment.setDayClickListener(dayClickListener);
            weekFragmentList.add(weekFragment);
        }
        weekViewPager.setFragments(fm, weekFragmentList);
    }

    private void initWeekData() {
        weekData = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DATE, 0 - today - 7);
        zeroDay = calendar.getTime();
        changeData(calendar);
    }

    private void changeData(Calendar calendar) {
        for (int i = 0; i < 21; i++) {
            calendar.add(Calendar.DATE, 1);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            WeekBean weekBean;
            if (weekData.size() <= i) {
                weekBean = new WeekBean();
                weekData.add(weekBean);
            } else {
                weekBean = weekData.get(i);
            }
            String date = DateFormat.format("yyyy-MM-dd", calendar).toString();
            weekBean.setDate(date);
            weekBean.setShowDate(dayOfMonth + "日" + weekDays[dayOfWeek - 1]);
            if (i == 7) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1;
                tvCurrentMonth.setText(year + "年" + month + "月");
            }
            if (TextUtils.isEmpty(activeDay)) {
                weekBean.setActive(DateUtils.isToday(calendar.getTimeInMillis()));
            } else {
                weekBean.setActive(activeDay.equals(weekBean.getShowDate()));
            }
        }
    }

    private void initListener() {
        weekViewPager.setScrollListener(this);
        weekViewPager.setUpdateListener(this);
    }

    @Override
    public void onLeftScroll() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(zeroDay);
        calendar.add(Calendar.DATE, -7);
        zeroDay = calendar.getTime();
        changeData(calendar);
    }

    @Override
    public void onRightScroll() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(zeroDay);
        calendar.add(Calendar.DATE, 7);
        zeroDay = calendar.getTime();
        changeData(calendar);
    }

    @Override
    public void updateData() {
        for (int i = 0; i < weekFragmentList.size(); i++) {
            WeekFragment weekFragment = weekFragmentList.get(i);
            weekFragment.setData(weekData.subList(i * 7, i * 7 + 7));
        }
    }
}
