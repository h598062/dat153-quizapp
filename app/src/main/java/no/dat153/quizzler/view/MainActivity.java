package no.dat153.quizzler.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import no.dat153.quizzler.R;
import no.dat153.quizzler.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static @ColorRes Integer bgColor;
    private ActivityMainBinding binding;

    private static final int REQUEST_CAMERA = 1;
    private static final int REQUEST_MEDIA_IMAGES = 2;
    private static final int REQUEST_EXTERNAL_STORAGE = 3;

    public static @ColorRes Integer getBgColor() {
        return bgColor;
    }

    public static void setBgColor(@ColorRes Integer bgColor) {
        MainActivity.bgColor = bgColor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.layout.setBackgroundTintList(getColorStateList(bgColor));
        binding.btnGalleri.setBackgroundTintList(getColorStateList(GalleryActivity.getBgColor()));
        binding.btnStartQuiz.setBackgroundTintList(getColorStateList(QuizActivity.getBgColor()));

        binding.btnGalleri.setOnClickListener(v -> {
            Intent intent = new Intent(this, GalleryActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        binding.btnStartQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuizActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        // sjekk om vi har tilgang til kamera, og spør om tilgang hvis vi ikke har det
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA);
        } else {
            GalleryActivity.setCameraEnabled(true);
        }
        // sjekk om vi har tilgang til media images, og spør om tilgang hvis vi ikke har det
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_MEDIA_IMAGES);
            } else {
                GalleryActivity.setGalleryEnabled(true);
            }
        }
        // hvis android versjonen er lavere enn tiramisu, sjekk om vi har tilgang til external storage, og spør om tilgang hvis vi ikke har det
        else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
            } else {
                GalleryActivity.setGalleryEnabled(true);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted
                    GalleryActivity.setCameraEnabled(true);
                } else {
                    // Camera permissions denied, deaktiver knappen for å legge til bilder fra kamera
                    GalleryActivity.setCameraEnabled(false);
                }
                break;
            // media images og external storage brukes til å lese bilder fra galleriet
            case REQUEST_MEDIA_IMAGES:
            case REQUEST_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission is granted
                    GalleryActivity.setGalleryEnabled(true);
                } else {
                    // Galleri permissions denied, deaktiver knappene for å legge til bilder fra galleri
                    GalleryActivity.setGalleryEnabled(false);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}