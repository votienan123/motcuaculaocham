package vn.toancauxanh.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Logger;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Store;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;
import com.querydsl.core.annotations.QueryInit;

import vn.toancauxanh.gg.model.PropertyChangeObject;
import vn.toancauxanh.service.BaseObject;
import vn.toancauxanh.service.BasicService;
import vn.toancauxanh.service.Quyen;

@MappedSuperclass
// @Cacheable(true)
public class Model<T extends Model<T>> extends BaseObject<T> {
	// private static final long serialVersionUID = 4219024869115773046L;

	private NhanVien nguoiSua;

	private NhanVien nguoiTao;

	private String trangThai = core() == null ? "ap_dung" : core().TT_AP_DUNG;

	private Long id;
	private Date ngayTao;
	private Date ngaySua;
	private boolean daXoa;

	public Date getNgaySua() {
		return this.ngaySua;
	}

	public Date getNgayTao() {
		return this.ngayTao;
	}

	public boolean isDaXoa() {
		return this.daXoa;
	}

	@Override
	public void setDaXoa(boolean deleted) {
		this.daXoa = deleted;
		if (deleted) {
			setTrangThai(core().TT_DA_XOA);
		}
	}

	public void setId(final Long _id) {
		this.id = _id != null && _id.longValue() == 0L ? null : _id;
	}

	public void setNgaySua(Date ngaySua1) {
		this.ngaySua = ngaySua1;
	}

	public void setNgayTao(Date ngayTao1) {
		this.ngayTao = ngayTao1;
	}

	@Override
	@Id
	@GeneratedValue
	public Long getId() {
		return id == null ? Long.valueOf(0) : id;
	}

	@Transient
	public boolean isCoQuyenChinhSua() {
		return core().getNhanVien().equals(nguoiTao);
	}

	@Override
	public void doSave() {
		setNgaySua(new Date());
		setNguoiSua(core().fetchNhanVien(true));
		if (noId()) {
			setNgayTao(getNgaySua());
			setNguoiTao(getNguoiSua());
		}
		super.doSave();
	}

	@Transient
	public boolean permitted() {
		return !core().TT_DA_XOA.equals(trangThai);
	}

