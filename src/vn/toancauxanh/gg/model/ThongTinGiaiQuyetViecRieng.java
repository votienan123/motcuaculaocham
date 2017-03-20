package vn.toancauxanh.gg.model;

import java.io.IOException;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "thongtingiaiquyetviecrieng")
public class ThongTinGiaiQuyetViecRieng extends Model<ThongTinGiaiQuyetViecRieng>{

	private Date ngayChoVe;
	private String lyDoVe = "";
	private Integer soNgayVe;
	private ThongTinCaiNghien thongTinCaiNghien;
			
	public Date getNgayChoVe() {
		return ngayChoVe;
	}

	public void setNgayChoVe(Date ngayChoVe) {
		this.ngayChoVe = ngayChoVe;
	}

	public String getLyDoVe() {
		return lyDoVe;
	}

	public void setLyDoVe(String lyDoVe) {
		this.lyDoVe = lyDoVe;
	}

	public Integer getSoNgayVe() {
		return soNgayVe;
	}

	public void setSoNgayVe(Integer soNgayVe) {
		this.soNgayVe = soNgayVe;
	}

	@ManyToOne
	public ThongTinCaiNghien getThongTinCaiNghien() {
		return thongTinCaiNghien;
	}

	public void setThongTinCaiNghien(ThongTinCaiNghien thongTinCaiNghien) {
		this.thongTinCaiNghien = thongTinCaiNghien;
	}

	@Command
	public void saveThongTinGiaiQuyetViecRieng(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		setThongTinCaiNghien((ThongTinCaiNghien)listObject);
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
}
