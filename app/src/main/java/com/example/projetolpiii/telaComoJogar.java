package com.example.projetolpiii;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class telaComoJogar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtoplay);

        //Botão de return
        ImageButton btnReturn = (ImageButton) findViewById(R.id.btn_return3);

        btnReturn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startActivity(new Intent(telaComoJogar.this, MainActivity.class));
            }
        });

        //setContentView(R.layout.activity_tela_como_jogar);
    }
}