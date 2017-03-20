package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CongTyKinhDoanh;
import vn.toancauxanh.gg.model.DatVe;
import vn.toancauxanh.gg.model.NhomCuaHoi;
import vn.toancauxanh.gg.model.PhanLoaiTour;
import vn.toancauxanh.gg.model.QCongTyKinhDoanh;
import vn.toancauxanh.gg.model.QDatVe;
import vn.toancauxanh.gg.model.QNhomCuaHoi;
import vn.toancauxanh.gg.model.QPhanLoaiTour;
import vn.toancauxanh.gg.model.enums.LoaiPhongVeLeEnum;
import vn.toancauxanh.gg.model.enums.TinhTrangVeEnum;
import vn.toancauxanh.gg.model.enums.TrangThaiDuyetVeEnum;
import vn.toancauxanh.service.BasicService;

public class VeDoiDuyetService extends BasicService<DatVe> {

	public List<LoaiPhongVeLeEnum> getPhongVeLeListAndNull() {
		List<LoaiPhongVeLeEnum> phongVeLeList = new ArrayList<LoaiPhongVeLeEnum>();
		phongVeLeList.add(null);
		phongVeLeList.addAll(Arrays.asList(LoaiPhongVeLeEnum.values()));
		return phongVeLeList;
	}

	// Get list NhomCuaHoi
	public List<NhomCuaHoi> getNhomCuaHoiListAndNull() {
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
		nhomCuaHoiList.add(null);
		nhomCuaHoiList
				.addAll(find(NhomCuaHoi.class).where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG)).fetch());
		return nhomCuaHoiList;
	}

	// Get list CongTyKinhDoanh based on NhomCuaHoi
	public List<CongTyKinhDoanh> getCongTyKinhDoanhListAndNull(NhomCuaHoi nhomCuaHoi) {
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		congTyKinhDoanhList.add(null);
		if (nhomCuaHoi != null) {
			congTyKinhDoanhList.addAll(
					find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
							.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(nhomCuaHoi)).fetch());
		}
		return congTyKinhDoanhList;
	}

	// Get list PhanLoaiTour
	public List<PhanLoaiTour> getPhanLoaiTourListAndNull() {
		List<PhanLoaiTour> phanLoaiTourList = new ArrayList<PhanLoaiTour>();
		phanLoaiTourList.add(null);
		phanLoaiTourList.addAll(
				find(PhanLoaiTour.class).where(QPhanLoaiTour.phanLoaiTour.trangThai.eq(core().TT_AP_DUNG)).fetch());
		return phanLoaiTourList;
	}

	// Get list VeHuy
	public JPAQuery<DatVe> getTargetQuery() {

		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "").trim();
		PhanLoaiTour phanLoaiTour = (PhanLoaiTour) MapUtils.getObject(getArg(), "phanLoaiTour");
		NhomCuaHoi nhomCuaHoi = (NhomCuaHoi) MapUtils.getObject(getArg(), "nhomCuaHoi");
		CongTyKinhDoanh congTyKinhDoanh = (CongTyKinhDoanh) MapUtils.getObject(getArg(), "congTyKinhDoanh");

		JPAQuery<DatVe> datVe = find(DatVe.class)
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DANG_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)));

		if (tuKhoa != null && !"".equals(tuKhoa) && !tuKhoa.isEmpty()) {
			datVe.where(QDatVe.datVe.id.eq(Long.valueOf(tuKhoa)));
		}

		if (trangThai != null && !"".equals(trangThai) && !trangThai.isEmpty()) {
			datVe.where(QDatVe.datVe.trangThai.eq(trangThai));
		}

		if (phanLoaiTour != null && !phanLoaiTour.noId()) {
			datVe.where(QDatVe.datVe.phanLoaiTour.eq(phanLoaiTour));
		}
		
		if (nhomCuaHoi != null) {
			datVe.where(QDatVe.datVe.nhomCuaHoi.eq(nhomCuaHoi));
		}

		if (congTyKinhDoanh != null) {
			datVe.where(QDatVe.datVe.congTyKinhDoanh.eq(congTyKinhDoanh));
		}
		
		if (getTuNgay() != null && getDenNgay() != null) {
			Calendar calNgayKhachDat = Calendar.getInstance();
			calNgayKhachDat.setTime(getFixTuNgay());
			Calendar calNgayThucHien = Calendar.getInstance();
			calNgayThucHien.setTime(getFixDenNgay());
			datVe.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayKhachDat.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayKhachDat.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayKhachDat.get(Calendar.DAY_OF_MONTH))))
					.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayThucHien.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHien.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(calNgayThucHien.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgay() == null && getDenNgay() != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFixDenNgay());
			datVe.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		} else if (getTuNgay() != null && getDenNgay() == null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(getFixTuNgay());
			datVe.where(QDatVe.datVe.ngayKhachDat.year().eq(cal.get(Calendar.YEAR))
					.and(QDatVe.datVe.ngayKhachDat.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}

		datVe.orderBy(QDatVe.datVe.ngaySua.desc());

		return datVe;
	}

}
