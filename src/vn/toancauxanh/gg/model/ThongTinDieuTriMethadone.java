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
@Table(name = "thongtindieutrithemadone")
public class ThongTinDieuTriMethadone extends Model<ThongTinDieuTriMethadone>  implements PropertyChangeListener{
		
	private Date ngayKhoiLieu;
	private String nhanXetQuaTrinhDieuTri = "";
	private Date ngayNgungDieuTri;
	private Date ngayRaKhoiChuongTrinh;
	private String lyDoRaKhoiChuongTrinh = "";
	private HanhViViPham hanhViViPham;
	private boolean lichSu;
	private DoiTuong doiTuong;

	private boolean flagChange = false;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public ThongTinDieuTriMethadone() {
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
	
	
	public Date getNgayKhoiLieu() {
		return ngayKhoiLieu;
	}

	public void setNgayKhoiLieu(Date ngayKhoiLieu) {
		Date oldValue = this.ngayKhoiLieu;
		this.ngayKhoiLieu = ngayKhoiLieu;
		pcs.firePropertyChange("Ngày khởi liều", getDate2String(oldValue), getDate2String(ngayKhoiLieu));
	}

	public String getNhanXetQuaTrinhDieuTri() {
		return nhanXetQuaTrinhDieuTri;
	}

	public void setNhanXetQuaTrinhDieuTri(String nhanXetQuaTrinhDieuTri) {
		String oldValue = this.nhanXetQuaTrinhDieuTri;
		this.nhanXetQuaTrinhDieuTri = nhanXetQuaTrinhDieuTri;
		pcs.firePropertyChange("Nhận xét quá trình điều trị", oldValue, nhanXetQuaTrinhDieuTri);
	}

	public Date getNgayNgungDieuTri() {
		return ngayNgungDieuTri;
	}

	public void setNgayNgungDieuTri(Date ngayNgungDieuTri) {
		Date oldValue = this.ngayNgungDieuTri;
		this.ngayNgungDieuTri = ngayNgungDieuTri;
		pcs.firePropertyChange("Ngày ngừng điều trị", getDate2String(oldValue), getDate2String(ngayNgungDieuTri));
	}

	public Date getNgayRaKhoiChuongTrinh() {
		return ngayRaKhoiChuongTrinh;
	}

	public void setNgayRaKhoiChuongTrinh(Date ngayRaKhoiChuongTrinh) {
		Date oldValue = this.ngayRaKhoiChuongTrinh;
		this.ngayRaKhoiChuongTrinh = ngayRaKhoiChuongTrinh;
		pcs.firePropertyChange("Ngày ra khỏi chương trình", getDate2String(oldValue), getDate2String(ngayRaKhoiChuongTrinh));
	}

	public String getLyDoRaKhoiChuongTrinh() {
		return lyDoRaKhoiChuongTrinh;
	}

	public void setLyDoRaKhoiChuongTrinh(String lyDoRaKhoiChuongTrinh) {
		String oldValue = this.lyDoRaKhoiChuongTrinh;
		this.lyDoRaKhoiChuongTrinh = lyDoRaKhoiChuongTrinh;
		pcs.firePropertyChange("Lý do ra khỏi chương trình", oldValue, lyDoRaKhoiChuongTrinh);
	}
	
	public boolean isLichSu() {
		return lichSu;
	}

	public void setLichSu(boolean lichSu) {
		this.lichSu = lichSu;
	}

	@ManyToOne
	public HanhViViPham getHanhViViPham() {
		return hanhViViPham;
	}

	public void setHanhViViPham(HanhViViPham hanhViViPham) {
		HanhViViPham oldValue = this.hanhViViPham;
		this.hanhViViPham = hanhViViPham;
		pcs.firePropertyChange("Hành vi vi phạm pháp luật", oldValue != null ? oldValue.getTen() : "", 
				hanhViViPham != null ? hanhViViPham.getTen() : "");
	}
	
	@ManyToOne
	public DoiTuong getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(DoiTuong doiTuong) {
		this.doiTuong = doiTuong;
	}

	@Command
	public void saveThongTinDieuTri(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
		@BindingParam("wdn") final Window wdn, 
		@BindingParam("doiTuong") final DoiTuong doiTuong,
		@BindingParam("isNew") final boolean isNew) throws IOException {	
		if (isNew) {
			setLichSu(false);
			if (noId()) {
				ThongTinDieuTriMethadone thongTin = find(ThongTinDieuTriMethadone.class)
						.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.trangThai.eq(core().TT_AP_DUNG))
						.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.doiTuong.eq(doiTuong))
						.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.lichSu.eq(false))
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
				lsHK.setNoiDung("Thêm mới thông tin điều trị Methadone");
				lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsHK.saveNotShowNotification();
			} else {
				LichSuThayDoi lsHK = new LichSuThayDoi();
				lsHK.setDoiTuong(getDoiTuong());;
				lsHK.setNoiDung("Cập nhật thông tin điều trị Methadone");
				lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsHK.saveNotShowNotification();
			}
		}
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
		BindUtils.postNotifyChange(null, null, listObject, "listThongTinDieuTriMethadoneAll");
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {				
				if (getNgayNgungDieuTri() != null && getNgayNgungDieuTri().before(getNgayKhoiLieu())) {
					addInvalidMessage(ctx, "lblErrNgayNgungDieuTri","Ngày ngừng điều trị không được lớn hơn ngày khởi liều.");
				} 
				if (getNgayRaKhoiChuongTrinh() != null && getNgayRaKhoiChuongTrinh().before(getNgayKhoiLieu())) {
					addInvalidMessage(ctx, "lblErrNgayRaKhoiChuongTrinh","Ngày ra khỏi chương trình không được lớn hơn ngày khởi liều.");
				} 
			}
		};
	}
}
