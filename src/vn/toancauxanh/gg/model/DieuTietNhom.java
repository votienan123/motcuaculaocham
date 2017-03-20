package vn.toancauxanh.gg.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "dieutietnhom")
public class DieuTietNhom extends Model<DieuTietNhom> {

	private NhomCuaHoi nhomCuaHoi;
	private NhomCuaHoi nhomDieuTiet;
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
	public NhomCuaHoi getNhomDieuTiet() {
		return nhomDieuTiet;
	}

	public void setNhomDieuTiet(NhomCuaHoi nhomDieuTiet) {
		this.nhomDieuTiet = nhomDieuTiet;
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
