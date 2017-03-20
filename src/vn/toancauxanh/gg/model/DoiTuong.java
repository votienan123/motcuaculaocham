package vn.toancauxanh.gg.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.JAXBElement;

import org.docx4j.XmlUtils;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Body;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.sys.ValidationMessages;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.image.AImage;
import org.zkoss.image.Image;
import org.zkoss.io.Files;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Messagebox;

import com.querydsl.core.annotations.QueryInit;

import vn.toancauxanh.gg.model.enums.DoiTuongNghien;
import vn.toancauxanh.gg.model.enums.LoaiDoiTuong;
import vn.toancauxanh.gg.model.enums.LoaiHinhThucXuLy;
import vn.toancauxanh.gg.model.enums.LoaiXuLy;
import vn.toancauxanh.gg.model.enums.NoiCapCMND;
import vn.toancauxanh.gg.model.enums.TinhTrangViecLam;
import vn.toancauxanh.model.Model;

@Entity
@Table(name = "doituong", indexes = { @Index(columnList = "hoVaTen")})
public class DoiTuong extends Model<DoiTuong> implements PropertyChangeListener{
	
	private String hoVaTen = "";
	private String tenKhac = "";
	private GioiTinh gioiTinh;
	private Date ngaySinh;
	private int namSinh;
	private String dacDiemNhanDang ="";
	private String soDinhDanh = "";
	private String soCMND = "";
	private Date ngayCapCMND;
	private NoiCapCMND noiCapCMND;
	private TrinhDoHocVan trinhDoVanHoa;
	private TinhTrangViecLam tinhTrangViecLam;
	private ThanhPhanDoiTuong thanhPhanDoiTuong;
	private NganhNghe ngheNghiep;	
	
	private DonViHanhChinh diaChiThuongTruTinh;
	private DonViHanhChinh diaChiThuongTruHuyen;
	private DonViHanhChinh diaChiThuongTruXa;
	private ToDanPho diaChiThuongTruToDanPho;
	private String diaChiThuongTru = "";
	
	private DonViHanhChinh diaChiHienNayTinh;
	private DonViHanhChinh diaChiHienNayHuyen;
	private DonViHanhChinh diaChiHienNayXa;
	private ToDanPho diaChiHienNayToDanPho;
	private String diaChiHienNay = "";
	
