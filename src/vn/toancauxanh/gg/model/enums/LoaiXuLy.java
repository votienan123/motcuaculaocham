package vn.toancauxanh.gg.model.enums;

public enum LoaiXuLy {
	XU_LY_HINH_SU("Xử lý hình sự"),
	XU_LY_HANH_CHINH("Xử lý hành chính");

	String text;

	LoaiXuLy(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
}
