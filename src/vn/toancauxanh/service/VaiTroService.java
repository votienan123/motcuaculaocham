package vn.toancauxanh.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.util.resource.Labels;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.QVaiTro;
import vn.toancauxanh.model.VaiTro;

public final class VaiTroService extends BasicService<VaiTro> {
	
	public List<VaiTro> getListAllVaiTroAndNull() {
		JPAQuery<VaiTro> q = find(VaiTro.class);
		List<VaiTro> list = new ArrayList<>();
		list.add(null);
		bootstrap();
		for (VaiTro vaiTro : q.where(QVaiTro.vaiTro.trangThai.ne(core().TT_DA_XOA)).fetch()) {
			list.add(vaiTro);
		}
		return list;
	}

	private Map<String, String> listDefaultAlias = new HashMap<>();
	
	public Map<String, String> getListDefaultAlias(){
		if(listDefaultAlias.isEmpty()){
			/*listDefaultAlias.put(VaiTro.PHONGCSMT, "Phòng CSMT Công an Thành phố");
			listDefaultAlias.put(VaiTro.DOICSMTCONGANQUANHUYEN, "Đội CSMT Công an Quận/Huyện");
			listDefaultAlias.put(VaiTro.CONGANPHUONGXA, "Công an Phường/Xã");
			listDefaultAlias.put(VaiTro.NHATAMGIUCONGANQUANHUYEN, "Nhà tạm giữ Công an Quận/Huyện");
			listDefaultAlias.put(VaiTro.TRAITAMGIAMCONGANTHANHPHO, "Trại tạm giam Công an Thành phố");
			listDefaultAlias.put(VaiTro.BENHVIENTAMTHAN, "Bệnh viện tâm thần");
			listDefaultAlias.put(VaiTro.COSOMETHADONE, "Cơ sở Methadone");
			listDefaultAlias.put(VaiTro.TRUNGTAMGIAODUCDAYNGHE, "Trung tâm giáo dục dạy nghề");
			listDefaultAlias.put(VaiTro.UBNDPHUONGXA, "UBND Phường/Xã, Tổ chức đoàn thể");
			listDefaultAlias.put(VaiTro.PHONGCSHS, "Phòng CSHS Công an Thành phố");
			listDefaultAlias.put(VaiTro.DOICSHSCONGANQUANHUYEN, "Đội CSHS Công an Quận/Huyện");*/
			
			listDefaultAlias.put(VaiTro.BANDIEUHANH, "Ban điều hành hiệp hội");
			listDefaultAlias.put(VaiTro.KETOAN, "Nhóm kế toán");
			listDefaultAlias.put(VaiTro.NHOMTRUONG, "Nhóm trưởng");
			listDefaultAlias.put(VaiTro.THANHVIEN, "Nhóm thành viên");
			listDefaultAlias.put(VaiTro.PHONGBANVELE, "Nhóm phòng bán vé lẻ");
			listDefaultAlias.put(VaiTro.ADMIN, "Quản trị viên (admin)");
		}
		return listDefaultAlias;
	}
	
	public QVaiTro getEntityPath() {
		return QVaiTro.vaiTro;

	}

	public void bootstrap() {
		if (find(VaiTro.class).fetchCount() == 0) {
			for (String vai : VaiTro.VAITRO_DEFAULTS) {
				VaiTro vaiTro = new VaiTro(Labels.getLabel("vaitro." + vai), vai);
				vaiTro.save();
			}
		}
	}

	public JPAQuery<VaiTro> getVaiTroQuery() {
		String param = MapUtils.getString(argDeco(), "tukhoa", "").trim();
		String trangThai = MapUtils.getString(argDeco(), "trangthai", "");
		JPAQuery<VaiTro> q = find(VaiTro.class).where(QVaiTro.vaiTro.trangThai.ne(core().TT_DA_XOA));
		if (param != null && !param.isEmpty()) {
			String tukhoa = "%" + param + "%";
			q.where(QVaiTro.vaiTro.tenVaiTro.like(tukhoa));
		}
		if (!trangThai.isEmpty()) {
			q.where(QVaiTro.vaiTro.trangThai.eq(trangThai));
		}
		q.orderBy(QVaiTro.vaiTro.soThuTu.asc());
		return q;
	}

	public VaiTro findOrNewByAlias(String alias) {
		VaiTro find = find(VaiTro.class).where(QVaiTro.vaiTro.alias.eq(alias)).fetchFirst();
		return find == null ? new VaiTro() : find;
	}

	private String img = "/backend/assets/img/edit.png";
	private String hoverImg = "/backend/assets/img/edit_hover.png";
	private String strUpdate = "Thao tác";
	private boolean update = true;
	private boolean updateThanhCong = true;

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
	public void clickButton(@BindingParam("model") final List<VaiTro> model) {
		if (strUpdate.equals("Thao tác")) {
			setStrUpdate("Lưu");
			setImg("/backend/assets/img/save.png");
			setHoverImg("/backend/assets/img/save_hover.png");
			setUpdate(false);
		} else {
			setUpdateThanhCong(true);
			for (VaiTro menu : model) {
				if (check(menu)) {
					setUpdateThanhCong(false);
					break;
				}
				menu.save();
			}
			if (isUpdateThanhCong()) {
				setStrUpdate("Thao tác");
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
		BindUtils.postNotifyChange(null, null, this, "vaiTroQuery");
	}

	private boolean check(VaiTro cat) {
		if ("".equals(cat.getSoThuTu()) || cat.getSoThuTu() < 0) {
			return true;
		}
		return false;
	}
}
