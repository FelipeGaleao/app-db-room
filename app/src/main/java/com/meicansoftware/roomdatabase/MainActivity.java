package com.meicansoftware.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;
    private List<Usuario> usuarios;
    private ListView listUsuarios;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(getApplicationContext());
        listUsuarios = findViewById(R.id.listViewUsuarios);
    }

    @Override
    protected void onResume(){
        super.onResume();
        intent = new Intent(this, ControleDeUsuarios.class);
        preencheUsuarios();
    }

    private void preencheUsuarios(){
        usuarios = db.usuarioDao().getAll();
        ArrayAdapter<Usuario> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, usuarios);
        listUsuarios.setAdapter(adapter);

        listUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Usuario usuarioselecionado = usuarios.get(position);
                intent.putExtra("usuario_id", usuarioselecionado.getId());
                startActivity(intent);
            }
        });
    }
    public void adicionaUsuario(View view){
        startActivity(intent);
    }
    public void voltar(View view){
        finish();
    }
}