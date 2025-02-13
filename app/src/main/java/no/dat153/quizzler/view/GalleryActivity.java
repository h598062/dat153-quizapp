package no.dat153.quizzler.view;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import no.dat153.quizzler.R;
import no.dat153.quizzler.databinding.ActivityGalleryBinding;
import no.dat153.quizzler.utils.DrawableUtils;

public class GalleryActivity extends AppCompatActivity {

    private ActivityGalleryBinding binding;

    private Animation fromBottom;
    private Animation fromTop;
    private Animation rotateOpen;
    private Animation rotateClose;

    private Boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGalleryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        EdgeToEdge.enable(this);
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(view, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        fromTop = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);

        DrawableUtils.setBackgroundColor(this, binding.layout, R.drawable.rounded_corners, com.shifthackz.catppuccin.palette.legacy.R.color.catppuccin_mocha_yellow);

        binding.addButton.setOnClickListener(v -> {
            if (clicked) {
                closeMenu();
                clicked = false;
            } else {
                openMenu();
                clicked = true;
            }
        });

        binding.addFromCameraButton.setOnClickListener(v -> {
            // Add from camera
            Toast.makeText(this, "You clicked the Camera button!", Toast.LENGTH_SHORT).show();
        });

        binding.addFromGalleryButton.setOnClickListener(v -> {
            // Add from gallery
            Toast.makeText(this, "You clicked the Gallery button!", Toast.LENGTH_SHORT).show();
        });


        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if (clicked) {
                    closeMenu();
                    clicked = false;
                } else {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

    }

    private void openMenu() {

        binding.addFromCameraButton.setVisibility(View.VISIBLE);
        binding.addFromGalleryButton.setVisibility(View.VISIBLE);

        binding.addFromCameraButton.startAnimation(fromBottom);
        binding.addFromGalleryButton.startAnimation(fromBottom);

        binding.addButton.startAnimation(rotateOpen);
    }

    private void closeMenu() {

        binding.addFromCameraButton.startAnimation(fromTop);
        binding.addFromGalleryButton.startAnimation(fromTop);

        binding.addFromCameraButton.setVisibility(View.GONE);
        binding.addFromGalleryButton.setVisibility(View.GONE);

        binding.addButton.startAnimation(rotateClose);
    }
}