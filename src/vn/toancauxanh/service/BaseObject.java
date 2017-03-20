package vn.toancauxanh.service;

import java.text.Normalizer;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.persistence.Transient;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.util.SystemPropertyUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Default;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.querydsl.jpa.impl.JPAQuery;

import vn.greenglobal.core.CoreObject;
import vn.toancauxanh.cms.service.HomeService;
import vn.toancauxanh.gg.model.DoiTuong;
import vn.toancauxanh.gg.model.DonViHanhChinh;
import vn.toancauxanh.gg.model.QThongTinViPham;
import vn.toancauxanh.gg.model.ThongTinCaiNghien;
import vn.toancauxanh.gg.model.ThongTinDieuTriMethadone;
import vn.toancauxanh.gg.model.ThongTinDieuTriTamThan;
import vn.toancauxanh.gg.model.ThongTinViPham;
import vn.toancauxanh.gg.model.enums.DoiTuongNghien;
import vn.toancauxanh.gg.model.enums.LoaiDoiTuong;
import vn.toancauxanh.gg.model.enums.LoaiHanhViViPham;
import vn.toancauxanh.gg.model.enums.LoaiHinhThucXuLy;
import vn.toancauxanh.gg.model.enums.LoaiXuLy;
import vn.toancauxanh.gg.model.enums.NoiCapCMND;
import vn.toancauxanh.gg.model.enums.PhanLoaiKhachDiTour;
import vn.toancauxanh.gg.model.enums.TinhTrangViecLam;
import vn.toancauxanh.model.NhanVien;
import vn.toancauxanh.model.QNhanVien;
import vn.toancauxanh.model.QVaiTro;
import vn.toancauxanh.model.Setting;
import vn.toancauxanh.model.VaiTro;

public class BaseObject<T> extends CoreObject<T> {
	
	@Override
	public Map<Object, Object> getArg() {
		Map<Object, Object> arg = super.getArg();
		return arg;
	}

	public void setActivePage(int value) {
		getArg().put(SystemPropertyUtils.resolvePlaceholders(PH_KEYPAGE), value + 1);
	}

	public <A> JPAQuery<A> page1(JPAQuery<A> q) {
		String kPage = SystemPropertyUtils.resolvePlaceholders(PH_KEYPAGE);
		int len = MapUtils.getIntValue(getArg(), SystemPropertyUtils.resolvePlaceholders(PH_KEYPAGESIZE));
		int page = Math.max(0, MapUtils.getIntValue(getArg(), kPage) - 1);
		if (q.fetchCount() <= page * len) {
			getArg().put(kPage, page = 0);
			BindUtils.postNotifyChange(null, null, getArg(), kPage);
		}
		return q.offset(page * len).limit(len);
	}

	public <A> JPAQuery<A> pageVideo(JPAQuery<A> q) {
		int len = 9;
		String kPage = SystemPropertyUtils.resolvePlaceholders(PH_KEYPAGE);
		int page = Math.max(0, MapUtils.getIntValue(getArg(), kPage) - 1);
		if (q.fetchCount() <= page * len) {
			getArg().put(kPage, page = 0);
			BindUtils.postNotifyChange(null, null, getArg(), kPage);
		}
		return q.offset(page * len).limit(len);
	}

	@Command
	public final void cmd(@BindingParam("ten") @Default(value = "") final String ten,
			@BindingParam("notify") Object beanObject, @BindingParam("attr") @Default(value = "*") String fields) {
		invoke(null, ten, null, beanObject, fields, null, false);
	}

	@Transient
	public Entry core() {
		return Entry.instance;
	}

	public Date date(Object key) {
		Object result = argDeco().get(key);
		if (!(result instanceof Date) && result != null) {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(CoreObject.DATE_FORMAT);
			result = simpleDateFormat.parse(result.toString(), new ParsePosition(0));
		}
		if (result != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime((Date) result);
			cal.add(Calendar.HOUR, 7);
			result = cal.getTime();
		}
		return (Date) result;
	}

	@Transient
	public final HomeService getHomeService() {
		return new HomeService();
	}

	public NhanVien getNhanVien() {
		return fetchNhanVien(false);
	}

	public NhanVien fetchNhanVienSaving() {
		return fetchNhanVien(true);
	}

	public NhanVien fetchNhanVien(boolean saving) {
		if (Executions.getCurrent() == null) {
			return null;
		}
		return getNhanVien(saving, (HttpServletRequest) Executions.getCurrent().getNativeRequest(),
				(HttpServletResponse) Executions.getCurrent().getNativeResponse());
	}

