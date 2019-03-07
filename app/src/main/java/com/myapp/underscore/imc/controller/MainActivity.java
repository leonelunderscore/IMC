package com.myapp.underscore.imc.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.myapp.underscore.imc.R;
import com.myapp.underscore.imc.model.DbHelper;
import com.myapp.underscore.imc.model.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private DbHelper db;
    private String sexe;
    private String status;
    private String nomComplet;
    private double taille;
    private double poids;


    private EditText txt_fullname;
    private EditText txt_poids;
    private EditText txt_taille;
    private RadioButton rad_centimetres;
    private RadioButton rad_metres;
    private Button btn_refresh;
    private Button btn_calculate;

    private Spinner spinner_sex;
    private Spinner spinner_status;

    private ArrayList<Person> people;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        people = new ArrayList<>();
        db = new DbHelper(this);
        txt_fullname = findViewById(R.id.txt_fullname);
        txt_poids = findViewById(R.id.txt_poids);
        txt_taille = findViewById(R.id.txt_taille);
        rad_centimetres = findViewById(R.id.centimeters);
        rad_metres = findViewById(R.id.meters);
        btn_calculate = findViewById(R.id.btn_calculate);
        btn_refresh = findViewById(R.id.btn_refresh);

        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkFields())
                    Toast.makeText(getApplicationContext(),"Remplir tous les champs",Toast.LENGTH_SHORT).show();
                else {
                    nomComplet = txt_fullname.getText().toString();
                    taille = getExactTaille();
                    poids = Double.parseDouble(txt_poids.getText().toString());
                    
                    Person person = new Person(nomComplet,sexe,status,taille,poids);

                    //save(person);

                    String msg = "";
                    if(!db.CreatePerson(person))
                        msg += "Des erreurs sont survenues!";
                    else{
                        msg += "Nom complet : "+person.getNomComplet()+"\n";
                        msg += "Taille : "+person.getTaille()+"m\n";
                        msg += "Poids : "+person.getPoids()+"kg\n";
                        msg += "Sexe : "+person.getSexe()+"\n";
                        msg += "Status : "+person.getStatus()+"\n";
                        msg += "IMC : "+person.getImc()+"\n";
                        msg += "Remarque : "+person.getDescription()+"\n";
                        msg += "Date de calcul : "+person.getDate();
                        people = db.getAllPeople();
                    }

                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle( "IMC DETAILS" )
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .setMessage(msg)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialoginterface, int i) {
                                    Toast.makeText(MainActivity.this,db.getPeopleCount()+" persons",Toast.LENGTH_LONG).show();
                                }
                            }).show();
                }
            }
        });

        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshFields();
            }
        });

        rad_metres.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    txt_taille.setHint("Taille en m");
                else
                    txt_taille.setHint("Taille en cm");
            }
        });
        spinner_status = findViewById(R.id.status_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.status,R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_status.setAdapter(adapter);
        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_sex = findViewById(R.id.sexes_spinner);
        ArrayAdapter<CharSequence> adapter_sex = ArrayAdapter.createFromResource(this,R.array.sexes,R.layout.spinner_item);
        adapter_sex.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner_sex.setAdapter(adapter_sex);
        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexe = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private String SerializePerson(Person p){
        String str = "{";
        str += p.getNomComplet();
        str += "|"+p.getSexe();
        str += "|"+p.getStatus();
        str += "|"+p.getTaille();
        str += "|"+p.getPoids();
        str += "|"+p.getImc();
        str += "|"+p.getDescription();
        str += "|"+p.getImcDate();

        return  str;
    }
    private void save(Person person) {
        File path = getApplicationContext().getFilesDir();
        File file = new File(path,"persons");
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(file,true);
            stream.write(SerializePerson(person).getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(stream != null)
                    stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshFields() {
        this.txt_fullname.setText("");
        this.txt_taille.setText("");
        this.txt_poids.setText("");
        this.spinner_status.setSelection(0);
        this.spinner_sex.setSelection(0);
        rad_centimetres.setChecked(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.log_consult:
                startActivity(new Intent(this,ImcLogActivity.class));
                break;
            case R.id.drop_db:
                String str = "";
                if(db.emptyTable("persons")){
                    str = "Table cleared!";
                }
                else{
                    str = "Error while clearing table";
                }
                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private double getExactTaille(){
        double p = 0.0;
        if(rad_centimetres.isChecked())
            p = Double.parseDouble(txt_taille.getText().toString()) / 100;
        else
        {
            if(Double.parseDouble(txt_taille.getText().toString()) > 2.0)
                p = Double.parseDouble(txt_taille.getText().toString()) / 100;
            else
                p = Double.parseDouble(txt_taille.getText().toString());
        }
        return p;
    }

    private boolean checkFields(){
        return !this.txt_taille.getText().toString().trim().equals("") &&
                !this.txt_fullname.getText().toString().trim().equals("") &&
                !this.txt_poids.getText().toString().trim().equals("");
    }
}
