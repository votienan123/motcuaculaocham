package vn.toancauxanh.gg.model.thongke;

import java.util.ArrayList;
import java.util.List;


public class ThongKePhanTichModel {
	private long soLieuNam;
	private long soLieuNu;
	private long soLieuDoTuoiDuoi16;
	private long soLieuDoTuoiTren16Duoi18;
	private long soLieuDoTuoiTren18Duoi30;
	private long soLieuDoTuoiTren30;
	private long soLieuCoViecLamOnDinh;
	private long soLieuCoViecLamKhongOnDinh;
	private long soLieuKhongViecLam;
	private long soLieuNghienMoi;
	private long soLieuTaiNghien;
	private List<NgheNghiepModel> listNgheNghiepModel = new ArrayList<NgheNghiepModel>();
	private List<GioiTinhModel> listGioiTinhModel = new ArrayList<GioiTinhModel>();
	private List<ThanhPhanGiaDinhModel> listThanhPhanGiaDinhModel = new ArrayList<ThanhPhanGiaDinhModel>();
	private List<MaTuySuDungModel> listMaTuySuDungModel = new ArrayList<MaTuySuDungModel>();
	private List<HinhThucXuLyModel> listHinhThucXuLyModel = new ArrayList<HinhThucXuLyModel>();
	private long soLieuNguoiDaNangCoDinh;
	private long soLieuNguoiDaNangKhongCoDinh;
	private long soLieuNguoiNgoaiTinh;
	private long soLieuTatCaNguoi;
	
	public long getSoLieuNam() {
		return soLieuNam;
	}
	public void setSoLieuNam(long soLieuNam) {
		this.soLieuNam = soLieuNam;
	}
	public long getSoLieuNu() {
		return soLieuNu;
	}
	public void setSoLieuNu(long soLieuNu) {
		this.soLieuNu = soLieuNu;
	}
	public long getSoLieuDoTuoiDuoi16() {
		return soLieuDoTuoiDuoi16;
	}
	public void setSoLieuDoTuoiDuoi16(long soLieuDoTuoiDuoi16) {
		this.soLieuDoTuoiDuoi16 = soLieuDoTuoiDuoi16;
	}
	public long getSoLieuDoTuoiTren16Duoi18() {
		return soLieuDoTuoiTren16Duoi18;
	}
	public void setSoLieuDoTuoiTren16Duoi18(long soLieuDoTuoiTren16Duoi18) {
		this.soLieuDoTuoiTren16Duoi18 = soLieuDoTuoiTren16Duoi18;
	}
	public long getSoLieuDoTuoiTren18Duoi30() {
		return soLieuDoTuoiTren18Duoi30;
	}
	public void setSoLieuDoTuoiTren18Duoi30(long soLieuDoTuoiTren18Duoi30) {
		this.soLieuDoTuoiTren18Duoi30 = soLieuDoTuoiTren18Duoi30;
	}
	public long getSoLieuDoTuoiTren30() {
		return soLieuDoTuoiTren30;
	}
	public void setSoLieuDoTuoiTren30(long soLieuDoTuoiTren30) {
		this.soLieuDoTuoiTren30 = soLieuDoTuoiTren30;
	}
	public long getSoLieuCoViecLamOnDinh() {
		return soLieuCoViecLamOnDinh;
	}
	public void setSoLieuCoViecLamOnDinh(long soLieuCoViecLamOnDinh) {
		this.soLieuCoViecLamOnDinh = soLieuCoViecLamOnDinh;
	}
	public long getSoLieuCoViecLamKhongOnDinh() {
		return soLieuCoViecLamKhongOnDinh;
	}
	public void setSoLieuCoViecLamKhongOnDinh(long soLieuCoViecLamKhongOnDinh) {
		this.soLieuCoViecLamKhongOnDinh = soLieuCoViecLamKhongOnDinh;
	}
	public long getSoLieuKhongViecLam() {
		return soLieuKhongViecLam;
	}
	public void setSoLieuKhongViecLam(long soLieuKhongViecLam) {
		this.soLieuKhongViecLam = soLieuKhongViecLam;
	}
	public List<NgheNghiepModel> getListNgheNghiepModel() {
		return listNgheNghiepModel;
	}
	public void setListNgheNghiepModel(List<NgheNghiepModel> listNgheNghiepModel) {
		this.listNgheNghiepModel = listNgheNghiepModel;
	}
	public List<GioiTinhModel> getListGioiTinhModel() {
		return listGioiTinhModel;
	}
	public void setListGioiTinhModel(List<GioiTinhModel> listGioiTinhModel) {
		this.listGioiTinhModel = listGioiTinhModel;
	}
	public List<ThanhPhanGiaDinhModel> getListThanhPhanGiaDinhModel() {
		return listThanhPhanGiaDinhModel;
	}
	public void setListThanhPhanGiaDinhModel(List<ThanhPhanGiaDinhModel> listThanhPhanGiaDinhModel) {
		this.listThanhPhanGiaDinhModel = listThanhPhanGiaDinhModel;
	}
	public long getSoLieuNghienMoi() {
		return soLieuNghienMoi;
	}
	public void setSoLieuNghienMoi(long soLieuNghienMoi) {
		this.soLieuNghienMoi = soLieuNghienMoi;
	}
	public long getSoLieuTaiNghien() {
		return soLieuTaiNghien;
	}
	public void setSoLieuTaiNghien(long soLieuTaiNghien) {
		this.soLieuTaiNghien = soLieuTaiNghien;
	}
	public List<MaTuySuDungModel> getListMaTuySuDungModel() {
		return listMaTuySuDungModel;
	}
	public void setListMaTuySuDungModel(List<MaTuySuDungModel> listMaTuySuDungModel) {
		this.listMaTuySuDungModel = listMaTuySuDungModel;
	}
	public List<HinhThucXuLyModel> getListHinhThucXuLyModel() {
		return listHinhThucXuLyModel;
	}
	public void setListHinhThucXuLyModel(List<HinhThucXuLyModel> listHinhThucXuLyModel) {
		this.listHinhThucXuLyModel = listHinhThucXuLyModel;
	}
	public long getSoLieuNguoiDaNangCoDinh() {
		return soLieuNguoiDaNangCoDinh;
	}
	public void setSoLieuNguoiDaNangCoDinh(long soLieuNguoiDaNangCoDinh) {
		this.soLieuNguoiDaNangCoDinh = soLieuNguoiDaNangCoDinh;
	}
	public long getSoLieuNguoiDaNangKhongCoDinh() {
		return soLieuNguoiDaNangKhongCoDinh;
	}
	public void setSoLieuNguoiDaNangKhongCoDinh(long soLieuNguoiDaNangKhongCoDinh) {
		this.soLieuNguoiDaNangKhongCoDinh = soLieuNguoiDaNangKhongCoDinh;
	}
	public long getSoLieuNguoiNgoaiTinh() {
		return soLieuNguoiNgoaiTinh;
	}
	public void setSoLieuNguoiNgoaiTinh(long soLieuNguoiNgoaiTinh) {
		this.soLieuNguoiNgoaiTinh = soLieuNguoiNgoaiTinh;
	}
	public long getSoLieuTatCaNguoi() {
		return soLieuTatCaNguoi;
	}
	public void setSoLieuTatCaNguoi(long soLieuTatCaNguoi) {
		this.soLieuTatCaNguoi = soLieuTatCaNguoi;
	}
	
}
