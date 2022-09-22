package com.meicansoftware.roomdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ControleDeUsuarios extends AppCompatActivity {
    private AppDatabase db;
    private EditText edtUsuario;
    private EditText edtEmail;
    private Button btnSalvar, btnExcluir;
    private int dbUsuarioId;
    private Usuario dbUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controle_de_usuarios);

        db = AppDatabase.getDatabase(getApplicationContext());
        edtUsuario = findViewById(R.id.edtUsuario);
        edtEmail = findViewById(R.id.edtEmail);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnExcluir = findViewById(R.id.btnExcluir);

        dbUsuarioId = getIntent().getIntExtra("usuario_id", -1);

    }
    protected void onResume(){
        super.onResume();
        if(dbUsuarioId >= 0){
            getUsuario();
        }else{
            btnExcluir.setVisibility(View.GONE);
        }
    }

    private void getUsuario(){
        dbUsuario = db.usuarioDao().getUser(dbUsuarioId);
        edtUsuario.setText(dbUsuario.getNome());
        edtEmail.setText(dbUsuario.getEmail());
    }

    public void salvarUsuario(View view){
        String nome = edtUsuario.getText().toString();
        String email = edtEmail.getText().toString();

        if(nome.equals("")||email.equals("")){
            Toast.makeText(this, "Preencher todos os campos!",
                    Toast.LENGTH_SHORT).show();
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);

        if(dbUsuario != null){
            usuario.setId(dbUsuarioId);
            db.usuarioDao().update(usuario);
            Toast.makeText(this, "Atualizado com sucesso.", Toast.LENGTH_SHORT).show();
        }else{
            db.usuarioDao().InsertAll(usuario);
            Toast.makeText(this, "Usuário inserido com sucesso", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    public void excluirUsuario(View view){
        new AlertDialog.Builder(this)
                .setTitle("Exclusão de usuário")
                .setMessage("Você deseja excluir esse usuário?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        excluir();
                    }
                })
                .setNegativeButton("Não", null)
                .show();
    }
    private void excluir(){
        try{
            db.usuarioDao().delete(dbUsuario);
            Toast.makeText(this, "Excluido com sucesso", Toast.LENGTH_SHORT).show();
        }catch (SQLiteConstraintException e){
            Toast.makeText(this, "Impossível excluir!", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    public void voltar(View view){
        finish();
    }
}
