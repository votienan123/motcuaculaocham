package vn.toancauxanh.gg.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.cms.service.DatVeService;
import vn.toancauxanh.gg.model.enums.LoaiPhongVeLeEnum;
import vn.toancauxanh.gg.model.enums.PhanLoaiKhachDiTour;
import vn.toancauxanh.gg.model.enums.QuocTichEnum;
import vn.toancauxanh.gg.model.enums.TinhTrangVeEnum;
import vn.toancauxanh.gg.model.enums.TrangThaiDuyetVeEnum;
import vn.toancauxanh.model.Model;
import vn.toancauxanh.model.Setting;

@Entity
@Table(name = "datve")
public class DatVe extends Model<DatVe> implements PropertyChangeListener {

	private String hoVaTen = "";
	private String soCMND = "";
	private String passport = "";
	private String diaChi = "";
	private String ghiChu = "";
	private String lyDoTinhTrangVe = "";
	private String soDienThoai = "";

	private Date ngaySinh;
	private Date ngayKhachDat = new Date();
	private Date ngayThucHienTour;

	private int soLuongNguoiLon = 0;
	private int soLuongTreEmDuoi10Tuoi = 0;
	private int soLuongTreEmDuoi5Tuoi = 0;
	private int soLuongNguoiLonBanDau = 0;
	private int soLuongTreEmDuoi10TuoiBanDau = 0;
	private int soLuongTreEmDuoi5TuoiBanDau = 0;

	private long giaDichVu;

	private QuocTichEnum quocTich = QuocTichEnum.VIETNAM;
	private TinhTrangVeEnum tinhTrangVe = TinhTrangVeEnum.DAT_VE;
	private LoaiPhongVeLeEnum loaiPhongBanVeLe = null;
	private TrangThaiDuyetVeEnum trangThaiDuyetVeEnum = TrangThaiDuyetVeEnum.DA_DUYET;

	private NhomCuaHoi nhomCuaHoi;
	private CongTyKinhDoanh congTyKinhDoanh;
	private PhanLoaiTour phanLoaiTour;
	private GiaVe2Ngay1Dem giaVe2Ngay1Dem;
	private PhanLoaiKhachThueTau phanLoaiKhachThueTau;
	private PhanLoaiKhachDiTour phanLoaiKhachDiTour;
	private GiaVeDiTourRieng giaVeDiTourRieng;
	private GiaVeDiTauRieng giaVeDiTauRieng;
	private TuyenXe tuyenXe;

	private boolean flagChange = false;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private boolean veDuocDuyet = false;

	public DatVe() {
		pcs.addPropertyChangeListener(this);
	}

	public boolean isVeDuocDuyet() {
		return veDuocDuyet;
	}

	public void setVeDuocDuyet(boolean veDuocDuyet) {
		this.veDuocDuyet = veDuocDuyet;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		PropertyChangeObject obj = new PropertyChangeObject(evt.getPropertyName(),
				evt.getOldValue() != null ? evt.getOldValue().toString() : "",
				evt.getNewValue() != null ? evt.getNewValue().toString() : "");
		if (flagChange && !listPropertyChangeEvent.contains(obj) && !obj.getNewValue().equals(obj.getOldValue())) {
			listPropertyChangeEvent.add(obj);
		}
	}

	private List<PropertyChangeObject> listPropertyChangeEvent = new ArrayList<PropertyChangeObject>();

	@Transient
	public List<PropertyChangeObject> getListPropertyChangeEvent() {
		return listPropertyChangeEvent;
	}

	@Transient
	public boolean isFlagChange() {
		return flagChange;
	}

	public void setFlagChange(boolean flagChange) {
		this.flagChange = flagChange;
	}

