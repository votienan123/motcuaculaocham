package vn.toancauxanh.gg.model.enums;

public enum LoaiHanhViViPham {
	NHOM_MA_TUY("Nhóm ma túy"),
	NHOM_HINH_SU("Nhóm hình sự");

	String text;

	LoaiHanhViViPham(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
}
