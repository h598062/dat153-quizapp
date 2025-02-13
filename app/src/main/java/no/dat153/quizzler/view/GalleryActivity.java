package no.dat153.quizzler.view;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import no.dat153.quizzler.R;
import no.dat153.quizzler.adapter.GalleryItemAdapter;
import no.dat153.quizzler.databinding.ActivityGalleryBinding;

public class GalleryActivity extends AppCompatActivity {

    private static final String TAG = "GalleryActivity";
    private static @ColorRes Integer bgColor;
    private ActivityGalleryBinding binding;
    private GalleryItemAdapter adapter;
    private Animation fromBottom;
    private Animation fromTop;
    private Animation rotateOpen;
    private Animation rotateClose;
    private Boolean floatingMenuOpen = false;

    public static @ColorRes Integer getBgColor() {
        return bgColor;
    }

    public static void setBgColor(@ColorRes Integer bgColor) {
        GalleryActivity.bgColor = bgColor;
    }

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

        binding.layout.setBackgroundTintList(getColorStateList(bgColor));

        binding.addButton.setOnClickListener(v -> {
            if (floatingMenuOpen) {
                closeMenu();
                floatingMenuOpen = false;
            } else {
                openMenu();
                floatingMenuOpen = true;
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
                if (floatingMenuOpen) {
                    closeMenu();
                    floatingMenuOpen = false;
                } else {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });

        int spanCount = calculateSpanCount();
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        binding.recyclerView.setLayoutManager(layoutManager);

        adapter = new GalleryItemAdapter(this, R.drawable.cola, R.drawable.gud, R.drawable.gisle, R.drawable.guy, R.drawable.wah);
        binding.recyclerView.setAdapter(adapter);

    }

    private int calculateSpanCount() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int itemWidth = 120; // Adjust based on item size in dp
        return Math.max(2, (int) (dpWidth / itemWidth)); // At least 2 columns
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