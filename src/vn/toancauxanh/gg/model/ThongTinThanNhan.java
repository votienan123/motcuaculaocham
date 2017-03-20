package vn.toancauxanh.gg.model;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "thongtinthannhan", indexes = { @Index(columnList = "hoVaTen")})
public class ThongTinThanNhan extends Model<ThongTinThanNhan>{
	
	private String hoVaTen = "";
	private String namSinh = "";
	private String diaChiThuongTru = "";
	private NganhNghe ngheNghiep;
	private QuanHeGiaDinh quanHeGiaDinh;
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
	public QuanHeGiaDinh getQuanHeGiaDinh() {
		return quanHeGiaDinh;
	}

	public void setQuanHeGiaDinh(QuanHeGiaDinh quanHeGiaDinh) {
		this.quanHeGiaDinh = quanHeGiaDinh;
	}

	@ManyToOne
	public DoiTuong getDoiTuong() {
		return doiTuong;
	}
	public void setDoiTuong(DoiTuong doiTuong) {
		this.doiTuong = doiTuong;
	}
	@Command
	public void saveGioiTinh(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
}
