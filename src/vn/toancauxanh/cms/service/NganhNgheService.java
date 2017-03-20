package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.NganhNghe;
import vn.toancauxanh.gg.model.QNganhNghe;
import vn.toancauxanh.service.BasicService;

public class NganhNgheService extends BasicService<NganhNghe>{
		
	@Init
	public void init() {
		List<NganhNghe> list = find(NganhNghe.class)
				.where(QNganhNghe.nganhNghe.trangThai.ne(core().TT_DA_XOA))
				.fetch();
		if (list == null || list.isEmpty()) {
			bootstrapNganhNghe();
		}
	}
	
	public List<NganhNghe> getListNganhNghe() {
		List<NganhNghe> list = new ArrayList<NganhNghe>();
		list = find(NganhNghe.class)
				.where(QNganhNghe.nganhNghe.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QNganhNghe.nganhNghe.ten.asc())
				.fetch();
		return list;
	}
	
	public List<NganhNghe> getListNganhNgheAndNull() {
		List<NganhNghe> list = new ArrayList<NganhNghe>();
		list.add(null);
		list.addAll(getListNganhNghe());
		return list;
	}
	
	public JPAQuery<NganhNghe> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<NganhNghe> phongBan = find(NganhNghe.class)
				.where(QNganhNghe.nganhNghe.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			phongBan.where(QNganhNghe.nganhNghe.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			phongBan.where(QNganhNghe.nganhNghe.trangThai.eq(trangThai));
		}
		phongBan.orderBy(QNganhNghe.nganhNghe.ngaySua.desc());
		return phongBan;
	}
	
	public void bootstrapNganhNghe() {
		NganhNghe nganhNghe = new NganhNghe("Cán bộ");
		nganhNghe.saveNotShowNotification();
		nganhNghe = new NganhNghe("Công nhân");
		nganhNghe.saveNotShowNotification();
		nganhNghe = new NganhNghe("Nông dân");
		nganhNghe.saveNotShowNotification();
		nganhNghe = new NganhNghe("Ngư dân");
		nganhNghe.saveNotShowNotification();
		nganhNghe = new NganhNghe("Học sinh");
		nganhNghe.saveNotShowNotification();
		nganhNghe = new NganhNghe("Sinh viên");
		nganhNghe.saveNotShowNotification();
		nganhNghe = new NganhNghe("Lái xe");
		nganhNghe.saveNotShowNotification();
		nganhNghe = new NganhNghe("Nhân viên công ty tư nhân");
		nganhNghe.saveNotShowNotification();
		nganhNghe = new NganhNghe("Lao động phổ thông");
		nganhNghe.saveNotShowNotification();
		nganhNghe = new NganhNghe("Không nghề nghiệp");
		nganhNghe.saveNotShowNotification();
	}
}
