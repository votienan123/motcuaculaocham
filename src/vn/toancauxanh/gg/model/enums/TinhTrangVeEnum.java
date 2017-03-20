package vn.toancauxanh.gg.model.enums;

public enum TinhTrangVeEnum {
	
	DAT_VE("Đặt vé"),
	HUY_VE("Huỷ vé"),
	PHUC_HOI_VE("Phục hồi vé");

	String text;

	TinhTrangVeEnum(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
	
}
