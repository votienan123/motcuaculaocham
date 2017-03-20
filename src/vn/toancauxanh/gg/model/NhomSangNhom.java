package vn.toancauxanh.gg.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "nhomsangnhom")
public class NhomSangNhom extends Model<NhomSangNhom> {
	private NhomCuaHoi nhomCuaHoi;
	private NhomCuaHoi nhomDieuTiet;
	//private DieuTietNhom dieuTietNhom;
	private Date ngayThucHienTour;
	private int soLuongKhachDieuTietCong = 0;
	private String loaiDieuTiet = "";

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

	/*@ManyToOne
	public DieuTietNhom getDieuTietNhom() {
		return dieuTietNhom;
	}

	public void setDieuTietNhom(DieuTietNhom dieuTietNhom) {
		this.dieuTietNhom = dieuTietNhom;
	}*/

	public Date getNgayThucHienTour() {
		return ngayThucHienTour;
	}

	public void setNgayThucHienTour(Date ngayThucHienTour) {
		this.ngayThucHienTour = ngayThucHienTour;
	}

	public int getSoLuongKhachDieuTietCong() {
		return soLuongKhachDieuTietCong;
	}

	public void setSoLuongKhachDieuTietCong(int soLuongKhachDieuTietCong) {
		this.soLuongKhachDieuTietCong = soLuongKhachDieuTietCong;
	}
	
	public String getLoaiDieuTiet() {
		return loaiDieuTiet;
	}

	public void setLoaiDieuTiet(String loaiDieuTiet) {
		this.loaiDieuTiet = loaiDieuTiet;
	}
}
