package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CongTyKinhDoanh;
import vn.toancauxanh.gg.model.NhomCuaHoi;
import vn.toancauxanh.gg.model.PhanLoaiCongTy;
import vn.toancauxanh.gg.model.QCongTyKinhDoanh;
import vn.toancauxanh.gg.model.QNhomCuaHoi;
import vn.toancauxanh.gg.model.QPhanLoaiCongTy;
import vn.toancauxanh.service.BasicService;

public class CongTyKinhDoanhService extends BasicService<CongTyKinhDoanh> {
	
	public JPAQuery<CongTyKinhDoanh> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "").trim();
		NhomCuaHoi nhomCuaHoi = (NhomCuaHoi) MapUtils.getObject(getArg(), "nhomCuaHoi");
		PhanLoaiCongTy phanLoaiCongTy = (PhanLoaiCongTy) MapUtils.getObject(getArg(), "phanLoaiCongTy");
		
		JPAQuery<CongTyKinhDoanh> congTyKinhDoanhQuery = find(CongTyKinhDoanh.class);
		
		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			congTyKinhDoanhQuery.where(QCongTyKinhDoanh.congTyKinhDoanh.ten.like("%" + tuKhoa + "%"));	
		}
		
		if (trangThai != null && !"".equals(trangThai) && !trangThai.isEmpty()) {
			congTyKinhDoanhQuery.where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(trangThai));
		}
		
		if (nhomCuaHoi != null && !nhomCuaHoi.noId()) {
			congTyKinhDoanhQuery.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(nhomCuaHoi));
		}
		
		if (phanLoaiCongTy != null && !phanLoaiCongTy.noId()) {
			congTyKinhDoanhQuery.where(QCongTyKinhDoanh.congTyKinhDoanh.phanLoaiCongTy.eq(phanLoaiCongTy));
		}
		
		congTyKinhDoanhQuery.orderBy(QCongTyKinhDoanh.congTyKinhDoanh.ngaySua.desc());
		
		return congTyKinhDoanhQuery;
	}
	
	public List<NhomCuaHoi> getNhomCuaHoiListAndNull() {
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
		nhomCuaHoiList.add(null);
		nhomCuaHoiList.addAll(find(NhomCuaHoi.class)
				.where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG)).fetch());
		return nhomCuaHoiList;
	}
	
	public List<PhanLoaiCongTy> getPhanLoaiCongTyListAndNull() {
		List<PhanLoaiCongTy> phanLoaiCongTyList = new ArrayList<PhanLoaiCongTy>();
		phanLoaiCongTyList.add(null);
		phanLoaiCongTyList.addAll(find(PhanLoaiCongTy.class)
				.where(QPhanLoaiCongTy.phanLoaiCongTy.trangThai.eq(core().TT_AP_DUNG)).fetch());
		return phanLoaiCongTyList;
	}
}
