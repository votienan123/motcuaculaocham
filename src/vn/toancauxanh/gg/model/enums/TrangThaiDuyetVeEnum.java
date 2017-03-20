package vn.toancauxanh.gg.model.enums;

public enum TrangThaiDuyetVeEnum {
	
	DA_DUYET("Đã duyệt"),
	DANG_DUYET("Đang duyệt");

	String text;

	TrangThaiDuyetVeEnum(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
	
}
