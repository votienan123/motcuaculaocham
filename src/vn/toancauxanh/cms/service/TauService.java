package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CongTyKinhDoanh;
import vn.toancauxanh.gg.model.QCongTyKinhDoanh;
import vn.toancauxanh.gg.model.QTau;
import vn.toancauxanh.gg.model.Tau;
import vn.toancauxanh.service.BasicService;

public class TauService extends BasicService<Tau> {

	public JPAQuery<Tau> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "").trim();
		CongTyKinhDoanh congTyKinhDoanh = (CongTyKinhDoanh) MapUtils.getObject(getArg(), "congTyKinhDoanh");
		
		JPAQuery<Tau> tauQuery = find(Tau.class);
		
		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			tauQuery.where(QTau.tau.ten.like("%" + tuKhoa + "%"));	
		}
		
		if (trangThai != null && !"".equals(trangThai) && !trangThai.isEmpty()) {
			tauQuery.where(QTau.tau.trangThai.eq(trangThai));
		}
		
		if (congTyKinhDoanh != null && !congTyKinhDoanh.noId()) {
			tauQuery.where(QTau.tau.congTyKinhDoanh.eq(congTyKinhDoanh));
		}
		
		tauQuery.orderBy(QTau.tau.ngaySua.desc());
		
		return tauQuery;
	}
	
	public List<CongTyKinhDoanh> getCongTyKinhDoanhListAndNull() {
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		congTyKinhDoanhList.add(null);
		congTyKinhDoanhList.addAll(find(CongTyKinhDoanh.class)
				.where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG)).fetch());
		return congTyKinhDoanhList;
	}
	
}
