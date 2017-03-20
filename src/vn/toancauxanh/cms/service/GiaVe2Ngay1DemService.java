package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.GiaVe2Ngay1Dem;
import vn.toancauxanh.gg.model.QGiaVe2Ngay1Dem;
import vn.toancauxanh.service.BasicService;

public class GiaVe2Ngay1DemService extends BasicService<GiaVe2Ngay1Dem>{

	public List<GiaVe2Ngay1Dem> getListGiaVe2Ngay1Dem() {
		List<GiaVe2Ngay1Dem> list = new ArrayList<GiaVe2Ngay1Dem>();
		list = find(GiaVe2Ngay1Dem.class)
				.where(QGiaVe2Ngay1Dem.giaVe2Ngay1Dem.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QGiaVe2Ngay1Dem.giaVe2Ngay1Dem.ten.asc())
				.fetch();
		return list;
	}
	
	public JPAQuery<GiaVe2Ngay1Dem> getTargetTourQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");
		
		JPAQuery<GiaVe2Ngay1Dem> phanLoaiTour = find(GiaVe2Ngay1Dem.class);
		
		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			phanLoaiTour.where(QGiaVe2Ngay1Dem.giaVe2Ngay1Dem.ten.like("%" + tuKhoa + "%"));
		}
		
		if (trangThai != null && !"".equals(trangThai) && !trangThai.isEmpty()) {
			phanLoaiTour.where(QGiaVe2Ngay1Dem.giaVe2Ngay1Dem.trangThai.eq(trangThai));
		}
		
		phanLoaiTour.orderBy(QGiaVe2Ngay1Dem.giaVe2Ngay1Dem.ngaySua.desc());
		
		return phanLoaiTour;
	}
	
}