	public void setListPropertyChangeEvent(List<PropertyChangeObject> listPropertyChangeEvent) {
		this.listPropertyChangeEvent = listPropertyChangeEvent;
	}

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		String oldValue = this.hoVaTen;
		this.hoVaTen = hoVaTen;
		pcs.firePropertyChange("Họ và tên", oldValue, hoVaTen);
	}

	public String getSoCMND() {
		return soCMND;
	}

	public void setSoCMND(String soCMND) {
		String oldValue = this.soCMND;
		this.soCMND = soCMND;
		pcs.firePropertyChange("Số CMND", oldValue, soCMND);
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		String oldValue = this.passport;
		this.passport = passport;
		pcs.firePropertyChange("Hộ chiếu", oldValue, passport);
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		String oldValue = this.diaChi;
		this.diaChi = diaChi;
		pcs.firePropertyChange("Địa chỉ", oldValue, diaChi);
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		String oldValue = this.ghiChu;
		this.ghiChu = ghiChu;
		pcs.firePropertyChange("Ghi chú", oldValue, ghiChu);
	}

	public String getLyDoTinhTrangVe() {
		return lyDoTinhTrangVe;
	}

	public void setLyDoTinhTrangVe(String lyDoTinhTrangVe) {
		String oldValue = this.lyDoTinhTrangVe;
		this.lyDoTinhTrangVe = lyDoTinhTrangVe;
		pcs.firePropertyChange("Lý do tình trạng vé", oldValue, lyDoTinhTrangVe);
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		String oldValue = this.soDienThoai;
		this.soDienThoai = soDienThoai;
		pcs.firePropertyChange("Số điện thoại", oldValue, soDienThoai);
	}

	@ManyToOne
	public TuyenXe getTuyenXe() {
		return tuyenXe;
	}

	public void setTuyenXe(TuyenXe tuyenXe) {
		TuyenXe oldValue = this.tuyenXe;
		this.tuyenXe = tuyenXe;
		pcs.firePropertyChange("Tuyến xe", oldValue != null ? oldValue.getTen() : "",
				tuyenXe != null ? tuyenXe.getTen() : "");
	}

	@Enumerated(EnumType.STRING)
	public PhanLoaiKhachDiTour getPhanLoaiKhachDiTour() {
		if (phanLoaiKhachDiTour == null) {
			phanLoaiKhachDiTour = PhanLoaiKhachDiTour.TOUR_BINH_THUONG;
		}
		return phanLoaiKhachDiTour;
	}

	public void setPhanLoaiKhachDiTour(PhanLoaiKhachDiTour phanLoaiKhachDiTour) {
		PhanLoaiKhachDiTour oldValue = this.phanLoaiKhachDiTour;
		this.phanLoaiKhachDiTour = phanLoaiKhachDiTour;
		pcs.firePropertyChange("Phân loại khách đi tour", oldValue != null ? oldValue.getText() : "",
				phanLoaiKhachDiTour != null ? phanLoaiKhachDiTour.getText() : "");
	}

	@ManyToOne
	public GiaVeDiTourRieng getGiaVeDiTourRieng() {
		return giaVeDiTourRieng;
	}

	public void setGiaVeDiTourRieng(GiaVeDiTourRieng giaVeDiTourRieng) {
		GiaVeDiTourRieng oldValue = this.giaVeDiTourRieng;
		this.giaVeDiTourRieng = giaVeDiTourRieng;
		pcs.firePropertyChange("Giá vé đi tour riêng", oldValue != null ? oldValue.getTen() : "",
				giaVeDiTourRieng != null ? giaVeDiTourRieng.getTen() : "");
	}

	@ManyToOne
	public GiaVe2Ngay1Dem getGiaVe2Ngay1Dem() {
		return giaVe2Ngay1Dem;
	}

	public void setGiaVe2Ngay1Dem(GiaVe2Ngay1Dem giaVe2Ngay1Dem) {
		GiaVe2Ngay1Dem oldValue = this.giaVe2Ngay1Dem;
		this.giaVe2Ngay1Dem = giaVe2Ngay1Dem;
		pcs.firePropertyChange("Giá vé đi 2 ngày 1 đêm", oldValue != null ? oldValue.getTen() : "",
				giaVe2Ngay1Dem != null ? giaVe2Ngay1Dem.getTen() : "");
	}

	@ManyToOne
	public GiaVeDiTauRieng getGiaVeDiTauRieng() {
		return giaVeDiTauRieng;
	}

	public void setGiaVeDiTauRieng(GiaVeDiTauRieng giaVeDiTauRieng) {
		GiaVeDiTauRieng oldValue = this.giaVeDiTauRieng;
		this.giaVeDiTauRieng = giaVeDiTauRieng;
		pcs.firePropertyChange("Giá tàu đi riêng", oldValue != null ? oldValue.getTen() : "",
				giaVeDiTauRieng != null ? giaVeDiTauRieng.getTen() : "");
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		Date oldValue = this.ngaySinh;
		this.ngaySinh = ngaySinh;
		pcs.firePropertyChange("Ngày sinh", oldValue, ngaySinh);
	}

	public Date getNgayKhachDat() {
		return ngayKhachDat;
	}

	public void setNgayKhachDat(Date ngayKhachDat) {
		Date oldValue = this.ngayKhachDat;
		this.ngayKhachDat = ngayKhachDat;
		pcs.firePropertyChange("Ngày đặt khách", oldValue, ngayKhachDat);
	}

	public Date getNgayThucHienTour() {
		return ngayThucHienTour;
	}

	public void setNgayThucHienTour(Date ngayThucHienTour) {
		Date oldValue = this.ngayThucHienTour;
		this.ngayThucHienTour = ngayThucHienTour;
		pcs.firePropertyChange("Ngày thực hiện tour", oldValue, ngayThucHienTour);
	}

	public int getSoLuongNguoiLon() {
		return soLuongNguoiLon;
	}

	public void setSoLuongNguoiLon(int soLuongNguoiLon) {
		int oldValue = this.soLuongNguoiLon;
		this.soLuongNguoiLon = soLuongNguoiLon;
		pcs.firePropertyChange("Số lượng người lớn", oldValue, soLuongNguoiLon);
	}

	public int getSoLuongTreEmDuoi10Tuoi() {
		return soLuongTreEmDuoi10Tuoi;
	}

	public void setSoLuongTreEmDuoi10Tuoi(int soLuongTreEmDuoi10Tuoi) {
		int oldValue = this.soLuongTreEmDuoi10Tuoi;
		this.soLuongTreEmDuoi10Tuoi = soLuongTreEmDuoi10Tuoi;
		pcs.firePropertyChange("Số lượng trẻ em dưới 10 tuổi", oldValue, soLuongTreEmDuoi10Tuoi);
	}

	public int getSoLuongTreEmDuoi5Tuoi() {
		return soLuongTreEmDuoi5Tuoi;
	}

	public void setSoLuongTreEmDuoi5Tuoi(int soLuongTreEmDuoi5Tuoi) {
		int oldValue = this.soLuongTreEmDuoi5Tuoi;
		this.soLuongTreEmDuoi5Tuoi = soLuongTreEmDuoi5Tuoi;
		pcs.firePropertyChange("Số lượng trẻ em dưới 5 tuổi", oldValue, soLuongTreEmDuoi5Tuoi);
	}

	public int getSoLuongNguoiLonBanDau() {
		return soLuongNguoiLonBanDau;
	}

	public void setSoLuongNguoiLonBanDau(int soLuongNguoiLonBanDau) {
		this.soLuongNguoiLonBanDau = soLuongNguoiLonBanDau;
	}

	public int getSoLuongTreEmDuoi10TuoiBanDau() {
		return soLuongTreEmDuoi10TuoiBanDau;
	}

	public void setSoLuongTreEmDuoi10TuoiBanDau(int soLuongTreEmDuoi10TuoiBanDau) {
		this.soLuongTreEmDuoi10TuoiBanDau = soLuongTreEmDuoi10TuoiBanDau;
	}

	public int getSoLuongTreEmDuoi5TuoiBanDau() {
		return soLuongTreEmDuoi5TuoiBanDau;
	}

	public void setSoLuongTreEmDuoi5TuoiBanDau(int soLuongTreEmDuoi5TuoiBanDau) {
		this.soLuongTreEmDuoi5TuoiBanDau = soLuongTreEmDuoi5TuoiBanDau;
	}

	private String giaVeNguoiLon = "";
	private String giaVeTreEmDuoi5Tuoi = "";
	private String giaVeTreEmDuoi10Tuoi = "";

	@Transient
	public String getGiaVeNguoiLon() {
		return giaVeNguoiLon;
	}

	public void setGiaVeNguoiLon(String giaVeNguoiLon) {
		this.giaVeNguoiLon = giaVeNguoiLon;
	}

	@Transient
	public String getGiaVeTreEmDuoi5Tuoi() {
		return giaVeTreEmDuoi5Tuoi;
	}

	public void setGiaVeTreEmDuoi5Tuoi(String giaVeTreEmDuoi5Tuoi) {
		this.giaVeTreEmDuoi5Tuoi = giaVeTreEmDuoi5Tuoi;
	}

	@Transient
	public String getGiaVeTreEmDuoi10Tuoi() {
		return giaVeTreEmDuoi10Tuoi;
	}

	public void setGiaVeTreEmDuoi10Tuoi(String giaVeTreEmDuoi10Tuoi) {
		this.giaVeTreEmDuoi10Tuoi = giaVeTreEmDuoi10Tuoi;
	}

	public long getGiaDichVu() {
		return giaDichVu;
	}

	public void setGiaDichVu(long giaDichVu) {
		long oldValue = this.giaDichVu;
		this.giaDichVu = giaDichVu;
		pcs.firePropertyChange("Giá dịch vụ", oldValue, giaDichVu);
	}

	@Enumerated(EnumType.STRING)
	public QuocTichEnum getQuocTich() {
		return quocTich;
	}

	public void setQuocTich(QuocTichEnum quocTich) {
		QuocTichEnum oldValue = this.quocTich;
		this.quocTich = quocTich;
		pcs.firePropertyChange("Quốc tịch", oldValue != null ? oldValue.getText() : "",
				quocTich != null ? quocTich.getText() : "");
	}

	@Enumerated(EnumType.STRING)
	public TinhTrangVeEnum getTinhTrangVe() {
		return tinhTrangVe;
	}

	public void setTinhTrangVe(TinhTrangVeEnum tinhTrangVe) {
		TinhTrangVeEnum oldValue = this.tinhTrangVe;
		this.tinhTrangVe = tinhTrangVe;
		pcs.firePropertyChange("Tình trạng vé", oldValue != null ? oldValue.getText() : "",
				tinhTrangVe != null ? tinhTrangVe.getText() : "");
	}

	@Enumerated(EnumType.STRING)
	public LoaiPhongVeLeEnum getLoaiPhongBanVeLe() {
		return loaiPhongBanVeLe;
	}

	public void setLoaiPhongBanVeLe(LoaiPhongVeLeEnum loaiPhongBanVeLe) {
		LoaiPhongVeLeEnum oldValue = this.loaiPhongBanVeLe;
		this.loaiPhongBanVeLe = loaiPhongBanVeLe;
		pcs.firePropertyChange("Loại phòng bán vé lẻ", oldValue != null ? oldValue.getText() : "",
				loaiPhongBanVeLe != null ? loaiPhongBanVeLe.getText() : "");
	}

	@Enumerated(EnumType.STRING)
	public TrangThaiDuyetVeEnum getTrangThaiDuyetVeEnum() {
		return trangThaiDuyetVeEnum;
	}

	public void setTrangThaiDuyetVeEnum(TrangThaiDuyetVeEnum trangThaiDuyetVeEnum) {
		TrangThaiDuyetVeEnum oldValue = this.trangThaiDuyetVeEnum;
		this.trangThaiDuyetVeEnum = trangThaiDuyetVeEnum;
		pcs.firePropertyChange("Trạng thái duyệt vé", oldValue != null ? oldValue.getText() : "",
				trangThaiDuyetVeEnum != null ? trangThaiDuyetVeEnum.getText() : "");
	}

	@ManyToOne
	public NhomCuaHoi getNhomCuaHoi() {
		if (noId()) {
			if (!getNhanVien().isQuanTriVien() && getNhanVien().getNhomCuaHoi() != null) {
				nhomCuaHoi = getNhanVien().getNhomCuaHoi();
			} else {
				if (nhomCuaHoi == null) {
					nhomCuaHoi = getNhanVien().getNhomCuaHoi();
				}
			}
		}
		return nhomCuaHoi;
	}

	public void setNhomCuaHoi(NhomCuaHoi nhomCuaHoi) {
		NhomCuaHoi oldValue = this.nhomCuaHoi;
		this.nhomCuaHoi = nhomCuaHoi;
		pcs.firePropertyChange("Nhóm của hội", oldValue != null ? oldValue.getTen() : "",
				nhomCuaHoi != null ? nhomCuaHoi.getTen() : "");
		BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanhListAndNull");
	}

	@ManyToOne
	public CongTyKinhDoanh getCongTyKinhDoanh() {
		if (noId()) {
			if (!getNhanVien().isQuanTriVien() && getNhanVien().getCongTyKinhDoanh() != null) {
				congTyKinhDoanh = getNhanVien().getCongTyKinhDoanh();
			} else {
				if (congTyKinhDoanh == null) {
					congTyKinhDoanh = getNhanVien().getCongTyKinhDoanh();
				}
			}
		}
		return congTyKinhDoanh;
	}

	public void setCongTyKinhDoanh(CongTyKinhDoanh congTyKinhDoanh) {
		CongTyKinhDoanh oldValue = this.congTyKinhDoanh;
		this.congTyKinhDoanh = congTyKinhDoanh;
		pcs.firePropertyChange("Công ty kinh doanh", oldValue != null ? oldValue.getTen() : "",
				congTyKinhDoanh != null ? congTyKinhDoanh.getTen() : "");
	}

	@ManyToOne
	public PhanLoaiTour getPhanLoaiTour() {
		return phanLoaiTour;
	}

	public void setPhanLoaiTour(PhanLoaiTour phanLoaiTour) {
		PhanLoaiTour oldValue = this.phanLoaiTour;
		this.phanLoaiTour = phanLoaiTour;
		pcs.firePropertyChange("Phân loại tour", oldValue != null ? oldValue.getTen() : "",
				phanLoaiTour != null ? phanLoaiTour.getTen() : "");
	}

	@ManyToOne
	public PhanLoaiKhachThueTau getPhanLoaiKhachThueTau() {
		return phanLoaiKhachThueTau;
	}

	public void setPhanLoaiKhachThueTau(PhanLoaiKhachThueTau phanLoaiKhachThueTau) {
		PhanLoaiKhachThueTau oldValue = this.phanLoaiKhachThueTau;
		this.phanLoaiKhachThueTau = phanLoaiKhachThueTau;
		pcs.firePropertyChange("Phân loại khách thuê tàu", oldValue != null ? oldValue.getTen() : "",
				phanLoaiKhachThueTau != null ? phanLoaiKhachThueTau.getTen() : "");
	}

	public long soKhachCongTyHienTai() {
		long tongVe = 0;
		JPAQuery<DatVe> datVes = find(DatVe.class)
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.id.eq(getNhomCuaHoi().getId()))
				.where(QDatVe.datVe.congTyKinhDoanh.id.eq(getCongTyKinhDoanh().getId()))
				.where(QDatVe.datVe.ngayThucHienTour.eq(getNgayThucHienTour()));

		if (!datVes.fetch().isEmpty()) {

			for (DatVe i : datVes.fetch()) {
				tongVe += (long) (i.getSoLuongNguoiLon() + i.getSoLuongTreEmDuoi10Tuoi()
						+ i.getSoLuongTreEmDuoi5Tuoi());
			}
		}
		return tongVe;
	}

