package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.PhanLoaiCongTy;
import vn.toancauxanh.gg.model.QPhanLoaiCongTy;
import vn.toancauxanh.service.BasicService;

public class PhanLoaiCongTyService extends BasicService<PhanLoaiCongTy> {

	public JPAQuery<PhanLoaiCongTy> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "").trim();

		JPAQuery<PhanLoaiCongTy> phanLoaiCongTy = find(PhanLoaiCongTy.class);

		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			phanLoaiCongTy.where(QPhanLoaiCongTy.phanLoaiCongTy.ten.like("%" + tuKhoa + "%"));
		}

		if (trangThai != null && !"".equals(trangThai) && !trangThai.isEmpty()) {
			phanLoaiCongTy.where(QPhanLoaiCongTy.phanLoaiCongTy.trangThai.eq(trangThai));
		}

		phanLoaiCongTy.orderBy(QPhanLoaiCongTy.phanLoaiCongTy.ngaySua.desc());

		return phanLoaiCongTy;
	}
}
