package vn.toancauxanh.cms.service;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.DonViHanhChinh;
import vn.toancauxanh.gg.model.QToDanPho;
import vn.toancauxanh.gg.model.ToDanPho;
import vn.toancauxanh.service.BasicService;

public class ToDanPhoService extends BasicService<ToDanPho>{
		
	private DonViHanhChinh selectedQuanHuyen;
	private DonViHanhChinh selectedPhuongXa;
	private String listToDanPho = "";
	
	@Override
	public DonViHanhChinh getSelectedQuanHuyen() {
		return selectedQuanHuyen;
	}
	
	@Override
	public void setSelectedQuanHuyen(DonViHanhChinh selectedQuanHuyen) {
		this.selectedQuanHuyen = selectedQuanHuyen;
		selectedPhuongXa = null;
		BindUtils.postNotifyChange(null, null, this, "selectedPhuongXa");
	}
	

	public DonViHanhChinh getSelectedPhuongXa() {
		return selectedPhuongXa;
	}

	public void setSelectedPhuongXa(DonViHanhChinh selectedPhuongXa) {
		this.selectedPhuongXa = selectedPhuongXa;
	}
	
	public String getListToDanPho() {
		return listToDanPho;
	}

	public void setListToDanPho(String listToDanPho) {
		this.listToDanPho = listToDanPho;
	}

	public JPAQuery<ToDanPho> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<ToDanPho> toDanPho = find(ToDanPho.class)
				.where(QToDanPho.toDanPho.trangThai.ne(core().TT_DA_XOA));
		if (getSelectedQuanHuyen() != null) {
			toDanPho.where(QToDanPho.toDanPho.quan.eq(getSelectedQuanHuyen()));
		}
		
		if (getSelectedPhuongXa() != null) {
			toDanPho.where(QToDanPho.toDanPho.phuong.eq(getSelectedPhuongXa()));
		}
		
		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			toDanPho.where(QToDanPho.toDanPho.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			toDanPho.where(QToDanPho.toDanPho.trangThai.eq(trangThai));
		}
		toDanPho.orderBy(QToDanPho.toDanPho.ngaySua.desc());
		return toDanPho;
	}
	
	@Command
	public void saveNhieuToDanPho(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		System.out.println("listToDanPho: " + listToDanPho);
		try {
			String[] listTDPStr = listToDanPho.split(";");
			ToDanPho toDanPho = null;
			for (String str: listTDPStr) {
				ToDanPho check = find(ToDanPho.class)
						.where(QToDanPho.toDanPho.tenVietTat.eq("Tổ " + str.trim()).or(QToDanPho.toDanPho.tenVietTat.eq(str.trim())))
						.where(QToDanPho.toDanPho.quan.eq(selectedQuanHuyen))
						.where(QToDanPho.toDanPho.phuong.eq(selectedPhuongXa))
						.fetchFirst();
				if (check == null) {
					toDanPho = new ToDanPho();
					toDanPho.setQuan(selectedQuanHuyen);
					toDanPho.setPhuong(selectedPhuongXa);
					if (selectedQuanHuyen.isThanhThi()) {
						toDanPho.setTen("Tổ dân phố " + str.trim());
						toDanPho.setTenVietTat("Tổ " + str.trim());
					} else {
						toDanPho.setTen("Thôn " + str.trim());
						toDanPho.setTenVietTat("" + str.trim());
					}
					toDanPho.saveNotShowNotification();					
				}
			}
			showNotification("Đã thêm thành công!", "", "success");
			wdn.detach();
			BindUtils.postNotifyChange(null, null, listObject, attr);	
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
