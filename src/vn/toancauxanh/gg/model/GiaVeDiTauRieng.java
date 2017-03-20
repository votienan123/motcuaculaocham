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
@Table(name = "giaveditaurieng", indexes = { @Index(columnList = "ten")})
public class GiaVeDiTauRieng extends Model<GiaVeDiTauRieng>{
	
	private int ten;
	private long giaVe;
	public int getTen() {
		return ten;
	}
	public void setTen(int ten) {
		this.ten = ten;
	}
	
	public long getGiaVe() {
		return giaVe;
	}

	public void setGiaVe(long giaVe) {
		this.giaVe = giaVe;
	}

	@Command
	public void saveGiaVeDiTauRieng(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
}
