package xyz.jilulu.jamesji.bilifun;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MuseMemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muse_member);

        String museMemberWhoStartedThisActivity = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        RelativeLayout commonParent = (RelativeLayout) findViewById(R.id.relLayout);
        ((TextView) commonParent.findViewById(R.id.museMemberName)).setText(museMemberWhoStartedThisActivity);
        ((ImageView) commonParent.findViewById(R.id.museMemberProfileImage)).setImageResource(getMuseMemberDrawableIDFromJaName(museMemberWhoStartedThisActivity));
    }

    private int getMuseMemberDrawableIDFromJaName(String jaName) {
        switch (jaName) {
            case "高坂 穂乃果":
                return R.drawable.honoka_low_res;
            case "南 ことり":
                return R.drawable.kotori_low_res;
            case "園田 海未":
                return R.drawable.umi_low_res;
            case "小泉 花陽":
                return R.drawable.hanayo_low_res;
            case "星空 凛":
                return R.drawable.rin_low_res;
            case "西木野 真姫":
                return R.drawable.maki_low_res;
            case "矢澤 にこ":
                return R.drawable.nico_low_res;
            case "東條 希":
                return R.drawable.nozami_low_res;
            case "絢瀬 絵里":
                return R.drawable.eri_low_res;
            default:
                return -1;
        }
    }
}