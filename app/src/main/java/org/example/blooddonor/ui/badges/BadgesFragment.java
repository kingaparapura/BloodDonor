package org.example.blooddonor.ui.badges;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import org.example.blooddonor.R;

public class BadgesFragment extends Fragment {

    private BadgesViewModel badgesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        badgesViewModel =
                ViewModelProviders.of(this).get(BadgesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_badges, container, false);
        return root;

        }

    }

