package com.example.sqlapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlapp.MainActivity;

import com.example.sqlapp.R;
import com.example.sqlapp.entities.User;
import com.example.sqlapp.services.UserService;

import java.util.List;


public class LoginActivity extends AppCompatActivity {


    EditText username, password;
    Button login, signup;
    private TextureView backgroundVideo;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = findViewById(R.id.Username);
        password = findViewById(R.id.Password);


        signup = findViewById(R.id.signup);
        login=findViewById(R.id.login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        backgroundVideo = findViewById(R.id.background_video);
        mediaPlayer = new MediaPlayer();

        backgroundVideo.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {

                Surface surface = new Surface(surfaceTexture);
                mediaPlayer.setSurface(surface);

                Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.background);
                try {
                    mediaPlayer.setDataSource(LoginActivity.this, videoUri);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            // Start playback when prepared
                            mediaPlayer.start();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
                // React to surface changes, if needed
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                // Clean up resources when the TextureView is destroyed
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
                // Update the TextureView, if needed
            }
        });

       login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserService sv = new UserService(LoginActivity.this);
                List<User> users = sv.findAllUsers();
                boolean userFound = false;

                for (User user : users) {
                    if (user.getUsername().equals(username.getText().toString().trim()) &&
                            user.getPassword().equals(password.getText().toString().trim())) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        userFound = true;
                        break;
                    }
                }

                if (!userFound) {
                    Toast.makeText(LoginActivity.this, "User Not Found. Check Your credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayerResources();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayerResources();
    }

    private void releaseMediaPlayerResources() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}