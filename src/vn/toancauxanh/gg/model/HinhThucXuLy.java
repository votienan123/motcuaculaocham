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

import vn.toancauxanh.gg.model.enums.HinhThucXuLyEnum;
import vn.toancauxanh.gg.model.enums.LoaiDoiTuong;
import vn.toancauxanh.gg.model.enums.LoaiHinhThucXuLy;
import vn.toancauxanh.model.Model;

@Entity
@Table(name = "hinhthucxuly", indexes = { @Index(columnList = "ten"), @Index(columnList = "moTa")})
public class HinhThucXuLy extends Model<HinhThucXuLy>{
	private String ten = "";
	private String moTa = "";
	private LoaiHinhThucXuLy loaiHinhThucXuLy;
	private LoaiDoiTuong loaiDoiTuong;
	private boolean macDinh;
	private HinhThucXuLyEnum hinhThucXuLyEnum;
	
	public HinhThucXuLy() {
		
	}
	
	public HinhThucXuLy(String ten, LoaiHinhThucXuLy loaiHinhThucXuLy, LoaiDoiTuong loaiDoiTuong, boolean macDinh, HinhThucXuLyEnum hinhThucXuLyEnum) {
		this.ten = ten;
		this.loaiHinhThucXuLy = loaiHinhThucXuLy;
		this.loaiDoiTuong = loaiDoiTuong;
		this.macDinh = macDinh;
		this.hinhThucXuLyEnum = hinhThucXuLyEnum;
	}
	
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
	
	public boolean isMacDinh() {
		return macDinh;
	}

	public void setMacDinh(boolean macDinh) {
		this.macDinh = macDinh;
	}

	@Enumerated(EnumType.STRING)
	public HinhThucXuLyEnum getHinhThucXuLyEnum() {
		return hinhThucXuLyEnum;
	}

	public void setHinhThucXuLyEnum(HinhThucXuLyEnum hinhThucXuLyEnum) {
		this.hinhThucXuLyEnum = hinhThucXuLyEnum;
	}

	@Enumerated(EnumType.STRING)
	public LoaiHinhThucXuLy getLoaiHinhThucXuLy() {
		return loaiHinhThucXuLy;
	}
	public void setLoaiHinhThucXuLy(LoaiHinhThucXuLy loaiHinhThucXuLy) {
		this.loaiHinhThucXuLy = loaiHinhThucXuLy;
	}
	@Enumerated(EnumType.STRING)
	public LoaiDoiTuong getLoaiDoiTuong() {
		return loaiDoiTuong;
	}
	public void setLoaiDoiTuong(LoaiDoiTuong loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}
	@Command
	public void saveHinhThucXuLy(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
}
