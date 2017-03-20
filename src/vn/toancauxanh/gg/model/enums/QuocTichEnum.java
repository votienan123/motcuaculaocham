package vn.toancauxanh.gg.model.enums;

public enum QuocTichEnum {
	
	VIETNAM("Việt nam"),
	HANQUOC("Hàn Quốc"),
	CHINA("Trung Quốc"),
	QUOC_GIA_KHAC("Quốc gia khác");

	String text;

	QuocTichEnum(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
	
}
