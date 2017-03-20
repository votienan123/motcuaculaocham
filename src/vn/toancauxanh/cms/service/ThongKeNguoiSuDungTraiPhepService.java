package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.DoiTuong;
import vn.toancauxanh.gg.model.DonViHanhChinh;
import vn.toancauxanh.gg.model.GioiTinh;
import vn.toancauxanh.gg.model.HinhThucXuLy;
import vn.toancauxanh.gg.model.MaTuySuDung;
import vn.toancauxanh.gg.model.NganhNghe;
import vn.toancauxanh.gg.model.QDoiTuong;
import vn.toancauxanh.gg.model.ThanhPhanDoiTuong;
import vn.toancauxanh.gg.model.enums.LoaiDoiTuong;
import vn.toancauxanh.gg.model.enums.TinhTrangViecLam;
import vn.toancauxanh.gg.model.thongke.GioiTinhModel;
import vn.toancauxanh.gg.model.thongke.HinhThucXuLyModel;
import vn.toancauxanh.gg.model.thongke.MaTuySuDungModel;
import vn.toancauxanh.gg.model.thongke.NgheNghiepModel;
import vn.toancauxanh.gg.model.thongke.ThanhPhanGiaDinhModel;
import vn.toancauxanh.gg.model.thongke.ThongKePhanTichModel;

public class ThongKeNguoiSuDungTraiPhepService extends ThongKeService{


	private ThongKePhanTichModel soLieu = new ThongKePhanTichModel();
		
	public ThongKePhanTichModel getSoLieu() {
		return soLieu;
	}

	public void setSoLieu(ThongKePhanTichModel soLieu) {
		this.soLieu = soLieu;
	}
	
