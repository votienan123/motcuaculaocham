package vn.toancauxanh.gg.model;

public enum Positions {
	banner_right("Banner bên phải"),
	banner_header("Banner header"),
	banner_header_l("Banner header trái"),
	banner_header_r("Banner header phải"),
	banner_middle("Banner middle"),
	banner_middle_l("Banner middle trái"),
	banner_middle_r("Banner middle phải"),
	banner_footer("Banner footer"),
	banner_footer_l("Banner footer trái"),
	banner_footer_r("Banner footer phải");

	String text;

	Positions(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
}
