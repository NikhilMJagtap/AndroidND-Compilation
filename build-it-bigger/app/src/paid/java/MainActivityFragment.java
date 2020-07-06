import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.example.jokelibrary.JokeActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.udacity.gradle.builditbigger.JokeAsyncTask;
import com.udacity.gradle.builditbigger.R;
import com.developers.telljoke.Joke;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ProgressBar pb;
    Button jokeButton;
    private static final String JOKE_KEY = "JOKE_KEY";
    private String joke;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
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
