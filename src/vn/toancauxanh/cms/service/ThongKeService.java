package vn.toancauxanh.cms.service;

import java.util.Date;

import javax.persistence.Transient;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import vn.toancauxanh.gg.model.DonVi;
import vn.toancauxanh.gg.model.DonViHanhChinh;
import vn.toancauxanh.service.BasicService;

public class ThongKeService extends BasicService<Object>{
	private DonViHanhChinh selectedTinhThanhTK;
	private DonViHanhChinh selectedQuanHuyenTK;
	private DonViHanhChinh selectedPhuongXaTK;
	private DonVi selectedDonVi;
	private Date tuNgayTK;
	private Date denNgayTK;
	private boolean thongKe;
	
	public DonViHanhChinh getSelectedTinhThanhTK() {
		return selectedTinhThanhTK;
	}
	public void setSelectedTinhThanhTK(DonViHanhChinh selectedTinhThanhTK) {
		this.selectedTinhThanhTK = selectedTinhThanhTK;
	}
	public DonViHanhChinh getSelectedQuanHuyenTK() {
		return selectedQuanHuyenTK;
	}
	public void setSelectedQuanHuyenTK(DonViHanhChinh selectedQuanHuyenTK) {
		this.selectedQuanHuyenTK = selectedQuanHuyenTK;
	}
	public DonViHanhChinh getSelectedPhuongXaTK() {
		return selectedPhuongXaTK;
	}
	public void setSelectedPhuongXaTK(DonViHanhChinh selectedPhuongXaTK) {
		this.selectedPhuongXaTK = selectedPhuongXaTK;
	}
	public Date getTuNgayTK() {
		return tuNgayTK;
	}
	public void setTuNgayTK(Date tuNgayTK) {
		this.tuNgayTK = tuNgayTK;
	}
	public Date getDenNgayTK() {
		return denNgayTK;
	}
	public void setDenNgayTK(Date denNgayTK) {
		this.denNgayTK = denNgayTK;
	}
	public boolean isThongKe() {
		return thongKe;
	}
	public void setThongKe(boolean thongKe) {
		this.thongKe = thongKe;
	}
	public DonVi getSelectedDonVi() {
		return selectedDonVi;
	}
	public void setSelectedDonVi(DonVi selectedDonVi) {
		this.selectedDonVi = selectedDonVi;
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {				
				if (getTuNgayTK() != null && getDenNgayTK() != null && getDenNgayTK().before(getTuNgayTK())) {
					addInvalidMessage(ctx, "lblErrDenNgayTK","Đến ngày thống kê không được lớn hơn từ ngày thống kê.");
				}  
			}
		};
	}
}

