package com.myapp.underscore.imc.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

public class Person {
    private String nomComplet;
    private String sexe;
    private String status;
    private double taille;
    private double poids;
    private double imc;
    private String description;
    private Calendar imcDate;

    public Person(String nomComplet, String sexe, String status, double taille, double poids) {
        this.nomComplet = nomComplet;
        this.sexe = sexe;
        this.status = status;
        this.taille = taille;
        this.poids = poids;
        //setImcDate(new Date(System.currentTimeMillis()));
        setImcDate(Calendar.getInstance());
    }

    public Person(){}

    public Person(String nom, String sexe, String status, double taille, double poid, double _imc, String desc, Calendar _imcDate){
        this.nomComplet = nom;
        this.sexe = sexe;
        this.status = status;
        this.taille = taille;
        this.poids = poid;
        this.imc = _imc;
        this.description = desc;
        this.imcDate = _imcDate;
    }
    @Override
    public String toString() {
        return "Person{" +
                "nomComplet='" + nomComplet + '\'' +
                ", sexe='" + sexe + '\'' +
                ", status='" + status + '\'' +
                ", taille=" + taille +" m"+
                ", poids=" + poids +" kg"+
                ", imc=" + String.format("%.2f",this.getImc()) +
                ", description='" + getDescription() + '\'' +
                ", imcDate=" + imcDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(nomComplet, person.nomComplet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nomComplet);
    }

    private double calculerImc(){
        return (this.getPoids() / (this.getTaille() * this.getTaille()));
    }
    public String getNomComplet() {
        return nomComplet;
    }

    private void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public String getSexe() {
        return sexe;
    }

    private void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getStatus() {
        return status;
    }

    private void setStatus(String status) {
        this.status = status;
    }

    public double getTaille() {
        return taille;
    }

    private void setTaille(double taille) {
        this.taille = taille;
    }

    public double getPoids() {
        return poids;
    }

    private void setPoids(double poids) {
        this.poids = poids;
    }

    public double getImc() {
        setImc(calculerImc());
        return this.imc;
    }

    private void setImc(double imc) {
        this.imc = imc;
    }

    public String getDescription() {
        this.imc = this.getImc();
        if(this.imc < 18.5f)
            this.setDescription("Maigre");
        else if (this.imc >= 18.5f && this.imc < 25.0f)
            this.setDescription("Normal");
        else if (this.imc >= 25.0f && this.imc < 30.0f)
            this.setDescription("Surpoids");
        else if(this.imc >= 30.0f && this.imc < 35.0f)
            this.setDescription("Obèse de classe 1");
        else if(this.imc >= 35.0f && this.imc < 40.0f)
            this.setDescription("Obèse de classe 2");
        else
            this.setDescription("Obèse de classe 3");

        return this.description;
    }

    private void setDescription(String description) {
        this.description = description;
    }

    public Calendar getImcDate() {
        return imcDate;
    }
    public String getDate(){
        SimpleDateFormat format = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm:ss");
        return format.format(getImcDate().getTime());
    }

    private void setImcDate(Calendar imcDate) {
        this.imcDate = imcDate;
    }
}
