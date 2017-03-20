package vn.toancauxanh.gg.model;

public class SoLieuModel {
	
	public SoLieuModel () {
		
	}
	
	public SoLieuModel(HinhThucXuLy hinhThucXuLy, long giaTri) {
		this.hinhThucXuLy = hinhThucXuLy;
		this.giaTri = giaTri;
	}
	
	private HinhThucXuLy hinhThucXuLy;
	private long giaTri;
	public HinhThucXuLy getHinhThucXuLy() {
		return hinhThucXuLy;
	}
	public void setHinhThucXuLy(HinhThucXuLy hinhThucXuLy) {
		this.hinhThucXuLy = hinhThucXuLy;
	}
	public long getGiaTri() {
		return giaTri;
	}
	public void setGiaTri(long giaTri) {
		this.giaTri = giaTri;
	}
}
