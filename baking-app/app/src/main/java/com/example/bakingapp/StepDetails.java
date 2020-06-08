package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import detail.FragmentDetail;

public class StepDetails extends AppCompatActivity {

    private Fragment fragmentDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);
        if(savedInstanceState != null){
            fragmentDetail = getSupportFragmentManager().getFragment(savedInstanceState, "fragmentDetails");
        }else {
            fragmentDetail = new FragmentDetail();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.detail_container, fragmentDetail).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "fragmentDetails", fragmentDetail);
    }
}
