package com.xwtec.weekviewlib;


import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xwtec.weekviewlib.listener.DayClickListener;

import java.util.List;

/**
 * @Author ayy
 * @Date 2018/11/26.
 * Describe:xxx
 */
public class WeekFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "WeekFragment";
    private LinearLayout llWeek;
    private List<WeekBean> data;
    private ColorStateList colorStateList;
    private float normalSize;
    private float activeSize;
    private DayClickListener dayClickListener;

    public void setDayClickListener(DayClickListener dayClickListener) {
        this.dayClickListener = dayClickListener;
    }

    public WeekFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        llWeek = view.findViewById(R.id.ll_week);
        for (int i = 0; i < llWeek.getChildCount(); i++) {
            llWeek.getChildAt(i).setOnClickListener(this);
        }
        updateView();
        return view;
    }

    public void setStyle(int normalColor, int activeColor, float normalSize, float activeSize) {
        this.normalSize = normalSize;
        this.activeSize = activeSize;
        colorStateList = new ColorStateList(new int[][]{{android.R.attr.state_selected}, {}}, new int[]{activeColor, normalColor});
    }

    public void setData(List<WeekBean> data) {
        this.data = data;
        updateView();
    }

    public void updateView() {
        if (data != null && llWeek != null) {
            if (llWeek.getChildCount() != data.size()) {
                throw new IllegalArgumentException("llWeek's child count is different from data's size!");
            }
            for (int i = 0; i < data.size(); i++) {
                WeekBean weekBean = data.get(i);
                if (weekBean != null) {
                    TextView textView = (TextView) llWeek.getChildAt(i);
                    textView.setText(weekBean.getShowDate());
                    textView.setTextColor(colorStateList);
                    textView.setTag(weekBean.getDate());
                    textView.setSelected(weekBean.isActive());
                    float textSize = weekBean.isActive() ? activeSize : normalSize;
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (dayClickListener != null) {
            String date = (String) view.getTag();
            dayClickListener.onDayClickListener(date);
        }
    }
}
