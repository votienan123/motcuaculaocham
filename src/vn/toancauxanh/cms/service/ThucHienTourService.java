package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CongTyKinhDoanh;
import vn.toancauxanh.gg.model.DatVe;
import vn.toancauxanh.gg.model.NhomCuaHoi;
import vn.toancauxanh.gg.model.PhanLoaiTour;
import vn.toancauxanh.gg.model.QCongTyKinhDoanh;
import vn.toancauxanh.gg.model.QDatVe;
import vn.toancauxanh.gg.model.QNhomCuaHoi;
import vn.toancauxanh.gg.model.QPhanLoaiTour;
import vn.toancauxanh.gg.model.Tour;
import vn.toancauxanh.service.BasicService;

public class ThucHienTourService  extends BasicService<Tour> {
	private CongTyKinhDoanh congTyKinhDoanh;
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
	
	public CongTyKinhDoanh getCongTyKinhDoanh() {
		if (congTyKinhDoanh == null) {
			return getCongTyKinhDoanhFirst();
		}
		return congTyKinhDoanh;
	}

	public void setCongTyKinhDoanh(CongTyKinhDoanh congTyKinhDoanh) {
		this.congTyKinhDoanh = congTyKinhDoanh;
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
	
	public List<NhomCuaHoi> getNhomCuaHois() {
		NhomCuaHoi nhomCuaHoiSearch = (NhomCuaHoi) MapUtils.getObject(getArg(), "nhomCuaHoi");
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		//PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
		
		JPAQuery<NhomCuaHoi> query = find(NhomCuaHoi.class).where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG));
		
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
		query.orderBy(QNhomCuaHoi.nhomCuaHoi.ngaySua.desc());
		nhomCuaHoiList.addAll(query.fetch());
		
		nhomCuaHoiNotifyChangeList = new ArrayList<NhomCuaHoi>();
		for (NhomCuaHoi nhom : nhomCuaHoiList) {
			nhomCuaHoiNotifyChangeList.add(nhom);
		}
		
		for (NhomCuaHoi nhom : nhomCuaHoiNotifyChangeList) {
			nhom.setTuNgayThucHienTour(getTuNgay());
			nhom.setDenNgayThucHienTour(getDenNgay());
			nhom.setTuKhoa(tuKhoa);
			//nhom.setPhanLoaiTour(phanLoaiTour);
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
	
	public List<CongTyKinhDoanh> getCongTyKinhDoanhs(NhomCuaHoi nhomCuaHoi) {
		
		CongTyKinhDoanh congTyKinhDoanhSearch = (CongTyKinhDoanh) MapUtils.getObject(getArg(), "congTyKinhDoanhSearch");
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		JPAQuery<CongTyKinhDoanh> query = find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
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
	
	public JPAQuery<CongTyKinhDoanh> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		Calendar cal = Calendar.getInstance();		
		
		JPAQuery<DatVe> datVeQuery = find(DatVe.class);

		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			datVeQuery.where(QDatVe.datVe.hoVaTen.like("%" + tuKhoa + "%"));
		}

		if (getCongTyKinhDoanh() != null && !getCongTyKinhDoanh().noId()) {
			datVeQuery.where(QDatVe.datVe.congTyKinhDoanh.eq(getCongTyKinhDoanh()));
		}
		
		if (getTuNgay() != null) {
			cal.setTime(getTuNgay());
			
			datVeQuery.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1)
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH)))));
		}
		datVeQuery.orderBy(QDatVe.datVe.ngaySua.desc());
		JPAQuery<CongTyKinhDoanh> congTyQuery = datVeQuery.select(QDatVe.datVe.congTyKinhDoanh).distinct();
		
		return congTyQuery;
	}
	
	@Command
	public void search(@BindingParam("notify") final Object beanObject,
			@BindingParam("attr") @Default(value = "*") final String attrs) {
		CongTyKinhDoanh congTyKinhDoanhSearch = (CongTyKinhDoanh) MapUtils.getObject(getArg(), "congTyKinhDoanhSearch");

		if (congTyKinhDoanhSearch == null) {
			setCongTyKinhDoanh(getCongTyKinhDoanhFirst());
			BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanh");
		}

		for (final String field : attrs.split("\\|")) {
			if (!field.isEmpty()) {
				BindUtils.postNotifyChange(null, null, beanObject == null ? this : beanObject, field);
			}
		}
	}
	
	public List<NhomCuaHoi> getNhomCuaHoiListAndNull() {
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
		nhomCuaHoiList.add(null);
		nhomCuaHoiList.addAll(find(NhomCuaHoi.class).where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG)).fetch());
		return nhomCuaHoiList;
	}
	
	public List<CongTyKinhDoanh> getCongTyKinhDoanhListAndNull(NhomCuaHoi nhomCuaHoi) {
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		congTyKinhDoanhList.add(null);
		if (nhomCuaHoi != null) {
			congTyKinhDoanhList.addAll(find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
				.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(nhomCuaHoi)).fetch());
		}
		return congTyKinhDoanhList;
	}
	
	public List<PhanLoaiTour> getPhanLoaiTourListAndNull() {
		List<PhanLoaiTour> phanLoaiTourList = new ArrayList<PhanLoaiTour>();
		phanLoaiTourList.add(null);
		phanLoaiTourList.addAll(find(PhanLoaiTour.class).where(QPhanLoaiTour.phanLoaiTour.trangThai.eq(core().TT_AP_DUNG)).fetch());

		return phanLoaiTourList;
	}
	
	@Command
	public void clickTabNhom(@BindingParam("nhom") NhomCuaHoi nhom, @BindingParam("tab") Tabbox tab,
			@BindingParam("tabpanels") Tabpanels tabpanels, @BindingParam("index") int index) {
		List<CongTyKinhDoanh> congTyKinhDoanhList = getCongTyKinhDoanhs(nhom);
		Tabpanel tabPanel = (Tabpanel) tabpanels.getChildren().get(index);
		
		if(tabPanel.isSelected()) {
			System.out.println("nhom " + nhom.getTen());
			System.out.println("tabPanel " + tabPanel);
		}
		
		if (congTyKinhDoanhList.size() > 0) {
			setCongTyKinhDoanh(congTyKinhDoanhList.get(0));
			BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanh");
			BindUtils.postNotifyChange(null, null, this, "targetQuery");
		}
	}
	
	@Command
	public void clickTabCongTy(@BindingParam("congTy") CongTyKinhDoanh congTy) {
		setCongTyKinhDoanh(congTy);
		BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanh");
		BindUtils.postNotifyChange(null, null, this, "targetQuery");
	}
}
