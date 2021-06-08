package com.example.simulacro_pc03;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simulacro_pc03.adapter.UsuarioAdapter;
import com.example.simulacro_pc03.api.ServiceUsuario;
import com.example.simulacro_pc03.entity.Usuario;
import com.example.simulacro_pc03.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioListaCrudActivity extends AppCompatActivity {

    List<Usuario> lstData = new ArrayList<Usuario>();
    UsuarioAdapter adaptador = null;
    ListView lstView = null;
    ServiceUsuario api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_lista_crud);

        lstView = findViewById(R.id.idCrudUsuarioList);
        adaptador = new UsuarioAdapter(this, R.layout.activity_usuario_crud_item, lstData);
        lstView.setAdapter(adaptador);

        api = ConnectionRest.getConnection().create(ServiceUsuario.class);

        lista();

    }

    public void lista(){
        mensaje("LOG -> En método lista 1");

        Call<List<Usuario>> call = api.listaUsuario();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                mensaje("LOG -> En método lista 2");

                if (response.isSuccessful()){
                    mensaje("LOG -> En método lista 3");
                    List<Usuario> lista = response.body();
                    lstData.clear();
                    lstData.addAll(lista);
                    adaptador.notifyDataSetChanged();
                } else {
                    mensaje("ERROR -> " +  "Error en la respuesta");
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                mensaje("ERROR -> " +  "Error en la respuesta");
            }
        });
    }

    void mensaje(String msg){
        Toast toast1 = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast1.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.idCrudUsuario){
            Intent intent = new Intent(this,UsuarioListaCrudActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}