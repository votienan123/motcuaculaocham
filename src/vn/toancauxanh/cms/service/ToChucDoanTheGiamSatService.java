package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.TreeNode;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.QToChucDoanTheGiamSat;
import vn.toancauxanh.gg.model.ToChucDoanTheGiamSat;
import vn.toancauxanh.service.BasicService;

public class ToChucDoanTheGiamSatService extends BasicService<ToChucDoanTheGiamSat>{
	
	private ToChucDoanTheGiamSat selectedTCDTQuanHuyen;
	private ToChucDoanTheGiamSat selectedTCDTTinhThanh;
	private ToChucDoanTheGiamSat selectedTCDTPhuongXa;
		
	public ToChucDoanTheGiamSat getSelectedTCDTQuanHuyen() {
		return selectedTCDTQuanHuyen;
	}

	public void setSelectedTCDTQuanHuyen(ToChucDoanTheGiamSat selectedTCDTQuanHuyen) {
		System.out.println("selectedTCDTQuanHuyen: " + selectedTCDTQuanHuyen);
		this.selectedTCDTQuanHuyen = selectedTCDTQuanHuyen;
		BindUtils.postNotifyChange(null, null, this, "listPhuongXaDaNang");
		BindUtils.postNotifyChange(null, null, this, "listPhuongXaDaNangAndNull");
	}
	

	public ToChucDoanTheGiamSat getSelectedTCDTTinhThanh() {
		return selectedTCDTTinhThanh;
	}

	public void setSelectedTCDTTinhThanh(ToChucDoanTheGiamSat selectedTCDTTinhThanh) {
		this.selectedTCDTTinhThanh = selectedTCDTTinhThanh;
		BindUtils.postNotifyChange(null, null, this, "listQuanHuyen");
	}
	
	public ToChucDoanTheGiamSat getSelectedTCDTPhuongXa() {
		return selectedTCDTPhuongXa;
	}

	public void setSelectedTCDTPhuongXa(ToChucDoanTheGiamSat selectedTCDTPhuongXa) {
		this.selectedTCDTPhuongXa = selectedTCDTPhuongXa;
		BindUtils.postNotifyChange(null, null, this, "listToDanPho");
	}

