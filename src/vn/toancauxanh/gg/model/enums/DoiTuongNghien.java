package vn.toancauxanh.gg.model.enums;

public enum DoiTuongNghien {
	NGHIEN_MOI("Nghiện mới"),
	TAI_NGHIEN("Tái nghiện");

	String text;

	DoiTuongNghien(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
}