/*	public void showMessageWarning(Long soKhachToiDa, Window wdn) {
		String message = "Đặt vé vượt số lượng cho phép trong ngày (" + soKhachToiDa
				+ " vé). \nBấm 'Chấp nhận' để đưa vé sang trang thái đợi duyệt. \nBấm 'Bỏ qua' để điều chỉnh lại thông tin.";
		String btnAccept = "Xác nhận đặt vé";
		Messagebox.show(message, btnAccept, Messagebox.CANCEL | Messagebox.OK, Messagebox.QUESTION,
				new EventListener<Event>() {

					@Override
					public void onEvent(final Event event) throws Exception {
						if(Messagebox.ON_OK.equals(event.getName())){
							
							setTrangThaiDuyetVeEnum(TrangThaiDuyetVeEnum.DANG_DUYET);
							setVeDuocDuyet(true);
							saveNotShowNotification();
							showNotification(
									"Vé của bạn đã chuyển sang trạng thái đợi duyệt. Vui lòng chờ kết quả",
									"", "success");
							wdn.detach();
							if (listPropertyChangeEvent.size() > 0) {
								LichSuDatVe lsDV = new LichSuDatVe();
								lsDV.setDatVe(DatVe.this);
								if (isNew) {
									lsDV.setNoiDung("Thêm mới đặt vé");
								} else {
									lsDV.setNoiDung("Chỉnh sửa đặt vé");
								}
								lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
								lsDV.saveNotShowNotification();
							}
						}
					}
				});
	}*/

	// Luu va chinh sua ve dat
	@Command
	public void saveDatVe(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		
		boolean isNew = noId();

		if (isNew) {
			setSoLuongNguoiLonBanDau(getSoLuongNguoiLon());
			setSoLuongTreEmDuoi10TuoiBanDau(getSoLuongTreEmDuoi10Tuoi());
			setSoLuongTreEmDuoi5TuoiBanDau(getSoLuongTreEmDuoi5Tuoi());
		}

		if (getNhanVien().isNhomThanhVien() || getNhanVien().isBanDieuHanh()) {
			long soKhachToiDa = getCongTyKinhDoanh().getSoKhachToiDa();
			long tongVe = soKhachCongTyHienTai();
			if (isNew) {
				tongVe += (long) (getSoLuongNguoiLon() + getSoLuongTreEmDuoi10Tuoi() + getSoLuongTreEmDuoi5Tuoi());
				if (tongVe > soKhachToiDa) {
					Messagebox.show(
							"Đặt vé vượt số lượng cho phép trong ngày (" + soKhachToiDa
									+ " vé). \nBấm 'Chấp nhận' để đưa vé sang trang thái đợi duyệt. \nBấm 'Bỏ qua' để điều chỉnh lại thông tin.",
							"Xác nhận đặt vé", Messagebox.CANCEL | Messagebox.OK, Messagebox.QUESTION,
							new EventListener<Event>() {
								@Override
								public void onEvent(final Event event) {
									if (Messagebox.ON_OK.equals(event.getName())) {
										setTrangThaiDuyetVeEnum(TrangThaiDuyetVeEnum.DANG_DUYET);
										setVeDuocDuyet(true);
										saveNotShowNotification();
										showNotification(
												"Vé của bạn đã chuyển sang trạng thái đợi duyệt. Vui lòng chờ kết quả",
												"", "success");
										wdn.detach();
										if (listPropertyChangeEvent.size() > 0) {
											LichSuDatVe lsDV = new LichSuDatVe();
											lsDV.setDatVe(DatVe.this);
											if (isNew) {
												lsDV.setNoiDung("Thêm mới đặt vé");
											} else {
												lsDV.setNoiDung("Chỉnh sửa đặt vé");
											}
											lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
											lsDV.saveNotShowNotification();
										}
									}
								}

							});
				} else {

					save();
					wdn.detach();
					BindUtils.postNotifyChange(null, null, listObject, attr);
					if (listPropertyChangeEvent.size() > 0) {
						LichSuDatVe lsDV = new LichSuDatVe();
						lsDV.setDatVe(this);
						if (isNew) {
							lsDV.setNoiDung("Thêm mới đặt vé");
						} else {
							lsDV.setNoiDung("Chỉnh sửa đặt vé");
						}
						lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
						lsDV.saveNotShowNotification();
					}
				}
			} else {
				Calendar calNgayThucHien = Calendar.getInstance();
				calNgayThucHien.setTime(getNgayThucHienTour());
				JPAQuery<DatVe> veCongTyList = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
						.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
						.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
								.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
						.where(QDatVe.datVe.nhomCuaHoi.eq(getNhomCuaHoi())
								.and(QDatVe.datVe.congTyKinhDoanh.eq(getCongTyKinhDoanh())))
						.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayThucHien.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHien.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(calNgayThucHien.get(Calendar.DAY_OF_MONTH))));
				
				long tongSoVeCu = 0;
				
				if(veCongTyList != null) {
					for(DatVe ve : veCongTyList.fetch()) {
						tongSoVeCu += Long.valueOf(ve.getSoLuongNguoiLon() + ve.getSoLuongTreEmDuoi10Tuoi() + ve.getGiaVeTreEmDuoi5Tuoi());
					}
				}
				
				JPAQuery<DatVe> thongTinVe = find(DatVe.class)
						.where(QDatVe.datVe.id.eq(getId()));
				long soLuongHienTai = (long) (thongTinVe.fetchOne().getSoLuongNguoiLon()
						+ thongTinVe.fetchOne().getSoLuongTreEmDuoi5Tuoi()
						+ thongTinVe.fetchOne().getSoLuongTreEmDuoi10Tuoi());
				long soLuongMoi = (long) (getSoLuongNguoiLon() + getSoLuongTreEmDuoi10Tuoi() + getSoLuongTreEmDuoi5Tuoi());
				
				long tongSoVeMoi = 0;
				tongSoVeMoi = tongSoVeCu - soLuongHienTai + soLuongMoi;
				
				if (tongSoVeMoi > soKhachToiDa) {
					if (soLuongMoi <= soLuongHienTai) {

						save();
						wdn.detach();
						// DatVeService datVeService = (DatVeService)
						// listObject;
						// datVeService.setCongTyKinhDoanh(null);
						// BindUtils.postNotifyChange(null, null, listObject,
						// "congTyKinhDoanh");
						// BindUtils.postNotifyChange(null, null, listObject,
						// "nhomCuaHois");
						// BindUtils.postNotifyChange(null, null, listObject,
						// "phongBanVeLeQuery");
						BindUtils.postNotifyChange(null, null, listObject, attr);

						if (listPropertyChangeEvent.size() > 0) {
							LichSuDatVe lsDV = new LichSuDatVe();
							lsDV.setDatVe(this);
							if (isNew) {
								lsDV.setNoiDung("Thêm mới đặt vé");
							} else {
								lsDV.setNoiDung("Chỉnh sửa đặt vé");
							}
							lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
							lsDV.saveNotShowNotification();
						}
					} else {
						Messagebox.show(
								"Đặt vé vượt số lượng cho phép trong ngày (" + soKhachToiDa
										+ " vé). \nBấm 'Chấp nhận' để đưa vé sang trang thái đợi duyệt. \nBấm 'Bỏ qua' để điều chỉnh lại thông tin.",
								"Xác nhận đặt vé", Messagebox.CANCEL | Messagebox.OK, Messagebox.QUESTION,
								new EventListener<Event>() {
									@Override
									public void onEvent(final Event event) {
										if (Messagebox.ON_OK.equals(event.getName())) {
											setTrangThaiDuyetVeEnum(TrangThaiDuyetVeEnum.DANG_DUYET);
											setVeDuocDuyet(true);
											saveNotShowNotification();
											showNotification(
													"Vé của bạn đã chuyển sang trạng thái đợi duyệt. Vui lòng chờ kết quả",
													"", "success");
											wdn.detach();
											// DatVeService datVeService =
											// (DatVeService) listObject;
											// datVeService.setCongTyKinhDoanh(null);
											// BindUtils.postNotifyChange(null,
											// null, listObject,
											// "congTyKinhDoanh");
											// BindUtils.postNotifyChange(null,
											// null, listObject, "nhomCuaHois");
											// BindUtils.postNotifyChange(null,
											// null, listObject,
											// "phongBanVeLeQuery");
											BindUtils.postNotifyChange(null, null, listObject, attr);
											if (listPropertyChangeEvent.size() > 0) {
												LichSuDatVe lsDV = new LichSuDatVe();
												lsDV.setDatVe(DatVe.this);
												if (isNew) {
													lsDV.setNoiDung("Thêm mới đặt vé");
												} else {
													lsDV.setNoiDung("Chỉnh sửa đặt vé");
												}
												lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
												lsDV.saveNotShowNotification();
											}
										}
									}

								});
					}
				} else {
					save();
					wdn.detach();
					// DatVeService datVeService = (DatVeService) listObject;
					// datVeService.setCongTyKinhDoanh(null);
					// BindUtils.postNotifyChange(null, null, listObject,
					// "congTyKinhDoanh");
					// BindUtils.postNotifyChange(null, null, listObject,
					// "nhomCuaHois");
					// BindUtils.postNotifyChange(null, null, listObject,
					// "phongBanVeLeQuery");
					BindUtils.postNotifyChange(null, null, listObject, attr);

					if (listPropertyChangeEvent.size() > 0) {
						LichSuDatVe lsDV = new LichSuDatVe();
						lsDV.setDatVe(this);
						if (isNew) {
							lsDV.setNoiDung("Thêm mới đặt vé");
						} else {
							lsDV.setNoiDung("Chỉnh sửa đặt vé");
						}
						lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
						lsDV.saveNotShowNotification();
					}
				}
			}
		} else {
			save();
			wdn.detach();
			// DatVeService datVeService = (DatVeService) listObject;
			// datVeService.setCongTyKinhDoanh(null);
			// BindUtils.postNotifyChange(null, null, listObject,
			// "congTyKinhDoanh");
			// BindUtils.postNotifyChange(null, null, listObject,
			// "nhomCuaHois");
			// BindUtils.postNotifyChange(null, null, listObject,
			// "phongBanVeLeQuery");
			BindUtils.postNotifyChange(null, null, listObject, attr);

			if (listPropertyChangeEvent.size() > 0) {
				LichSuDatVe lsDV = new LichSuDatVe();
				lsDV.setDatVe(this);
				if (isNew) {
					lsDV.setNoiDung("Thêm mới đặt vé");
				} else {
					lsDV.setNoiDung("Chỉnh sửa đặt vé");
				}
				lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsDV.saveNotShowNotification();
			}
		}
	}

	@Command
	public void saveDatVeAndPrint(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr, @BindingParam("wdn") final Window wdn) {

		boolean isNew = noId();
		if (isNew) {
			setSoLuongNguoiLonBanDau(getSoLuongNguoiLon());
			setSoLuongTreEmDuoi10TuoiBanDau(getSoLuongTreEmDuoi10Tuoi());
			setSoLuongTreEmDuoi5TuoiBanDau(getSoLuongTreEmDuoi5Tuoi());
		}

		if (getNhanVien().isNhomThanhVien() || getNhanVien().isBanDieuHanh()) {

			long soKhachToiDa = getCongTyKinhDoanh().getSoKhachToiDa();
			long tongVe = soKhachCongTyHienTai();
			if (isNew) {

				tongVe += (long) (getSoLuongNguoiLon() + getSoLuongTreEmDuoi10Tuoi() + getSoLuongTreEmDuoi5Tuoi());
				if (tongVe > soKhachToiDa) {
					Messagebox.show(
							"Đặt vé vượt số lượng cho phép trong ngày (" + soKhachToiDa
									+ " vé). \nBấm 'Chấp nhận' để đưa vé sang trang thái đợi duyệt. \nBấm 'Bỏ qua' để điều chỉnh lại thông tin.",
							"Xác nhận đặt vé", Messagebox.CANCEL | Messagebox.OK, Messagebox.QUESTION,
							new EventListener<Event>() {
								@Override
								public void onEvent(final Event event) {
									if (Messagebox.ON_OK.equals(event.getName())) {
										setTrangThaiDuyetVeEnum(TrangThaiDuyetVeEnum.DANG_DUYET);
										setVeDuocDuyet(true);
										saveNotShowNotification();
										showNotification(
												"Vé của bạn đã chuyển sang trạng thái đợi duyệt. Vui lòng chờ kết quả",
												"", "success");
										wdn.detach();
										// DatVeService datVeService =
										// (DatVeService) listObject;
										// datVeService.setCongTyKinhDoanh(null);
										// BindUtils.postNotifyChange(null,
										// null, listObject, "congTyKinhDoanh");
										// BindUtils.postNotifyChange(null,
										// null, listObject, "nhomCuaHois");
										// BindUtils.postNotifyChange(null,
										// null, listObject,
										// "phongBanVeLeQuery");
										BindUtils.postNotifyChange(null, null, listObject, attr);

										if (listPropertyChangeEvent.size() > 0) {
											LichSuDatVe lsDV = new LichSuDatVe();
											lsDV.setDatVe(DatVe.this);
											if (isNew) {
												lsDV.setNoiDung("Thêm mới đặt vé");
											} else {
												lsDV.setNoiDung("Chỉnh sửa đặt vé");
											}
											lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
											lsDV.saveNotShowNotification();
										}
									}
								}

							});
				} else {

					save();
					wdn.detach();
					// DatVeService datVeService = (DatVeService) listObject;
					// datVeService.setCongTyKinhDoanh(null);
					// BindUtils.postNotifyChange(null, null, listObject,
					// "congTyKinhDoanh");
					// BindUtils.postNotifyChange(null, null, listObject,
					// "nhomCuaHois");
					// BindUtils.postNotifyChange(null, null, listObject,
					// "phongBanVeLeQuery");
					BindUtils.postNotifyChange(null, null, listObject, attr);
					Clients.evalJavaScript("openPrint( " + getId() + ");");

					if (listPropertyChangeEvent.size() > 0) {
						LichSuDatVe lsDV = new LichSuDatVe();
						lsDV.setDatVe(this);
						if (isNew) {
							lsDV.setNoiDung("Thêm mới đặt vé");
						} else {
							lsDV.setNoiDung("Chỉnh sửa đặt vé");
						}
						lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
						lsDV.saveNotShowNotification();
					}
				}
			} else {

				Calendar calNgayThucHien = Calendar.getInstance();
				calNgayThucHien.setTime(getNgayThucHienTour());
				JPAQuery<DatVe> veCongTyList = find(DatVe.class).where(QDatVe.datVe.trangThai.eq(core().TT_AP_DUNG))
						.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
						.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
								.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
						.where(QDatVe.datVe.nhomCuaHoi.eq(getNhomCuaHoi())
								.and(QDatVe.datVe.congTyKinhDoanh.eq(getCongTyKinhDoanh())))
						.where(QDatVe.datVe.ngayThucHienTour.year().eq(calNgayThucHien.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(calNgayThucHien.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(calNgayThucHien.get(Calendar.DAY_OF_MONTH))));
				
				long tongSoVeCu = 0;
				
				if(veCongTyList != null) {
					for(DatVe ve : veCongTyList.fetch()) {
						tongSoVeCu += Long.valueOf(ve.getSoLuongNguoiLon() + ve.getSoLuongTreEmDuoi10Tuoi() + ve.getGiaVeTreEmDuoi5Tuoi());
					}
				}
				
				JPAQuery<DatVe> thongTinVe = find(DatVe.class)
						.where(QDatVe.datVe.id.eq(getId()));
				long soLuongHienTai = (long) (thongTinVe.fetchOne().getSoLuongNguoiLon()
						+ thongTinVe.fetchOne().getSoLuongTreEmDuoi5Tuoi()
						+ thongTinVe.fetchOne().getSoLuongTreEmDuoi10Tuoi());
				long soLuongMoi = (long) (getSoLuongNguoiLon() + getSoLuongTreEmDuoi10Tuoi() + getSoLuongTreEmDuoi5Tuoi());
				
				long tongSoVeMoi = 0;
				tongSoVeMoi = tongSoVeCu - soLuongHienTai + soLuongMoi;

				if (tongSoVeMoi > soKhachToiDa) {
					if (soLuongMoi <= soLuongHienTai) {

						save();
						wdn.detach();
						// DatVeService datVeService = (DatVeService)
						// listObject;
						// datVeService.setCongTyKinhDoanh(null);
						// BindUtils.postNotifyChange(null, null, listObject,
						// "congTyKinhDoanh");
						// BindUtils.postNotifyChange(null, null, listObject,
						// "nhomCuaHois");
						// BindUtils.postNotifyChange(null, null, listObject,
						// "phongBanVeLeQuery");
						BindUtils.postNotifyChange(null, null, listObject, attr);
						Clients.evalJavaScript("openPrint( " + getId() + ");");

						if (listPropertyChangeEvent.size() > 0) {
							LichSuDatVe lsDV = new LichSuDatVe();
							lsDV.setDatVe(this);
							if (isNew) {
								lsDV.setNoiDung("Thêm mới đặt vé");
							} else {
								lsDV.setNoiDung("Chỉnh sửa đặt vé");
							}
							lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
							lsDV.saveNotShowNotification();
						}
					} else {
						Messagebox.show(
								"Đặt vé vượt số lượng cho phép trong ngày (" + soKhachToiDa
										+ " vé). \nBấm 'Chấp nhận' để đưa vé sang trang thái đợi duyệt. \nBấm 'Bỏ qua' để điều chỉnh lại thông tin.",
								"Xác nhận đặt vé", Messagebox.CANCEL | Messagebox.OK, Messagebox.QUESTION,
								new EventListener<Event>() {
									@Override
									public void onEvent(final Event event) {
										if (Messagebox.ON_OK.equals(event.getName())) {
											setTrangThaiDuyetVeEnum(TrangThaiDuyetVeEnum.DANG_DUYET);
											setVeDuocDuyet(true);
											saveNotShowNotification();
											showNotification(
													"Vé của bạn đã chuyển sang trạng thái đợi duyệt. Vui lòng chờ kết quả",
													"", "success");
											wdn.detach();
											// DatVeService datVeService =
											// (DatVeService) listObject;
											// datVeService.setCongTyKinhDoanh(null);
											// BindUtils.postNotifyChange(null,
											// null, listObject,
											// "congTyKinhDoanh");
											// BindUtils.postNotifyChange(null,
											// null, listObject, "nhomCuaHois");
											// BindUtils.postNotifyChange(null,
											// null, listObject,
											// "phongBanVeLeQuery");
											BindUtils.postNotifyChange(null, null, listObject, attr);

											if (listPropertyChangeEvent.size() > 0) {
												LichSuDatVe lsDV = new LichSuDatVe();
												lsDV.setDatVe(DatVe.this);
												if (isNew) {
													lsDV.setNoiDung("Thêm mới đặt vé");
												} else {
													lsDV.setNoiDung("Chỉnh sửa đặt vé");
												}
												lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
												lsDV.saveNotShowNotification();
											}
										}
									}

								});
					}
				} else {
					save();
					wdn.detach();
					// DatVeService datVeService = (DatVeService) listObject;
					// datVeService.setCongTyKinhDoanh(null);
					// BindUtils.postNotifyChange(null, null, listObject,
					// "congTyKinhDoanh");
					// BindUtils.postNotifyChange(null, null, listObject,
					// "nhomCuaHois");
					// BindUtils.postNotifyChange(null, null, listObject,
					// "phongBanVeLeQuery");
					BindUtils.postNotifyChange(null, null, listObject, attr);
					Clients.evalJavaScript("openPrint( " + getId() + ");");

					if (listPropertyChangeEvent.size() > 0) {
						LichSuDatVe lsDV = new LichSuDatVe();
						lsDV.setDatVe(this);
						if (isNew) {
							lsDV.setNoiDung("Thêm mới đặt vé");
						} else {
							lsDV.setNoiDung("Chỉnh sửa đặt vé");
						}
						lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
						lsDV.saveNotShowNotification();
					}
				}

			}
		} else {
			save();
			wdn.detach();
			// DatVeService datVeService = (DatVeService) listObject;
			// datVeService.setCongTyKinhDoanh(null);
			// BindUtils.postNotifyChange(null, null, listObject,
			// "congTyKinhDoanh");
			// BindUtils.postNotifyChange(null, null, listObject,
			// "nhomCuaHois");
			// BindUtils.postNotifyChange(null, null, listObject,
			// "phongBanVeLeQuery");
			BindUtils.postNotifyChange(null, null, listObject, attr);
			Clients.evalJavaScript("openPrint( " + getId() + ");");

			if (listPropertyChangeEvent.size() > 0) {
				LichSuDatVe lsDV = new LichSuDatVe();
				lsDV.setDatVe(this);
				if (isNew) {
					lsDV.setNoiDung("Thêm mới đặt vé");
				} else {
					lsDV.setNoiDung("Chỉnh sửa đặt vé");
				}
				lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
				lsDV.saveNotShowNotification();
			}
		}
	}

	@Command
	public void print() {
		Clients.evalJavaScript("openPrint( " + getId() + ");");
	}

	@Command
	public void validateSoLuong() {
		String _classname = "redTextColor";
		int n = 0;
		n = soLuongNguoiLon == 0 ? n += 1 : n;
		n = soLuongTreEmDuoi5Tuoi == 0 ? n += 1 : n;
		n = soLuongTreEmDuoi10Tuoi == 0 ? n += 1 : n;
		String msg = "Số lượng khách phải lớn hơn 0.";
		String s = "validateAddClass(" + "'" + _classname + "', '" + msg + "')";
		if (n <= 2 && (soLuongNguoiLon >= 0 && soLuongTreEmDuoi5Tuoi >= 0 && soLuongTreEmDuoi10Tuoi >= 0)) {
			s = "validateRemoveClass(" + "'" + _classname + "')";
		}
		Clients.evalJavaScript(s);
	}
	
	@Command
	public void napGiaVe() {
		
		if (getPhanLoaiKhachDiTour() == PhanLoaiKhachDiTour.THUE_TAU_DI_RIENG || getPhanLoaiKhachDiTour() == PhanLoaiKhachDiTour.TOUR_DI_RIENG) {
			giaVe2Ngay1Dem = null;
			BindUtils.postNotifyChange(null, null, this, "giaVe2Ngay1Dem");
			phanLoaiTour = null;
			BindUtils.postNotifyChange(null, null, this, "phanLoaiTour");
		} 
		
		if (getPhanLoaiKhachDiTour() == PhanLoaiKhachDiTour.TOUR_BINH_THUONG) {
			giaVe2Ngay1Dem = null;
			BindUtils.postNotifyChange(null, null, this, "giaVe2Ngay1Dem");
		}
		
		if (getPhanLoaiKhachDiTour() == PhanLoaiKhachDiTour.HAINGAY_MOTDEM){
			phanLoaiTour = null;
			BindUtils.postNotifyChange(null, null, this, "phanLoaiTour");
		}
		
		if (!getNhanVien().isNhomThanhVien()) {
			long giaTemp = 0;
			if (getPhanLoaiKhachDiTour().equals(PhanLoaiKhachDiTour.TOUR_BINH_THUONG)) {
				if (getPhanLoaiTour() != null) {
					giaTemp = (getPhanLoaiTour().getGiaNguoiLon() * getSoLuongNguoiLon())
							+ (getPhanLoaiTour().getGiaTreEm4Den9() * getSoLuongTreEmDuoi10Tuoi())
							+ (getPhanLoaiTour().getGiaTreEm1Den3() * getSoLuongTreEmDuoi5Tuoi());
				}
			} else if (getPhanLoaiKhachDiTour().equals(PhanLoaiKhachDiTour.HAINGAY_MOTDEM)) {
				if (getGiaVe2Ngay1Dem() != null) {
					giaTemp = (getGiaVe2Ngay1Dem().getGiaNguoiLon() * getSoLuongNguoiLon())
							+ (getGiaVe2Ngay1Dem().getGiaTreEm4Den9() * getSoLuongTreEmDuoi10Tuoi())
							+ (getGiaVe2Ngay1Dem().getGiaTreEm1Den3() * getSoLuongTreEmDuoi5Tuoi());
				}
			} else if (getPhanLoaiKhachDiTour().equals(PhanLoaiKhachDiTour.TOUR_DI_RIENG)) {
				GiaVeDiTourRieng giaVe = find(GiaVeDiTourRieng.class)
						.where(QGiaVeDiTourRieng.giaVeDiTourRieng.trangThai.eq(core().TT_AP_DUNG))
						.where(QGiaVeDiTourRieng.giaVeDiTourRieng.soNguoiDen.goe(getSoLuongNguoiLon())
								.and(QGiaVeDiTourRieng.giaVeDiTourRieng.soNguoiTu.loe(getSoLuongNguoiLon())))
						.orderBy(QGiaVeDiTourRieng.giaVeDiTourRieng.soNguoiDen.asc()).fetchFirst();
				if (giaVe != null) {
					giaTemp = giaVe.getGiaVe() * getSoLuongNguoiLon();
				}
			} else {
				GiaVeDiTauRieng giaVe = find(GiaVeDiTauRieng.class)
						.where(QGiaVeDiTauRieng.giaVeDiTauRieng.trangThai.eq(core().TT_AP_DUNG))
						.where(QGiaVeDiTauRieng.giaVeDiTauRieng.ten.goe(getSoLuongNguoiLon()))
						.orderBy(QGiaVeDiTauRieng.giaVeDiTauRieng.ten.asc()).fetchFirst();

				if (giaVe != null) {
					giaTemp = giaVe.getGiaVe();
				}
			}
			if (getLoaiPhongBanVeLe() != null
					&& getLoaiPhongBanVeLe().equals(LoaiPhongVeLeEnum.PHONG_VE_LE_GIAN_TIEP)) {
				if (getTuyenXe() != null) {
					giaTemp += getTuyenXe().getGiaVe();
				}
			}
			giaDichVu = giaTemp;
			BindUtils.postNotifyChange(null, null, this, "giaDichVu");
		}
	}

	@Transient
	public List<CongTyKinhDoanh> getCongTyKinhDoanhListAndNull() {
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		congTyKinhDoanhList.add(null);
		if (nhomCuaHoi != null) {
			congTyKinhDoanhList.addAll(
					find(CongTyKinhDoanh.class).where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
							.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.id.eq(nhomCuaHoi.getId()))
							.orderBy(QNhomCuaHoi.nhomCuaHoi.ten.asc()).fetch());
		}

		return congTyKinhDoanhList;
	}

	@Transient
	public @Nullable Date getFixTuNgay() {
		Date fixTuNgay = ngayKhachDat;
		if (fixTuNgay != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fixTuNgay);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			fixTuNgay = cal.getTime();
		}
		return fixTuNgay;
	}

	@Transient
	public @Nullable Date getFixDenNgay() {
		Date fixDenNgay = ngayThucHienTour;
		if (fixDenNgay != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(fixDenNgay);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			fixDenNgay = cal.getTime();
		}
		return fixDenNgay;
	}

	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				// Date fromDate = getFixTuNgay();
				// Date toDate = getFixDenNgay();
				Date ngaySinh = getNgaySinh();

				if (noId()) {
					// if (fromDate != null) {
					// Date current = new Date();
					// if (fromDate.before(current)) {
					// addInvalidMessage(ctx, "lblErrNgayKhachDat", "Phải chọn
					// ngày lớn hơn hoặc bằng ngày hiện tại.");
					// }
					// }

					/*
					 * if (toDate != null && toDate.before(new Date())) {
					 * addInvalidMessage(ctx, "lblErrNgayThucHienTour",
					 * "Phải chọn ngày lớn hơn hoặc bằng ngày hiện tại."); }
					 */

					/*
					 * if (fromDate != null && toDate != null) { if
					 * (fromDate.after(toDate)) { addInvalidMessage(ctx,
					 * "lblErrNgayThucHienTour",
					 * "Ngày thực hiện tour phải lớn hơn hoặc bằng ngày khách đặt."
					 * ); } }
					 */
				}

				if (getPhanLoaiKhachDiTour().equals(PhanLoaiKhachDiTour.TOUR_DI_RIENG)) {
					if (getSoLuongNguoiLon() == 0) {
						addInvalidMessage(ctx, "lblErrSoLuongKhach2", "Số lượng khách phải lớn hơn 0.");
					} else {
						GiaVeDiTourRieng giaVe = find(GiaVeDiTourRieng.class)
								.where(QGiaVeDiTourRieng.giaVeDiTourRieng.trangThai.eq(core().TT_AP_DUNG))
								.where(QGiaVeDiTourRieng.giaVeDiTourRieng.soNguoiTu.loe(getSoLuongNguoiLon())
										.and(QGiaVeDiTourRieng.giaVeDiTourRieng.soNguoiDen.goe(getSoLuongNguoiLon())))
								.fetchFirst();
						if (giaVe == null) {
							addInvalidMessage(ctx, "lblErrSoLuongKhach2", "Số lượng khách này chưa được cấu hình giá.");
						}
					}
				} else if (getPhanLoaiKhachDiTour().equals(PhanLoaiKhachDiTour.THUE_TAU_DI_RIENG)) {
					if (getSoLuongNguoiLon() == 0) {
						addInvalidMessage(ctx, "lblErrSoLuongKhach2", "Số lượng khách phải lớn hơn 0.");
					} else {
						GiaVeDiTauRieng giaVe = find(GiaVeDiTauRieng.class)
								.where(QGiaVeDiTauRieng.giaVeDiTauRieng.trangThai.eq(core().TT_AP_DUNG))
								.where(QGiaVeDiTauRieng.giaVeDiTauRieng.ten.goe(getSoLuongNguoiLon())).fetchFirst();
						if (giaVe == null) {
							addInvalidMessage(ctx, "lblErrSoLuongKhach2",
									"Không có tàu đủ chỗ cho số lượng khách này.");
						}
					}
				}

				if (ngaySinh != null) {
					if (ngaySinh.after(new Date())) {
						addInvalidMessage(ctx, "lblErrNgaySinh", "Ngày sinh phải nhỏ hơn hoặc bằng ngày hiện tại.");
					}
				}

				if (soLuongNguoiLon < 1) {
					addInvalidMessage(ctx, "lblErrSoLuongKhach", "Số lượng khách người lớn phải lớn hơn 0.");
				}

				if (soCMND != null && !"".equals(soCMND) && (soCMND.length() < 9 || soCMND.length() > 9)) {
					addInvalidMessage(ctx, "lblSoCMND", "Số chứng minh nhân dân không hợp lệ.");
				}

				if (soDienThoai != null && !"".equals(soDienThoai)
						&& (soDienThoai.length() > 11 || soDienThoai.length() < 10)) {
					addInvalidMessage(ctx, "lblSoDienThoai", "Số điện thoại không hợp lệ.");
				}
				if (getNgayThucHienTour() != null) {
					Calendar calNgayDatTour = Calendar.getInstance();
					calNgayDatTour.setTime(getNgayKhachDat());
					Calendar calNgayThucHien = Calendar.getInstance();
					calNgayThucHien.setTime(getNgayThucHienTour());
					Calendar toDay = Calendar.getInstance();
					toDay.setTime(new Date());
					Calendar toDay7h30 = Calendar.getInstance();
					toDay7h30.setTime(new Date());
					toDay7h30.set(Calendar.HOUR_OF_DAY, 7);
					toDay7h30.set(Calendar.MINUTE, 30);
					Calendar toDay12h = Calendar.getInstance();
					toDay12h.setTime(new Date());
					toDay12h.set(Calendar.HOUR_OF_DAY, 12);
					toDay12h.set(Calendar.MINUTE, 0);
					if (!getNhanVien().isBanDieuHanh()) {
						if (calNgayThucHien.get(Calendar.DAY_OF_YEAR) < calNgayDatTour.get(Calendar.DAY_OF_YEAR)
								&& calNgayThucHien.get(Calendar.YEAR) <= calNgayDatTour.get(Calendar.YEAR)) {
							System.out.println("Ngày thực hiện tour không hợp lệ");
							addInvalidMessage(ctx, "lblErrNgayThucHienTour",
									"Ngày thực hiện tour không được nhỏ hơn ngày đặt tour");
						} else {
							if(getNhanVien().isNhomThanhVien() || getNhanVien().isBanDieuHanh()) {
								if (toDay.getTime().before(toDay7h30.getTime())) {
									System.out.println("truoc 7h30");
								} else if (toDay.getTime().before(toDay12h.getTime())) {
									System.out.println("sau 7h30 truoc 12h");
									if (getPhanLoaiKhachDiTour().equals(PhanLoaiKhachDiTour.TOUR_BINH_THUONG)
											&& getPhanLoaiTour() != null && getPhanLoaiTour().isTourBuoiChieu()) {
										System.out.println("la tour buoi chieu binh thuong, cho phep cung ngay");
									} else {
										if (calNgayThucHien.get(Calendar.DAY_OF_YEAR) <= calNgayDatTour
												.get(Calendar.DAY_OF_YEAR)
												&& calNgayThucHien.get(Calendar.YEAR) <= calNgayDatTour.get(Calendar.YEAR)) {
											addInvalidMessage(ctx, "lblErrNgayThucHienTour",
													"Ngày thực hiện tour không được bằng ngày hiện tại");
										}
									}
								} else {
									System.out.println("sau 12h");
									if (calNgayThucHien.get(Calendar.DAY_OF_YEAR) <= calNgayDatTour.get(Calendar.DAY_OF_YEAR)
											&& calNgayThucHien.get(Calendar.YEAR) <= calNgayDatTour.get(Calendar.YEAR)) {
										addInvalidMessage(ctx, "lblErrNgayThucHienTour",
												"Ngày thực hiện tour không được bằng ngày hiện tại");
									}
								}
							}
						}
					}
				}
			}
		};
	}

	@Command
	public void saveTinhTrangVe(@BindingParam("list") final Object listObject,
			@BindingParam("attr") @Default(value = "*") final String attr, @BindingParam("wdn") final Window wdn,
			@BindingParam("isHuyVe") final boolean isHuyVe) {
		String msg = "";
		
		if (isHuyVe) {
			Calendar cal = Calendar.getInstance();
			Date date = new Date();
			cal.setTime(date);
			if(getNgayThucHienTour() != null) {
				cal.setTime(getNgayThucHienTour());
			}
			
			JPAQuery<VeDuyet> veDuyetQuery = find(VeDuyet.class)
					.where(QVeDuyet.veDuyet.congTyKinhDoanh.eq(getCongTyKinhDoanh()))
					.where(QVeDuyet.veDuyet.nhomCuaHoi.eq(getNhomCuaHoi()))
					.where(QVeDuyet.veDuyet.ngayThucHienTour.year()
							.eq(cal.get(Calendar.YEAR))
							.and(QVeDuyet.veDuyet.ngayThucHienTour.month()
									.eq(cal.get(Calendar.MONTH) + 1))
							.and(QVeDuyet.veDuyet.ngayThucHienTour.dayOfMonth()
									.eq(cal.get(Calendar.DAY_OF_MONTH))));
			
			DatVeService datVeService = (DatVeService) listObject;
			datVeService.setCongTyKinhDoanh(null);
			this.setTinhTrangVe(TinhTrangVeEnum.HUY_VE);
			msg = "Hủy vé thành công!";
			saveNotShowNotification();
			
			//Huy ve duyet trong ban duyet ve
			VeDuyet veDuyet = veDuyetQuery.fetchFirst();
			int soLuongVDTru = getSoLuongNguoiLon() + getSoLuongTreEmDuoi5Tuoi() + getSoLuongTreEmDuoi10Tuoi();
			if(veDuyet != null) {
				veDuyet.setSoLuongVeDuyet(veDuyet.getSoLuongVeDuyet() - soLuongVDTru);
				veDuyet.saveNotShowNotification();
			}
			
			showNotification(msg, "", "success");
			wdn.detach();
			// BindUtils.postNotifyChange(null, null, listObject,
			// "congTyKinhDoanh");
			// BindUtils.postNotifyChange(null, null, listObject,
			// "nhomCuaHois");
			// BindUtils.postNotifyChange(null, null, listObject,
			// "phongBanVeLeQuery");
			if (attr != null && "".equals(attr)) {
				BindUtils.postNotifyChange(null, null, listObject, "phongBanVeLeQuery");
				BindUtils.postNotifyChange(null, null, listObject, "listDatVeQuery");
			} else {
				BindUtils.postNotifyChange(null, null, listObject, attr);
			}
		} else {
			if (getNhomCuaHoi() != null && getCongTyKinhDoanh() != null) {
				JPAQuery<CongTyKinhDoanh> congTyKinhDoanh = find(CongTyKinhDoanh.class)
						.where(QCongTyKinhDoanh.congTyKinhDoanh.id.eq(getCongTyKinhDoanh().getId()));

				long soKhachToiDa = congTyKinhDoanh.fetchFirst().getSoKhachToiDa();
				long tongVe = soKhachCongTyHienTai();
				tongVe += (long) (getSoLuongNguoiLon() + getSoLuongTreEmDuoi10Tuoi() + getSoLuongTreEmDuoi5Tuoi());

				if (tongVe > soKhachToiDa) {
					Messagebox.show(
							"Đặt vé vượt số lượng cho phép trong ngày (" + soKhachToiDa
									+ " vé). \nBấm 'Chấp nhận' để đưa vé sang trang thái đợi duyệt. \nBấm 'Bỏ qua' để điều chỉnh lại thông tin.",
							"Xác nhận đặt vé", Messagebox.CANCEL | Messagebox.OK, Messagebox.QUESTION,
							new EventListener<Event>() {
								@Override
								public void onEvent(final Event event) {
									if (Messagebox.ON_OK.equals(event.getName())) {
										setTinhTrangVe(TinhTrangVeEnum.PHUC_HOI_VE);
										setTrangThaiDuyetVeEnum(TrangThaiDuyetVeEnum.DANG_DUYET);
										saveNotShowNotification();
										showNotification(
												"Vé của bạn đã chuyển sang trạng thái đợi duyệt. Vui lòng chờ kết quả",
												"", "success");
										wdn.detach();
										// BindUtils.postNotifyChange(null,
										// null, listObject, "congTyKinhDoanh");
										// BindUtils.postNotifyChange(null,
										// null, listObject, "nhomCuaHois");
										// BindUtils.postNotifyChange(null,
										// null, listObject,
										// "phongBanVeLeQuery");
										if (attr != null && "".equals(attr)) {
											BindUtils.postNotifyChange(null, null, listObject, "phongBanVeLeQuery");
											BindUtils.postNotifyChange(null, null, listObject, "listDatVeQuery");
										} else {
											BindUtils.postNotifyChange(null, null, listObject, attr);
										}

										if (listPropertyChangeEvent.size() > 0) {
											LichSuDatVe lsDV = new LichSuDatVe();
											lsDV.setDatVe(DatVe.this);
											if (isHuyVe) {
												lsDV.setNoiDung("Hủy vé");
											} else {
												lsDV.setNoiDung("Phục hồi vé");
											}
											lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
											lsDV.saveNotShowNotification();
										}
									}
								}

							});
				} else {
					this.setTinhTrangVe(TinhTrangVeEnum.PHUC_HOI_VE);
					msg = "Phục hồi vé thành công!";
					saveNotShowNotification();
					showNotification(msg, "", "success");
					wdn.detach();
					// BindUtils.postNotifyChange(null, null, listObject,
					// "congTyKinhDoanh");
					// BindUtils.postNotifyChange(null, null, listObject,
					// "nhomCuaHois");
					// BindUtils.postNotifyChange(null, null, listObject,
					// "phongBanVeLeQuery");
					if (attr != null && "".equals(attr)) {
						BindUtils.postNotifyChange(null, null, listObject, "phongBanVeLeQuery");
						BindUtils.postNotifyChange(null, null, listObject, "listDatVeQuery");
					} else {
						BindUtils.postNotifyChange(null, null, listObject, attr);
					}

					if (listPropertyChangeEvent.size() > 0) {
						LichSuDatVe lsDV = new LichSuDatVe();
						lsDV.setDatVe(this);
						if (isHuyVe) {
							lsDV.setNoiDung("Hủy vé");
						} else {
							lsDV.setNoiDung("Phục hồi vé");
						}
						lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
						lsDV.saveNotShowNotification();
					}
				}

			} else {
				this.setTinhTrangVe(TinhTrangVeEnum.PHUC_HOI_VE);
				msg = "Phục hồi vé thành công!";
				saveNotShowNotification();
				showNotification(msg, "", "success");
				wdn.detach();
				// BindUtils.postNotifyChange(null, null, listObject,
				// "congTyKinhDoanh");
				// BindUtils.postNotifyChange(null, null, listObject,
				// "nhomCuaHois");
				// BindUtils.postNotifyChange(null, null, listObject,
				// "phongBanVeLeQuery");
				if (attr != null && "".equals(attr)) {
					BindUtils.postNotifyChange(null, null, listObject, "phongBanVeLeQuery");
					BindUtils.postNotifyChange(null, null, listObject, "listDatVeQuery");
				} else {
					BindUtils.postNotifyChange(null, null, listObject, attr);
				}

				if (listPropertyChangeEvent.size() > 0) {
					LichSuDatVe lsDV = new LichSuDatVe();
					lsDV.setDatVe(this);
					if (isHuyVe) {
						lsDV.setNoiDung("Hủy vé");
					} else {
						lsDV.setNoiDung("Phục hồi vé");
					}
					lsDV.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
					lsDV.saveNotShowNotification();
				}
			}

		}
	}

	@Command
	public void deleteTrangThaiDatVeConfirmAndNotify(final @BindingParam("notify") Object beanObject,
			final @BindingParam("attr") @Default(value = "*") String attr,
			final @BindingParam("isPhucHoi") boolean isPhucHoi) {
		if (!checkInUse()) {
			Messagebox.show("Bạn muốn vé này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK, Messagebox.QUESTION,
					new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								doDelete(true);
								showNotification("Xóa vé thành công!", "", "success");
								if (beanObject != null) {
									if (!isPhucHoi) {
										DatVeService datVeService = (DatVeService) beanObject;
										datVeService.setCongTyKinhDoanh(null);
									}
									// BindUtils.postNotifyChange(null, null,
									// beanObject, "congTyKinhDoanh");
									// BindUtils.postNotifyChange(null, null,
									// beanObject, "nhomCuaHois");
									// BindUtils.postNotifyChange(null, null,
									// beanObject, "phongBanVeLeQuery");
									BindUtils.postNotifyChange(null, null, beanObject, attr);
								}
							}
						}

					});
		}
	}

	@Transient
	public boolean getDuocPhepDuyetVe() {
		System.out.println("nhom " + this.getNhomCuaHoi().getTen() + " cong ty " + this.getCongTyKinhDoanh().getTen());
		boolean check = false;
		long tongSoKhachToiDaCuaNhom = getNhomCuaHoi().getTongSoGhe();
		long tongSoKhachDatCuaCongTy = getTongSoLuongVeDatCuaCongTy();
		long soLuongKhach = getSoLuongNguoiLon() + getSoLuongTreEmDuoi10Tuoi() + getSoLuongTreEmDuoi5Tuoi();
		long soGhe = tongSoKhachToiDaCuaNhom - tongSoKhachDatCuaCongTy;
		if (soGhe >= soLuongKhach) {
			check = true;
		}
		if (getNhanVien() != null) {
			if (getNhanVien().isBanDieuHanh()) {
				check = true;
			}
		}
		return check;
	}

	@Transient
	public int getTongSoLuongVeDatCuaCongTy() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getNgayKhachDat());

		int total = 0;
		JPAQuery<DatVe> datVeQuery = find(DatVe.class).where(QDatVe.datVe.nhomCuaHoi.eq(getNhomCuaHoi()))
				.where(QDatVe.datVe.trangThaiDuyetVeEnum.eq(TrangThaiDuyetVeEnum.DA_DUYET))
				.where(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.DAT_VE)
						.or(QDatVe.datVe.tinhTrangVe.eq(TinhTrangVeEnum.PHUC_HOI_VE)))
				.where(QDatVe.datVe.nhomCuaHoi.isNotNull().and(QDatVe.datVe.congTyKinhDoanh.isNotNull()))
				.where(QDatVe.datVe.ngayThucHienTour.year().eq(cal.get(Calendar.YEAR))
						.and(QDatVe.datVe.ngayThucHienTour.month().eq(cal.get(Calendar.MONTH) + 1))
						.and(QDatVe.datVe.ngayThucHienTour.dayOfMonth().eq(cal.get(Calendar.DAY_OF_MONTH))));

		if (datVeQuery != null) {
			List<DatVe> datVes = datVeQuery.fetch();
			for (DatVe datVe : datVes) {
				total += datVe.getSoLuongNguoiLon() + datVe.getSoLuongTreEmDuoi10Tuoi()
						+ datVe.getSoLuongTreEmDuoi5Tuoi();
			}
		}
		return total;
	}

	@Command
	public void duyetVeConfirmAndNotify(final @BindingParam("notify") Object beanObject,
			final @BindingParam("attr") @Default(value = "*") String attr) {
		// long tongSoKhachToiDaCuaNhom = getNhomCuaHoi().getTongSoGhe();
		// long tongSoKhachDatCuaCongTy = getTongSoLuongVeDatCuaCongTy();
		long soLuongKhach = getSoLuongNguoiLon() + getSoLuongTreEmDuoi10Tuoi() + getSoLuongTreEmDuoi5Tuoi();
		// long soGhe = tongSoKhachToiDaCuaNhom - tongSoKhachDatCuaCongTy;

		if (!checkInUse()) {
			Messagebox.show("Bạn muốn duyệt vé này?", "Xác nhận duyệt vé", Messagebox.CANCEL | Messagebox.OK,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								Calendar cal = Calendar.getInstance();
								cal.setTime(getNgayThucHienTour());
								transactioner().execute(new TransactionCallbackWithoutResult() {
									@Override
									protected void doInTransactionWithoutResult(TransactionStatus arg0) {
										CongTyKinhDoanh congTy = getCongTyKinhDoanh();
										JPAQuery<VeDuyet> veDuyetQuery = find(VeDuyet.class)
												.where(QVeDuyet.veDuyet.congTyKinhDoanh.eq(congTy))
												.where(QVeDuyet.veDuyet.nhomCuaHoi.eq(getNhomCuaHoi()))
												.where(QVeDuyet.veDuyet.ngayThucHienTour.year()
														.eq(cal.get(Calendar.YEAR))
														.and(QVeDuyet.veDuyet.ngayThucHienTour.month()
																.eq(cal.get(Calendar.MONTH) + 1))
														.and(QVeDuyet.veDuyet.ngayThucHienTour.dayOfMonth()
																.eq(cal.get(Calendar.DAY_OF_MONTH))));

										VeDuyet vd = new VeDuyet();
										vd.setNgayTao(new Date());
										vd.setNgaySua(new Date());
										vd.setNguoiTao(getNhanVien());
										vd.setCongTyKinhDoanh(congTy);
										vd.setNhomCuaHoi(getNhomCuaHoi());
										vd.setNgayThucHienTour(getNgayThucHienTour());
										if (veDuyetQuery.fetchCount() > 0) {
											vd = veDuyetQuery.fetchFirst();
											vd.setNgaySua(new Date());
											vd.setNguoiSua(getNhanVien());
										}
										vd.setSoLuongVeDuyet((int) soLuongKhach + vd.getSoLuongVeDuyet());
										if (congTy.getVeDuyets().contains(vd)) {
											congTy.getVeDuyets().remove(vd);
										}
										congTy.getVeDuyets().add(vd);
										congTy.saveNotShowNotification();
										setTrangThaiDuyetVeEnum(TrangThaiDuyetVeEnum.DA_DUYET);
										saveNotShowNotification();
										showNotification("Duyệt vé thành công!", "", "success");
										if (beanObject != null) {
											BindUtils.postNotifyChange(null, null, beanObject, attr);
										}
									}
								});
							}
						}

					});
		}
	}
	
	

	@Transient
	public String getTongGia() {
		JPAQuery<Setting> query = find(Setting.class);
		if (query != null) {
			Setting setting = query.fetchFirst();
			return (getSoLuongNguoiLon() * Long.valueOf(setting.getGiaVeNguoiLon().replace(".", ""))
					+ (getSoLuongTreEmDuoi10Tuoi() * Long.valueOf(setting.getGiaVeTreEmDuoi10Tuoi().replace(".", ""))
							+ (getSoLuongTreEmDuoi5Tuoi()
									* Long.valueOf(setting.getGiaVeTreEmDuoi5Tuoi().replace(".", "")))))
					+ "";
		}
		return "";
	}

	@Transient
	public List<LichSuDatVe> getListLichSuDatVe() {
		List<LichSuDatVe> list = new ArrayList<LichSuDatVe>();
		list = find(LichSuDatVe.class).where(QLichSuDatVe.lichSuDatVe.datVe.eq(this))
				.orderBy(QLichSuDatVe.lichSuDatVe.ngayTao.desc()).fetch();
		return list;
	}

	@Command
	public void redirectPageChiTietLichSu(@BindingParam("zul") String zul, @BindingParam("vmArgs") Object vmArgs,
			@BindingParam("vm") Object vm, @BindingParam("lichSu") LichSuDatVe lichSu) {
		Map<String, Object> args = new HashMap<>();
		args.put("vmArgs", vmArgs);
		args.put("vm", vm);
		args.put("lichSu", lichSu);
		Executions.createComponents(zul, null, args);
	}

	private String maVe = "";

	@Transient
	public String getMaVe() {
		String idStr = getId().toString();
		if (idStr.length() == 1) {
			maVe = "00000" + idStr;
		} else if (idStr.length() == 2) {
			maVe = "0000" + idStr;
		} else if (idStr.length() == 3) {
			maVe = "000" + idStr;
		} else if (idStr.length() == 4) {
			maVe = "00" + idStr;
		} else if (idStr.length() == 5) {
			maVe = "0" + idStr;
		} else {
			maVe = idStr;
		}
		return maVe;
	}

	public void setMaVe(String maVe) {
		this.maVe = maVe;
	}
	
}
