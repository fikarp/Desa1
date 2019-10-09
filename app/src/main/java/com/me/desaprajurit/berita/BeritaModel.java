package com.me.desaprajurit.berita;

public class BeritaModel {

    private String sId, sJudul, sBerita, sFoto, sStatus, sUserInput;


    public BeritaModel(){

    }

    public BeritaModel(String sId, String sJudul, String sBerita, String sFoto, String sStatus, String sUserInput){
        this.sId = sId;
        this.sJudul = sJudul;
        this.sBerita = sBerita;
        this.sFoto = sFoto;
        this.sStatus = sStatus;
        this.sUserInput = sUserInput;
    }

    public String getsId(){
        return sId;
    }

    public  void setsId(String sId){
        this.sId = sId;
    }

    public String getsJudul(){
        return sJudul;
    }

    public void setsJudul(String sJudul){
        this.sJudul = sJudul;
    }

    public String getsBerita(){
        return sBerita;
    }

    public void setsBerita(String sBerita){
        this.sBerita = sBerita;
    }

    public String getsFoto(){
        return sFoto;
    }
    public void setsFoto(String sFoto){
        this.sFoto = sFoto;
    }

    public String getsStatus(){
        return sStatus;
    }

    public void setsStatus(String sStatus){
        this.sStatus = sStatus;
    }

    public String getsUserInput(){
        return sUserInput;
    }

    public void setsUserInput(String sUserInput){
        this.sUserInput = sUserInput;
    }
}
