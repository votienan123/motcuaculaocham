package vn.toancauxanh.gg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "tour")
public class Tour extends Model<Tour> {
	private NhomCuaHoi nhomCuaHoi;
	private CongTyKinhDoanh congTyKinhDoanh;
	private CongTyKinhDoanh congTyDieuTiet;
	private Tau tau;
	private String maBaoTon = "";
	private Date ngayThucHienTour;
	private int soLuongKhachDieuTiet = 0;
	private int soLuongKhachThucHienTour = 0;
	private int soLuongVePhongBanVeLe = 0;
	private boolean laTauCongVu;
	private int soLuongKhachThucHienTourThanhCong = 0;
	
	private int soLuongNguoiLon = 0;
	private int soLuongTreEm1Den3 = 0;
	private int soLuongTrEm4Den9 = 0;
	private boolean isCoTau;
	
	@ManyToOne
	public NhomCuaHoi getNhomCuaHoi() {
		return nhomCuaHoi;
	}

	public void setNhomCuaHoi(NhomCuaHoi nhomCuaHoi) {
		this.nhomCuaHoi = nhomCuaHoi;
		BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanhListAndNull");
	}

	@ManyToOne
	public CongTyKinhDoanh getCongTyDieuTiet() {
		return congTyDieuTiet;
	}

	public void setCongTyDieuTiet(CongTyKinhDoanh congTyDieuTiet) {
		this.congTyDieuTiet = congTyDieuTiet;
	}
	
	@ManyToOne
	public CongTyKinhDoanh getCongTyKinhDoanh() {
		return congTyKinhDoanh;
	}

	public void setCongTyKinhDoanh(CongTyKinhDoanh congTyKinhDoanh) {
		this.congTyKinhDoanh = congTyKinhDoanh;
	}

	@ManyToOne
	public Tau getTau() {
		return tau;
	}

	public void setTau(Tau tau) {
		this.tau = tau;
	}

	public Date getNgayThucHienTour() {
		return ngayThucHienTour;
	}

	public void setNgayThucHienTour(Date ngayThucHienTour) {
		this.ngayThucHienTour = ngayThucHienTour;
	}

	public int getSoLuongKhachDieuTiet() {
		return soLuongKhachDieuTiet;
	}

	public void setSoLuongKhachDieuTiet(int soLuongKhachDieuTiet) {
		this.soLuongKhachDieuTiet = soLuongKhachDieuTiet;
	}

	public boolean isLaTauCongVu() {
		return laTauCongVu;
	}

	public void setLaTauCongVu(boolean laTauCongVu) {
		this.laTauCongVu = laTauCongVu;
	}
	
	public int getSoLuongNguoiLon() {
		return soLuongNguoiLon;
	}

	public void setSoLuongNguoiLon(int soLuongNguoiLon) {
		this.soLuongNguoiLon = soLuongNguoiLon;
	}

	public int getSoLuongTreEm1Den3() {
		return soLuongTreEm1Den3;
	}

	public void setSoLuongTreEm1Den3(int soLuongTreEm1Den3) {
		this.soLuongTreEm1Den3 = soLuongTreEm1Den3;
	}

	public int getSoLuongTrEm4Den9() {
		return soLuongTrEm4Den9;
	}

	public void setSoLuongTrEm4Den9(int soLuongTrEm4Den9) {
		this.soLuongTrEm4Den9 = soLuongTrEm4Den9;
	}
	
	public String getMaBaoTon() {
		return maBaoTon;
	}

	public void setMaBaoTon(String maBaoTon) {
		this.maBaoTon = maBaoTon;
	}
	
	public boolean isCoTau() {
		return isCoTau;
	}

	public void setCoTau(boolean isCoTau) {
		this.isCoTau = isCoTau;
	}

	/* --------------------------------- PROPERTIES OTHER --------------------------------- */
	@Transient
	public int getSoLuongKhachThucHienTourThanhCong() {
		soLuongKhachThucHienTourThanhCong = soLuongKhachDieuTiet;
		return soLuongKhachThucHienTourThanhCong;
	}

	public void setSoLuongKhachThucHienTourThanhCong(int soLuongKhachThucHienTourThanhCong) {
		this.soLuongKhachThucHienTourThanhCong = soLuongKhachThucHienTourThanhCong;
	}

	@Transient
	public int getSoLuongKhachThucHienTour() {		
		return soLuongKhachThucHienTour;
	}

	public void setSoLuongKhachThucHienTour(int soLuongKhachThucHienTour) {
		this.soLuongKhachThucHienTour = soLuongKhachThucHienTour;
	}
	
	public void setSoLuongVePhongBanVeLe(int soLuongVePhongBanVeLe) {
		this.soLuongVePhongBanVeLe = soLuongVePhongBanVeLe;
	}

	@Transient
	public int getSoLuongVePhongBanVeLe() {
		return soLuongVePhongBanVeLe;
	}

	@Transient
	public List<CongTyKinhDoanh> getCongTyKinhDoanhListAndNull() {
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		congTyKinhDoanhList.add(null);
		if (nhomCuaHoi != null) {
			congTyKinhDoanhList.addAll(
					find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
							.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.id.eq(nhomCuaHoi.getId()))
							.orderBy(QNhomCuaHoi.nhomCuaHoi.ten.asc()).fetch());
		}

		return congTyKinhDoanhList;
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				
			}
		};
	}
}
