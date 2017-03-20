package vn.toancauxanh.gg.model;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "giaveditourrieng", indexes = { @Index(columnList = "ten")})
public class GiaVeDiTourRieng extends Model<GiaVeDiTourRieng>{
	public GiaVeDiTourRieng () {
		
	}
	
	public GiaVeDiTourRieng(String ten) {
		this.ten = ten;
	}
	
	private String ten = "";
	private int soNguoiTu;
	private int soNguoiDen;
	private long giaVe;
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}	
	
	public int getSoNguoiTu() {
		return soNguoiTu;
	}

	public void setSoNguoiTu(int soNguoiTu) {
		this.soNguoiTu = soNguoiTu;
	}

	public int getSoNguoiDen() {
		return soNguoiDen;
	}

	public void setSoNguoiDen(int soNguoiDen) {
		this.soNguoiDen = soNguoiDen;
	}

	public long getGiaVe() {
		return giaVe;
	}

	public void setGiaVe(long giaVe) {
		this.giaVe = giaVe;
	}

	@Command
	public void saveGiaVeDiTourRieng(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {				
				if (getSoNguoiDen() < getSoNguoiTu()) {
					addInvalidMessage(ctx, "lblErrSoNguoi","Số lượng đến không được nhỏ hơn số lượng từ.");
				} 
			}
		};
	}
}
