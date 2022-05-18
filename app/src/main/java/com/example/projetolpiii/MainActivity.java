package com.example.projetolpiii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnIniciarJogo = (Button) findViewById(R.id.btnIniciarJogo);
        Button btnComoJogar =  (Button) findViewById(R.id.btnComoJogar);
        Button btnSobre =  (Button) findViewById(R.id.btnSobre);
        Button btnSair = (Button) findViewById(R.id.btnSair);


        btnIniciarJogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, telaPerguntas.class);
                startActivity(intent);
            }
        });

        btnComoJogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, telaComoJogar.class);
                startActivity(intent);
            }
        });

        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, telaSobre.class);
                startActivity(intent);
            }
        });


        //Botão para fechar a aplicação
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });
    }
}