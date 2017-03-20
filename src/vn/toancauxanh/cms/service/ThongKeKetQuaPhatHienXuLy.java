package vn.toancauxanh.cms.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.ListUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Command;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.DonViHanhChinh;
import vn.toancauxanh.gg.model.HinhThucXuLy;
import vn.toancauxanh.gg.model.QHinhThucXuLy;
import vn.toancauxanh.gg.model.QThongTinViPham;
import vn.toancauxanh.gg.model.SoLieuModel;
import vn.toancauxanh.gg.model.ThongKeModel;
import vn.toancauxanh.gg.model.ThongTinViPham;
import vn.toancauxanh.gg.model.ToDanPho;

public class ThongKeKetQuaPhatHienXuLy extends ThongKeService{

	private List<ThongKeModel> listThongKe;
	private List<String> cols = new ArrayList<String>();
	private Map<String,Object> argsGrid = new HashMap<String, Object>();
	
	@Command
	public void thongKeKetQua(){
		setThongKe(true);
		BindUtils.postNotifyChange(null, null, this, "thongKe");
		ThongKeModel ObjectThongKe;
		List<SoLieuModel> soLieus;
		String colName = " ";
		listThongKe = new ArrayList<ThongKeModel>();
		
		List<DonViHanhChinh> listDonViHanhChinh = new ArrayList<>();
		List<ToDanPho> listToDanPho = new ArrayList<>();
		boolean isDaNang = false;
		boolean isQuanHuyen = false;
		boolean isPhuongXa = false;
		if (getSelectedQuanHuyenTK() == null) {
			listDonViHanhChinh = core().getDonViHanhChinhs().getListQuanHuyenDaNang();
			listDonViHanhChinh.add(null);
			isDaNang = true;
		} else if (getSelectedPhuongXaTK() == null) {
			listDonViHanhChinh = core().getDonViHanhChinhs().getListDonViCon(getSelectedQuanHuyenTK());
			isQuanHuyen = true;
		} else {
			listToDanPho = core().getDonViHanhChinhs().getListToDanPhoTheoPhuong(getSelectedPhuongXaTK());
			isPhuongXa = true;
		}
		
		if (!isPhuongXa) {
			boolean firstNull = true;
			for (Object obj : ListUtils.union(listDonViHanhChinh, Collections.singletonList(null))) {
				DonViHanhChinh donVi = (DonViHanhChinh) obj;				
				ObjectThongKe = new ThongKeModel();
				soLieus = new ArrayList<SoLieuModel>();
				long sl = 0;				
				
				System.out.println("ca DANang");
				for(int i = 0; i < getListHinhThucXuLy().size(); i++){
					HinhThucXuLy ht = getListHinhThucXuLy().get(i);
					sl = getSoKetQuaCuaDonViTheoHinhThuc(donVi, ht, isDaNang, firstNull);
					soLieus.add(new SoLieuModel(ht, sl));
				}
				if (isDaNang && donVi == null && firstNull) {
					firstNull = false;
					ObjectThongKe.setNgoaiDiaBan(true);
				}
				ObjectThongKe.setDonViHanhChinh(donVi);
				ObjectThongKe.setSoLieus(soLieus);					
				listThongKe.add(ObjectThongKe);							
			}
		} else {
			for (Object obj : ListUtils.union(listToDanPho, Collections.singletonList(null))) {
				ToDanPho donVi = (ToDanPho) obj;
				
				ObjectThongKe = new ThongKeModel();
				soLieus = new ArrayList<SoLieuModel>();
				long sl = 0;	
				for(int i = 0; i < getListHinhThucXuLy().size(); i++){
					HinhThucXuLy ht = getListHinhThucXuLy().get(i);
					sl = getSoKetQuaCuaDonViTheoHinhThuc(donVi, ht);
					soLieus.add(new SoLieuModel(ht, sl));
				}
				ObjectThongKe.setToDanPho(donVi);
				ObjectThongKe.setSoLieus(soLieus);	
				listThongKe.add(ObjectThongKe);							
			}
		}
		argsGrid.put("modelGrid", listThongKe);
		argsGrid.put("cols", cols = getColumns(new String[]{colName}));
		BindUtils.postNotifyChange(null, null,this,"argsGrid");
		BindUtils.postNotifyChange(null, null,this,"title");
	}
	
	public List<HinhThucXuLy> getListHinhThucXuLy() {
		List<HinhThucXuLy> list = new ArrayList<HinhThucXuLy>();
		list = find(HinhThucXuLy.class)
				.where(QHinhThucXuLy.hinhThucXuLy.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QHinhThucXuLy.hinhThucXuLy.loaiDoiTuong.asc())
				.fetch();
		list.add(null);
		return list;
	}
	