	@Command
	public void deleteTrangThaiConfirmAndNotify(final @BindingParam("notify") Object beanObject,
			final @BindingParam("attr") @Default(value = "*") String attr) {
		if (!checkInUse()) {
			Messagebox.show("Bạn muốn xoá dữ liệu này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								doDelete(true);
								showNotification("Xóa dữ liệu thành công!", "", "success");
								if (beanObject != null) {
									BindUtils.postNotifyChange(null, null, beanObject, attr);
									if (beanObject != Model.this) {
										BindUtils.postNotifyChange(null, null, Model.this, "*");
									}
								}
							}
						}
					});
		}

	}

	@Command
	public void deleteRemoveConfirm() {
		if (!checkInUse()) {
			Messagebox.show("Bạn muốn xoá dữ liệu này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								doDelete(false);
								showNotification("Xóa dữ liệu thành công!", "", "success");
							}
						}
					});
		}
	}

	@Transient
	public String getTrangThaiText() {
		return new BasicService<>().getTrangThaiList().get(getTrangThai());
	}

	@QueryInit("*.*.*.*")
	@ManyToOne
	public NhanVien getNguoiSua() {
		return nguoiSua;
	}

	@QueryInit("*.*.*.*")
	@ManyToOne
	public NhanVien getNguoiTao() {
		return nguoiTao;
	}

	@Transient
	public NhanVien getOrNewNguoiTao() {
		NhanVien result = nguoiTao;
		if (result == null) {
			result = new NhanVien();
		}
		return result;
	}

	@Field(index = org.hibernate.search.annotations.Index.YES, analyze = Analyze.NO, store = Store.NO)
	public String getTrangThai() {
		return trangThai;
	}

	/*
	 * @Transient public boolean isDuocLuu() { return
	 * core().getQuyen().get(core().BAIVIETTHEM) ||
	 * core().getQuyen().get(core().BAIVIETSUA); }
	 * 
	 * @Transient public boolean isDuocSua() { return
	 * core().getQuyen().get(core().BAIVIETSUA); }
	 */

	@Transient
	public String getLoaiNoiDung() {
		return getClass().getSimpleName();
	}

	@Transient
	public String getTrangThaiSoan() {
		return trangThai;
	}

	@Transient
	public String setStylePublishStatus(boolean publishStatus) {
		if (publishStatus) {
			return "label label-success";
		}
		return "label label-default";
	}

	protected String businessKey() {
		return getClass().getName() + "@" + getId();
	}

	@Transient
	public boolean isApDung() {
		return core().TT_AP_DUNG.equals(trangThai);
	}

	@Command
	public void saveAndContinue() {
		saveNotRedirect();
		// getContextResource().openAddNoConfirm();
	}

	@Command
	public void saveNotRedirect() {
		saveValidate();
		showNotification("Đã lưu thành công!", "", "success");
	}

	@Command
	public void saveRedirect() {
		saveNotRedirect();
		// getContextResource().openListNoConfirm();
	}

	public void saveValidate() {
		validate();
		save();
	}

	@Override
	public void save() {
		transactioner().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				Logger.getGlobal().info(getId() + "");
				boolean noid = noId();
				doSave();
				if (noid) {
					showNotification("Đã lưu thành công!", "", "success");
				} else {
					showNotification("Đã cập nhật thành công!", "", "success");
				}
				Logger.getGlobal().info(getId() + "");
			}
		});
	}

	public void saveNotShowNotification() {
		doSave();
	}

	public void setApDung(final boolean isApdung) {
		if (isApdung != isApDung()) {
			trangThai = isApdung ? core().TT_AP_DUNG : core().TT_KHONG_AP_DUNG;
		}
	}

	public void setNguoiSua(final NhanVien _nguoiSua) {
		nguoiSua = _nguoiSua;
	}

	public void setNguoiTao(final NhanVien _nguoiTao) {
		nguoiTao = _nguoiTao;
	}

	public void setTrangThai(final String _trangThai) {
		trangThai = Strings.nullToEmpty(_trangThai);
	}

	public void validate() {
		//
	}

	@Transient
	public String getDocumentType() {
		return "";
	}

	@Transient
	public Quyen getQuyen(String resource) {
		return new Quyen(core().getQuyen().getRealm(), resource, getId(), getNguoiTao());
	}

	@Command
	public void cancelPopup(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}

	private boolean checkApDung;

	@Transient
	public boolean isCheckApDung() {
		checkApDung = false;
		if (core().TT_AP_DUNG.equals(getTrangThai())) {
			checkApDung = true;
		}
		return checkApDung;
	}

	public void setCheckApDung(final boolean _isCheckApDung) {
		if (_isCheckApDung) {
			setTrangThai(core().TT_AP_DUNG);
		} else {
			setTrangThai(core().TT_KHONG_AP_DUNG);
		}
		this.checkApDung = _isCheckApDung;
	}

	@Transient
	public String getDate2String(Date date) {
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("dd/MM/yyyy");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT+7"));
		String out = "";
		if (date != null) {
			out = dateFormatGmt.format(date);
		}
		return out;
	}

	@Transient
	public String getChiTietThayDoi(final List<PropertyChangeObject> list) {
		String out = "";
		if (list.size() > 0) {
			out += "[";
			for (PropertyChangeObject obj : list) {
				out += "{'propertyName': '" + obj.getPropertyName() + "'," + "'oldValue': '" + obj.getOldValue()
						+ "','newValue': '" + obj.getNewValue() + "'},";
			}
			out += "]";
			out = out.replaceAll("},]", "}]");
		}
		return out;
	}

}
