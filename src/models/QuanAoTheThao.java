package models;

import java.math.BigDecimal;

public class QuanAoTheThao extends SanPham {
    private String kichCoQuanAo;
    private String mauSac;
    private String chatLieu;

    public QuanAoTheThao() {
        kichCoQuanAo = "";
        mauSac = "";
        chatLieu = "";
    }

    public QuanAoTheThao(String maSanPham, String tenSanPham, String thuongHieu,
            BigDecimal giaBan, LoaiSP loaiSP, String kichCoQuanAo, String mauSac, String chatLieu) {
        super(maSanPham, tenSanPham, thuongHieu, giaBan, loaiSP);
        setKichCoQuanAo(kichCoQuanAo);
        setMauSac(mauSac);
        setChatLieu(chatLieu);
    }

    public String getKichCoQuanAo() {
        return kichCoQuanAo;
    }

    public void setKichCoQuanAo(String kichCoQuanAo) {
        kiemTraChuoi(kichCoQuanAo);
        this.kichCoQuanAo = kichCoQuanAo;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        kiemTraChuoi(mauSac);
        this.mauSac = mauSac;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        kiemTraChuoi(chatLieu);
        this.chatLieu = chatLieu;
    }

    @Override
    public boolean kiemTraHopLe() {
        return super.kiemTraHopLe() && !kichCoQuanAo.isEmpty() && !mauSac.isEmpty() && !chatLieu.isEmpty();
    }

    @Override
    public void capNhatTu(SanPham moi) {
        super.capNhatTu(moi);
        QuanAoTheThao sp = (QuanAoTheThao) moi;
        setKichCoQuanAo(sp.getKichCoQuanAo());
        setMauSac(sp.getMauSac());
        setChatLieu(sp.getChatLieu());
    }

    @Override
    public String hienThiThongTin() {
        return "QuanAoTheThao | " + thongTinChung()
                + " | " + kichCoQuanAo + " | " + mauSac + " | " + chatLieu;
    }

    private static void kiemTraChuoi(String giaTri) {
        if (giaTri == null || giaTri.trim().isEmpty())
            throw new IllegalArgumentException("Thong tin khong duoc rong.");
        if (giaTri.contains("|") || giaTri.contains("\n") || giaTri.contains("\r"))
            throw new IllegalArgumentException("Khong nhap dau | hoac xuong dong.");
    }

}
