package com.example.opennews.Auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.opennews.MainActivity;
import com.example.opennews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import java.util.concurrent.Executor;

public class LoginFragment extends Fragment {

    private final String TAG = "openNews";
    private FirebaseAuth auth;
    private TextView errorTextView;
    private EditText emailTextView;
    private EditText passwordTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailTextView = view.findViewById(R.id.et_mail);
        passwordTextView = view.findViewById(R.id.et_password);
        errorTextView = view.findViewById(R.id.tv_error);
        Button btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate(emailTextView.getText().toString(), passwordTextView.getText().toString());
            }
        });
        view.findViewById(R.id.gsign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthenticationActivity)getActivity()).googleSignIn();
            }
        });

    }



    private void authenticate(final String email, String password){
        auth = FirebaseAuth.getInstance();
        if(email == null || email.equals("") || password == null || password.equals("")){
            errorTextView.setVisibility(View.VISIBLE);
            errorTextView.setText(getString(R.string.email_pass_req));
            return;
        }
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener( getActivity(),
                new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e(TAG, "Logged In");
                            errorTextView.setText("");
                            errorTextView.setVisibility(View.INVISIBLE);
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            String error = ((FirebaseAuthException) task.getException()).getErrorCode();
                            if(error.equals("ERROR_USER_NOT_FOUND")){
                                errorTextView.setText(getString(R.string.invalid_creds));
                            } else if(error.equals("ERROR_USER_DISABLED")){
                                errorTextView.setText(getString(R.string.user_disabled));
                            } else {
                                errorTextView.setText(getString(R.string.invalid_creds));
                            }
                            errorTextView.setVisibility(View.VISIBLE);
                            passwordTextView.setText("");
                        }
                    }
                }
        );
    }
}

