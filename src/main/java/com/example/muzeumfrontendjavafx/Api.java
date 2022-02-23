package com.example.muzeumfrontendjavafx;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Api {
    private static final String Url = "http://localhost:8000/api/";
    public static final String statuesUrl = Url+"statues";
    public static final String paintingsUrl = Url+"paintings";

    public static List<Painting> getPaintings() throws Exception {
        List<Painting> paintingList = new ArrayList<>();
        JSONArray array = arrayFromUrl(new URL(paintingsUrl));
        for(Object object: array){
            JSONObject json = (JSONObject) object;
            int id = Integer.parseInt(json.get("id").toString());
            String title = json.get("title").toString();
            int year = Integer.parseInt(json.get("year").toString());
            boolean onDisplay = Boolean.parseBoolean(json.get("on_display").toString());
            paintingList.add(new Painting(id,title,year,onDisplay));
        }
        return paintingList;
    }
    public static List<Statue> getStatues() throws Exception {
        List<Statue> statueList = new ArrayList<>();
        JSONArray array = arrayFromUrl(new URL(statuesUrl));
        for(Object object: array){
            JSONObject json = (JSONObject) object;
            int id = Integer.parseInt(json.get("id").toString());
            String person = json.get("person").toString();
            int height = Integer.parseInt(json.get("height").toString());
            int price = Integer.parseInt(json.get("price").toString());
            statueList.add(new Statue(id,person,height,price));
        }
        return statueList;
    }
    public static JSONArray arrayFromUrl(URL url) throws Exception{
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        int responseCode = conn.getResponseCode();
        if (responseCode != 200){
            throw new RuntimeException("Response: "+responseCode);
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String row = br.readLine();
        br.close();
        JSONParser parser = new JSONParser();
        return (JSONArray) parser.parse(String.valueOf(row));
    }

    public static boolean addStatue(Statue newStatue) throws IOException {
        String newString = newStatue.toJson();
        URL url = new URL(statuesUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.connect();
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        wr.write(newString);
        wr.flush();
        wr.close();
        int responseCode = conn.getResponseCode();
        return responseCode < 300;
    }
    public static boolean addPainting(Painting newPainting) throws IOException  {
        String newString = newPainting.toJson();
        URL url = new URL(paintingsUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.connect();
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        wr.write(newString);
        wr.flush();
        wr.close();
        int responseCode = conn.getResponseCode();
        return responseCode < 300;
    }
    public static boolean changePainting(Painting chPainting) throws Exception {
        String changeString = chPainting.toJson();
        URL url = new URL(paintingsUrl+"/"+chPainting.getId());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.connect();
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        wr.write(changeString);
        wr.flush();
        wr.close();
        int responseCode = conn.getResponseCode();
        return responseCode < 300;
    }
    public static boolean changeStatue(Statue chStatue) throws Exception {
        String changeString = chStatue.toJson();
        URL url = new URL(paintingsUrl+"/"+chStatue.getId());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setDoOutput(true);
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.connect();
        BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        wr.write(changeString);
        wr.flush();
        wr.close();
        int responseCode = conn.getResponseCode();
        return responseCode < 300;
    }
    public static boolean deletePainting(Integer id) throws Exception {
        URL url = new URL(paintingsUrl+"/"+id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("DELETE");
        conn.connect();
        int responseCode = conn.getResponseCode();
        return responseCode == 204;
    }
    public static boolean deleteStatue(Integer id) throws Exception {
        URL url = new URL(statuesUrl+"/"+id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("DELETE");
        conn.connect();
        int responseCode = conn.getResponseCode();
        return responseCode == 204;
    }


}
