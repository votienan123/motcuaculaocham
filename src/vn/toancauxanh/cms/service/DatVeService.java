package vn.toancauxanh.cms.service;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.annotation.DependsOn;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CongTyKinhDoanh;
import vn.toancauxanh.gg.model.DatVe;
import vn.toancauxanh.gg.model.GiaVeDiTauRieng;
import vn.toancauxanh.gg.model.GiaVeDiTourRieng;
import vn.toancauxanh.gg.model.NhomCuaHoi;
import vn.toancauxanh.gg.model.PhanLoaiKhachThueTau;
import vn.toancauxanh.gg.model.PhanLoaiTour;
import vn.toancauxanh.gg.model.QCongTyKinhDoanh;
import vn.toancauxanh.gg.model.QDatVe;
import vn.toancauxanh.gg.model.QDieuTietNhom;
import vn.toancauxanh.gg.model.QGiaVeDiTauRieng;
import vn.toancauxanh.gg.model.QGiaVeDiTourRieng;
import vn.toancauxanh.gg.model.QNhomCuaHoi;
import vn.toancauxanh.gg.model.QPhanLoaiKhachThueTau;
import vn.toancauxanh.gg.model.QPhanLoaiTour;
import vn.toancauxanh.gg.model.QVeDuyet;
import vn.toancauxanh.gg.model.VeDuyet;
import vn.toancauxanh.gg.model.enums.LoaiPhongVeLeEnum;
import vn.toancauxanh.gg.model.enums.PhanLoaiKhachDiTour;
import vn.toancauxanh.gg.model.enums.QuocTichEnum;
import vn.toancauxanh.gg.model.enums.TinhTrangVeEnum;
import vn.toancauxanh.gg.model.enums.TrangThaiDuyetVeEnum;
import vn.toancauxanh.model.NhanVien;
import vn.toancauxanh.model.QNhanVien;
import vn.toancauxanh.service.BasicService;
import vn.toancauxanh.service.ExcelUtil;

public class DatVeService extends BasicService<DatVe> {

	private List<CongTyKinhDoanh> listResult = new ArrayList<CongTyKinhDoanh>();
	private String titleDate = "";
	

	public String getTitleDate() {
		return titleDate;
	}

	public void setTitleDate(String titleDate) {
		this.titleDate = titleDate;
	}

	@Init
	public void init() {
		setTuNgay(new Date());
		BindUtils.postNotifyChange(null, null, this, "tuNgay");
	}

	private CongTyKinhDoanh congTyKinhDoanh;
	private List<NhomCuaHoi> nhomCuaHoiNotifyChangeList;
	private List<CongTyKinhDoanh> congTyKinhDoanhNotifyChangeList;
	private String soLuongVePhongBanVeLe = "";
	private boolean selectedNgayThucHien = true;

	public boolean isSelectedNgayThucHien() {
		return selectedNgayThucHien;
	}

	public void setSelectedNgayThucHien(boolean selectedNgayThucHien) {
		this.selectedNgayThucHien = selectedNgayThucHien;
	}

	public String getSoLuongVePhongBanVeLe() {
		return soLuongVePhongBanVeLe;
	}

	public void setSoLuongVePhongBanVeLe(String soLuongVePhongBanVeLe) {
		this.soLuongVePhongBanVeLe = soLuongVePhongBanVeLe;
	}

	public List<NhomCuaHoi> getNhomCuaHoiNotifyChangeList() {
		return nhomCuaHoiNotifyChangeList;
	}

	public void setNhomCuaHoiNotifyChangeList(List<NhomCuaHoi> nhomCuaHoiNotifyChangeList) {
		this.nhomCuaHoiNotifyChangeList = nhomCuaHoiNotifyChangeList;
	}

	public List<CongTyKinhDoanh> getCongTyKinhDoanhNotifyChangeList() {
		return congTyKinhDoanhNotifyChangeList;
	}

	public void setCongTyKinhDoanhNotifyChangeList(List<CongTyKinhDoanh> congTyKinhDoanhNotifyChangeList) {
		this.congTyKinhDoanhNotifyChangeList = congTyKinhDoanhNotifyChangeList;
	}

	public CongTyKinhDoanh getCongTyKinhDoanh() {
		if (congTyKinhDoanh == null) {
			return getCongTyKinhDoanhFirst();
		}
		return congTyKinhDoanh;
	}

	public void setCongTyKinhDoanh(CongTyKinhDoanh congTyKinhDoanh) {
		this.congTyKinhDoanh = congTyKinhDoanh;
	}

	public JPAQuery<DatVe> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");

		JPAQuery<DatVe> datVeQuery = find(DatVe.class)
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

		if (getCongTyKinhDoanh() != null && !getCongTyKinhDoanh().noId()) {
			datVeQuery.where(QDatVe.datVe.congTyKinhDoanh.eq(getCongTyKinhDoanh()));
		}

