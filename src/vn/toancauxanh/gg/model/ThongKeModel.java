package vn.toancauxanh.gg.model;

import java.util.ArrayList;
import java.util.List;

public class ThongKeModel {

	public ThongKeModel() {

	}
	
	public ThongKeModel(List<SoLieuModel> _soLieus) {
		this.soLieus = _soLieus;
	}
	
	private DonViHanhChinh donViHanhChinh;
	private ToDanPho toDanPho;
	private boolean ngoaiDiaBan;
	private DonVi donVi;
	private List<SoLieuModel> soLieus=new ArrayList<SoLieuModel>();
	public DonViHanhChinh getDonViHanhChinh() {
		return donViHanhChinh;
	}
	public void setDonViHanhChinh(DonViHanhChinh donViHanhChinh) {
		this.donViHanhChinh = donViHanhChinh;
	}
	public List<SoLieuModel> getSoLieus() {
		return soLieus;
	}
	public void setSoLieus(List<SoLieuModel> soLieus) {
		this.soLieus = soLieus;
	}

	public ToDanPho getToDanPho() {
		return toDanPho;
	}

	public void setToDanPho(ToDanPho toDanPho) {
		this.toDanPho = toDanPho;
	}

	public boolean isNgoaiDiaBan() {
		return ngoaiDiaBan;
	}

	public void setNgoaiDiaBan(boolean ngoaiDiaBan) {
		this.ngoaiDiaBan = ngoaiDiaBan;
	}

	public DonVi getDonVi() {
		return donVi;
	}

	public void setDonVi(DonVi donVi) {
		this.donVi = donVi;
	}
}
