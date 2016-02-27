package xyz.jilulu.jamesji.bilifun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<MuseMember> museMemberList = initMuseMembers();

        mAdapter = new MyAdapter(museMemberList);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

    private List<MuseMember> initMuseMembers() {
        List<MuseMember> museMemberList = new ArrayList<>();
        String[] museMembers = {"高坂 穂乃果（こうさか ほのか）", "南 ことり（みなみ ことり）", "園田 海未（そのだ うみ）",
                "小泉 花陽（こいずみ はなよ）", "星空 凛（ほしぞら りん）", "西木野 真姫（にしきの まき）",
                "矢澤 にこ（やざわ にこ）", "東條 希（とうじょう のぞみ）", "絢瀬 絵里（あやせ えり）"};
        for (String eachMuseMember : museMembers) {
            int museAvatarResourceID;
            switch (eachMuseMember) {
                case "高坂 穂乃果（こうさか ほのか）":
                    museAvatarResourceID = R.drawable.honoka_low_res;
                    break;
                case "南 ことり（みなみ ことり）":
                    museAvatarResourceID = R.drawable.kotori_low_res;
                    break;
                case "園田 海未（そのだ うみ）":
                    museAvatarResourceID = R.drawable.umi_low_res;
                    break;
                case "小泉 花陽（こいずみ はなよ）":
                    museAvatarResourceID = R.drawable.hanayo_low_res;
                    break;
                case "星空 凛（ほしぞら りん）":
                    museAvatarResourceID = R.drawable.rin_low_res;
                    break;
                case "西木野 真姫（にしきの まき）":
                    museAvatarResourceID = R.drawable.maki_low_res;
                    break;
                case "矢澤 にこ（やざわ にこ）":
                    museAvatarResourceID = R.drawable.nico_low_res;
                    break;
                case "東條 希（とうじょう のぞみ）":
                    museAvatarResourceID = R.drawable.nozomi_low_res;
                    break;
                case "絢瀬 絵里（あやせ えり）":
                    museAvatarResourceID = R.drawable.eri_low_res;
                    break;
                default:
                    museAvatarResourceID = -1;
                    break;
            }
            MuseMember newMember = new MuseMember(eachMuseMember, museAvatarResourceID);
            museMemberList.add(newMember);
        }
        return museMemberList;
    }

}
