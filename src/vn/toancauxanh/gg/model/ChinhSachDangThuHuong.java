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
@Table(name = "chinhsachdangthuhuong")
public class ChinhSachDangThuHuong extends Model<ChinhSachDangThuHuong> implements PropertyChangeListener{
	
	private String noiDungChinhSach;
	private String hieuQuaTuViecThuHuong;
	private HanhViViPham hanhViViPham;
	private DoiTuong doiTuong;
	private boolean lichSu;
	private boolean flagChange = false;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public ChinhSachDangThuHuong() {
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

	public String getNoiDungChinhSach() {
		return noiDungChinhSach;
	}

	public void setNoiDungChinhSach(String noiDungChinhSach) {
		String oldValue = this.noiDungChinhSach;
		this.noiDungChinhSach = noiDungChinhSach;
		pcs.firePropertyChange("Nội dung chính sách thụ hưởng", oldValue, noiDungChinhSach);
	}

	public String getHieuQuaTuViecThuHuong() {
		return hieuQuaTuViecThuHuong;
	}

	public void setHieuQuaTuViecThuHuong(String hieuQuaTuViecThuHuong) {
		String oldValue = this.hieuQuaTuViecThuHuong;
		this.hieuQuaTuViecThuHuong = hieuQuaTuViecThuHuong;
		pcs.firePropertyChange("Hiệu quả từ việc thụ hưởng", oldValue, hieuQuaTuViecThuHuong);
	}

	@ManyToOne
	public HanhViViPham getHanhViViPham() {
		return hanhViViPham;
	}

	public void setHanhViViPham(HanhViViPham hanhViViPham) {
		this.hanhViViPham = hanhViViPham;
	}

	@ManyToOne
	public DoiTuong getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(DoiTuong doiTuong) {
		this.doiTuong = doiTuong;
	}

	public boolean isLichSu() {
		return lichSu;
	}

	public void setLichSu(boolean lichSu) {
		this.lichSu = lichSu;
	}

	@Command
	public void saveChinhSachThuHuong(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
	@BindingParam("wdn") final Window wdn, 
	@BindingParam("doiTuong") final DoiTuong doiTuong,
	@BindingParam("isNew") final boolean isNew) throws IOException {	
		if (isNew) {
			setLichSu(false);
			if (noId()) {
				ChinhSachDangThuHuong thongTin = find(ChinhSachDangThuHuong.class)
						.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.trangThai.eq(core().TT_AP_DUNG))
						.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.doiTuong.eq(doiTuong))
						.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.lichSu.eq(false))
						.fetchFirst();
				if (thongTin != null) {
					thongTin.setLichSu(true);
					thongTin.saveNotShowNotification();
				}
				doiTuong.setCoHuongThuChinhSachViPham(false);
				doiTuong.setCoHuongThuChinhSach(true);
			}
		} else {
			if (noId()) {
				setLichSu(true);
			}
		}
		doiTuong.saveNotShowNotification();
		setDoiTuong(doiTuong);
		save();
		if (listPropertyChangeEvent.size() > 0) {
			if (isNew) {
				LichSuThayDoi lsHK = new LichSuThayDoi();
				lsHK.setDoiTuong(getDoiTuong());;
				lsHK.setNoiDung("Thêm mới chính sách thụ hưởng");
				lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsHK.saveNotShowNotification();
			} else {
				LichSuThayDoi lsHK = new LichSuThayDoi();
				lsHK.setDoiTuong(getDoiTuong());;
				lsHK.setNoiDung("Cập nhật chính sách thụ hưởng");
				lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsHK.saveNotShowNotification();
			}
		}
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
		BindUtils.postNotifyChange(null, null, listObject, "listThongTinDieuTriTamThanAll");
	}
}
