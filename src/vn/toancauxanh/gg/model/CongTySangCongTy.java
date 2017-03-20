package vn.toancauxanh.gg.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "congtysangcongty")
public class CongTySangCongTy extends Model<CongTySangCongTy> {
	private NhomCuaHoi nhomCuaHoi;
	private CongTyKinhDoanh congTyKinhDoanh;
	private CongTyKinhDoanh congTyDieuTiet;
	private Date ngayThucHienTour;
	private int soLuongKhachDieuTiet = 0;

	@ManyToOne
	public NhomCuaHoi getNhomCuaHoi() {
		return nhomCuaHoi;
	}

	public void setNhomCuaHoi(NhomCuaHoi nhomCuaHoi) {
		this.nhomCuaHoi = nhomCuaHoi;
	}

	@ManyToOne
	public CongTyKinhDoanh getCongTyKinhDoanh() {
		return congTyKinhDoanh;
	}

	public void setCongTyKinhDoanh(CongTyKinhDoanh congTyKinhDoanh) {
		this.congTyKinhDoanh = congTyKinhDoanh;
	}

	@ManyToOne
	public CongTyKinhDoanh getCongTyDieuTiet() {
		return congTyDieuTiet;
	}

	public void setCongTyDieuTiet(CongTyKinhDoanh congTyDieuTiet) {
		this.congTyDieuTiet = congTyDieuTiet;
	}

	public Date getNgayThucHienTour() {
		return ngayThucHienTour;
	}

	public void setNgayThucHienTour(Date ngayThucHienTour) {
		this.ngayThucHienTour = ngayThucHienTour;
	}

	public int getSoLuongKhachDieuTiet() {
		return soLuongKhachDieuTiet;
	}

	public void setSoLuongKhachDieuTiet(int soLuongKhachDieuTiet) {
		this.soLuongKhachDieuTiet = soLuongKhachDieuTiet;
	}
}
