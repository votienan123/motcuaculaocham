package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Init;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.CongTyKinhDoanh;
import vn.toancauxanh.gg.model.DatVe;
import vn.toancauxanh.gg.model.DieuTietNhom;
import vn.toancauxanh.gg.model.NhomCuaHoi;
import vn.toancauxanh.gg.model.QCongTyKinhDoanh;
import vn.toancauxanh.gg.model.QDatVe;
import vn.toancauxanh.gg.model.QDieuTietNhom;
import vn.toancauxanh.gg.model.QNhomCuaHoi;
import vn.toancauxanh.gg.model.QTour;
import vn.toancauxanh.gg.model.Tour;
import vn.toancauxanh.gg.model.enums.LoaiPhongVeLeEnum;
import vn.toancauxanh.gg.model.enums.TinhTrangVeEnum;
import vn.toancauxanh.gg.model.enums.TrangThaiDuyetVeEnum;

public class ThongKeCLCKhachHuyDatTourService extends ThongKeCLCService {

	Calendar calNgayDatVe = Calendar.getInstance();
	Calendar calNgayThucHienTour = Calendar.getInstance();

	@Init
	public void init() {
		setNgayDatVe(new Date());
		setNgayThucHienTour(new Date());
		BindUtils.postNotifyChange(null, null, this, "ngayDatVe");
		BindUtils.postNotifyChange(null, null, this, "ngayThucHienTour");
	}

