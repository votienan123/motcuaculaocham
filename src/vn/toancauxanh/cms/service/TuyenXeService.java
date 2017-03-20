package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QTuyenXe;
import vn.toancauxanh.gg.model.TuyenXe;
import vn.toancauxanh.service.BasicService;

public class TuyenXeService extends BasicService<TuyenXe>{
		
	
	public List<TuyenXe> getListTuyenXe() {
		List<TuyenXe> list = new ArrayList<TuyenXe>();
		list = find(TuyenXe.class)
				.where(QTuyenXe.tuyenXe.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QTuyenXe.tuyenXe.ten.asc())
				.fetch();
		return list;
	}
	
	public JPAQuery<TuyenXe> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<TuyenXe> phongBan = find(TuyenXe.class)
				.where(QTuyenXe.tuyenXe.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			phongBan.where(QTuyenXe.tuyenXe.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			phongBan.where(QTuyenXe.tuyenXe.trangThai.eq(trangThai));
		}
		phongBan.orderBy(QTuyenXe.tuyenXe.ngaySua.desc());
		return phongBan;
	}
}
