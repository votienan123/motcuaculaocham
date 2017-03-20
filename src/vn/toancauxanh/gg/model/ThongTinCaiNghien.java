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
@Table(name = "thongtincainghien")
public class ThongTinCaiNghien extends Model<ThongTinCaiNghien> implements PropertyChangeListener{
		
	private boolean lichSu;
	private DoiTuong doiTuong;
	private Date ngayVaoTrungTam;
	private int soLanVao;
	private String thoiHanCaiNghien = "";
	private String khenThuong = "";
	private String kyLuat = "";
	private String xepLoaiRenLuyen = "";
	private Date ngayChoVe;
	private boolean flagChange = false;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public ThongTinCaiNghien() {
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
	
	public boolean isLichSu() {
		return lichSu;
	}

	public void setLichSu(boolean lichSu) {
		this.lichSu = lichSu;
	}

	@ManyToOne
	public DoiTuong getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(DoiTuong doiTuong) {
		this.doiTuong = doiTuong;
	}

	public Date getNgayVaoTrungTam() {
		return ngayVaoTrungTam;
	}

	public void setNgayVaoTrungTam(Date ngayVaoTrungTam) {
		Date oldValue = this.ngayVaoTrungTam;
		this.ngayVaoTrungTam = ngayVaoTrungTam;
		pcs.firePropertyChange("Ngày vào trung tâm", getDate2String(oldValue), getDate2String(ngayVaoTrungTam));
	}

	public int getSoLanVao() {
		return soLanVao;
	}

	public void setSoLanVao(int soLanVao) {
		int oldValue = this.soLanVao;
		this.soLanVao = soLanVao;
		pcs.firePropertyChange("Số lần vào trung tâm", oldValue, soLanVao);
	}

	public String getThoiHanCaiNghien() {
		return thoiHanCaiNghien;
	}

	public void setThoiHanCaiNghien(String thoiHanCaiNghien) {
		String oldValue = this.thoiHanCaiNghien;
		this.thoiHanCaiNghien = thoiHanCaiNghien;
		pcs.firePropertyChange("Thời hạn cai nghiện", oldValue, thoiHanCaiNghien);
	}

	public String getKhenThuong() {
		return khenThuong;
	}

	public void setKhenThuong(String khenThuong) {
		String oldValue = this.khenThuong;
		this.khenThuong = khenThuong;
		pcs.firePropertyChange("Khen thưởng", oldValue, khenThuong);
	}

	public String getKyLuat() {
		return kyLuat;
	}

	public void setKyLuat(String kyLuat) {
		String oldValue = this.kyLuat;
		this.kyLuat = kyLuat;
		pcs.firePropertyChange("Kỷ luật", oldValue, kyLuat);
	}

	public String getXepLoaiRenLuyen() {
		return xepLoaiRenLuyen;
	}

	public void setXepLoaiRenLuyen(String xepLoaiRenLuyen) {
		String oldValue = this.xepLoaiRenLuyen;
		this.xepLoaiRenLuyen = xepLoaiRenLuyen;
		pcs.firePropertyChange("Xếp loại rèn luyện", oldValue, xepLoaiRenLuyen);
	}

	public Date getNgayChoVe() {
		return ngayChoVe;
	}

	public void setNgayChoVe(Date ngayChoVe) {
		Date oldValue = this.ngayChoVe;
		this.ngayChoVe = ngayChoVe;
		pcs.firePropertyChange("Ngày cho về", getDate2String(oldValue), getDate2String(ngayChoVe));
	}

	@Command
	public void saveThongTinCaiNghien(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
	@BindingParam("wdn") final Window wdn, 
	@BindingParam("doiTuong") final DoiTuong doiTuong,
	@BindingParam("isNew") final boolean isNew) throws IOException {	
		if (isNew) {
			setLichSu(false);
			if (noId()) {
				ThongTinCaiNghien thongTin = find(ThongTinCaiNghien.class)
						.where(QThongTinCaiNghien.thongTinCaiNghien.trangThai.eq(core().TT_AP_DUNG))
						.where(QThongTinCaiNghien.thongTinCaiNghien.doiTuong.eq(doiTuong))
						.where(QThongTinCaiNghien.thongTinCaiNghien.lichSu.eq(false))
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
				lsHK.setNoiDung("Thêm mới thông tin điều trị cai nghiện");
				lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsHK.saveNotShowNotification();
			} else {
				LichSuThayDoi lsHK = new LichSuThayDoi();
				lsHK.setDoiTuong(getDoiTuong());;
				lsHK.setNoiDung("Cập nhật thông tin điều trị cai nghiện");
				lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsHK.saveNotShowNotification();
			}
		}
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
		BindUtils.postNotifyChange(null, null, listObject, "listThongTinCaiNghienAll");
	}
	
	@Transient
	public List<NhanXetCaiNghien> getListNhanXetCaiNghien() {
		List<NhanXetCaiNghien> list = new ArrayList<NhanXetCaiNghien>();
		list = find(NhanXetCaiNghien.class)
				.where(QNhanXetCaiNghien.nhanXetCaiNghien.thongTinCaiNghien.eq(this))
				.where(QNhanXetCaiNghien.nhanXetCaiNghien.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QNhanXetCaiNghien.nhanXetCaiNghien.nam.desc())
				.orderBy(QNhanXetCaiNghien.nhanXetCaiNghien.thang.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<ThongTinGiaiQuyetViecRieng> getListThongTinGiaiQuyetViecRieng() {
		List<ThongTinGiaiQuyetViecRieng> list = new ArrayList<ThongTinGiaiQuyetViecRieng>();
		list = find(ThongTinGiaiQuyetViecRieng.class)
				.where(QThongTinGiaiQuyetViecRieng.thongTinGiaiQuyetViecRieng.thongTinCaiNghien.eq(this))
				.where(QThongTinGiaiQuyetViecRieng.thongTinGiaiQuyetViecRieng.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThongTinGiaiQuyetViecRieng.thongTinGiaiQuyetViecRieng.ngayChoVe.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public NhanXetCaiNghien getNhanXetThangCuoiCung() {
		if (getListNhanXetCaiNghien().size() > 0) {
			return getListNhanXetCaiNghien().get(0);
		}
		return null;
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {				
				if (getNgayChoVe() != null && getNgayChoVe().before(getNgayVaoTrungTam())) {
					addInvalidMessage(ctx, "lblErrNgayNgungDieuTri","Ngày cho về không được lớn hơn ngày vào trung tâm.");
				}  
			}
		};
	}
}
