package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.HinhThucXuLy;
import vn.toancauxanh.gg.model.QHinhThucXuLy;
import vn.toancauxanh.gg.model.enums.HinhThucXuLyEnum;
import vn.toancauxanh.gg.model.enums.LoaiDoiTuong;
import vn.toancauxanh.gg.model.enums.LoaiHinhThucXuLy;
import vn.toancauxanh.service.BasicService;

public class HinhThucXuLyService extends BasicService<HinhThucXuLy>{
	
	@Init
	public void init() {
		List<HinhThucXuLy> list = find(HinhThucXuLy.class)
				.where(QHinhThucXuLy.hinhThucXuLy.trangThai.ne(core().TT_DA_XOA))
				.fetch();
		if (list == null || list.isEmpty()) {
			bootstrapHinhThucXuLy();
		}
	}
	
	private LoaiHinhThucXuLy selectedLoaiHTXL;
	private LoaiDoiTuong selectedLoaiDoiTuong;
		
	public LoaiHinhThucXuLy getSelectedLoaiHTXL() {
		return selectedLoaiHTXL;
	}

	public void setSelectedLoaiHTXL(LoaiHinhThucXuLy selectedLoaiHTXL) {
		this.selectedLoaiHTXL = selectedLoaiHTXL;
	}

	public LoaiDoiTuong getSelectedLoaiDoiTuong() {
		return selectedLoaiDoiTuong;
	}

	public void setSelectedLoaiDoiTuong(LoaiDoiTuong selectedLoaiDoiTuong) {
		this.selectedLoaiDoiTuong = selectedLoaiDoiTuong;
	}
	
	public List<HinhThucXuLy> getListHinhThucXuLy() {
		List<HinhThucXuLy> list = new ArrayList<HinhThucXuLy>();
		list = find(HinhThucXuLy.class)
				.where(QHinhThucXuLy.hinhThucXuLy.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QHinhThucXuLy.hinhThucXuLy.ten.asc())
				.fetch();
		return list;
	}
	
	public List<HinhThucXuLy> getListHinhThucXuLyNguoiNghien() {
		List<HinhThucXuLy> list = new ArrayList<HinhThucXuLy>();
		list = find(HinhThucXuLy.class)
				.where(QHinhThucXuLy.hinhThucXuLy.trangThai.eq(core().TT_AP_DUNG))
				.where(QHinhThucXuLy.hinhThucXuLy.loaiDoiTuong.eq(LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY)
						.or(QHinhThucXuLy.hinhThucXuLy.loaiDoiTuong.eq(LoaiDoiTuong.TUY_TRUONG_HOP)))
				.orderBy(QHinhThucXuLy.hinhThucXuLy.ten.asc())
				.fetch();
		return list;
	}
	
	public List<HinhThucXuLy> getListHinhThucXuLyTraiPhepMaTuy() {
		List<HinhThucXuLy> list = new ArrayList<HinhThucXuLy>();
		list = find(HinhThucXuLy.class)
				.where(QHinhThucXuLy.hinhThucXuLy.trangThai.eq(core().TT_AP_DUNG))
				.where(QHinhThucXuLy.hinhThucXuLy.loaiDoiTuong.eq(LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY)
						.or(QHinhThucXuLy.hinhThucXuLy.loaiDoiTuong.eq(LoaiDoiTuong.TUY_TRUONG_HOP)))
				.orderBy(QHinhThucXuLy.hinhThucXuLy.ten.asc())
				.fetch();
		return list;
	}
	
