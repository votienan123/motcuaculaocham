package vn.toancauxanh.cms.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.Transient;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.google.charts.ColumnChart;
import org.zkoss.google.charts.data.DataTable;
import org.zkoss.zul.Div;

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

public class BieuDoTourService extends ThongKeCLCService {

	//private int soNgay = 1;
	
    public Date getFixNgayThucHienTour() {
		Calendar tuNgayCal = Calendar.getInstance();
		tuNgayCal.setTime(getTuNgayThucHienTour());
		tuNgayCal.set(Calendar.HOUR_OF_DAY, 0);
		tuNgayCal.set(Calendar.MINUTE, 0);
		tuNgayCal.set(Calendar.SECOND, 0);
		tuNgayCal.set(Calendar.MILLISECOND, 0);
		return tuNgayCal.getTime();
	}
	
	public Date getFixDenNgayThucHienTour() {
		Calendar denNgayCal = Calendar.getInstance();
		denNgayCal.setTime(getDenNgayThucHienTour());
		denNgayCal.set(Calendar.HOUR_OF_DAY, 23);
		denNgayCal.set(Calendar.MINUTE, 59);
		denNgayCal.set(Calendar.SECOND, 59);
		denNgayCal.set(Calendar.MILLISECOND, 0);
		return denNgayCal.getTime();
	}