	@Command
	public void thongKeKetQua() {
		setThongKe(true);
		BindUtils.postNotifyChange(null, null, this, "thongKe");
		QDoiTuong qT = QDoiTuong.doiTuong;
		Calendar now = Calendar.getInstance();
		now.add(Calendar.YEAR, -16);
		Date old16 = now.getTime();	
		int tuoi16 = now.get(Calendar.YEAR);
		now = Calendar.getInstance();
		now.add(Calendar.YEAR, -18);
		Date old18 = now.getTime();
		int tuoi18 = now.get(Calendar.YEAR);
		now = Calendar.getInstance();
		now.add(Calendar.YEAR, -30);
		int tuoi30 = now.get(Calendar.YEAR);
		Date old30 = now.getTime();		
		DonViHanhChinh daNang = core().getDonViHanhChinhs().getDonViDaNang();	
		List<GioiTinhModel> listGioiTinh = new ArrayList<GioiTinhModel>();
		List<NgheNghiepModel> listNgheNghiep = new ArrayList<NgheNghiepModel>();
		List<ThanhPhanGiaDinhModel> listThanhPhanGiaDinh = new ArrayList<ThanhPhanGiaDinhModel>();
		List<MaTuySuDungModel> listMaTuySuDung = new ArrayList<MaTuySuDungModel>();
		List<HinhThucXuLyModel> listHinhThucXuLy = new ArrayList<HinhThucXuLyModel>();
		for (GioiTinh gioiTinh : core().getGioiTinhs().getListGioiTinh()) {
			GioiTinhModel gioiTinhModel = new GioiTinhModel();
			gioiTinhModel.setTen(gioiTinh.getTen());
			gioiTinhModel.setSoLieu(getQueryThongTin()
					.where(qT.gioiTinh.eq(gioiTinh))
					.fetchCount());
			listGioiTinh.add(gioiTinhModel);
		}
		for (NganhNghe nganhNghe : core().getNganhNghes().getListNganhNghe()) {
			NgheNghiepModel ngheNghiepModel = new NgheNghiepModel();
			ngheNghiepModel.setTen(nganhNghe.getTen());
			ngheNghiepModel.setSoLieu(getQueryThongTin()
					.where(qT.ngheNghiep.eq(nganhNghe))
					.fetchCount());
			listNgheNghiep.add(ngheNghiepModel);
		}
		for (ThanhPhanDoiTuong thanhPhan : core().getThanhPhanDoiTuongs().getListThanhPhanDoiTuong()) {
			ThanhPhanGiaDinhModel thanhPhanModel = new ThanhPhanGiaDinhModel();
			thanhPhanModel.setTen(thanhPhan.getTen());
			thanhPhanModel.setSoLieu(getQueryThongTin()
					.where(qT.thanhPhanDoiTuong.eq(thanhPhan))
					.fetchCount());
			listThanhPhanGiaDinh.add(thanhPhanModel);
		}
		for (MaTuySuDung maTuy : core().getMaTuySuDungs().getListMaTuySuDung()) {
			MaTuySuDungModel maTuySuDungModel = new MaTuySuDungModel();
			maTuySuDungModel.setTen(maTuy.getTen());
			maTuySuDungModel.setSoLieu(getQueryThongTin()
					.where(qT.maTuySuDungs.contains(maTuy))
					.fetchCount());
			listMaTuySuDung.add(maTuySuDungModel);
		}
		for (HinhThucXuLy hinhThuc : core().getHinhThucXuLys().getListHinhThucXuLyTraiPhepMaTuy()) {
			HinhThucXuLyModel hinhThucModel = new HinhThucXuLyModel();
			hinhThucModel.setTen(hinhThuc.getTen());
			hinhThucModel.setSoLieu(getQueryThongTin()
					.where(qT.thongTinViPhamHienHanh.hinhThucXuLy.eq(hinhThuc))
					.fetchCount());
			listHinhThucXuLy.add(hinhThucModel);
		}
		soLieu.setListGioiTinhModel(listGioiTinh);
		soLieu.setListNgheNghiepModel(listNgheNghiep);
		soLieu.setListThanhPhanGiaDinhModel(listThanhPhanGiaDinh);
		soLieu.setListMaTuySuDungModel(listMaTuySuDung);
		soLieu.setListHinhThucXuLyModel(listHinhThucXuLy);
		soLieu.setSoLieuDoTuoiDuoi16(getQueryThongTin()
				.where(qT.ngaySinh.after(old16).or(qT.namSinh.gt(tuoi16)))
				.fetchCount());
		soLieu.setSoLieuDoTuoiTren16Duoi18(getQueryThongTin()
				.where(qT.ngaySinh.between(old18, old16).or(qT.namSinh.between(tuoi18, tuoi16)))
				.fetchCount());
		soLieu.setSoLieuDoTuoiTren18Duoi30(getQueryThongTin()
				.where(qT.ngaySinh.between(old30, old18).or(qT.namSinh.between(tuoi30, tuoi18)))
				.fetchCount());
		soLieu.setSoLieuDoTuoiTren30(getQueryThongTin()
				.where(qT.ngaySinh.before(old30).or(qT.namSinh.lt(tuoi30)))
				.fetchCount());
		soLieu.setSoLieuCoViecLamOnDinh(getQueryThongTin()
				.where(qT.tinhTrangViecLam.eq(TinhTrangViecLam.CO_VIEC_ON_DINH))
				.fetchCount());
		soLieu.setSoLieuCoViecLamKhongOnDinh(getQueryThongTin()
				.where(qT.tinhTrangViecLam.eq(TinhTrangViecLam.CO_VIEC_KHONG_ON_DINH))
				.fetchCount());
		soLieu.setSoLieuKhongViecLam(getQueryThongTin()
				.where(qT.tinhTrangViecLam.eq(TinhTrangViecLam.KHONG_CO_VIEC))
				.fetchCount());
		soLieu.setSoLieuNguoiDaNangCoDinh(getQueryThongTin()
				.where(qT.diaChiThuongTruTinh.eq(qT.diaChiHienNayTinh)
						.and(qT.diaChiThuongTruHuyen.eq(qT.diaChiHienNayHuyen))
						.and(qT.diaChiThuongTruXa.eq(qT.diaChiHienNayXa)))
				.where(qT.diaChiThuongTruTinh.eq(daNang))
				.fetchCount());
		soLieu.setSoLieuNguoiDaNangKhongCoDinh(getQueryThongTin()
				.where(qT.diaChiThuongTruTinh.ne(qT.diaChiHienNayTinh)
						.or(qT.diaChiThuongTruHuyen.ne(qT.diaChiHienNayHuyen))
						.or(qT.diaChiThuongTruXa.ne(qT.diaChiHienNayXa)))
				.where(qT.diaChiThuongTruTinh.eq(daNang))
				.fetchCount());
		soLieu.setSoLieuNguoiNgoaiTinh(getQueryThongTin()
				.where(qT.diaChiThuongTruTinh.ne(daNang))
				.fetchCount());
		soLieu.setSoLieuTatCaNguoi(getQueryThongTin()
				.where(qT.diaChiThuongTruTinh.isNotNull())
				.fetchCount());
		BindUtils.postNotifyChange(null, null, this, "soLieu");
	}
	
	
	public JPAQuery<DoiTuong> getQueryThongTin() {
		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.hinhThucXuLy.loaiDoiTuong.eq(LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY)
						.or(QDoiTuong.doiTuong.thongTinViPhamHienHanh.hinhThucXuLy.loaiDoiTuong.eq(LoaiDoiTuong.TUY_TRUONG_HOP)))
				.where(QDoiTuong.doiTuong.nguoiSuDungTraiPhep.eq(true))
				.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.lichSu.eq(false));
		DonViHanhChinh daNang = core().getDonViHanhChinhs().getDonViDaNang();
		
		if (getTuNgayTK() != null && getDenNgayTK() !=null) {
			q.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.ngayViPham.between(getTuNgayTK(), getDenNgayTK()));
		} else if (getTuNgayTK() != null) {
			q.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.ngayViPham.after(getTuNgayTK()));
		} else if (getDenNgayTK() != null) {
			q.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.ngayViPham.before(getDenNgayTK()));
		}
		return q;
	}
}
