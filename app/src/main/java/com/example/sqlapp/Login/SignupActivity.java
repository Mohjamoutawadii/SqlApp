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

import com.example.sqlapp.R;
import com.example.sqlapp.entities.User;
import com.example.sqlapp.services.UserService;

public class SignupActivity extends AppCompatActivity {

    EditText username, password, name;

    Button signup;
    private TextureView backgroundVideo;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.fullname);
        username = findViewById(R.id.Username2);
        password = findViewById(R.id.Passowrd2);
        signup = findViewById(R.id.signup2);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(username.getText().toString().trim(), password.getText().toString().trim(), name.getText().toString().trim());
                UserService sv = new UserService(SignupActivity.this);
                sv.createUser(user);

                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
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
                    mediaPlayer.setDataSource(SignupActivity.this, videoUri);
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