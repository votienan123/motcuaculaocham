package vn.toancauxanh.cms.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.validator.AbstractValidator;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CongTyKinhDoanh;
import vn.toancauxanh.gg.model.CongTySangCongTy;
import vn.toancauxanh.gg.model.DatVe;
import vn.toancauxanh.gg.model.DieuTietNhom;
import vn.toancauxanh.gg.model.NhomCuaHoi;
import vn.toancauxanh.gg.model.NhomSangNhom;
import vn.toancauxanh.gg.model.QCongTyKinhDoanh;
import vn.toancauxanh.gg.model.QCongTySangCongTy;
import vn.toancauxanh.gg.model.QDatVe;
import vn.toancauxanh.gg.model.QDieuTietNhom;
import vn.toancauxanh.gg.model.QNhomCuaHoi;
import vn.toancauxanh.gg.model.QNhomSangNhom;
import vn.toancauxanh.gg.model.QTour;
import vn.toancauxanh.gg.model.Tour;
import vn.toancauxanh.gg.model.enums.TinhTrangVeEnum;
import vn.toancauxanh.gg.model.enums.TrangThaiDuyetVeEnum;
import vn.toancauxanh.service.ExcelUtil;

public class ThongKeCLCKhachThucHienTourService extends ThongKeCLCService {

	private List<Map<String, Object>> listResult = new ArrayList<Map<String, Object>>();
	private String titleDate = "";
		
	public List<Map<String, Object>> getListResult() {
		return listResult;
	}

	public void setListResult(List<Map<String, Object>> listResult) {
		this.listResult = listResult;
	}
	
	public String getTitleDate() {
		return titleDate;
	}

	public void setTitleDate(String titleDate) {
		this.titleDate = titleDate;
	}

	@Init
	public void init() {
		setNgayThucHienTour(new Date());
		BindUtils.postNotifyChange(null, null, this, "ngayThucHienTour");
	}

	public long getTongSoKhachThucHienThanhCong() {

		long total = 0;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getNgayThucHienTour());