    @Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {				
				if (getDenNgayThucHienTour() != null && getFixDenNgayThucHienTour().before(getFixNgayThucHienTour())) {
					addInvalidMessage(ctx, "lblErrDenNgayTK","Đến ngày thực hiện tour không được lớn hơn từ ngày thực hiện tour.");
				}  
			}
		};
	}

	@Init
	public void init() {
		setTuNgayThucHienTour(new Date());
		BindUtils.postNotifyChange(null, null, this, "tuNgayThucHienTour");
	}

	public void showChart(Div chartArea) {

		chartArea.getChildren().clear();

		DataTable dataSheet = new DataTable();
		dataSheet.addStringColumn("Thực hiện tour");
		dataSheet.addNumberColumn("Thực hiên tour");
//		dataSheet.addColumn(ColumnType.STRING, ColumnRole.ANNOTATION);
		
		JPAQuery<NhomCuaHoi> nhomCuaHoiList = find(NhomCuaHoi.class)
				.where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG));

		if (nhomCuaHoiList != null) {
			for (NhomCuaHoi i : nhomCuaHoiList.fetch()) {

				/*JPAQuery<DatVe> veCuaHois = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
						.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
								.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
						.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
						.where(QDatVe.datVe.nhomCuaHoi.eq(i));

				veCuaHois = getVeTheoNgay(veCuaHois);
				Long soLuongKhachDat = getSoLuongKhach(veCuaHois);

				JPAQuery<DieuTietNhom> dieuTietNhoms = find(DieuTietNhom.class)
						.where(QDieuTietNhom.dieuTietNhom.trangThai.eq(core().TT_AP_DUNG))
						.where(QDieuTietNhom.dieuTietNhom.nhomCuaHoi.eq(i));

				dieuTietNhoms = getDieuTietTheoNgay(dieuTietNhoms);
				Long soLuongDieuTietNhom = getSoLuongKhachDieuTiet(dieuTietNhoms);*/

				JPAQuery<Tour> tours = find(Tour.class).where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
						.where(QTour.tour.nhomCuaHoi.eq(i));

				tours = getTourTheoNgay(tours);
				Long soLuongKhachThucHienTourThanhCong = getSoLuongKhachThucHienTourThanhCong(tours);
				dataSheet.addRow(i.getTen(),tyLePhanTramSoGheToiDa(Long.valueOf(i.getTongSoGhe()*getSoNgay()),soLuongKhachThucHienTourThanhCong));
			}
		}
		
		Map<String, Object> hAxis = new HashMap<>();
		Map<String, Object> vAxis = new HashMap<>();
		vAxis.put("maxValue", 100);
		vAxis.put("minValue", 0);
		vAxis.put("height", "75%");
		vAxis.put("title", "Tỷ lệ % khách thực hiện tour");

		ColumnChart chart = new ColumnChart();
		chart.setColors("#029a38");
		chart.setHeight("300px");
		chart.setTitle("Biểu đồ thống kê thực hiện tour các nhóm của hội");
		
		chart.setData(dataSheet);
		chart.setOption("hAxis", hAxis);
		chart.setOption("vAxis", vAxis);
		chartArea.appendChild(chart);
		
		if (nhomCuaHoiList != null) {
			for (NhomCuaHoi i : nhomCuaHoiList.fetch()) {
				
				DataTable dataChild = new DataTable();
				dataChild.addStringColumn("Thực hiện tour");
				dataChild.addNumberColumn("Thực hiên tour");
				
				JPAQuery<CongTyKinhDoanh> congTyKinhDoanhList = find(CongTyKinhDoanh.class)
						.where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
						.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(i));
				
				if (congTyKinhDoanhList != null) {
					for (CongTyKinhDoanh x : congTyKinhDoanhList.fetch()) {
					
						/*JPAQuery<DatVe> veCuaCongTy = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
								.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
										.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
								.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
								.where(QDatVe.datVe.nhomCuaHoi.eq(i).and(QDatVe.datVe.congTyKinhDoanh.eq(x)));
						
						veCuaCongTy = getVeTheoNgay(veCuaCongTy);
						Long soLuongKhachDatCongTy = getSoLuongKhach(veCuaCongTy);*/
						
						JPAQuery<Tour> dieuTietTourCongTy = find(Tour.class).where(QTour.tour.trangThai.eq(core().TT_AP_DUNG))
								.where(QTour.tour.nhomCuaHoi.eq(i).and(QTour.tour.congTyKinhDoanh.eq(x)));
						
						dieuTietTourCongTy = getTourTheoNgay(dieuTietTourCongTy);
//						Long soLuongDieuTietCongTy = getSoLuongKhachDieuTietTour(dieuTietTourCongTy);
						Long soLuongKhachCongTyThucHienTourThanhCong = getSoLuongKhachThucHienTourThanhCong(dieuTietTourCongTy);
						dataChild.addRow(x.getTen(),tyLePhanTramSoGheToiDa(Long.valueOf(x.getSoKhachToiDa()*getSoNgay()),soLuongKhachCongTyThucHienTourThanhCong));
					}
				}

				Map<String, Object> hAxiss = new HashMap<>();
				Map<String, Object> vAxiss = new HashMap<>();
				//hAxiss.put("title", "Biểu đồ khách thực hiện " + i.getTen().toString());
				vAxiss.put("maxValue", 100);
				vAxiss.put("minValue", 0);
				hAxiss.put("minValue", 0);
				vAxiss.put("title", "Tỷ lệ % khách thực hiện tour");

				ColumnChart chartChild = new ColumnChart();
				chartChild.setData(dataChild);
				chartChild.setTitle(i.getTen().toString());
				chartChild.setOption("hAxis", hAxiss);
				chartChild.setOption("vAxis", vAxiss);
				chartArea.appendChild(chartChild);
			}
		}
	}

	public double tyLePhanTramSoGheToiDa (double soGheToiDa, double soLuongKhachThucHienTourThanhCong) {
		double tyLe = 0;
		if (soGheToiDa > 0) {
			tyLe = (soLuongKhachThucHienTourThanhCong * 100) / soGheToiDa;
		}
		return (double) Math.round(tyLe*100.0)/100.0;
	}
	
	// Get all ve
	public JPAQuery<DatVe> getVeTheoNgay(JPAQuery<DatVe> ve) {

		if (ve != null) {
			ve = ve.where(QDatVe.datVe.ngayThucHienTour.between(getTuNgayThucHienTour(), getDenNgayThucHienTour()));
		}
		return ve;
	}

	// Get all dieu tiet
	public JPAQuery<DieuTietNhom> getDieuTietTheoNgay(JPAQuery<DieuTietNhom> nhom) {

		if (nhom != null) {
			nhom = nhom.where(QDieuTietNhom.dieuTietNhom.ngayThucHienTour.between(getTuNgayThucHienTour(),
					getDenNgayThucHienTour()));
		}
		return nhom;
	}

	// Get all dieu tiet
	public JPAQuery<Tour> getTourTheoNgay(JPAQuery<Tour> tour) {
		if (tour != null) {
            if (getDenNgayThucHienTour() != null) {
				tour.where(QTour.tour.ngayThucHienTour.between(getFixNgayThucHienTour(), getFixDenNgayThucHienTour()));
			} else {
                Calendar calendar = Calendar.getInstance();
		        calendar.setTime(getTuNgayThucHienTour());
					tour.where(QTour.tour.ngayThucHienTour.year().eq(calendar.get(Calendar.YEAR))
							.and(QTour.tour.ngayThucHienTour.month().eq(calendar.get(Calendar.MONTH) + 1))
							.and(QTour.tour.ngayThucHienTour.dayOfMonth().eq(calendar.get(Calendar.DAY_OF_MONTH))));
			}
		}
		return tour;
	}
	
	@SuppressWarnings("deprecation")
	public int getSoNgay () {
		int soNgay = 1;
		if (getDenNgayThucHienTour() != null) {
			soNgay = getFixDenNgayThucHienTour().getDate() - getFixNgayThucHienTour().getDate()+1;
		}
		return soNgay;
	}
	
	public Long getSoLuongKhachDieuTiet(JPAQuery<DieuTietNhom> nhom) {

		Long total = 0L;
		if (nhom != null) {

			for (DieuTietNhom i : nhom.fetch()) {
				total += Long.valueOf(i.getSoLuongKhachDieuTiet());
			}
		}
		return total;
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

	public Long getSoLuongKhachThucHienTourThanhCong(JPAQuery<Tour> tour) {

		Long total = 0L;
		if (tour != null) {

			for (Tour i : tour.fetch()) {
				total += Long.valueOf(i.getSoLuongNguoiLon()) + Long.valueOf(i.getSoLuongTrEm4Den9())
						+ Long.valueOf(i.getSoLuongTreEm1Den3());
			}
		}
		return total;
	}

	public Long getSoLuongKhachDieuTietTour(JPAQuery<Tour> tour) {

		Long total = 0L;
		if (tour != null) {

			for (Tour i : tour.fetch()) {
				total += Long.valueOf(i.getSoLuongKhachDieuTiet());
			}
		}
		return total;
	}

	public double tyLePhanTram(double soLuongKhachDat, double soLuongDieuTiet, double soLuongKhachThucHienTourThanhCong) {
		double tyLe = 0;
		double tongSoVe = soLuongKhachDat + soLuongDieuTiet;
		if (tongSoVe > 0) {
			tyLe = soLuongKhachThucHienTourThanhCong / tongSoVe;
		}
		return (double) Math.round(tyLe*100.0)/100.0;
	}
}
