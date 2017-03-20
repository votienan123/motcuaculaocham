package vn.toancauxanh.gg.model.enums;

public enum HinhThucXuLyEnum {
	PHAT_TIEN("Phạt tiền"),
	CANH_CAO("Cảnh cáo"),
	CAI_NGHIEN_TU_NGUYEN_TAI_GIA_DINH("Cai nghiện tự nguyện tại gia đình"),
	CAI_NGHIEN_TU_NGUYEN_TAI_CONG_DONG("Cai nghiện tự nguyện tại cộng đồng"),
	CAI_NGHIEN_BAT_BUOC_TAI_CONG_DONG("Cai nghiện bắt buộc tại cộng đồng"),
	GIAO_DUC_TAI_XA_PHUONG("Giáo dục tại xã phường"),
	TAP_TRUNG_CAI_NGHIEN_BAT_BUOC("Tập trung cai nghiện bắt buộc"),
	TAP_TRUNG_CAI_NGHIEN_TU_NGUYEN("Tập trung cai nghiện tự nguyện"),
	DUA_VAO_CO_SO_GIAO_DUC("Đưa vào cơ sở giáo dục"),
	DUA_VAO_TRUONG_GIAO_DUONG("Đưa vào trường giáo dưỡng"),
	DIEU_TRI_METHADONE("Điều trị bằng Methadone"),
	DIEU_TRI_LOAN_THAN("Điều trị loạn thần"),
	DANG_QUAN_LY_SAU_CAI("Đang quản lý sau cai"),
	BI_XU_LY_HINH_SU_O_NHA_TAM_GIU("Bị xử lý hình sự ở nhà tạm giữ"),
	BI_XU_LY_HINH_SU_O_TRAI_TAM_GIAM("Bị xử lý hình sự ở trại tạm giam"),
	BI_XU_LY_HINH_SU_DANG_DUOC_TAI_NGOAI("Bị xử lý hình sự đang được tại ngoại");

	String text;

	HinhThucXuLyEnum(final String txt) {
		text = txt;
	}

	public String getText() {
		return text;
	}
}
