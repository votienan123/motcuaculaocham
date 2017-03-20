package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CongTyKinhDoanh;
import vn.toancauxanh.gg.model.DatVe;
import vn.toancauxanh.gg.model.DieuTiet;
import vn.toancauxanh.gg.model.NhomCuaHoi;
import vn.toancauxanh.gg.model.PhanLoaiKhachThueTau;
import vn.toancauxanh.gg.model.PhanLoaiTour;
import vn.toancauxanh.gg.model.QCongTyKinhDoanh;
import vn.toancauxanh.gg.model.QDatVe;
import vn.toancauxanh.gg.model.QNhomCuaHoi;
import vn.toancauxanh.gg.model.QPhanLoaiKhachThueTau;
import vn.toancauxanh.gg.model.QPhanLoaiTour;
import vn.toancauxanh.gg.model.enums.QuocTichEnum;
import vn.toancauxanh.service.BasicService;

public class DieuTietService extends BasicService<DieuTiet> {
	
//	private CongTyKinhDoanh congTyKinhDoanh;
	private NhomCuaHoi nhomCuaHoi;
	private List<NhomCuaHoi> nhomCuaHoiNotifyChangeList;
	private List<CongTyKinhDoanh> congTyKinhDoanhNotifyChangeList;

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

//	public CongTyKinhDoanh getCongTyKinhDoanh() {
//		if (congTyKinhDoanh == null) {
//			return getCongTyKinhDoanhFirst();
//		}
//		return congTyKinhDoanh;
//	}

//	public void setCongTyKinhDoanh(CongTyKinhDoanh congTyKinhDoanh) {
//		this.congTyKinhDoanh = congTyKinhDoanh;
//	}

	public NhomCuaHoi getNhomCuaHoi() {
		return nhomCuaHoi;
	}

	public void setNhomCuaHoi(NhomCuaHoi nhomCuaHoi) {
		this.nhomCuaHoi = nhomCuaHoi;
	}

	public JPAQuery<CongTyKinhDoanh> getTargetQuery() {
//		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
//		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "").trim();
//		PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");

		JPAQuery<CongTyKinhDoanh> congTyQuery = find(CongTyKinhDoanh.class);

		if (getNhomCuaHoi() != null && !getNhomCuaHoi().noId()) {
			congTyQuery.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(getNhomCuaHoi()));
		}

		congTyQuery.orderBy(QCongTyKinhDoanh.congTyKinhDoanh.ten.asc());

		return congTyQuery;
	}
	
//	public List<PhanLoaiTour> getPhanLoaiTourListAndNull() {
//		List<PhanLoaiTour> phanLoaiTourList = new ArrayList<PhanLoaiTour>();
//		phanLoaiTourList.add(null);
//		phanLoaiTourList.addAll(find(PhanLoaiTour.class).where(QPhanLoaiTour.phanLoaiTour.trangThai.eq(core().TT_AP_DUNG)).fetch());
//
//		return phanLoaiTourList;
//	}
//	
//	public List<PhanLoaiKhachThueTau> getPhanLoaiKhachThueTauListAndNull() {
//		List<PhanLoaiKhachThueTau> phanLoaiKhachThueTauList = new ArrayList<PhanLoaiKhachThueTau>();
//		phanLoaiKhachThueTauList.add(null);
//		phanLoaiKhachThueTauList.addAll(find(PhanLoaiKhachThueTau.class)
//			.where(QPhanLoaiKhachThueTau.phanLoaiKhachThueTau.trangThai.eq(core().TT_AP_DUNG)).fetch());
//
//		return phanLoaiKhachThueTauList;
//	}
//
//	public List<QuocTichEnum> getQuocTichListAndNull() {
//		List<QuocTichEnum> quocTichList = new ArrayList<QuocTichEnum>();
//		quocTichList.add(null);
//		quocTichList.addAll(Arrays.asList(QuocTichEnum.values()));
//
//		return quocTichList;
//	}
//	
	public List<NhomCuaHoi> getNhomCuaHois() {
		NhomCuaHoi nhomCuaHoiSearch = (NhomCuaHoi) MapUtils.getObject(getArg(), "nhomCuaHoiSearch");
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
		
		JPAQuery<NhomCuaHoi> query = find(NhomCuaHoi.class).where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG));
		
