package com.myapp.underscore.imc.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.myapp.underscore.imc.R;
import com.myapp.underscore.imc.model.Person;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImcLogActivity extends AppCompatActivity{

    private String str = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc_log);

        for (String s : getPersons(getDatas())){
            str += s;
            str += "\n";
        }

        ((TextView)findViewById(R.id.msg)).setText(str);

    }

    private String getDatas() {
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        BufferedReader in = null;

        try {
            in = new BufferedReader(new FileReader(new File(getApplication().getFilesDir(), "persons")));
            while ((line = in.readLine()) != null) stringBuilder.append(line);

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

        return stringBuilder.toString();
    }

    private Person DeserializePerson(String str){
        String[] parts= str.split("\\|");
        return new Person(parts[0],parts[1],parts[2],Double.parseDouble(parts[3]),Double.parseDouble(parts[4]),Float.parseFloat(parts[5]),parts[6],null);
    }

    private ArrayList<String> getPersons(String src){
        String[] parts = src.split("\\{");
        int i = 0;
        String p = null;
        ArrayList<String> ps = new ArrayList<>();
        for(i = 0; i< parts.length;i++){
            p = parts[i];
            ps.add(p);
        }
        return ps;
    }


}
