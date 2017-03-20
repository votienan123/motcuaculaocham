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

@Entity
@Table(name="phanloaicongty")
public class PhanLoaiCongTy extends Model<PhanLoaiCongTy> {

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
	public void savePhanLoaiCongTy(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	@Override
	@Command
	public void deleteTrangThaiConfirmAndNotify(final @BindingParam("notify") Object beanObject,
			final @BindingParam("attr") @Default(value = "*") String attr) {
		JPAQuery<CongTyKinhDoanh> congTyKinhDoanhQuery = find(CongTyKinhDoanh.class)
				.where(QCongTyKinhDoanh.congTyKinhDoanh.phanLoaiCongTy.eq(this));
		
		if (congTyKinhDoanhQuery != null && congTyKinhDoanhQuery.fetch().size() > 0) {
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
						JPAQuery<PhanLoaiCongTy> q = find(PhanLoaiCongTy.class)
								.where(QPhanLoaiCongTy.phanLoaiCongTy.ten.trim().eq(ten.trim()));
						if (!noId()) {
							q.where(QPhanLoaiCongTy.phanLoaiCongTy.id.ne(getId()));
						}
						if (q.fetchCount() > 0) {
							addInvalidMessage(ctx, "lblErrTenPhanLoaiCongTy", "Tên loại công ty đang được sử dụng.");
						}
					}
				}
			}
		};
	}
}
