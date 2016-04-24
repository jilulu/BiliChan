package xyz.jilulu.bilichan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.gigamole.library.NavigationTabBar;

import java.util.ArrayList;

import xyz.jilulu.bilichan.Adapters.MainActivityFragmentAdapter;

public class MainActivity extends FragmentActivity {

    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;

    public static final int NUMBER_OF_FRAGMENTS = 4;
    public static final int SEARCH_FRAGMENT = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.activity_main_view_pager_horizontal_ntb);

        fragmentPagerAdapter = new MainActivityFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.activity_main_ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.ic_search), Color.parseColor(colors[0]), "Search"));
        models.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.ic_second), Color.parseColor(colors[1]), "Discover"));
        models.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.ic_third), Color.parseColor(colors[2]), "Favorites"));
        models.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.ic_fourth), Color.parseColor(colors[3]), "Preferences"));
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager);

        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        navigationTabBar.post(new Runnable() {
            @Override
            public void run() {
                final View bgNavigationTabBar = findViewById(R.id.activity_main_bg_ntb_horizontal);
                bgNavigationTabBar.getLayoutParams().height = (int) navigationTabBar.getBarHeight();
                bgNavigationTabBar.requestLayout();
            }
        });


//        navigationTabBar.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
//                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
//                    switch (i) {
//                        case 0:
//                            model.setBadgeTitle("NTB");
//                            break;
//                        case 1:
//                            model.setBadgeTitle("with");
//                            break;
//                        case 2:
//                            model.setBadgeTitle("title");
//                            break;
//                        case 3:
//                            model.setBadgeTitle("badge");
//                            break;
//                        case 4:
//                            model.setBadgeTitle("777");
//                            break;
//                    }
//                    navigationTabBar.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            model.showBadge();
//                        }
//                    }, i * 100);
//                }
//            }
//        }, 500);

    }
}