//		if (nhomCuaHoiSearch != null) {
//			query.where(QNhomCuaHoi.nhomCuaHoi.eq(nhomCuaHoiSearch));
//			List<NhomCuaHoi> listTmp = query.fetch();
//			if (listTmp != null && listTmp.size() > 0) {
//				List<CongTyKinhDoanh> congTyKinhDoanhList = getCongTyKinhDoanhs(listTmp.get(0));
//				if (congTyKinhDoanhList != null && congTyKinhDoanhList.size() > 0) {
//					setCongTyKinhDoanh(congTyKinhDoanhList.get(0));
//				}
//			}
//		}
		
		nhomCuaHoiList.addAll(query.fetch());
		
		if (nhomCuaHoiList.size() > 0) {
			setNhomCuaHoi(nhomCuaHoiList.get(0));
			BindUtils.postNotifyChange(null, null, this, "nhomCuaHoi");
		}
		
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
//
//	public List<NhomCuaHoi> getNhomCuaHoiListAndNull() {
//		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
//		nhomCuaHoiList.add(null);
//		nhomCuaHoiList.addAll(find(NhomCuaHoi.class).where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG)).fetch());
//		return nhomCuaHoiList;
//	}
//	
//	public List<CongTyKinhDoanh> getCongTyKinhDoanhs(NhomCuaHoi nhomCuaHoi) {
//		CongTyKinhDoanh congTyKinhDoanhSearch = (CongTyKinhDoanh) MapUtils.getObject(getArg(), "congTyKinhDoanhSearch");
//		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
//		PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");
//		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
//		JPAQuery<CongTyKinhDoanh> query = find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
//				.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(nhomCuaHoi));
//		
//		if (congTyKinhDoanhSearch != null) {
//			query.where(QCongTyKinhDoanh.congTyKinhDoanh.eq(congTyKinhDoanhSearch));
//			setCongTyKinhDoanh(congTyKinhDoanhSearch);
//			BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanh");
//		}
//		
//		congTyKinhDoanhList.addAll(query.fetch());
//		
//		congTyKinhDoanhNotifyChangeList = new ArrayList<CongTyKinhDoanh>();
//		for (CongTyKinhDoanh congTy : congTyKinhDoanhList) {
//			congTyKinhDoanhNotifyChangeList.add(congTy);
//		}
//		
//		for (CongTyKinhDoanh congTy : congTyKinhDoanhNotifyChangeList) {
//			congTy.setTuNgayThucHienTour(getTuNgay());
//			congTy.setDenNgayThucHienTour(getDenNgay());
//			congTy.setTuKhoa(tuKhoa);
//			congTy.setPhanLoaiTour(phanLoaiTour);
//			BindUtils.postNotifyChange(null, null, congTy, "tuNgayThucHienTour");
//			BindUtils.postNotifyChange(null, null, congTy, "denNgayThucHienTour");
//			BindUtils.postNotifyChange(null, null, congTy, "tuKhoa");
//			BindUtils.postNotifyChange(null, null, congTy, "phanLoaiTour");
//			BindUtils.postNotifyChange(null, null, congTy, "tenVaSoLuongVe");
//		}
//		
//		if (congTyKinhDoanhNotifyChangeList.size() > 0) {
//			BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanhNotifyChangeList");
//		}
//		
//		return congTyKinhDoanhList;
//	}
//
//	public List<CongTyKinhDoanh> getCongTyKinhDoanhListAndNull(NhomCuaHoi nhomCuaHoi) {
//		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
//		congTyKinhDoanhList.add(null);
//		if (nhomCuaHoi != null) {
//			congTyKinhDoanhList.addAll(find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
//				.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(nhomCuaHoi)).fetch());
//		}
//		return congTyKinhDoanhList;
//	}
//	
	@Command
	public void clickTabNhom(@BindingParam("nhom") NhomCuaHoi nhom) {
		setNhomCuaHoi(nhom);
		BindUtils.postNotifyChange(null, null, this, "nhomCuaHoi");
		BindUtils.postNotifyChange(null, null, this, "targetQuery");
	}
//	
//	@Command
//	public void clickTabNhom(@BindingParam("nhom") NhomCuaHoi nhom, @BindingParam("tab") Tabbox tab,
//			@BindingParam("tabpanels") Tabpanels tabpanels, @BindingParam("index") int index) {
//		List<CongTyKinhDoanh> congTyKinhDoanhList = getCongTyKinhDoanhs(nhom);
//
//		Tabbox tabClick = (Tabbox) tabpanels.getChildren().get(index).getChildren().get(0);
//		try {
//			
//			tabClick.setSelectedIndex(0);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		
//		if (congTyKinhDoanhList.size() > 0) {
//			setCongTyKinhDoanh(congTyKinhDoanhList.get(0));
//			BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanh");
//			BindUtils.postNotifyChange(null, null, this, "targetQuery");
//		}
//	}
//	
//	public CongTyKinhDoanh getCongTyKinhDoanhFirst() {
//		List<NhomCuaHoi> nhomCuaHoiList = getNhomCuaHois();
//		if (nhomCuaHoiList.size() > 0) {
//			List<CongTyKinhDoanh> congTyKinhDoanhList = getCongTyKinhDoanhs(nhomCuaHoiList.get(0));
//			if (congTyKinhDoanhList != null && congTyKinhDoanhList.size() > 0) {
//				return congTyKinhDoanhList.get(0);
//			}
//		}
//		return null;
//	}
//	
//	@Command
//	public void search(@BindingParam("notify") final Object beanObject,
//		@BindingParam("attr") @Default(value = "*") final String attrs) {
//		NhomCuaHoi nhomCuaHoiSearch = (NhomCuaHoi) MapUtils.getObject(getArg(), "nhomCuaHoiSearch");
//		CongTyKinhDoanh congTyKinhDoanhSearch = (CongTyKinhDoanh) MapUtils.getObject(getArg(), "congTyKinhDoanhSearch");
//		
//		if (nhomCuaHoiSearch == null && congTyKinhDoanhSearch == null) {
//			setCongTyKinhDoanh(getCongTyKinhDoanhFirst());
//			BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanh");
//		}
//		
//		for (final String field : attrs.split("\\|")) {
//			if (!field.isEmpty()) {
//				BindUtils.postNotifyChange(null, null, beanObject == null ? this : beanObject, field);
//			}
//		}		
//	}
	
}
