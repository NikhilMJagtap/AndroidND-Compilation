package steps;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;


import com.example.bakingapp.R;

public class StepsActivity extends FragmentActivity {
    private Fragment stepsFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        if(savedInstanceState != null){
            stepsFragment = getSupportFragmentManager().getFragment(savedInstanceState, "stepsFragment");
        }else {
            stepsFragment = new StepsFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_steps_container, stepsFragment).commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "stepsFragment", stepsFragment);
    }
}
