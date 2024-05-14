package enset.android.projetsynthese.ui.UserChat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import enset.android.projetsynthese.R;
import enset.android.projetsynthese.ui.UserChat.models.User;


public class SignUpActivity  extends AppCompatActivity {

    EditText userEmail, userPassword,userName;
    TextView signinBtn, signupBtn;
    String email, password,name;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        databaseReference= FirebaseDatabase.getInstance().getReference("users");
        userName=findViewById(R.id.nametext);
        userEmail=findViewById(R.id.emailtext);
        userPassword=findViewById(R.id.passwordtext);
        signinBtn=findViewById(R.id.signinButton);
        signupBtn=findViewById(R.id.signupButton);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = userName.getText().toString().trim();
                email = userEmail.getText().toString().trim();
                password = userPassword.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    userEmail.setError("Please enter your name");
                    userEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    userEmail.setError("Please enter your mail");
                    userEmail.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    userPassword.setError("Please enter your password");
                    userPassword.requestFocus();
                    return;
                }
                Signup();
            }
        });
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });


    }
    private void Signup() {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.trim(),password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        UserProfileChangeRequest userProfileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                        user.updateProfile(userProfileChangeRequest);

                        User userModel=new User(FirebaseAuth.getInstance().getUid(),name,email,password);
                        databaseReference.child(FirebaseAuth.getInstance().getUid()).setValue(userModel);
                        Intent intent=new Intent(SignUpActivity.this,UserChatFragment.class);
                        intent.putExtra("name",name);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this,"Sign up failed",Toast.LENGTH_SHORT);
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(SignUpActivity.this,UserChatFragment.class));
            finish();
        }
    }
}
