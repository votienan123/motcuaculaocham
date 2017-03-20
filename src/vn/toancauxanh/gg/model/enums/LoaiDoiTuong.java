package vn.toancauxanh.gg.model.enums;

public enum LoaiDoiTuong {
	NGUOI_NGHIEN_MA_TUY("Người nghiện ma túy"),
	SU_DUNG_TRAI_PHEP_MA_TUY("Người sử dụng trái phép ma túy"),
	TUY_TRUONG_HOP("Dựa vào hình thức trước đó để phân biệt"),;

	String text;

	LoaiDoiTuong(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
}
