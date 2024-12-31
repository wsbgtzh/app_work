package com.example.app_work.index.pages.fragment_page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.app_work.Data.GlobalData;
import com.example.app_work.R;

public class MySpaceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myspace, container, false);
        TextView usernameView = rootView.findViewById(R.id.username);
        usernameView.setText(GlobalData.getInstance().getUsername());
        TextView fansView = rootView.findViewById(R.id.fans);
        fansView.setText(GlobalData.getInstance().getFans().toString());
        TextView followView = rootView.findViewById(R.id.follow);
        followView.setText(GlobalData.getInstance().getFollow().toString());
        return rootView;
    }
}