	public long getSoKetQuaCuaDonViTheoHinhThuc(DonViHanhChinh donViHanhChinh, HinhThucXuLy hinhThucXuLy, boolean isDaNang, boolean firstNull) {
		System.out.println("donVi: " + donViHanhChinh);
		System.out.println("hinhThuc: " + hinhThucXuLy);
		DonViHanhChinh daNang = core().getDonViHanhChinhs().getDonViDaNang();
		JPAQuery<ThongTinViPham> q = find(ThongTinViPham.class);
		QThongTinViPham qT = QThongTinViPham.thongTinViPham;
		if (donViHanhChinh == null) {
			if (isDaNang) {
				if (firstNull) {
					q.where(qT.doiTuong.diaChiThuongTruTinh.ne(daNang));
				} else {
					q.where(qT.doiTuong.diaChiThuongTruTinh.isNotNull());
				}
			} else {
				q.where(qT.doiTuong.diaChiThuongTruHuyen.eq(getSelectedQuanHuyenTK()));
			}
		} else {
			if (isDaNang) {
				q.where(qT.doiTuong.diaChiThuongTruHuyen.eq(donViHanhChinh));
			} else {
				q.where(qT.doiTuong.diaChiThuongTruXa.eq(donViHanhChinh));
			}
		}		
		if (hinhThucXuLy != null) {
			q.where(qT.hinhThucXuLy.eq(hinhThucXuLy));
		}
		if (getTuNgayTK() != null && getDenNgayTK() !=null) {
			q.where(qT.ngayViPham.between(getTuNgayTK(), getDenNgayTK()));
		} else if (getTuNgayTK() != null) {
			q.where(qT.ngayViPham.after(getTuNgayTK()));
		} else if (getDenNgayTK() != null) {
			q.where(qT.ngayViPham.before(getDenNgayTK()));
		}
		return q.fetchCount();
	}
	
	public long getSoKetQuaCuaDonViTheoHinhThuc(ToDanPho toDanPho, HinhThucXuLy hinhThucXuLy) {
		JPAQuery<ThongTinViPham> q = find(ThongTinViPham.class);
		QThongTinViPham qT = QThongTinViPham.thongTinViPham;
		if (toDanPho == null) {
			q.where(qT.doiTuong.diaChiThuongTruXa.eq(getSelectedPhuongXaTK()));
		} else {
			q.where(qT.doiTuong.diaChiThuongTruToDanPho.eq(toDanPho));
		}
		if (hinhThucXuLy != null) {
			q.where(qT.hinhThucXuLy.eq(hinhThucXuLy));
		}
		if (getTuNgayTK() != null && getDenNgayTK() !=null) {
			q.where(qT.ngayViPham.between(getTuNgayTK(), getDenNgayTK()));
		} else if (getTuNgayTK() != null) {
			q.where(qT.ngayViPham.after(getTuNgayTK()));
		} else if (getDenNgayTK() != null) {
			q.where(qT.ngayViPham.before(getDenNgayTK()));
		}
		return q.fetchCount();
	}
	
	public String getValueTk(ThongKeModel objTk, String idx1) {
		int colItemStatus_index = Integer.valueOf(idx1);
		if (colItemStatus_index == 0) {
			if (objTk.getToDanPho() != null) {
				return objTk.getToDanPho().getTenVietTat();
			} else if (objTk.getDonViHanhChinh() != null) {
				return objTk.getDonViHanhChinh().getTen();
			}
			if (getSelectedQuanHuyenTK() == null) {
				if (objTk.isNgoaiDiaBan()) {
					return "Ngoài thành phố";
				} else {
					return "Toàn thành phố";
				}
			} else if (getSelectedPhuongXaTK() == null) {
				return "Toàn " +  getSelectedQuanHuyenTK().getTen();
			}
			return "Toàn " + getSelectedPhuongXaTK().getTen();
		}
		if (objTk.getSoLieus() != null && objTk.getSoLieus().get(colItemStatus_index - 1 ) != null) {
			return ""+formatDouble(objTk.getSoLieus().get(colItemStatus_index - 1 ).getGiaTri());
		}
		return "";
	}
	
	public List<String> getColumns(String[] colsName) {
		List<String> l = new ArrayList<String>();
		for (String colName : colsName) {
			l.add(colName);
		}
		
		for (HinhThucXuLy hinhThuc: getListHinhThucXuLy()) {
			if (hinhThuc != null) {
				l.add(hinhThuc.getTen());
			} else {
				l.add("Tổng số");
			}			
		}
		return l;
	}
	
	protected String formatDouble(double number) {
		//#.###,##
		final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator(',');
		decimalFormatSymbols.setGroupingSeparator('.');
		final DecimalFormat decimalFormat = new DecimalFormat("#.###", decimalFormatSymbols);
		return decimalFormat.format(number);
	}

	public List<ThongKeModel> getListThongKe() {
		return listThongKe;
	}

	public void setListThongKe(List<ThongKeModel> listThongKe) {
		this.listThongKe = listThongKe;
	}

	public List<String> getCols() {
		return cols;
	}

	public void setCols(List<String> cols) {
		this.cols = cols;
	}

	public Map<String, Object> getArgsGrid() {
		return argsGrid;
	}

	public void setArgsGrid(Map<String, Object> argsGrid) {
		this.argsGrid = argsGrid;
	}
	
	
}
