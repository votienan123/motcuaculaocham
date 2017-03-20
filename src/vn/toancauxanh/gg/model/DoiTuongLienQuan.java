package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "doituonglienquan", indexes = { @Index(columnList = "hoVaTen")})
public class DoiTuongLienQuan extends Model<DoiTuongLienQuan>{
	
	private String hoVaTen = "";
	private String namSinh = "";
	private String diaChiThuongTru = "";
	private NganhNghe ngheNghiep;
	private QuanHeDoiTuongLienQuan quanHeDoiTuongLienQuan;
	private DoiTuong doiTuong;
	
	public String getHoVaTen() {
		return hoVaTen;
	}
	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	public String getNamSinh() {
		return namSinh;
	}

	public void setNamSinh(String namSinh) {
		this.namSinh = namSinh;
	}

	public String getDiaChiThuongTru() {
		return diaChiThuongTru;
	}

	public void setDiaChiThuongTru(String diaChiThuongTru) {
		this.diaChiThuongTru = diaChiThuongTru;
	}

	@ManyToOne
	public NganhNghe getNgheNghiep() {
		return ngheNghiep;
	}

	public void setNgheNghiep(NganhNghe ngheNghiep) {
		this.ngheNghiep = ngheNghiep;
	}

	@ManyToOne
	public QuanHeDoiTuongLienQuan getQuanHeDoiTuongLienQuan() {
		return quanHeDoiTuongLienQuan;
	}
	public void setQuanHeDoiTuongLienQuan(QuanHeDoiTuongLienQuan quanHeDoiTuongLienQuan) {
		this.quanHeDoiTuongLienQuan = quanHeDoiTuongLienQuan;
	}
	@ManyToOne
	public DoiTuong getDoiTuong() {
		return doiTuong;
	}
	public void setDoiTuong(DoiTuong doiTuong) {
		this.doiTuong = doiTuong;
	}
}
