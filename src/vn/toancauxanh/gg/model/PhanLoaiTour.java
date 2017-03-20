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
@Table(name = "phanloaitour")
public class PhanLoaiTour extends Model<PhanLoaiTour> {

	private String ten;
	private String moTa;
	private long giaNguoiLon;
	private long giaTreEm4Den9;
	private long giaTreEm1Den3;
	private boolean tourBuoiChieu;

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
	
	public long getGiaNguoiLon() {
		return giaNguoiLon;
	}

	public void setGiaNguoiLon(long giaNguoiLon) {
		this.giaNguoiLon = giaNguoiLon;
	}

	public long getGiaTreEm4Den9() {
		return giaTreEm4Den9;
	}

	public void setGiaTreEm4Den9(long giaTreEm4Den9) {
		this.giaTreEm4Den9 = giaTreEm4Den9;
	}

	public long getGiaTreEm1Den3() {
		return giaTreEm1Den3;
	}

	public void setGiaTreEm1Den3(long giaTreEm1Den3) {
		this.giaTreEm1Den3 = giaTreEm1Den3;
	}
	
	public boolean isTourBuoiChieu() {
		return tourBuoiChieu;
	}

	public void setTourBuoiChieu(boolean tourBuoiChieu) {
		this.tourBuoiChieu = tourBuoiChieu;
	}

	@Command
	public void savePhanLoaiTour(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}

	@Override
	@Command
	public void deleteTrangThaiConfirmAndNotify(final @BindingParam("notify") Object beanObject,
			final @BindingParam("attr") @Default(value = "*") String attr) {

		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.phanLoaiTour.eq(this));

		if (datVeQuery != null && datVeQuery.fetch().size() > 0) {
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
						JPAQuery<PhanLoaiTour> q = find(PhanLoaiTour.class)
								.where(QPhanLoaiTour.phanLoaiTour.ten.trim().eq(ten.trim()));
						if (!noId()) {
							q.where(QPhanLoaiTour.phanLoaiTour.id.ne(getId()));
						}
						if (q.fetchCount() > 0) {
							addInvalidMessage(ctx, "lblErrTenLoaiTour", "Tên phân loại tour đang được sử dụng.");
						}
					}
				}				
			}
		};
	}
}
