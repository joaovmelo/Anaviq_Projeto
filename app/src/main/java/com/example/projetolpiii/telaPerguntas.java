package com.example.projetolpiii;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetolpiii.entidades.Pergunta;
import com.example.projetolpiii.entidades.Resposta;
import com.example.projetolpiii.servicos.Consultar;
import com.example.projetolpiii.servicos.ConsultarIds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class telaPerguntas extends AppCompatActivity {

    private TextView txtPergunta;
    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;
    private ImageView coracao;
    private ImageView coracao2;
    private ImageView coracao3;
    private Chronometer ch;
    List<Pergunta> p;

    String correta = "";
    int id = 0;
    Random rd = new Random();
    List<Integer> lista = new ArrayList<>();
    List<Integer> listaIds = new ArrayList<>();
    List<Integer> guardarIds = new ArrayList<>();
    List<ImageView> listaCoracoes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perguntas);

        txtPergunta = (TextView) findViewById(R.id.txtPergunta);
        btnA = (Button) findViewById(R.id.btnA);
        btnB = (Button) findViewById(R.id.btnB);
        btnC = (Button) findViewById(R.id.btnC);
        btnD = (Button) findViewById(R.id.btnD);
        coracao = (ImageView) findViewById(R.id.imgCoracao);
        coracao2 = (ImageView) findViewById(R.id.imgCoracao2);
        coracao3 = (ImageView) findViewById(R.id.imgCoracao3);
        ch = (Chronometer) findViewById(R.id.cronometro);
        ch.setBase(SystemClock.elapsedRealtime());
        ch.start();

        listaCoracoes.add(coracao);
        listaCoracoes.add(coracao2);
        listaCoracoes.add(coracao3);

        btnA.setVisibility(View.INVISIBLE);
        btnB.setVisibility(View.INVISIBLE);
        btnC.setVisibility(View.INVISIBLE);
        btnD.setVisibility(View.INVISIBLE);

        try {
            listaIds = new ConsultarIds().execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        gerarLayout();
    }

    public void gerarLayout(){
        if(listaCoracoes.isEmpty()){
            encerrarQuiz();
        }
        if(guardarIds.size() == listaIds.size()){
            encerrarQuiz();
        }

        try {
            id = listaIds.get(rd.nextInt(listaIds.size()));
            p = new Consultar(id).execute().get();

            while(guardarIds.contains(p.get(0).getId_pergunta())){
                id = listaIds.get(rd.nextInt(listaIds.size()));
                p = new Consultar(id).execute().get();
            }
            guardarIds.add(p.get(0).getId_pergunta());

            for(Resposta r : p.get(0).getRespostas()){
                if(r.getCorreta()){
                    correta = r.getResposta();
                }
            }

            if(p.get(0).getRespostas().size() == 4){
                btnA.setVisibility(View.VISIBLE);
                btnB.setVisibility(View.VISIBLE);
                btnC.setVisibility(View.VISIBLE);
                btnD.setVisibility(View.VISIBLE);
                lista = gerarNumeros(p.get(0).getRespostas().size());
                txtPergunta.setText(p.get(0).getPergunta());
                btnA.setText(p.get(0).getRespostas().get(lista.get(0)).getResposta());
                btnB.setText(p.get(0).getRespostas().get(lista.get(1)).getResposta());
                btnC.setText(p.get(0).getRespostas().get(lista.get(2)).getResposta());
                btnD.setText(p.get(0).getRespostas().get(lista.get(3)).getResposta());
            }else if(p.get(0).getRespostas().size() == 3){
                btnD.setVisibility(View.INVISIBLE);
                btnA.setVisibility(View.VISIBLE);
                btnB.setVisibility(View.VISIBLE);
                btnC.setVisibility(View.VISIBLE);
                lista = gerarNumeros(p.get(0).getRespostas().size());
                txtPergunta.setText(p.get(0).getPergunta());
                btnA.setText(p.get(0).getRespostas().get(lista.get(0)).getResposta());
                btnB.setText(p.get(0).getRespostas().get(lista.get(1)).getResposta());
                btnC.setText(p.get(0).getRespostas().get(lista.get(2)).getResposta());
            }else{
                btnC.setVisibility(View.INVISIBLE);
                btnD.setVisibility(View.INVISIBLE);
                btnA.setVisibility(View.VISIBLE);
                btnB.setVisibility(View.VISIBLE);
                lista = gerarNumeros(p.get(0).getRespostas().size());
                txtPergunta.setText(p.get(0).getPergunta());
                btnA.setText(p.get(0).getRespostas().get(lista.get(0)).getResposta());
                btnB.setText(p.get(0).getRespostas().get(lista.get(1)).getResposta());
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void verificar(View view){
        Button btn = (Button) view;
        if(btn.getText() == correta){
            gerarLayout();
        }else{
            listaCoracoes.get(0).setVisibility(View.INVISIBLE);
            listaCoracoes.remove(0);
            AlertDialog.Builder encerrar = new AlertDialog.Builder(this);
            encerrar.setTitle("Resposta correta");
            encerrar.setMessage(p.get(0).getExplicacao());
            encerrar.setCancelable(false);
            encerrar.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    gerarLayout();
                }
            });
            encerrar.create().show();
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

    private void encerrarQuiz(){
        AlertDialog.Builder encerrar = new AlertDialog.Builder(this);
        ch.stop();
        if(listaCoracoes.size() == 0){
            encerrar.setTitle("Fim de jogo!!!");
            encerrar.setMessage("Infelizmente voce perdeu todas as suas vidas.\n" +
                    "Clique em continuar para voltar ao menu principal e tente novamente.");
            encerrar.setCancelable(false);
            encerrar.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(telaPerguntas.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            encerrar.create().show();
        }else{
            encerrar.setTitle("Voce terminou o quiz!!!");
            encerrar.setMessage("Você venceu!!!\nTempo decorrido: " + ch.getText() +"\nClique em continuar para voltar ao menu principal.");
            encerrar.setCancelable(false);
            encerrar.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(telaPerguntas.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            encerrar.create().show();
        }
    }
}