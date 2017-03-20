package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.PhanLoaiKhachThueTau;
import vn.toancauxanh.gg.model.QPhanLoaiKhachThueTau;
import vn.toancauxanh.service.BasicService;

public class PhanLoaiKhachThueTauService extends BasicService<PhanLoaiKhachThueTau> {
	
	public JPAQuery<PhanLoaiKhachThueTau> getTargetKhachThueTauQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");
		
		JPAQuery<PhanLoaiKhachThueTau> phanLoaiKhachThueTau = find(PhanLoaiKhachThueTau.class);
		
		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			phanLoaiKhachThueTau.where(QPhanLoaiKhachThueTau.phanLoaiKhachThueTau.ten.like("%" + tuKhoa + "%"));
		}
		
		if (trangThai != null && !"".equals(trangThai) && !trangThai.isEmpty()) {
			phanLoaiKhachThueTau.where(QPhanLoaiKhachThueTau.phanLoaiKhachThueTau.trangThai.eq(trangThai));
		}
		
		phanLoaiKhachThueTau.orderBy(QPhanLoaiKhachThueTau.phanLoaiKhachThueTau.ngaySua.desc());
		
		return phanLoaiKhachThueTau;
	}
	
}
