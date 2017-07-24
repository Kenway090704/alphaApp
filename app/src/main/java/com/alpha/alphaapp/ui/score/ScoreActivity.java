package com.alpha.alphaapp.ui.score;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.alpha.alphaapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ScoreActivity extends AppCompatActivity {
    //http://www.open-open.com/lib/view/open1455716525589.html
    private List<Fragment> mTabContents = new ArrayList<>();
    private FragmentPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private List<String> mDatas = Arrays.asList("Ò³Ãæ1", "Ò³Ãæ2", "Ò³Ãæ3", "Ò³Ãæ4",
            "Ò³Ãæ5", "Ò³Ãæ6", "Ò³Ãæ7", "Ò³Ãæ8", "Ò³Ãæ9");

    private ViewPagerIndicator mIndicator;
    private BounceScrollView mScrollView;
    private RotatImageView mRotatImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vp_indicator);

        initView();
        initDatas();
        //ÉèÖÃTabÉÏµÄ±êÌâ
        mIndicator.setTabItemTitles(mDatas);
        mViewPager.setAdapter(mAdapter);
        //ÉèÖÃ¹ØÁªµÄViewPager
        mIndicator.setViewPager(mViewPager, mScrollView, 0);
        //ÉèÖÃ¹ØÁªµÄÍ¼Æ¬Ðý×ª£¬¸ù¾ÝÐèÒªÉèÖÃ£¬Ð§¹û²»ÊÇºÜºÃ
        mScrollView.setRotatImageView(mRotatImageView);
    }

    private void initDatas() {

        for (String data : mDatas) {
            VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
            mTabContents.add(fragment);
        }

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mTabContents.get(position);
            }
        };
    }

    private void initView() {
        mScrollView = (BounceScrollView) findViewById(R.id.id_scrollview);
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
        mRotatImageView = (RotatImageView) findViewById(R.id.id_rotat_imageView);
    }
}
