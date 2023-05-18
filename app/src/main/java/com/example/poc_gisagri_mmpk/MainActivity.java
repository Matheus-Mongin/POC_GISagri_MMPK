package com.example.poc_gisagri_mmpk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.esri.arcgisruntime.loadable.LoadStatus;
import com.esri.arcgisruntime.mapping.MobileMapPackage;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.example.poc_gisagri_mmpk.databinding.ActivityMainBinding;

import java.time.Duration;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MapView mapView;
    private MobileMapPackage mmpk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapView = binding.mapView;
        loadMobileMapPackage(getExternalFilesDir(null).getPath() + getString(R.string.teste_mmpk));
    }

    /**
     * @param filePath o arquivo de mapa para testes está dentro do diretório do projeto
     *                 na pasta teste_mapa.
     * Salvar o arquivo dentro do diretório files da aplicação no dispositivo.
     */
   private void loadMobileMapPackage(String filePath) {
        mmpk = new MobileMapPackage(filePath);

        mmpk.loadAsync();
        mmpk.addDoneLoadingListener(() -> {
            if (mmpk.getLoadStatus() == LoadStatus.LOADED && !mmpk.getMaps().isEmpty()) {
                mapView.setMap(mmpk.getMaps().get(0));
            }
            else {
                Toast.makeText(getApplicationContext(), "Failed to load the mobile map package", Toast.LENGTH_LONG).show();
            }
        });
   }

    @Override
    protected void onPause() {
        mapView.pause();
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mapView.resume();
    }

    @Override
    protected void onDestroy() {
        mapView.dispose();
        super.onDestroy();
    }
}