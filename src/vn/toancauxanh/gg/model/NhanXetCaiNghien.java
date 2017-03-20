package vn.toancauxanh.gg.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "nhanxetcainghien")
public class NhanXetCaiNghien extends Model<NhanXetCaiNghien> implements PropertyChangeListener{
	
	private Integer thang;
	private Integer nam;
	private String nhanXet = "";
	private ThongTinCaiNghien thongTinCaiNghien;
	
	private boolean flagChange = false;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public NhanXetCaiNghien() {
		pcs.addPropertyChangeListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {		
		PropertyChangeObject obj = new PropertyChangeObject(evt.getPropertyName(), 
				evt.getOldValue() != null ? evt.getOldValue().toString() : "", 
				evt.getNewValue() != null ?	evt.getNewValue().toString() : "");
			//System.out.println("obj: " + obj.toString());
		if (flagChange && !listPropertyChangeEvent.contains(obj) && !obj.getNewValue().equals(obj.getOldValue())) {        	
			listPropertyChangeEvent.add(obj);
        }
    }
	
	private List<PropertyChangeObject> listPropertyChangeEvent = new ArrayList<PropertyChangeObject>();
	
	@Transient
	public List<PropertyChangeObject> getListPropertyChangeEvent() {
		return listPropertyChangeEvent;
	}	

	@Transient
	public boolean isFlagChange() {
		return flagChange;
	}

	public void setFlagChange(boolean flagChange) {
		this.flagChange = flagChange;
	}

	public void setListPropertyChangeEvent(List<PropertyChangeObject> listPropertyChangeEvent) {
		this.listPropertyChangeEvent = listPropertyChangeEvent;
	}
	
	public Integer getThang() {
		return thang;
	}

	public void setThang(Integer thang) {
		Integer oldValue = this.thang;
		this.thang = thang;
		pcs.firePropertyChange("Tháng", oldValue, thang);
	}

	public Integer getNam() {
		return nam;
	}

	public void setNam(Integer nam) {
		Integer oldValue = this.nam;
		this.nam = nam;
		pcs.firePropertyChange("Năm", oldValue, nam);
	}

	public String getNhanXet() {
		return nhanXet;
	}

	public void setNhanXet(String nhanXet) {
		String oldValue = this.nhanXet;
		this.nhanXet = nhanXet;
		pcs.firePropertyChange("Nhận xét", oldValue, nhanXet);
	}
	
	@Transient
	public String getThangNam() {
		return thang + "/" + nam;
	}
	
	@ManyToOne
	public ThongTinCaiNghien getThongTinCaiNghien() {
		return thongTinCaiNghien;
	}

	public void setThongTinCaiNghien(ThongTinCaiNghien thongTinCaiNghien) {
		this.thongTinCaiNghien = thongTinCaiNghien;
	}

	@Command
	public void saveNhanXetCaiNghien(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) throws IOException {	
		setThongTinCaiNghien((ThongTinCaiNghien)listObject);
		save();
		if (listPropertyChangeEvent.size() > 0) {
			LichSuThayDoi lsHK = new LichSuThayDoi();
			lsHK.setDoiTuong(getThongTinCaiNghien().getDoiTuong());;
			lsHK.setNoiDung("Cập nhật nhận xét cai nghiện tháng " + getThangNam());
			lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
			lsHK.saveNotShowNotification();
		}
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);		
	}
}
