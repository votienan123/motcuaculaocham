package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.annotation.Init;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.HanhViViPham;
import vn.toancauxanh.gg.model.QHanhViViPham;
import vn.toancauxanh.gg.model.enums.LoaiHanhViViPham;
import vn.toancauxanh.service.BasicService;

public class HanhViViPhamService extends BasicService<HanhViViPham>{
		
	private LoaiHanhViViPham selectedLoaiHVVP;
	
	
	@Init
	public void init() {
		List<HanhViViPham> list = find(HanhViViPham.class)
				.where(QHanhViViPham.hanhViViPham.trangThai.ne(core().TT_DA_XOA))
				.fetch();
		if (list == null || list.isEmpty()) {
			bootstrapHanhViViPham();
		}
	}
	
	public LoaiHanhViViPham getSelectedLoaiHVVP() {
		return selectedLoaiHVVP;
	}

	public void setSelectedLoaiHVVP(LoaiHanhViViPham selectedLoaiHVVP) {
		this.selectedLoaiHVVP = selectedLoaiHVVP;
	}

	public List<HanhViViPham> getListHanhViViPham() {
		List<HanhViViPham> list = new ArrayList<HanhViViPham>();
		list = find(HanhViViPham.class)
				.where(QHanhViViPham.hanhViViPham.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QHanhViViPham.hanhViViPham.ten.asc())
				.fetch();
		return list;
	}
	
	public List<HanhViViPham> getListHanhViViPhamAndNull() {
		List<HanhViViPham> list = new ArrayList<HanhViViPham>();
		list.add(null);
		list.addAll(getListHanhViViPham());
		return list;
	}
	
	public JPAQuery<HanhViViPham> getTargetQuery() {
		String tuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"),"").trim();
		String trangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "");

		JPAQuery<HanhViViPham> phongBan = find(HanhViViPham.class)
				.where(QHanhViViPham.hanhViViPham.trangThai.ne(core().TT_DA_XOA));

		if (tuKhoa != null && !tuKhoa.isEmpty()) {
			String tukhoa = "%" + tuKhoa + "%";
			phongBan.where(QHanhViViPham.hanhViViPham.ten.like(tukhoa));	
		}
		if (!trangThai.isEmpty()) {
			phongBan.where(QHanhViViPham.hanhViViPham.trangThai.eq(trangThai));
		}
		
		if (selectedLoaiHVVP != null) {
			phongBan.where(QHanhViViPham.hanhViViPham.loaiHanhViViPham.eq(selectedLoaiHVVP));
		}
		phongBan.orderBy(QHanhViViPham.hanhViViPham.ngaySua.desc());
		return phongBan;
	}
	
	public void bootstrapHanhViViPham() {
		HanhViViPham hanhVi = new HanhViViPham("Tàng trữ ma túy",LoaiHanhViViPham.NHOM_MA_TUY);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Vận chuyển ma túy",LoaiHanhViViPham.NHOM_MA_TUY);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Mua bán ma túy",LoaiHanhViViPham.NHOM_MA_TUY);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Tổ chức sử dụng ma túy",LoaiHanhViViPham.NHOM_MA_TUY);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Sử dụng trái phép ma túy",LoaiHanhViViPham.NHOM_MA_TUY);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Lừa đảo",LoaiHanhViViPham.NHOM_HINH_SU);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Trộm cắp",LoaiHanhViViPham.NHOM_HINH_SU);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Cướp",LoaiHanhViViPham.NHOM_HINH_SU);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Cướp giật",LoaiHanhViViPham.NHOM_HINH_SU);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Cưỡng đoạt tài sản",LoaiHanhViViPham.NHOM_HINH_SU);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Cố ý gây thương tích",LoaiHanhViViPham.NHOM_HINH_SU);
		hanhVi.saveNotShowNotification();
		hanhVi = new HanhViViPham("Gây rối trật tự công cộng",LoaiHanhViViPham.NHOM_HINH_SU);
		hanhVi.saveNotShowNotification();
	}
}
