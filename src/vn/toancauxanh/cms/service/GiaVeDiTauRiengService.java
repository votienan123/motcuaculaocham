package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.GiaVeDiTauRieng;
import vn.toancauxanh.gg.model.QGiaVeDiTauRieng;
import vn.toancauxanh.service.BasicService;

public class GiaVeDiTauRiengService extends BasicService<GiaVeDiTauRieng>{
		
	
	public List<GiaVeDiTauRieng> getListGiaVeDiTauRieng() {
		List<GiaVeDiTauRieng> list = new ArrayList<GiaVeDiTauRieng>();
		list = find(GiaVeDiTauRieng.class)
				.where(QGiaVeDiTauRieng.giaVeDiTauRieng.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QGiaVeDiTauRieng.giaVeDiTauRieng.ten.asc())
				.fetch();
		return list;
	}
	
	public JPAQuery<GiaVeDiTauRieng> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<GiaVeDiTauRieng> phongBan = find(GiaVeDiTauRieng.class)
				.where(QGiaVeDiTauRieng.giaVeDiTauRieng.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			phongBan.where(QGiaVeDiTauRieng.giaVeDiTauRieng.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			phongBan.where(QGiaVeDiTauRieng.giaVeDiTauRieng.trangThai.eq(trangThai));
		}
		phongBan.orderBy(QGiaVeDiTauRieng.giaVeDiTauRieng.ngaySua.desc());
		return phongBan;
	}
}
