package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "tau")
public class Tau extends Model<Tau> {

	private String ten = "";
	private String moTa = "";
	private CongTyKinhDoanh congTyKinhDoanh;
	private long soGhe = 0;
	
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

	public long getSoGhe() {
		return soGhe;
	}

	public void setSoGhe(long soGhe) {
		this.soGhe = soGhe;
	}

	@ManyToOne
	public CongTyKinhDoanh getCongTyKinhDoanh() {
		return congTyKinhDoanh;
	}

	public void setCongTyKinhDoanh(CongTyKinhDoanh congTyKinhDoanh) {
		this.congTyKinhDoanh = congTyKinhDoanh;
	}
	
	@Command
	public void saveTau(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {

		// Save Tau's information
		save();

		// Reset soKhachToiDa (CongTyKinhDoanh)
		long soKhachToiDaCongTyCapNhat = 0;
		JPAQuery<Tau> tauList = find(Tau.class).where(QTau.tau.congTyKinhDoanh.eq(getCongTyKinhDoanh()));
		for (Tau i : tauList.fetch()) {
			soKhachToiDaCongTyCapNhat += i.getSoGhe();
		}

		getCongTyKinhDoanh().setSoKhachToiDa(soKhachToiDaCongTyCapNhat);
		getCongTyKinhDoanh().saveNotShowNotification();

		// Reset tongSoGhe (NhomCuaHoi)
		long tongSoGhe = 0;
		JPAQuery<CongTyKinhDoanh> congTyKinhDoanhList = find(CongTyKinhDoanh.class)
				.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(getCongTyKinhDoanh().getNhomCuaHoi()));
		for (CongTyKinhDoanh i : congTyKinhDoanhList.fetch()) {
			tongSoGhe += i.getSoKhachToiDa();
		}
		getCongTyKinhDoanh().getNhomCuaHoi().setTongSoGhe(tongSoGhe);
		getCongTyKinhDoanh().getNhomCuaHoi().saveNotShowNotification();

		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}

	@Override
	@Command
	public void deleteTrangThaiConfirmAndNotify(final @BindingParam("notify") Object beanObject,
			final @BindingParam("attr") @Default(value = "*") String attr) {
		if (!checkInUse()) {
			Messagebox.show("Bạn muốn xoá dữ liệu này ?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								doDelete(true);
								
								// Reset soKhachToiDa (CongTyKinhDoanh)
								long soKhachToiDaCongTyCapNhat = 0;
								JPAQuery<Tau> tauList = find(Tau.class).where(QTau.tau.congTyKinhDoanh.eq(getCongTyKinhDoanh()));
								for (Tau i : tauList.fetch()) {
									soKhachToiDaCongTyCapNhat += i.getSoGhe();
								}

								getCongTyKinhDoanh().setSoKhachToiDa(soKhachToiDaCongTyCapNhat);
								getCongTyKinhDoanh().saveNotShowNotification();

								// Reset tongSoGhe (NhomCuaHoi)
								long tongSoGhe = 0;
								JPAQuery<CongTyKinhDoanh> congTyKinhDoanhList = find(CongTyKinhDoanh.class)
										.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.eq(getCongTyKinhDoanh().getNhomCuaHoi()));
								for (CongTyKinhDoanh i : congTyKinhDoanhList.fetch()) {
									tongSoGhe += i.getSoKhachToiDa();
								}
								getCongTyKinhDoanh().getNhomCuaHoi().setTongSoGhe(tongSoGhe);
								getCongTyKinhDoanh().getNhomCuaHoi().saveNotShowNotification();
								
								showNotification("Xóa dữ liệu thành công!", "", "success");
								if (beanObject != null) {
									BindUtils.postNotifyChange(null, null, beanObject, attr);
									if (beanObject != Tau.this) {
										BindUtils.postNotifyChange(null, null, Tau.this, "*");
									}
								}
							}
						}
					});
		}

	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				if (ten != null) {
					if (!ten.isEmpty()) {
						JPAQuery<Tau> q = find(Tau.class).where(QTau.tau.ten.trim().eq(ten.trim()));
						if (!noId()) {
							q.where(QTau.tau.id.ne(getId()));
						}
						if (congTyKinhDoanh != null) {
							q.where(QTau.tau.congTyKinhDoanh.eq(getCongTyKinhDoanh()));
						}
						if (q.fetchCount() > 0) {
							addInvalidMessage(ctx, "lblErrTenTau", "Tên tàu đang được sử dụng.");
						}
					}
				}
				
				if (soGhe <= 0) {
					addInvalidMessage(ctx, "lblErrSoLuongGhe", "Số ghế phải lớn hơn 0.");				
				}
			}
		};
	}
}
