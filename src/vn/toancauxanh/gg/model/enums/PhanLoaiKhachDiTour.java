package vn.toancauxanh.gg.model.enums;

public enum PhanLoaiKhachDiTour {
	TOUR_BINH_THUONG("Tour hằng ngày"),
	TOUR_DI_RIENG("Tour đi riêng"),
	THUE_TAU_DI_RIENG("Thuê tàu đi riêng"),
	HAINGAY_MOTDEM("2 ngày 1 đêm");

	String text;

	PhanLoaiKhachDiTour(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
}
