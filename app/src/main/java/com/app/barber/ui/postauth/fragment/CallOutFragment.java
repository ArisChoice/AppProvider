package com.app.barber.ui.postauth.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.barber.R;
import com.app.barber.core.BaseFragment;
import com.app.barber.ui.adapters.pager.CalloutPagerAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by harish on 25/10/18.
 */

public class CallOutFragment extends BaseFragment {
    @BindView(R.id.call_out_tabs)
    TabLayout callOutTabs;
    @BindView(R.id.callout_pager)
    ViewPager calloutPager;
    private CalloutPagerAdapter calloutPagerAdapter;

    @Override
    protected int getFragmentLayout() {
        return R.layout.layout_callout_fragments;
    }

    @Override
    public void UpdateData(int position, Bundle bundle) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getFragmentLayout(), container, false);
        ButterKnife.bind(this, rootView);
        initPager();
        initTabs();
        return rootView;
    }

    private void initTabs() {
        callOutTabs.addTab(createTab(R.string.title_appointments, R.drawable.appoinments_));
        callOutTabs.addTab(createTab(R.string.str_overview, R.drawable.calendar_2));
//        callOutTabs.addTab(callOutTabs.newTab().setText(R.string.title_appointments).setIcon(R.drawable.appoinments_));
//        callOutTabs.addTab(callOutTabs.newTab().setText(R.string.str_overview).setIcon(R.drawable.calendar_2));
//        callOutTabs.setupWithViewPager(calloutPager);
      /*  callOutTabs.setTabTextColors(
                ContextCompat.getColor(getActivity(), R.color.color_white),
                ContextCompat.getColor(getActivity(), R.color.color_white)
        );
*/
        callOutTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.e(" onTabSelected ", " " + tab.getPosition());
//                callOutTabs.getTabAt(tab.getPosition()).getCustomView().setSelected(true);
                calloutPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        wrapTabIndicatorToTitle(exploreTabLayout, 60, 60);
    }

    private TabLayout.Tab createTab(int title_appointments, int appoinments_) {
        TabLayout.Tab tab = callOutTabs.newTab().setText(getString(title_appointments)).setIcon(appoinments_).setCustomView(R.layout.custom_tab);
        // remove imageView bottom margin
        if (tab.getCustomView() != null) {
            ImageView imageView = (ImageView) tab.getCustomView().findViewById(android.R.id.icon);
            ViewGroup.MarginLayoutParams lp = ((ViewGroup.MarginLayoutParams) imageView.getLayoutParams());
            lp.bottomMargin = 0;
            imageView.requestLayout();
        }
        return tab;
    }

    /**
     * initialize pager
     */
    private void initPager() {
        calloutPagerAdapter = new CalloutPagerAdapter(getActivity().getApplicationContext(), getChildFragmentManager());
        calloutPager.setAdapter(calloutPagerAdapter);
        calloutPager.setOffscreenPageLimit(2);
        calloutPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                callOutTabs.getTabAt(position).select();
                List<Fragment> fragList = getChildFragmentManager().getFragments();
                for (int i = 0; i < fragList.size(); i++) {
                    if (fragList.get(i) instanceof OverviewFragment) {
                        ((OverviewFragment) fragList.get(i)).setToDefault();
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
