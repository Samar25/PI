/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author Samar
 */
public class Product {

    private int id;
    private String nom, categorie;
    private float prix;
    private String description;

    public Product(int id, String nom, String categorie, float prix, String description) {
        this.id = id;
        this.nom = nom;

        this.categorie = categorie;
        this.prix = prix;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public float getPrix() {
        return prix;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
