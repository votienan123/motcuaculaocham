package vn.toancauxanh.gg.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "veduyet")
public class VeDuyet extends Model<VeDuyet> {
	private Date ngayThucHienTour;
	private CongTyKinhDoanh congTyKinhDoanh;
	private NhomCuaHoi nhomCuaHoi;
	private int soLuongVeDuyet = 0;

	public Date getNgayThucHienTour() {
		return ngayThucHienTour;
	}

	public void setNgayThucHienTour(Date ngayThucHienTour) {
		this.ngayThucHienTour = ngayThucHienTour;
	}

	@ManyToOne
	public CongTyKinhDoanh getCongTyKinhDoanh() {
		return congTyKinhDoanh;
	}

	public void setCongTyKinhDoanh(CongTyKinhDoanh congTyKinhDoanh) {
		this.congTyKinhDoanh = congTyKinhDoanh;
	}

	@ManyToOne
	public NhomCuaHoi getNhomCuaHoi() {
		return nhomCuaHoi;
	}

	public void setNhomCuaHoi(NhomCuaHoi nhomCuaHoi) {
		this.nhomCuaHoi = nhomCuaHoi;
	}

	public int getSoLuongVeDuyet() {
		return soLuongVeDuyet;
	}

	public void setSoLuongVeDuyet(int soLuongVeDuyet) {
		this.soLuongVeDuyet = soLuongVeDuyet;
	}

}
