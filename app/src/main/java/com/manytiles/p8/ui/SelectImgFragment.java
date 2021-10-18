package com.manytiles.p8.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;


import com.manytiles.p8.MainActivityViewModel;
import com.manytiles.p8.R;
import com.manytiles.p8.databinding.FragmentHomeBinding;
import com.manytiles.p8.databinding.SelectImgBinding;
import com.manytiles.p8.ui.home.HomeViewModel;

import java.io.IOException;

public class SelectImgFragment extends Fragment {

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    private ImageView imageSelected;

    private SelectImgBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = SelectImgBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivityViewModel viewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    //se coge la uri a la foto seleccionada
                    Uri photoUri = result.getData().getData();
                    try {
                        //se crea un bitmap(imagen) a partir de la uri que se devuelve
                        //ese bitmap (que es propiamente una imagen, ya se puede trocear en piezas o procesarlo como imagen)
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireContext().getContentResolver(), photoUri);
                        binding.fotoSeleccionadaImageView.setImageBitmap(bitmap);
                        viewModel.getPuzzleModel().setImagen(bitmap);
                        NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                        controller.navigate(R.id.action_nav_selectimg_to_nav_SeleccionarDificultad);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(requireContext(), "Debe seleccionar una foto obligatoriamente", Toast.LENGTH_LONG).show();
                }

                this.getClass();
            }
        });

        //El intent que se va a lanzar, solo permite imagenes
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        //al hacer click en un boton cuyo id es "testButton"
        //CUIDADO este botón hay que crearlo en la vista, si no lo creas no existe y va a fallar
        //es tan sencillo como poner en la vista/layout de este fragment un Button cuyo id sea testButton
        binding.testButton.setOnClickListener(view -> {
            //lanzar el intent, se tiene que abrir una nueva pantalla que permita navegar
            //por los archivos del movil y permite seleccionar una imagen
            //al pulsar sobre la imagen android dispara el metodo onActivityResult
            launcher.launch(intent);
        });
        launcher.launch(intent);
        return root;
    }

   /*
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_img);

        imageSelected = findViewById(R.id.selected_img);

        Button selectImgButton = (Button) findViewById(R.id.button_select_img);
        selectImgButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(
                            SelectImgActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION
                    );
                }else{
                    selectImg();
                }
            }
        });
    }

    */

    public void selectImg() {

        try {
            /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);*/
            Intent intent = new Intent();
            intent.setType("image/");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Complete Action Using "), REQUEST_CODE_SELECT_IMAGE);
            /*if(intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
            }*/
        } catch (Exception exception) {
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectImg();
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                Uri selectedImageUrl = data.getData();
                if(selectedImageUrl != null){
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(selectedImageUrl);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imageSelected.setImageBitmap(bitmap);
                    } catch (Exception exception){
                        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

   */
}