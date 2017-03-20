package vn.toancauxanh.gg.model.enums;

public enum LoaiPhongVeLeEnum {
	
	PHONG_VE_LE_TRUC_TIEP("Phòng vé lẻ trực tiếp"),
	PHONG_VE_LE_GIAN_TIEP("Phòng vé lẻ gián tiếp");

	String text;

	LoaiPhongVeLeEnum(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
	
}