	public Long getTongSoKhachHuyDatTour() {

		Long total = 0L;

		JPAQuery<DatVe> veHuyList = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.HUY_VE));

		if (veHuyList != null) {

			if (getNgayDatVe() != null) {

				calNgayDatVe.setTime(getNgayDatVe());
				veHuyList = veHuyList.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayDatVe.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayDatVe.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayDatVe.get(Calendar.DAY_OF_MONTH))));
				if (getNgayThucHienTour() != null) {

					calNgayThucHienTour.setTime(getNgayThucHienTour());
					veHuyList = veHuyList.where(QDatVe.datVe.ngayThucHienTour.year()
							.eq(calNgayThucHienTour.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHienTour.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
									.eq(calNgayThucHienTour.get(Calendar.DAY_OF_MONTH))));
				}
			} else if (getNgayDatVe() == null) {

				if (getNgayThucHienTour() != null) {

					calNgayThucHienTour.setTime(getNgayThucHienTour());
					veHuyList = veHuyList.where(QDatVe.datVe.ngayThucHienTour.year()
							.eq(calNgayThucHienTour.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHienTour.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
									.eq(calNgayThucHienTour.get(Calendar.DAY_OF_MONTH))));
				}
			}

			total = getSoLuongKhach(veHuyList);
		}

		return total;
	}

	public List<Map<String, Object>> getNhomCuaHoiHuyDatTour() {
		
		// Ve Dat list
		JPAQuery<DatVe> veDatList = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)));
		
		// Ve Huy List
		JPAQuery<DatVe> veHuyList = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.HUY_VE));

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		// Phong ve ban le
		JPAQuery<DatVe> vePhongBanLeDat = veDatList
				.where(QDatVe.datVe.loaiPhongBanVeLe.eq(LoaiPhongVeLeEnum.PHONG_VE_LE_GIAN_TIEP)
						.or(QDatVe.datVe.loaiPhongBanVeLe.eq(LoaiPhongVeLeEnum.PHONG_VE_LE_TRUC_TIEP)));

		JPAQuery<DatVe> vePhongBanLeHuy = veHuyList
				.where(QDatVe.datVe.loaiPhongBanVeLe.eq(LoaiPhongVeLeEnum.PHONG_VE_LE_GIAN_TIEP)
						.or(QDatVe.datVe.loaiPhongBanVeLe.eq(LoaiPhongVeLeEnum.PHONG_VE_LE_TRUC_TIEP)));

		vePhongBanLeDat = getVeTheoNgay(vePhongBanLeDat);
		vePhongBanLeHuy = getVeTheoNgay(vePhongBanLeHuy);

		Map<String, Object> mapChildPhongBanLe = new HashMap<String, Object>();

		Long soLuongVePhongBanLeDat = getSoLuongKhach(vePhongBanLeDat);
		Long soLuongVePhongBanLeHuy = getSoLuongKhach(vePhongBanLeHuy);
		
		mapChildPhongBanLe.put("nhom", "Phòng bán vé lẻ");
		mapChildPhongBanLe.put("tongSo", soLuongVePhongBanLeDat + soLuongVePhongBanLeHuy);
		mapChildPhongBanLe.put("soLuongKhachDatTour", soLuongVePhongBanLeDat);
		mapChildPhongBanLe.put("soLuongKhachHuyDatTour", soLuongVePhongBanLeHuy);
		Collections.addAll(mapList, mapChildPhongBanLe);

		// Nhom cua hoi
		JPAQuery<NhomCuaHoi> nhomCuaHoiList = find(NhomCuaHoi.class)
				.where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG));

		if (nhomCuaHoiList != null) {
			for (NhomCuaHoi i : nhomCuaHoiList.fetch()) {
				JPAQuery<DatVe> veNhomCuaHoiDat = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
						.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
								.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
						.where(QDatVe.datVe.nhomCuaHoi.eq(i));
				JPAQuery<DatVe> veNhomCuaHoiHuy = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
						.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.HUY_VE))
						.where(QDatVe.datVe.nhomCuaHoi.eq(i));
				
				Map<String, Object> mapChildNhomCuaHoi = new HashMap<String, Object>();

				veNhomCuaHoiDat = getVeTheoNgay(veNhomCuaHoiDat);
				veNhomCuaHoiHuy = getVeTheoNgay(veNhomCuaHoiHuy);
				Long soLuongVeNhomCuaHoiDat = getSoLuongKhach(veNhomCuaHoiDat);
				Long soLuongVeNhomCuaHoiHuy = getSoLuongKhach(veNhomCuaHoiHuy);

				mapChildNhomCuaHoi.put("nhom", i);
				mapChildNhomCuaHoi.put("tongSo", soLuongVeNhomCuaHoiDat + soLuongVeNhomCuaHoiHuy);
				mapChildNhomCuaHoi.put("soLuongKhachDatTour", soLuongVeNhomCuaHoiDat);
				mapChildNhomCuaHoi.put("soLuongKhachHuyDatTour", soLuongVeNhomCuaHoiHuy);
				Collections.addAll(mapList, mapChildNhomCuaHoi);
			}
		}

		return mapList;
	}

	public List<Map<String, Object>> getCongTyKinhDoanhCuaNhomThongKeHuy(NhomCuaHoi nhomCuaHoi) {

		JPAQuery<DatVe> veDatList = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)));

		JPAQuery<DatVe> veHuyList = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.HUY_VE));

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		if (nhomCuaHoi != null) {
			JPAQuery<CongTyKinhDoanh> congTyKinhDoanhList = find(CongTyKinhDoanh.class)
					.where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
					.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(nhomCuaHoi));

			if (congTyKinhDoanhList != null) {
				for (CongTyKinhDoanh i : congTyKinhDoanhList.fetch()) {

					Map<String, Object> mapChild = new HashMap<String, Object>();

					JPAQuery<DatVe> veCongTyDat = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
							.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
									.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
							.where(QDatVe.datVe.nhomCuaHoi.eq(nhomCuaHoi).and(QDatVe.datVe.congTyKinhDoanh.eq(i)));
					JPAQuery<DatVe> veCongTyHuy = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
							.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.HUY_VE))
							.where(QDatVe.datVe.nhomCuaHoi.eq(nhomCuaHoi).and(QDatVe.datVe.congTyKinhDoanh.eq(i)));

					veCongTyDat = getVeTheoNgay(veCongTyDat);
					veCongTyHuy = getVeTheoNgay(veCongTyHuy);

					Long soLuongVeCongTyDat = getSoLuongKhach(veCongTyDat);
					Long soLuongVeCongTyHuy = getSoLuongKhach(veCongTyHuy);

					mapChild.put("congty", i);
					mapChild.put("tongSo", soLuongVeCongTyDat + soLuongVeCongTyHuy);
					mapChild.put("soLuongKhachDatTour", soLuongVeCongTyDat);
					mapChild.put("soLuongKhachHuyDatTour", soLuongVeCongTyHuy);
					Collections.addAll(mapList, mapChild);
				}
			}
		}

		return mapList;
	}

	// Get all ve
	public JPAQuery<DatVe> getVeTheoNgay(JPAQuery<DatVe> ve) {

		if (ve != null) {
			if (getNgayDatVe() != null) {
				
				calNgayDatVe.setTime(getNgayDatVe());
				ve = ve.where(QDatVe.datVe.ngayKhachDat.year().eq(calNgayDatVe.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayKhachDat.month().eq(calNgayDatVe.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayKhachDat.dayOfMonth().eq(calNgayDatVe.get(Calendar.DAY_OF_MONTH))));
				if (getNgayThucHienTour() != null) {

					calNgayThucHienTour.setTime(getNgayThucHienTour());
					ve = ve.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayThucHienTour.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHienTour.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
									.eq(calNgayThucHienTour.get(Calendar.DAY_OF_MONTH))));
				}
			} else if (getNgayDatVe() == null) {

				if (getNgayThucHienTour() != null) {
					calNgayThucHienTour.setTime(getNgayThucHienTour());
					ve = ve.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayThucHienTour.get(Calendar.YEAR))
							.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHienTour.get(Calendar.MONTH) + 1))
							.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth()
									.eq(calNgayThucHienTour.get(Calendar.DAY_OF_MONTH))));
				}
			}
		}
		return ve;
	}

	public Long getSoLuongKhach(JPAQuery<DatVe> ve) {

		Long total = 0L;
		if (ve != null) {

			for (DatVe i : ve.fetch()) {
				total += Long.valueOf(i.getSoLuongNguoiLon()) + Long.valueOf(i.getSoLuongTreEmDuoi10Tuoi())
						+ Long.valueOf(i.getSoLuongTreEmDuoi5Tuoi());
			}
		}
		return total;
	}
}
