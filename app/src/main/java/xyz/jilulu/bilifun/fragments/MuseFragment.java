package xyz.jilulu.bilifun.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import xyz.jilulu.bilifun.R;
import xyz.jilulu.bilifun.activities.MainActivity;
import xyz.jilulu.bilifun.adapters.MyAdapter;
import xyz.jilulu.bilifun.helpers.MuseMember;

/**
 * Created by jamesji on 4/3/2016.
 */
public class MuseFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;

    public MuseFragment() {
        // Empty constructor required for fragment subclasses
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_muse, container, false);
        int i = getArguments().getInt(MainActivity.ARG_FRAGMENT_POSITION);
        String fragmentTitle = getResources().getStringArray(R.array.fragment_title_array)[i];
        getActivity().setTitle(fragmentTitle);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<MuseMember> museMemberList = initMuseMembers();

        mAdapter = new MyAdapter(museMemberList);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
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
