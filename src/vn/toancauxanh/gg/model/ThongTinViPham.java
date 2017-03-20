package vn.toancauxanh.gg.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.querydsl.core.annotations.QueryInit;

import vn.toancauxanh.gg.model.enums.LoaiDoiTuong;
import vn.toancauxanh.gg.model.enums.LoaiHinhThucXuLy;
import vn.toancauxanh.gg.model.enums.LoaiXuLy;
import vn.toancauxanh.model.Model;

@Entity
@Table(name = "thongtinvipham")
public class ThongTinViPham extends Model<ThongTinViPham> implements PropertyChangeListener{
	
	private DoiTuong doiTuong;
	private Date ngayViPham;
	private Date ngayKetThuc;
	private DonVi donViCha;
	private DonVi donViCon;
	private DonVi donVi;
	private HanhViViPham hanhViViPham;
	private HinhThucXuLy hinhThucXuLy;
	private MaTuySuDung maTuySuDung;
	private boolean lichSu;
	private boolean laThongTinMoi;
	private String moTaHinhThucXuLy = "";
	private LoaiXuLy loaiXuLy;
	
	private boolean flagChange = false;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public ThongTinViPham() {
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
	
	@ManyToOne
	@QueryInit("*.*.*")
	public DoiTuong getDoiTuong() {
		return doiTuong;
	}

	public void setDoiTuong(DoiTuong doiTuong) {
		this.doiTuong = doiTuong;
	}

	public Date getNgayViPham() {
		return ngayViPham;
	}

	public void setNgayViPham(Date ngayViPham) {
		Date oldValue = this.ngayViPham;
		this.ngayViPham = ngayViPham;
		pcs.firePropertyChange("Thời điểm bắt đầu", getDate2String(oldValue), getDate2String(ngayViPham));
	}
	
	public boolean isLaThongTinMoi() {
		return laThongTinMoi;
	}

	public void setLaThongTinMoi(boolean laThongTinMoi) {
		this.laThongTinMoi = laThongTinMoi;
	}

	public Date getNgayKetThuc() {
		return ngayKetThuc;
	}
	
	@Enumerated(EnumType.STRING)
	public LoaiXuLy getLoaiXuLy() {
		return loaiXuLy;
	}

	public void setLoaiXuLy(LoaiXuLy loaiXuLy) {
		this.loaiXuLy = loaiXuLy;
	}

	public void setNgayKetThuc(Date ngayKetThuc) {
		Date oldValue = this.ngayKetThuc;
		this.ngayKetThuc = ngayKetThuc;
		pcs.firePropertyChange("Thời điểm kết thúc", getDate2String(oldValue), getDate2String(ngayKetThuc));
	}
	
	public String getMoTaHinhThucXuLy() {
		return moTaHinhThucXuLy;
	}

	public void setMoTaHinhThucXuLy(String moTaHinhThucXuLy) {
		String oldValue = this.moTaHinhThucXuLy;
		this.moTaHinhThucXuLy = moTaHinhThucXuLy;
		pcs.firePropertyChange("Mô tả hình thức xử lý", oldValue, moTaHinhThucXuLy);
	}

	@ManyToOne
	public DonVi getDonViCha() {
		return donViCha;
	}

	public void setDonViCha(DonVi donViCha) {
		this.donViCha = donViCha;
	}
	
	@ManyToOne
	public DonVi getDonViCon() {
		return donViCon;
	}

	public void setDonViCon(DonVi donViCon) {
		this.donViCon = donViCon;
	}

	@ManyToOne
	public DonVi getDonVi() {
		return donVi;
	}

	public void setDonVi(DonVi donVi) {
		DonVi oldValue = this.donVi;
		this.donVi = donVi;
		pcs.firePropertyChange("Đơn vị xử lý", oldValue != null ? oldValue.getTen() : "", 
				donVi != null ? donVi.getTen() : "");
	}
	
	@ManyToOne
	public HanhViViPham getHanhViViPham() {
		return hanhViViPham;
	}

	public void setHanhViViPham(HanhViViPham hanhViViPham) {
		HanhViViPham oldValue = this.hanhViViPham;
		this.hanhViViPham = hanhViViPham;
		pcs.firePropertyChange("Hành vi vi phạm", oldValue != null ? oldValue.getTen() : "", 
				hanhViViPham != null ? hanhViViPham.getTen() : "");
	}

	@ManyToOne
	public HinhThucXuLy getHinhThucXuLy() {
		return hinhThucXuLy;
	}

	public void setHinhThucXuLy(HinhThucXuLy hinhThucXuLy) {
		HinhThucXuLy oldValue = this.hinhThucXuLy;
		this.hinhThucXuLy = hinhThucXuLy;
		pcs.firePropertyChange("Hình thức xử lý", oldValue != null ? oldValue.getTen() : "", 
				hinhThucXuLy != null ? hinhThucXuLy.getTen() : "");
	}

	@ManyToOne
	public MaTuySuDung getMaTuySuDung() {
		return maTuySuDung;
	}

	public void setMaTuySuDung(MaTuySuDung maTuySuDung) {
		MaTuySuDung oldValue = this.maTuySuDung;
		this.maTuySuDung = maTuySuDung;
		pcs.firePropertyChange("Ma túy sử dụng", oldValue != null ? oldValue.getTen() : "", 
				maTuySuDung != null ? maTuySuDung.getTen() : "");
	}

	public boolean isLichSu() {
		return lichSu;
	}

	public void setLichSu(boolean lichSu) {
		this.lichSu = lichSu;
	}

	@Command
	public void saveThongTinViPham(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn, 
			@BindingParam("doiTuong") final DoiTuong doiTuong,
			@BindingParam("isNew") final boolean isNew) throws Exception {
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    cal.set(Calendar.MINUTE, 1);// adds one hour
	    Date today = cal.getTime(); 
	    System.out.println("ngayKetThuc: " + getNgayKetThuc());
	    System.out.println("today: " + today);
		if (getNgayKetThuc() != null && getNgayKetThuc().before(today)) {
			Messagebox.show("Việc chọn ngày kết thúc trước ngày hiện tại sẽ đồng nghĩa với việc xóa hình thức quản lý, xử lý đang áp dụng này."
					+ " Bạn có chắc chắn muốn thực hiện điều này không?","XÁC NHẬN", Messagebox.CANCEL | Messagebox.OK,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								setLichSu(true);
								setDoiTuong(doiTuong);
								setLaThongTinMoi(true);
								if (getDonViCon() != null) {
									setDonVi(getDonViCon());
								} else {
									setDonVi(getDonViCha());
								}
								save();
								
								if (getHinhThucXuLy().getLoaiHinhThucXuLy().equals(LoaiHinhThucXuLy.TIEN_AN_TIEN_SU)) {
									if (getLoaiXuLy().equals(LoaiXuLy.XU_LY_HINH_SU)) {
										doiTuong.setCoTienAn(true);
									} else {
										doiTuong.setCoTienSu(true);
									}
								}
								
								if (listPropertyChangeEvent.size() > 0) {
									if (isNew) {
										LichSuThayDoi lsHK = new LichSuThayDoi();
										lsHK.setDoiTuong(getDoiTuong());;
										lsHK.setNoiDung("Thêm mới hình thức xử lý: " + getHinhThucXuLy().getTen());
										lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
										lsHK.saveNotShowNotification();
									} else {
										LichSuThayDoi lsHK = new LichSuThayDoi();
										lsHK.setDoiTuong(getDoiTuong());;
										lsHK.setNoiDung("Cập nhật hình thức xử lý: " + getHinhThucXuLy().getTen());
										lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
										lsHK.saveNotShowNotification();
									}
								}
								
								ThongTinViPham hienHanh = find(ThongTinViPham.class)
										.where(QThongTinViPham.thongTinViPham.doiTuong.eq(doiTuong))
										.where(QThongTinViPham.thongTinViPham.lichSu.eq(false))
										.orderBy(QThongTinViPham.thongTinViPham.ngayViPham.desc())
										.fetchFirst();
								doiTuong.setThongTinViPhamHienHanh(hienHanh);
								doiTuong.saveNotShowNotification();
								if (getMaTuySuDung() != null && !doiTuong.getMaTuySuDungs().contains(getMaTuySuDung())) {
									try {
										doiTuong.getMaTuySuDungs().add(getMaTuySuDung());
									} catch (Exception e) {
										// TODO: handle exception
									}
								}
								doiTuong.saveNotShowNotification();
								wdn.detach();
								BindUtils.postNotifyChange(null, null, listObject, attr);
							}
						}
					});
		} else {
			setLichSu(false);
			setDoiTuong(doiTuong);
			setLaThongTinMoi(true);
			if (getDonViCon() != null) {
				setDonVi(getDonViCon());
			} else {
				setDonVi(getDonViCha());
			}
			save();
			
			if (listPropertyChangeEvent.size() > 0) {
				if (isNew) {
					LichSuThayDoi lsHK = new LichSuThayDoi();
					lsHK.setDoiTuong(getDoiTuong());;
					lsHK.setNoiDung("Thêm mới hình thức xử lý: " + getHinhThucXuLy().getTen());
					lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
					lsHK.saveNotShowNotification();
				} else {
					LichSuThayDoi lsHK = new LichSuThayDoi();
					lsHK.setDoiTuong(getDoiTuong());;
					lsHK.setNoiDung("Cập nhật hình thức xử lý: " + getHinhThucXuLy().getTen());
					lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
					lsHK.saveNotShowNotification();
				}
			}
			
			ChinhSachDangThuHuong chinhSach = find(ChinhSachDangThuHuong.class)
					.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.doiTuong.eq(doiTuong))
					.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.lichSu.eq(false))
					.fetchFirst();
			
			if (chinhSach != null) {
				if (chinhSach.getHanhViViPham() == null) {
					if (getHanhViViPham() != null) {
						chinhSach.setHanhViViPham(getHanhViViPham());
						chinhSach.saveNotShowNotification();
						doiTuong.setCoHuongThuChinhSachViPham(true);
					}
				}
			}
			
			ThongTinViPham hienHanh = find(ThongTinViPham.class)
					.where(QThongTinViPham.thongTinViPham.doiTuong.eq(doiTuong))
					.where(QThongTinViPham.thongTinViPham.lichSu.eq(false))
					.orderBy(QThongTinViPham.thongTinViPham.ngayViPham.desc())
					.fetchFirst();
			if (getHinhThucXuLy().getLoaiDoiTuong().equals(LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY)) {
				doiTuong.setNguoiNghien(true);
			} else if (getHinhThucXuLy().getLoaiDoiTuong().equals(LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY)) {
				if (!doiTuong.isNguoiNghien()) {
					doiTuong.setNguoiSuDungTraiPhep(true);
				}				
			} else if (getHinhThucXuLy().getLoaiDoiTuong().equals(LoaiDoiTuong.TUY_TRUONG_HOP)) {
				if (doiTuong.getThongTinViPhamHienHanh() != null) {
					if (doiTuong.getThongTinViPhamHienHanh().getHinhThucXuLy().getLoaiDoiTuong().equals(LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY)) {
						doiTuong.setNguoiNghien(true);
					} else if (doiTuong.getThongTinViPhamHienHanh().getHinhThucXuLy().getLoaiDoiTuong().equals(LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY)){
						doiTuong.setNguoiSuDungTraiPhep(true);
					} 
				} else {
					doiTuong.setNguoiSuDungTraiPhep(true);
				}
			}
			doiTuong.setThongTinViPhamHienHanh(hienHanh);
			try {
				doiTuong.saveNotShowNotification();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("doiTuong save exception");
			}	
			if (getMaTuySuDung() != null && !doiTuong.getMaTuySuDungs().contains(getMaTuySuDung())) {
				try {
					doiTuong.getMaTuySuDungs().add(getMaTuySuDung());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			doiTuong.saveNotShowNotification();
			wdn.detach();
			BindUtils.postNotifyChange(null, null, listObject, attr);
		}
	}
	
	@Command
	public void saveLichSuViPham(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn, @BindingParam("doiTuong") final DoiTuong doiTuong,
			@BindingParam("isNew") final boolean isNew) throws IOException {
		
		setDoiTuong(doiTuong);
		if (getDonViCon() != null) {
			setDonVi(getDonViCon());
		} else {
			setDonVi(getDonViCha());
		}
		if (getHinhThucXuLy().getLoaiHinhThucXuLy().equals(LoaiHinhThucXuLy.TIEN_AN_TIEN_SU)) {
			if (getLoaiXuLy().equals(LoaiXuLy.XU_LY_HINH_SU)) {
				doiTuong.setCoTienAn(true);
			} else {
				doiTuong.setCoTienSu(true);
			}
		}
		save();
		wdn.detach();
		try {
			doiTuong.saveNotShowNotification();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("doiTuong save exception");
		}		
		if (getMaTuySuDung() != null && !doiTuong.getMaTuySuDungs().contains(getMaTuySuDung())) {
			try {
				doiTuong.getMaTuySuDungs().add(getMaTuySuDung());
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		doiTuong.saveNotShowNotification();
		if (listPropertyChangeEvent.size() > 0) {
			if (isNew) {
				LichSuThayDoi lsHK = new LichSuThayDoi();
				lsHK.setDoiTuong(getDoiTuong());;
				lsHK.setNoiDung("Thêm mới lịch sử xử lý: " + getHinhThucXuLy().getTen());
				lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsHK.saveNotShowNotification();
			} else {
				LichSuThayDoi lsHK = new LichSuThayDoi();
				lsHK.setDoiTuong(getDoiTuong());;
				lsHK.setNoiDung("Cập nhật lịch sử xử lý: " + getHinhThucXuLy().getTen());
				lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsHK.saveNotShowNotification();
			}
		}
		BindUtils.postNotifyChange(null, null, listObject, "listTienAn");
		BindUtils.postNotifyChange(null, null, listObject, "listTienSu");
		BindUtils.postNotifyChange(null, null, listObject, "listViPhamHanhChinh");
		BindUtils.postNotifyChange(null, null, listObject, "listLichSuKhac");
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {				
				if (getNgayKetThuc() != null && getNgayKetThuc().before(getNgayViPham())) {
					addInvalidMessage(ctx, "lblErrNgayNgungDieuTri","Thời điểm kết thúc không được lớn hơn thời điểm bắt đầu.");
				}
				Calendar cal = Calendar.getInstance(); // creates calendar
			    cal.setTime(new Date()); // sets calendar time/date
			    cal.add(Calendar.DAY_OF_MONTH, -1);
			    cal.set(Calendar.MINUTE, 1);// adds one hour
			    Date today = cal.getTime(); 
				if (getNgayKetThuc() != null && isLichSu() && getNgayKetThuc().after(today)) {
					addInvalidMessage(ctx, "lblErrNgayNgungDieuTri","Thời điểm kết thúc không được lớn hơn hoặc bằng ngày hiện tại.");
				}
			}
		};
	}
}