	public NhanVien getNhanVien(boolean isSave, HttpServletRequest req, HttpServletResponse res) {
		NhanVien nhanVien = null;

		String key = getClass() + "." + NhanVien.class;
		nhanVien = (NhanVien) req.getAttribute(key);
		if (nhanVien == null || nhanVien.noId()) {
			Object token = null;
			Cookie[] cookies = req.getCookies();
			if (cookies != null) {
				for (Cookie c : cookies) {
					if ("email".equals(c.getName())) {
						token = c.getValue();
						break;
					}
				}
			}
			if (token == null) {
				HttpSession ses = req.getSession();
				token = ses.getAttribute("email");
			}
			if (token != null) {
				String[] parts = new String(Base64.decodeBase64(token.toString())).split(":");
				NhanVien nhanVienLogin = em().find(NhanVien.class, NumberUtils.toLong(parts[0], 0));
				if (parts.length == 3 && nhanVienLogin != null) {
					long expire = NumberUtils.toLong(parts[1], 0);
					if (expire > System.currentTimeMillis() && token.equals(nhanVienLogin.getCookieToken(expire))) {
						nhanVien = nhanVienLogin;
					}
				}
			}
			if (!isSave && (nhanVien == null)) {
				if (nhanVien == null) {
					bootstrapNhanVien();
				}
				nhanVien = new NhanVien();
				if (token != null) {
					req.getSession().removeAttribute("email");
				}
				StringBuilder url = new StringBuilder();
				url.append(req.getScheme()) // http (https)
						.append("://") // ://
						.append(req.getServerName()); // localhost (domain name)
				if (req.getServerPort() != 80 && req.getServerPort() != 443) {
					url.append(":").append(req.getServerPort()); // app name
				}
				try {
					res.sendRedirect(url + req.getContextPath() + "/login");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			req.setAttribute(key, nhanVien);
		}

		return isSave && nhanVien != null && nhanVien.noId() ? null : nhanVien;
	}
	
	public void bootstrapNhanVien() {
		JPAQuery<NhanVien> q = find(NhanVien.class)
				.where(QNhanVien.nhanVien.daXoa.isFalse())
				.where(QNhanVien.nhanVien.trangThai.eq(core().TT_AP_DUNG));
		final NhanVien nhanVien = new NhanVien("admin", "Super Admin");
		if (q.fetchCount() == 0) {
			nhanVien.getQuyens().add("*");
			nhanVien.updatePassword("1");
			nhanVien.saveNotShowNotification();
		
			core().getVaiTros().bootstrap();
			JPAQuery<VaiTro> vaiTroQuery = find(VaiTro.class)
					.where(QVaiTro.vaiTro.trangThai.eq(core().TT_AP_DUNG));
			if (vaiTroQuery.fetchCount() > 0) {
				VaiTro vaiTro = find(VaiTro.class).where(QVaiTro.vaiTro.alias.eq("admin")).fetchFirst();
				Set<VaiTro> vaiTros = new HashSet<VaiTro>();
				vaiTros.add(vaiTro);
				nhanVien.setVaiTros(vaiTros);
				nhanVien.saveNotShowNotification();
			}
		}
		
	}

	@Transient
	public NhanVienService getNhanViens() {
		return new NhanVienService();
	}

	@Transient
	public Setting getSetting() {
		String key = BaseObject.class + "." + Setting.class;
		Setting result = (Setting) Executions.getCurrent().getAttribute(key);
		if (result == null || result.noId()) {
			result = find(Setting.class).fetchFirst();
			if (result == null) {
				result = new Setting();
				result.save();
			}
			Executions.getCurrent().setAttribute(key, result);
		}
		return result;
	}


	@Transient
	public final Map<String, String> getTrangThaiList() {
		HashMap<String, String> result = new HashMap<>();
		result.put("khong_ap_dung", "Không áp dụng");
		result.put("ap_dung", "Áp dụng");
		return result;
	}

	@Transient
	public final Map<String, String> getTrangThaiListAndNull() {
		HashMap<String, String> result = new HashMap<>();
		result.put(null, "");
		result.put("khong_ap_dung", "Không áp dụng");
		result.put("ap_dung", "Áp dụng");
		return result;
	}
	
	public List<LoaiHinhThucXuLy> getListLoaiHinhThucXuLys() {
		List<LoaiHinhThucXuLy> pos = new ArrayList<>(Arrays.asList(LoaiHinhThucXuLy.values()));
		return pos;
	}
	
	public List<LoaiHinhThucXuLy> getListLoaiHinhThucXuLysAndNull() {
		List<LoaiHinhThucXuLy> list = new ArrayList<LoaiHinhThucXuLy>();
		list.add(null);
		list.addAll(getListLoaiHinhThucXuLys());
		return list;
	}
	
	public List<TinhTrangViecLam> getListTinhTrangViecLam() {
		List<TinhTrangViecLam> list = new ArrayList<>(Arrays.asList(TinhTrangViecLam.values()));
		return list;
	}
	
	public List<DoiTuongNghien> getListDoiTuongNghien() {
		List<DoiTuongNghien> list = new ArrayList<>(Arrays.asList(DoiTuongNghien.values()));
		return list;
	}
	
	public List<NoiCapCMND> getListNoiCapCMND() {
		List<NoiCapCMND> list = new ArrayList<>(Arrays.asList(NoiCapCMND.values()));
		return list;
	}
	
	public List<LoaiHanhViViPham> getListLoaiHanhViViPham() {
		List<LoaiHanhViViPham> list = new ArrayList<>(Arrays.asList(LoaiHanhViViPham.values()));
		return list;
	}
	
	public List<PhanLoaiKhachDiTour> getListPhanLoaiKhachDiTour() {
		List<PhanLoaiKhachDiTour> list = new ArrayList<>(Arrays.asList(PhanLoaiKhachDiTour.values()));
		return list;
	}
	
	public List<PhanLoaiKhachDiTour> getListPhanLoaiKhachDiTourAndNull() {
		List<PhanLoaiKhachDiTour> list = new ArrayList<PhanLoaiKhachDiTour>();
		list.add(null);
		list.addAll(getListPhanLoaiKhachDiTour());
		return list;
	}
	
	public List<LoaiXuLy> getListLoaiXuLy() {
		List<LoaiXuLy> list = new ArrayList<>(Arrays.asList(LoaiXuLy.values()));
		return list;
	}
	
	public List<LoaiXuLy> getListLoaiXuLyAndNull() {
		List<LoaiXuLy> list = new ArrayList<LoaiXuLy>();
		list.add(null);
		list.addAll(getListLoaiXuLy());
		return list;
	}
	
	public List<LoaiHanhViViPham> getListLoaiHanhViViPhamAndNull() {
		List<LoaiHanhViViPham> list = new ArrayList<LoaiHanhViViPham>();
		list.add(null);
		list.addAll(getListLoaiHanhViViPham());
		return list;
	}
	
	public List<LoaiDoiTuong> getListLoaiDoiTuongs() {
		List<LoaiDoiTuong> pos = new ArrayList<>(Arrays.asList(LoaiDoiTuong.values()));
		return pos;
	}
	
	public List<LoaiDoiTuong> getListLoaiDoiTuongsAndNull() {
		List<LoaiDoiTuong> list = new ArrayList<LoaiDoiTuong>();
		list.add(null);
		list.addAll(getListLoaiDoiTuongs());
		return list;
	}
	
	private DonViHanhChinh selectedQuanHuyen;
	
	@Transient
	public DonViHanhChinh getSelectedQuanHuyen() {
		return selectedQuanHuyen;
	}

	public void setSelectedQuanHuyen(DonViHanhChinh selectedQuanHuyen) {
		this.selectedQuanHuyen = selectedQuanHuyen;
		BindUtils.postNotifyChange(null, null, this, "listPhuongXa");
		BindUtils.postNotifyChange(null, null, this, "listPhuongXaAndNull");
	}
	
	 
	@Transient
	public boolean isNhanVienDaKhoa() {
		return !getNhanVien().isCheckApDung();
	}
	

	@Transient
	public boolean isNhanVienDaKichHoat() {
		return !getNhanVien().isCheckKichHoat();
	}

	@Command
	public void redirectPage(@BindingParam("zul") String zul, @BindingParam("vmArgs") Object vmArgs,
			@BindingParam("vm") Object vm) {
		Map<String, Object> args = new HashMap<>();
		args.put("vmArgs", vmArgs);
		args.put("vm", vm);
		Executions.createComponents(zul, null, args);
	}
	
	@Command
	public void redirectPageHanhVi(@BindingParam("zul") String zul, @BindingParam("vmArgs") Object vmArgs,
			@BindingParam("vm") Object vm, @BindingParam("doiTuong") DoiTuong doiTuong, @BindingParam("isNew") boolean isNew) {
		Map<String, Object> args = new HashMap<>();
		args.put("vmArgs", vmArgs);
		args.put("vm", vm);
		args.put("doiTuong", doiTuong);
		args.put("isNew", isNew);
		Executions.createComponents(zul, null, args);
	}
	
	@Command
	public void deleteHanhVi(@BindingParam("vmArgs") Object vmArgs, 
			@BindingParam("doiTuong") DoiTuong doiTuong, @BindingParam("thongTin") ThongTinViPham thongTin) {
		System.out.println("deleteDoiTuong: ");
		
		if (!checkInUse()) {
			System.out.println("in usse");
			Messagebox.show("Bạn có chắc chắn muốn xóa hình thức xử lý, quản lý đang áp dụng này không?", "XÁC NHẬN", Messagebox.CANCEL | Messagebox.OK,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								thongTin.setLichSu(true);
								thongTin.saveNotShowNotification();
								ThongTinViPham hienHanh = find(ThongTinViPham.class)
										.where(QThongTinViPham.thongTinViPham.doiTuong.eq(doiTuong))
										.where(QThongTinViPham.thongTinViPham.lichSu.eq(false))
										.where(QThongTinViPham.thongTinViPham.ngayKetThuc.isNull()
												.or(QThongTinViPham.thongTinViPham.ngayKetThuc.after(new Date())))
										.orderBy(QThongTinViPham.thongTinViPham.ngayViPham.desc())
										.fetchFirst();
								doiTuong.setThongTinViPhamHienHanh(hienHanh);
								doiTuong.saveNotShowNotification();
								showNotification("Xóa thành công!", "", "success");
								BindUtils.postNotifyChange(null, null, doiTuong, "listThongTinViPham");
							}
						}
					});
		}

	}
	
	@Command
	public void deleteThongTinDieuTri(@BindingParam("vmArgs") Object vmArgs, @BindingParam("attr") String attr,
			@BindingParam("doiTuong") DoiTuong doiTuong, @BindingParam("thongTin") Object thongTin) {
		System.out.println("deleteDoiTuong: ");
		
		if (!checkInUse()) {
			System.out.println("in usse");
			Messagebox.show("Bạn có chắc chắn muốn xóa thông tin này không?", "XÁC NHẬN", Messagebox.CANCEL | Messagebox.OK,
					Messagebox.QUESTION, new EventListener<Event>() {
						@Override
						public void onEvent(final Event event) {
							if (Messagebox.ON_OK.equals(event.getName())) {
								if (thongTin instanceof ThongTinDieuTriMethadone) {
									((ThongTinDieuTriMethadone)thongTin).setLichSu(true);
									((ThongTinDieuTriMethadone)thongTin).saveNotShowNotification();
								}
								if (thongTin instanceof ThongTinDieuTriTamThan) {
									((ThongTinDieuTriTamThan)thongTin).setLichSu(true);
									((ThongTinDieuTriTamThan)thongTin).saveNotShowNotification();
								}
								if (thongTin instanceof ThongTinCaiNghien) {
									((ThongTinCaiNghien)thongTin).setLichSu(true);
									((ThongTinCaiNghien)thongTin).saveNotShowNotification();
								}
								showNotification("Xóa thành công!", "", "success");
								BindUtils.postNotifyChange(null, null, doiTuong, attr);
							}
						}
					});
		}

	}

	@SuppressWarnings("deprecation")
	protected CellStyle setBorderAndFont(final Workbook workbook, final int borderSize, final boolean isTitle,
			final int fontSize, final String fontColor, final String textAlign, final boolean boil) {
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderTop((short) borderSize); // single line border
		cellStyle.setBorderBottom((short) borderSize); // single line border
		cellStyle.setBorderLeft((short) borderSize); // single line border
		cellStyle.setBorderRight((short) borderSize); // single line border

		if (textAlign.equals("RIGHT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		} else if (textAlign.equals("CENTER")) {
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		} else if (textAlign.equals("LEFT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		} else {
			// do nothing
		}

		if (boil) {
			final Font font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			if (isTitle) {
				if (fontColor.equals("RED")) {
					font.setColor(Font.COLOR_RED);
				} else if (fontColor.equals("BLUE")) {
					font.setColor((short) 4);
				} else {
					// do no thing
				}
			}
			font.setFontHeightInPoints((short) fontSize);
			cellStyle.setFont(font);
		}
		return cellStyle;
	}

	public String unAccent(String s) {
		String temp = Normalizer.normalize(s.toLowerCase(), Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		return pattern.matcher(temp).replaceAll("").replaceAll("đ", "d").replaceAll(" ", "")
				.replaceAll("[^a-zA-Z0-9 -]", "");
	}

	public void showNotification(String content, String title, String type) {
		switch (type) {
			case "success":
				Clients.evalJavaScript("toastrSuccess('" + content + "', '" + title + "');");
				break;
			case "info":
				Clients.evalJavaScript("toastrInfo('" + content + "', '" + title + "');");
				break;
			case "warning":
				Clients.evalJavaScript("toastrWarning('" + content + "', '" + title + "');");
				break;
			case "error":
				Clients.evalJavaScript("toastrError('" + content + "', '" + title + "');");
				break;
			default:
				break;
		}
	}
	
	@Command
	public void invokeGG(@BindingParam("notify") Object vmArgs, @BindingParam("attr") String attrs,
			@BindingParam("detach") final Window wdn) {
		for (final String field : attrs.split("\\|")) {
			if (!field.isEmpty()) {
				BindUtils.postNotifyChange(null, null, vmArgs, field);
			}
		}
		if (wdn != null) {
			wdn.detach();
		}
	}
}