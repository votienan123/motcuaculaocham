package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.NhanVien;
import vn.toancauxanh.model.QNhanVien;

@Entity
@Table(name = "nhomnguoidung")
public class NhomNguoiDung extends Model<NhomNguoiDung> {

	private String ten = "";
	private String moTa = "";

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@Lob
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Command
	public void saveNhomNguoiDung(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr, @BindingParam("wdn") final Window wdn) {
		JPAQuery<NhanVien> nhanVienQuery = null;
		
		if (!noId()) {
			nhanVienQuery = find(NhanVien.class).where(QNhanVien.nhanVien.nhomNguoiDung.eq(this));
		}
		
		if (nhanVienQuery != null && nhanVienQuery.fetch().size() > 0 && !isCheckApDung()) {
			showNotification("Dữ liệu này đang được sử dụng!", "", "warning");
		} else {
			save();
			wdn.detach();
			BindUtils.postNotifyChange(null, null, listObject, attr);
		}
	}
	
	@Override
	@Command
	public void deleteTrangThaiConfirmAndNotify(final @BindingParam("notify") Object beanObject,
			final @BindingParam("attr") @Default(value = "*") String attr) {
		JPAQuery<NhanVien> nhanVienQuery = find(NhanVien.class).where(QNhanVien.nhanVien.nhomNguoiDung.eq(this));
		
		if (nhanVienQuery != null && nhanVienQuery.fetch().size() > 0) {
			showNotification("Dữ liệu này đang được sử dụng!", "", "warning");
		} else {
			super.deleteTrangThaiConfirmAndNotify(beanObject, attr);
		}
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				if (ten != null) {
					if (!ten.isEmpty()) {
						JPAQuery<NhomNguoiDung> q = find(NhomNguoiDung.class)
								.where(QNhomNguoiDung.nhomNguoiDung.ten.trim().eq(ten.trim()));
						if (!noId()) {
							q.where(QNhomNguoiDung.nhomNguoiDung.id.ne(getId()));
						}
						if (q.fetchCount() > 0) {
							addInvalidMessage(ctx, "lblErrTenNhomNguoiDung", "Tên nhóm người dùng đang được sử dụng.");
						}
					}
				}
			}
		};
	}
}
