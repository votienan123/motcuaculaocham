package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.MaTuySuDung;
import vn.toancauxanh.gg.model.QMaTuySuDung;
import vn.toancauxanh.service.BasicService;

public class MaTuySuDungService extends BasicService<MaTuySuDung>{
		
	@Init
	public void init() {
		List<MaTuySuDung> list = find(MaTuySuDung.class)
				.where(QMaTuySuDung.maTuySuDung.trangThai.ne(core().TT_DA_XOA))
				.fetch();
		if (list == null || list.isEmpty()) {
			bootstrapMaTuySuDung();
		}
	}
	
	public List<MaTuySuDung> getListMaTuySuDung() {
		List<MaTuySuDung> list = new ArrayList<MaTuySuDung>();
		list = find(MaTuySuDung.class)
				.where(QMaTuySuDung.maTuySuDung.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QMaTuySuDung.maTuySuDung.ten.asc())
				.fetch();
		return list;
	}
	
	public JPAQuery<MaTuySuDung> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<MaTuySuDung> phongBan = find(MaTuySuDung.class)
				.where(QMaTuySuDung.maTuySuDung.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			phongBan.where(QMaTuySuDung.maTuySuDung.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			phongBan.where(QMaTuySuDung.maTuySuDung.trangThai.eq(trangThai));
		}
		phongBan.orderBy(QMaTuySuDung.maTuySuDung.ngaySua.desc());
		return phongBan;
	}
	
	public void bootstrapMaTuySuDung() {
		MaTuySuDung maTuy = new MaTuySuDung("Thuốc phiện");
		maTuy.saveNotShowNotification();
		maTuy = new MaTuySuDung("Cần sa");
		maTuy.saveNotShowNotification();
		maTuy = new MaTuySuDung("Cocain");
		maTuy.saveNotShowNotification();
		maTuy = new MaTuySuDung("Heroin");
		maTuy.saveNotShowNotification();
		maTuy = new MaTuySuDung("Ma túy tổng hợp");
		maTuy.saveNotShowNotification();
		maTuy = new MaTuySuDung("Cỏ Mỹ");
		maTuy.saveNotShowNotification();
		maTuy = new MaTuySuDung("Ma túy khác");
		maTuy.saveNotShowNotification();
		maTuy = new MaTuySuDung("Sử dụng nhiều loại ma túy");
		maTuy.saveNotShowNotification();
	}
}
