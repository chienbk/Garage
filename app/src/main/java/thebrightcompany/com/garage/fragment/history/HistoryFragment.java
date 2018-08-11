package thebrightcompany.com.garage.fragment.history;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import thebrightcompany.com.garage.R;
import thebrightcompany.com.garage.view.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {

    public static final String TAG = HistoryFragment.class.getSimpleName();
    private MainActivity homeActivity;

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    private GarageTabPagerAdapter adapter;

    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        homeActivity.setTitle("Lịch sử sửa chữa");
    }

    @Override
    public void onResume() {
        //initView();
        super.onResume();
    }

    /**
     * The method use to setup viewpager
     */
    private void setupViewPager() {
        adapter = new GarageTabPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new PrepareHistoryFragment(), "Đang Sửa");
        adapter.addFragment(new FinsishedHistoryFragment(), "Đã Xong");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.homeActivity = (MainActivity) context;
    }
}
