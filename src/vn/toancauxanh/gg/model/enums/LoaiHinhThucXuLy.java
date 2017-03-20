package vn.toancauxanh.gg.model.enums;

public enum LoaiHinhThucXuLy {
	TIEN_AN_TIEN_SU("Tiền án, Tiền sự"),
	XU_PHAT_HANH_CHINH("Xử phạt hành chính"),
	LICH_SU_KHAC("Lịch sử khác");

	String text;

	LoaiHinhThucXuLy(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
}
