package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QQuanHeDoiTuongLienQuan;
import vn.toancauxanh.gg.model.QuanHeDoiTuongLienQuan;
import vn.toancauxanh.service.BasicService;

public class QuanHeDoiTuongLienQuanService extends BasicService<QuanHeDoiTuongLienQuan>{
		
	@Init
	public void init() {
		List<QuanHeDoiTuongLienQuan> list = find(QuanHeDoiTuongLienQuan.class)
				.where(QQuanHeDoiTuongLienQuan.quanHeDoiTuongLienQuan.trangThai.ne(core().TT_DA_XOA))
				.fetch();
		if (list == null || list.isEmpty()) {
			bootstrapQuanHeDoiTuongLienQuan();
		}
	}
	
	public List<QuanHeDoiTuongLienQuan> getListQuanHeDoiTuongLienQuan() {
		List<QuanHeDoiTuongLienQuan> list = new ArrayList<QuanHeDoiTuongLienQuan>();
		list = find(QuanHeDoiTuongLienQuan.class)
				.where(QQuanHeDoiTuongLienQuan.quanHeDoiTuongLienQuan.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QQuanHeDoiTuongLienQuan.quanHeDoiTuongLienQuan.ten.asc())
				.fetch();
		return list;
	}
	
	public JPAQuery<QuanHeDoiTuongLienQuan> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<QuanHeDoiTuongLienQuan> phongBan = find(QuanHeDoiTuongLienQuan.class)
				.where(QQuanHeDoiTuongLienQuan.quanHeDoiTuongLienQuan.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			phongBan.where(QQuanHeDoiTuongLienQuan.quanHeDoiTuongLienQuan.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			phongBan.where(QQuanHeDoiTuongLienQuan.quanHeDoiTuongLienQuan.trangThai.eq(trangThai));
		}
		phongBan.orderBy(QQuanHeDoiTuongLienQuan.quanHeDoiTuongLienQuan.ngaySua.desc());
		return phongBan;
	}
	
	public void bootstrapQuanHeDoiTuongLienQuan() {
		QuanHeDoiTuongLienQuan quanHe = new QuanHeDoiTuongLienQuan("Chủ mưu");
		quanHe.saveNotShowNotification();
		quanHe = new QuanHeDoiTuongLienQuan("Tòng phạm");
		quanHe.saveNotShowNotification();
	}
}
