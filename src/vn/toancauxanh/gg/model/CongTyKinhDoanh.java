package vn.toancauxanh.gg.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.cms.service.ThucHienTourService;
import vn.toancauxanh.gg.model.enums.TinhTrangVeEnum;
import vn.toancauxanh.gg.model.enums.TrangThaiDuyetVeEnum;
import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.NhanVien;
import vn.toancauxanh.model.QNhanVien;

@Entity
@Table(name = "congtykinhdoanh")
public class CongTyKinhDoanh extends Model<CongTyKinhDoanh> {

	private String ten = "";
	private String moTa = "";
	private String diaChi = "";
	private String soDienThoai = "";
	private String nguoiDaiDien = "";
	private long soKhachToiDa = 0;
	private NhomCuaHoi nhomCuaHoi;
	private PhanLoaiCongTy phanLoaiCongTy;
	@SuppressWarnings("unused")
	private long soLuongTau = 0;
	
	@Transient
	public long getSoLuongTau() {		
		JPAQuery<Tau> tauList = find(Tau.class)
				.where(QTau.tau.congTyKinhDoanh.eq(this));
		if (tauList.fetch().isEmpty()) {
			return 0;
		}
		return tauList.fetchCount();
	}

	public void setSoLuongTau(long soLuongTau) {
		this.soLuongTau = soLuongTau;
	}
	
	@ManyToOne
	public PhanLoaiCongTy getPhanLoaiCongTy() {
		return phanLoaiCongTy;
	}

	public void setPhanLoaiCongTy(PhanLoaiCongTy phanLoaiCongTy) {
		this.phanLoaiCongTy = phanLoaiCongTy;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}
	
	public String getNguoiDaiDien() {
		return nguoiDaiDien;
	}

	public void setNguoiDaiDien(String nguoiDaiDien) {
		this.nguoiDaiDien = nguoiDaiDien;
	}

	public long getSoKhachToiDa() {
		return soKhachToiDa;
	}

	public void setSoKhachToiDa(long soKhachToiDa) {
		this.soKhachToiDa = soKhachToiDa;
	}

	@Lob
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	@ManyToOne
	public NhomCuaHoi getNhomCuaHoi() {
		return nhomCuaHoi;
	}

	public void setNhomCuaHoi(NhomCuaHoi nhomCuaHoi) {
		this.nhomCuaHoi = nhomCuaHoi;
	}
	
