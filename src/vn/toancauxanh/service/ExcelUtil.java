package vn.toancauxanh.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.zkoss.zul.Filedownload;

import vn.toancauxanh.gg.model.CongTyKinhDoanh;
import vn.toancauxanh.gg.model.DonViHanhChinh;
import vn.toancauxanh.gg.model.ThongKeModel;

public class ExcelUtil {

	private static ExcelUtil instance;

	public static ExcelUtil getInStance() {
		if (instance == null) {
			instance = new ExcelUtil();
		}
		return instance;
	}

	public static void exportThongKeVe(String title, String fileName, String sheetName,
			List<CongTyKinhDoanh> listResult) throws IOException  {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		try {
			Cell c = null;
			// New Sheet
			Sheet sheet1 = null;
			sheet1 = wb.createSheet(sheetName);
			sheet1.getPrintSetup().setLandscape(true);
			sheet1.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			// Row and column indexes
			int idx = 0;
			// Generate column headings
			Row row;
			int countTitle = 0;

			CellStyle cellStyleDataRight = setBorderAndFont(wb, 1, false, 11, "", "RIGHT");
			CellStyle cellStyleDataLeft = setBorderAndFont(wb, 1, false, 11, "", "LEFT");
			CellStyle cellStyleDataCenter = setBorderAndFont(wb, 1, false, 11, "", "CENTER");

			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue(title);
			c.setCellStyle(setBorderAndFont(wb, 0, true, 13, "BLUE", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(countTitle, countTitle + 1, 0, 5));

			for (idx = 1; idx <= 1; idx++) {
				row = sheet1.createRow(idx);
				c = row.createCell(1);
				/*
				 * if (idx == 1) { c.setCellValue("Thời gian:" +
				 * App.app().getThongKeService().getTitleTime()); } else if (idx
				 * == 2) {
				 * c.setCellValue("Địa bàn: "+App.app().getThongKeService().
				 * getSelectedDonViHanhChinh().getTen()); }
				 */
			}
			idx++;
			row = sheet1.createRow(idx);

			// set column width
			sheet1.setColumnWidth(0, 16 * 256);
			// Generate rows header of grid
			row = sheet1.createRow(idx);
			idx++;

			c = row.createCell(0);
			c.setCellValue("STT");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(0, 6 * 256);
			c = row.createCell(1);
			c.setCellValue("Nhóm");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "LEFT"));
			sheet1.setColumnWidth(1, 20 * 256);
			c = row.createCell(2);
			c.setCellValue("Tên công ty");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "LEFT"));
			sheet1.setColumnWidth(2, 30 * 256);
			c = row.createCell(3);
			c.setCellValue("Tổng số lượng khách");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(3, 15 * 256);
			c = row.createCell(4);
			c.setCellValue("Lớn");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(4, 15 * 256);
			c = row.createCell(5);
			c.setCellValue("Nhỏ (5 - 9)");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(5, 15 * 256);
			c = row.createCell(6);
			c.setCellValue("Bé (1 - 4)");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(6, 15 * 256);
			c = row.createCell(7);
			c.setCellValue("Số lượng khách ban đầu");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(7, 15 * 256);
			c = row.createCell(8);
			c.setCellValue("Vé duyệt");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(8, 15 * 256);
			c = row.createCell(9);
			c.setCellValue("Vé sau 7h30");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(9, 15 * 256);
				
			int i = 1;
			for (CongTyKinhDoanh map : listResult) {
				row = sheet1.createRow(idx);
				c = row.createCell(0);
				c.setCellValue(i);
				c.setCellStyle(cellStyleDataCenter);

				c = row.createCell(1);
				c.setCellValue(map.getNhomCuaHoi().getTen());
				c.setCellStyle(cellStyleDataLeft);

				c = row.createCell(2);
				c.setCellValue(map.getTen());
				c.setCellStyle(cellStyleDataLeft);

				c = row.createCell(3);
				c.setCellValue(String.valueOf(Long.valueOf(map.getSoLuongNguoiLonNhat()+map.getSoLuongTreEm1Den3()+map.getSoLuongTrEm4Den9())));
				c.setCellStyle(cellStyleDataCenter);

				c = row.createCell(4);
				c.setCellValue(Long.valueOf(map.getSoLuongNguoiLonNhat() > 0 ? map.getSoLuongNguoiLonNhat() : 0));
				c.setCellStyle(cellStyleDataCenter);

				c = row.createCell(5);
				c.setCellValue(Long.valueOf(map.getSoLuongTreEm4Den9Tuoi() > 0 ? map.getSoLuongTreEm4Den9Tuoi() : 0));
				c.setCellStyle(cellStyleDataCenter);
				
				c = row.createCell(6);
				c.setCellValue(Long.valueOf(map.getSoLuongTreEm1Den3Tuoi() > 0 ? map.getSoLuongTreEm1Den3Tuoi() : 0));
				c.setCellStyle(cellStyleDataCenter);
				
				c = row.createCell(7);
				c.setCellValue(Long.valueOf(map.getTongSoLuongVeDatBanDauCuaCongTy() > 0 ? map.getTongSoLuongVeDatBanDauCuaCongTy() : 0));
				c.setCellStyle(cellStyleDataCenter);
				
				c = row.createCell(8);
				c.setCellValue(Long.valueOf(map.getTongSoLuongVeDuyetCuaCongTy() > 0 ? map.getTongSoLuongVeDuyetCuaCongTy() : 0));
				c.setCellStyle(cellStyleDataCenter);
				
				c = row.createCell(9);
				c.setCellValue(map.getTongVeSau7h30());
				c.setCellStyle(cellStyleDataCenter);
				
				i++;
				idx++;
			}

			idx++;
			// createNoteRow(wb, sheet1, idx);
			idx++;

			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			wb.write(fileOut);
			Filedownload.save(new ByteArrayInputStream(fileOut.toByteArray()), "application/octet-stream",
					fileName + ".xlsx");
		} finally {
			wb.close();
		}
	}

	public static void exportThongKe(String title, String fileName, String sheetName,
			List<Map<String, Object>> listResult) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		try {
			Cell c = null;
			// New Sheet
			Sheet sheet1 = null;
			sheet1 = wb.createSheet(sheetName);
			sheet1.getPrintSetup().setLandscape(true);
			sheet1.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			// Row and column indexes
			int idx = 0;
			// Generate column headings
			Row row;
			int countTitle = 0;

			CellStyle cellStyleDataRight = setBorderAndFont(wb, 1, false, 11, "", "RIGHT");
			CellStyle cellStyleDataLeft = setBorderAndFont(wb, 1, false, 11, "", "LEFT");
			CellStyle cellStyleDataCenter = setBorderAndFont(wb, 1, false, 11, "", "CENTER");

			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue(title);
			c.setCellStyle(setBorderAndFont(wb, 0, true, 13, "BLUE", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(countTitle, countTitle + 1, 0, 5));

			for (idx = 1; idx <= 1; idx++) {
				row = sheet1.createRow(idx);
				c = row.createCell(1);
				/*
				 * if (idx == 1) { c.setCellValue("Thời gian:" +
				 * App.app().getThongKeService().getTitleTime()); } else if (idx
				 * == 2) {
				 * c.setCellValue("Địa bàn: "+App.app().getThongKeService().
				 * getSelectedDonViHanhChinh().getTen()); }
				 */
			}
			idx++;
			row = sheet1.createRow(idx);

			// set column width
			sheet1.setColumnWidth(0, 16 * 256);
			// Generate rows header of grid
			row = sheet1.createRow(idx);
			idx++;

			c = row.createCell(0);
			c.setCellValue("STT");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(0, 6 * 256);
			c = row.createCell(1);
			c.setCellValue("Nhóm");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "LEFT"));
			sheet1.setColumnWidth(1, 20 * 256);
			c = row.createCell(2);
			c.setCellValue("Tên công ty");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "LEFT"));
			sheet1.setColumnWidth(2, 30 * 256);
			c = row.createCell(3);
			c.setCellValue("Số lượng khách");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(3, 15 * 256);
			c = row.createCell(4);
			c.setCellValue("Điều tiết");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(4, 10 * 256);
			c = row.createCell(5);
			c.setCellValue("Khách thực hiện tour thành công");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.setColumnWidth(5, 20 * 256);

			int i = 1;
			for (Map<String, Object> map : listResult) {
				row = sheet1.createRow(idx);
				c = row.createCell(0);
				c.setCellValue(i);
				c.setCellStyle(cellStyleDataCenter);

				c = row.createCell(1);
				c.setCellValue(((CongTyKinhDoanh) map.get("congty")).getNhomCuaHoi().getTen());
				c.setCellStyle(cellStyleDataLeft);

				c = row.createCell(2);
				c.setCellValue(((CongTyKinhDoanh) map.get("congty")).getTen());
				c.setCellStyle(cellStyleDataLeft);

				c = row.createCell(3);
				c.setCellValue(map.get("soLuongKhach") + "");
				c.setCellStyle(cellStyleDataCenter);

				c = row.createCell(4);
				c.setCellValue(map.get("dieuTiet") + "");
				c.setCellStyle(cellStyleDataCenter);

				c = row.createCell(5);
				c.setCellValue(map.get("khachThucHienTourThanhCong") + "");
				c.setCellStyle(cellStyleDataCenter);
				i++;
				idx++;
			}

			idx++;
			// createNoteRow(wb, sheet1, idx);
			idx++;

			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			wb.write(fileOut);
			Filedownload.save(new ByteArrayInputStream(fileOut.toByteArray()), "application/octet-stream",
					fileName + ".xlsx");
		} finally {
			wb.close();
		}
	}

	public static String getValueTk(ThongKeModel objTk, int idx1, DonViHanhChinh donViHanhChinh) {
		System.out.println("idx1: " + idx1);
		int colItemStatus_index = idx1;
		if (colItemStatus_index == 0) {
			if (objTk.getToDanPho() != null) {
				return objTk.getToDanPho().getTenVietTat();
			} else if (objTk.getDonViHanhChinh() != null) {
				return objTk.getDonViHanhChinh().getTen();
			}
			if (donViHanhChinh == null) {
				if (objTk.isNgoaiDiaBan()) {
					return "Ngoài thành phố";
				} else {
					return "Toàn thành phố";
				}
			} else {
				return "Toàn " + donViHanhChinh.getTen();
			}
		}
		if (objTk.getSoLieus() != null && objTk.getSoLieus().get(colItemStatus_index - 1) != null) {
			return "" + formatDouble(objTk.getSoLieus().get(colItemStatus_index - 1).getGiaTri());
		}
		return "";
	}

	public static String formatDouble(double number) {
		// #.###,##
		final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator(',');
		decimalFormatSymbols.setGroupingSeparator('.');
		final DecimalFormat decimalFormat = new DecimalFormat("#.###", decimalFormatSymbols);
		return decimalFormat.format(number);
	}

	private static void setBorderMore(int flag, Workbook wb, Row row, Cell c, int begin, int end, int fontSize) {
		final CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderLeft((short) 1);

		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		for (int i = begin; i < end; i++) {
			Cell c1 = row.createCell(i);
			if (flag == 1) {
				cellStyle.setBorderTop((short) 2);
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
			} else {
				cellStyle.setBorderTop((short) 1);
			}
			if (flag == 2) {
				cellStyle.setBorderBottom((short) 2);
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
			} else {
				cellStyle.setBorderBottom((short) 1);
			}

			if (i == (end - 1)) {
				cellStyle.setBorderRight((short) 2);
			} else {
				cellStyle.setBorderRight((short) 1);
			}
			if (flag == 3) {
				cellStyle.setBorderTop((short) 1);
				cellStyle.setBorderBottom((short) 1);
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
				cellStyle.setBorderRight((short) 1);
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			}
			c1.setCellStyle(cellStyle);
		}
	}

	public static CellStyle setBorderAndFont(final Workbook workbook, final int borderSize, final boolean isTitle,
			final int fontSize, final String fontColor, final String textAlign) {
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);

		cellStyle.setBorderTop((short) borderSize); // single line border
		cellStyle.setBorderBottom((short) borderSize); // single line border
		cellStyle.setBorderLeft((short) borderSize); // single line border
		cellStyle.setBorderRight((short) borderSize); // single line border
		cellStyle.setAlignment(CellStyle.VERTICAL_CENTER);

		if (textAlign.equals("RIGHT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		} else if (textAlign.equals("CENTER")) {
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		} else if (textAlign.equals("LEFT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		}
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		final Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		if (isTitle) {

			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			if (fontColor.equals("RED")) {
				font.setColor(Font.COLOR_RED);
			} else if (fontColor.equals("BLUE")) {
				font.setColor((short) 4);
			} else {
				// do no thing
			}
		}
		font.setFontHeightInPoints((short) fontSize);
		cellStyle.setFont(font);

		return cellStyle;
	}

	public static void createNoteRow(Workbook wb, Sheet sheet1, int idx) {
		Cell c;
		org.apache.poi.ss.usermodel.Row row;
		row = sheet1.createRow(idx);
		c = row.createCell(1);
		c.setCellValue("* Theo HCP");
		c.setCellStyle(setBorderAndFont(wb, 0, true, 11, "", "LEFT"));
	}

	public static void exportThongKeThoiGian(String title, String ngay, String fileName, String sheetname,
			List<Object[]> list) throws IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		try {
			Cell c = null;
			// New Sheet
			Sheet sheet1 = null;
			sheet1 = wb.createSheet(sheetname);
			// Row and column indexes
			int idx = 0;
			// Generate column headings
			Row row;
			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue(title);
			c.setCellStyle(setBorderAndFont(wb, 0, true, 14, "BLUE", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));

			idx++;
			// Generate column headings
			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue(ngay);
			c.setCellStyle(setBorderAndFont(wb, 0, true, 14, "BLUE", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));

			// set column width
			sheet1.setColumnWidth(0, 30 * 256);
			sheet1.setColumnWidth(1, 16 * 256);
			sheet1.setColumnWidth(2, 16 * 256);
			sheet1.setColumnWidth(3, 16 * 256);
			sheet1.setColumnWidth(4, 16 * 256);
			sheet1.setColumnWidth(5, 16 * 256);
			sheet1.setColumnWidth(6, 16 * 256);
			// Generate rows header of grid
			idx++;
			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue("");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(2, 3, 0, 0));
			c = row.createCell(1);
			setBorderMore(3, wb, row, c, 1, 4, 11);
			c.setCellValue("Hộ khẩu");
			sheet1.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
			c = row.createCell(4);
			// c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
			setBorderMore(3, wb, row, c, 4, 7, 11);
			c.setCellValue("Nhân khẩu");
			sheet1.addMergedRegion(new CellRangeAddress(2, 2, 4, 6));
			idx++;
			row = sheet1.createRow(idx);
			c = row.createCell(1);
			c.setCellValue("Thường trú");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			c = row.createCell(2);
			c.setCellValue("Tạm trú");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			c = row.createCell(3);
			c.setCellValue("Tổng");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			c = row.createCell(4);
			c.setCellValue("Thường trú");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			c = row.createCell(5);
			c.setCellValue("Tạm trú");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			c = row.createCell(6);
			c.setCellValue("Tổng");
			c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "", "CENTER"));
			idx++;
			int i = 1;
			CellStyle cellStyleLeft = setBorderAndFont(wb, 1, i == list.size(), 11, "", "LEFT");
			CellStyle cellStyleCenter = setBorderAndFont(wb, 1, i == list.size(), 11, "", "CENTER");
			for (Object[] ob : list) {
				row = sheet1.createRow(idx);
				c = row.createCell(0);
				c.setCellValue(ob[0] + "");
				c.setCellStyle(cellStyleLeft);
				c = row.createCell(1);
				c.setCellValue(ob[1] + "");
				c.setCellStyle(cellStyleCenter);
				c = row.createCell(2);
				c.setCellValue(ob[2] + "");
				c.setCellStyle(cellStyleCenter);
				c = row.createCell(3);
				c.setCellValue(ob[3] + "");
				c.setCellStyle(cellStyleCenter);
				c = row.createCell(4);
				c.setCellValue(ob[4] + "");
				c.setCellStyle(cellStyleCenter);
				c = row.createCell(5);
				c.setCellValue(ob[5] + "");
				c.setCellStyle(cellStyleCenter);
				c = row.createCell(6);
				c.setCellValue(ob[6] + "");
				c.setCellStyle(cellStyleCenter);
				i++;
				idx++;
			}

			idx++;
			// createNoteRow(wb, sheet1, idx);
			idx++;

			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			wb.write(fileOut);
			// System.out.println("fileName: " + fileName);
			Filedownload.save(new ByteArrayInputStream(fileOut.toByteArray()), "application/octet-stream",
					fileName + ".xlsx");
		} finally {
			wb.close();
		}
	}
	/*
	 * 
	 * public static void exportDanhSachHoKhau(String fileName, String
	 * sheetName, List<HoKhau> list, String title, String timThay) throws
	 * IOException { SimpleDateFormat dateFormat = new
	 * SimpleDateFormat("dd/MM/yyyy"); // New Workbook Workbook wb = new
	 * XSSFWorkbook(); try { Cell c = null; // New Sheet Sheet sheet1 = null;
	 * sheet1 = wb.createSheet(sheetName); sheet1.createFreezePane(0, 3); // Row
	 * and column indexes int idx = 0; // Generate column headings Row row; row
	 * = sheet1.createRow(idx); c = row.createCell(0);
	 * c.setCellValue("DANH SÁCH HỘ KHẨU " + title.toUpperCase());
	 * c.setCellStyle(setBorderAndFont(wb, 0, true, 14, "BLUE", "CENTER"));
	 * sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
	 * 
	 * // set column width sheet1.setColumnWidth(0, 6 * 256);
	 * sheet1.setColumnWidth(1, 13 * 256); sheet1.setColumnWidth(2, 56 * 256);
	 * sheet1.setColumnWidth(3, 9 * 256); sheet1.setColumnWidth(4, 30 * 256);
	 * sheet1.setColumnWidth(5, 10 * 256); sheet1.setColumnWidth(6, 12 * 256);
	 * sheet1.setColumnWidth(7, 14 * 256); sheet1.setColumnWidth(8, 7 * 256);
	 * sheet1.setColumnWidth(9, 25 * 256); // Generate rows header of grid
	 * idx++; row = sheet1.createRow(idx); c = row.createCell(0);
	 * c.setCellValue(timThay); c.setCellStyle(setBorderAndFont(wb, 0, false,
	 * 12, "", "LEFT")); sheet1.addMergedRegion(new CellRangeAddress(1, 1, 0,
	 * 6)); idx++; row = sheet1.createRow(idx); idx++;
	 * 
	 * c = row.createCell(0); c.setCellValue("STT");
	 * c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","LEFT")); c =
	 * row.createCell(1); c.setCellValue("Số hồ sơ hộ");
	 * c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","LEFT")); c =
	 * row.createCell(2); c.setCellValue("Nơi đăng ký " + title);
	 * c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","LEFT")); c =
	 * row.createCell(3); c.setCellValue("Thôn/Tổ");
	 * c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","LEFT")); c =
	 * row.createCell(4); c.setCellValue("Chủ hộ");
	 * c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","LEFT")); c =
	 * row.createCell(5); c.setCellValue("Ngày sinh");
	 * c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","LEFT")); c =
	 * row.createCell(6); c.setCellValue("Số CMND");
	 * c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","LEFT")); c =
	 * row.createCell(7); c.setCellValue("Ngày đăng ký");
	 * c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","LEFT")); c =
	 * row.createCell(8); c.setCellValue("Số NK");
	 * c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","LEFT")); c =
	 * row.createCell(9); c.setCellValue("Người nhập");
	 * c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","LEFT"));
	 * 
	 * int i = 1; for (HoKhau hk: list) { row = sheet1.createRow(idx); c =
	 * row.createCell(0); c.setCellValue(i); c.setCellStyle(setBorderAndFont(wb,
	 * 1, false, 11, "","LEFT")); c = row.createCell(1);
	 * c.setCellValue(hk.getSoHoSoHoKhau()); c.setCellStyle(setBorderAndFont(wb,
	 * 1, false, 11, "","LEFT")); c = row.createCell(2);
	 * c.setCellValue(hk.getDiaChiThuongTruHome());
	 * c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT")); c =
	 * row.createCell(3); c.setCellValue(hk.getToDanPho() == null ? "" :
	 * hk.getToDanPho().getTenVietTat()); c.setCellStyle(setBorderAndFont(wb, 1,
	 * false, 11, "","LEFT")); c = row.createCell(4);
	 * c.setCellValue(hk.getHoTenChuHo().toUpperCase());
	 * c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT")); c =
	 * row.createCell(5); c.setCellValue(hk.getNgaySinhChuHo() != null ?
	 * dateFormat.format(hk.getNgaySinhChuHo()) : hk.getNamSinhChuHo());
	 * c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT")); c =
	 * row.createCell(6); c.setCellValue(hk.getSoCmndChuHo());
	 * c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT")); c =
	 * row.createCell(7); c.setCellValue(hk.getNgayDangKy() != null ?
	 * dateFormat.format(hk.getNgayDangKy()) : hk.getNamDangKy());
	 * c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT")); c =
	 * row.createCell(8); c.setCellValue(hk.getSoNhanKhau());
	 * c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT")); c =
	 * row.createCell(9); c.setCellValue(hk.getTenNguoiSua());
	 * c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT")); i++;
	 * idx++; }
	 * 
	 * idx++; //createNoteRow(wb, sheet1, idx); idx++;
	 * 
	 * ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
	 * wb.write(fileOut); //System.out.println("fileName: " + fileName);
	 * Filedownload.save(new
	 * ByteArrayInputStream(fileOut.toByteArray()),"application/octet-stream",
	 * fileName + ".xlsx"); } finally { wb.close(); } }
	 */
}