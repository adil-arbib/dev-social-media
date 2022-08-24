package com.arbib.admin_panel.objects;

public class Skill {
    private String id;
    private String libelle;
    private String description;

    public Skill(String id, String libelle, String description) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
    }

    public Skill(String libelle, String description) {
        this.libelle = libelle;
        this.description = description;
    }

    public Skill(Skill skill){
        this.libelle = skill.getLibelle();
        this.description = skill.getDescription();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
