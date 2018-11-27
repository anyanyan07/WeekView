package com.xwtec.weekviewlib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.xwtec.weekviewlib.adapter.WeekAdapter;
import com.xwtec.weekviewlib.listener.ScrollListener;
import com.xwtec.weekviewlib.listener.UpdateListener;

import java.util.List;

/**
 * @Author ayy
 * @Date 2018/11/26.
 * Describe:xxx
 */

public class WeekViewPager extends ViewPager {

    private List<? extends Fragment> fragmentList;
    private ScrollListener scrollListener;
    private UpdateListener updateListener;

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public WeekViewPager(@NonNull Context context) {
        super(context);
    }

    public WeekViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setFragments(FragmentManager fm, List<? extends Fragment> fragmentList) {
        if (fragmentList == null || fragmentList.size() != 3) {
            throw new IllegalArgumentException("the size of fragmentList must be 3!");
        }
        this.fragmentList = fragmentList;
        setAdapter(new WeekAdapter(fm, fragmentList));
        addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset == 0) {
                    if (position == getAdapter().getCount() - 1) {
                        if (scrollListener != null) {
                            scrollListener.onRightScroll();
                        }
                    } else if (position == 0) {
                        if (scrollListener != null) {
                            scrollListener.onLeftScroll();
                        }
                    }
                    if (updateListener != null) {
                        updateListener.updateData();
                    }
                    setCurrentItem(1, false);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        setCurrentItem(1);
    }
}
