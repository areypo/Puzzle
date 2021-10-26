package com.manytiles.p8.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.manytiles.p8.MainActivity;
import com.manytiles.p8.MainActivityViewModel;
import com.manytiles.p8.R;

public class SelectImgActivity extends AppCompatActivity {

    MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_img);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Button selectImageButton = findViewById(R.id.select_img_button);
        ImageView selectedImageView = findViewById(R.id.selected_img_view);
        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK) {
                    Uri photoPath = result.getData().getData();
                    selectedImageView.setImageURI(photoPath);
                    Bitmap bitmap = BitmapFactory.decodeFile(photoPath.getPath());
                    viewModel.getPuzzleModel().setImagen(bitmap);
                    Intent selectLevelActivityIntent = new Intent(SelectImgActivity.this, SelectLevelActivity.class);
                    startActivity(selectLevelActivityIntent);
                } else {
                    Intent mainActivityIntent = new Intent(SelectImgActivity.this, MainActivity.class);
                    startActivity(mainActivityIntent);
                }
            }
        });

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        selectImageButton.setOnClickListener(view -> {
            launcher.launch(intent);
        });

        launcher.launch(intent);
    }

    public void loadImageFromGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivity(galleryIntent);
    }

}