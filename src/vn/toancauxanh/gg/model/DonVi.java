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
@Table(name = "donvi", indexes = { @Index(columnList = "ten"), @Index(columnList = "moTa")})
public class DonVi extends Model<DonVi>{
	
	private String ten = "";
	private DonVi cha;
	private String moTa = "";
	private DonViHanhChinh thanhPho;
	private DonViHanhChinh quan;
	private DonViHanhChinh phuong;
	private DonViHanhChinh donViHanhChinh;
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	@ManyToOne
	public DonVi getCha() {
		return cha;
	}
	public void setCha(DonVi cha) {
		this.cha = cha;
	}
	
	@ManyToOne
	public DonViHanhChinh getThanhPho() {
		if (thanhPho == null) {
			thanhPho = core().getDonViHanhChinhs().getDonViDaNang();
		}
		return thanhPho;
	}
	public void setThanhPho(DonViHanhChinh thanhpho) {
		this.thanhPho = thanhpho;
	}
	
	@ManyToOne
	public DonViHanhChinh getDonViHanhChinh() {
		return donViHanhChinh;
	}
	public void setDonViHanhChinh(DonViHanhChinh donViHanhChinh) {
		this.donViHanhChinh = donViHanhChinh;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	@ManyToOne
	public DonViHanhChinh getQuan() {
		return quan;
	}
	public void setQuan(DonViHanhChinh quan) {
		this.quan = quan;
	}
	
	@ManyToOne
	public DonViHanhChinh getPhuong() {
		return phuong;
	}
	public void setPhuong(DonViHanhChinh phuong) {
		this.phuong = phuong;
	}
	@Command
	public void saveDonVi(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		if (phuong != null) {
			setDonViHanhChinh(phuong);
		} else if (quan != null) {
			setDonViHanhChinh(quan);
		} else {
			setDonViHanhChinh(thanhPho);
		}
		if (!noId() && cha != null && cha.getId() == getId()) {
			showNotification("Không thể chọn chính đơn vị này làm đơn vị cha", "", "warning");
		} else {
			save();
			wdn.detach();
			BindUtils.postNotifyChange(null, null, listObject, attr);	
		}	
	}
}
