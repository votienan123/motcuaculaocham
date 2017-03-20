package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.NhomCuaHoi;
import vn.toancauxanh.gg.model.QNhomCuaHoi;
import vn.toancauxanh.service.BasicService;

public class NhomCuaHoiService extends BasicService<NhomCuaHoi> {

	public JPAQuery<NhomCuaHoi> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "").trim();
		
		JPAQuery<NhomCuaHoi> nhomCuaHoi = find(NhomCuaHoi.class);
		
		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			nhomCuaHoi.where(QNhomCuaHoi.nhomCuaHoi.ten.like("%" + tuKhoa + "%"));	
		}
		
		if (trangThai != null && !"".equals(trangThai) && !trangThai.isEmpty()) {
			nhomCuaHoi.where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(trangThai));
		}
		
		nhomCuaHoi.orderBy(QNhomCuaHoi.nhomCuaHoi.ngaySua.desc());
		
		return nhomCuaHoi;
	}
	
}
