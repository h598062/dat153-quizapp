package no.dat153.quizzler.view;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import no.dat153.quizzler.R;
import no.dat153.quizzler.adapter.GalleryItemAdapter;
import no.dat153.quizzler.databinding.ActivityGalleryBinding;
import no.dat153.quizzler.entity.QuestionItem;
import no.dat153.quizzler.viewmodel.GalleryViewModel;

public class GalleryActivity extends AppCompatActivity {

    private static @ColorRes Integer bgColor;
    private final String TAG = this.getClass().getSimpleName();
    private ActivityGalleryBinding binding;
    private GalleryItemAdapter adapter;
    private GalleryViewModel viewModel;
    private Animation fromBottom;
    private Animation fromTop;
    private Animation rotateOpen;
    private Animation rotateClose;
    private Boolean floatingMenuOpen = false;

    private ActivityResultLauncher<Intent> cameraLauncher;
    private ActivityResultLauncher<PickVisualMediaRequest> galleryLauncher;

    private Uri photoUri;
    private final ActivityResultLauncher<String> requestCameraPermissions = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (isGranted) {
            openCamera();
        } else {
            Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
        }
    });

    /**
     * Henter bakgrunnsfargen for denne aktiviteten
     *
     * @return Fargen som er satt
     */
    public static @ColorRes Integer getBgColor() {
        return bgColor;
    }

    /**
     * Setter bakgrunnsfargen for denne aktiviteten
     *
     * @param bgColor Fargen som skal settes
     */
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

        loadAnimations();

        binding.layout.setBackgroundTintList(getColorStateList(bgColor));

        binding.addButton.setOnClickListener(v -> {
            if (floatingMenuOpen) {
                closeMenu();
            } else {
                openMenu();
            }
        });

        binding.addFromCameraButton.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Log.d(TAG, "AddFromCameraButton: Camera permission not granted, asking for permission");
                requestCameraPermissions.launch(Manifest.permission.CAMERA);
            }

            closeMenu();
        });

        binding.addFromGalleryButton.setOnClickListener(v -> {
            openGallery();
            closeMenu();
        });


        int spanCount = calculateSpanCount();
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount);
        binding.recyclerView.setLayoutManager(layoutManager);

        adapter = new GalleryItemAdapter(this);
        binding.recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        viewModel.getQuestions().observe(this, questions -> adapter.setQuestions(questions));

        setupBackButtonHandler();
        setupCamera();
        setupGalleryPicker();
    }

    private void loadAnimations() {
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim);
        fromTop = AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim);
        rotateOpen = AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim);
    }

    private void setupBackButtonHandler() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event
                if (floatingMenuOpen) {
                    closeMenu();
                } else {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
            }
        });
    }

    private void setupCamera() {
        // Launcher for å åpne Camera app for å ta et bilde. Det er her callback med resultatet fra bildet er
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // Gjør noe med bildet som er tatt
                // The image is saved at photoUri
                // Toast.makeText(this, "Image saved to: " + photoUri.getPath(), Toast.LENGTH_LONG).show();
                Log.d(TAG, "CameraLauncher: Image saved to: " + photoUri);
                askForQuestionText(photoUri);
            }
        });
    }

    private void setupGalleryPicker() {
        // Lanucher for å åpne phone gallery image picker. Det er her callback med resultatet fra bildet er
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
            if (uri != null) {
                // Gjør noe med bildet som er valgt
                // The selected image is at uri
                // Toast.makeText(this, "Image Selected: " + uri, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "GalleryLauncher: Selected image uri: " + uri);
                getContentResolver().takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                askForQuestionText(uri);
            }
        });
    }

    private void askForQuestionText(Uri imageUri) {
        Dialog d = new Dialog(this);
        d.setContentView(R.layout.dialog_text_input_image_preview);
        d.setCancelable(false);

        ImageView imageView = d.findViewById(R.id.dialog_image);
        imageView.setImageURI(imageUri);

        TextView title = d.findViewById(R.id.dialog_title);
        title.setText(R.string.new_question_dialog_title);

        TextInputLayout textInput = d.findViewById(R.id.dialog_text_input);
        textInput.setHint(R.string.new_question_dialog_hint);

        d.findViewById(R.id.dialog_button).setOnClickListener(v -> {
            String text = textInput.getEditText().getText().toString();
            addQuestion(text, imageUri);
            d.dismiss();
        });
        d.show();
        d.getWindow().setLayout(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
    }

    private void addQuestion(String text, Uri imageUri) {
        viewModel.add(new QuestionItem(text, imageUri));
    }

    /**
     * Moderne måten å åpne en Phone Gallery image picker meny
     */
    private void openGallery() {
        galleryLauncher.launch(new PickVisualMediaRequest.Builder().setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly.INSTANCE).build()); // Opens gallery to pick an image
    }

    /**
     * Moderne måten å åpne en Camera app for å ta et bilde
     */
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(); // Create a file to store the image
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                photoUri = FileProvider.getUriForFile(this, "no.dat153.quizzler.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                cameraLauncher.launch(takePictureIntent);
            }
        }
    }

    /**
     * Lager en fil for å lagre bildet som skal tas
     *
     * @return File som representerer bildet
     * @throws IOException ved feil
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    /**
     * Beregner antall kolonner i RecyclerView basert på skjermstørrelse
     *
     * @return Antall kolonner
     */
    private int calculateSpanCount() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int itemWidth = 120; // Adjust based on item size in dp
        return Math.max(2, (int) (dpWidth / itemWidth)); // At least 2 columns
    }

    /**
     * Åpner floating menyen for å legge til bilder
     */
    private void openMenu() {
        floatingMenuOpen = true;

        binding.addFromCameraButton.setVisibility(View.VISIBLE);
        binding.addFromGalleryButton.setVisibility(View.VISIBLE);

        binding.addFromCameraButton.startAnimation(fromBottom);
        binding.addFromGalleryButton.startAnimation(fromBottom);

        binding.addButton.startAnimation(rotateOpen);
    }

    /**
     * Lukker floating menyen for å legge til bilder
     */
    private void closeMenu() {
        floatingMenuOpen = false;

        binding.addFromCameraButton.startAnimation(fromTop);
        binding.addFromGalleryButton.startAnimation(fromTop);

        binding.addFromCameraButton.setVisibility(View.GONE);
        binding.addFromGalleryButton.setVisibility(View.GONE);

        binding.addButton.startAnimation(rotateClose);
    }
}