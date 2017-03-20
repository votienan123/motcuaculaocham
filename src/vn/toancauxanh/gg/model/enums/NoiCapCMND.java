package vn.toancauxanh.gg.model.enums;

public enum NoiCapCMND {
	DA_NANG("CA TP.Đà Nẵng"),
	NOI_KHAC("CA tỉnh, thành phố khác");

	String text;

	NoiCapCMND(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
}