	@Command
	public void saveCongTyKinhDoanh(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr, @BindingParam("wdn") final Window wdn) {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	@Override
	@Command
	public void deleteTrangThaiConfirmAndNotify(final @BindingParam("notify") Object beanObject,
			final @BindingParam("attr") @Default(value = "*") String attr) {
		JPAQuery<Tau> tauQuery = find(Tau.class).where(QTau.tau.congTyKinhDoanh.eq(this));
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.congTyKinhDoanh.eq(this));
		JPAQuery<NhanVien> nguoiDungQuery = find(NhanVien.class).where(QNhanVien.nhanVien.congTyKinhDoanh.eq(this));

		if ((tauQuery != null && tauQuery.fetch().size() > 0) || (datVeQuery != null && datVeQuery.fetch().size() > 0)
				|| (nguoiDungQuery != null && nguoiDungQuery.fetch().size() > 0)) {
			showNotification("Dữ liệu này đang được sử dụng!", "", "warning");
		} else {
			super.deleteTrangThaiConfirmAndNotify(beanObject, attr);
		}
	}

	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				if (ten != null) {
					if (!ten.isEmpty()) {
						JPAQuery<CongTyKinhDoanh> q = find(CongTyKinhDoanh.class)
								.where(QCongTyKinhDoanh.congTyKinhDoanh.ten.trim().eq(ten.trim()));
						if (!noId()) {
							q.where(QCongTyKinhDoanh.congTyKinhDoanh.id.ne(getId()));
						}
						if (nhomCuaHoi != null) {
							q.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(getNhomCuaHoi()));
						}
						if (q.fetchCount() > 0) {
							addInvalidMessage(ctx, "lblErrTenCongTy", "Tên công ty đang được sử dụng.");
						}
					}
				}
			}
		};
	}
	
	@Transient
	public String getTenVaSoLuongVe() {
		int total = 0;
		String str = "";
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.congTyKinhDoanh.eq(this))
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()));
		
		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			datVeQuery.where(QDatVe.datVe.hoVaTen.like("%" + tuKhoa + "%"));
		}
		
		if (phanLoaiTour != null && !phanLoaiTour.noId()) {
			datVeQuery.where(QDatVe.datVe.phanLoaiTour.eq(phanLoaiTour));
		}
		
		if (getTuNgayThucHienTour() != null && getDenNgayThucHienTour() != null) {
			Calendar calNgayKhachDat = Calendar.getInstance();
			calNgayKhachDat.setTime(getFixTuNgay());
			Calendar calNgayThucHien = Calendar.getInstance();
			calNgayThucHien.setTime(getFixDenNgay());
			
			datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))))
					.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayThucHien.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHien.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(calNgayThucHien.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgayThucHienTour() == null && getDenNgayThucHienTour() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFixDenNgay());
			datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgayThucHienTour() != null && getDenNgayThucHienTour() == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFixTuNgay());
			datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayKhachDat.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}

		if (datVeQuery != null) {
			List<DatVe> datVes = datVeQuery.fetch();
			for (DatVe datVe : datVes) {
				total += datVe.getSoLuongNguoiLon() + datVe.getSoLuongTreEmDuoi10Tuoi() + datVe.getSoLuongTreEmDuoi5Tuoi();
			}
			if (total == 0) {
				str = " chưa có khách đặt vé";
			} else {
				str = " có " + total + " vé đã đặt";
			}
		}
		return datVeQuery != null ? this.getTen().toUpperCase() + str : "";
	}
	
	private Date tuNgayThucHienTour;
	private Date denNgayThucHienTour;
	private String tuKhoa;
	private PhanLoaiTour phanLoaiTour;

	@Transient
	public PhanLoaiTour getPhanLoaiTour() {
		return phanLoaiTour;
	}

	public void setPhanLoaiTour(PhanLoaiTour phanLoaiTour) {
		this.phanLoaiTour = phanLoaiTour;
	}

	@Transient
	public String getTuKhoa() {
		return tuKhoa;
	}

	public void setTuKhoa(String tuKhoa) {
		this.tuKhoa = tuKhoa;
	}

	@Transient
	public Date getTuNgayThucHienTour() {
		return tuNgayThucHienTour;
	}

	public void setTuNgayThucHienTour(Date tuNgayThucHienTour) {
		this.tuNgayThucHienTour = tuNgayThucHienTour;
	}

	@Transient
	public Date getDenNgayThucHienTour() {
		return denNgayThucHienTour;
	}

	public void setDenNgayThucHienTour(Date denNgayThucHienTour) {
		this.denNgayThucHienTour = denNgayThucHienTour;
	}
	
	@Transient
	public @Nullable Date getFixTuNgay() {
		Date fixTuNgay = tuNgayThucHienTour;
		if (fixTuNgay != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fixTuNgay);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			fixTuNgay = cal.getTime();
		}
		return fixTuNgay;
	}
	
	@Transient
	public @Nullable Date getFixDenNgay() {
		Date fixDenNgay = denNgayThucHienTour;
		if (fixDenNgay != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fixDenNgay);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			fixDenNgay = cal.getTime();
		}
		return fixDenNgay;
	}
	
	@Transient
	public long getSoLuongVe() {
		long total = 0;
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.congTyKinhDoanh.eq(this))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
					.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)));
		if (datVeQuery != null) {
			List<DatVe> datVes = datVeQuery.fetch();
			for (DatVe datVe : datVes) {
				total += datVe.getSoLuongNguoiLon() + datVe.getSoLuongTreEmDuoi10Tuoi() + datVe.getSoLuongTreEmDuoi5Tuoi();
			}
		}
		
		return total;
	}
	
	@Transient
	public String getSoLuongKhachNgayDiTour() {
		int soLuong = soLuongKhachDieuTiet + Integer.valueOf(getTongSoLuongVe());
		String str =  soLuong +"/" +getSoKhachToiDa();
		
		return str;
	}
	
	/*---------------------------------------------- DIEU TIET VE ---------------------------------------------- */
	private List<Tour> tours;
	private Set<VeDuyet> veDuyets = new HashSet<VeDuyet>();
	private Set<CongTySangCongTy> congTySangCongTys = new HashSet<CongTySangCongTy>();
	private Date ngayThucHienTour;
	private int soLuongKhachDieuTiet = 0;
	private int tongSoLuongKhachDieuTiet = 0;
	private int soLuongKhachThucHienTourThanhCong = 0;
	
	private String soLuongKhachToiDaDieuTietDuocText = "";
	private int soLuongVePhongBanVeLe = 0;
	private int soLuongVeDaDat = 0;
	private int soLuongGioiHan = 0;
	private int soLuongKhachGioiHanDieuTiet = 0;
	private boolean duocDieuTietVe = false;
	private boolean duocNhapGiayBaoTon = false;
	
	//@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//@Fetch(value = FetchMode.SUBSELECT)
	@Transient
	public Set<CongTySangCongTy> getCongTySangCongTys() {
		return congTySangCongTys;
	}

	public void setCongTySangCongTys(Set<CongTySangCongTy> congTySangCongTys) {
		this.congTySangCongTys = congTySangCongTys;
	}

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	public Set<VeDuyet> getVeDuyets() {
		return veDuyets;
	}

	public void setVeDuyets(Set<VeDuyet> veDuyets) {
		this.veDuyets = veDuyets;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	public List<Tour> getTours() {
		return tours;
	}

	public void setTours(List<Tour> tours) {
		this.tours = tours;
	}
	
	@Transient
	public Date getNgayThucHienTour() {
		if(tuNgayThucHienTour == null) {
			Date date = new Date();
			ngayThucHienTour = date;
		} else {
			ngayThucHienTour = tuNgayThucHienTour;
		}
		return ngayThucHienTour;
	}

	public void setNgayThucHienTour(Date ngayThucHienTour) {
		ngayThucHienTour = tuNgayThucHienTour;
		this.ngayThucHienTour = ngayThucHienTour;
	}
	
	@Transient
	public String getSoLuongKhachToiDaDieuTietDuocText() {
		String str = "";
		if (soLuongKhachGioiHanDieuTiet <= 0) {
			str = "(Số lượng khách đã đạt số lượng cho phép" + ")";
		} else {
			str = "(Số lượng khách tối đa có thể điều tiết được " +soLuongKhachGioiHanDieuTiet +")";
		}
		soLuongKhachToiDaDieuTietDuocText = str;
		return soLuongKhachToiDaDieuTietDuocText;
	}

	public void setSoLuongKhachToiDaDieuTietDuocText(String soLuongKhachToiDaDieuTietDuocText) {
		this.soLuongKhachToiDaDieuTietDuocText = soLuongKhachToiDaDieuTietDuocText;
	}
	
	@Transient
	public int getSoLuongVePhongBanVeLe() {
		return soLuongVePhongBanVeLe;
	}
	
	public void setSoLuongVePhongBanVeLe(int soLuongVePhongBanVeLe) {
		this.soLuongVePhongBanVeLe = soLuongVePhongBanVeLe;
	}
	
	@Transient
	public int getTongSoLuongKhachDieuTiet() {
		tongSoLuongKhachDieuTiet = dieuTietChung();
		return tongSoLuongKhachDieuTiet;
	}
	
	public void setTongSoLuongKhachDieuTiet(int tongSoLuongKhachDieuTiet) {
		this.tongSoLuongKhachDieuTiet = tongSoLuongKhachDieuTiet;
	}

	@Transient
	public int getSoLuongGioiHan() {
		return soLuongGioiHan;
	}

	public void setSoLuongGioiHan(int soLuongGioiHan) {
		this.soLuongGioiHan = soLuongGioiHan;
	}
	
	@Transient
	public int getSoLuongVeDaDat() {
		return soLuongVeDaDat;
	}

	public void setSoLuongVeDaDat(int soLuongVeDaDat) {
		this.soLuongVeDaDat = soLuongVeDaDat;
	}
	
	@Transient
	public int getSoLuongKhachDieuTiet() {
		return soLuongKhachDieuTiet;
	}

	public void setSoLuongKhachDieuTiet(int soLuongKhachDieuTiet) {
		this.soLuongKhachDieuTiet = soLuongKhachDieuTiet;
	}
	
	@Transient
	public int getSoLuongKhachGioiHanDieuTiet() {
		return soLuongKhachGioiHanDieuTiet;
	}

	public void setSoLuongKhachGioiHanDieuTiet(int soLuongKhachGioiHanDieuTiet) {
		this.soLuongKhachGioiHanDieuTiet = soLuongKhachGioiHanDieuTiet;
	}
	
	@Transient
	public boolean isDuocDieuTietVe() {
		if(getNhomCuaHoi() != null) {
			if(getNhanVien().getNhomCuaHoi() != null) {
				if(getNhanVien().getNhomCuaHoi().equals(getNhomCuaHoi())) {
					duocDieuTietVe = true;
				}
			}
		}
		return duocDieuTietVe;
	}

	public void setDuocDieuTietVe(boolean duocDieuTietVe) {
		this.duocDieuTietVe = duocDieuTietVe;
	}
	
	@Transient
	public boolean isDuocNhapGiayBaoTon() {
		if(getNhanVien().getCongTyKinhDoanh() != null) {
			if(getNhanVien().getCongTyKinhDoanh().equals(this)) {
				duocNhapGiayBaoTon = true;
			}
		}
		return duocNhapGiayBaoTon;
	}

	public void setDuocNhapGiayBaoTon(boolean duocNhapGiayBaoTon) {
		this.duocNhapGiayBaoTon = duocNhapGiayBaoTon;
	}

	@Command
	public void selectedTenNhom() {
		BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanhListAndNull");
	}
	
	@Command
	public void changeSoLuongVe() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		long congTyId = 0;

		if (tuNgayThucHienTour != null) {
			cal.setTime(tuNgayThucHienTour);
		} else {
			cal.setTime(date);
		}

		if (getCongTyKD() == null) {
			congTyId = getId();
		} else {
			congTyId = getCongTyKD().getId();
		}

		if (congTyId > 0) {
			napSoLuongGioiHan(congTyId);
			napSoLuongVeDaDat(cal, congTyId);
		}
		napSoVeDaTieuTiet(cal);
		tinhSoLuongKhachGioiHanDieuTiet();
		napFormMacDinh();

		BindUtils.postNotifyChange(null, null, this, "congTyKD");
		BindUtils.postNotifyChange(null, null, this, "soLuongGioiHan");
		BindUtils.postNotifyChange(null, null, this, "soLuongVeDaDat");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	public void napSoLuongGioiHan(long congTyId){
		JPAQuery<CongTyKinhDoanh> q = find(CongTyKinhDoanh.class)
				.where(QCongTyKinhDoanh.congTyKinhDoanh.id.eq(congTyId));
		
		CongTyKinhDoanh layCongTyGioiHan = q.fetchFirst();
		setSoLuongGioiHan((int)layCongTyGioiHan.getSoKhachToiDa());
	}
	
	public void napSoLuongVeDaDat(Calendar cal, long congTyId){
		JPAQuery<DatVe> datVeQuery = find(DatVe.class)
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNotNull()
						.and(QDatVe.datVe.congTyKinhDoanh.isNotNull()));
		
		if (getNgayThucHienTour() != null) {
			cal.setTime(ngayThucHienTour);
			datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1)
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH)))));
		}
		datVeQuery.where(QDatVe.datVe.congTyKinhDoanh.id.eq(congTyId)).orderBy(QDatVe.datVe.ngaySua.desc());
		
		int total = 0;
		datVeQuery.where(QDatVe.datVe.veDuocDuyet.isFalse());
		for (DatVe datVe : datVeQuery.fetch()) {
			total += datVe.getSoLuongNguoiLon() + datVe.getSoLuongTreEmDuoi10Tuoi() + datVe.getSoLuongTreEmDuoi5Tuoi();
		}
		setSoLuongVeDaDat(total);
	}
	
	public void napSoVeDaTieuTiet(Calendar cal){
		JPAQuery<Tour> q = find(Tour.class).where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
				.where(QTour.tour.congTyKinhDoanh.eq(this).and(QTour.tour.nhomCuaHoi.eq(getNhomCuaHoi())))
				.where(QTour.tour.coTau.eq(false))
				.where(QTour.tour.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QTour.tour.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		int total = 0;
		for (Tour tour : q.fetch()) {
			total += tour.getSoLuongKhachDieuTiet();
		}
		for (Tour tour : getTours()) {
			if(tour.noId()) {
				total += tour.getSoLuongKhachDieuTiet();
			}
		}
		setSoLuongKhachDieuTiet(total);
	}
	
	public void tinhSoLuongKhachGioiHanDieuTiet() {
		int gioiHan = 0;
		int soLuongKhachGioiHanCuaCongTy = getSoLuongGioiHan();
		int soLuongKhachHangDaDat = getSoLuongVeDaDat();
		int soLuongKhachHangDieuTiet = getSoLuongKhachDieuTiet();
		gioiHan = soLuongKhachGioiHanCuaCongTy - soLuongKhachHangDaDat - soLuongKhachHangDieuTiet;
		setSoLuongKhachGioiHanDieuTiet(gioiHan);
	}
	
	@Transient
	public int getSoLuongDieuTietTuNhom() {
		Calendar cal = Calendar.getInstance();
		int soLuong = 0;
		JPAQuery<DieuTietNhom> q = find(DieuTietNhom.class);
		if (getNgayThucHienTour() != null) {
			cal.setTime(ngayThucHienTour);
			q.where(QDieuTietNhom.dieuTietNhom.nhomCuaHoi.eq(getNhomCuaHoi()))
					.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1)
									.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.dayOfMonth()
											.eq(cal.get(Calendar.DAY_OF_MONTH)))));
			
			JPAQuery<NhomSangNhom> nhomSangNhomCong = nhomSangNhomGocQuery()
					.where(QNhomSangNhom.nhomSangNhom.nhomDieuTiet.eq(getNhomCuaHoi()))
					.where(QNhomSangNhom.nhomSangNhom.loaiDieuTiet.eq(core().DT_NHOM_SA_NHOM_TRU));
			
			JPAQuery<NhomSangNhom> nhomSangNhomTru = nhomSangNhomGocQuery()
					.where(QNhomSangNhom.nhomSangNhom.nhomCuaHoi.eq(getNhomCuaHoi()))
					.where(QNhomSangNhom.nhomSangNhom.loaiDieuTiet.eq(core().DT_NHOM_SA_NHOM_TRU));
			
			JPAQuery<VeDuyet> veDuyetQuery = find(VeDuyet.class)
					//.where(QVeDuyet.veDuyet.congTyKinhDoanh.eq(this))
					.where(QVeDuyet.veDuyet.nhomCuaHoi.eq(getNhomCuaHoi()))
					.where(QVeDuyet.veDuyet.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QVeDuyet.veDuyet.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
							.and(QVeDuyet.veDuyet.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
			
			int total = 0;
			JPAQuery<Tour> q2 = find(Tour.class).where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
					.where(QTour.tour.nhomCuaHoi.eq(getNhomCuaHoi())).where(QTour.tour.coTau.eq(false))
					.where(QTour.tour.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QTour.tour.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
							.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
			for (Tour tour : q2.fetch()) {
				total += tour.getSoLuongKhachDieuTiet();
			}
			
			for (DieuTietNhom dt : q.fetch()) {
				soLuong += dt.getSoLuongKhachDieuTiet();
			}
			for (NhomSangNhom nhomCong : nhomSangNhomCong.fetch()) {
				soLuong += nhomCong.getSoLuongKhachDieuTietCong();
			}
			for (NhomSangNhom nhomTru : nhomSangNhomTru.fetch()) {
				soLuong -= nhomTru.getSoLuongKhachDieuTietCong();
			}
			for (VeDuyet veDuyet : veDuyetQuery.fetch()) {
				soLuong += veDuyet.getSoLuongVeDuyet();
			}
			soLuong -= total;
		}
		
		return soLuong;
	}
	
	@Transient
	private JPAQuery<DieuTietNhom> dieuTietNhomQuery() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();

		if (tuNgayThucHienTour != null) {
			cal.setTime(tuNgayThucHienTour);
		} else {
			cal.setTime(date);
		}
		JPAQuery<DieuTietNhom> q = find(DieuTietNhom.class)
				.where(QDieuTietNhom.dieuTietNhom.trangThai.eq(core().TT_AP_DUNG))
				.where(QDieuTietNhom.dieuTietNhom.nhomCuaHoi.eq(getNhomCuaHoi()))
				.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.dayOfMonth()
								.eq(cal.get(Calendar.DAY_OF_MONTH))));

		return q;
	}
	
	@Transient
	private JPAQuery<NhomSangNhom> nhomSangNhomGocQuery() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();

		if (tuNgayThucHienTour != null) {
			cal.setTime(tuNgayThucHienTour);
		} else {
			cal.setTime(date);
		}
		JPAQuery<NhomSangNhom> q = find(NhomSangNhom.class)
				.where(QNhomSangNhom.nhomSangNhom.trangThai.eq(core().TT_AP_DUNG))
				.where(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.dayOfMonth()
								.eq(cal.get(Calendar.DAY_OF_MONTH))));

		return q;
	}
	
	public void napSoLuongDieuTietTuNhom() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ngayThucHienTour);
		JPAQuery<DieuTietNhom> q = dieuTietNhomQuery();
		JPAQuery<NhomSangNhom> nhomSangNhomCong = nhomSangNhomGocQuery()
				.where(QNhomSangNhom.nhomSangNhom.nhomDieuTiet.eq(getNhomCuaHoi()))
				.where(QNhomSangNhom.nhomSangNhom.loaiDieuTiet.eq(core().DT_NHOM_SA_NHOM_TRU));
		JPAQuery<NhomSangNhom> nhomSangNhomTru = nhomSangNhomGocQuery()
				.where(QNhomSangNhom.nhomSangNhom.nhomCuaHoi.eq(getNhomCuaHoi()))
				.where(QNhomSangNhom.nhomSangNhom.loaiDieuTiet.eq(core().DT_NHOM_SA_NHOM_TRU));
		
		int total = 0;
		JPAQuery<Tour> q2 = find(Tour.class)
				.where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
