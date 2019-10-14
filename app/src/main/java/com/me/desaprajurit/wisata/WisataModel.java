package com.me.desaprajurit.wisata;

public class WisataModel {
    private String sId, sNamaWisata, sKeterangan, sFoto, sVideo, sAlamat, sSejarah,
    sLatitude, sLongitude, sUser;

    public WisataModel(){

    }

    public WisataModel(String sId, String sNamaWisata, String sKeterangan, String sFoto, String sVideo, String sUser){
        this.sId = sId;
        this.sNamaWisata = sNamaWisata;
        this.sKeterangan = sKeterangan;
        this.sFoto = sFoto;
        this.sVideo = sVideo;
        this.sUser = sUser;
    }

    public String getsId(){
        return sId;
    }

    public  void setsId(String sId){
        this.sId = sId;
    }

    public String getsNamaWisata(){
        return sNamaWisata;
    }

    public void setsNamaWisata(String sNamaWisata){
        this.sNamaWisata = sNamaWisata;
    }

    public String getsKeterangan(){
        return sKeterangan;
    }

    public void setsKeterangan(String sKeterangan){
        this.sKeterangan = sKeterangan;
    }

    public String getsFoto(){
        return sFoto;
    }
    public void setsFoto(String sFoto){
        this.sFoto = sFoto;
    }

    public String getsSejarah(){
        return sSejarah;
    }

    public void setsSejarah(String sSejarah){
        this.sSejarah = sSejarah;
    }


    public String getsUser(){
        return sUser;
    }

    public void setsUser(String sUser){
        this.sUser = sUser;
    }

    public String getsVideo(){
        return sVideo;
    }

    public void setsVideo(String sVideo){
        this.sVideo = sVideo;
    }

    public String getsAlamat(){
        return sAlamat;
    }

    public void setsAlamat(String sAlamat){
        this.sAlamat = sAlamat;
    }


    public String getsLatitude(){
        return sLatitude;
    }

    public void setsLatitude(String sLatitude){
        this.sLatitude = sLatitude;
    }

    public String getsLongitude(){
        return sLongitude;
    }

    public void setsLongitude(String sLongitude){
        this.sLongitude = sLongitude;
    }
}
