package vn.toancauxanh.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.google.common.base.Strings;

import vn.toancauxanh.gg.model.CongTyKinhDoanh;
import vn.toancauxanh.gg.model.NhomCuaHoi;
import vn.toancauxanh.gg.model.NhomNguoiDung;
import vn.toancauxanh.gg.model.QCongTyKinhDoanh;
import vn.toancauxanh.gg.model.QNhomCuaHoi;
import vn.toancauxanh.service.Quyen;

@Entity
@Table(name = "nhanvien")
public class NhanVien extends Model<NhanVien> {
	
	public static transient final Logger LOG = LogManager.getLogger(NhanVien.class.getName());
	
/*	public static final String TONGBIENTAP = "tongbientap";
	public static final String CONGTACVIEN = "congtacvien";
	public static final String BIENTAPVIEN = "bientapvien";
	public static final String QUANTRIVIEN = "quantrivien";*/
	
	public static final String QUANTRIVIEN = "admin";
	public static final String NHOMTHANHVIEN = "thanhvien";
	public static final String NHOMTRUONG = "nhomtruong";
	public static final String BANDIEUHANH = "bandieuhanh";
	public static final String KETOAN = "ketoan";
	public static final String PHONGBANVELE = "phongbanvele";
	
	private String chucVu = "";
	private String diaChi = "";
	private String email = "";
	private String hinhDaiDien = "";
	private String hoVaTen = "";
	private String matKhau = "";
	private String salkey = "";
	private String soDienThoai = "";
	private String tenDangNhap = "";
	private Date ngaySinh;
	private Set<String> quyens = new HashSet<>();
	private Set<String> tatCaQuyens = new HashSet<>();
	private Set<VaiTro> vaiTros = new HashSet<>();
	private String matKhau2 = "";
	private NhomNguoiDung nhomNguoiDung;
	private int active = 0;
	private NhomCuaHoi nhomCuaHoi;
	private CongTyKinhDoanh congTyKinhDoanh;
	
	private Quyen quyen = new Quyen(new SimpleAccountRealm() {
		@Override
		protected AuthorizationInfo getAuthorizationInfo(final PrincipalCollection arg0) {
			final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.setStringPermissions(getTatCaQuyens());
			return info;
		}
	});

	@Override
	public String toString() {
		return super.toString() + " " + tenDangNhap + " " + getVaiTros() + " " + getTatCaQuyens();
	}
		
	public String getSalkey() {
		return salkey;
	}


	public void setSalkey(String salkey) {
		this.salkey = salkey;
	}
	
	@Transient
	public String getMatKhau2() {
		return matKhau2;
	}

	public void setMatKhau2(String matKhau2) {
		this.matKhau2 = matKhau2;
	}
	
	@ManyToOne
	public NhomNguoiDung getNhomNguoiDung() {
		return nhomNguoiDung;
	}

	public void setNhomNguoiDung(NhomNguoiDung nhomNguoiDung) {
		this.nhomNguoiDung = nhomNguoiDung;
	}
	
	@ManyToOne
	public NhomCuaHoi getNhomCuaHoi() {
		return nhomCuaHoi;
	}

	public void setNhomCuaHoi(NhomCuaHoi nhomCuaHoi) {
		this.nhomCuaHoi = nhomCuaHoi;
	}

	@ManyToOne
	public CongTyKinhDoanh getCongTyKinhDoanh() {
		return congTyKinhDoanh;
	}

