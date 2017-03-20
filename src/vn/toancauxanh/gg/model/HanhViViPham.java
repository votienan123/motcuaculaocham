package vn.toancauxanh.gg.model;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.gg.model.enums.LoaiHanhViViPham;
import vn.toancauxanh.model.Model;

@Entity
@Table(name = "hanhvivipham", indexes = { @Index(columnList = "ten"), @Index(columnList = "moTa")})
public class HanhViViPham extends Model<HanhViViPham>{
	
	public HanhViViPham() {
		
	}
	
	public HanhViViPham(String ten, LoaiHanhViViPham loaiHanhViViPham) {
		this.ten = ten;
		this.loaiHanhViViPham = loaiHanhViViPham;
	}
	
	private String ten = "";
	private String moTa = "";
	private LoaiHanhViViPham loaiHanhViViPham;
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	@Enumerated(EnumType.STRING)
	public LoaiHanhViViPham getLoaiHanhViViPham() {
		return loaiHanhViViPham;
	}
	public void setLoaiHanhViViPham(LoaiHanhViViPham loaiHanhViViPham) {
		this.loaiHanhViViPham = loaiHanhViViPham;
	}
	@Command
	public void saveHanhViViPham(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
}
