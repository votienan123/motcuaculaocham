package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.PhanLoaiTour;
import vn.toancauxanh.gg.model.QPhanLoaiTour;
import vn.toancauxanh.service.BasicService;

public class PhanLoaiTourService extends BasicService<PhanLoaiTour>{

	public JPAQuery<PhanLoaiTour> getTargetTourQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");
		
		JPAQuery<PhanLoaiTour> phanLoaiTour = find(PhanLoaiTour.class);
		
		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			phanLoaiTour.where(QPhanLoaiTour.phanLoaiTour.ten.like("%" + tuKhoa + "%"));
		}
		
		if (trangThai != null && !"".equals(trangThai) && !trangThai.isEmpty()) {
			phanLoaiTour.where(QPhanLoaiTour.phanLoaiTour.trangThai.eq(trangThai));
		}
		
		phanLoaiTour.orderBy(QPhanLoaiTour.phanLoaiTour.ngaySua.desc());
		
		return phanLoaiTour;
	}
	
}
