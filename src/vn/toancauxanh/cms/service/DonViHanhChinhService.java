package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;
import org.zkoss.zul.DefaultTreeModel;
import org.zkoss.zul.TreeNode;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.DonViHanhChinh;
import vn.toancauxanh.gg.model.QDonViHanhChinh;
import vn.toancauxanh.gg.model.QToDanPho;
import vn.toancauxanh.gg.model.ToDanPho;
import vn.toancauxanh.service.BasicService;
import vn.toancauxanh.service.NaturalOrderComparator;

public class DonViHanhChinhService extends BasicService<DonViHanhChinh>{
	
	private DonViHanhChinh selectedDVHCQuanHuyen;
	private DonViHanhChinh selectedDVHCTinhThanh;
	private DonViHanhChinh selectedDVHCPhuongXa;
		
	public DonViHanhChinh getSelectedDVHCQuanHuyen() {
		return selectedDVHCQuanHuyen;
	}

	public void setSelectedDVHCQuanHuyen(DonViHanhChinh selectedDVHCQuanHuyen) {
		System.out.println("selectedDVHCQuanHuyen: " + selectedDVHCQuanHuyen);
		this.selectedDVHCQuanHuyen = selectedDVHCQuanHuyen;
		BindUtils.postNotifyChange(null, null, this, "listPhuongXaDaNang");
		BindUtils.postNotifyChange(null, null, this, "listPhuongXaDaNangAndNull");
	}
	

	public DonViHanhChinh getSelectedDVHCTinhThanh() {
		return selectedDVHCTinhThanh;
	}

	public void setSelectedDVHCTinhThanh(DonViHanhChinh selectedDVHCTinhThanh) {
		this.selectedDVHCTinhThanh = selectedDVHCTinhThanh;
		BindUtils.postNotifyChange(null, null, this, "listQuanHuyen");
		BindUtils.postNotifyChange(null, null, this, "listQuanHuyenAndNull");
		System.out.println("setSelectedDVHCTinhThanh");
	}
	
	public DonViHanhChinh getSelectedDVHCPhuongXa() {
		return selectedDVHCPhuongXa;
	}

	public void setSelectedDVHCPhuongXa(DonViHanhChinh selectedDVHCPhuongXa) {
		this.selectedDVHCPhuongXa = selectedDVHCPhuongXa;
		BindUtils.postNotifyChange(null, null, this, "listToDanPho");
	}

	@Init
	public void init() {
		List<DonViHanhChinh> list = find(DonViHanhChinh.class)
				.where(QDonViHanhChinh.donViHanhChinh.trangThai.ne(core().TT_DA_XOA))
				.fetch();
		if (list == null || list.isEmpty()) {
			bootstrapDonViHanhChinh();
		}
	}
	