//				.where(QTour.tour.congTyKinhDoanh.eq(this)
				.where(QTour.tour.nhomCuaHoi.eq(getNhomCuaHoi()))
				.where(QTour.tour.coTau.eq(false))
				.where(QTour.tour.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QTour.tour.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		
		JPAQuery<VeDuyet> veDuyetQuery = find(VeDuyet.class)
				//.where(QVeDuyet.veDuyet.congTyKinhDoanh.eq(this))
				.where(QVeDuyet.veDuyet.nhomCuaHoi.eq(getNhomCuaHoi()))
				.where(QVeDuyet.veDuyet.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QVeDuyet.veDuyet.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QVeDuyet.veDuyet.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		
		for (Tour tour : q2.fetch()) {
			total += tour.getSoLuongKhachDieuTiet();
		}
		
		int soLuong = 0;
		int dieuTietNhom = 0;
		for (DieuTietNhom dt : q.fetch()) {
			soLuong += dt.getSoLuongKhachDieuTiet();
		}
		for (NhomSangNhom nhomCong : nhomSangNhomCong.fetch()) {
			System.out.println("+ " );
			soLuong += nhomCong.getSoLuongKhachDieuTietCong();
		}
		for (NhomSangNhom nhomTru : nhomSangNhomTru.fetch()) {
			soLuong -= nhomTru.getSoLuongKhachDieuTietCong();
		}
		for (VeDuyet veDuyet : veDuyetQuery.fetch()) {
			soLuong += veDuyet.getSoLuongVeDuyet();
		}
		System.out.println("dieuTietNhom " +dieuTietNhom);
		soLuong -= total;
		//soLuong += dieuTietNhom;
		setSoLuongVePhongBanVeLe(soLuong);
		BindUtils.postNotifyChange(null, null, this, "soLuongVePhongBanVeLe");
	}
	
	public void napSoLuongVePhongBanVeLe() {
		Calendar cal = Calendar.getInstance();		
		JPAQuery<DatVe> datVeQuery = find(DatVe.class)
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNull().and(QDatVe.datVe.congTyKinhDoanh.isNull()));
		
		int total = 0;
		if (getNgayThucHienTour() != null) {
			cal.setTime(ngayThucHienTour);
			datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1)
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH)))));
			
			JPAQuery<Tour> q = find(Tour.class)
					.where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
