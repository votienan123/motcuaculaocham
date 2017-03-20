package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QTrinhDoHocVan;
import vn.toancauxanh.gg.model.TrinhDoHocVan;
import vn.toancauxanh.service.BasicService;

public class TrinhDoHocVanService extends BasicService<TrinhDoHocVan>{
	
	private String img = "/backend/assets/img/edit.png";
	private String hoverImg = "/backend/assets/img/edit_hover.png";
	private String strUpdate = "Thứ tự";
	private boolean update = true;
	private boolean updateThanhCong = true;
	
	@Init
	public void init() {
		List<TrinhDoHocVan> list = find(TrinhDoHocVan.class)
				.where(QTrinhDoHocVan.trinhDoHocVan.trangThai.ne(core().TT_DA_XOA))
				.fetch();
		if (list == null || list.isEmpty()) {
			bootstrapTrinhDoHocVan();
		}
	}
	
	public List<TrinhDoHocVan> getListTrinhDoHocVan() {
		List<TrinhDoHocVan> list = new ArrayList<TrinhDoHocVan>();
		list = find(TrinhDoHocVan.class)
				.where(QTrinhDoHocVan.trinhDoHocVan.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QTrinhDoHocVan.trinhDoHocVan.soThuTu.asc())
				.fetch();
		return list;
	}
	
	public List<TrinhDoHocVan> getListTrinhDoHocVanAndNull() {
		List<TrinhDoHocVan> list = new ArrayList<TrinhDoHocVan>();
		list.add(null);
		list.addAll(getListTrinhDoHocVan());
		return list;
	}
	
	public JPAQuery<TrinhDoHocVan> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<TrinhDoHocVan> phongBan = find(TrinhDoHocVan.class)
				.where(QTrinhDoHocVan.trinhDoHocVan.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			phongBan.where(QTrinhDoHocVan.trinhDoHocVan.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			phongBan.where(QTrinhDoHocVan.trinhDoHocVan.trangThai.eq(trangThai));
		}
		phongBan.orderBy(QTrinhDoHocVan.trinhDoHocVan.soThuTu.asc());
		return phongBan;
	}
	
	public void bootstrapTrinhDoHocVan() {
		TrinhDoHocVan trinhDo = new TrinhDoHocVan("Chưa đi học", 1);
		trinhDo.saveAndContinue();
		trinhDo = new TrinhDoHocVan("Cấp I", 2);
		trinhDo.saveAndContinue();
		trinhDo = new TrinhDoHocVan("Cấp II", 3);
		trinhDo.saveAndContinue();
		trinhDo = new TrinhDoHocVan("Cấp III", 4);
		trinhDo.saveAndContinue();
		trinhDo = new TrinhDoHocVan("Trung cấp", 5);
		trinhDo.saveAndContinue();
		trinhDo = new TrinhDoHocVan("Cao đẳng", 6);
		trinhDo.saveAndContinue();
		trinhDo = new TrinhDoHocVan("Đại học", 7);
		trinhDo.saveAndContinue();
		trinhDo = new TrinhDoHocVan("Sau đại học", 8);
		trinhDo.saveAndContinue();
	}
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getHoverImg() {
		return hoverImg;
	}

	public void setHoverImg(String hoverImg) {
		this.hoverImg = hoverImg;
	}

	public String getStrUpdate() {
		return strUpdate;
	}

	public void setStrUpdate(String strUpdate) {
		this.strUpdate = strUpdate;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isUpdateThanhCong() {
		return updateThanhCong;
	}

	public void setUpdateThanhCong(boolean updateThanhCong) {
		this.updateThanhCong = updateThanhCong;
	}
	
	@Command
	public void clickButton(@BindingParam("model") final List<TrinhDoHocVan> model) {
		if (strUpdate.equals("Thứ tự")) {
			setStrUpdate("Lưu");
			setImg("/backend/assets/img/save.png");
			setHoverImg("/backend/assets/img/save_hover.png");
			setUpdate(false);
		} else {
			setUpdateThanhCong(true);
			for (TrinhDoHocVan phongBan: model) {
				if (check(phongBan)) {
					setUpdateThanhCong(false);
					break;
				}
				phongBan.save();
			}
			
			if (isUpdateThanhCong()) {
				setStrUpdate("Thứ tự");
				setImg("/backend/assets/img/edit.png");
				setHoverImg("/backend/assets/img/edit_hover.png");
				setUpdate(true);
			} else {
				setUpdateThanhCong(updateThanhCong);
			}
		}
		BindUtils.postNotifyChange(null, null, this, "img");
		BindUtils.postNotifyChange(null, null, this, "hoverImg");
		BindUtils.postNotifyChange(null, null, this, "update");
		BindUtils.postNotifyChange(null, null, this, "strUpdate");
		BindUtils.postNotifyChange(null, null, this, "updateThanhCong");
		BindUtils.postNotifyChange(null, null, this, "targetQuery");
	}
	
	private boolean check (TrinhDoHocVan phongBan) {
		if ("".equals(phongBan.getSoThuTu()) || phongBan.getSoThuTu() < 0) {
			return true;
		}
		return false;
	}
}
