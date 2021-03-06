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
@Table(name = "quanhegiadinh", indexes = { @Index(columnList = "ten")})
public class QuanHeGiaDinh extends Model<QuanHeGiaDinh>{
	
	public QuanHeGiaDinh() {
		
	}
	
	public QuanHeGiaDinh(String ten) {
		this.ten = ten;
	}
	
	private String ten = "";
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	@Command
	public void saveQuanHeGiaDinh(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
}
