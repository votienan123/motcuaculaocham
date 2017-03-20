package vn.toancauxanh.gg.model;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "trinhdohocvan", indexes = { @Index(columnList = "ten"), @Index(columnList = "moTa")})
public class TrinhDoHocVan extends Model<TrinhDoHocVan>{
	
	public TrinhDoHocVan() {
		
	}
	
	public TrinhDoHocVan(String ten, int soThuTu) {
		this.ten = ten;
		this.soThuTu = soThuTu;
	}
	
	private String ten = "";
	private String moTa = "";
	private int soThuTu;
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
	
	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	@Command
	public void saveTrinhDoHocVan(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
}
