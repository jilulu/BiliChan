package xyz.jilulu.bilichan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.gigamole.library.NavigationTabBar;

import java.util.ArrayList;

import xyz.jilulu.bilichan.Adapters.MainActivityFragmentAdapter;
import xyz.jilulu.bilichan.Fragments.EmptyFavoriteFragment;

public class MainActivity extends FragmentActivity implements EmptyFavoriteFragment.EmptyFragmentPlaceHolder.OnNavigationButtonClicked {

    private android.support.v13.app.FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager viewPager;

    public static final int NUMBER_OF_FRAGMENTS = 4;
    public static final int SEARCH_FRAGMENT = 0;
    public static final int DISCOVER_FRAGMENT = 1;
    public static final int FAVORITE_FRAGMENT = 2;
    public static final int PREFERENCE_FRAGMENT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    @Override
    public void onButtonClicked(View v) {
        String buttonText = ((Button) v).getText().toString();
        int pageNum;
        switch (buttonText.charAt(0)) {
            case 'S': // Search
                pageNum = SEARCH_FRAGMENT;
                break;
            case 'D':
                pageNum = DISCOVER_FRAGMENT;
                break;
            default:
                pageNum = -1;
                break;
        }
        viewPager.setCurrentItem(pageNum);
    }

    private void initUI() {
        viewPager = (ViewPager) findViewById(R.id.activity_main_view_pager_horizontal_ntb);
        viewPager.setOffscreenPageLimit(NUMBER_OF_FRAGMENTS);

        fragmentPagerAdapter = new MainActivityFragmentAdapter(getFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        final String[] colors = getResources().getStringArray(R.array.tabcolors);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.activity_main_ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.ic_search), Color.parseColor(colors[0]), "Search"));
        models.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.ic_discover), Color.parseColor(colors[1]), "Discover"));
        models.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.ic_fav_main), Color.parseColor(colors[2]), "Favorites"));
        models.add(new NavigationTabBar.Model(getResources().getDrawable(R.drawable.ic_pref), Color.parseColor(colors[3]), "Preferences"));
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
