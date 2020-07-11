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

import org.w3c.dom.Text;

public class SignupFragment extends Fragment {
    private final String TAG = "openNews";
    private FirebaseAuth auth;

    private EditText emailEditText, passwordEditText;
    private TextView errorText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEditText = view.findViewById(R.id.et_mail_signup);
        passwordEditText = view.findViewById(R.id.et_password_signup);
        errorText = view.findViewById(R.id.tv_error_signup);
        Button btn = view.findViewById(R.id.btn_signup);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });
        view.findViewById(R.id.gsign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AuthenticationActivity)getActivity()).googleSignIn();
            }
        });
    }

    private void authenticate(String email, final String password){
        auth = FirebaseAuth.getInstance();
        if(email == null || email.equals("") || password == null || password.equals("")){
            errorText.setVisibility(View.VISIBLE);
            errorText.setText(getString(R.string.email_pass_req));
            return;
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( getActivity(),
                new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.e(TAG, "Signed up");
                            startActivity(new Intent(getContext(), MainActivity.class));
                            getActivity().finish();
                        } else {
                            String error = ((FirebaseAuthException) task.getException()).getErrorCode();
                            errorText.setVisibility(View.VISIBLE);
                            passwordEditText.setText("");
                            switch(error){
                                case "ERROR_INVALID_EMAIL":
                                    errorText.setText(getString(R.string.mail_invalid));
                                    passwordEditText.setText("");
                                    emailEditText.requestFocus();
                                    break;
                                case "ERROR_WEAK_PASSWORD":
                                    errorText.setText(getString(R.string.pass_invalid));
                                    passwordEditText.requestFocus();
                                    break;
                                default:
                                    errorText.setText(getString(R.string.something_wrong));
                                    passwordEditText.requestFocus();
                                    break;
                            }

                        }
                    }
                }
        );
    }
}
