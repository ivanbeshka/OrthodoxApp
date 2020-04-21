package com.example.orthodoxapp.ui.dialog.create_event;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.orthodoxapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;

public class SelectTimeFragment extends DialogFragment {

  private ViewPager2 viewPager;
  private TabLayout tabLayout;
  private Button btnOk;
  private Button btnCancel;
  private PagerTimeAdapter adapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_view_pager, container, false);

    initView(root);

    adapter = new PagerTimeAdapter(getParentFragmentManager(), getLifecycle());
    viewPager.setAdapter(adapter);
    new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
      if (position == 0) {
        tab.setText("from");
      } else {
        tab.setText("to");
      }
    }).attach();

    btnOk.setOnClickListener(v -> {
      TimeFragment fromTimeFragment = adapter.getFragment(0);
      String fromTime = fromTimeFragment.getTime();
      String toTime;
      //creating fragment only post scrolling on it
      try {
        TimeFragment toTimeFragment = adapter.getFragment(1);
        toTime = toTimeFragment.getTime();
      }catch (IndexOutOfBoundsException ignored){
        toTime = fromTime;
      }

      Intent intent = new Intent();
      intent.putExtra("time", fromTime + " - " + toTime);
      getTargetFragment().onActivityResult(CreateEventFragment.RC_TARGET_FRAGMENT_TIME, 1, intent);
      dismiss();
    });

    btnCancel.setOnClickListener(v -> dismiss());

    return root;
  }

  private void initView(View root) {
    viewPager = root.findViewById(R.id.viewpager);
    tabLayout = root.findViewById(R.id.tab_layout);
    btnOk = root.findViewById(R.id.btn_ok_vp);
    btnCancel = root.findViewById(R.id.btn_cancel_vp);
  }

}
class PagerTimeAdapter extends FragmentStateAdapter {

  private ArrayList<TimeFragment> fragments;

  PagerTimeAdapter(@NonNull FragmentManager fragmentManager,
      @NonNull Lifecycle lifecycle) {
    super(fragmentManager, lifecycle);
    fragments = new ArrayList<>();
  }

  TimeFragment getFragment(int position) {
    return fragments.get(position);
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    TimeFragment fragment = new TimeFragment();
    fragments.add(position, fragment);
    return fragment;
  }

  @Override
  public int getItemCount() {
    return 2;
  }
}