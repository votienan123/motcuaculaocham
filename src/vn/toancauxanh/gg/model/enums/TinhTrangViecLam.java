package vn.toancauxanh.gg.model.enums;

public enum TinhTrangViecLam {
	CO_VIEC_ON_DINH("Có việc làm ổn định"),
	CO_VIEC_KHONG_ON_DINH("Có việc làm nhưng không ổn định"),
	KHONG_CO_VIEC("Không có việc làm");

	String text;

	TinhTrangViecLam(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
}
