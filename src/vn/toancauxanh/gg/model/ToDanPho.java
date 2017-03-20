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
@Table(name = "todanpho", indexes = { @Index(columnList = "ten"), @Index(columnList = "moTa")})
public class ToDanPho extends Model<ToDanPho>{
	
	private String ten = "";
	private String tenVietTat = "";
	private String moTa = "";
	private DonViHanhChinh quan;
	private DonViHanhChinh phuong;
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	public String getTenVietTat() {
		return tenVietTat;
	}
	public void setTenVietTat(String tenVietTat) {
		this.tenVietTat = tenVietTat;
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
	public void saveToDanPho(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
}