//					.where(QTour.tour.congTyKinhDoanh.eq(this)
//							.and(QTour.tour.nhomCuaHoi.eq(getNhomCuaHoi())))
					.where(QTour.tour.coTau.eq(false))
					.where(QTour.tour.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QTour.tour.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
							.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
			
			for (Tour tour : q.fetch()) {
				total += tour.getSoLuongKhachDieuTiet();
			}
		}
		
		int soLuong = 0;
		for (DatVe dv : datVeQuery.fetch()) {
			soLuong += (dv.getSoLuongNguoiLon() + dv.getSoLuongTreEmDuoi5Tuoi() + dv.getSoLuongTreEmDuoi10Tuoi());
		}
		
		soLuong -= total;
		setSoLuongVePhongBanVeLe(soLuong);
		BindUtils.postNotifyChange(null, null, this, "soLuongVePhongBanVeLe");
	}
	
	@Transient
	public int dieuTietChung() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		
		if (ngayThucHienTour != null) {
			cal.setTime(ngayThucHienTour);
		} else {
			cal.setTime(date);
		}
		
		// lay so luong dieu tiet cua rieng tung cong ty trong Tour
		List<Tour> listTour =  find(Tour.class)
				.where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
				.where(QTour.tour.congTyKinhDoanh.eq(this)
						.and(QTour.tour.nhomCuaHoi.eq(getNhomCuaHoi())))
				.where(QTour.tour.coTau.eq(isCoTau))
				.where(QTour.tour.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
				.and(QTour.tour.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH)+1))
				.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))))
				.fetch();
		int tongsl = 0;
		for (Tour tour : listTour) {
			tongsl += tour.getSoLuongKhachDieuTiet();
		}
		
		return tongsl;
	}
	
	@Transient
	public String getTongSoLuongVe() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		
		if (tuNgayThucHienTour != null) {
			cal.setTime(tuNgayThucHienTour);
		} else {
			cal.setTime(date);
		}
		
		int total = 0;
		String str = "";
		
		JPAQuery<DatVe> datVeQuery = find(DatVe.class)
				.where(QDatVe.datVe.congTyKinhDoanh.eq(this))
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()));
		 
		if (getNgayThucHienTour() != null) {
			cal.setTime(ngayThucHienTour);//
			datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		datVeQuery.orderBy(QDatVe.datVe.ngaySua.desc());
		
		if (datVeQuery != null) {
			datVeQuery.where(QDatVe.datVe.veDuocDuyet.isFalse());
			List<DatVe> datVes = datVeQuery.fetch();
			for (DatVe datVe : datVes) {
				total += datVe.getSoLuongNguoiLon() + datVe.getSoLuongTreEmDuoi10Tuoi() + datVe.getSoLuongTreEmDuoi5Tuoi();
			}
			
			if (total != 0) {
				str = "" +total;
			}
		}
		return datVeQuery != null ? str : "";
	}
	
	@Command
	public void redirectThucHienTourPage(@BindingParam("zul") String zul, @BindingParam("vmArgs") Object vmArgs,
			@BindingParam("vm") Object vm) {
		Map<String, Object> args = new HashMap<>();
		args.put("vmArgs", vmArgs);
		args.put("vm", vm);
		Executions.createComponents(zul, null, args);
	}
	
	@Command
	public void xacNhanDatTour() {
		Tour tour = new Tour();
		if(soLuongKhachDieuTiet <= soLuongVePhongBanVeLe && soLuongKhachDieuTiet <= soLuongKhachGioiHanDieuTiet) {
			int soLuongKiemTra = soLuongVePhongBanVeLe > soLuongKhachGioiHanDieuTiet ? 
					(soLuongVePhongBanVeLe - soLuongKhachGioiHanDieuTiet) : (soLuongKhachGioiHanDieuTiet - soLuongVePhongBanVeLe);
			System.out.println("soLuongKiemTra " +soLuongKiemTra);
			/*if(soLuongKiemTra == 0 || soLuongKhachDieuTiet <= soLuongKiemTra) {*/
			if (soLuongKiemTra > 0) {
				if(soLuongKhachDieuTiet > 0) {
					tour.setCongTyKinhDoanh(this);
					CongTyKinhDoanh congTyDieuTiet = find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.id.eq(congTyKD.getId())).fetchFirst();
					tour.setCongTyDieuTiet(congTyDieuTiet);
					tour.setNgayThucHienTour(getNgayThucHienTour());
					tour.setNhomCuaHoi(getNhom());
					tour.setSoLuongKhachDieuTiet(getSoLuongKhachDieuTiet());
					tour.setCoTau(false);
					if (tours.size() > 0) {
						for (Tour tour2 : tours) {
							System.out.println("8");
							if (tour2.getNhomCuaHoi().equals(getNhom())) {
								if (tour2.getCongTyKinhDoanh().equals(this)) {
									if (tour2.getCongTyDieuTiet().equals(congTyDieuTiet)) {
										tour2.setSoLuongKhachDieuTiet(getSoLuongKhachDieuTiet() + tour2.getSoLuongKhachDieuTiet());
										tour = tour2;
										System.out.println("%%");
										break;
									}
								}
							}
						}
					}
					
					if (tours.contains(tour)) {
						tours.remove(tour);
						System.out.println("exist");
					}
					tours.add(tour);

					soLuongVePhongBanVeLe -= soLuongKhachDieuTiet;
					soLuongKhachGioiHanDieuTiet -= soLuongKhachDieuTiet;
					napFormMacDinh();
				}
			}	
		}
		BindUtils.postNotifyChange(null, null, this, "tours");
		BindUtils.postNotifyChange(null, null, this, "soLuongVePhongBanVeLe");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	@Command
	public void xoaTour(Tour tour) {
		soLuongKhachGioiHanDieuTiet += tour.getSoLuongKhachDieuTiet();
		soLuongVePhongBanVeLe += tour.getSoLuongKhachDieuTiet();

		if (!tour.noId()) {
			for (Tour t : getTours()) {
				if (t.equals(tour)) {
					tour.setDaXoa(true);
					tour.setTrangThai(core().TT_DA_XOA);
					xoaTours.add(tour);
					break;
				}
			}
		} else {
			getTours().remove(tour);
		}
		if(tours.size() > 0) {
			tours = tours.stream().filter(t -> !t.getTrangThai().equals(core().TT_DA_XOA)).collect(Collectors.toList());
		}
		
		BindUtils.postNotifyChange(null, null, this, "tours");
		BindUtils.postNotifyChange(null, null, this, "soLuongVePhongBanVeLe");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	@Command
	public void saveDatTour(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		ThucHienTourService srv = (ThucHienTourService)listObject;
		NhomCuaHoi nhom = this.getNhomCuaHoi();
		//Luu tour hien tai
		for (Tour tour : tours) {
			tour.saveNotShowNotification();
		}
		//Luu tour bi xoa
		for (Tour tour : xoaTours) {
			tour.saveNotShowNotification();
		}
		this.saveNotShowNotification();
		nhom.saveNotShowNotification();
		wdn.detach();
		srv.setCongTyKinhDoanh(null);
		
		BindUtils.postNotifyChange(null, null, this, "nhomCuaHoi");
		BindUtils.postNotifyChange(null, null, this, "tours");
		BindUtils.postNotifyChange(null, null, listObject, "congTyKinhDoanh");
		BindUtils.postNotifyChange(null, null, listObject, "nhomCuaHois");
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	@Command
	public void validateSoLuong() {
		
	}
	
	@Transient
	public AbstractValidator getValidatorTour() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				if (ten != null) {
					if (!ten.isEmpty()) {
						JPAQuery<CongTyKinhDoanh> q = find(CongTyKinhDoanh.class)
								.where(QCongTyKinhDoanh.congTyKinhDoanh.ten.trim().eq(ten.trim()));
						if (!noId()) {
							q.where(QCongTyKinhDoanh.congTyKinhDoanh.id.ne(getId()));
						}
						if (nhomCuaHoi != null) {
							q.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(getNhomCuaHoi()));
						}
						if (q.fetchCount() > 0) {
							addInvalidMessage(ctx, "lblErrTenCongTy", "Tên công ty đang được sử dụng.");
						}
					}
				}

				if (soKhachToiDa <= 0) {
					addInvalidMessage(ctx, "lblErrTenCongTy", "Số khách tối đa phải lớn hơn 0.");
				}
			}
		};
	}
	
	/*---------------------------------------------------- Dung chung ------------------------------------------------------*/
	private CongTyKinhDoanh congTyKD;
	private NhomCuaHoi nhom;
	private boolean isCoTau;
	private List<Tour> xoaTours = new ArrayList<Tour>();
	
	@Transient
	public CongTyKinhDoanh getCongTyKD() {
		return congTyKD;
	}

	public void setCongTyKD(CongTyKinhDoanh congTyKD) {
		this.congTyKD = congTyKD;
	}

	@Transient
	public List<Tour> getXoaTours() {
		return xoaTours;
	}

	public void setXoaTours(List<Tour> xoaTours) {
		this.xoaTours = xoaTours;
	}

	@Transient
	public boolean isCoTau() {
		return isCoTau;
	}

	public void setCoTau(boolean isCoTau) {
		this.isCoTau = isCoTau;
	}
	
	@Transient
	public NhomCuaHoi getNhom() {
		if(nhom == null) {
			nhom = getNhomCuaHoi();
		}
		return nhom;
	}

	public void setNhom(NhomCuaHoi nhom) {	
		this.nhom = nhom;
	}
	
	@Transient
	public List<CongTyKinhDoanh> getCongTyKinhDoanhListAndNull() {
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		congTyKinhDoanhList.add(null);
		if (nhom != null) {
			congTyKinhDoanhList.addAll(
					find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
							.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.id.eq(nhom.getId()))
							.orderBy(QNhomCuaHoi.nhomCuaHoi.ten.asc()).fetch());
		}

		return congTyKinhDoanhList;
	}

	public void filterTours() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();

		if (ngayThucHienTour != null) {
			cal.setTime(ngayThucHienTour);
		} else {
			cal.setTime(date);
		}

		tours = find(Tour.class).where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
				.where(QTour.tour.congTyKinhDoanh.eq(this)).where(QTour.tour.coTau.eq(isCoTau))
				.where(QTour.tour.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QTour.tour.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))))
				.fetch();
		BindUtils.postNotifyChange(null, null, this, "tours");
	}
	
	@Transient
	public int getSoLuongKhachThucHienTourThanhCong() {
		soLuongKhachThucHienTourThanhCong = 0;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		if (ngayThucHienTour != null) {
			cal.setTime(ngayThucHienTour);
		} else {
			cal.setTime(date);
		}

		List<Tour> listTour = find(Tour.class)
				.where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
				.where(QTour.tour.congTyKinhDoanh.eq(this).and(QTour.tour.nhomCuaHoi.eq(getNhomCuaHoi())))
				.where(QTour.tour.coTau.eq(true))
				.where(QTour.tour.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QTour.tour.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))))
				.fetch();

		for (Tour tour : listTour) {
			soLuongKhachThucHienTourThanhCong += tour.getSoLuongNguoiLon() + tour.getSoLuongTreEm1Den3()
					+ tour.getSoLuongTrEm4Den9();
		}
		return soLuongKhachThucHienTourThanhCong;
	}
	
	public void napCongTyKinhDoanh() {
		if(congTyKD == null) {
			CongTyKinhDoanh cty = find(CongTyKinhDoanh.class)
					.where(QCongTyKinhDoanh.congTyKinhDoanh.id.eq(getId())).fetchFirst();
			congTyKD = cty;
		}
	}
	
	/*---------------------------------------------------- Ma bao ton -----------------------------------------------------------*/
	private int soLuongNguoiLon = 0;
	private int soLuongTreEm1Den3 = 0;
	private int soLuongTrEm4Den9 = 0;
	private int soGhe = 0;
	private Tau tau;
	private String maBaoTon;
	private boolean duocNhapBaoTon = false;

	@Transient
	public Tau getTau() {
		return tau;
	}
	
	@Transient
	public boolean isDuocNhapBaoTon() {
		if (getNhanVien().getCongTyKinhDoanh() != null) {
			if (getNhanVien().getCongTyKinhDoanh().equals(this)) {
				duocNhapBaoTon = true;
			}
		}
		return duocNhapBaoTon;
	}

	public void setDuocNhapBaoTon(boolean duocNhapBaoTon) {
		this.duocNhapBaoTon = duocNhapBaoTon;
	}

	public void setTau(Tau tau) {
		this.tau = tau;
	}

	@Transient
	public String getMaBaoTon() {
		return maBaoTon;
	}

	public void setMaBaoTon(String maBaoTon) {
		this.maBaoTon = maBaoTon;
	}

	@Transient
	public int getSoLuongNguoiLon() {
		return soLuongNguoiLon;
	}

	public void setSoLuongNguoiLon(int soLuongNguoiLon) {
		this.soLuongNguoiLon = soLuongNguoiLon;
	}

	@Transient
	public int getSoLuongTreEm1Den3() {
		return soLuongTreEm1Den3;
	}

	public void setSoLuongTreEm1Den3(int soLuongTreEm1Den3) {
		this.soLuongTreEm1Den3 = soLuongTreEm1Den3;
	}

	@Transient
	public int getSoLuongTrEm4Den9() {
		return soLuongTrEm4Den9;
	}

	public void setSoLuongTrEm4Den9(int soLuongTrEm4Den9) {
		this.soLuongTrEm4Den9 = soLuongTrEm4Den9;
	}
	
	@Transient
	public int getSoGhe() {
		return soGhe;
	}

	public void setSoGhe(int soGhe) {
		this.soGhe = soGhe;
	}

	@Transient
	public List<Tau> getTauListAndNull() {
		List<Tau> tauList = new ArrayList<Tau>();
		tauList.add(null);
		tauList.addAll(
				find(Tau.class).where(QTau.tau.trangThai.eq(core().TT_AP_DUNG))
						.where(QTau.tau.congTyKinhDoanh.eq(this))
						.fetch());
		return tauList;
	}
	
	@Command
	public void validateSoLuongMBT() {
		
	}
	
	@Command
	public void selectedTau() {
		truSoGheTrenTau(tau);
	}
	
	public void truSoGheTrenTau(Tau tau) {
		int soGheTrenTau = 0;
		if(tau != null) {
			soGheTrenTau = (int) tau.getSoGhe();
			for (Tour tour : tours) {
				if(tour.getTau().equals(tau)) {
					soGheTrenTau -= tour.getSoLuongNguoiLon() + tour.getSoLuongTreEm1Den3() + tour.getSoLuongTrEm4Den9();
				}
			}
		}
		setSoGhe(soGheTrenTau);
		BindUtils.postNotifyChange(null, null, this, "soGhe");
	}
	
	public void napFormMacDinh() {
		setSoLuongNguoiLon(0);
		setSoLuongTreEm1Den3(0);
		setSoLuongTrEm4Den9(0);
		setMaBaoTon("");
		setSoLuongKhachDieuTiet(0);
		
		BindUtils.postNotifyChange(null, null, this, "maBaoTon");
		BindUtils.postNotifyChange(null, null, this, "soLuongNguoiLon");
		BindUtils.postNotifyChange(null, null, this, "soLuongTreEm1Den3");
		BindUtils.postNotifyChange(null, null, this, "soLuongTrEm4Den9");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachDieuTiet");
	}
	
	@Command
	public void xoaMaBaoTonTour(Tour tour) {
		CongTyKinhDoanh congTy = this;
		Messagebox.show("Bạn muốn giấy bảo tồn này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK, Messagebox.QUESTION,
			new EventListener<Event>() {
				@Override
				public void onEvent(final Event event) {
					if (Messagebox.ON_OK.equals(event.getName())) {
						if (!tour.noId()) {
							for (Tour t : getTours()) {
								if (t.equals(tour)) {
									tour.setDaXoa(true);
									tour.setTrangThai(core().TT_DA_XOA);
									xoaTours.add(tour);
									break;
								}
							}
						} 
						getTours().remove(tour);
						if(tours.size() > 0) {
							tours = tours.stream().filter(t -> !t.getTrangThai().equals(core().TT_DA_XOA)).collect(Collectors.toList());
						}
						truSoGheTrenTau(tau);
						BindUtils.postNotifyChange(null, null, congTy, "tours");
					}	
				}
		});
	}
	
	@Command
	public void xacNhanMaBaoTon() {
		Tour tour = new Tour();
		tour.setCongTyKinhDoanh(this);
		//tour.setCongTyDieuTiet(congTyKinhDoanh);
		tour.setNgayThucHienTour(getNgayThucHienTour());
		tour.setNhomCuaHoi(getNhom());
		tour.setMaBaoTon(getMaBaoTon());
		//tour.setSoLuongKhachDieuTiet(getSoLuongKhachDieuTiet());
		tour.setSoLuongNguoiLon(getSoLuongNguoiLon());
		tour.setSoLuongTreEm1Den3(getSoLuongTreEm1Den3());
		tour.setSoLuongTrEm4Den9(getSoLuongTrEm4Den9());
		tour.setCoTau(isCoTau);
		if (tau != null) {
			tour.setTau(tau);
		}		
		getTours().add(tour);
		truSoGheTrenTau(tau);
		napFormMacDinh();
		BindUtils.postNotifyChange(null, null, this, "tours");
	}
	
	@Command
	public void saveMaBaoTon(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		ThucHienTourService srv = (ThucHienTourService)listObject;
		//Luu tour hien tai
		for (Tour tour : tours) {
			tour.saveNotShowNotification();
		}
		//Luu tour bi xoa
		for (Tour tour : xoaTours) {
			tour.saveNotShowNotification();
		}
		//Cap nhat cong ty
		this.saveNotShowNotification();
		wdn.detach();
		srv.setCongTyKinhDoanh(null);
		
		
		BindUtils.postNotifyChange(null, null, this, "nhomCuaHoi");
		BindUtils.postNotifyChange(null, null, this, "tours");
		BindUtils.postNotifyChange(null, null, listObject, "congTyKinhDoanh");
		BindUtils.postNotifyChange(null, null, listObject, "nhomCuaHois");
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	@Transient
	public AbstractValidator getValidatorMBT() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				/*int n = 0;
				n += soLuongNguoiLon == 0 ? 1 : 0;
				n += soLuongTreEm1Den3 == 0 ? 1 : 0;
				n += soLuongTrEm4Den9 == 0 ? 1 : 0;*/
				if(soLuongNguoiLon <= 0) {
					addInvalidMessage(ctx, "lblErrSoLuongMBT", "Số lượng người lớn phải lớn hơn 0.");
				} else {
					if (tau != null) {
						int tong = 0;
						tong += getSoLuongNguoiLon() + getSoLuongTreEm1Den3() + getSoLuongTrEm4Den9();
						if (getSoGhe() < tong) {
							addInvalidMessage(ctx, "lblErrSoLuongGhe",
									"Tổng Số lượng phải bé hơn hoặc bằng số ghế tối đa trên tàu.");
						} else {
							for (Tour tour : getTours()) {
								if (tour.getTau().equals(getTau())) {
									addInvalidMessage(ctx, "lblErrTau", "Tàu hiện tại đã có trong danh sách.");
								}
							}
						}
					}
				}
			}
		};
	}
	
	private Long soLuongNguoiLonNhat;
	private Long soLuongTreEm4Den9Tuoi;
	private Long soLuongTreEm1Den3Tuoi;

	@Transient
	public Long getSoLuongNguoiLonNhat() {
		return soLuongNguoiLonNhat;
	}

	public void setSoLuongNguoiLonNhat(Long soLuongNguoiLonNhat) {
		this.soLuongNguoiLonNhat = soLuongNguoiLonNhat;
	}

	@Transient
	public Long getSoLuongTreEm4Den9Tuoi() {
		return soLuongTreEm4Den9Tuoi;
	}

	public void setSoLuongTreEm4Den9Tuoi(Long soLuongTreEm4Den9Tuoi) {
		this.soLuongTreEm4Den9Tuoi = soLuongTreEm4Den9Tuoi;
	}

	@Transient
	public Long getSoLuongTreEm1Den3Tuoi() {
		return soLuongTreEm1Den3Tuoi;
	}

	public void setSoLuongTreEm1Den3Tuoi(Long soLuongTreEm1Den3Tuoi) {
		this.soLuongTreEm1Den3Tuoi = soLuongTreEm1Den3Tuoi;
	}
	
	/*---------------------------------------------------- VÉ DUYỆT ----------------------------------------------------*/
	private @Nullable Date tuNgay;
	private @Nullable Date denNgay;

	@Transient
	public @Nullable Date getTuNgay() {
		return tuNgay;
	}

	public void setTuNgay(Date _tuNgay) {
		this.tuNgay = _tuNgay;
	}

	@Transient
	public @Nullable Date getFixTuNgayDat() {
		Date fixTuNgay = tuNgay;
		if (fixTuNgay != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fixTuNgay);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			fixTuNgay = cal.getTime();
		}
		return fixTuNgay;
	}

	@Transient
	public @Nullable Date getDenNgay() {
		return denNgay;
	}

	public void setDenNgay(Date _denNgay) {
		this.denNgay = _denNgay;
	}

	@Transient
	public @Nullable Date getFixDenNgayThucHien() {
		Date fixDenNgay = denNgay;
		if (fixDenNgay != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fixDenNgay);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			fixDenNgay = cal.getTime();
		}
		return fixDenNgay;
	}
	
	/*---------------------------------------------------- HIEN THI THONG KE ----------------------------------------------------*/
	
	private int tongSoLuongVeDuyetCuaCongTy = 0;
	private int tongSoLuongVeDatBanDauCuaCongTy = 0;
	private String tongVeSau7h30 = "";
	
	@Transient
	public int getTongSoLuongVeDatBanDauCuaCongTy() {
		return tongSoLuongVeDatBanDauCuaCongTy;
	}

	public void setTongSoLuongVeDatBanDauCuaCongTy(int tongSoLuongVeDatBanDauCuaCongTy) {
		this.tongSoLuongVeDatBanDauCuaCongTy = tongSoLuongVeDatBanDauCuaCongTy;
	}

	@Transient
	public int getTongSoLuongVeDuyetCuaCongTy() {
		return tongSoLuongVeDuyetCuaCongTy;
	}

	public void setTongSoLuongVeDuyetCuaCongTy(int tongSoLuongVeDuyetCuaCongTy) {
		this.tongSoLuongVeDuyetCuaCongTy = tongSoLuongVeDuyetCuaCongTy;
	}

	@Transient
	public String getTongVeSau7h30() {
		return tongVeSau7h30;
	}

	public void setTongVeSau7h30(String tongVeSau7h30) {
		this.tongVeSau7h30 = tongVeSau7h30;
	}

	/*---------------------------------------------------- DIEU TIET SANG CONG TY ----------------------------------------------------*/
	private String soLuongDieuTietTuCongTyText = "0";
	private CongTyKinhDoanh congTyDieuTiet;
	private NhomCuaHoi nhomDieuTietCongTy;
	private List<CongTySangCongTy> xoaCongTySangCongTys = new ArrayList<CongTySangCongTy>();
	
	@Transient
	public NhomCuaHoi getNhomDieuTietCongTy() {
		if(nhomDieuTietCongTy == null) {
			nhomDieuTietCongTy = getNhomCuaHoi();
		}
		return nhomDieuTietCongTy;
	}

	public void setNhomDieuTietCongTy(NhomCuaHoi nhomDieuTietCongTy) {
		this.nhomDieuTietCongTy = nhomDieuTietCongTy;
	}

	@Transient
	public CongTyKinhDoanh getCongTyDieuTiet() {
		return congTyDieuTiet;
	}

	public void setCongTyDieuTiet(CongTyKinhDoanh congTyDieuTiet) {
		this.congTyDieuTiet = congTyDieuTiet;
	}

	@Transient
	public List<CongTySangCongTy> getXoaCongTySangCongTys() {
		return xoaCongTySangCongTys;
	}

	public void setXoaCongTySangCongTys(List<CongTySangCongTy> xoaCongTySangCongTys) {
		this.xoaCongTySangCongTys = xoaCongTySangCongTys;
	}
	
	@Transient
	public String getSoLuongDieuTietTuCongTyText() {
		int soLuongDieuTietTuCongTy = dieuTietCongTy();
		soLuongDieuTietTuCongTyText = "" + soLuongDieuTietTuCongTy;
		if (soLuongDieuTietTuCongTy > 0) {
			soLuongDieuTietTuCongTyText = "+ " + soLuongDieuTietTuCongTy;
		}
		return soLuongDieuTietTuCongTyText;
	}

	public void setSoLuongDieuTietTuCongTyText(String soLuongDieuTietTuCongTyText) {
		this.soLuongDieuTietTuCongTyText = soLuongDieuTietTuCongTyText;
	}

	@Transient
	public int dieuTietCongTy() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();

		if (ngayThucHienTour != null) {
			cal.setTime(ngayThucHienTour);
		} else {
			cal.setTime(date);
		}

		int soLuong = 0;
		JPAQuery<CongTySangCongTy> congTySangCongTyCong = congTySangCongTyQuery()
				.where(QCongTySangCongTy.congTySangCongTy.congTyDieuTiet.eq(this));
		JPAQuery<CongTySangCongTy> congTySangCongTyTru = congTySangCongTyQuery()
				.where(QCongTySangCongTy.congTySangCongTy.congTyKinhDoanh.eq(this));

		for (CongTySangCongTy congTyCong : congTySangCongTyCong.fetch()) {
			soLuong += congTyCong.getSoLuongKhachDieuTiet();
		}
		for (CongTySangCongTy congTyTru : congTySangCongTyTru.fetch()) {
			soLuong -= congTyTru.getSoLuongKhachDieuTiet();
		}
		return soLuong;
	}
	
	@Transient
	public List<CongTyKinhDoanh> getCongTyKinhDoanhListNotMe() {
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		if (nhomDieuTietCongTy != null) {
			congTyKinhDoanhList.addAll(
					find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
							.where(QCongTyKinhDoanh.congTyKinhDoanh.ne(this))
							.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.id.eq(nhomDieuTietCongTy.getId()))
							.orderBy(QNhomCuaHoi.nhomCuaHoi.ten.asc()).fetch());
		}
		return congTyKinhDoanhList;
	}
	
	@Transient
	private JPAQuery<CongTySangCongTy> congTySangCongTyQuery() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();

		if (tuNgayThucHienTour != null) {
			cal.setTime(tuNgayThucHienTour);
		} else {
			cal.setTime(date);
		}
		JPAQuery<CongTySangCongTy> q = find(CongTySangCongTy.class)
				.where(QCongTySangCongTy.congTySangCongTy.trangThai.eq(core().TT_AP_DUNG))
				.where(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour.dayOfMonth()
								.eq(cal.get(Calendar.DAY_OF_MONTH))));

		return q;
	}
	
	public void napSoVeDaTieuTietCongTy(Calendar cal, long nhomId){
		JPAQuery<CongTySangCongTy> q = congTySangCongTyQuery()
				.where(QCongTySangCongTy.congTySangCongTy.nhomCuaHoi.id.eq(nhomId));
		int total = 0;
		for (CongTySangCongTy cty : q.fetch()) {
			total += cty.getSoLuongKhachDieuTiet();
		}
		for (CongTySangCongTy cty : getCongTySangCongTys()) {
			if (cty.noId()) {
				total += cty.getSoLuongKhachDieuTiet();
			}
		}
		setSoLuongKhachDieuTiet(total);
	}
	
	public void napSoLuongDieuTietTuCongTy() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ngayThucHienTour);
		JPAQuery<CongTySangCongTy> congTySangCongTyCong = congTySangCongTyQuery()
				.where(QCongTySangCongTy.congTySangCongTy.congTyDieuTiet.eq(this));
		JPAQuery<CongTySangCongTy> congTySangCongTyTru = congTySangCongTyQuery()
				.where(QCongTySangCongTy.congTySangCongTy.congTyKinhDoanh.eq(this));
		
		JPAQuery<Tour> q2 = find(Tour.class)
				.where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
				.where(QTour.tour.congTyKinhDoanh.eq(this))
				.where(QTour.tour.coTau.eq(false))
				.where(QTour.tour.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QTour.tour.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));

		int soLuong = 0;
		for (Tour tour : q2.fetch()) {
			soLuong += tour.getSoLuongKhachDieuTiet();
		}
		
		for (CongTySangCongTy congTyCong : congTySangCongTyCong.fetch()) {
			soLuong += congTyCong.getSoLuongKhachDieuTiet();
		}
		for (CongTySangCongTy congTyTru : congTySangCongTyTru.fetch()) {
			soLuong -= congTyTru.getSoLuongKhachDieuTiet();
		}
		
		napSoLuongVeDaDat(cal, this.getId());
		soLuong += getSoLuongVeDaDat();
		setSoLuongVePhongBanVeLe(soLuong);
		BindUtils.postNotifyChange(null, null, this, "soLuongVePhongBanVeLe");
	}
	
	public void truSoKhachDieuTietSangCongTy(CongTyKinhDoanh congTyDieuTiet) {
		int gioiHan = soLuongGioiHan;
		if (congTyDieuTiet != null) {
			int soKhachToiDa = (int) congTyDieuTiet.getSoKhachToiDa();
			int soLuongKhachHangDaDat = getSoLuongVeDaDat();
			for (CongTySangCongTy congTySangCongTy : congTySangCongTys) {
				if (congTySangCongTy.getCongTyDieuTiet().equals(congTyDieuTiet)) {
					gioiHan = soKhachToiDa - congTySangCongTy.getSoLuongKhachDieuTiet();
					break;
				}
			}
			gioiHan -= soLuongKhachHangDaDat;
		}
		setSoLuongKhachGioiHanDieuTiet(gioiHan);
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	public void napDanhSachDieuTietCongTy() {
		JPAQuery<CongTySangCongTy> q = congTySangCongTyQuery()
				.where(QCongTySangCongTy.congTySangCongTy.congTyKinhDoanh.eq(this));
		congTySangCongTys.clear();
		congTySangCongTys.addAll(q.fetch());
		BindUtils.postNotifyChange(null, null, this, "congTySangCongTys");
	}
	
	@Command
	public void changeCongTy() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		long congTyId = 0;
		long nhomId = 0;
		
		if (tuNgayThucHienTour != null) {
			cal.setTime(tuNgayThucHienTour);
		} else {
			cal.setTime(date);
		}

		if (getCongTyDieuTiet() != null) {
			congTyId = getCongTyDieuTiet().getId();
			nhomId = getCongTyDieuTiet().getNhomCuaHoi().getId();
			napSoLuongGioiHan(congTyId);
			napSoLuongVeDaDat(cal, congTyId);
			napSoVeDaTieuTietCongTy(cal, nhomId);		
		}
	
		truSoKhachDieuTietSangCongTy(congTyDieuTiet);
		napFormMacDinh();

		BindUtils.postNotifyChange(null, null, this, "congTyDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongGioiHan");
		BindUtils.postNotifyChange(null, null, this, "soLuongVeDaDat");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}

	@Command
	public void selectedTenNhomCongTy() {
		BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanhListNotMe");
	}
	
	@Command
	public void xacNhanDieuTietCongTy() {
		CongTySangCongTy congTySangCongTy = new CongTySangCongTy();
		int soLuongKiemTra = soLuongVePhongBanVeLe > soLuongKhachGioiHanDieuTiet ? 
				soLuongKhachGioiHanDieuTiet : soLuongVePhongBanVeLe;
		System.out.println("soLuongKiemTra " +soLuongKiemTra);
		if (soLuongKhachDieuTiet > 0 && (soLuongKhachGioiHanDieuTiet > 0 && soLuongKiemTra >= soLuongKhachDieuTiet)
				&& soLuongKiemTra >= 0) {
			if(soLuongKhachDieuTiet > 0) {
				congTySangCongTy.setCongTyKinhDoanh(this);
				CongTyKinhDoanh cTyDieuTiet = find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.id.eq(congTyDieuTiet.getId())).fetchFirst();
				congTySangCongTy.setCongTyDieuTiet(cTyDieuTiet);
				congTySangCongTy.setNgayThucHienTour(getNgayThucHienTour());
				congTySangCongTy.setNhomCuaHoi(getNhom());
				congTySangCongTy.setSoLuongKhachDieuTiet(getSoLuongKhachDieuTiet());
				if (congTySangCongTys.size() > 0) {
					for (CongTySangCongTy congTySangCongTy2 : congTySangCongTys) {
						if (congTySangCongTy2.getNhomCuaHoi().equals(getNhom())) {
							if (congTySangCongTy2.getCongTyKinhDoanh().equals(this)) {
								if (congTySangCongTy2.getCongTyDieuTiet().equals(cTyDieuTiet)) {
									congTySangCongTy2.setSoLuongKhachDieuTiet(getSoLuongKhachDieuTiet() + congTySangCongTy2.getSoLuongKhachDieuTiet());
									congTySangCongTy = congTySangCongTy2;
									break;
								}
							}
						}
					}
				}
				
				if (congTySangCongTys.contains(congTySangCongTy)) {
					congTySangCongTys.remove(congTySangCongTy);
				}
				congTySangCongTys.add(congTySangCongTy);
				
				truSoKhachDieuTietSangCongTy(congTyDieuTiet);
				soLuongVePhongBanVeLe -= soLuongKhachDieuTiet;
				//soLuongKhachGioiHanDieuTiet -= soLuongKhachDieuTiet;
				napFormMacDinh();
			}
		}	
		BindUtils.postNotifyChange(null, null, this, "congTySangCongTys");
		BindUtils.postNotifyChange(null, null, this, "soLuongVePhongBanVeLe");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	@Command
	public void xoaCongTy(CongTySangCongTy congTy) {
		if(congTyDieuTiet != null) {
			if (congTy.getCongTyDieuTiet().equals(congTyDieuTiet)) {
				soLuongKhachGioiHanDieuTiet += congTy.getSoLuongKhachDieuTiet();
			}
		}
		soLuongVePhongBanVeLe += congTy.getSoLuongKhachDieuTiet();

		if (!congTy.noId()) {
			for (CongTySangCongTy t : congTySangCongTys) {
				if (t.equals(congTy)) {
					congTy.setDaXoa(true);
					congTy.setTrangThai(core().TT_DA_XOA);
					xoaCongTySangCongTys.add(congTy);
					break;
				}
			}
		} else {
			congTySangCongTys.remove(congTy);
		}
		if(congTySangCongTys.size() > 0) {
			congTySangCongTys = congTySangCongTys.stream().filter(t -> !t.getTrangThai().equals(core().TT_DA_XOA)).collect(Collectors.toSet());
		}
		
		BindUtils.postNotifyChange(null, null, this, "congTySangCongTys");
		BindUtils.postNotifyChange(null, null, this, "soLuongVePhongBanVeLe");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	@Command
	public void saveDieuTietCongTy(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		ThucHienTourService srv = (ThucHienTourService)listObject;
		NhomCuaHoi nhom = this.getNhomCuaHoi();
		//Luu tour hien tai
		for (CongTySangCongTy congTySangCongTy : congTySangCongTys) {
			congTySangCongTy.saveNotShowNotification();
		}
		//Luu tour bi xoa
		for (CongTySangCongTy congTySangCongTy : xoaCongTySangCongTys) {
			congTySangCongTy.saveNotShowNotification();
		}
		this.saveNotShowNotification();
		nhom.saveNotShowNotification();
		wdn.detach();
		srv.setCongTyKinhDoanh(null);
		
		BindUtils.postNotifyChange(null, null, this, "nhomCuaHoi");
		BindUtils.postNotifyChange(null, null, this, "congTySangCongTys");
		BindUtils.postNotifyChange(null, null, listObject, "congTyKinhDoanh");
		BindUtils.postNotifyChange(null, null, listObject, "nhomCuaHois");
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
}
