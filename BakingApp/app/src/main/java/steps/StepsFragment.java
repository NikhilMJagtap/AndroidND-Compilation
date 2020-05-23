package steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
//import android.app.Fragment;
import com.example.bakingapp.R;
import com.example.bakingapp.RecipeViewModel;
import com.example.bakingapp.StepDetails;

import java.util.ArrayList;

import detail.FragmentDetail;
import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;
import moe.feng.common.stepperview.VerticalStepperView;
import utils.Recipe;
import utils.Step;


public class StepsFragment extends Fragment implements IStepperAdapter {

    private final String indexKey = "INDEX";
    private final String stepIndexKey = "STEP_INDEX";
    private final String CURRENT_STEP_STATE = "currentStepIndex";
    private ArrayList<Step> steps;
    private int mCurrentStep = 0;
    private VerticalStepperView mVerticalStepperView;
    private View mView;
    private View stepperView;
    private int position = 0;
    private Fragment detailFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView != null) return mView;
        mView = inflater.inflate(R.layout.fragment_steps, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Intent intent = getActivity().getIntent();
        if(intent.hasExtra(indexKey)){
            position = intent.getIntExtra(indexKey, 0);
        }
        Recipe recipe = RecipeViewModel.getRecipes().getValue().get(position);
        steps = new ArrayList<>(recipe.getSteps());
        mVerticalStepperView = view.findViewById(R.id.vertical_stepper_view);
        mVerticalStepperView.setStepperAdapter(this);
        mVerticalStepperView.setCurrentStep(mCurrentStep);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENT_STEP_STATE, mVerticalStepperView.getCurrentStep());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null){
            int step = savedInstanceState.getInt(CURRENT_STEP_STATE);
            mCurrentStep = step;
            mVerticalStepperView.setCurrentStep(step);
        }
    }

    @NonNull
    @Override
    public CharSequence getTitle(int i) {
        return steps.get(i).getShortDescription();
    }

    @Nullable
    @Override
    public CharSequence getSummary(int i) {
        return steps.get(i).getDescription();
    }

    @Override
    public int size() {
        return steps.size();
    }

    @Override
    public View onCreateCustomView(final int i, final Context context, VerticalStepperItemView parent) {
//        if(stepperView != null) return stepperView;
        stepperView = LayoutInflater.from(context).inflate(R.layout.vertical_stepper_item, parent, false);
        Button nextBtn = stepperView.findViewById(R.id.button_next);
        if(i == steps.size() - 1)
            nextBtn.setVisibility(View.INVISIBLE);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mVerticalStepperView.nextStep()){
                    Log.e("API2", "someError");
                }else{
                    mCurrentStep++;
                }
            }
        });
        Button prepareBtn = stepperView.findViewById(R.id.button_prepare);
        prepareBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(getResources().getBoolean(R.bool.isTablet)) {
                    detailFragment = new FragmentDetail();
                    Bundle bundle = new Bundle();
                    bundle.putInt(indexKey, position);
                    bundle.putInt(stepIndexKey, i);
                    detailFragment.setArguments(bundle);
                    detailFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.detail_container, detailFragment)
                            .commit();
                } else {
                    Intent intent = new Intent(context, StepDetails.class);
                    intent.putExtra(indexKey, position);
                    intent.putExtra(stepIndexKey, i);
                    startActivity(intent);
                }
            }
        });
        Button previousBtn = stepperView.findViewById(R.id.button_previous);
        if(i == 0)
            previousBtn.setVisibility(View.GONE);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mVerticalStepperView.prevStep()){
                    Log.e("API2", "someError");
                }else{
                    mCurrentStep--;
                }
            }
        });
        if(getResources().getBoolean(R.bool.isTablet)){
            detailFragment = new FragmentDetail();
            Bundle bundle = new Bundle();
            bundle.putInt(indexKey, position);
            bundle.putInt(stepIndexKey, mCurrentStep);
            detailFragment.setArguments(bundle);
            detailFragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_container, detailFragment)
                    .commit();
        }
        return stepperView;
    }

    @Override
    public void onShow(int i) {

    }

    @Override
    public void onHide(int i) {

    }
}