	public List<HinhThucXuLy> getListHinhThucXuLyCaiNghienTaiCongDong() {
		List<HinhThucXuLy> list = new ArrayList<HinhThucXuLy>();
		list = find(HinhThucXuLy.class)
				.where(QHinhThucXuLy.hinhThucXuLy.trangThai.eq(core().TT_AP_DUNG))
				.where(QHinhThucXuLy.hinhThucXuLy.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.CAI_NGHIEN_BAT_BUOC_TAI_CONG_DONG)
						.or(QHinhThucXuLy.hinhThucXuLy.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.CAI_NGHIEN_TU_NGUYEN_TAI_GIA_DINH))
						.or(QHinhThucXuLy.hinhThucXuLy.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.CAI_NGHIEN_TU_NGUYEN_TAI_CONG_DONG)))
				.orderBy(QHinhThucXuLy.hinhThucXuLy.ten.asc())
				.fetch();
		return list;
	}
	
	public List<HinhThucXuLy> getListHinhThucXuLyGiamGiu() {
		List<HinhThucXuLy> list = new ArrayList<HinhThucXuLy>();
		list = find(HinhThucXuLy.class)
				.where(QHinhThucXuLy.hinhThucXuLy.trangThai.eq(core().TT_AP_DUNG))
				.where(QHinhThucXuLy.hinhThucXuLy.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.BI_XU_LY_HINH_SU_O_NHA_TAM_GIU)
						.or(QHinhThucXuLy.hinhThucXuLy.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.BI_XU_LY_HINH_SU_O_TRAI_TAM_GIAM)))
				.orderBy(QHinhThucXuLy.hinhThucXuLy.ten.asc())
				.fetch();
		return list;
	}
	
	public List<HinhThucXuLy> getListHinhThucXuLyGiaoDuc() {
		List<HinhThucXuLy> list = new ArrayList<HinhThucXuLy>();
		list = find(HinhThucXuLy.class)
				.where(QHinhThucXuLy.hinhThucXuLy.trangThai.eq(core().TT_AP_DUNG))
				.where(QHinhThucXuLy.hinhThucXuLy.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.DUA_VAO_CO_SO_GIAO_DUC)
						.or(QHinhThucXuLy.hinhThucXuLy.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.DUA_VAO_TRUONG_GIAO_DUONG)))
				.orderBy(QHinhThucXuLy.hinhThucXuLy.ten.asc())
				.fetch();
		return list;
	}
	
	public List<HinhThucXuLy> getListHinhThucXuLyCaiNghienTapTrung() {
		List<HinhThucXuLy> list = new ArrayList<HinhThucXuLy>();
		list = find(HinhThucXuLy.class)
				.where(QHinhThucXuLy.hinhThucXuLy.trangThai.eq(core().TT_AP_DUNG))
				.where(QHinhThucXuLy.hinhThucXuLy.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.TAP_TRUNG_CAI_NGHIEN_BAT_BUOC)
						.or(QHinhThucXuLy.hinhThucXuLy.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.TAP_TRUNG_CAI_NGHIEN_TU_NGUYEN)))
				.orderBy(QHinhThucXuLy.hinhThucXuLy.ten.asc())
				.fetch();
		return list;
	}

	public JPAQuery<HinhThucXuLy> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<HinhThucXuLy> hinhThuc = find(HinhThucXuLy.class)
				.where(QHinhThucXuLy.hinhThucXuLy.trangThai.ne(core().TT_DA_XOA));

		if (selectedLoaiHTXL != null) {
			hinhThuc.where(QHinhThucXuLy.hinhThucXuLy.loaiHinhThucXuLy.eq(selectedLoaiHTXL));
		}
		
		if (selectedLoaiDoiTuong != null) {
			hinhThuc.where(QHinhThucXuLy.hinhThucXuLy.loaiDoiTuong.eq(selectedLoaiDoiTuong));
		}
		
		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			hinhThuc.where(QHinhThucXuLy.hinhThucXuLy.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			hinhThuc.where(QHinhThucXuLy.hinhThucXuLy.trangThai.eq(trangThai));
		}
		hinhThuc.orderBy(QHinhThucXuLy.hinhThucXuLy.ngaySua.desc());
		return hinhThuc;
	}
	
	public void bootstrapHinhThucXuLy() {
		HinhThucXuLy hinhThuc = new HinhThucXuLy("Phạt tiền", LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH, LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY, true, HinhThucXuLyEnum.PHAT_TIEN);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Cảnh cáo", LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH, LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY, true, HinhThucXuLyEnum.CANH_CAO);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Cai nghiện tự nguyện tại gia đình", LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH, LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY, true, HinhThucXuLyEnum.CAI_NGHIEN_TU_NGUYEN_TAI_GIA_DINH);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Cai nghiện tự nguyện tại cộng đồng", LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH, LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY, true, HinhThucXuLyEnum.CAI_NGHIEN_TU_NGUYEN_TAI_CONG_DONG);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Cai nghiện bắt buộc tại cộng đồng", LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH, LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY, true, HinhThucXuLyEnum.CAI_NGHIEN_BAT_BUOC_TAI_CONG_DONG);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Giáo dục tại xã, phường", LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH, LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY, true, HinhThucXuLyEnum.GIAO_DUC_TAI_XA_PHUONG);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Tập trung cai nghiện bắt buộc", LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH, LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY, true, HinhThucXuLyEnum.TAP_TRUNG_CAI_NGHIEN_BAT_BUOC);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Tập trung cai nghiện tự nguyện", LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH, LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY, true, HinhThucXuLyEnum.TAP_TRUNG_CAI_NGHIEN_TU_NGUYEN);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Đưa vào cơ sở giáo dục", LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH, LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY, true, HinhThucXuLyEnum.DUA_VAO_CO_SO_GIAO_DUC);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Đưa vào trường giáo dưỡng", LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH, LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY, true, HinhThucXuLyEnum.DUA_VAO_TRUONG_GIAO_DUONG);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Điều trị bằng Methadone", LoaiHinhThucXuLy.LICH_SU_KHAC, LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY, true, HinhThucXuLyEnum.DIEU_TRI_METHADONE);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Điều trị loạn thần (tâm thần)", LoaiHinhThucXuLy.LICH_SU_KHAC, LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY, true, HinhThucXuLyEnum.DIEU_TRI_LOAN_THAN);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Đang quản lý sau cai", LoaiHinhThucXuLy.LICH_SU_KHAC, LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY, true, HinhThucXuLyEnum.DANG_QUAN_LY_SAU_CAI);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Bị xử lý hình sự đang ở Nhà tạm giữ", LoaiHinhThucXuLy.TIEN_AN_TIEN_SU, LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY, true, HinhThucXuLyEnum.BI_XU_LY_HINH_SU_O_NHA_TAM_GIU);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Bị xử lý hình sự đang ở Trại tạm giam", LoaiHinhThucXuLy.TIEN_AN_TIEN_SU, LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY, true, HinhThucXuLyEnum.BI_XU_LY_HINH_SU_O_TRAI_TAM_GIAM);
		hinhThuc.saveNotShowNotification();
		hinhThuc = new HinhThucXuLy("Bị xử lý hình sự đang được tại ngoại", LoaiHinhThucXuLy.TIEN_AN_TIEN_SU, LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY, true, HinhThucXuLyEnum.BI_XU_LY_HINH_SU_DANG_DUOC_TAI_NGOAI);
		hinhThuc.saveNotShowNotification();
	}
}
