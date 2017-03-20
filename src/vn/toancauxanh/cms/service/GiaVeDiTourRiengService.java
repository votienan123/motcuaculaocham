package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.GiaVeDiTourRieng;
import vn.toancauxanh.gg.model.QGiaVeDiTourRieng;
import vn.toancauxanh.service.BasicService;

public class GiaVeDiTourRiengService extends BasicService<GiaVeDiTourRieng>{
		
	
	public List<GiaVeDiTourRieng> getListGiaVeDiTourRieng() {
		List<GiaVeDiTourRieng> list = new ArrayList<GiaVeDiTourRieng>();
		list = find(GiaVeDiTourRieng.class)
				.where(QGiaVeDiTourRieng.giaVeDiTourRieng.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QGiaVeDiTourRieng.giaVeDiTourRieng.ten.asc())
				.fetch();
		return list;
	}
	
	public JPAQuery<GiaVeDiTourRieng> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<GiaVeDiTourRieng> phongBan = find(GiaVeDiTourRieng.class)
				.where(QGiaVeDiTourRieng.giaVeDiTourRieng.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			phongBan.where(QGiaVeDiTourRieng.giaVeDiTourRieng.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			phongBan.where(QGiaVeDiTourRieng.giaVeDiTourRieng.trangThai.eq(trangThai));
		}
		phongBan.orderBy(QGiaVeDiTourRieng.giaVeDiTourRieng.ngaySua.desc());
		return phongBan;
	}
}