	@Init
	public void init() {
		List<ToChucDoanTheGiamSat> list = find(ToChucDoanTheGiamSat.class)
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.ne(core().TT_DA_XOA))
				.fetch();
		if (list == null || list.isEmpty()) {
			bootstrapToChucDoanTheGiamSat();
		}
	}
	
	public List<ToChucDoanTheGiamSat> getListTinhThanh() {
		List<ToChucDoanTheGiamSat> list = new ArrayList<ToChucDoanTheGiamSat>();
		list = find(ToChucDoanTheGiamSat.class)
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.eq(core().TT_AP_DUNG))
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.cha.isNull())
				.orderBy(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.ten.asc())
				.fetch();
		return list;
	}
	
	public List<ToChucDoanTheGiamSat> getListQuanHuyen() {
		List<ToChucDoanTheGiamSat> list = new ArrayList<ToChucDoanTheGiamSat>();
		if (selectedTCDTTinhThanh != null) {
			list = find(ToChucDoanTheGiamSat.class)
					.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.eq(core().TT_AP_DUNG))
					.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.cha.eq(selectedTCDTTinhThanh))
					.orderBy(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.ten.asc())
					.fetch();
		}		
		return list;
	}
	
	public List<ToChucDoanTheGiamSat> getListQuanHuyenDaNang() {
		List<ToChucDoanTheGiamSat> list = new ArrayList<ToChucDoanTheGiamSat>();
		list = find(ToChucDoanTheGiamSat.class)
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.eq(core().TT_AP_DUNG))
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.cha.ma.eq("danang"))
				.orderBy(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.ten.asc())
				.fetch();
		return list;
	}
	
	public ToChucDoanTheGiamSat getDonViDaNang() {
		ToChucDoanTheGiamSat donVi = null;
		donVi = find(ToChucDoanTheGiamSat.class)
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.eq(core().TT_AP_DUNG))
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.ma.eq("danang"))
				.fetchFirst();
		return donVi;
	}
	
	public List<ToChucDoanTheGiamSat> getListDonViDaNang() {
		List<ToChucDoanTheGiamSat> list = new ArrayList<ToChucDoanTheGiamSat>();
		list.add(getDonViDaNang());
		return list;
	}
	
	public List<ToChucDoanTheGiamSat> getListQuanHuyenDaNangAndNull() {
		List<ToChucDoanTheGiamSat> list = new ArrayList<ToChucDoanTheGiamSat>();
		list.add(null);
		list.addAll(getListQuanHuyenDaNang());
		return list;
	}
	
	public List<ToChucDoanTheGiamSat> getListAllTCDT() {
		//System.out.println("getListAllCategory()");
		List<ToChucDoanTheGiamSat> list = new ArrayList<>();
		for (ToChucDoanTheGiamSat category : getList2()) {
			if (core().TT_AP_DUNG.equals(category.getTrangThai())) {
				list.add(category);
				list.addAll(getCategoryChildren2(category));
			}
		}
		return list;
	}
	
	
	public List<ToChucDoanTheGiamSat> getListPhuongXaDaNang() {
		List<ToChucDoanTheGiamSat> list = new ArrayList<ToChucDoanTheGiamSat>();
		if (selectedTCDTQuanHuyen != null) {
			list = find(ToChucDoanTheGiamSat.class)
					.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.eq(core().TT_AP_DUNG))
					.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.cha.eq(selectedTCDTQuanHuyen))
					.orderBy(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.ten.asc())
					.fetch();
		}
		return list;
	}
	
	
	public List<ToChucDoanTheGiamSat> getListPhuongXaDaNangAndNull() {
		List<ToChucDoanTheGiamSat> list = new ArrayList<ToChucDoanTheGiamSat>();
		list.add(null);
		list.addAll(getListPhuongXaDaNang());
		return list;
	}
	public JPAQuery<ToChucDoanTheGiamSat> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<ToChucDoanTheGiamSat> linhVuc = find(ToChucDoanTheGiamSat.class)
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			linhVuc.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			linhVuc.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.eq(trangThai));
		}
		linhVuc.orderBy(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.ngaySua.desc());
		return linhVuc;
	}
	
	
	public DefaultTreeModel<ToChucDoanTheGiamSat> getModel() {
		String param = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");
		ToChucDoanTheGiamSat catGoc = new ToChucDoanTheGiamSat();
		DefaultTreeModel<ToChucDoanTheGiamSat> model = new DefaultTreeModel<>(catGoc.getNode(), true);
		for (ToChucDoanTheGiamSat cat : getList2()) {
			if ((cat.getTen().toLowerCase().contains(param.toLowerCase()) && cat.getTrangThai().contains(trangThai))
					|| cat.loadSizeChild() > 0) {
				catGoc.getNode().add(cat.getNode());
			}
		}
		if (!param.isEmpty() || !param.equals("") || !trangThai.isEmpty() || !trangThai.equals("")) {
			//catGoc.getNode().getModel().setOpenObjects(catGoc.getNode().getChildren());
		}
		// model.setOpenObjects(catGoc.getNode().getChildren());
		//openObject(model, catGoc.getNode());
		return model;
	}
	
	public List<ToChucDoanTheGiamSat> getCategoryChildren(ToChucDoanTheGiamSat category) {
		List<ToChucDoanTheGiamSat> list = new ArrayList<>();
		if (!category.getTrangThai().equalsIgnoreCase(core().TT_DA_XOA)) {
			for (TreeNode<ToChucDoanTheGiamSat> el : category.getNode().getChildren()) {
				list.add(el.getData());
				list.addAll(getCategoryChildren(el.getData()));
			}
		}
		return list;
	}
	
	public List<ToChucDoanTheGiamSat> getCategoryChildren2(ToChucDoanTheGiamSat category) {
		List<ToChucDoanTheGiamSat> list = new ArrayList<>();
		if (category.getTrangThai().equalsIgnoreCase(core().TT_AP_DUNG)) {
			for (TreeNode<ToChucDoanTheGiamSat> el : category.getNode().getChildren()) {
				if (el.getData().getTrangThai().equals(core().TT_AP_DUNG)) {
					list.add(el.getData());
					list.addAll(getCategoryChildren2(el.getData()));
				}
			}
		}
		return list;
	}
	
	public List<ToChucDoanTheGiamSat> getList2() {
		JPAQuery<ToChucDoanTheGiamSat> q = find(ToChucDoanTheGiamSat.class);
		q.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.ne(core().TT_DA_XOA))
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.cha.isNull());
		List<ToChucDoanTheGiamSat> list = q.fetch();
		String param = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");
		for (ToChucDoanTheGiamSat category : list) {
			if (category.getTen().toLowerCase().contains(param.toLowerCase())
					&& (trangThai.isEmpty() || (!trangThai.isEmpty() && category.getTrangThai().equals(trangThai)))) {
				category.loadChildren();
			} else {
				category.loadChildren(param, trangThai);
			}
		}
		return list;
	}
	
	public void openObject(DefaultTreeModel<ToChucDoanTheGiamSat> model, TreeNode<ToChucDoanTheGiamSat> node) {
		if (node.isLeaf()) {
			//model.addOpenObject(node);
		} else {
			for (TreeNode<ToChucDoanTheGiamSat> child : node.getChildren()) {
				model.addOpenObject(child);
				openObject(node.getModel(), child);
			}
		}
	}
	
	private void bootstrapToChucDoanTheGiamSat() {
		ToChucDoanTheGiamSat daNang = new ToChucDoanTheGiamSat();
		daNang.setTen("Thành phố Đà Nẵng");
		daNang.setCapDonVi(1);
		daNang.setMa("danang");
		daNang.setMacDinh(true);
		daNang.saveNotShowNotification();
		
		ToChucDoanTheGiamSat haiChau = new ToChucDoanTheGiamSat();
		haiChau.setCha(daNang);
		haiChau.setTen("Quận Hải Châu");
		haiChau.setCapDonVi(2);
		haiChau.setMacDinh(true);
		haiChau.saveNotShowNotification();
		
		ToChucDoanTheGiamSat sonTra = new ToChucDoanTheGiamSat();
		sonTra.setCha(daNang);
		sonTra.setCapDonVi(2);
		sonTra.setMacDinh(true);
		sonTra.setTen("Quận Sơn Trà");
		sonTra.saveNotShowNotification();
		
		ToChucDoanTheGiamSat camLe = new ToChucDoanTheGiamSat();
		camLe.setCha(daNang);
		camLe.setCapDonVi(2);
		camLe.setMacDinh(true);
		camLe.setTen("Quận Cẩm Lệ");
		camLe.saveNotShowNotification();
		
		ToChucDoanTheGiamSat lienChieu = new ToChucDoanTheGiamSat();
		lienChieu.setCha(daNang);
		lienChieu.setCapDonVi(2);
		lienChieu.setMacDinh(true);
		lienChieu.setTen("Quận Liên Chiểu");
		lienChieu.saveNotShowNotification();
		
		ToChucDoanTheGiamSat nguHanhSon = new ToChucDoanTheGiamSat();
		nguHanhSon.setCha(daNang);
		nguHanhSon.setCapDonVi(2);
		nguHanhSon.setMacDinh(true);
		nguHanhSon.setTen("Quận Ngũ Hành Sơn");
		nguHanhSon.saveNotShowNotification();
		
		ToChucDoanTheGiamSat thanhKhe = new ToChucDoanTheGiamSat();
		thanhKhe.setCha(daNang);
		thanhKhe.setCapDonVi(2);
		thanhKhe.setMacDinh(true);
		thanhKhe.setTen("Quận Thanh Khê");
		thanhKhe.saveNotShowNotification();
		
		ToChucDoanTheGiamSat hoaVang = new ToChucDoanTheGiamSat();
		hoaVang.setCha(daNang);
		hoaVang.setCapDonVi(2);
		hoaVang.setMacDinh(true);
		hoaVang.setTen("Huyện Hòa Vang");
		hoaVang.saveNotShowNotification();
		
		ToChucDoanTheGiamSat phuong = new ToChucDoanTheGiamSat();
		
		//Hai Chau 13 phuong
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setTen("Phường Bình Hiên");
		phuong.saveNotShowNotification();			
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCapDonVi(3);
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setTen("Phường Bình Thuận");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setTen("Phường Hải Châu I");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setCapDonVi(3);
		phuong.setTen("Phường Hải Châu II");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setCapDonVi(3);
		phuong.setTen("Phường Hòa Cường Bắc");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setCapDonVi(3);
		phuong.setTen("Phường Hòa Cường Nam");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setCapDonVi(3);
		phuong.setTen("Phường Hòa Thuận Đông");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		
		phuong.setCapDonVi(3);
		phuong.setTen("Phường Hòa Thuận Tây");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		
		phuong.setMacDinh(true);
		phuong.setTen("Phường Nam Dương");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Phước Ninh");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		
		phuong.setMacDinh(true);
		phuong.setTen("Phường Thạch Thang");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Thanh Bình");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Thuận Phước");
		phuong.saveNotShowNotification();	
		
		//Son Tra 7 phường
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường An Hải Bắc");
		phuong.saveNotShowNotification();	
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường An Hải Đông");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường An Hải Tây");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Mân Thái");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Nại Hiên Đông");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Phước Mỹ");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Thọ Quang");
		phuong.saveNotShowNotification();
		
		//Cẩm lệ 6 phường
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa An");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Phát");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Thọ Đông");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Thọ Tây");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Xuân");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Khuê Trung");
		phuong.saveNotShowNotification();
		
		//Liên chiều 5
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(lienChieu);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Hiệp Bắc");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCapDonVi(3);
		phuong.setCha(lienChieu);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Hiệp Nam");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(lienChieu);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Khánh Bắc");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(lienChieu);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Khánh Nam");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(lienChieu);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Minh");
		phuong.saveNotShowNotification();
		
		//Quan Ngu hanh son 4 phuong
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(nguHanhSon);
		phuong.setCapDonVi(3);
		phuong.setTen("Phường Hòa Hải");
		phuong.setMacDinh(true);
		
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(nguHanhSon);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Quý");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(nguHanhSon);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Khuê Mỹ");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(nguHanhSon);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Mỹ An");
		phuong.setCapDonVi(3);
		phuong.saveNotShowNotification();
		
		//Quận Thanh Khê 11 phường
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường An Khê");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Chính Gián");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Hòa Khê");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Tam Thuận");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Tân Chính");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Thạch Gián");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Thanh Khê Đông");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Thanh Khê Tây");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Thạch Lộc Đán");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Vĩnh Trung");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Phường Xuân Hà");
		phuong.saveNotShowNotification();
		
		//Hoa vang 11 xa
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Xã Hòa Bắc");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Xã Hòa Châu");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Xã Hòa Khương");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Xã Hòa Liên");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Xã Hòa Nhơn");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Xã Hòa Ninh");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Xã Hòa Phong");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setMacDinh(true);
		
		phuong.setCapDonVi(3);
		phuong.setTen("Xã Hòa Phú");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Xã Hòa Phước");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		
		phuong.setTen("Xã Hòa Sơn");
		phuong.saveNotShowNotification();
		phuong = new ToChucDoanTheGiamSat();
		phuong.setCha(hoaVang);
		phuong.setMacDinh(true);
		phuong.setCapDonVi(3);
		
		phuong.setTen("Xã Hòa Tiến");
		phuong.saveNotShowNotification();
	}
}
