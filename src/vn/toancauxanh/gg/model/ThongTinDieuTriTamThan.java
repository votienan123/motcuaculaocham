package vn.toancauxanh.gg.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@Table(name = "thongtindieutritamthan")
public class ThongTinDieuTriTamThan extends Model<ThongTinDieuTriTamThan> implements PropertyChangeListener{
		
	private Date ngayBatDauDieuTri;
	private Date ngayKetThucDieuTri;
	private String nhanXetQuaTrinhDieuTri = "";
	private DoiTuong doiTuong;
	private boolean lichSu;
	private boolean flagChange = false;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public ThongTinDieuTriTamThan() {
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
	
	public Date getNgayBatDauDieuTri() {
		return ngayBatDauDieuTri;
	}

	public void setNgayBatDauDieuTri(Date ngayBatDauDieuTri) {
		Date oldValue = this.ngayBatDauDieuTri;
		this.ngayBatDauDieuTri = ngayBatDauDieuTri;
		pcs.firePropertyChange("Ngày bắt đầu điều trị", getDate2String(oldValue), getDate2String(ngayBatDauDieuTri));
	}

	public Date getNgayKetThucDieuTri() {
		return ngayKetThucDieuTri;
	}

	public void setNgayKetThucDieuTri(Date ngayKetThucDieuTri) {
		Date oldValue = this.ngayKetThucDieuTri;
		this.ngayKetThucDieuTri = ngayKetThucDieuTri;
		pcs.firePropertyChange("Ngày kết thúc điều trị", getDate2String(oldValue), getDate2String(ngayKetThucDieuTri));
	}
	
	public String getNhanXetQuaTrinhDieuTri() {
		return nhanXetQuaTrinhDieuTri;
	}

	public void setNhanXetQuaTrinhDieuTri(String nhanXetQuaTrinhDieuTri) {
		String oldValue = this.nhanXetQuaTrinhDieuTri;
		this.nhanXetQuaTrinhDieuTri = nhanXetQuaTrinhDieuTri;
		pcs.firePropertyChange("Nhận xét quá trình điều trị", oldValue, nhanXetQuaTrinhDieuTri);
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
	public void saveThongTinDieuTri(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
	@BindingParam("wdn") final Window wdn, 
	@BindingParam("doiTuong") final DoiTuong doiTuong,
	@BindingParam("isNew") final boolean isNew) throws IOException {	
		if (isNew) {
			setLichSu(false);
			if (noId()) {
				ThongTinDieuTriTamThan thongTin = find(ThongTinDieuTriTamThan.class)
						.where(QThongTinDieuTriTamThan.thongTinDieuTriTamThan.trangThai.eq(core().TT_AP_DUNG))
						.where(QThongTinDieuTriTamThan.thongTinDieuTriTamThan.doiTuong.eq(doiTuong))
						.where(QThongTinDieuTriTamThan.thongTinDieuTriTamThan.lichSu.eq(false))
						.fetchFirst();
				if (thongTin != null) {
					thongTin.setLichSu(true);
					thongTin.saveNotShowNotification();
				}
			}
		} else {
			if (noId()) {
				setLichSu(true);
			}
		}
		setDoiTuong(doiTuong);
		save();
		if (listPropertyChangeEvent.size() > 0) {
			if (isNew) {
				LichSuThayDoi lsHK = new LichSuThayDoi();
				lsHK.setDoiTuong(getDoiTuong());;
				lsHK.setNoiDung("Thêm mới thông tin điều trị tâm thần");
				lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsHK.saveNotShowNotification();
			} else {
				LichSuThayDoi lsHK = new LichSuThayDoi();
				lsHK.setDoiTuong(getDoiTuong());;
				lsHK.setNoiDung("Cập nhật thông tin điều trị tâm thần");
				lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsHK.saveNotShowNotification();
			}
		}
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
		BindUtils.postNotifyChange(null, null, listObject, "listThongTinDieuTriTamThanAll");
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {				
				if (getNgayKetThucDieuTri() != null && getNgayKetThucDieuTri().before(getNgayBatDauDieuTri())) {
					addInvalidMessage(ctx, "lblErrNgayNgungDieuTri","Ngày kết thúc điều trị không được lớn hơn ngày bắt đầu điều trị.");
				}  
			}
		};
	}
}
