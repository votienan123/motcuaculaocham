package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QThanhPhanDoiTuong;
import vn.toancauxanh.gg.model.ThanhPhanDoiTuong;
import vn.toancauxanh.service.BasicService;

public class ThanhPhanDoiTuongService extends BasicService<ThanhPhanDoiTuong>{
		
	@Init
	public void init() {
		List<ThanhPhanDoiTuong> list = find(ThanhPhanDoiTuong.class)
				.where(QThanhPhanDoiTuong.thanhPhanDoiTuong.trangThai.ne(core().TT_DA_XOA))
				.fetch();
		if (list == null || list.isEmpty()) {
			bootstrapThanhPhanDoiTuong();
		}
	}
	
	public List<ThanhPhanDoiTuong> getListThanhPhanDoiTuong() {
		List<ThanhPhanDoiTuong> list = new ArrayList<ThanhPhanDoiTuong>();
		list = find(ThanhPhanDoiTuong.class)
				.where(QThanhPhanDoiTuong.thanhPhanDoiTuong.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThanhPhanDoiTuong.thanhPhanDoiTuong.ten.asc())
				.fetch();
		return list;
	}
	
	public JPAQuery<ThanhPhanDoiTuong> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<ThanhPhanDoiTuong> phongBan = find(ThanhPhanDoiTuong.class)
				.where(QThanhPhanDoiTuong.thanhPhanDoiTuong.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			phongBan.where(QThanhPhanDoiTuong.thanhPhanDoiTuong.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			phongBan.where(QThanhPhanDoiTuong.thanhPhanDoiTuong.trangThai.eq(trangThai));
		}
		phongBan.orderBy(QThanhPhanDoiTuong.thanhPhanDoiTuong.ngaySua.desc());
		return phongBan;
	}
	
	public void bootstrapThanhPhanDoiTuong() {
		ThanhPhanDoiTuong thanhPhan = new ThanhPhanDoiTuong("Học sinh");
		thanhPhan.saveNotShowNotification();
		thanhPhan = new ThanhPhanDoiTuong("Sinh viên");
		thanhPhan.saveNotShowNotification();
		thanhPhan = new ThanhPhanDoiTuong("Cán bộ");
		thanhPhan.saveNotShowNotification();
		thanhPhan = new ThanhPhanDoiTuong("Công nhân");
		thanhPhan.saveNotShowNotification();
		thanhPhan = new ThanhPhanDoiTuong("Nông dân");
		thanhPhan.saveNotShowNotification();
		thanhPhan = new ThanhPhanDoiTuong("Khác");
		thanhPhan.saveNotShowNotification();
	}
}
