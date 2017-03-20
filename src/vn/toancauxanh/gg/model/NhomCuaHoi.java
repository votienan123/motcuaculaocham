package vn.toancauxanh.gg.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.enums.TinhTrangVeEnum;
import vn.toancauxanh.gg.model.enums.TrangThaiDuyetVeEnum;
import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.NhanVien;
import vn.toancauxanh.model.QNhanVien;

@Entity
@Table(name="nhomcuahoi")
public class NhomCuaHoi extends Model<NhomCuaHoi> {

	private String ten = "";
	private String moTa = "";
	private String truongNhom = "";
	private String phoNhom = "";
	private long tongSoGhe = 0;
	@SuppressWarnings("unused")
	private long soLuongTau = 0;
	@SuppressWarnings("unused")
	private long soCongTy = 0;
	private long soLuongDieuTietThem = 0;
	
	public long getSoLuongDieuTietThem() {
		return soLuongDieuTietThem;
	}

	public void setSoLuongDieuTietThem(long soLuongDieuTietThem) {
		this.soLuongDieuTietThem = soLuongDieuTietThem;
	}

	@Transient
	public long getSoCongTy() {
		JPAQuery<CongTyKinhDoanh> congTyKinhDoanhList = find(CongTyKinhDoanh.class)
				.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.id.eq(getId()));
		return congTyKinhDoanhList.fetchCount();
	}

	public void setSoCongTy(long soCongTy) {
		this.soCongTy = soCongTy;
	}

	@Transient
	public long getSoLuongTau() {
		long soLuongTau = 0;
		
		JPAQuery<CongTyKinhDoanh> congTyKinhDoanhList = find(CongTyKinhDoanh.class)
				.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.id.eq(getId()));
		if (!congTyKinhDoanhList.fetch().isEmpty()) {
			for(CongTyKinhDoanh i:congTyKinhDoanhList.fetch()) {
				soLuongTau += i.getSoLuongTau();
			}
		}
		return soLuongTau;
	}

	public void setSoLuongTau(long soLuongTau) {
		this.soLuongTau = soLuongTau;
	}
	
	
	public String getTruongNhom() {
		return truongNhom;
	}

	public void setTruongNhom(String truongNhom) {
		this.truongNhom = truongNhom;
	}

	public String getPhoNhom() {
		return phoNhom;
	}

	public void setPhoNhom(String phoNhom) {
		this.phoNhom = phoNhom;
	}

	public long getTongSoGhe() {
		return tongSoGhe;
	}

	public void setTongSoGhe(long tongSoGhe) {
		this.tongSoGhe = tongSoGhe;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@Lob
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
	@Command
	public void saveNhomCuaHoi(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	@Override
	@Command
	public void deleteTrangThaiConfirmAndNotify(final @BindingParam("notify") Object beanObject,
			final @BindingParam("attr") @Default(value = "*") String attr) {
		JPAQuery<CongTyKinhDoanh> congTyKinhDoanhQuery = find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(this));
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.nhomCuaHoi.eq(this));
		JPAQuery<NhanVien> nguoiDungQuery = find(NhanVien.class).where(QNhanVien.nhanVien.nhomCuaHoi.eq(this));
		
		if ((congTyKinhDoanhQuery != null && congTyKinhDoanhQuery.fetch().size() > 0) || (datVeQuery != null && datVeQuery.fetch().size() > 0)
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
						JPAQuery<NhomCuaHoi> q = find(NhomCuaHoi.class)
								.where(QNhomCuaHoi.nhomCuaHoi.ten.trim().eq(ten.trim()));
						if (!noId()) {
							q.where(QNhomCuaHoi.nhomCuaHoi.id.ne(getId()));
						}
						if (q.fetchCount() > 0) {
							addInvalidMessage(ctx, "lblErrTenNhom", "Tên nhóm đang được sử dụng.");
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
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.nhomCuaHoi.eq(this))
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
	
	/*-------------------------------------------------- DIEU TIET ---------------------------------------------- */
	private Date ngayThucHienTour;
	private NhomCuaHoi nhomDieuTiet;
	private List<DieuTietNhom> dieuTietNhoms = new ArrayList<DieuTietNhom>();
	private List<DieuTietNhom> xoaDieuTietNhoms = new ArrayList<DieuTietNhom>();
	private int soLuongKhachDieuTiet = 0;
	private long soLuongKhachConLai = 0;
	private int soLuongVePhongBanVeLe = 0;
	private int soLuongGioiHan = 0;
	private int soLuongVeDaDat = 0;
	private int soLuongKhachGioiHanDieuTiet = 0;
	private String soLuongKhachToiDaDieuTietDuocText = "";
	private int soGhe = 0;
	
	@Transient
	public List<DieuTietNhom> getXoaDieuTietNhoms() {
		return xoaDieuTietNhoms;
	}

	public void setXoaDieuTietNhoms(List<DieuTietNhom> xoaDieuTietNhoms) {
		this.xoaDieuTietNhoms = xoaDieuTietNhoms;
	}
	
	@Transient
	public List<DieuTietNhom> getDieuTietNhoms() {
		if (dieuTietNhoms.size() > 0) {
			dieuTietNhoms = dieuTietNhoms.stream().filter(t -> !t.getTrangThai().equals(core().TT_DA_XOA))
					.collect(Collectors.toList());
		}

		return dieuTietNhoms;
	}
	
	public void setDieuTietNhoms(List<DieuTietNhom> dieuTietNhoms) {
		this.dieuTietNhoms = dieuTietNhoms;
	}

	@Transient
	public long getSoLuongKhachConLai() {
		return soLuongKhachConLai;
	}

	public void setSoLuongKhachConLai(long soLuongKhachConLai) {
		this.soLuongKhachConLai = soLuongKhachConLai;
	}

	@Transient
	public int getSoLuongKhachDieuTiet() {
		return soLuongKhachDieuTiet;
	}

	public void setSoLuongKhachDieuTiet(int soLuongKhachDieuTiet) {
		this.soLuongKhachDieuTiet = soLuongKhachDieuTiet;
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
	
	public int getSoLuongVePhongBanVeLe() {
		return soLuongVePhongBanVeLe;
	}

	public void setSoLuongVePhongBanVeLe(int soLuongVePhongBanVeLe) {
		this.soLuongVePhongBanVeLe = soLuongVePhongBanVeLe;
	}
	
	@Transient
	public int getSoLuongKhachGioiHanDieuTiet() {
		return soLuongKhachGioiHanDieuTiet;
	}

	public void setSoLuongKhachGioiHanDieuTiet(int soLuongKhachGioiHanDieuTiet) {
		this.soLuongKhachGioiHanDieuTiet = soLuongKhachGioiHanDieuTiet;
	}
	
	@Transient
	public String getSoLuongKhachToiDaDieuTietDuocText() {
		String str = "";
		if (soLuongKhachGioiHanDieuTiet <= 0) {
			str = "(Nhóm đã đạt số lượng cho phép" + ")";
		} else {
			str = "(Số lượng khách tối đa có thể điều tiết được " + soLuongKhachGioiHanDieuTiet + ")";
		}
		soLuongKhachToiDaDieuTietDuocText = str;
		return soLuongKhachToiDaDieuTietDuocText;
	}

	public void setSoLuongKhachToiDaDieuTietDuocText(String soLuongKhachToiDaDieuTietDuocText) {
		this.soLuongKhachToiDaDieuTietDuocText = soLuongKhachToiDaDieuTietDuocText;
	}
	
	@Transient
	public int getSoGhe() {
		return soGhe;
	}

	public void setSoGhe(int soGhe) {
		this.soGhe = soGhe;
	}
	
	@Transient
	public NhomCuaHoi getNhomDieuTiet() {
		return nhomDieuTiet;
	}

	public void setNhomDieuTiet(NhomCuaHoi nhomDieuTiet) {
		this.nhomDieuTiet = nhomDieuTiet;
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
	private JPAQuery<DieuTietNhom> getDieuTietNhomQuery() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getNgayThucHienTour());
		JPAQuery<DieuTietNhom> dieuTietNhomQuery =  find(DieuTietNhom.class)
				.where(QDieuTietNhom.dieuTietNhom.nhomCuaHoi.eq(this))
				.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		return dieuTietNhomQuery;
	}
	
	public void napDanhSachDieuTiet() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getNgayThucHienTour());
		JPAQuery<DieuTietNhom> dieuTietNhomQuery =  find(DieuTietNhom.class)
				//.where(QDieuTietNhom.dieuTietNhom.nhomCuaHoi.eq(getNhomDieuTiet()))
				.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		dieuTietNhoms.addAll(dieuTietNhomQuery.fetch());
		BindUtils.postNotifyChange(null, null, this, "dieuTietNhoms");
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
			
			JPAQuery<DieuTietNhom> q = find(DieuTietNhom.class)
					.where(QDieuTietNhom.dieuTietNhom.trangThai.eq(core().TT_AP_DUNG))
					.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
							.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
			
			for (DieuTietNhom dt : q.fetch()) {
				total += dt.getSoLuongKhachDieuTiet();
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
	
	public void napFormMacDinh() {
		setSoLuongKhachDieuTiet(0);
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachDieuTiet");
	}
	
	@Command
	public void changeSoLuongVe() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		long nhomId = 0;
		
		if (tuNgayThucHienTour != null) {
			cal.setTime(tuNgayThucHienTour);
		} else {
			cal.setTime(date);
		}
		
		if (getNhomDieuTiet() != null) {
			nhomId = getNhomDieuTiet().getId();
			napSoLuongGioiHan(nhomId);
			napSoLuongVeDaDat(cal, nhomId);
			napSoVeDaTieuTiet(cal, nhomId);
		}
		
		truSoVe(nhomDieuTiet);
		napFormMacDinh();
		
		BindUtils.postNotifyChange(null, null, this, "soLuongGioiHan");
		BindUtils.postNotifyChange(null, null, this, "soLuongVeDaDat");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	public void truSoVe(NhomCuaHoi nhomDieuTiet) {
		int gioiHan = soLuongGioiHan;
		if(nhomDieuTiet != null) {
			int tongSoGhe = (int) nhomDieuTiet.getTongSoGhe();
			int soLuongKhachHangDaDat = getSoLuongVeDaDat();
			System.out.println("tongSoGhe " +tongSoGhe);
			System.out.println("soLuongKhachHangDaDat " +soLuongKhachHangDaDat);
			for (DieuTietNhom dt : dieuTietNhoms) {
				if(dt.getNhomCuaHoi().equals(nhomDieuTiet)) {
					System.out.println("out");
					gioiHan = tongSoGhe - dt.getSoLuongKhachDieuTiet();
					break;
				}
			} 
			gioiHan -= soLuongKhachHangDaDat;
		}
		setSoLuongKhachGioiHanDieuTiet(gioiHan);
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	public void tinhSoLuongKhachGioiHanDieuTiet() {
		int gioiHan = 0;
		int soLuongKhachGioiHanCuaCongTy = getSoLuongGioiHan();
		int soLuongKhachHangDaDat = getSoLuongVeDaDat();
		int soLuongKhachHangDieuTiet = getSoLuongKhachDieuTiet(); 
		gioiHan = soLuongKhachGioiHanCuaCongTy - soLuongKhachHangDaDat - soLuongKhachHangDieuTiet;
		setSoLuongKhachGioiHanDieuTiet(gioiHan);
	}
	
	public void napSoLuongGioiHan(long nhomId){
		JPAQuery<NhomCuaHoi> q = find(NhomCuaHoi.class)
				.where(QNhomCuaHoi.nhomCuaHoi.id.eq(nhomId));
		NhomCuaHoi nhom = q.fetchFirst();
		setSoLuongGioiHan((int)nhom.getTongSoGhe());
	}
	
	public void napSoLuongVeDaDat(Calendar cal, long nhomId) {
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.nhomCuaHoi.id.eq(nhomId))
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()))
				.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));

		int total = 0;
		datVeQuery.where(QDatVe.datVe.veDuocDuyet.isFalse());
		for (DatVe datVe : datVeQuery.fetch()) {
			total += datVe.getSoLuongNguoiLon() + datVe.getSoLuongTreEmDuoi10Tuoi() + datVe.getSoLuongTreEmDuoi5Tuoi();
		}
		setSoLuongVeDaDat(total);
	}
	
	public void napSoVeDaTieuTiet(Calendar cal, long nhomId) {
		JPAQuery<DieuTietNhom> q = find(DieuTietNhom.class)
				.where(QDieuTietNhom.dieuTietNhom.trangThai.eq(core().TT_AP_DUNG))
				//.where(QDieuTietNhom.dieuTietNhom.nhomCuaHoi.id.eq(nhomId))
				.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.dayOfMonth()
								.eq(cal.get(Calendar.DAY_OF_MONTH))));
		int total = 0;
		for (DieuTietNhom nh : q.fetch()) {
			total += nh.getSoLuongKhachDieuTiet();
		}
		for (DieuTietNhom nh : getDieuTietNhoms()) {
			if (nh.noId()) {
				total += nh.getSoLuongKhachDieuTiet();
			}
		}
		setSoLuongKhachDieuTiet(total);
	}
	
	@Transient
	public int getSoLuongDieuTietCuaNhom() {
		Calendar cal = Calendar.getInstance();
		int soLuong = 0;
		JPAQuery<DieuTietNhom> q = find(DieuTietNhom.class);
		if (getNgayThucHienTour() != null) {
			cal.setTime(ngayThucHienTour);
			q.where(QDieuTietNhom.dieuTietNhom.nhomCuaHoi.eq(this))
					.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1)
									.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.dayOfMonth()
											.eq(cal.get(Calendar.DAY_OF_MONTH)))));
			
			JPAQuery<NhomSangNhom> nhomSangNhomCong = nhomSangNhomGocQuery()
					.where(QNhomSangNhom.nhomSangNhom.nhomDieuTiet.eq(this))
					.where(QNhomSangNhom.nhomSangNhom.loaiDieuTiet.eq(core().DT_NHOM_SA_NHOM_TRU));
			
			JPAQuery<NhomSangNhom> nhomSangNhomTru = nhomSangNhomGocQuery()
					.where(QNhomSangNhom.nhomSangNhom.nhomCuaHoi.eq(this))
					.where(QNhomSangNhom.nhomSangNhom.loaiDieuTiet.eq(core().DT_NHOM_SA_NHOM_TRU));
			
			JPAQuery<VeDuyet> veDuyetQuery = find(VeDuyet.class)
					.where(QVeDuyet.veDuyet.trangThai.eq(core().TT_AP_DUNG))
					.where(QVeDuyet.veDuyet.nhomCuaHoi.eq(this))
					.where(QVeDuyet.veDuyet.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QVeDuyet.veDuyet.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
							.and(QVeDuyet.veDuyet.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
			
			int total = 0;
			JPAQuery<Tour> q2 = find(Tour.class).where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
					.where(QTour.tour.nhomCuaHoi.eq(this)).where(QTour.tour.coTau.eq(false))
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
	public String getTenVaSoLuongVeTour() {
		int total = 0;
		String str = "";
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.nhomCuaHoi.eq(this))
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()));
		
		
		if (getNgayThucHienTour() != null) {
			System.out.println("getNgayThucHienTour() " +getNgayThucHienTour());
			Calendar cal = Calendar.getInstance();
			cal.setTime(getNgayThucHienTour());
			datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		} 
				
		if (datVeQuery != null) {
			datVeQuery.where(QDatVe.datVe.veDuocDuyet.isFalse());
			List<DatVe> datVes = datVeQuery.fetch();
			for (DatVe datVe : datVes) {
				total += datVe.getSoLuongNguoiLon() + datVe.getSoLuongTreEmDuoi10Tuoi()
						+ datVe.getSoLuongTreEmDuoi5Tuoi();
			}
			if (total == 0) {
				str = " có 0 khách đặt vé";
			} else {
				str = " có " + total + " khách đã đặt";
			}
			if (getSoLuongDieuTietCuaNhom() > 0) {
				str += ", " + getSoLuongDieuTietCuaNhom() + " khách đợi điều tiết";
			}
		}
		return datVeQuery != null ? this.getTen().toUpperCase() + str : "";
	}
	
	@Command
	public void xacNhanDieuTietNhom() {
		DieuTietNhom dieuTietNhom = new DieuTietNhom();
		if (soLuongKhachDieuTiet <= soLuongVePhongBanVeLe && soLuongKhachDieuTiet <= soLuongKhachGioiHanDieuTiet) {
			int soLuongKiemTra = soLuongVePhongBanVeLe > soLuongKhachGioiHanDieuTiet
					? (soLuongVePhongBanVeLe - soLuongKhachGioiHanDieuTiet)
					: (soLuongKhachGioiHanDieuTiet - soLuongVePhongBanVeLe);
			System.out.println("soLuongKiemTra " +soLuongKiemTra);
			/*if (soLuongKiemTra == 0 || soLuongKhachDieuTiet <= soLuongKiemTra) {*/
			if (soLuongKiemTra > 0) {
				if(soLuongKhachDieuTiet > 0) {
					dieuTietNhom.setNhomCuaHoi(getNhomDieuTiet());
					dieuTietNhom.setNgayThucHienTour(getNgayThucHienTour());
					dieuTietNhom.setSoLuongKhachDieuTiet(soLuongKhachDieuTiet);
					if (getDieuTietNhoms().size() > 0) {
						for (DieuTietNhom dt : getDieuTietNhoms()) {
							if (dt.getNhomCuaHoi().equals(getNhomDieuTiet())) {
								dt.setSoLuongKhachDieuTiet(soLuongKhachDieuTiet + dt.getSoLuongKhachDieuTiet());
								dieuTietNhom = dt;
								break;
							}
						}
					}
					
					if (getDieuTietNhoms().contains(dieuTietNhom)) {
						getDieuTietNhoms().remove(dieuTietNhom);
					}
					getDieuTietNhoms().add(dieuTietNhom);
					truSoVe(nhomDieuTiet);
					soLuongVePhongBanVeLe -= soLuongKhachDieuTiet;
					//soLuongKhachGioiHanDieuTiet -= soLuongKhachDieuTiet;
				}
			}
		}
		napFormMacDinh();
		BindUtils.postNotifyChange(null, null, this, "dieuTietNhoms");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongVePhongBanVeLe");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	@Command
	public void saveDieuTietNhom(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		for (DieuTietNhom dieuTietNhom : dieuTietNhoms) {
			dieuTietNhom.save();
		}
		for (DieuTietNhom xoaDieuTietNhom : xoaDieuTietNhoms) {
			xoaDieuTietNhom.save();
		}
		
		wdn.detach();
		BindUtils.postNotifyChange(null, null, this, "dieuTietNhoms");
		BindUtils.postNotifyChange(null, null, listObject, "nhomCuaHois");
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	@Command
	public void xoaDieuTietNhom(DieuTietNhom dieuTietNhom) {
		if(nhomDieuTiet != null) {
			if(dieuTietNhom.getNhomCuaHoi().equals(nhomDieuTiet)) {
				soLuongKhachGioiHanDieuTiet += dieuTietNhom.getSoLuongKhachDieuTiet();
			}
		}
		soLuongVePhongBanVeLe += dieuTietNhom.getSoLuongKhachDieuTiet();
		if (!dieuTietNhom.noId()) {
			for (DieuTietNhom dt : getDieuTietNhoms()) {
				if (dt.equals(dieuTietNhom)) {
					dieuTietNhom.setDaXoa(true);
					dieuTietNhom.setTrangThai(core().TT_DA_XOA);
					xoaDieuTietNhoms.add(dieuTietNhom);
					break;
				}
			}
		}
		getDieuTietNhoms().remove(dieuTietNhom);
		
		BindUtils.postNotifyChange(null, null, this, "dieuTietNhoms");
		BindUtils.postNotifyChange(null, null, this, "xoaDieuTietNhoms");
		BindUtils.postNotifyChange(null, null, this, "soLuongVePhongBanVeLe");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	@Command
	public void layDanhSachNhomDieuTiet() {
		//JPAQuery<DieuTietNhom> dieuTietNhomQuery = getDieuTietNhomQuery();
		BindUtils.postNotifyChange(null, null, this, "dieuTietNhoms");
	}
	
	public void laySoLuongKhachConLai() {
		JPAQuery<DieuTietNhom> dieuTietNhomQuery = getDieuTietNhomQuery();
		int soLuong = 0;
		if (dieuTietNhomQuery.fetchCount() > 0) {
			for (DieuTietNhom dieuTietNhom : dieuTietNhomQuery.fetch()) {
				soLuong += dieuTietNhom.getSoLuongKhachDieuTiet();
			}
		}
		soLuongKhachConLai = getTongSoGhe() - soLuong;
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachConLai");
	}
	
	public void formDieuTietNhomDefault() {
		nhomDieuTiet = new NhomCuaHoi();
		soLuongKhachDieuTiet = 0;
		BindUtils.postNotifyChange(null, null, this, "nhomDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachDieuTiet");
	}
	
	@Transient
	public AbstractValidator getValidatorDieuTietNhom() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				/*if(soLuongKhachDieuTiet > getSoLuongKhachConLai()) {
					addInvalidMessage(ctx, "lblErrSoLuongKhach", "Số lượng phải nhỏ hơn hoặc bằng với số lượng khách điều tiết còn lại.");
				}*/
			}
		};
	}
	
	/*-------------------------------------------------- NHOM SANG NHOM ---------------------------------------------- */
	private List<NhomSangNhom> nhomSangNhoms = new ArrayList<NhomSangNhom>();
	private List<NhomSangNhom> xoaNhomSangNhoms = new ArrayList<NhomSangNhom>();
	private int soLuongDieuTietTuNhom = 0;
	private boolean duocDieuTietNhomSangNhom = false;
	
	@Transient
	public boolean isDuocDieuTietNhomSangNhom() {
		if(getNhanVien().getNhomCuaHoi() != null) {
			if(getNhanVien().getNhomCuaHoi().equals(this)) {
				duocDieuTietNhomSangNhom = true;
			}
		}
		return duocDieuTietNhomSangNhom;
	}

	public void setDuocDieuTietNhomSangNhom(boolean duocDieuTietNhomSangNhom) {
		this.duocDieuTietNhomSangNhom = duocDieuTietNhomSangNhom;
	}

	@Transient
	public List<NhomSangNhom> getXoaNhomSangNhoms() {
		return xoaNhomSangNhoms;
	}

	public void setXoaNhomSangNhoms(List<NhomSangNhom> xoaNhomSangNhoms) {
		this.xoaNhomSangNhoms = xoaNhomSangNhoms;
	}

	@Transient
	public List<NhomSangNhom> getNhomSangNhoms() {
		return nhomSangNhoms;
	}

	public void setNhomSangNhoms(List<NhomSangNhom> nhomSangNhoms) {
		this.nhomSangNhoms = nhomSangNhoms;
	}
	
	@Transient
	public int getSoLuongDieuTietTuNhom() {
		return soLuongDieuTietTuNhom;
	}

	public void setSoLuongDieuTietTuNhom(int soLuongDieuTietTuNhom) {
		this.soLuongDieuTietTuNhom = soLuongDieuTietTuNhom;
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
				.where(QDieuTietNhom.dieuTietNhom.nhomCuaHoi.eq(this))
				.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.dayOfMonth()
								.eq(cal.get(Calendar.DAY_OF_MONTH))));

		return q;
	}
	
	@Transient
	private JPAQuery<NhomSangNhom> nhomSangNhomQuery() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();

		if (tuNgayThucHienTour != null) {
			cal.setTime(tuNgayThucHienTour);
		} else {
			cal.setTime(date);
		}
		JPAQuery<NhomSangNhom> q = find(NhomSangNhom.class)
				.where(QNhomSangNhom.nhomSangNhom.trangThai.eq(core().TT_AP_DUNG))
				.where(QNhomSangNhom.nhomSangNhom.nhomCuaHoi.eq(this))
				.where(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.dayOfMonth()
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
	
	@Transient
	public List<NhomCuaHoi> getNhomCuaHoiList() {
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
		nhomCuaHoiList.addAll(find(NhomCuaHoi.class)
				.where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG))
				.where(QNhomCuaHoi.nhomCuaHoi.ne(this))
				.fetch());
		return nhomCuaHoiList;
	}
	
	@Command
	public void changeNhom() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		long nhomId = 0;
		
		if (tuNgayThucHienTour != null) {
			cal.setTime(tuNgayThucHienTour);
		} else {
			cal.setTime(date);
		}
		
		if (getNhomDieuTiet() != null) {
			nhomId = getNhomDieuTiet().getId();
			napSoLuongGioiHan(nhomId);
			napSoLuongVeDaDat(cal, nhomId);
			napSoVeDaTieuTiet(cal, nhomId);
		}
		
		truSoKhachDieuTietSangNhom(nhomDieuTiet);
		napFormMacDinh();
		
		BindUtils.postNotifyChange(null, null, this, "soLuongGioiHan");
		BindUtils.postNotifyChange(null, null, this, "soLuongVeDaDat");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	public void napSoLuongDieuTietTuNhom() {
		Calendar cal = Calendar.getInstance();
		
		JPAQuery<DieuTietNhom> q = dieuTietNhomQuery();
		JPAQuery<NhomSangNhom> nhomSangNhomCong = nhomSangNhomGocQuery()
				.where(QNhomSangNhom.nhomSangNhom.nhomDieuTiet.eq(this))
				.where(QNhomSangNhom.nhomSangNhom.loaiDieuTiet.eq(core().DT_NHOM_SA_NHOM_TRU));
		
		JPAQuery<NhomSangNhom> nhomSangNhomTru = nhomSangNhomGocQuery()
				.where(QNhomSangNhom.nhomSangNhom.nhomCuaHoi.eq(this))
				.where(QNhomSangNhom.nhomSangNhom.loaiDieuTiet.eq(core().DT_NHOM_SA_NHOM_TRU));
		
		int total = 0;
		int soLuong = 0;
		if (getNgayThucHienTour() != null) {
			cal.setTime(ngayThucHienTour);
			JPAQuery<Tour> q2 = find(Tour.class).where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
					.where(QTour.tour.nhomCuaHoi.eq(this)).where(QTour.tour.coTau.eq(false))
					.where(QTour.tour.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QTour.tour.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
							.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
			
			JPAQuery<VeDuyet> veDuyetQuery = find(VeDuyet.class)
					.where(QVeDuyet.veDuyet.trangThai.eq(core().TT_AP_DUNG))
					.where(QVeDuyet.veDuyet.nhomCuaHoi.eq(this))
					.where(QVeDuyet.veDuyet.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QVeDuyet.veDuyet.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
							.and(QVeDuyet.veDuyet.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
			
			for (Tour tour : q2.fetch()) {
				total += tour.getSoLuongKhachDieuTiet();
			}
			for (VeDuyet veDuyet : veDuyetQuery.fetch()) {
				soLuong += veDuyet.getSoLuongVeDuyet();
			}
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
		
		soLuong -= total;
		setSoLuongDieuTietTuNhom(soLuong);
		BindUtils.postNotifyChange(null, null, this, "soLuongDieuTietTuNhom");
	}
	
	public void napDanhSachDieuTietNhom() {
		JPAQuery<NhomSangNhom> q = nhomSangNhomQuery();
		nhomSangNhoms.clear();
		nhomSangNhoms.addAll(q.fetch());
		BindUtils.postNotifyChange(null, null, this, "nhomSangNhoms");
	}
	
	@Command
	public void xacNhanNhomSangNhom() {
		NhomSangNhom nhomSangNhom = new NhomSangNhom();
		int soLuongKiemTra = soLuongDieuTietTuNhom > soLuongKhachGioiHanDieuTiet
				? (soLuongDieuTietTuNhom - soLuongKhachGioiHanDieuTiet)
				: (soLuongKhachGioiHanDieuTiet - soLuongDieuTietTuNhom);
		if (soLuongKhachDieuTiet > 0 && (soLuongKhachGioiHanDieuTiet > 0 && soLuongDieuTietTuNhom > 0)
				&& soLuongKiemTra >= 0) {
			nhomSangNhom.setNhomCuaHoi(this);
			nhomSangNhom.setNhomDieuTiet(nhomDieuTiet);
			nhomSangNhom.setNgayThucHienTour(getNgayThucHienTour());
			nhomSangNhom.setLoaiDieuTiet(core().DT_NHOM_SA_NHOM_TRU);
			nhomSangNhom.setSoLuongKhachDieuTietCong(soLuongKhachDieuTiet);
			if (getNhomSangNhoms().size() > 0) {
				for (NhomSangNhom nhom : nhomSangNhoms) {
					if (nhom.getNhomDieuTiet().equals(nhomDieuTiet)) {
						nhom.setSoLuongKhachDieuTietCong(soLuongKhachDieuTiet + nhom.getSoLuongKhachDieuTietCong());
						nhomSangNhom = nhom;
						break;
					}
				}
			}

			if (nhomSangNhoms.contains(nhomSangNhom)) {
				nhomSangNhoms.remove(nhomSangNhom);
			}
			nhomSangNhoms.add(nhomSangNhom);

			truSoKhachDieuTietSangNhom(nhomDieuTiet);
			soLuongDieuTietTuNhom -= soLuongKhachDieuTiet;
		}

		napFormMacDinh();
		BindUtils.postNotifyChange(null, null, this, "nhomSangNhoms");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongDieuTietTuNhom");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	@Command
	public void saveDieuTietNhomSangNhom(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr, @BindingParam("wdn") final Window wdn) {
		for (NhomSangNhom nhomSangNhom : nhomSangNhoms) {
			nhomSangNhom.save();
		}
		for (NhomSangNhom xoaNhomSangNhom : xoaNhomSangNhoms) {
			xoaNhomSangNhom.save();
		}

		wdn.detach();
		BindUtils.postNotifyChange(null, null, this, "dieuTietNhoms");
		BindUtils.postNotifyChange(null, null, listObject, "nhomCuaHois");
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	@Command
	public void xoaDieuTietNhomSangNhom(NhomSangNhom nhomSangNhom) {
		if (nhomDieuTiet != null) {
			if (nhomSangNhom.getNhomDieuTiet().equals(nhomDieuTiet)) {
				soLuongKhachGioiHanDieuTiet += nhomSangNhom.getSoLuongKhachDieuTietCong();
			}
		}
		soLuongDieuTietTuNhom += nhomSangNhom.getSoLuongKhachDieuTietCong();
		if (!nhomSangNhom.noId()) {
			for (NhomSangNhom nhom : getNhomSangNhoms()) {
				if (nhom.equals(nhomSangNhom)) {
					nhomSangNhom.setDaXoa(true);
					nhomSangNhom.setTrangThai(core().TT_DA_XOA);
					xoaNhomSangNhoms.add(nhomSangNhom);
					break;
				}
			}
		}
		getNhomSangNhoms().remove(nhomSangNhom);

		BindUtils.postNotifyChange(null, null, this, "nhomSangNhoms");
		BindUtils.postNotifyChange(null, null, this, "xoaNhomSangNhoms");
		BindUtils.postNotifyChange(null, null, this, "soLuongDieuTietTuNhom");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
	
	public void truSoKhachDieuTietSangNhom(NhomCuaHoi nhom) {
		int gioiHan = soLuongGioiHan;
		if (nhom != null) {
			int tongSoGhe = (int) nhom.getTongSoGhe();
			int soLuongKhachHangDaDat = getSoLuongVeDaDat();
			for (NhomSangNhom nhomSangNhom : nhomSangNhoms) {
				if (nhomSangNhom.getNhomDieuTiet().equals(nhom)) {
					gioiHan = tongSoGhe - nhomSangNhom.getSoLuongKhachDieuTietCong();
					break;
				}
			}
			gioiHan -= soLuongKhachHangDaDat;
		}
		setSoLuongKhachGioiHanDieuTiet(gioiHan);
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachGioiHanDieuTiet");
		BindUtils.postNotifyChange(null, null, this, "soLuongKhachToiDaDieuTietDuocText");
	}
}
