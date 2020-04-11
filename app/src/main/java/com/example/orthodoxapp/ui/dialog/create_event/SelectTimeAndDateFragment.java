package com.example.orthodoxapp.ui.dialog.create_event;

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

public class SelectTimeAndDateFragment extends DialogFragment {

  private ViewPager2 viewPager;
  private TabLayout tabLayout;
  private Button btnOk;
  private Button btnCancel;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_view_pager, container, false);

   initView(root);

    viewPager.setAdapter(new PagerLayoutAdapter(getParentFragmentManager(), getLifecycle()));
    new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
      if (position == 0) tab.setText("from"); else tab.setText("to");
    }).attach();

    btnCancel.setOnClickListener(v -> dismiss());

    return root;
  }

  private void initView(View root){
    viewPager = root.findViewById(R.id.viewpager);
    tabLayout = root.findViewById(R.id.tab_layout);
    btnOk = root.findViewById(R.id.btn_ok_vp);
    btnCancel = root.findViewById(R.id.btn_cancel_vp);
  }
}



class PagerLayoutAdapter extends FragmentStateAdapter {

  PagerLayoutAdapter(@NonNull FragmentManager fragmentManager,
      @NonNull Lifecycle lifecycle) {
    super(fragmentManager, lifecycle);
  }

  @NonNull
  @Override
  public Fragment createFragment(int position) {
    return DateFragment.newInstance(position);
  }

  @Override
  public int getItemCount() {
    return 2;
  }
}
