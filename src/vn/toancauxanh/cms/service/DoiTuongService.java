package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.DoiTuong;
import vn.toancauxanh.gg.model.DonViHanhChinh;
import vn.toancauxanh.gg.model.GioiTinh;
import vn.toancauxanh.gg.model.NganhNghe;
import vn.toancauxanh.gg.model.QDoiTuong;
import vn.toancauxanh.gg.model.ToDanPho;
import vn.toancauxanh.gg.model.TrinhDoHocVan;
import vn.toancauxanh.gg.model.enums.HinhThucXuLyEnum;
import vn.toancauxanh.gg.model.enums.LoaiDoiTuong;
import vn.toancauxanh.service.BasicService;

public class DoiTuongService extends BasicService<DoiTuong>{
	
	private GioiTinh selectedGioiTinh;
	private TrinhDoHocVan selectedTrinhDoHocVan;
	private NganhNghe selectedNgheNghiep;
	private Date ngaySinh;
	private DonViHanhChinh selectedTinhThanh;
	private DonViHanhChinh selectedQuanHuyen;
	private DonViHanhChinh selectedPhuongXa;
	private List<ToDanPho> selectedToDanPhos = new ArrayList<ToDanPho>();
	
	public GioiTinh getSelectedGioiTinh() {
		return selectedGioiTinh;
	}

	public void setSelectedGioiTinh(GioiTinh selectedGioiTinh) {
		this.selectedGioiTinh = selectedGioiTinh;
	}	

	public TrinhDoHocVan getSelectedTrinhDoHocVan() {
		return selectedTrinhDoHocVan;
	}

	public void setSelectedTrinhDoHocVan(TrinhDoHocVan selectedTrinhDoHocVan) {
		this.selectedTrinhDoHocVan = selectedTrinhDoHocVan;
	}

	public NganhNghe getSelectedNgheNghiep() {
		return selectedNgheNghiep;
	}

