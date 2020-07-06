import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.developers.telljoke.Joke;
import com.example.jokelibrary.JokeActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.gradle.builditbigger.JokeAsyncTask;
import com.udacity.gradle.builditbigger.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    Button jokeButton;
    String joke;
    private ProgressBar pb;
    private static final String JOKE_KEY = "JOKE_KEY";

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        jokeButton = root.findViewById(R.id.joke_btn);
        pb = root.findViewById(R.id.pb);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setVisibility(View.VISIBLE);
                new JokeAsyncTask(new JokeAsyncTask.OnFinish() {
                    @Override
                    public void onFinishMethod(String s) {
                        pb.setVisibility(View.INVISIBLE);
                        joke = s;
                        Intent intent = new Intent(getActivity(), JokeActivity.class);
                        intent.putExtra(JOKE_KEY, joke);
                        startActivity(intent);
                    }
                }).execute();
            }
        });

//        jokeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pb.setVisibility(View.VISIBLE);
//                joke = Joke.getJoke();
//                Intent intent = new Intent(getActivity(), JokeActivity.class);
//                intent.putExtra(JOKE_KEY, joke);
//                pb.setVisibility(View.INVISIBLE);
//                startActivity(intent);
//            }
//        });

        return root;
    }
}