		if (getTuNgay() != null && getDenNgay() != null) {
			Calendar calNgayKhachDat = Calendar.getInstance();
			calNgayKhachDat.setTime(getFixTuNgay());
			Calendar calNgayThucHien = Calendar.getInstance();
			calNgayThucHien.setTime(getFixDenNgay());

			datVeQuery
					.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))))
					.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayThucHien.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHien.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
									.eq(calNgayThucHien.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgay() == null && getDenNgay() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFixDenNgay());
			datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgay() != null && getDenNgay() == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFixTuNgay());
			datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayKhachDat.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}

		// if (getTuNgay() != null && getDenNgay() != null) {
		// datVeQuery.where(QDatVe.datVe.ngayThucHienTour.between(getFixTuNgay(),
		// getFixDenNgay()));
		// } else if (getTuNgay() == null && getDenNgay() != null) {
		// datVeQuery.where(QDatVe.datVe.ngayThucHienTour.loe(getFixDenNgay()));
		// } else if (getTuNgay() != null && getDenNgay() == null) {
		// datVeQuery.where(QDatVe.datVe.ngayThucHienTour.goe(getFixTuNgay()));
		// }

		datVeQuery.orderBy(QDatVe.datVe.ngaySua.desc());

		return datVeQuery;
	}

	public List<PhanLoaiTour> getPhanLoaiTourListAndNull() {
		List<PhanLoaiTour> phanLoaiTourList = new ArrayList<PhanLoaiTour>();
		phanLoaiTourList.add(null);
		phanLoaiTourList.addAll(
				find(PhanLoaiTour.class).where(QPhanLoaiTour.phanLoaiTour.trangThai.eq(core().TT_AP_DUNG)).fetch());

		return phanLoaiTourList;
	}

	public List<PhanLoaiKhachThueTau> getPhanLoaiKhachThueTauListAndNull() {
		List<PhanLoaiKhachThueTau> phanLoaiKhachThueTauList = new ArrayList<PhanLoaiKhachThueTau>();
		phanLoaiKhachThueTauList.add(null);
		phanLoaiKhachThueTauList.addAll(find(PhanLoaiKhachThueTau.class)
				.where(QPhanLoaiKhachThueTau.phanLoaiKhachThueTau.trangThai.eq(core().TT_AP_DUNG)).fetch());

		return phanLoaiKhachThueTauList;
	}

	public List<QuocTichEnum> getQuocTichListAndNull() {

		List<QuocTichEnum> quocTichList = new ArrayList<QuocTichEnum>();
		quocTichList.add(null);
		quocTichList.addAll(Arrays.asList(QuocTichEnum.values()));
		return quocTichList;
	}

	public List<LoaiPhongVeLeEnum> getPhongVeLeListAndNull() {

		List<LoaiPhongVeLeEnum> phongVeLeList = new ArrayList<LoaiPhongVeLeEnum>();
		phongVeLeList.add(null);
		phongVeLeList.addAll(Arrays.asList(LoaiPhongVeLeEnum.values()));
		return phongVeLeList;
	}

	public List<NhomCuaHoi> getNhomCuaHois() {
		NhomCuaHoi nhomCuaHoiSearch = (NhomCuaHoi) MapUtils.getObject(getArg(), "nhomCuaHoiSearch");
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();

		JPAQuery<NhomCuaHoi> query = find(NhomCuaHoi.class)
				.where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG));

		if (nhomCuaHoiSearch != null) {
			query.where(QNhomCuaHoi.nhomCuaHoi.eq(nhomCuaHoiSearch));
			List<NhomCuaHoi> listTmp = query.fetch();
			if (listTmp != null && listTmp.size() > 0) {
				List<CongTyKinhDoanh> congTyKinhDoanhList = getCongTyKinhDoanhs(listTmp.get(0));
				if (congTyKinhDoanhList != null && congTyKinhDoanhList.size() > 0) {
					setCongTyKinhDoanh(congTyKinhDoanhList.get(0));
				}
			}
		}

		nhomCuaHoiList.addAll(query.fetch());

		nhomCuaHoiNotifyChangeList = new ArrayList<NhomCuaHoi>();
		for (NhomCuaHoi nhom : nhomCuaHoiList) {
			nhomCuaHoiNotifyChangeList.add(nhom);
		}

		for (NhomCuaHoi nhom : nhomCuaHoiNotifyChangeList) {
			nhom.setTuNgayThucHienTour(getTuNgay());
			nhom.setDenNgayThucHienTour(getDenNgay());
			nhom.setTuKhoa(tuKhoa);
			nhom.setPhanLoaiTour(phanLoaiTour);
			BindUtils.postNotifyChange(null, null, nhom, "tuNgayThucHienTour");
			BindUtils.postNotifyChange(null, null, nhom, "denNgayThucHienTour");
			BindUtils.postNotifyChange(null, null, nhom, "tuKhoa");
			BindUtils.postNotifyChange(null, null, nhom, "phanLoaiTour");
			BindUtils.postNotifyChange(null, null, nhom, "tenVaSoLuongVe");
		}

		if (nhomCuaHoiNotifyChangeList.size() > 0) {
			BindUtils.postNotifyChange(null, null, this, "nhomCuaHoiNotifyChangeList");
		}

		return nhomCuaHoiList;
	}

	public List<NhomCuaHoi> getNhomCuaHoiListAndNull() {
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
		nhomCuaHoiList.add(null);
		nhomCuaHoiList
				.addAll(find(NhomCuaHoi.class).where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG)).fetch());
		return nhomCuaHoiList;
	}

	public List<NhomCuaHoi> getNhomCuaHoiList() {
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
		nhomCuaHoiList
				.addAll(find(NhomCuaHoi.class).where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG)).fetch());
		return nhomCuaHoiList;
	}

	public List<CongTyKinhDoanh> getCongTyKinhDoanhs(NhomCuaHoi nhomCuaHoi) {
		CongTyKinhDoanh congTyKinhDoanhSearch = (CongTyKinhDoanh) MapUtils.getObject(getArg(), "congTyKinhDoanhSearch");
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		JPAQuery<CongTyKinhDoanh> query = find(CongTyKinhDoanh.class)
				.where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
				.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(nhomCuaHoi));

		if (congTyKinhDoanhSearch != null) {
			query.where(QCongTyKinhDoanh.congTyKinhDoanh.eq(congTyKinhDoanhSearch));
			setCongTyKinhDoanh(congTyKinhDoanhSearch);
			BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanh");
		}

		congTyKinhDoanhList.addAll(query.fetch());

		congTyKinhDoanhNotifyChangeList = new ArrayList<CongTyKinhDoanh>();
		for (CongTyKinhDoanh congTy : congTyKinhDoanhList) {
			congTyKinhDoanhNotifyChangeList.add(congTy);
		}

		for (CongTyKinhDoanh congTy : congTyKinhDoanhNotifyChangeList) {
			congTy.setTuNgayThucHienTour(getTuNgay());
			congTy.setDenNgayThucHienTour(getDenNgay());
			congTy.setTuKhoa(tuKhoa);
			congTy.setPhanLoaiTour(phanLoaiTour);
			System.out.println("Data hiện: "+congTy);
			BindUtils.postNotifyChange(null, null, congTy, "tuNgayThucHienTour");
			BindUtils.postNotifyChange(null, null, congTy, "denNgayThucHienTour");
			BindUtils.postNotifyChange(null, null, congTy, "tuKhoa");
			BindUtils.postNotifyChange(null, null, congTy, "phanLoaiTour");
			BindUtils.postNotifyChange(null, null, congTy, "tenVaSoLuongVe");
		}

		if (congTyKinhDoanhNotifyChangeList.size() > 0) {
			BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanhNotifyChangeList");
		}

		return congTyKinhDoanhList;
	}

	public List<CongTyKinhDoanh> getCongTyKinhDoanhListAndNull(NhomCuaHoi nhomCuaHoi) {
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		congTyKinhDoanhList.add(null);
		if (nhomCuaHoi != null) {
			congTyKinhDoanhList.addAll(
					find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
							.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(nhomCuaHoi)).fetch());
		}
		return congTyKinhDoanhList;
	}

	@Command
	public void clickTabCongTy(@BindingParam("congTy") CongTyKinhDoanh congTy) {
		setCongTyKinhDoanh(congTy);
		BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanh");
		BindUtils.postNotifyChange(null, null, this, "targetQuery");
		BindUtils.postNotifyChange(null, null, this, "phongBanVeLeQuery");
	}

	@Command
	public void clickTabNhom(@BindingParam("nhom") NhomCuaHoi nhom, @BindingParam("tab") Tabbox tab,
			@BindingParam("tabpanels") Tabpanels tabpanels, @BindingParam("index") int index) {
		List<CongTyKinhDoanh> congTyKinhDoanhList = getCongTyKinhDoanhs(nhom);

		Tabbox tabClick = (Tabbox) tabpanels.getChildren().get(index).getChildren().get(0);
		try {
			tabClick.setSelectedIndex(0);
		} catch (Exception e) {
		}

		if (congTyKinhDoanhList.size() > 0) {
			setCongTyKinhDoanh(congTyKinhDoanhList.get(0));
			BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanh");
			BindUtils.postNotifyChange(null, null, this, "targetQuery");
			BindUtils.postNotifyChange(null, null, this, "phongBanVeLeQuery");
		}
	}

	public CongTyKinhDoanh getCongTyKinhDoanhFirst() {
		List<NhomCuaHoi> nhomCuaHoiList = getNhomCuaHois();
		if (nhomCuaHoiList.size() > 0) {
			List<CongTyKinhDoanh> congTyKinhDoanhList = getCongTyKinhDoanhs(nhomCuaHoiList.get(0));
			if (congTyKinhDoanhList != null && congTyKinhDoanhList.size() > 0) {
				return congTyKinhDoanhList.get(0);
			}
		}
		return null;
	}

	@Command
	public void search(@BindingParam("notify") final Object beanObject,
			@BindingParam("attr") @Default(value = "*") final String attrs) {
		NhomCuaHoi nhomCuaHoiSearch = (NhomCuaHoi) MapUtils.getObject(getArg(), "nhomCuaHoiSearch");
		CongTyKinhDoanh congTyKinhDoanhSearch = (CongTyKinhDoanh) MapUtils.getObject(getArg(), "congTyKinhDoanhSearch");

		if (nhomCuaHoiSearch == null && congTyKinhDoanhSearch == null) {
			setCongTyKinhDoanh(getCongTyKinhDoanhFirst());
			BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanh");
		}

		for (final String field : attrs.split("\\|")) {
			if (!field.isEmpty()) {
				BindUtils.postNotifyChange(null, null, beanObject == null ? this : beanObject, field);
			}
		}
	}

	public JPAQuery<DatVe> getPhongBanVeLeQuery() {

		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");
		JPAQuery<DatVe> datVeQuery = find(DatVe.class)
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNull().and(QDatVe.datVe.congTyKinhDoanh.isNull()));

		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			datVeQuery.where(QDatVe.datVe.id.eq(Long.valueOf(tuKhoa)));
		}

		if (phanLoaiTour != null && !phanLoaiTour.noId()) {
			datVeQuery.where(QDatVe.datVe.phanLoaiTour.eq(phanLoaiTour));
		}

		if (getTuNgay() != null && getDenNgay() != null) {
			Calendar calNgayKhachDat = Calendar.getInstance();
			calNgayKhachDat.setTime(getFixTuNgay());
			Calendar calNgayThucHien = Calendar.getInstance();
			calNgayThucHien.setTime(getFixDenNgay());
			datVeQuery
					.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))))
					.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayThucHien.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHien.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
									.eq(calNgayThucHien.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgay() == null && getDenNgay() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFixDenNgay());
			datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgay() != null && getDenNgay() == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFixTuNgay());
			datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayKhachDat.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}

		datVeQuery.orderBy(QDatVe.datVe.ngaySua.desc());

		return datVeQuery;
	}

	public JPAQuery<DatVe> getListDatVeQuery() {

		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");
		CongTyKinhDoanh congTyKinhDoanh = (CongTyKinhDoanh) MapUtils.getObject(getArg(), "congTyKinhDoanh");
		NhomCuaHoi nhomCuaHoi = (NhomCuaHoi) MapUtils.getObject(getArg(), "nhomCuaHoi");

		JPAQuery<DatVe> datVeQuery = find(DatVe.class)
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()));

		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			datVeQuery.where(QDatVe.datVe.id.eq(Long.valueOf(tuKhoa)));
		}

		if (phanLoaiTour != null && !phanLoaiTour.noId()) {
			datVeQuery.where(QDatVe.datVe.phanLoaiTour.eq(phanLoaiTour));
		}

		if (nhomCuaHoi != null && !nhomCuaHoi.noId()) {
			datVeQuery.where(QDatVe.datVe.nhomCuaHoi.eq(nhomCuaHoi));
		}

		if (nhomCuaHoi != null && congTyKinhDoanh != null && !congTyKinhDoanh.noId()) {
			datVeQuery.where(QDatVe.datVe.congTyKinhDoanh.eq(congTyKinhDoanh));
		}

		if (getTuNgay() != null && getDenNgay() != null) {
			Calendar calNgayKhachDat = Calendar.getInstance();
			calNgayKhachDat.setTime(getFixTuNgay());
			Calendar calNgayThucHien = Calendar.getInstance();
			calNgayThucHien.setTime(getFixDenNgay());
			datVeQuery
					.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))))
					.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayThucHien.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHien.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
									.eq(calNgayThucHien.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgay() == null && getDenNgay() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFixDenNgay());
			datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgay() != null && getDenNgay() == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFixTuNgay());
			datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayKhachDat.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgay() == null && getDenNgay() == null) {
			setTuNgay(new Date());
		}

		datVeQuery.orderBy(QDatVe.datVe.ngaySua.desc());

		return datVeQuery;
	}

	public List<NhomCuaHoi> getNhomCuaHoiThongKes() {
		System.out.println("SelectedNgayThucHien: " + selectedNgayThucHien);
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
		JPAQuery<NhomCuaHoi> query = find(NhomCuaHoi.class)
				.where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG));

		nhomCuaHoiList.addAll(query.fetch());
		
		return nhomCuaHoiList;
	}

	public JPAQuery<CongTyKinhDoanh> getCongTyKinhDoanhThongKes(NhomCuaHoi nhom) {
		JPAQuery<CongTyKinhDoanh> query = find(CongTyKinhDoanh.class)
				.where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
				.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(nhom));	
		return query;
	}

	public List<List<String>> getNhomCuaHoiVaPhongBanVeLes() {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> listTmp = new ArrayList<String>();
		listTmp.add("Phòng bán vé lẻ");
		listTmp.add("0");
		list.add(listTmp);
		//listResult.add(list);
		JPAQuery<NhomCuaHoi> query = find(NhomCuaHoi.class)
				.where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG));
		if (query != null) {
			for (NhomCuaHoi nhom : query.fetch()) {
				listTmp = new ArrayList<String>();
				listTmp.add(nhom.getTen());
				listTmp.add(nhom.getId().toString());
				list.add(listTmp);
			}
		}
		return list;
	}

	public Date getFixTuNgay2() {
		Calendar tuNgayCal = Calendar.getInstance();
		tuNgayCal.setTime(getTuNgay());
		tuNgayCal.set(Calendar.HOUR_OF_DAY, 0);
		tuNgayCal.set(Calendar.MINUTE, 0);
		tuNgayCal.set(Calendar.SECOND, 0);
		tuNgayCal.set(Calendar.MILLISECOND, 0);
		return tuNgayCal.getTime();
	}

	public Date getFixDenNgay2() {
		Calendar denNgayCal = Calendar.getInstance();
		denNgayCal.setTime(getDenNgay());
		denNgayCal.set(Calendar.HOUR_OF_DAY, 23);
		denNgayCal.set(Calendar.MINUTE, 59);
		denNgayCal.set(Calendar.SECOND, 59);
		denNgayCal.set(Calendar.MILLISECOND, 0);
		return denNgayCal.getTime();
	}

	private Long soLuongNguoiLonNhat;
	private Long soLuongTreEm4Den9Tuoi;
	private Long soLuongTreEm1Den3Tuoi;

	public Long getSoLuongNguoiLonNhat() {
		return soLuongNguoiLonNhat;
	}

	public void setSoLuongNguoiLonNhat(Long soLuongNguoiLonNhat) {
		this.soLuongNguoiLonNhat = soLuongNguoiLonNhat;
	}

	public Long getSoLuongTreEm4Den9Tuoi() {
		return soLuongTreEm4Den9Tuoi;
	}

	public void setSoLuongTreEm4Den9Tuoi(Long soLuongTreEm4Den9Tuoi) {
		this.soLuongTreEm4Den9Tuoi = soLuongTreEm4Den9Tuoi;
	}

	public Long getSoLuongTreEm1Den3Tuoi() {
		return soLuongTreEm1Den3Tuoi;
	}

	public void setSoLuongTreEm1Den3Tuoi(Long soLuongTreEm1Den3Tuoi) {
		this.soLuongTreEm1Den3Tuoi = soLuongTreEm1Den3Tuoi;
	}

	public List<Long> getSoLuongTotalNhom(String id) {
		List<Long> list = new ArrayList<Long>();
		List<DatVe> datVes = new ArrayList<DatVe>();
		Long totalNguoiLon = 0l;
		Long totalTreEm4Den9Tuoi = 0l;
		Long totalTreEm1Den3Tuoi = 0l;

		JPAQuery<DatVe> datVeQuery = null;
		if ("0".equals(id)) {
			datVeQuery = find(DatVe.class).where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
					.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
							.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
					.where(QDatVe.datVe.nhomCuaHoi.isNull().and(QDatVe.datVe.congTyKinhDoanh.isNull()));
		} else {
			datVeQuery = find(DatVe.class).where(QDatVe.datVe.nhomCuaHoi.id.eq(Long.valueOf(id.toString())))
					.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
					.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
							.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
					.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()));
		}

		if (selectedNgayThucHien) {
			// Tim kiem theo ngay thuc hien
			if (getDenNgay() != null) {
				datVeQuery.where(QDatVe.datVe.ngayThucHienTour.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
								.eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		} else {
			// Tim kiem theo ngay dat ve
			if (getDenNgay() != null) {
				datVeQuery.where(QDatVe.datVe.ngayKhachDat.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		}
		
		if (datVeQuery != null) {
			datVeQuery.where(QDatVe.datVe.veDuocDuyet.isFalse());
			datVes = datVeQuery.fetch();
			if (datVes.size() > 0) {
				for (DatVe datVe : datVes) {
					totalNguoiLon += Long.valueOf(datVe.getSoLuongNguoiLon());
					totalTreEm4Den9Tuoi += Long.valueOf(datVe.getSoLuongTreEmDuoi10Tuoi());
					totalTreEm1Den3Tuoi += Long.valueOf(datVe.getSoLuongTreEmDuoi5Tuoi());
				}
				list.add(totalNguoiLon == 0 || "".equals(totalNguoiLon) ? 0l : totalNguoiLon);
				list.add(totalTreEm4Den9Tuoi == 0 || "".equals(totalTreEm4Den9Tuoi) ? 0l : totalTreEm4Den9Tuoi);
				list.add(totalTreEm1Den3Tuoi == 0 || "".equals(totalTreEm1Den3Tuoi) ? 0l : totalTreEm1Den3Tuoi);
			}
		}

		return list;
	}

	public Long getTongSoLuongVeCuaHiepHoi() {
		Long total = 0l;
		Long totalNguoiLon = 0l;
		Long totalTreEm4Den9Tuoi = 0l;
		Long totalTreEm1Den3Tuoi = 0l;
		List<DatVe> datVes = new ArrayList<DatVe>();
		JPAQuery<DatVe> datVeQuery = find(DatVe.class)
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)));
		if (selectedNgayThucHien) {
			// Tim kiem theo ngay thuc hien
			if (getDenNgay() != null) {
				datVeQuery.where(QDatVe.datVe.ngayThucHienTour.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
								.eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		} else {
			// Tim kiem theo ngay dat ve
			if (getDenNgay() != null) {
				datVeQuery.where(QDatVe.datVe.ngayKhachDat.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		}
		
		if (datVeQuery != null) {
			//datVeQuery.where(QDatVe.datVe.veDuocDuyet.isTrue());
			datVes = datVeQuery.fetch();
			if (datVes.size() > 0) {
				for (DatVe datVe : datVes) {
					totalNguoiLon += Long.valueOf(datVe.getSoLuongNguoiLon());
					totalTreEm4Den9Tuoi += Long.valueOf(datVe.getSoLuongTreEmDuoi10Tuoi());
					totalTreEm1Den3Tuoi += Long.valueOf(datVe.getSoLuongTreEmDuoi5Tuoi());
				}
				total += totalNguoiLon + totalTreEm4Den9Tuoi + totalTreEm1Den3Tuoi;
			}
		}

		return total;
	}

	private NhomCuaHoi nhomCuaHoi;

	@Transient
	public NhomCuaHoi getNhomCuaHoi() {
		return nhomCuaHoi;
	}

	public void setNhomCuaHoi(String Id) {
		NhomCuaHoi nhom = null;
		if (!Id.equals("0")) {
			nhom = find(NhomCuaHoi.class).where(QNhomCuaHoi.nhomCuaHoi.id.eq(Long.valueOf(Id))).fetchFirst();
		}
		this.nhomCuaHoi = nhom;
	}
	
	@Transient
	public int getTongSoLuongVeDatBanDauCuaNhom() {
		int total = 0;
		if (nhomCuaHoi != null) {
			JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.nhomCuaHoi.eq(nhomCuaHoi))
					.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
					.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
							.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
					.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()));
			
			if (selectedNgayThucHien) {
				//Tim kiem theo ngay thuc hien
				if (getDenNgay() != null) {
					datVeQuery.where(QDatVe.datVe.ngayThucHienTour.between(getFixTuNgay2(), getFixDenNgay2()));
				} else {
					Calendar calNgayKhachDat = Calendar.getInstance();
					calNgayKhachDat.setTime(getFixTuNgay());
					datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayKhachDat.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
				}
			} else {
				//Tim kiem theo ngay dat ve
				if (getDenNgay() != null) {
					datVeQuery.where(QDatVe.datVe.ngayKhachDat.between(getFixTuNgay2(), getFixDenNgay2()));
				} else {
					Calendar calNgayKhachDat = Calendar.getInstance();
					calNgayKhachDat.setTime(getFixTuNgay());
					datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
				}
			}
			
			datVeQuery.where(QDatVe.datVe.veDuocDuyet.isFalse());
			for (DatVe datVe : datVeQuery.fetch()) {
				total += datVe.getSoLuongNguoiLonBanDau() + datVe.getSoLuongTreEmDuoi10TuoiBanDau()
						+ datVe.getSoLuongTreEmDuoi5TuoiBanDau();
			}
		} else {
			JPAQuery<DatVe> datVePhongVeLe = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
					.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
					.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
					.where(QDatVe.datVe.nhomCuaHoi.isNull().and(QDatVe.datVe.congTyKinhDoanh.isNull()));
			
			if (selectedNgayThucHien) {
				//Tim kiem theo ngay thuc hien
				if (getDenNgay() != null) {
					datVePhongVeLe.where(QDatVe.datVe.ngayThucHienTour.between(getFixTuNgay2(), getFixDenNgay2()));
				} else {
					Calendar calNgayKhachDat = Calendar.getInstance();
					calNgayKhachDat.setTime(getFixTuNgay());
					datVePhongVeLe.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayKhachDat.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
				}
			} else {
				//Tim kiem theo ngay dat ve
				if (getDenNgay() != null) {
					datVePhongVeLe.where(QDatVe.datVe.ngayKhachDat.between(getFixTuNgay2(), getFixDenNgay2()));
				} else {
					Calendar calNgayKhachDat = Calendar.getInstance();
					calNgayKhachDat.setTime(getFixTuNgay());
					datVePhongVeLe.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
				}
			}
			
			datVePhongVeLe.where(QDatVe.datVe.veDuocDuyet.isFalse());
			for (DatVe datVe : datVePhongVeLe.fetch()) {
				total += datVe.getSoLuongNguoiLonBanDau() + datVe.getSoLuongTreEmDuoi10TuoiBanDau()
						+ datVe.getSoLuongTreEmDuoi5TuoiBanDau();
			}
		}
		return total;
	}

	@Transient
	public int getTongSoLuongVeDuyettCuaNhom() {
		Calendar cal = Calendar.getInstance();
		Date denNgay = getDenNgay();
		int total = 0;
		if (denNgay == null) {
			denNgay = new Date();
		}
		cal.setTime(denNgay);

		if (nhomCuaHoi != null) {
			JPAQuery<VeDuyet> veDuyetQuery = find(VeDuyet.class)
					.where(QVeDuyet.veDuyet.nhomCuaHoi.eq(nhomCuaHoi));
			
			if (selectedNgayThucHien) {
				if (getDenNgay() != null) {
					veDuyetQuery.where(QVeDuyet.veDuyet.ngayThucHienTour.between(getFixTuNgay2(), getFixDenNgay2()));
				} else {
					Calendar calNgayKhachDat = Calendar.getInstance();
					calNgayKhachDat.setTime(getFixTuNgay());
					veDuyetQuery.where(QVeDuyet.veDuyet.ngayThucHienTour.year().eq(calNgayKhachDat.get(Calendar.YEAR))
							.and(QVeDuyet.veDuyet.ngayThucHienTour.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
							.and(QVeDuyet.veDuyet.ngayThucHienTour.dayOfMonth()
									.eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
				}
			}
			
			if (veDuyetQuery != null) {
				List<VeDuyet> veDuyets = veDuyetQuery.fetch();
				for (VeDuyet veDuyet : veDuyets) {
					total += veDuyet.getSoLuongVeDuyet();
				}
			}
		}

		return total;
	}

	@Transient 
	public Long getTongGiaTienPhongVeBanLe() {
		
		Long totalCash = 0L;
		JPAQuery<DatVe> datVePhongVeLe = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
				.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNull().and(QDatVe.datVe.congTyKinhDoanh.isNull()));
		
		if (selectedNgayThucHien) {
			//Tim kiem theo ngay thuc hien
			if (getDenNgay() != null) {
				datVePhongVeLe.where(QDatVe.datVe.ngayThucHienTour.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVePhongVeLe.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		} else {
			//Tim kiem theo ngay dat ve
			if (getDenNgay() != null) {
				datVePhongVeLe.where(QDatVe.datVe.ngayKhachDat.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVePhongVeLe.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		}
		
		datVePhongVeLe.where(QDatVe.datVe.veDuocDuyet.isFalse());
		for (DatVe datVe : datVePhongVeLe.fetch()) {
			totalCash += Long.valueOf(datVe.getGiaDichVu());
		}
		
		return totalCash;
	}
	
//	@Command
//	public void napGiaVe() {
//		
//		if (getPhanLoaiKhachDiTour() == PhanLoaiKhachDiTour.THUE_TAU_DI_RIENG || getPhanLoaiKhachDiTour() == PhanLoaiKhachDiTour.TOUR_DI_RIENG) {
//			giaVe2Ngay1Dem = null;
//			BindUtils.postNotifyChange(null, null, this, "giaVe2Ngay1Dem");
//			phanLoaiTour = null;
//			BindUtils.postNotifyChange(null, null, this, "phanLoaiTour");
//		} 
//		
//		if (getPhanLoaiKhachDiTour() == PhanLoaiKhachDiTour.TOUR_BINH_THUONG) {
//			giaVe2Ngay1Dem = null;
//			BindUtils.postNotifyChange(null, null, this, "giaVe2Ngay1Dem");
//		}
//		
//		if (getPhanLoaiKhachDiTour() == PhanLoaiKhachDiTour.HAINGAY_MOTDEM){
//			phanLoaiTour = null;
//			BindUtils.postNotifyChange(null, null, this, "phanLoaiTour");
//		}
//		
//		if (!getNhanVien().isNhomThanhVien()) {
//			long giaTemp = 0;
//			if (getPhanLoaiKhachDiTour().equals(PhanLoaiKhachDiTour.TOUR_BINH_THUONG)) {
//				if (getPhanLoaiTour() != null) {
//					giaTemp = (getPhanLoaiTour().getGiaNguoiLon() * getSoLuongNguoiLon())
//							+ (getPhanLoaiTour().getGiaTreEm4Den9() * getSoLuongTreEmDuoi10Tuoi())
//							+ (getPhanLoaiTour().getGiaTreEm1Den3() * getSoLuongTreEmDuoi5Tuoi());
//				}
//			} else if (getPhanLoaiKhachDiTour().equals(PhanLoaiKhachDiTour.HAINGAY_MOTDEM)) {
//				if (getGiaVe2Ngay1Dem() != null) {
//					giaTemp = (getGiaVe2Ngay1Dem().getGiaNguoiLon() * getSoLuongNguoiLon())
//							+ (getGiaVe2Ngay1Dem().getGiaTreEm4Den9() * getSoLuongTreEmDuoi10Tuoi())
//							+ (getGiaVe2Ngay1Dem().getGiaTreEm1Den3() * getSoLuongTreEmDuoi5Tuoi());
//				}
//			} else if (getPhanLoaiKhachDiTour().equals(PhanLoaiKhachDiTour.TOUR_DI_RIENG)) {
//				GiaVeDiTourRieng giaVe = find(GiaVeDiTourRieng.class)
//						.where(QGiaVeDiTourRieng.giaVeDiTourRieng.trangThai.eq(core().TT_AP_DUNG))
//						.where(QGiaVeDiTourRieng.giaVeDiTourRieng.soNguoiDen.goe(getSoLuongNguoiLon())
//								.and(QGiaVeDiTourRieng.giaVeDiTourRieng.soNguoiTu.loe(getSoLuongNguoiLon())))
//						.orderBy(QGiaVeDiTourRieng.giaVeDiTourRieng.soNguoiDen.asc()).fetchFirst();
//				if (giaVe != null) {
//					giaTemp = giaVe.getGiaVe() * getSoLuongNguoiLon();
//				}
//			} else {
//				GiaVeDiTauRieng giaVe = find(GiaVeDiTauRieng.class)
//						.where(QGiaVeDiTauRieng.giaVeDiTauRieng.trangThai.eq(core().TT_AP_DUNG))
//						.where(QGiaVeDiTauRieng.giaVeDiTauRieng.ten.goe(getSoLuongNguoiLon()))
//						.orderBy(QGiaVeDiTauRieng.giaVeDiTauRieng.ten.asc()).fetchFirst();
//
//				if (giaVe != null) {
//					giaTemp = giaVe.getGiaVe();
//				}
//			}
//			if (getLoaiPhongBanVeLe() != null
//					&& getLoaiPhongBanVeLe().equals(LoaiPhongVeLeEnum.PHONG_VE_LE_GIAN_TIEP)) {
//				if (getTuyenXe() != null) {
//					giaTemp += getTuyenXe().getGiaVe();
//				}
//			}
//			giaDichVu = giaTemp;
//			BindUtils.postNotifyChange(null, null, this, "giaDichVu");
//		}
//	}
	
	@Command
	public void thongKeVe() {
		listResult.clear();		
		BindUtils.postNotifyChange(null, null, this, "nhomCuaHoiThongKes");
		BindUtils.postNotifyChange(null, null, this, "nhomCuaHoiVaPhongBanVeLes");
		BindUtils.postNotifyChange(null, null, this, "tongSoLuongVeCuaHiepHoi");
	}
	
	@Command
	public void xuatExcel() throws IOException {
		ExcelUtil.exportThongKeVe("Thống kê vé " + titleDate, "thongkeve", "nhom1", listResult);
	}
	
	private int tongSoLuongVeDuyetCuaCongTy = 0;
	private int tongSoLuongVeDatBanDauCuaCongTy = 0;
	private String tongVeSau7h30 = "";
	
	public String getTongVeSau7h30() {
		return tongVeSau7h30;
	}

	public void setTongVeSau7h30(String tongVeSau7h30) {
		this.tongVeSau7h30 = tongVeSau7h30;
	}

	public int getTongSoLuongVeDatBanDauCuaCongTy() {
		return tongSoLuongVeDatBanDauCuaCongTy;
	}

	public void setTongSoLuongVeDatBanDauCuaCongTy(int tongSoLuongVeDatBanDauCuaCongTy) {
		this.tongSoLuongVeDatBanDauCuaCongTy = tongSoLuongVeDatBanDauCuaCongTy;
	}

	public int getTongSoLuongVeDuyetCuaCongTy() {
		return tongSoLuongVeDuyetCuaCongTy;
	}

	public void setTongSoLuongVeDuyetCuaCongTy(int tongSoLuongVeDuyetCuaCongTy) {
		this.tongSoLuongVeDuyetCuaCongTy = tongSoLuongVeDuyetCuaCongTy;
	}
 
	public int getTongSoLuongVeDatBanDauCuaCongTy(CongTyKinhDoanh congTy) {
		int total = 0;
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.congTyKinhDoanh.eq(congTy))
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()));

		if (selectedNgayThucHien) {
			// Tim kiem theo ngay thuc hien
			if (getDenNgay() != null) {
				datVeQuery.where(QDatVe.datVe.ngayThucHienTour.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
								.eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		} else {
			// Tim kiem theo ngay dat ve
			if (getDenNgay() != null) {
				datVeQuery.where(QDatVe.datVe.ngayKhachDat.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		}

		datVeQuery.where(QDatVe.datVe.veDuocDuyet.isFalse());
		for (DatVe datVe : datVeQuery.fetch()) {
			total += datVe.getSoLuongNguoiLonBanDau() + datVe.getSoLuongTreEmDuoi10TuoiBanDau()
					+ datVe.getSoLuongTreEmDuoi5TuoiBanDau();
		}
		setTongSoLuongVeDatBanDauCuaCongTy(total);
		BindUtils.postNotifyChange(null, null, this, "tongSoLuongVeDatBanDauCuaCongTy");
		return total;
	}
	
	public int getTongSoLuongVeDuyettCuaCongTy(CongTyKinhDoanh congTy) {
		Calendar cal = Calendar.getInstance();
		Date denNgay = getDenNgay();
		int total = 0;
		if (denNgay == null) {
			denNgay = new Date();
		}
		cal.setTime(denNgay);

		JPAQuery<VeDuyet> veDuyetQuery = find(VeDuyet.class).where(QVeDuyet.veDuyet.congTyKinhDoanh.eq(congTy));

		if (selectedNgayThucHien) {
			if (getDenNgay() != null) {
				veDuyetQuery.where(QVeDuyet.veDuyet.ngayThucHienTour.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				veDuyetQuery.where(QVeDuyet.veDuyet.ngayThucHienTour.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QVeDuyet.veDuyet.ngayThucHienTour.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QVeDuyet.veDuyet.ngayThucHienTour.dayOfMonth()
								.eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		}

		if (veDuyetQuery != null) {
			List<VeDuyet> veDuyets = veDuyetQuery.fetch();
			for (VeDuyet veDuyet : veDuyets) {
				total += veDuyet.getSoLuongVeDuyet();
			}
		}
		setTongSoLuongVeDuyetCuaCongTy(total);
		BindUtils.postNotifyChange(null, null, this, "tongSoLuongVeDuyetCuaCongTy");
		return total;
	}
	
	public String formatNumber(int number) {
		if (number > 1000) {
			return String.valueOf(number);
		}
		try {
			NumberFormat formatter = new DecimalFormat("###,###");
			String resp = formatter.format(number);
			resp = resp.replaceAll(",", ".");
			return resp;
		} catch (Exception e) {
			return "";
		}
	}

	public String getTongVeSau7h30(CongTyKinhDoanh congTy) {
		String str = "0";
		Calendar cal = Calendar.getInstance();
		Date denNgay = getDenNgay();
		int total = 0;
		if (denNgay == null) {
			denNgay = new Date();
		}
		cal.setTime(denNgay);
		JPAQuery<NhanVien> nhanVienQuery = find(NhanVien.class)
				.where(QNhanVien.nhanVien.vaiTros.any().alias.eq("bandieuhanh"));
		NhanVien nhanVien = nhanVienQuery.fetchFirst();
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.congTyKinhDoanh.eq(congTy))
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()))
				.where(QDatVe.datVe.nguoiTao.eq(nhanVien));

		if (selectedNgayThucHien) {
			// Tim kiem theo ngay thuc hien
			if (getDenNgay() != null) {
				datVeQuery.where(QDatVe.datVe.ngayThucHienTour.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
								.eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		} else {
			// Tim kiem theo ngay dat ve
			if (getDenNgay() != null) {
				datVeQuery.where(QDatVe.datVe.ngayKhachDat.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		}
		datVeQuery.where(QDatVe.datVe.veDuocDuyet.isFalse());
		for (DatVe datVe : datVeQuery.fetch()) {
			total += datVe.getSoLuongNguoiLon() + datVe.getSoLuongTreEmDuoi10Tuoi() + datVe.getSoLuongTreEmDuoi5Tuoi();
			
		}
		str = formatNumber(total);

		setTongVeSau7h30(str);
		BindUtils.postNotifyChange(null, null, this, "tongVeSau7h30");
		return str;
	}
	
	public String getTenCuaCongTy(CongTyKinhDoanh congTy) {
		List<DatVe> datVes = new ArrayList<DatVe>();
		Long totalNguoiLon = 0l;
		Long totalTreEm4Den9Tuoi = 0l;
		Long totalTreEm1Den3Tuoi = 0l;
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.congTyKinhDoanh.eq(congTy))
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()));

		if (selectedNgayThucHien) {
			// Tim kiem theo ngay thuc hien
			if (getDenNgay() != null) {
				datVeQuery.where(QDatVe.datVe.ngayThucHienTour.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
								.eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		} else {
			// Tim kiem theo ngay dat ve
			if (getDenNgay() != null) {
				datVeQuery.where(QDatVe.datVe.ngayKhachDat.between(getFixTuNgay2(), getFixDenNgay2()));
			} else {
				Calendar calNgayKhachDat = Calendar.getInstance();
				calNgayKhachDat.setTime(getFixTuNgay());
				datVeQuery.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))));
			}
		}

		if (datVeQuery != null) {
			datVeQuery.where(QDatVe.datVe.veDuocDuyet.isFalse());
			datVes = datVeQuery.fetch();
			for (DatVe datVe : datVes) {
				totalNguoiLon += Long.valueOf(datVe.getSoLuongNguoiLon());
				totalTreEm4Den9Tuoi += Long.valueOf(datVe.getSoLuongTreEmDuoi10Tuoi());
				totalTreEm1Den3Tuoi += Long.valueOf(datVe.getSoLuongTreEmDuoi5Tuoi());
			}

			congTy.setSoLuongNguoiLonNhat(totalNguoiLon);
			congTy.setSoLuongTreEm1Den3Tuoi(totalTreEm1Den3Tuoi);
			congTy.setSoLuongTreEm4Den9Tuoi(totalTreEm4Den9Tuoi);
			
			tongSoLuongVeDatBanDauCuaCongTy = getTongSoLuongVeDatBanDauCuaCongTy(congTy);
			setTongSoLuongVeDatBanDauCuaCongTy(tongSoLuongVeDatBanDauCuaCongTy);
			tongSoLuongVeDuyetCuaCongTy = getTongSoLuongVeDuyettCuaCongTy(congTy);
			setTongSoLuongVeDuyetCuaCongTy(tongSoLuongVeDuyetCuaCongTy);
			tongVeSau7h30 = getTongVeSau7h30(congTy);
			setTongVeSau7h30(tongVeSau7h30);
			
			congTy.setTongSoLuongVeDatBanDauCuaCongTy(getTongSoLuongVeDatBanDauCuaCongTy());
			congTy.setTongSoLuongVeDuyetCuaCongTy(getTongSoLuongVeDuyetCuaCongTy());
			congTy.setTongVeSau7h30(getTongVeSau7h30());
			
			BindUtils.postNotifyChange(null, null, congTy, "tongVeSau7h30");
			BindUtils.postNotifyChange(null, null, this, "tongVeSau7h30");
			BindUtils.postNotifyChange(null, null, congTy, "soLuongNguoiLonNhat");
			BindUtils.postNotifyChange(null, null, congTy, "soLuongTreEm1Den3Tuoi");
			BindUtils.postNotifyChange(null, null, congTy, "soLuongTreEm4Den9Tuoi");
			BindUtils.postNotifyChange(null, null, congTy, "tongSoLuongVeDuyetCuaCongTy");
			BindUtils.postNotifyChange(null, null, congTy, "tongSoLuongVeDatBanDauCuaCongTy");
			BindUtils.postNotifyChange(null, null, this, "tongSoLuongVeDuyetCuaCongTy");
			BindUtils.postNotifyChange(null, null, this, "tongSoLuongVeDatBanDauCuaCongTy");
			listResult.add(congTy);
		}

		return congTy.getTen();
	}
}