	private Date thoiDiemBatDauSuDungMaTuy;
	private ThongTinViPham newThongTinViPham = new ThongTinViPham();
	private Image imageContent;
	private String iconName = "";
	private String iconUrl = "";
	private DoiTuongNghien doiTuongNghien;
	private boolean coQuyetDinhTruyNa;
	private String toiPhamTruyNa = "";
	private String donViTruyNa = "";
	private boolean coQuyetDinhTruyTim;
	private String dauHieuNghiVanVPPL = "";
	private String bieuHienLoanThanKinh = "";
	private String canBoQLLoanThanKinh = "";
	private String donViCanBoQLLoanThanKinh = "";
	private String sdtCanBoQLLoanThanKinh = "";
	private String canBoQuanLy = "";
	private ToChucDoanTheGiamSat donViCanBoQuanLy;
	private String soDTCanBoQuanLy = "";
	private List<ThongTinThanNhan> listThongTinThanNhan = new ArrayList<ThongTinThanNhan>();
	private boolean flagEmpty = false;
	private boolean flagEmpty2 = false;
	private ThongTinViPham thongTinViPhamHienHanh;
	private Set<MaTuySuDung> maTuySuDungs = new HashSet<MaTuySuDung>();
	private boolean	nguoiNghien;
	private boolean nguoiSuDungTraiPhep;
	private boolean coTienAn;
	private boolean coTienSu;
	private boolean coHuongThuChinhSach;
	private boolean coHuongThuChinhSachViPham;
	private boolean flagChange = false;
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	public DoiTuong() {
		pcs.addPropertyChangeListener(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {		
		PropertyChangeObject obj = new PropertyChangeObject(evt.getPropertyName(), 
				evt.getOldValue() != null ? evt.getOldValue().toString() : "", 
				evt.getNewValue() != null ?	evt.getNewValue().toString() : "");
			//System.out.println("obj: " + obj.toString());
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
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "doituong_matuysudung", joinColumns = {
			@JoinColumn(name = "doituong_id") }, inverseJoinColumns = { @JoinColumn(name = "matuysudung_id") })
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	public Set<MaTuySuDung> getMaTuySuDungs() {
		return maTuySuDungs;
	}

	public void setMaTuySuDungs(Set<MaTuySuDung> maTuySuDungs) {
		this.maTuySuDungs = maTuySuDungs;
	}

	public boolean isNguoiNghien() {
		return nguoiNghien;
	}

	public void setNguoiNghien(boolean nguoiNghien) {
		this.nguoiNghien = nguoiNghien;
	}

	public boolean isNguoiSuDungTraiPhep() {
		return nguoiSuDungTraiPhep;
	}

	public void setNguoiSuDungTraiPhep(boolean nguoiSuDungTraiPhep) {
		this.nguoiSuDungTraiPhep = nguoiSuDungTraiPhep;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		String oldValue = this.hoVaTen;
		this.hoVaTen = hoVaTen;
		pcs.firePropertyChange("Họ và tên", oldValue, hoVaTen);
	}
	
	public int getNamSinh() {
		return namSinh;
	}

	public void setNamSinh(int namSinh) {
		int oldValue = this.namSinh;
		this.namSinh = namSinh;
		pcs.firePropertyChange("Năm sinh", oldValue, namSinh);
	}

	public String getTenKhac() {
		return tenKhac;
	}

	public void setTenKhac(String tenKhac) {
		String oldValue = this.tenKhac;
		this.tenKhac = tenKhac;
		pcs.firePropertyChange("Tên khác", oldValue, tenKhac);
	}
	
	public String getCanBoQuanLy() {
		return canBoQuanLy;
	}

	public void setCanBoQuanLy(String canBoQuanLy) {
		String oldValue = this.canBoQuanLy;
		this.canBoQuanLy = canBoQuanLy;
		pcs.firePropertyChange("Cán bộ quản lý", oldValue, canBoQuanLy);
	}
	
	@ManyToOne
	@QueryInit("*.*.*")
	public ThongTinViPham getThongTinViPhamHienHanh() {
		return thongTinViPhamHienHanh;
	}

	public void setThongTinViPhamHienHanh(ThongTinViPham thongTinViPhamHienHanh) {
		this.thongTinViPhamHienHanh = thongTinViPhamHienHanh;
	}

	@ManyToOne
	public ToChucDoanTheGiamSat getDonViCanBoQuanLy() {
		return donViCanBoQuanLy;
	}

	public void setDonViCanBoQuanLy(ToChucDoanTheGiamSat donViCanBoQuanLy) {
		ToChucDoanTheGiamSat oldValue = this.donViCanBoQuanLy;
		this.donViCanBoQuanLy = donViCanBoQuanLy;
		pcs.firePropertyChange("Đơn vị của cán bộ", oldValue != null ? oldValue.getTen() : "", 
				donViCanBoQuanLy != null ? donViCanBoQuanLy.getTen() : "");
	}

	public String getSoDTCanBoQuanLy() {
		return soDTCanBoQuanLy;
	}

	public void setSoDTCanBoQuanLy(String soDTCanBoQuanLy) {
		String oldValue = this.soDTCanBoQuanLy;
		this.soDTCanBoQuanLy = soDTCanBoQuanLy;
		pcs.firePropertyChange("Số điện thoại cán bộ", oldValue, soDTCanBoQuanLy);
	}

	@ManyToOne
	public GioiTinh getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(GioiTinh gioiTinh) {
		GioiTinh oldValue = this.gioiTinh;
		this.gioiTinh = gioiTinh;
		pcs.firePropertyChange("Giới tính", oldValue != null ? oldValue.getTen() : "", 
				gioiTinh != null ? gioiTinh.getTen() : "");
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		Date oldValue = this.ngaySinh;
		this.ngaySinh = ngaySinh;
		pcs.firePropertyChange("Ngày sinh", getDate2String(oldValue), getDate2String(ngaySinh));
	}
	
	public boolean isCoTienAn() {
		return coTienAn;
	}

	public void setCoTienAn(boolean coTienAn) {
		this.coTienAn = coTienAn;
	}

	public boolean isCoTienSu() {
		return coTienSu;
	}

	public void setCoTienSu(boolean coTienSu) {
		this.coTienSu = coTienSu;
	}
	
	public boolean isCoHuongThuChinhSach() {
		return coHuongThuChinhSach;
	}

	public void setCoHuongThuChinhSach(boolean coHuongThuChinhSach) {
		this.coHuongThuChinhSach = coHuongThuChinhSach;
	}

	public boolean isCoHuongThuChinhSachViPham() {
		return coHuongThuChinhSachViPham;
	}

	public void setCoHuongThuChinhSachViPham(boolean coHuongThuChinhSachViPham) {
		this.coHuongThuChinhSachViPham = coHuongThuChinhSachViPham;
	}

	public String getDacDiemNhanDang() {
		return dacDiemNhanDang;
	}

	public void setDacDiemNhanDang(String dacDiemNhanDang) {
		String oldValue = this.dacDiemNhanDang;
		this.dacDiemNhanDang = dacDiemNhanDang;
		pcs.firePropertyChange("Đặc điểm nhận dạng", oldValue, dacDiemNhanDang);
	}

	public String getSoCMND() {
		return soCMND;
	}

	public void setSoCMND(String soCMND) {
		String oldValue = this.soCMND;
		this.soCMND = soCMND;
		pcs.firePropertyChange("Số CMND", oldValue, soCMND);
	}
	
	public String getSoDinhDanh() {
		return soDinhDanh;
	}

	public void setSoDinhDanh(String soDinhDanh) {
		String oldValue = this.soDinhDanh;
		this.soDinhDanh = soDinhDanh;
		pcs.firePropertyChange("Số thẻ căn cước", oldValue, soDinhDanh);
	}

	public Date getNgayCapCMND() {
		return ngayCapCMND;
	}

	public void setNgayCapCMND(Date ngayCapCMND) {
		Date oldValue = this.ngayCapCMND;
		this.ngayCapCMND = ngayCapCMND;
		pcs.firePropertyChange("Ngày cấp CMND", getDate2String(oldValue), getDate2String(ngayCapCMND));
	}

	@Enumerated(EnumType.STRING)
	public NoiCapCMND getNoiCapCMND() {
		return noiCapCMND;
	}

	public void setNoiCapCMND(NoiCapCMND noiCapCMND) {
		NoiCapCMND oldValue = this.noiCapCMND;
		this.noiCapCMND = noiCapCMND;
		pcs.firePropertyChange("Nơi cấp CMND", oldValue != null ? oldValue.getText() : "", 
				noiCapCMND != null ? noiCapCMND.getText() : "");
	}
	
	@Enumerated(EnumType.STRING)
	public DoiTuongNghien getDoiTuongNghien() {
		return doiTuongNghien;
	}

	public void setDoiTuongNghien(DoiTuongNghien doiTuongNghien) {
		DoiTuongNghien oldValue = this.doiTuongNghien;
		this.doiTuongNghien = doiTuongNghien;
		pcs.firePropertyChange("Đối tượng nghiện", oldValue != null ? oldValue.getText() : "", 
				doiTuongNghien != null ? doiTuongNghien.getText() : "");
	}

	@ManyToOne
	public TrinhDoHocVan getTrinhDoVanHoa() {
		return trinhDoVanHoa;
	}

	public void setTrinhDoVanHoa(TrinhDoHocVan trinhDoVanHoa) {
		TrinhDoHocVan oldValue = this.trinhDoVanHoa;
		this.trinhDoVanHoa = trinhDoVanHoa;
		pcs.firePropertyChange("Trình độ văn hóa", oldValue != null ? oldValue.getTen() : "", 
				trinhDoVanHoa != null ? trinhDoVanHoa.getTen() : "");
	}

	@ManyToOne
	public DonViHanhChinh getDiaChiThuongTruTinh() {
		return diaChiThuongTruTinh;
	}

	public void setDiaChiThuongTruTinh(DonViHanhChinh diaChiThuongTruTinh) {
		DonViHanhChinh oldValue = this.diaChiThuongTruTinh;
		this.diaChiThuongTruTinh = diaChiThuongTruTinh;
		pcs.firePropertyChange("Địa chỉ thường trú thành phố/tỉnh", oldValue != null ? oldValue.getTen() : "", 
				diaChiThuongTruTinh != null ? diaChiThuongTruTinh.getTen() : "");
	}

	@ManyToOne()
	public DonViHanhChinh getDiaChiThuongTruHuyen() {
		return diaChiThuongTruHuyen;
	}
	
	public void setDiaChiThuongTruHuyen(DonViHanhChinh diaChiThuongTruHuyen) {
		DonViHanhChinh oldValue = this.diaChiThuongTruHuyen;
		this.diaChiThuongTruHuyen = diaChiThuongTruHuyen;
		pcs.firePropertyChange("Địa chỉ thường trú quận/huyện", oldValue != null ? oldValue.getTen() : "", 
				diaChiThuongTruHuyen != null ? diaChiThuongTruHuyen.getTen() : "");
	}

	@ManyToOne
	public DonViHanhChinh getDiaChiThuongTruXa() {
		return diaChiThuongTruXa;
	}

	public void setDiaChiThuongTruXa(DonViHanhChinh diaChiThuongTruXa) {
		DonViHanhChinh oldValue = this.diaChiThuongTruXa;
		this.diaChiThuongTruXa = diaChiThuongTruXa;
		pcs.firePropertyChange("Địa chỉ thường trú phường/xã", oldValue != null ? oldValue.getTen() : "", 
				diaChiThuongTruXa != null ? diaChiThuongTruXa.getTen() : "");
	}

	@ManyToOne
	public ToDanPho getDiaChiThuongTruToDanPho() {
		return diaChiThuongTruToDanPho;
	}

	public void setDiaChiThuongTruToDanPho(ToDanPho diaChiThuongTruToDanPho) {
		ToDanPho oldValue = this.diaChiThuongTruToDanPho;
		this.diaChiThuongTruToDanPho = diaChiThuongTruToDanPho;
		pcs.firePropertyChange("Địa chỉ thường trú tổ/thôn", oldValue != null ? oldValue.getTen() : "", 
				diaChiThuongTruToDanPho != null ? diaChiThuongTruToDanPho.getTenVietTat() : "");
	}

	public String getDiaChiThuongTru() {
		return diaChiThuongTru;
	}

	public void setDiaChiThuongTru(String diaChiThuongTru) {
		String oldValue = this.diaChiThuongTru;
		this.diaChiThuongTru = diaChiThuongTru;
		pcs.firePropertyChange("Số nhà, tên đường thường trú", oldValue, diaChiThuongTru);
	}

	@ManyToOne
	public DonViHanhChinh getDiaChiHienNayTinh() {
		return diaChiHienNayTinh;
	}

	public void setDiaChiHienNayTinh(DonViHanhChinh diaChiHienNayTinh) {
		DonViHanhChinh oldValue = this.diaChiHienNayTinh;
		this.diaChiHienNayTinh = diaChiHienNayTinh;
		pcs.firePropertyChange("Địa chỉ hiện nay thành phố/tỉnh", oldValue != null ? oldValue.getTen() : "", 
				diaChiHienNayTinh != null ? diaChiHienNayTinh.getTen() : "");
	}

	@ManyToOne
	public DonViHanhChinh getDiaChiHienNayHuyen() {
		return diaChiHienNayHuyen;
	}

	public void setDiaChiHienNayHuyen(DonViHanhChinh diaChiHienNayHuyen) {
		DonViHanhChinh oldValue = this.diaChiHienNayHuyen;
		this.diaChiHienNayHuyen = diaChiHienNayHuyen;
		pcs.firePropertyChange("Địa chỉ hiện nay quận/huyện", oldValue != null ? oldValue.getTen() : "", 
				diaChiHienNayHuyen != null ? diaChiHienNayHuyen.getTen() : "");
	}

	@ManyToOne
	public DonViHanhChinh getDiaChiHienNayXa() {
		return diaChiHienNayXa;
	}

	public void setDiaChiHienNayXa(DonViHanhChinh diaChiHienNayXa) {
		DonViHanhChinh oldValue = this.diaChiHienNayXa;
		this.diaChiHienNayXa = diaChiHienNayXa;
		pcs.firePropertyChange("Địa chỉ hiện nay phường/xã", oldValue != null ? oldValue.getTen() : "", 
				diaChiHienNayXa != null ? diaChiHienNayXa.getTen() : "");
	}

	@ManyToOne
	public ToDanPho getDiaChiHienNayToDanPho() {
		return diaChiHienNayToDanPho;
	}

	public void setDiaChiHienNayToDanPho(ToDanPho diaChiHienNayToDanPho) {
		ToDanPho oldValue = this.diaChiHienNayToDanPho;
		this.diaChiHienNayToDanPho = diaChiHienNayToDanPho;
		pcs.firePropertyChange("Địa chỉ hiện nay tổ/thôn", oldValue != null ? oldValue.getTen() : "", 
				diaChiHienNayToDanPho != null ? diaChiHienNayToDanPho.getTenVietTat() : "");
	}

	public String getDiaChiHienNay() {
		return diaChiHienNay;
	}

	public void setDiaChiHienNay(String diaChiHienNay) {
		String oldValue = this.diaChiHienNay;
		this.diaChiHienNay = diaChiHienNay;
		pcs.firePropertyChange("Số nhà, tên đường hiện nay", oldValue, diaChiHienNay);
	}

	@Enumerated(EnumType.STRING)
	public TinhTrangViecLam getTinhTrangViecLam() {
		return tinhTrangViecLam;
	}

	public void setTinhTrangViecLam(TinhTrangViecLam tinhTrangViecLam) {
		TinhTrangViecLam oldValue = this.tinhTrangViecLam;
		this.tinhTrangViecLam = tinhTrangViecLam;
		pcs.firePropertyChange("Tình trạng việc làm", oldValue != null ? oldValue.getText() : "", 
				tinhTrangViecLam != null ? tinhTrangViecLam.getText() : "");
	}

	@ManyToOne
	public ThanhPhanDoiTuong getThanhPhanDoiTuong() {
		return thanhPhanDoiTuong;
	}

	public void setThanhPhanDoiTuong(ThanhPhanDoiTuong thanhPhanDoiTuong) {
		ThanhPhanDoiTuong oldValue = this.thanhPhanDoiTuong;
		this.thanhPhanDoiTuong = thanhPhanDoiTuong;
		pcs.firePropertyChange("Thành phần gia đình", oldValue != null ? oldValue.getTen() : "", 
				thanhPhanDoiTuong != null ? thanhPhanDoiTuong.getTen() : "");
	}

	@ManyToOne
	public NganhNghe getNgheNghiep() {
		return ngheNghiep;
	}

	public void setNgheNghiep(NganhNghe ngheNghiep) {
		NganhNghe oldValue = this.ngheNghiep;
		this.ngheNghiep = ngheNghiep;
		pcs.firePropertyChange("Nghề nghiệp", oldValue != null ? oldValue.getTen() : "", 
				ngheNghiep != null ? ngheNghiep.getTen() : "");
	}
	
	public boolean isCoQuyetDinhTruyNa() {
		return coQuyetDinhTruyNa;
	}

	public void setCoQuyetDinhTruyNa(boolean isCoQuyetDinhTruyNa) {
		boolean oldValue = this.coQuyetDinhTruyNa;
		this.coQuyetDinhTruyNa = isCoQuyetDinhTruyNa;
		pcs.firePropertyChange("Có quyết định truy nã", oldValue ? "Đúng" : "Sai", isCoQuyetDinhTruyNa ? "Đúng" : "Sai");
	}

	public String getToiPhamTruyNa() {
		return toiPhamTruyNa;
	}

	public void setToiPhamTruyNa(String toiPhamTruyNa) {
		String oldValue = this.toiPhamTruyNa;
		this.toiPhamTruyNa = toiPhamTruyNa;
		pcs.firePropertyChange("Tội bị truy nã", oldValue, toiPhamTruyNa);
	}

	public String getDonViTruyNa() {
		return donViTruyNa;
	}

	public void setDonViTruyNa(String donViTruyNa) {
		String oldValue = this.donViTruyNa;
		this.donViTruyNa = donViTruyNa;
		pcs.firePropertyChange("Đơn vị truy nã", oldValue, donViTruyNa);
	}

	public boolean isCoQuyetDinhTruyTim() {
		return coQuyetDinhTruyTim;
	}

	public void setCoQuyetDinhTruyTim(boolean isCoQuyetDinhTruyTim) {
		boolean oldValue = this.coQuyetDinhTruyTim;
		this.coQuyetDinhTruyTim = isCoQuyetDinhTruyTim;
		pcs.firePropertyChange("Có quyết định truy nã", oldValue ? "Đúng" : "Sai", isCoQuyetDinhTruyTim ? "Đúng" : "Sai");
	}

	public String getDauHieuNghiVanVPPL() {
		return dauHieuNghiVanVPPL;
	}

	public void setDauHieuNghiVanVPPL(String dauHieuNghiVanVPPL) {
		String oldValue = this.dauHieuNghiVanVPPL;
		this.dauHieuNghiVanVPPL = dauHieuNghiVanVPPL;
		pcs.firePropertyChange("Dấu hiệu nghiên vấn VPPL", oldValue, dauHieuNghiVanVPPL);
	}

	public String getBieuHienLoanThanKinh() {
		return bieuHienLoanThanKinh;
	}

	public void setBieuHienLoanThanKinh(String bieuHienLoanThanKinh) {
		String oldValue = this.bieuHienLoanThanKinh;
		this.bieuHienLoanThanKinh = bieuHienLoanThanKinh;
		pcs.firePropertyChange("Biểu hiện loạn thần kinh", oldValue, bieuHienLoanThanKinh);
	}

	public String getCanBoQLLoanThanKinh() {
		return canBoQLLoanThanKinh;
	}

	public void setCanBoQLLoanThanKinh(String canBoQLLoanThanKinh) {
		this.canBoQLLoanThanKinh = canBoQLLoanThanKinh;
		
	}

	public String getDonViCanBoQLLoanThanKinh() {
		return donViCanBoQLLoanThanKinh;
	}

	public void setDonViCanBoQLLoanThanKinh(String donViCanBoQLLoanThanKinh) {
		this.donViCanBoQLLoanThanKinh = donViCanBoQLLoanThanKinh;
	}

	public String getSdtCanBoQLLoanThanKinh() {
		return sdtCanBoQLLoanThanKinh;
	}

	public void setSdtCanBoQLLoanThanKinh(String sdtCanBoQLLoanThanKinh) {
		this.sdtCanBoQLLoanThanKinh = sdtCanBoQLLoanThanKinh;
	}

	public Date getThoiDiemBatDauSuDungMaTuy() {
		return thoiDiemBatDauSuDungMaTuy;
	}

	public void setThoiDiemBatDauSuDungMaTuy(Date thoiDiemBatDauSuDungMaTuy) {
		this.thoiDiemBatDauSuDungMaTuy = thoiDiemBatDauSuDungMaTuy;
	}
	
	@Transient
	public ThongTinViPham getNewThongTinViPham() {
		return newThongTinViPham;
	}

	public void setNewThongTinViPham(ThongTinViPham newThongTinViPham) {
		this.newThongTinViPham = newThongTinViPham;
	}	
	
	@Transient
	public List<ThongTinViPham> getListThongTinViPham() {
		List<ThongTinViPham> list = new ArrayList<ThongTinViPham>();
		list = find(ThongTinViPham.class)
				.where(QThongTinViPham.thongTinViPham.doiTuong.id.eq(this.getId()))
				.where(QThongTinViPham.thongTinViPham.trangThai.eq(core().TT_AP_DUNG))
				.where(QThongTinViPham.thongTinViPham.lichSu.eq(false))
				.orderBy(QThongTinViPham.thongTinViPham.ngayViPham.desc())
				.fetch();
		return list;
	}
	
	@Transient
	private boolean flagImage = true;

	@Transient
	public org.zkoss.image.Image getImageContent() throws FileNotFoundException, IOException {
		System.out.println("getImageContent flag: " + flagImage);
		if (imageContent == null && !core().TT_DA_XOA.equals(getTrangThai())) {
			if (flagImage) {

				loadImageIsView();
			}
		}
		return imageContent;
	}

	public void setImageContent(org.zkoss.image.Image _imageContent) {
		this.imageContent = _imageContent;
	}

	private void loadImageIsView() throws FileNotFoundException, IOException {
		System.out.println("loadImageIsView");
		String imgName = "";
		String path = "";
		path = folderStore() + getIconName();
		if (!"".equals(getIconUrl()) && new File(path).exists()) {
			System.out.println("exists");
			try (FileInputStream fis = new FileInputStream(new File(path));) {
				setImageContent(new AImage(imgName, fis));
			}
		} else {
			String filePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/backend/assets/img/no_image_user.png");
			try (FileInputStream fis = new FileInputStream(new File(filePath));) {
				setImageContent(new AImage("imge", fis));
			}
		}
	}

	private boolean beforeSaveImg() throws IOException {
		if (getImageContent() == null) {
			return false;
		}
		saveImageToServer();
		return true;
	}
	
	@Command
	public void attachImages(@BindingParam("media") final Media media,
			@BindingParam("vmsgs") final ValidationMessages vmsgs) {
		if (media instanceof org.zkoss.image.Image) {
			String tenFile = media.getName();
			tenFile = tenFile.replace(" ", "");
			tenFile = unAccent(tenFile.substring(0, tenFile.lastIndexOf("."))) + "_"
					+ Calendar.getInstance().getTimeInMillis() + tenFile.substring(tenFile.lastIndexOf("."));
			setImageContent((org.zkoss.image.Image) media);
			setIconName(tenFile);
			if (vmsgs != null) {
				vmsgs.clearKeyMessages("errLabel");
			}
			BindUtils.postNotifyChange(null, null, this, "imageContent");
			BindUtils.postNotifyChange(null, null, this, "iconname");
		} else {
			showNotification("Không phải hình ảnh", "", "warning");
		}
	}

	protected void saveImageToServer() throws IOException {

		Image imageContent2 = getImageContent();
		if (imageContent2 != null) {
			// luu hinh
			if (getIconName() != null && !getIconName().isEmpty()) {
				setIconUrl(folderImageUrl().concat(getIconName()));
				final File baseDir = new File(folderStore().concat(getIconName()));
				Files.copy(baseDir, imageContent2.getStreamData());
			}
		}
	}

	@Transient
	public String folderImageUrl() {
		return "/" + Labels.getLabel("filestore.folder") + "/doituong/";
	}

	@Command
	public void deleteImg() {
		setImageContent(null);
		setIconName("");
		flagImage = false;
		BindUtils.postNotifyChange(null, null, this, "imageContent");
		BindUtils.postNotifyChange(null, null, this, "name");
	}
	
	@Transient
	public String getThongTinViPhamHienHanhStr() {
		if (getThongTinViPhamHienHanh() != null && getThongTinViPhamHienHanh().getHinhThucXuLy() != null) {
			return getThongTinViPhamHienHanh().getHinhThucXuLy().getTen();
		}
		return "Loại ra khỏi quản lý";
	}
	
	@Transient
	public boolean isFlagEmpty() {
		return flagEmpty;
	}

	public void setFlagEmpty(boolean flagEmpty) {
		this.flagEmpty = flagEmpty;
	}
	
	@Transient
	public boolean isFlagEmpty2() {
		return flagEmpty2;
	}

	public void setFlagEmpty2(boolean flagEmpty2) {
		this.flagEmpty2 = flagEmpty2;
	}

	@Transient
	public List<ThongTinThanNhan> getListThongTinThanNhan() {
		if (!noId() && listThongTinThanNhan.isEmpty() && !flagEmpty) {
			listThongTinThanNhan = find(ThongTinThanNhan.class)
					.where(QThongTinThanNhan.thongTinThanNhan.doiTuong.eq(this))
					.fetch();
		}
		return listThongTinThanNhan;
	}

	public void setListThongTinThanNhan(List<ThongTinThanNhan> listThongTinThanNhan) {
		this.listThongTinThanNhan = listThongTinThanNhan;
	}
	
	@Transient
	public List<ThongTinThanNhan> getListThongTinThanNhanAdd() {
		return getListThongTinThanNhan();
	}
	
	
	@Command
	public void themThongTinThanNhan() {
		System.out.println("themThongTinThanNhan");
		ThongTinThanNhan newThongTin = new ThongTinThanNhan();
		newThongTin.setDoiTuong(this);
		listThongTinThanNhan.add(newThongTin);
		BindUtils.postNotifyChange(null, null, this, "listThongTinThanNhan");
		BindUtils.postNotifyChange(null, null, this, "listThongTinThanNhanAdd");
	}
	
	@Command
	public void deleteThongTinThanNhan(@BindingParam("item") final ThongTinThanNhan e) {
		Messagebox.show("Bạn có chắc chắn muốn xóa thông tin thân nhân này không?", "XÁC NHẬN", Messagebox.CANCEL | Messagebox.OK,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							if (getListThongTinThanNhan().size() == 1) {
								setFlagEmpty(true);
							}
							getListThongTinThanNhan().remove(e);
							BindUtils.postNotifyChange(null, null, this, "listThongTinThanNhan");
							BindUtils.postNotifyChange(null, null, this, "listThongTinThanNhanAdd");
						}
					}
				});
		
		
	}
	

	
	public void saveThongTinThanNhan() {
		List<ThongTinThanNhan> listThongTinGoc = new ArrayList<>();
		if (!noId()) {
			listThongTinGoc.addAll(find(ThongTinThanNhan.class)
					.where(QThongTinThanNhan.thongTinThanNhan.trangThai.ne(core().TT_DA_XOA))
					.where(QThongTinThanNhan.thongTinThanNhan.doiTuong.eq(this))
					.fetch());
		}
		for (ThongTinThanNhan au: listThongTinGoc) {			
			if(!listThongTinThanNhan.contains(au)) {
				au.doDelete(true);
			}
		}
		for (ThongTinThanNhan au: listThongTinThanNhan) {
			au.saveNotShowNotification();
		}
	}
		
	@Command
	public void saveDoiTuongMoi(@BindingParam("res") final String res) 
			throws IOException {	
		beforeSaveImg();
		if (newThongTinViPham.getHinhThucXuLy().getLoaiDoiTuong().equals(LoaiDoiTuong.NGUOI_NGHIEN_MA_TUY)) {
			setNguoiNghien(true);
		} else {
			setNguoiSuDungTraiPhep(true);
		}
		save();
		if (newThongTinViPham.getDonViCon() != null) {
			newThongTinViPham.setDonVi(newThongTinViPham.getDonViCon());
		} else {
			newThongTinViPham.setDonVi(newThongTinViPham.getDonViCha());
		}
		newThongTinViPham.setDoiTuong(this);
		newThongTinViPham.saveNotShowNotification();
		setThongTinViPhamHienHanh(newThongTinViPham);
		if (newThongTinViPham.getMaTuySuDung() != null) {
			getMaTuySuDungs().add(newThongTinViPham.getMaTuySuDung());	
		}		
		saveNotShowNotification();
		saveThongTinThanNhan();
		
		LichSuThayDoi lsHK = new LichSuThayDoi();
		lsHK.setDoiTuong(this);;
		lsHK.setNoiDung("Thêm mới đối tượng");
		lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
		lsHK.saveNotShowNotification();
		
		lsHK = new LichSuThayDoi();
		lsHK.setDoiTuong(this);;
		lsHK.setNoiDung("Thêm mới hình thức xử lý: " + newThongTinViPham.getHinhThucXuLy().getTen());
		lsHK.setChiTietThayDoi(getChiTietThayDoi(newThongTinViPham.getListPropertyChangeEvent()));
		lsHK.saveNotShowNotification();
		
		//saveDoiTuongLienQuan();
		Executions.sendRedirect("/" + res + "/xem/" + getId());
	}

	@Command
	public void saveDoiTuong(@BindingParam("res") final String res) 
			throws IOException {
		beforeSaveImg();
		save();
		saveThongTinThanNhan();
		if (listPropertyChangeEvent.size() > 0) {
			LichSuThayDoi lsHK = new LichSuThayDoi();
			lsHK.setDoiTuong(this);
			lsHK.setNoiDung("Chỉnh sửa thông tin đối tượng");
			lsHK.setChiTietThayDoi(getChiTietThayDoi(listPropertyChangeEvent));
			lsHK.saveNotShowNotification();
		}		
		Executions.sendRedirect("/" + res + "/xem/" + getId());
	}
	
	@Command
	public void cancel(@BindingParam("res") final String res) 
			throws IOException {	
		Executions.sendRedirect("/" + res);
	}
	
	@Transient
	public String getDiaChiThuongTruFull() {
		String out = "";
		if (!diaChiThuongTru.isEmpty()) {
			out += diaChiThuongTru + ", ";
		}
		if (getDiaChiThuongTruToDanPho() != null) {
			out += getDiaChiThuongTruToDanPho().getTenVietTat() + ", ";
		}
		if (getDiaChiThuongTruXa() != null) {
			out += getDiaChiThuongTruXa().getTen() + ", ";
		}
		if (getDiaChiThuongTruHuyen() != null) {
			out += getDiaChiThuongTruHuyen().getTen() + ", ";
		}
		if (getDiaChiThuongTruTinh() != null) {
			out += getDiaChiThuongTruTinh().getTen();
		}
		return out;
	}
	@Transient
	public String getDiaChiHienNayFull() {
		String out = "";
		if (!diaChiHienNay.isEmpty()) {
			out += diaChiHienNay + ", ";
		}
		if (getDiaChiHienNayToDanPho() != null) {
			out += getDiaChiHienNayToDanPho().getTenVietTat() + ", ";
		}
		if (getDiaChiHienNayXa() != null) {
			out += getDiaChiHienNayXa().getTen() + ", ";
		}
		if (getDiaChiHienNayHuyen() != null) {
			out += getDiaChiHienNayHuyen().getTen() + ", ";
		}
		if (getDiaChiHienNayTinh() != null) {
			out += getDiaChiHienNayTinh().getTen();
		}
		return out;
	}
	
	@Transient
	public List<ThongTinThanNhan> getListThongTinThanNhanDetail() {
		List<ThongTinThanNhan> list = new ArrayList<ThongTinThanNhan>();
		list = find(ThongTinThanNhan.class)
				.where(QThongTinThanNhan.thongTinThanNhan.doiTuong.eq(this))
				.where(QThongTinThanNhan.thongTinThanNhan.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThongTinThanNhan.thongTinThanNhan.quanHeGiaDinh.ten.asc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<DoiTuongLienQuan> getListDoiTuongLienQuanDetail() {
		List<DoiTuongLienQuan> list = new ArrayList<DoiTuongLienQuan>();
		list = find(DoiTuongLienQuan.class)
				.where(QDoiTuongLienQuan.doiTuongLienQuan.doiTuong.eq(this))
				.where(QDoiTuongLienQuan.doiTuongLienQuan.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QDoiTuongLienQuan.doiTuongLienQuan.quanHeDoiTuongLienQuan.ten.asc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<ThongTinViPham> getListTienAn() {
		List<ThongTinViPham> list = new ArrayList<>();
		list = find(ThongTinViPham.class)
				.where(QThongTinViPham.thongTinViPham.doiTuong.eq(this))
				.where(QThongTinViPham.thongTinViPham.lichSu.eq(true))
				.where(QThongTinViPham.thongTinViPham.hinhThucXuLy.loaiHinhThucXuLy.eq(LoaiHinhThucXuLy.TIEN_AN_TIEN_SU))
				.where(QThongTinViPham.thongTinViPham.loaiXuLy.eq(LoaiXuLy.XU_LY_HINH_SU))
				.where(QThongTinViPham.thongTinViPham.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThongTinViPham.thongTinViPham.ngayViPham.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<ThongTinViPham> getListTienSu() {
		List<ThongTinViPham> list = new ArrayList<>();
		list = find(ThongTinViPham.class)
				.where(QThongTinViPham.thongTinViPham.doiTuong.eq(this))
				.where(QThongTinViPham.thongTinViPham.lichSu.eq(true))
				.where(QThongTinViPham.thongTinViPham.hinhThucXuLy.loaiHinhThucXuLy.eq(LoaiHinhThucXuLy.TIEN_AN_TIEN_SU))
				.where(QThongTinViPham.thongTinViPham.loaiXuLy.eq(LoaiXuLy.XU_LY_HANH_CHINH))
				.where(QThongTinViPham.thongTinViPham.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThongTinViPham.thongTinViPham.ngayViPham.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<ThongTinViPham> getListViPhamHanhChinh() {
		List<ThongTinViPham> list = new ArrayList<>();
		list = find(ThongTinViPham.class)
				.where(QThongTinViPham.thongTinViPham.doiTuong.eq(this))
				.where(QThongTinViPham.thongTinViPham.lichSu.eq(true))
				.where(QThongTinViPham.thongTinViPham.hinhThucXuLy.loaiHinhThucXuLy.eq(LoaiHinhThucXuLy.XU_PHAT_HANH_CHINH))
				.where(QThongTinViPham.thongTinViPham.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThongTinViPham.thongTinViPham.ngayViPham.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<ThongTinViPham> getListLichSuKhac() {
		List<ThongTinViPham> list = new ArrayList<>();
		list = find(ThongTinViPham.class)
				.where(QThongTinViPham.thongTinViPham.doiTuong.eq(this))
				.where(QThongTinViPham.thongTinViPham.lichSu.eq(true))
				.where(QThongTinViPham.thongTinViPham.hinhThucXuLy.loaiHinhThucXuLy.eq(LoaiHinhThucXuLy.LICH_SU_KHAC))
				.where(QThongTinViPham.thongTinViPham.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThongTinViPham.thongTinViPham.ngayViPham.desc())
				.fetch();
		return list;
	}
	
	@Command
	public void deleteDoiTuong(@BindingParam("res") final String res) {
		System.out.println("deleteDoiTuong: ");
		
		if (!checkInUse()) {
			System.out.println("in usse");
			Messagebox.show("Việc lựa chọn xóa hồ sơ sẽ đồng nghĩa với việc mất hoàn toàn " +
					"dữ liệu của người này trong hệ thống." +
					"Bán có chắc là muốn xóa hồ sơ \"" + getHoVaTen() + "\" không?", "BẠN MUỐN XÓA HỒ SƠ " + getHoVaTen() + "?", Messagebox.CANCEL | Messagebox.OK,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								doDelete(true);
								List<ThongTinViPham> list = find(ThongTinViPham.class)
										.where(QThongTinViPham.thongTinViPham.trangThai.eq(core().TT_AP_DUNG))
										.where(QThongTinViPham.thongTinViPham.doiTuong.eq(DoiTuong.this))
										.fetch();
								for (ThongTinViPham thongTin : list) {
									thongTin.doDelete(true);
								}
								showNotification("Xóa thành công!", "", "success");
								Executions.sendRedirect("/" + res);
							}
						}
					});
		}
	}
	
	@Transient
	public List<ThongTinDieuTriMethadone> getListThongTinDieuTriMethadone() {
		List<ThongTinDieuTriMethadone> list = new ArrayList<ThongTinDieuTriMethadone>();
		list = find(ThongTinDieuTriMethadone.class)
				.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.lichSu.eq(false))
				.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.doiTuong.eq(this))
				.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.ngayKhoiLieu.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<ThongTinDieuTriMethadone> getListThongTinDieuTriMethadoneAll() {
		List<ThongTinDieuTriMethadone> list = new ArrayList<ThongTinDieuTriMethadone>();
		list = find(ThongTinDieuTriMethadone.class)
				.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.trangThai.eq(core().TT_AP_DUNG))
				.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.doiTuong.eq(this))
				.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.lichSu.eq(true))
				.orderBy(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.ngayKhoiLieu.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<ThongTinDieuTriTamThan> getListThongTinDieuTriTamThan() {
		List<ThongTinDieuTriTamThan> list = new ArrayList<ThongTinDieuTriTamThan>();
		list = find(ThongTinDieuTriTamThan.class)
				.where(QThongTinDieuTriTamThan.thongTinDieuTriTamThan.lichSu.eq(false))
				.where(QThongTinDieuTriTamThan.thongTinDieuTriTamThan.doiTuong.eq(this))
				.where(QThongTinDieuTriTamThan.thongTinDieuTriTamThan.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThongTinDieuTriTamThan.thongTinDieuTriTamThan.ngayBatDauDieuTri.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<ThongTinDieuTriTamThan> getListThongTinDieuTriTamThanAll() {
		List<ThongTinDieuTriTamThan> list = new ArrayList<ThongTinDieuTriTamThan>();
		list = find(ThongTinDieuTriTamThan.class)
				.where(QThongTinDieuTriTamThan.thongTinDieuTriTamThan.doiTuong.eq(this))
				.where(QThongTinDieuTriTamThan.thongTinDieuTriTamThan.trangThai.eq(core().TT_AP_DUNG))
				.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.lichSu.eq(true))
				.orderBy(QThongTinDieuTriTamThan.thongTinDieuTriTamThan.ngayBatDauDieuTri.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<ChinhSachDangThuHuong> getListThongTinThuHuong() {
		List<ChinhSachDangThuHuong> list = new ArrayList<ChinhSachDangThuHuong>();
		list = find(ChinhSachDangThuHuong.class)
				.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.doiTuong.eq(this))
				.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.trangThai.eq(core().TT_AP_DUNG))
				.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.lichSu.eq(false))
				.fetch();
		return list;
	}
	
	@Transient
	public List<ChinhSachDangThuHuong> getListThongTinThuHuongAll() {
		List<ChinhSachDangThuHuong> list = new ArrayList<ChinhSachDangThuHuong>();
		list = find(ChinhSachDangThuHuong.class)
				.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.doiTuong.eq(this))
				.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.trangThai.eq(core().TT_AP_DUNG))
				.where(QChinhSachDangThuHuong.chinhSachDangThuHuong.lichSu.eq(true))
				.fetch();
		return list;
	}
	
	@Transient
	public List<ThongTinCaiNghien> getListThongTinCaiNghien() {
		List<ThongTinCaiNghien> list = new ArrayList<ThongTinCaiNghien>();
		list = find(ThongTinCaiNghien.class)
				.where(QThongTinCaiNghien.thongTinCaiNghien.lichSu.eq(false))
				.where(QThongTinCaiNghien.thongTinCaiNghien.doiTuong.eq(this))
				.where(QThongTinCaiNghien.thongTinCaiNghien.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThongTinCaiNghien.thongTinCaiNghien.ngayVaoTrungTam.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<ThongTinCaiNghien> getListThongTinCaiNghienAll() {
		List<ThongTinCaiNghien> list = new ArrayList<ThongTinCaiNghien>();
		list = find(ThongTinCaiNghien.class)
				.where(QThongTinCaiNghien.thongTinCaiNghien.doiTuong.eq(this))
				.where(QThongTinDieuTriMethadone.thongTinDieuTriMethadone.lichSu.eq(true))
				.where(QThongTinCaiNghien.thongTinCaiNghien.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QThongTinCaiNghien.thongTinCaiNghien.ngayVaoTrungTam.desc())
				.fetch();
		return list;
	}
	
	@Transient
	public List<LichSuThayDoi> getListLichSuThayDoi() {
		List<LichSuThayDoi> list = new ArrayList<LichSuThayDoi>();
		list = find(LichSuThayDoi.class)
				.where(QLichSuThayDoi.lichSuThayDoi.doiTuong.eq(this))
				.orderBy(QLichSuThayDoi.lichSuThayDoi.ngayTao.desc())
				.fetch();
		return list;
	}
	
	private WordprocessingMLPackage wordMLPackage;
	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	@Transient
	public WordprocessingMLPackage getWordMLPackage() {
		return wordMLPackage;
	}

	public void setWordMLPackage(WordprocessingMLPackage wordMLPackage) {
		this.wordMLPackage = wordMLPackage;
	}
	
	@Transient
	public ByteArrayOutputStream getOut() {
		return out;
	}

	public void setOut(ByteArrayOutputStream out) {
		this.out = out;
	}
	
	
	@Command
	public void xuatLyLichDoiTuong(@BindingParam("doiTuong") DoiTuong doiTuong) throws Exception{
		String filePath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/WEB-INF/word/thaydoi-nhankhau2.doc");
		File nhanTien = new File(filePath);
		out = new ByteArrayOutputStream();
		if(!nhanTien.exists()){
			Clients.showNotification("Không tìm thấy mẫu", "error", null, "top_center", 10000);
		}else{
			exportLyLichNhanKhau(nhanTien, doiTuong);
		}
	}
	
	@SuppressWarnings("deprecation")
	public void exportLyLichNhanKhau(File NhanTien, DoiTuong doituong1) throws Exception {
		HashMap<String, String> mappings = new HashMap<String, String>();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		mappings.put("tenDT", doituong1.getHoVaTen().toUpperCase());
		if (doituong1.getNgaySinh() != null) {
			mappings.put("ngaySinhDT", df.format(doituong1.getNgaySinh()));
		} 
		mappings.put("biDanhDT", doituong1.getTenKhac() != null ? doituong1.getTenKhac() : "");
		mappings.put("gioiTinhDT", doituong1.getGioiTinh().getTen());
		mappings.put("dacDiemDT", doituong1.getDacDiemNhanDang() != null ? doituong1.getDacDiemNhanDang() : "");
		mappings.put("trinhDoHocVanDT", doituong1.getTrinhDoVanHoa() != null ? doituong1.getTrinhDoVanHoa().getTen() : "");
		mappings.put("tinhTrangViecLamDT", doituong1.getTinhTrangViecLam() != null ? doituong1.getTinhTrangViecLam().getText() : "");
		mappings.put("ngheNghiepDT", doituong1.getNgheNghiep() != null ? doituong1.getNgheNghiep().getTen() : "");
		mappings.put("soTheCanCuocDT", doituong1.getSoDinhDanh() != null ? doituong1.getSoDinhDanh() : "");
		mappings.put("soCmndDT", doituong1.getSoCMND() != null ? doituong1.getSoCMND() : "");
		mappings.put("ngayCapCmndDT", doituong1.getNgayCapCMND() != null ? df.format(doituong1.getNgayCapCMND()) : "");
		mappings.put("noiCapCmndDT", doituong1.getNoiCapCMND() != null ? doituong1.getNoiCapCMND().getText() : "");
		mappings.put("noiDkNkttDT", doituong1.getDiaChiThuongTruFull() != null ? doituong1.getDiaChiThuongTruFull() : "");
		mappings.put("noiOHienTaiDT", doituong1.getDiaChiHienNayFull() != null ? doituong1.getDiaChiHienNayFull() : "");
		mappings.put("thanhPhanGiaDinhDT", doituong1.getThanhPhanDoiTuong() != null ? doituong1.getThanhPhanDoiTuong().getTen() : "");
		//		if (nhanKhau1.getAnh() == null) {
//			mappings.put("imageNK", "Ảnh 3x4");
//		}
		String fileName = removeSpecialLetter(doituong1.getHoVaTen().toLowerCase()) + "_" + calendar.get(Calendar.HOUR) + "_" + calendar.get(Calendar.MINUTE)
		+ "_" + calendar.get(Calendar.DAY_OF_MONTH) + "_" + (calendar.get(Calendar.MONTH) + 1) + "_" + calendar.get(Calendar.YEAR);
		setWordMLPackage(WordprocessingMLPackage.load(NhanTien));
		variableReplace(mappings);
		
		if (doituong1.getImageContent() != null) {
			MainDocumentPart mdp = getWordMLPackage().getMainDocumentPart();
			Body b = mdp.getJaxbElement().getBody();
			List<Object> runs = getAllElementFromObject(b,R.class);
			for (Object o : runs) {			 
	            R run = (R)o;                        
	            String str = XmlUtils.marshaltoString(run);
	            //the keyword was found
	            if (str.contains("imageNK")){
	                P p = (P)run.getParent();
	                p.getContent().remove(run);
	                p.getContent().add(createImageRun(getWordMLPackage(),doituong1.getImageContent().getByteData()));
	                break;
	            }     
			}
		
		}
		runLyLich(fileName, doituong1.getListThongTinThanNhanDetail());
	}
	
	public R createImageRun( WordprocessingMLPackage wordMLPackage, byte[] bytes) throws Exception{        
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
        Inline inline = imagePart.createImageInline( null, "img alt", 0, 1, false);
        org.docx4j.wml.ObjectFactory factory = Context.getWmlObjectFactory();
        org.docx4j.wml.R  run = factory.createR();               
        org.docx4j.wml.Drawing drawing = factory.createDrawing(); 
        run.getContent().add(drawing);        
        drawing.getAnchorOrInline().add(inline);        
        return run;        
    }
	
	public void variableReplace(final HashMap<String, String> mappings) throws Exception {
		VariablePrepare.prepare(getWordMLPackage());
		getWordMLPackage().getMainDocumentPart().variableReplace(mappings);
	}
	
	public List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
	    List<Object> result = new ArrayList<Object>();
	    if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();

	    if (obj.getClass().equals(toSearch))
	        result.add(obj);
	    else if (obj instanceof ContentAccessor) {
	        List<?> children = ((ContentAccessor) obj).getContent();
	        for (Object child : children) {
	            result.addAll(getAllElementFromObject(child, toSearch));
	        }
	    }
	    return result;
	}
	
	public final void runLyLich(String fileName, List<ThongTinThanNhan> listThongTinThanNhan) throws Exception {
		writeNoiDungQuanHe(listThongTinThanNhan);
		complete();
		getOut().close();
		Filedownload.save(new ByteArrayInputStream(getOut().toByteArray()), "application/octet-stream",
				fileName + ".doc");
	}
	
	public void complete() throws Docx4JException {
		setWordMLPackage(getWordMLPackage().getMainDocumentPart().convertAltChunks());
		getWordMLPackage().save(getOut());
	}
	
	public void writeNoiDungQuanHe(List<ThongTinThanNhan> listThongTinThanNhan) {
		try{
			List<Map<String, String>> mapCpDt = new ArrayList<Map<String, String>>();
			if (listThongTinThanNhan.size() > 0) {
				int stt = 1;
				for (ThongTinThanNhan quanHe : listThongTinThanNhan) {
					System.out.println("quanHe: " + quanHe.getQuanHeGiaDinh().getTen());
					Map<String, String> m = new HashMap<String, String>();
					m.put("stt", stt + "");
					m.put("quanHeGD", quanHe.getQuanHeGiaDinh().getTen());
					m.put("hoVaTenGD", quanHe.getHoVaTen().toUpperCase());
					m.put("ngaySinhGD", quanHe.getNamSinh() != null ? quanHe.getNamSinh() : "");
					m.put("ngheNghiepGD", quanHe.getNgheNghiep() != null ? quanHe.getNgheNghiep().getTen() : "");
					m.put("diaChiThuongTruGD", quanHe.getDiaChiThuongTru() != null ? quanHe.getDiaChiThuongTru() : "");					
					mapCpDt.add(m);
					stt++;
				}
			} else {
				for (int i=1; i < 3; i++) {
					Map<String, String> m = new HashMap<String, String>();
					m.put("stt", "");
					m.put("quanHeGD", "");
					m.put("hoVaTenGD", "");
					m.put("ngaySinhGD", "");
					m.put("ngheNghiepGD", "");
					m.put("diaChiThuongTruGD", "");	
					mapCpDt.add(m);
				}				
			}			
			WordUtil.replaceTable(new String[]{"stt","quanHeGD", "hoVaTenGD","ngaySinhGD","ngheNghiepGD","diaChiThuongTruGD"}, 
					mapCpDt, this.getWordMLPackage(), 1);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {				
				if (getNewThongTinViPham().getNgayKetThuc() != null && getNewThongTinViPham().getNgayKetThuc().before(getNewThongTinViPham().getNgayViPham())) {
					addInvalidMessage(ctx, "lblErrNgayNgungDieuTri","Thời điểm kết thúc không được lớn hơn thời điểm bắt đầu.");
				} 
				
			}
		};
	}
}
