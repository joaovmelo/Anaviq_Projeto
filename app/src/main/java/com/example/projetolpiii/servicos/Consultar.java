package com.example.projetolpiii.servicos;

import android.os.AsyncTask;

import com.example.projetolpiii.entidades.Pergunta;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consultar extends AsyncTask<Void, Void, ArrayList<Pergunta>> {

    private Integer id_pergunta;

    public Consultar(Integer id_pergunta) {
        this.id_pergunta = id_pergunta;
    }

    @Override
    protected ArrayList<Pergunta> doInBackground(Void... voids) {

        String data = "";
        try {
            String url = "https://api-queimaduras.herokuapp.com/consultar-pergunta/" + this.id_pergunta;
            data = getJSON(url, 300);
        }catch (Exception e) {
            e.printStackTrace();
        }
        Type tipoLista = new TypeToken<ArrayList<Pergunta>>() {}.getType();
        return new Gson().fromJson(data, tipoLista);
    }

    public String getJSON(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }
}