package models;

public abstract class SanPham {
    private String maSanPham;
    private String tenSanPham;
    private String thuongHieu;
    private String mauSac;
    private double giaBan;
    private int soLuongTon;
    private LoaiSP loaiSP; 

    //Không tham số
    public SanPham(){
        maSanPham ="";
        tenSanPham="";
        thuongHieu="";
        mauSac="";
        giaBan=0;
        soLuongTon=0;
        loaiSP=null;
    }
    //Có tham số
    public SanPham(String maSanPham, String tenSanPham, String thuongHieu,String mauSac ,double giaBan, int soLuongTon, LoaiSP loaiSP){
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.thuongHieu = thuongHieu;
        this.mauSac = mauSac;
        this.giaBan = giaBan;
        this.soLuongTon = soLuongTon;
        this.loaiSP = loaiSP;
    }
    public String getMaSanPham(){
        return maSanPham;
    }
    public void setMaSanPham(String maSanPham){
        this.maSanPham = maSanPham;
    }
    public String getTenSanPham(){
        return tenSanPham;
    }
    public void setTenSanPham(String tenSanPham){
        this.tenSanPham=tenSanPham;
    }public String getThuongHieu(){
        return thuongHieu;
    }
    public void setThuongHieu(String thuongHieu){
        this.thuongHieu=thuongHieu;
    }
    public double getGiaBan(){
        return giaBan;
    }
    public void setGiaBan(double giaBan){
        this.giaBan=giaBan;
    }
    public String getMauSac(){
        return mauSac;
    }
    public void setMauSac(String mauSac){
        this.mauSac=mauSac;
    }
    public int getSoLuongTon(){
        return soLuongTon;
    }
    public void setSoLuongTon(int soLuongTon){
        this.soLuongTon=soLuongTon;
    }
    public LoaiSP getLoaiSP(){
        return loaiSP;
    }
    public void setLoaiSP(LoaiSP loaiSP){
        this.loaiSP=loaiSP;
    }
    
}