		JPAQuery<Tour> tourList = find(Tour.class).where(QTour.tour.trangThai.eq(core().TT_AP_DUNG));
		if (getDenNgayThucHienTour() != null) {
			 tourList.where(QTour.tour.ngayThucHienTour.between(getFixNgayThucHienTour(), getFixDenNgayThucHienTour()));
		} else {
			tourList.where(QTour.tour.ngayThucHienTour.year().eq(calendar.get(Calendar.YEAR))
					.and(QTour.tour.ngayThucHienTour.month().eq(calendar.get(Calendar.MONTH) + 1))
					.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(calendar.get(Calendar.DAY_OF_MONTH))));
		}
		

		if (tourList != null) {
			for (Tour i : tourList.fetch()) {
				total += i.getSoLuongNguoiLon() + i.getSoLuongTreEm1Den3() + i.getSoLuongTrEm4Den9();
			}
		}

		return total;
	}
	
	private JPAQuery<NhomSangNhom> nhomSangNhomQuery() {
		JPAQuery<NhomSangNhom> q = find(NhomSangNhom.class)
				.where(QNhomSangNhom.nhomSangNhom.trangThai.eq(core().TT_AP_DUNG));

		return q;
	}
	
	@SuppressWarnings("deprecation")
	public List<Map<String, Object>> getNhomCuaHoiThucHienTour() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if (getDenNgayThucHienTour() != null) {
			if (getDenNgayThucHienTour().getYear() == getNgayThucHienTour().getYear() 
					&& getDenNgayThucHienTour().getMonth() == getNgayThucHienTour().getMonth()
					&& getDenNgayThucHienTour().getDay() == getNgayThucHienTour().getDay()) {
				titleDate = "trong ngày " + df.format(getNgayThucHienTour());
			} else {
				titleDate = "từ " + df.format(getNgayThucHienTour()) + " đến " + df.format(getDenNgayThucHienTour());
			}
		} else {
			titleDate = "trong ngày " + df.format(getNgayThucHienTour());
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getNgayThucHienTour());

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		JPAQuery<NhomCuaHoi> nhomCuaHoiList = find(NhomCuaHoi.class)
				.where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG));

		if (nhomCuaHoiList != null) {
			for (NhomCuaHoi i : nhomCuaHoiList.fetch()) {

				Map<String, Object> mapChild = new HashMap<String, Object>();

				JPAQuery<DatVe> veCuaHois = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
						.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
								.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
						.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
						.where(QDatVe.datVe.nhomCuaHoi.eq(i));
				if (getDenNgayThucHienTour() != null) {
					veCuaHois.where(QDatVe.datVe.ngayThucHienTour.between(getFixNgayThucHienTour(), getFixDenNgayThucHienTour()));
				} else {
					veCuaHois.where(QDatVe.datVe.ngayThucHienTour.year().eq(calendar.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calendar.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
									.eq(calendar.get(Calendar.DAY_OF_MONTH))));
				}
				

				long soLuongKhachDat = 0;
				if (veCuaHois != null) {
					for (DatVe datVe : veCuaHois.fetch()) {
						soLuongKhachDat += Long.valueOf(datVe.getSoLuongNguoiLon())
								+ Long.valueOf(datVe.getSoLuongTreEmDuoi10Tuoi())
								+ Long.valueOf(datVe.getSoLuongTreEmDuoi5Tuoi());
					}
				}

				// Add -->
				JPAQuery<DieuTietNhom> dieuTietNhoms = find(DieuTietNhom.class)
						.where(QDieuTietNhom.dieuTietNhom.trangThai.eq(core().TT_AP_DUNG))
						.where(QDieuTietNhom.dieuTietNhom.nhomCuaHoi.eq(i));
				if (getDenNgayThucHienTour() != null) {
					dieuTietNhoms.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.between(getFixNgayThucHienTour(), getFixDenNgayThucHienTour()));
				} else {
					dieuTietNhoms.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.year().eq(calendar.get(Calendar.YEAR))
							.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.month()
									.eq(calendar.get(Calendar.MONTH) + 1))
							.and(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.dayOfMonth()
									.eq(calendar.get(Calendar.DAY_OF_MONTH))));
				}
				

				long soLuongDieuTietNhom = 0;
				if (dieuTietNhoms != null) {
					for (DieuTietNhom dieuTietNhom : dieuTietNhoms.fetch()) {
						soLuongDieuTietNhom += Long.valueOf(dieuTietNhom.getSoLuongKhachDieuTiet());
					}
				}

				JPAQuery<Tour> tours = find(Tour.class).where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
						.where(QTour.tour.nhomCuaHoi.eq(i));
				if (getDenNgayThucHienTour() != null) {
					tours.where(QTour.tour.ngayThucHienTour.between(getFixNgayThucHienTour(), getFixDenNgayThucHienTour()));
				} else {
					tours.where(QTour.tour.ngayThucHienTour.year().eq(calendar.get(Calendar.YEAR))
							.and(QTour.tour.ngayThucHienTour.month().eq(calendar.get(Calendar.MONTH) + 1))
							.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(calendar.get(Calendar.DAY_OF_MONTH))));
				}
				long soLuongKhachThucHienTourThanhCong = 0;
				if (tours != null) {
					for (Tour tour : tours.fetch()) {
						soLuongKhachThucHienTourThanhCong += Long.valueOf(tour.getSoLuongNguoiLon())
								+ Long.valueOf(tour.getSoLuongTrEm4Den9()) + Long.valueOf(tour.getSoLuongTreEm1Den3());
					}
				}

				JPAQuery<NhomSangNhom> nhomSangNhomCong = nhomSangNhomQuery();
				JPAQuery<NhomSangNhom> nhomSangNhomTru = nhomSangNhomQuery();
				if (getDenNgayThucHienTour() != null) {
					nhomSangNhomCong.where(QNhomSangNhom.nhomSangNhom.nhomDieuTiet.eq(i))
							.where(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.between(getFixNgayThucHienTour(),
									getFixDenNgayThucHienTour()));

					nhomSangNhomTru.where(QNhomSangNhom.nhomSangNhom.nhomCuaHoi.eq(i))
							.where(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.between(getFixNgayThucHienTour(),
									getFixDenNgayThucHienTour()));
				} else {
					Calendar cal = Calendar.getInstance();
					cal.setTime(getNgayThucHienTour());
					nhomSangNhomCong.where(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
							.and(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.dayOfMonth()
									.eq(cal.get(Calendar.DAY_OF_MONTH))));
					nhomSangNhomTru.where(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
							.and(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
							.and(QNhomSangNhom.nhomSangNhom.ngayThucHienTour.dayOfMonth()
									.eq(cal.get(Calendar.DAY_OF_MONTH))));
				}
				long soLuongDieuTietTuNhom = 0;
				String soLuongDieuTietTuNhomText = "0";
				for (NhomSangNhom nhomCong : nhomSangNhomCong.fetch()) {
					soLuongDieuTietTuNhom += nhomCong.getSoLuongKhachDieuTietCong();
				}
				for (NhomSangNhom nhomTru : nhomSangNhomTru.fetch()) {
					soLuongDieuTietTuNhom -= nhomTru.getSoLuongKhachDieuTietCong();
				}
				soLuongDieuTietTuNhomText = "" + soLuongDieuTietTuNhom;
				if (soLuongDieuTietTuNhom > 0) {
					soLuongDieuTietTuNhomText = "+ " + soLuongDieuTietTuNhom;
				}
				
				mapChild.put("nhom", i);
				mapChild.put("soLuongKhach", soLuongKhachDat);
				mapChild.put("dieuTiet", soLuongDieuTietNhom);
				mapChild.put("dieuTietTuNhom", soLuongDieuTietTuNhom);
				mapChild.put("soLuongDieuTietTuNhomText", soLuongDieuTietTuNhomText);
				mapChild.put("khachThucHienTourThanhCong", soLuongKhachThucHienTourThanhCong);
				Collections.addAll(mapList, mapChild);
			}
		}

		return mapList;
	}
	
	private JPAQuery<CongTySangCongTy> congTySangCongTyQuery() {
		JPAQuery<CongTySangCongTy> q = find(CongTySangCongTy.class)
				.where(QCongTySangCongTy.congTySangCongTy.trangThai.eq(core().TT_AP_DUNG));

		return q;
	}
	
	public List<Map<String, Object>> getCongTyKinhDoanhCuaNhomThongKe(NhomCuaHoi nhomCuaHoi) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getNgayThucHienTour());

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		JPAQuery<CongTyKinhDoanh> congTyKinhDoanhList = find(CongTyKinhDoanh.class)
				.where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
				.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(nhomCuaHoi));

		if (congTyKinhDoanhList != null) {
			for (CongTyKinhDoanh i : congTyKinhDoanhList.fetch()) {

				Map<String, Object> mapChild = new HashMap<String, Object>();

				JPAQuery<DatVe> veCuaCongTy = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
						.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
								.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
						.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
						.where(QDatVe.datVe.nhomCuaHoi.eq(nhomCuaHoi).and(QDatVe.datVe.congTyKinhDoanh.eq(i)));
				if (getDenNgayThucHienTour() != null) {
					veCuaCongTy.where(QDatVe.datVe.ngayThucHienTour.between(getFixNgayThucHienTour(), getFixDenNgayThucHienTour()));
				} else {
					veCuaCongTy.where(QDatVe.datVe.ngayThucHienTour.year().eq(calendar.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calendar.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
									.eq(calendar.get(Calendar.DAY_OF_MONTH))));
				}
				

				long soLuongKhachDatCongTy = 0;
				if (veCuaCongTy != null) {
					for (DatVe datVe : veCuaCongTy.fetch()) {
						soLuongKhachDatCongTy += Long.valueOf(datVe.getSoLuongNguoiLon())
								+ Long.valueOf(datVe.getSoLuongTreEmDuoi10Tuoi())
								+ Long.valueOf(datVe.getSoLuongTreEmDuoi5Tuoi());
					}
				}

				JPAQuery<Tour> dieuTietTourCongTy = find(Tour.class).where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
						.where(QTour.tour.nhomCuaHoi.eq(nhomCuaHoi).and(QTour.tour.congTyKinhDoanh.eq(i)));
				
				if (getDenNgayThucHienTour() != null) {
					dieuTietTourCongTy.where(QTour.tour.ngayThucHienTour.between(getFixNgayThucHienTour(), getFixDenNgayThucHienTour()));
				} else {
					dieuTietTourCongTy.where(QTour.tour.ngayThucHienTour.year().eq(calendar.get(Calendar.YEAR))
							.and(QTour.tour.ngayThucHienTour.month().eq(calendar.get(Calendar.MONTH) + 1))
							.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(calendar.get(Calendar.DAY_OF_MONTH))));
				}
				
				long soLuongDieuTietCongTy = 0;
				long soLuongKhachCongTyThucHienTourThanhCong = 0;
				
				if (dieuTietTourCongTy != null) {
					for (Tour tour : dieuTietTourCongTy.fetch()) {
						soLuongDieuTietCongTy += Long.valueOf(tour.getSoLuongKhachDieuTiet());
						soLuongKhachCongTyThucHienTourThanhCong += Long.valueOf(tour.getSoLuongNguoiLon())
								+ Long.valueOf(tour.getSoLuongTreEm1Den3())
								+ Long.valueOf(tour.getSoLuongTrEm4Den9());
					}
				}
				
				JPAQuery<CongTySangCongTy> congTySangCongTyCong = congTySangCongTyQuery()
						.where(QCongTySangCongTy.congTySangCongTy.congTyDieuTiet.eq(i));
				JPAQuery<CongTySangCongTy> congTySangCongTyTru = congTySangCongTyQuery()
						.where(QCongTySangCongTy.congTySangCongTy.congTyKinhDoanh.eq(i));
				long dieuDietTuCongTySangCongTy = 0;
				String dieuDietTuCongTySangCongTyText = "0";
				if (getDenNgayThucHienTour() != null) {
					congTySangCongTyCong.where(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour
							.between(getFixNgayThucHienTour(), getFixDenNgayThucHienTour()));
					congTySangCongTyTru.where(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour
							.between(getFixNgayThucHienTour(), getFixDenNgayThucHienTour()));
				} else {
					congTySangCongTyCong.where(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour.year().eq(calendar.get(Calendar.YEAR))
							.and(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour.month().eq(calendar.get(Calendar.MONTH) + 1))
							.and(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour.dayOfMonth()
									.eq(calendar.get(Calendar.DAY_OF_MONTH))));
					congTySangCongTyTru.where(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour.year().eq(calendar.get(Calendar.YEAR))
							.and(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour.month().eq(calendar.get(Calendar.MONTH) + 1))
							.and(QCongTySangCongTy.congTySangCongTy.ngayThucHienTour.dayOfMonth()
									.eq(calendar.get(Calendar.DAY_OF_MONTH))));
				}
				for (CongTySangCongTy congTyCong : congTySangCongTyCong.fetch()) {
					dieuDietTuCongTySangCongTy += congTyCong.getSoLuongKhachDieuTiet();
				}
				for (CongTySangCongTy congTyTru : congTySangCongTyTru.fetch()) {
					dieuDietTuCongTySangCongTy -= congTyTru.getSoLuongKhachDieuTiet();
				}
				dieuDietTuCongTySangCongTyText = "" + dieuDietTuCongTySangCongTy;
				if (dieuDietTuCongTySangCongTy > 0) {
					dieuDietTuCongTySangCongTyText = "+ " + dieuDietTuCongTySangCongTy;
				}
				
				mapChild.put("congty", i);
				mapChild.put("soLuongKhach", soLuongKhachDatCongTy);
				mapChild.put("dieuTiet", soLuongDieuTietCongTy);
				mapChild.put("dieuDietTuCongTySangCongTy", dieuDietTuCongTySangCongTy);
				mapChild.put("dieuDietTuCongTySangCongTyText", dieuDietTuCongTySangCongTyText);
				mapChild.put("khachThucHienTourThanhCong", soLuongKhachCongTyThucHienTourThanhCong);
				Collections.addAll(mapList, mapChild);
				listResult.add(mapChild);
			}
		}
		return mapList;
	}
	
	public Date getFixNgayThucHienTour() {
		Calendar tuNgayCal = Calendar.getInstance();
		tuNgayCal.setTime(getNgayThucHienTour());
		tuNgayCal.set(Calendar.HOUR_OF_DAY, 0);
		tuNgayCal.set(Calendar.MINUTE, 0);
		tuNgayCal.set(Calendar.SECOND, 0);
		tuNgayCal.set(Calendar.MILLISECOND, 0);
		return tuNgayCal.getTime();
	}
	
	public Date getFixDenNgayThucHienTour() {
		Calendar denNgayCal = Calendar.getInstance();
		denNgayCal.setTime(getDenNgayThucHienTour());
		denNgayCal.set(Calendar.HOUR_OF_DAY, 23);
		denNgayCal.set(Calendar.MINUTE, 59);
		denNgayCal.set(Calendar.SECOND, 59);
		denNgayCal.set(Calendar.MILLISECOND, 0);
		return denNgayCal.getTime();
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {				
				if (getDenNgayThucHienTour() != null && getFixDenNgayThucHienTour().before(getFixNgayThucHienTour())) {
					addInvalidMessage(ctx, "lblErrDenNgayTK","Đến ngày thực hiện tour không được lớn hơn từ ngày thực hiện tour.");
				}  
			}
		};
	}
	
	@Command
	public void thongKe() {
		listResult.clear();		
		BindUtils.postNotifyChange(null, null, this, "tongSoKhachThucHienThanhCong");
		BindUtils.postNotifyChange(null, null, this, "nhomCuaHoiThucHienTour");
		BindUtils.postNotifyChange(null, null, this, "getCongTyKinhDoanhCuaNhomThongKe");
	}
	
	@Command 
	public void xuatExcel() throws IOException {
		ExcelUtil.exportThongKe("Thống kê khách thực hiện tour " + titleDate, "thongKeKhachThucHienTour", "thucHienTour", listResult);
	}

}
