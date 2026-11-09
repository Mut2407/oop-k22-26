package models;

public class LoaiSP {
    private String maLoaiSP;
    private String tenLoaiSP;

    //không tham số
    public LoaiSP(){
        maLoaiSP="";
        tenLoaiSP="";
    }
    //có tham số
    public LoaiSP(String maLoaiSP, String tenLoaiSP){
        this.maLoaiSP=maLoaiSP;
        this.tenLoaiSP=tenLoaiSP;
    }
    public String getMaLoaiSP(){
        return maLoaiSP;
    }
    public void setMaLoaiSP(String maLoaiSP){
        this.maLoaiSP=maLoaiSP;
    }
    public String getTenLoaiSP(){
        return tenLoaiSP;
    }
    public void setTenLoaiSP(String tenLoaiSP){
        this.tenLoaiSP=tenLoaiSP;
    }
}
