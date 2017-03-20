package vn.toancauxanh.cms.service;

import java.util.Date;

import javax.persistence.Transient;

import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

import vn.toancauxanh.service.BasicService;

public class ThongKeCLCService extends BasicService<Object> {
	
	private Date ngayDatVe;
	private Date ngayThucHienTour;
	private Date tuNgayThucHienTour;
	private Date denNgayThucHienTour;
	
	public Date getNgayDatVe() {
		return ngayDatVe;
	}
	public void setNgayDatVe(Date ngayDatVe) {
		this.ngayDatVe = ngayDatVe;
	}
	public Date getNgayThucHienTour() {
		return ngayThucHienTour;
	}
	public void setNgayThucHienTour(Date ngayThucHienTour) {
		this.ngayThucHienTour = ngayThucHienTour;
	}

	public Date getTuNgayThucHienTour() {
		return tuNgayThucHienTour;
	}
	public void setTuNgayThucHienTour(Date tuNgayThucHienTour) {
		this.tuNgayThucHienTour = tuNgayThucHienTour;
	}

	public Date getDenNgayThucHienTour() {
		return denNgayThucHienTour;
	}
	public void setDenNgayThucHienTour(Date denNgayThucHienTour) {
		this.denNgayThucHienTour = denNgayThucHienTour;
	}

	
	/*@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {				
				if (getTuNgayTK() != null && getDenNgayTK() != null && getDenNgayTK().before(getTuNgayTK())) {
					addInvalidMessage(ctx, "lblErrDenNgayTK","Đến ngày thống kê không được lớn hơn từ ngày thống kê.");
				}  
			}
		};
	}*/
}
