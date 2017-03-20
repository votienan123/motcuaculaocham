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
@Table(name = "tuyenxe", indexes = { @Index(columnList = "ten")})
public class TuyenXe extends Model<TuyenXe>{
	public TuyenXe () {
		
	}
	
	public TuyenXe(String ten) {
		this.ten = ten;
	}
	
	private String ten = "";
	private long giaVe;
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	public long getGiaVe() {
		return giaVe;
	}

	public void setGiaVe(long giaVe) {
		this.giaVe = giaVe;
	}

	@Command
	public void saveTuyenXe(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
}