	public List<DonViHanhChinh> getListTinhThanh() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list = find(DonViHanhChinh.class)
				.where(QDonViHanhChinh.donViHanhChinh.trangThai.eq(core().TT_AP_DUNG))
				.where(QDonViHanhChinh.donViHanhChinh.cha.isNull())
				.orderBy(QDonViHanhChinh.donViHanhChinh.ten.asc())
				.fetch();
		return list;
	}
	
	public List<DonViHanhChinh> getListTinhThanhAndNull() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		list.addAll(getListTinhThanh());
		return list;
	}
	
	public List<DonViHanhChinh> getListQuanHuyen() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		if (selectedDVHCTinhThanh != null) {
			list = find(DonViHanhChinh.class)
					.where(QDonViHanhChinh.donViHanhChinh.trangThai.eq(core().TT_AP_DUNG))
					.where(QDonViHanhChinh.donViHanhChinh.cha.eq(selectedDVHCTinhThanh))
					.orderBy(QDonViHanhChinh.donViHanhChinh.ten.asc())
					.fetch();
		}		
		return list;
	}
	
	public List<DonViHanhChinh> getListQuanHuyenAndNull() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		list.addAll(getListQuanHuyen());
		return list;
	}
	
	public List<DonViHanhChinh> getListQuanHuyenDaNang() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list = find(DonViHanhChinh.class)
				.where(QDonViHanhChinh.donViHanhChinh.trangThai.eq(core().TT_AP_DUNG))
				.where(QDonViHanhChinh.donViHanhChinh.cha.ma.eq("danang"))
				.orderBy(QDonViHanhChinh.donViHanhChinh.ten.asc())
				.fetch();
		return list;
	}
	
	public List<DonViHanhChinh> getListDonViCon(DonViHanhChinh cha) {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list = find(DonViHanhChinh.class)
				.where(QDonViHanhChinh.donViHanhChinh.trangThai.eq(core().TT_AP_DUNG))
				.where(QDonViHanhChinh.donViHanhChinh.cha.eq(cha))
				.orderBy(QDonViHanhChinh.donViHanhChinh.ten.asc())
				.fetch();
		return list;
	}
	
	public List<ToDanPho> getListToDanPhoTheoPhuong(DonViHanhChinh cha) {
		List<ToDanPho> list = new ArrayList<ToDanPho>();
		list = find(ToDanPho.class)
				.where(QToDanPho.toDanPho.trangThai.eq(core().TT_AP_DUNG))
				.where(QToDanPho.toDanPho.phuong.eq(cha))
				.orderBy(QToDanPho.toDanPho.ten.asc())
				.fetch();
		Collections.sort(list, new NaturalOrderComparator());
		return list;
	}
	
	public DonViHanhChinh getDonViDaNang() {
		DonViHanhChinh donVi = null;
		donVi = find(DonViHanhChinh.class)
				.where(QDonViHanhChinh.donViHanhChinh.trangThai.eq(core().TT_AP_DUNG))
				.where(QDonViHanhChinh.donViHanhChinh.ma.eq("danang"))
				.fetchFirst();
		return donVi;
	}
	
	public List<DonViHanhChinh> getListDonViDaNang() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(getDonViDaNang());
		return list;
	}
	
	public List<DonViHanhChinh> getListQuanHuyenDaNangAndNull() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		list.addAll(getListQuanHuyenDaNang());
		return list;
	}
	
	public List<ToDanPho> getListToDanPho() {
		List<ToDanPho> list = new ArrayList<ToDanPho>();
		if (selectedDVHCPhuongXa != null) {
			list = find(ToDanPho.class)
					.where(QToDanPho.toDanPho.trangThai.eq(core().TT_AP_DUNG))
					.where(QToDanPho.toDanPho.phuong.eq(selectedDVHCPhuongXa))
					.fetch();
			Collections.sort(list, new NaturalOrderComparator());
			//list.add(0, null);
		}
		return list;
	}
	
	public List<DonViHanhChinh> getListPhuongXaDaNang() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		if (selectedDVHCQuanHuyen != null) {
			list = find(DonViHanhChinh.class)
					.where(QDonViHanhChinh.donViHanhChinh.trangThai.eq(core().TT_AP_DUNG))
					.where(QDonViHanhChinh.donViHanhChinh.cha.eq(selectedDVHCQuanHuyen))
					.orderBy(QDonViHanhChinh.donViHanhChinh.ten.asc())
					.fetch();
		}
		return list;
	}
	
	
	public List<DonViHanhChinh> getListPhuongXaDaNangAndNull() {
		List<DonViHanhChinh> list = new ArrayList<DonViHanhChinh>();
		list.add(null);
		list.addAll(getListPhuongXaDaNang());
		return list;
	}
	public JPAQuery<DonViHanhChinh> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<DonViHanhChinh> linhVuc = find(DonViHanhChinh.class)
				.where(QDonViHanhChinh.donViHanhChinh.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			linhVuc.where(QDonViHanhChinh.donViHanhChinh.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			linhVuc.where(QDonViHanhChinh.donViHanhChinh.trangThai.eq(trangThai));
		}
		linhVuc.orderBy(QDonViHanhChinh.donViHanhChinh.ngaySua.desc());
		return linhVuc;
	}
	
	
	public DefaultTreeModel<DonViHanhChinh> getModel() {
		String param = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");
		DonViHanhChinh catGoc = new DonViHanhChinh();
		DefaultTreeModel<DonViHanhChinh> model = new DefaultTreeModel<>(catGoc.getNode(), true);
		for (DonViHanhChinh cat : getList2()) {
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
	
	public List<DonViHanhChinh> getCategoryChildren(DonViHanhChinh category) {
		List<DonViHanhChinh> list = new ArrayList<>();
		if (!category.getTrangThai().equalsIgnoreCase(core().TT_DA_XOA)) {
			for (TreeNode<DonViHanhChinh> el : category.getNode().getChildren()) {
				list.add(el.getData());
				list.addAll(getCategoryChildren(el.getData()));
			}
		}
		return list;
	}
	
	public List<DonViHanhChinh> getList2() {
		JPAQuery<DonViHanhChinh> q = find(DonViHanhChinh.class);
		q.where(QDonViHanhChinh.donViHanhChinh.trangThai.ne(core().TT_DA_XOA))
				.where(QDonViHanhChinh.donViHanhChinh.cha.isNull());
		List<DonViHanhChinh> list = q.fetch();
		String param = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");
		for (DonViHanhChinh category : list) {
			if (category.getTen().toLowerCase().contains(param.toLowerCase())
					&& (trangThai.isEmpty() || (!trangThai.isEmpty() && category.getTrangThai().equals(trangThai)))) {
				category.loadChildren();
			} else {
				category.loadChildren(param, trangThai);
			}
		}
		return list;
	}
	
	public void openObject(DefaultTreeModel<DonViHanhChinh> model, TreeNode<DonViHanhChinh> node) {
		if (node.isLeaf()) {
			model.addOpenObject(node);
		} else {
			for (TreeNode<DonViHanhChinh> child : node.getChildren()) {
				model.addOpenObject(child);
				openObject(node.getModel(), child);
			}
		}
	}
	
	private void bootstrapDonViHanhChinh() {
		DonViHanhChinh daNang = new DonViHanhChinh();
		daNang.setTen("Thành phố Đà Nẵng");
		daNang.setCapDonVi(1);
		daNang.setMa("danang");
		daNang.setMacDinh(true);
		daNang.saveNotShowNotification();
		
		DonViHanhChinh haiChau = new DonViHanhChinh();
		haiChau.setCha(daNang);
		haiChau.setTen("Quận Hải Châu");
		haiChau.setCapDonVi(2);
		haiChau.setThanhThi(true);
		haiChau.setMacDinh(true);
		haiChau.saveNotShowNotification();
		
		DonViHanhChinh sonTra = new DonViHanhChinh();
		sonTra.setCha(daNang);
		sonTra.setCapDonVi(2);
		sonTra.setMacDinh(true);
		sonTra.setThanhThi(true);
		sonTra.setTen("Quận Sơn Trà");
		sonTra.saveNotShowNotification();
		
		DonViHanhChinh camLe = new DonViHanhChinh();
		camLe.setCha(daNang);
		camLe.setCapDonVi(2);
		camLe.setThanhThi(true);
		camLe.setMacDinh(true);
		camLe.setTen("Quận Cẩm Lệ");
		camLe.saveNotShowNotification();
		
		DonViHanhChinh lienChieu = new DonViHanhChinh();
		lienChieu.setCha(daNang);
		lienChieu.setCapDonVi(2);
		lienChieu.setThanhThi(true);
		lienChieu.setMacDinh(true);
		lienChieu.setTen("Quận Liên Chiểu");
		lienChieu.saveNotShowNotification();
		
		DonViHanhChinh nguHanhSon = new DonViHanhChinh();
		nguHanhSon.setCha(daNang);
		nguHanhSon.setCapDonVi(2);
		nguHanhSon.setThanhThi(true);
		nguHanhSon.setMacDinh(true);
		nguHanhSon.setTen("Quận Ngũ Hành Sơn");
		nguHanhSon.saveNotShowNotification();
		
		DonViHanhChinh thanhKhe = new DonViHanhChinh();
		thanhKhe.setCha(daNang);
		thanhKhe.setCapDonVi(2);
		thanhKhe.setThanhThi(true);
		thanhKhe.setMacDinh(true);
		thanhKhe.setTen("Quận Thanh Khê");
		thanhKhe.saveNotShowNotification();
		
		DonViHanhChinh hoaVang = new DonViHanhChinh();
		hoaVang.setCha(daNang);
		hoaVang.setCapDonVi(2);
		hoaVang.setMacDinh(true);
		hoaVang.setThanhThi(false);
		hoaVang.setTen("Huyện Hòa Vang");
		hoaVang.saveNotShowNotification();
		
		DonViHanhChinh phuong = new DonViHanhChinh();
		
		//Hai Chau 13 phuong
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Bình Hiên");
		phuong.saveNotShowNotification();			
		phuong = new DonViHanhChinh();
		phuong.setCapDonVi(3);
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Bình Thuận");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hải Châu I");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setCapDonVi(3);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hải Châu II");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setCapDonVi(3);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Cường Bắc");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setCapDonVi(3);
		phuong.setTen("Phường Hòa Cường Nam");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setCapDonVi(3);
		phuong.setTen("Phường Hòa Thuận Đông");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setCapDonVi(3);
		phuong.setTen("Phường Hòa Thuận Tây");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setThanhThi(true);
		phuong.setMacDinh(true);
		phuong.setTen("Phường Nam Dương");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Phước Ninh");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setThanhThi(true);
		phuong.setMacDinh(true);
		phuong.setTen("Phường Thạch Thang");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Thanh Bình");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(haiChau);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Thuận Phước");
		phuong.saveNotShowNotification();	
		
		//Son Tra 7 phường
		phuong = new DonViHanhChinh();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường An Hải Bắc");
		phuong.saveNotShowNotification();	
		phuong = new DonViHanhChinh();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường An Hải Đông");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường An Hải Tây");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Mân Thái");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Nại Hiên Đông");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Phước Mỹ");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(sonTra);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Thọ Quang");
		phuong.saveNotShowNotification();
		
		//Cẩm lệ 6 phường
		phuong = new DonViHanhChinh();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa An");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Phát");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Thọ Đông");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Thọ Tây");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Xuân");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(camLe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Khuê Trung");
		phuong.saveNotShowNotification();
		
		//Liên chiều 5
		phuong = new DonViHanhChinh();
		phuong.setCha(lienChieu);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Hiệp Bắc");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCapDonVi(3);
		phuong.setCha(lienChieu);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Hiệp Nam");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(lienChieu);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Khánh Bắc");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(lienChieu);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Khánh Nam");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(lienChieu);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Minh");
		phuong.saveNotShowNotification();
		
		//Quan Ngu hanh son 4 phuong
		phuong = new DonViHanhChinh();
		phuong.setCha(nguHanhSon);
		phuong.setCapDonVi(3);
		phuong.setTen("Phường Hòa Hải");
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(nguHanhSon);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Quý");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(nguHanhSon);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Khuê Mỹ");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(nguHanhSon);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Mỹ An");
		phuong.setCapDonVi(3);
		phuong.saveNotShowNotification();
		
		//Quận Thanh Khê 11 phường
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường An Khê");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Chính Gián");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Hòa Khê");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Tam Thuận");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Tân Chính");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Thạch Gián");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Thanh Khê Đông");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Thanh Khê Tây");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Thạch Lộc Đán");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Vĩnh Trung");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(thanhKhe);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(true);
		phuong.setTen("Phường Xuân Hà");
		phuong.saveNotShowNotification();
		
		//Hoa vang 11 xa
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(false);
		phuong.setTen("Xã Hòa Bắc");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(false);
		phuong.setTen("Xã Hòa Châu");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(false);
		phuong.setTen("Xã Hòa Khương");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(false);
		phuong.setTen("Xã Hòa Liên");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(false);
		phuong.setTen("Xã Hòa Nhơn");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(false);
		phuong.setTen("Xã Hòa Ninh");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(false);
		phuong.setTen("Xã Hòa Phong");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setMacDinh(true);
		phuong.setThanhThi(false);
		phuong.setCapDonVi(3);
		phuong.setTen("Xã Hòa Phú");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(false);
		phuong.setTen("Xã Hòa Phước");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setCapDonVi(3);
		phuong.setMacDinh(true);
		phuong.setThanhThi(false);
		phuong.setTen("Xã Hòa Sơn");
		phuong.saveNotShowNotification();
		phuong = new DonViHanhChinh();
		phuong.setCha(hoaVang);
		phuong.setMacDinh(true);
		phuong.setCapDonVi(3);
		phuong.setThanhThi(false);
		phuong.setTen("Xã Hòa Tiến");
		phuong.saveNotShowNotification();
	}
}
