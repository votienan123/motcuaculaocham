package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.NhomNguoiDung;
import vn.toancauxanh.gg.model.QNhomNguoiDung;
import vn.toancauxanh.service.BasicService;

public class NhomNguoiDungService extends BasicService<NhomNguoiDung> {
	
	public JPAQuery<NhomNguoiDung> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");
		
		JPAQuery<NhomNguoiDung> nhomNguoiDungQuery = find(NhomNguoiDung.class);
		
		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			nhomNguoiDungQuery.where(QNhomNguoiDung.nhomNguoiDung.ten.like("%" + tuKhoa + "%"));
		}
		
		if (trangThai != null && !"".equals(trangThai) && !trangThai.isEmpty()) {
			nhomNguoiDungQuery.where(QNhomNguoiDung.nhomNguoiDung.trangThai.eq(trangThai));
		}
		
		nhomNguoiDungQuery.orderBy(QNhomNguoiDung.nhomNguoiDung.ngaySua.desc());
		
		return nhomNguoiDungQuery;
	}
	
}
