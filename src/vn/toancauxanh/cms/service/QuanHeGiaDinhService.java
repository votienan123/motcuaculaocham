package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QQuanHeGiaDinh;
import vn.toancauxanh.gg.model.QuanHeGiaDinh;
import vn.toancauxanh.service.BasicService;

public class QuanHeGiaDinhService extends BasicService<QuanHeGiaDinh>{
		
	@Init
	public void init() {
		List<QuanHeGiaDinh> list = find(QuanHeGiaDinh.class)
				.where(QQuanHeGiaDinh.quanHeGiaDinh.trangThai.ne(core().TT_DA_XOA))
				.fetch();
		if (list == null || list.isEmpty()) {
			bootstrapQuanHeGiaDinh();
		}
	}
	
	public List<QuanHeGiaDinh> getListQuanHeGiaDinh() {
		List<QuanHeGiaDinh> list = new ArrayList<QuanHeGiaDinh>();
		list = find(QuanHeGiaDinh.class)
				.where(QQuanHeGiaDinh.quanHeGiaDinh.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QQuanHeGiaDinh.quanHeGiaDinh.ten.asc())
				.fetch();
		return list;
	}
	
	public JPAQuery<QuanHeGiaDinh> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<QuanHeGiaDinh> phongBan = find(QuanHeGiaDinh.class)
				.where(QQuanHeGiaDinh.quanHeGiaDinh.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			phongBan.where(QQuanHeGiaDinh.quanHeGiaDinh.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			phongBan.where(QQuanHeGiaDinh.quanHeGiaDinh.trangThai.eq(trangThai));
		}
		phongBan.orderBy(QQuanHeGiaDinh.quanHeGiaDinh.ngaySua.desc());
		return phongBan;
	}
	
	public void bootstrapQuanHeGiaDinh() {
		QuanHeGiaDinh quanHe = new QuanHeGiaDinh("Cha");
		quanHe.saveNotShowNotification();
		quanHe = new QuanHeGiaDinh("Mẹ");
		quanHe.saveNotShowNotification();
		quanHe = new QuanHeGiaDinh("Vợ");
		quanHe.saveNotShowNotification();
		quanHe = new QuanHeGiaDinh("Chồng");
		quanHe.saveNotShowNotification();
		quanHe = new QuanHeGiaDinh("Ông nội");
		quanHe.saveNotShowNotification();
		quanHe = new QuanHeGiaDinh("Bà nội");
		quanHe.saveNotShowNotification();
		quanHe = new QuanHeGiaDinh("Ông ngoại");
		quanHe.saveNotShowNotification();
		quanHe = new QuanHeGiaDinh("Bà ngoại");
		quanHe.saveNotShowNotification();
		quanHe = new QuanHeGiaDinh("Con");
		quanHe.saveNotShowNotification();
	}
}