	public void setCongTyKinhDoanh(CongTyKinhDoanh congTyKinhDoanh) {
		this.congTyKinhDoanh = congTyKinhDoanh;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	//@Fetch(FetchMode.SUBSELECT)
	@CollectionTable(name = "nhanvien_quyens", joinColumns = {@JoinColumn(name = "nhanVien_id")})
	public Set<String> getQuyens() {
		return quyens;
	}

	@Transient
	public Set<String> getTatCaQuyens() {
		if (tatCaQuyens.isEmpty()) {
			tatCaQuyens.addAll(quyens);
			for (VaiTro vaiTro : vaiTros) {
				if (!vaiTro.getAlias().isEmpty()) {
					tatCaQuyens.add(vaiTro.getAlias());
				}
				tatCaQuyens.addAll(vaiTro.getQuyens());
			}
			if (Labels.getLabel("email.superuser").equals(tenDangNhap)) {
				tatCaQuyens.add("*");
			}
		}
		return tatCaQuyens;
	}

	public void setQuyens(final Set<String> dsChoPhep) {
		quyens = dsChoPhep;
	}


	@Transient
	public String getVaiTroText() {
		String result = "";
		for (VaiTro vt : getVaiTros()) {
			result += (result.isEmpty() ? "" : ", ") + vt.getTenVaiTro();
		}
		return result;
	}

	public NhanVien() {
		super();
	}

	public NhanVien(final String tenDangNhap_, final String _hoTen) {
		super();
		tenDangNhap = tenDangNhap_;
		hoVaTen = _hoTen;
	}

	@Override
	public void doSave() {
		super.doSave();
	}

	public String getChucVu() {
		return chucVu;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public String getEmail() {
		return email;
	}

	public String getHinhDaiDien() {
		return hinhDaiDien;
	}

	public String getHoVaTen() {
		return hoVaTen;
	}
	

	public String getMatKhau() {
		return matKhau;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "nhanvien_vaitro", joinColumns = {
			@JoinColumn(name = "nhanvien_id") }, inverseJoinColumns = { @JoinColumn(name = "vaitros_id") })
	//@Fetch(value = FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	public Set<VaiTro> getVaiTros() {
		return vaiTros;
	}
	
	private Set<VaiTro> vaiTrosCopy = new HashSet<VaiTro>();
	
	@Transient
	public Set<VaiTro> getVaiTrosCopy() {
		return vaiTrosCopy;
	}

	public void setVaiTrosCopy() {
		System.out.println("setVaiTrosCopy " +getVaiTros().size());
		vaiTrosCopy.clear();
		getVaiTros().forEach(v -> {
//			VaiTro vaiTro = new VaiTro();
//			vaiTro.setId(v.getId());
//			vaiTro.setTenVaiTro(v.getTenVaiTro());
//			vaiTro.setAlias(v.getAlias());
			
			VaiTro vaiTro = find(VaiTro.class).where(QVaiTro.vaiTro.id.eq(v.getId())).fetchFirst();
			vaiTrosCopy.add(vaiTro);
		});
		BindUtils.postNotifyChange(null, null, this, "vaiTrosCopy");
	}
	
	// @Transient
	// public boolean isSuperUser() {
	// return realm.isPermitted(null, "*");
	// }

	
	public void setChucVu(final String _chuVu) {
		chucVu = Strings.nullToEmpty(_chuVu);
	}

	public void setDiaChi(final String _diaChi) {
		diaChi = Strings.nullToEmpty(_diaChi);
	}

	public void setEmail(final String _email) {
		email = Strings.nullToEmpty(_email);
	}

	public void setHinhDaiDien(final String _hinhDaiDien) {
		hinhDaiDien = Strings.nullToEmpty(_hinhDaiDien);
	}

	public void setHoVaTen(final String _hoVaTen) {
		hoVaTen = Strings.nullToEmpty(_hoVaTen);
	}

	public void setMatKhau(final String _matKhau) {
		matKhau = Strings.nullToEmpty(_matKhau);
	}

	public void setNgaySinh(final Date _ngaySinh) {
		ngaySinh = _ngaySinh;
	}

	public void setSoDienThoai(final String _soDienThoai) {
		soDienThoai = Strings.nullToEmpty(_soDienThoai);
	}

	public void setTenDangNhap(final String _tenDangNhap) {
		tenDangNhap = Strings.nullToEmpty(_tenDangNhap);
	}

	public void setVaiTros(final Set<VaiTro> vaiTros1) {
		vaiTros = vaiTros1;
	}

	@Transient
	public Quyen getTatCaQuyen() {
		return quyen;
	}
	
	@Transient
	public boolean isQuanTriVien() {
		boolean rs = false;
		for (VaiTro vt : getNhanVien().getVaiTros()) {
			if (StringUtils.equals(QUANTRIVIEN, vt.getAlias())) {
				rs = true;
				break;
			}
		}
		return rs;
	}
	
	@Transient
	public boolean isNhomThanhVien() {
		boolean rs = false;
		for (VaiTro vt : getNhanVien().getVaiTros()) {
			if (StringUtils.equals(NHOMTHANHVIEN, vt.getAlias())) {
				rs = true;
				break;
			}
		}
		return rs;
	}
	
	@Transient
	public boolean isNhomTruong() {
		boolean rs = false;
		for (VaiTro vt : getNhanVien().getVaiTros()) {
			if (StringUtils.equals(NHOMTRUONG, vt.getAlias())) {
				rs = true;
				break;
			}
		}
		return rs;
	}
	
	@Transient
	public boolean isBanDieuHanh() {
		boolean rs = false;
		for (VaiTro vt : getNhanVien().getVaiTros()) {
			if (StringUtils.equals(BANDIEUHANH, vt.getAlias())) {
				rs = true;
				break;
			}
		}
		return rs;
	}
	
	@Transient
	public boolean isKeToan() {
		boolean rs = false;
		for (VaiTro vt : getNhanVien().getVaiTros()) {
			if (StringUtils.equals(KETOAN, vt.getAlias())) {
				rs = true;
				break;
			}
		}
		return rs;
	}
	
	@Transient
	public boolean isPhongBanVeLe() {
		boolean rs = false;
		for (VaiTro vt : getNhanVien().getVaiTros()) {
			if (StringUtils.equals(PHONGBANVELE, vt.getAlias())) {
				rs = true;
				break;
			}
		}
		return rs;
	}
	
	/*@Transient
	public boolean isCongTacVien() {
		return core().getQuyen().get(CONGTACVIEN);
	}

	@Transient
	public boolean isBienTapVien() {
		return core().getQuyen().get(BIENTAPVIEN);
	}

	@Transient
	public boolean isTongBienTap() {
		return core().getQuyen().get(TONGBIENTAP); // entry.quyen.tongbientap
	}

	@Transient
	public boolean isQuanTriVien() {
		return core().getQuyen().get(QUANTRIVIEN); // entry.quyen.tongbientap
	}
*/
	/*@Transient
	public boolean ischoPhepLuu() {
		if (isCongTacVien() || isBienTapVien() || isTongBienTap()) {
			return true;
		}
		return false;
	}

	@Transient
	public boolean ischoPhepXuatBan() {
		if (isTongBienTap()) {
			return true;
		}
		return false;
	}*/

	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				if (getVaiTros().size() == 0) {
					addInvalidMessage(ctx, "lblErrVaiTros", "Bạn phải chọn vai trò cho người dùng");
				}
			}
		};
	}
	
	@Transient
	public boolean getCheckTenDangNhap() {
		boolean status = false;
		BindUtils.postNotifyChange(null, null, this, "tenDangNhap");
		String str = getTenDangNhap();
		System.out.println("str : " +str);
		if(!str.isEmpty()) {
			System.out.println("AAAAA");
			if(str.contains(" ")) {
				status = true;
			}
		}
		return status;
	}
	
	@Command
	public void kiemTraTenDangNhap() {
		System.out.println("KiemTraTenDangNhap");
		BindUtils.postNotifyChange(null, null, this, "checkTenDangNhap");
	}
	

	@Command
	public void selectVaiTros() {
		System.out.println("selectVaiTros");
		BindUtils.postNotifyChange(null, null, this, "vaiTros");
		if(!getVaiTros().isEmpty()) {
			String _classname = "redTextColor";
	        String s = "ox_removeClass(" + "'" + _classname + "')";
	        System.out.println("s : " +s);
	        Clients.evalJavaScript(s);
		}
		BindUtils.postNotifyChange(null, null, this, "selectedQuanTriVien");
	}
	
	@Transient
	public AbstractValidator getValidator(boolean isBackend) {
		System.out.println("validate");
		return new AbstractValidator() {
			@Override
			public void validate(final  ValidationContext ctx) {
				if (isBackend && getVaiTros() != null && getVaiTros().size() == 0) {
					addInvalidMessage(ctx, "lblErrVaiTros", "Bạn phải chọn vai trò cho người dùng.");
				}
//				if(!getSelectedQuanTriVien()) {
//					if(getNhomNguoiDung() == null) {
//						addInvalidMessage(ctx, "lblErrNhomNguoiDung", "Nhóm người dùng không được để trống.");
//					}
//				}
			}
		};
	}

	@Command
	public void khoaThanhVien(@BindingParam("vm") final Object vm) {
		if ("admin".equals(getTenDangNhap())) {
			showNotification("Không thể khóa SuperUser", "", "warning");
		} else {
			Messagebox.show("Bạn muốn khóa nhân viên này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								setCheckApDung(false);
								save();
								BindUtils.postNotifyChange(null, null, vm, "targetQueryNhanVien");
							}
						}
					});

		}
	}

	@Command
	public void moKhoaThanhVien(@BindingParam("vm") final Object vm) {
		Messagebox.show("Bạn muốn mở khóa nhân viên này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK,
				Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							setCheckApDung(true);
							save();
							BindUtils.postNotifyChange(null, null, vm, "targetQueryNhanVien");
						}
					}
				});
	}

	private boolean checkKichHoat;

	public boolean isCheckKichHoat() {
		return checkKichHoat;
	}

	public void setCheckKichHoat(boolean checkKichHoat) {
		this.checkKichHoat = checkKichHoat;
	}

	@Command
	public void toggleLock(@BindingParam("list") final Object obj) {
		String dialogText = "";
		if (!checkKichHoat) {
			dialogText = "Bạn muốn ngưng kích hoạt người dùng đã chọn?";
		} else {
			dialogText = "Bạn muốn kích hoạt người dùng đã chọn?";
		}
		Messagebox.show(dialogText, "Xác nhận", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION,
				new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							if (checkKichHoat) {
								setCheckKichHoat(false);
							} else {
								setCheckKichHoat(true);
							}
							save();
							BindUtils.postNotifyChange(null, null, obj, "targetQueryNhanVien");
						}
					}
				});
	}

	@Command
	public void deleteNhanVienInListVaiTro(@BindingParam("vaitro") final VaiTro vt,
		@BindingParam("nhanvien") final NhanVien nv) {
	Messagebox.show("Bạn có chắc chắn muốn xóa vai trò " + vt.getTenVaiTro() + " của nhân viên " + nv.getHoVaTen(),
			"Xác nhận", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new EventListener<Event>() {
				@Override
				public void onEvent(final Event event) {
					if (Messagebox.ON_OK.equals(event.getName())) {
						vaiTros.remove(vt);
						save();
						BindUtils.postNotifyChange(null, null, vt, "listNhanVien");
					}
				}
			});
	}
	
	@Command
	public void saveNhanVien(@BindingParam("list") final Object listObject,
			@BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		if (matKhau2 != null && !matKhau2.isEmpty()) {
			updatePassword(matKhau2);
		}
		if (getVaiTros().size() > 0 && ("admin".equals(getVaiTros().iterator().next().getAlias()) || "phongbanvele".equals(getVaiTros().iterator().next().getAlias())
				|| "bandieuhanh".equals(getVaiTros().iterator().next().getAlias()))) {
			setNhomCuaHoi(null);
			setCongTyKinhDoanh(null);
			setNhomNguoiDung(null);
		}
		setActive(0);
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
	
	
	public String getCookieToken(long expire) {
		String token = getId() + ":" + expire + ":";
		return Base64.encodeBase64String(token.concat(DigestUtils.md5Hex(token + matKhau + ":" + salkey)).getBytes());
	}
	
	public void updatePassword(String pass) {
		BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
		String salkey = getSalkey();
		if (salkey == null || salkey.equals("")) {
			salkey = encryptor.encryptPassword((new Date()).toString());
		}
		String passNoHash = pass + salkey;
		String passHash = encryptor.encryptPassword(passNoHash);
		setSalkey(salkey);
		setMatKhau(passHash);
	}
	
	@Command
	public void closeNhanVien(@BindingParam("notify") Object vmArgs, @BindingParam("attr") String attr,
			@BindingParam("detach") final Window wdn) {
		vaiTros.clear();
		vaiTros.addAll(vaiTrosCopy);
		BindUtils.postNotifyChange(null, null, vmArgs, attr);
		if (wdn != null) {
			wdn.detach();
		}
	}
	
	@Transient
	public List<CongTyKinhDoanh> getCongTyKinhDoanhListAndNull() {
		List<CongTyKinhDoanh> congTyKinhDoanhList = new ArrayList<CongTyKinhDoanh>();
		congTyKinhDoanhList.add(null);
		if(nhomCuaHoi != null) {
			congTyKinhDoanhList = find(CongTyKinhDoanh.class)
					.where(QCongTyKinhDoanh.congTyKinhDoanh.trangThai.eq(core().TT_AP_DUNG))
					.where(QCongTyKinhDoanh.congTyKinhDoanh.nhomCuaHoi.id.eq(nhomCuaHoi.getId()))
					.orderBy(QNhomCuaHoi.nhomCuaHoi.ten.asc()).fetch();
		}
		
		return congTyKinhDoanhList;
	}
	
	@Command
	public void nhomHoiSelected() {
		BindUtils.postNotifyChange(null, null, this, "nhomCuaHoi");
		BindUtils.postNotifyChange(null, null, this, "congTyKinhDoanhListAndNull");
	}
	
	
	@Transient
	public boolean getSelectedQuanTriVien() {
		boolean result = false;
		for (VaiTro vt : getVaiTros()) {
			if (StringUtils.equals(QUANTRIVIEN, vt.getAlias())) {
				setNhomNguoiDung(null);
				result = true;
				break;
			}
		}
		return result;
	}
}
