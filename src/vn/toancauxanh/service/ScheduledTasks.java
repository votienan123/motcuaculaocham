package vn.toancauxanh.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import vn.toancauxanh.gg.model.DoiTuong;
import vn.toancauxanh.gg.model.QDoiTuong;
import vn.toancauxanh.gg.model.QThongTinViPham;
import vn.toancauxanh.gg.model.ThongTinViPham;
import vn.toancauxanh.gg.model.enums.HinhThucXuLyEnum;
import vn.toancauxanh.gg.model.enums.LoaiHinhThucXuLy;
import vn.toancauxanh.gg.model.enums.LoaiXuLy;
import vn.toancauxanh.model.Setting;

@Component
public class ScheduledTasks extends BasicService<Object> {
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	/*
	@Scheduled(cron = "0 1 0 * * *")
	public void updateHinhThucXuLyQuanLy() throws Exception {
		Setting setting = find(Setting.class)
				.fetchFirst();
		System.out.println("now: " + new Date());
		Calendar now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, - 10);
		Date mocThoiGianHetSauCai = now.getTime();	
		now = Calendar.getInstance();
		now.add(Calendar.DAY_OF_MONTH, - 15);
		Date mocThoiGianHetSauViPham = now.getTime();
		List<DoiTuong> listDoiTuongSauCai = new ArrayList<DoiTuong>();
		listDoiTuongSauCai = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.hinhThucXuLy.hinhThucXuLyEnum.eq(HinhThucXuLyEnum.DANG_QUAN_LY_SAU_CAI))
				.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.ngayViPham.before(mocThoiGianHetSauCai))
				.fetch();
		for (DoiTuong doiTuong : listDoiTuongSauCai) {
			doiTuong.getThongTinViPhamHienHanh().setLichSu(true);
			doiTuong.getThongTinViPhamHienHanh().setNgayKetThuc(new Date());
			doiTuong.getThongTinViPhamHienHanh().saveNotShowNotification();
			doiTuong.setThongTinViPhamHienHanh(null);
			doiTuong.setNguoiNghien(false);
			doiTuong.setNguoiSuDungTraiPhep(false);
			doiTuong.saveNotShowNotification();
			System.out.println("doi Tuong " + doiTuong.getHoVaTen() + " het han sau cai");
		}
		
		List<DoiTuong> listDoiTuongSauViPham = new ArrayList<DoiTuong>();
		listDoiTuongSauViPham = find(DoiTuong.class)
				.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.hinhThucXuLy.loaiHinhThucXuLy.eq(LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH))
				.where(QDoiTuong.doiTuong.thongTinViPhamHienHanh.ngayViPham.before(mocThoiGianHetSauViPham))
				.fetch();
		for (DoiTuong doiTuong : listDoiTuongSauViPham) {
			doiTuong.getThongTinViPhamHienHanh().setLichSu(true);
			doiTuong.getThongTinViPhamHienHanh().setNgayKetThuc(new Date());
			doiTuong.getThongTinViPhamHienHanh().saveNotShowNotification();
			doiTuong.setThongTinViPhamHienHanh(null);
			doiTuong.saveNotShowNotification();
			System.out.println("doi Tuong " + doiTuong.getHoVaTen() + " het han sau cai");
		}
	}
	
	@Scheduled(cron = "0 1 0 * * *")
	public void updateHinhThucXuLyQuanLyHetHan() throws Exception { 
		System.out.println("updateHinhThucXuLyQuanLyHetHan");
		List<ThongTinViPham> listHetHan = new ArrayList<ThongTinViPham>();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(new Date()); // sets calendar time/date
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    cal.set(Calendar.MINUTE, 1);// adds one hour
	    Date today = cal.getTime(); 
		listHetHan = find(ThongTinViPham.class)
				.where(QThongTinViPham.thongTinViPham.lichSu.eq(false))
				.where(QThongTinViPham.thongTinViPham.ngayKetThuc.before(today))
				.fetch();
		for (ThongTinViPham thongTin : listHetHan) {
			if (thongTin.getHinhThucXuLy().getLoaiHinhThucXuLy().equals(LoaiHinhThucXuLy.TIEN_AN_TIEN_SU)) {
				if (thongTin.getLoaiXuLy().equals(LoaiXuLy.XU_LY_HINH_SU)) {
					thongTin.getDoiTuong().setCoTienAn(true);
				} else {
					thongTin.getDoiTuong().setCoTienSu(true);
				}
			}
			thongTin.setLichSu(true);
			thongTin.saveNotShowNotification();
			ThongTinViPham hienHanh = find(ThongTinViPham.class)
					.where(QThongTinViPham.thongTinViPham.doiTuong.eq(thongTin.getDoiTuong()))
					.where(QThongTinViPham.thongTinViPham.lichSu.eq(false))
					.orderBy(QThongTinViPham.thongTinViPham.ngayViPham.desc())
					.fetchFirst();
			thongTin.getDoiTuong().setThongTinViPhamHienHanh(hienHanh);
			thongTin.getDoiTuong().saveNotShowNotification();
			System.out.println("thongTin: " + thongTin.getDoiTuong().getHoVaTen()+ "; " + thongTin.getNgayKetThuc());
		}
	}*/

}
