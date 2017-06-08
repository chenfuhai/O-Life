package com.olife.o_life;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.RadioGroup;

import com.olife.o_life.util.OlifeFragmentManager;
import com.olife.o_life.util.StatusBarUtils;

import java.util.ArrayList;

/**
 * 首页Activity
 */
public class IndexActivity extends ToolBarBaseActivity {

    private OlifeFragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //自动检查更新
        //BmobUpdateAgent.update(this);
        //有WIFI 自动下载
        //BmobUpdateAgent.silentUpdate(this);

        AboutMeFragment aboutmeFragment = new AboutMeFragment();
        final OneKeyCheckFragment oneKeyFragment = new OneKeyCheckFragment();
        DiscoveryFragment discoveryFragment = new DiscoveryFragment();
        ReGoodsFragment reGoodsFragment = new ReGoodsFragment();

        fm = new OlifeFragmentManager(this,R.id.fragment);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(oneKeyFragment);
        fragments.add(discoveryFragment);
        fragments.add(reGoodsFragment);
        fragments.add(aboutmeFragment);

        ArrayList<String> fragmentTags = new ArrayList<>();
        fragmentTags.add(OneKeyCheckFragment.class.getName());
        fragmentTags.add(DiscoveryFragment.class.getName());
        fragmentTags.add(ReGoodsFragment.class.getName());
        fragmentTags.add(AboutMeFragment.class.getName());

        fm.addFragments(fragments,fragmentTags,0);


        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.indexNavigationbar);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch(id){
                    case R.id.btnIndexOnekey:
                        fm.showFragment(OneKeyCheckFragment.class.getName());
                        //TODO 设置一个get颜色的方法 把当前的颜色取出来 然后设置给状态栏\
                        StatusBarUtils.setWindowStatusBarColor(IndexActivity.this,oneKeyFragment.getBackgroundColor(),false);
                    break;
                    case R.id.btnIndexDiscovery:
                        fm.showFragment(DiscoveryFragment.class.getName());
                        StatusBarUtils.setWindowStatusBarColor(IndexActivity.this,
                                ContextCompat.getColor(getApplicationContext(),R.color.bg_blue_deep),false);
                        break;
                    case R.id.btnIndexGoods:
                        fm.showFragment(ReGoodsFragment.class.getName());
                        StatusBarUtils.setWindowStatusBarColor(IndexActivity.this,
                                ContextCompat.getColor(getApplicationContext(),R.color.bg_blue_deep),false);
                        break;
                    case R.id.btnIndexAboutme:
                        StatusBarUtils.setWindowStatusBarColor(IndexActivity.this,R.color.bg_blue_deep,false);
                        fm.showFragment(AboutMeFragment.class.getName());
                        StatusBarUtils.setWindowStatusBarColor(IndexActivity.this,
                                ContextCompat.getColor(getApplicationContext(),R.color.bg_blue_deep),false);
                        break;
                    default:
                    break;
                }
            }
        });




    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }
}
