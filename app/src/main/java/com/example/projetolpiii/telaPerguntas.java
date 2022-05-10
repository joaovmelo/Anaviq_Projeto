package com.example.projetolpiii;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.projetolpiii.entidades.Pergunta;
import com.example.projetolpiii.servicos.Consultar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class telaPerguntas extends AppCompatActivity {

    Random rd = new Random();
    List<Integer> lista = new ArrayList<>();
    List<Integer> listaIds = Arrays.asList(1, 2, 4, 37, 38, 39, 40, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 55);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perguntas);

        try {
            int id = listaIds.get(rd.nextInt(listaIds.size()));
            List<Pergunta> p = new Consultar(id).execute().get();

            gerarLayout(p);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> gerarNumeros(int tamanho){
        List<Integer> numeros = new ArrayList<>();
        while(numeros.size() < tamanho){
            int x = rd.nextInt(tamanho);
            if(!numeros.contains(x)){
                numeros.add(x);
            }
        }
        return (ArrayList<Integer>) numeros;
    }

    private void gerarLayout(List<Pergunta> p){
        final TextView txtPergunta = (TextView) findViewById(R.id.txtPergunta);
        final TextView txtExplicacao = (TextView) findViewById(R.id.txtExplicacao);
        final TextView txtA = (TextView) findViewById(R.id.txtA);
        final TextView txtB = (TextView) findViewById(R.id.txtB);
        final TextView txtC = (TextView) findViewById(R.id.txtC);
        final TextView txtD = (TextView) findViewById(R.id.txtD);
        final Button btnA = (Button) findViewById(R.id.btnA);
        final Button btnB = (Button) findViewById(R.id.btnB);
        final Button btnC = (Button) findViewById(R.id.btnC);
        final Button btnD = (Button) findViewById(R.id.btnD);
        final TextView txtPergunta5 = (TextView) findViewById(R.id.txtPergunta5);

        if(p.get(0).getRespostas().size() == 4){
            txtExplicacao.setVisibility(View.INVISIBLE);
            lista = gerarNumeros(p.get(0).getRespostas().size());
            txtPergunta.setText(p.get(0).getPergunta());
            txtA.setText(p.get(0).getRespostas().get(lista.get(0)).getResposta());
            txtB.setText(p.get(0).getRespostas().get(lista.get(1)).getResposta());
            txtC.setText(p.get(0).getRespostas().get(lista.get(2)).getResposta());
            txtD.setText(p.get(0).getRespostas().get(lista.get(3)).getResposta());
        }else{
            txtD.setVisibility(View.INVISIBLE);
            txtPergunta5.setVisibility(View.INVISIBLE);
            btnD.setVisibility(View.INVISIBLE);
            txtExplicacao.setVisibility(View.INVISIBLE);
            lista = gerarNumeros(p.get(0).getRespostas().size());
            txtPergunta.setText(p.get(0).getPergunta());
            txtA.setText(p.get(0).getRespostas().get(lista.get(0)).getResposta());
            txtB.setText(p.get(0).getRespostas().get(lista.get(1)).getResposta());
            txtC.setText(p.get(0).getRespostas().get(lista.get(2)).getResposta());
        }
    }
}