	public void setSelectedNgheNghiep(NganhNghe selectedNgheNghiep) {
		this.selectedNgheNghiep = selectedNgheNghiep;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public DonViHanhChinh getSelectedTinhThanh() {
		return selectedTinhThanh;
	}

	public void setSelectedTinhThanh(DonViHanhChinh selectedTinhThanh) {
		this.selectedTinhThanh = selectedTinhThanh;
	}

	@Override
	public DonViHanhChinh getSelectedQuanHuyen() {
		return selectedQuanHuyen;
	}

	@Override
	public void setSelectedQuanHuyen(DonViHanhChinh selectedQuanHuyen) {
		this.selectedQuanHuyen = selectedQuanHuyen;
	}

	public DonViHanhChinh getSelectedPhuongXa() {
		return selectedPhuongXa;
	}

	public void setSelectedPhuongXa(DonViHanhChinh selectedPhuongXa) {
		this.selectedPhuongXa = selectedPhuongXa;
	}

	public List<ToDanPho> getSelectedToDanPhos() {
		return selectedToDanPhos;
	}

	public void setSelectedToDanPhos(List<ToDanPho> selectedToDanPhos) {
		this.selectedToDanPhos = selectedToDanPhos;
	}

	public JPAQuery<DoiTuong> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> phongBan = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			phongBan.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		phongBan.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return phongBan;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiNghien() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where((QDoiTuong.doiTuong.thongTinViPhamHienHanh.isNotNull()
						.and(QDoiTuong.doiTuong.thongTinViPhamHienHanh
								.hinhThucXuLy.loaiDoiTuong.eq(LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY)))
						.or(QDoiTuong.doiTuong.nguoiNghien.eq(true)));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiSuDungTraiPhep() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where((QDoiTuong.doiTuong.thongTinViPhamHienHanh.isNotNull()
						.and(QDoiTuong.doiTuong
								.thongTinViPhamHienHanh.hinhThucXuLy.loaiDoiTuong
								.eq(LoaiDoiTuong.SU_DUNG_TRAI_PHEP_MA_TUY)))
						.or(QDoiTuong.doiTuong.nguoiSuDungTraiPhep.eq(true)));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiSauCai() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.isNotNull()
						.and(QDoiTuong.doiTuong
								.thongTinViPhamHienHanh
								.hinhThucXuLy
								.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.DANG_QUAN_LY_SAU_CAI))
						);

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiSauQuanLy() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.isNull());

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiNgoaiTinh() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.ne(core().getDonViHanhChinhs().getDonViDaNang()));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiTruyNaTruyTim() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(QDoiTuong.doiTuong.coQuyetDinhTruyNa.eq(true).or(QDoiTuong.doiTuong.coQuyetDinhTruyTim.eq(true)));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiCoTienAn() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(QDoiTuong.doiTuong.coTienAn.eq(true));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiCoTienSu() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(QDoiTuong.doiTuong.coTienSu.eq(true));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiCoDauHieuNghiVan() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(QDoiTuong.doiTuong.dauHieuNghiVanVPPL.isNotEmpty());

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiCoBieuHienLoanThan() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(QDoiTuong.doiTuong.bieuHienLoanThanKinh.isNotEmpty());

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiDangThuHuongChinhSach() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(QDoiTuong.doiTuong.coHuongThuChinhSach.eq(true));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiDangThuHuongChinhSachViPham() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(QDoiTuong.doiTuong.coHuongThuChinhSachViPham.eq(true));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public JPAQuery<DoiTuong> getTargetQueryNguoiCoDiaChiKhacNhau() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();

		JPAQuery<DoiTuong> q = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.trangThai.ne(core().TT_DA_XOA))
				.where(
						(
							(QDoiTuong.doiTuong.diaChiThuongTruTinh.isNull().and(QDoiTuong.doiTuong.diaChiHienNayTinh.isNotNull()))
							.or(QDoiTuong.doiTuong.diaChiThuongTruTinh.isNotNull().and(QDoiTuong.doiTuong.diaChiHienNayTinh.isNull()))
							.or(QDoiTuong.doiTuong.diaChiThuongTruTinh.ne(QDoiTuong.doiTuong.diaChiHienNayTinh))
						)
						.or(
							(QDoiTuong.doiTuong.diaChiThuongTruHuyen.isNull().and(QDoiTuong.doiTuong.diaChiHienNayHuyen.isNotNull()))
							.or(QDoiTuong.doiTuong.diaChiThuongTruHuyen.isNotNull().and(QDoiTuong.doiTuong.diaChiHienNayHuyen.isNull()))
							.or(QDoiTuong.doiTuong.diaChiThuongTruHuyen.ne(QDoiTuong.doiTuong.diaChiHienNayHuyen))
						)
						.or(
							(QDoiTuong.doiTuong.diaChiThuongTruXa.isNull().and(QDoiTuong.doiTuong.diaChiHienNayXa.isNotNull()))
							.or(QDoiTuong.doiTuong.diaChiThuongTruXa.isNotNull().and(QDoiTuong.doiTuong.diaChiHienNayXa.isNull()))
							.or(QDoiTuong.doiTuong.diaChiThuongTruXa.ne(QDoiTuong.doiTuong.diaChiHienNayXa))
						)
						.or(
							(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.isNull().and(QDoiTuong.doiTuong.diaChiHienNayToDanPho.isNotNull()))
							.or(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.isNotNull().and(QDoiTuong.doiTuong.diaChiHienNayToDanPho.isNull()))
							.or(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.ne(QDoiTuong.doiTuong.diaChiHienNayToDanPho))
						)
				
				);

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			q.where(QDoiTuong.doiTuong.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QDoiTuong.doiTuong.soCMND.contains(tuKhoa)));	
		}
		if (ngaySinh != null) {
			Calendar cal = Calendar.getInstance();
		    cal.setTime(ngaySinh);
			q.where(QDoiTuong.doiTuong.ngaySinh.year().eq(cal.get(Calendar.YEAR))
					.and(QDoiTuong.doiTuong.ngaySinh.month().eq(cal.get(Calendar.MONTH) + 1))
					.and(QDoiTuong.doiTuong.ngaySinh.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));
		}
		
		if (selectedGioiTinh != null) {
			q.where(QDoiTuong.doiTuong.gioiTinh.eq(selectedGioiTinh));
		}
		if (selectedNgheNghiep != null) {
			q.where(QDoiTuong.doiTuong.ngheNghiep.eq(selectedNgheNghiep));
		}
		if (selectedTrinhDoHocVan != null) {
			q.where(QDoiTuong.doiTuong.trinhDoVanHoa.eq(selectedTrinhDoHocVan));
		}
		if (selectedTinhThanh != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruTinh.eq(selectedTinhThanh)
					.or(QDoiTuong.doiTuong.diaChiHienNayTinh.eq(selectedTinhThanh)));
		}
		if (selectedQuanHuyen != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruHuyen.eq(selectedQuanHuyen)
					.or(QDoiTuong.doiTuong.diaChiHienNayHuyen.eq(selectedQuanHuyen)));
		}
		if (selectedPhuongXa != null) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruXa.eq(selectedPhuongXa)
					.or(QDoiTuong.doiTuong.diaChiHienNayXa.eq(selectedPhuongXa)));
		}
		if (selectedToDanPhos.size() > 0) {
			q.where(QDoiTuong.doiTuong.diaChiThuongTruToDanPho.in(selectedToDanPhos)
					.or(QDoiTuong.doiTuong.diaChiHienNayToDanPho.in(selectedToDanPhos)));
		}
		q.orderBy(QDoiTuong.doiTuong.ngaySua.desc());
		return q;
	}
	
	public String getTenToDanPho() {
		String ten = "";
		if (selectedToDanPhos != null && !selectedToDanPhos.isEmpty()) {
			for (int i = 0; i < selectedToDanPhos.size(); i++) {
				ten = ten.concat(selectedToDanPhos.get(i).getTenVietTat()).concat(";");				
			}
		}
		return ten;
	}
	
	@Command
	public void onShowTDP() {
		BindUtils.postNotifyChange(null, null, this, "tenToDanPho");
	}
}
