package models;

public class GiayTheThao extends SanPham {
    private double kichCoGiay;
    private String chatLieu;
    private String loaiDeGiay;

    //không tham số
    public GiayTheThao() {
        kichCoGiay=0;
        chatLieu="";
        loaiDeGiay="";
    }

    //có tham số
    public GiayTheThao(String maSanPham, String tenSanPham, String thuongHieu, String mauSac, double giaBan, int soLuongTon, LoaiSP loaiSP, double kichCoGiay, String chatLieu, String loaiDeGiay) {
        super(maSanPham, tenSanPham, thuongHieu, mauSac, giaBan, soLuongTon, loaiSP);
        this.kichCoGiay=kichCoGiay;
        this.chatLieu = chatLieu;
        this.loaiDeGiay = loaiDeGiay;
    }
    public double getKichCoGiay(){
        return kichCoGiay;
    }
    public void setKichCoGiay(double kichCoGiay){
        this.kichCoGiay = kichCoGiay;
    }
    public String getChatLieu(){
        return chatLieu;
    }
    public void setChatLieu(String chatLieu){
        this.chatLieu = chatLieu;
    }
    public String getLoaiDeGiay(){
        return loaiDeGiay;
    }
    public void setLoaiDeGiay(String loaiDeGiay){
        this.loaiDeGiay = loaiDeGiay;
    }

}
