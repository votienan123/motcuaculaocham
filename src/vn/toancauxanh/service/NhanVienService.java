package vn.toancauxanh.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoProperties.Storage;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.ToServerCommand;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Desktop;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import com.querydsl.jpa.impl.JPAQuery;

import vn.toancauxanh.gg.model.NhomCuaHoi;
import vn.toancauxanh.gg.model.NhomNguoiDung;
import vn.toancauxanh.gg.model.QNhomCuaHoi;
import vn.toancauxanh.gg.model.QNhomNguoiDung;
import vn.toancauxanh.model.NhanVien;
import vn.toancauxanh.model.QNhanVien;

public final class NhanVienService extends BasicService<NhanVien>{

	public NhanVien getNhanVien(boolean saving) {
		if (Executions.getCurrent() == null) {
			return null;
		}
		return getNhanVien(saving, (HttpServletRequest) Executions.getCurrent().getNativeRequest(),
				(HttpServletResponse) Executions.getCurrent().getNativeResponse());
	}

	public JPAQuery<NhanVien> getTargetQueryNhanVien() {
		String paramTrangThai = MapUtils.getString(argDeco(), Labels.getLabel("param.trangthai"), "").trim();
		String paramTuKhoa = MapUtils.getString(argDeco(), Labels.getLabel("param.tukhoa"), "").trim();
		// Long paramVaiTro = (Long)
		// argDeco().get(Labels.getLabel("param.vaitro"));

		JPAQuery<NhanVien> q = find(NhanVien.class).where(QNhanVien.nhanVien.trangThai.ne(core().TT_DA_XOA));

		if (paramTuKhoa != null && !paramTuKhoa.isEmpty()) {
			String tukhoa = "%" + paramTuKhoa + "%";
			q.where(QNhanVien.nhanVien.hoVaTen.like(tukhoa));
		}

		/*
		 * if (paramVaiTro != null) { VaiTro vaiTro =
		 * find(VaiTro.class).where(QVaiTro.vaiTro.id.eq(paramVaiTro)).
		 * fetchFirst(); q.where(QNhanVien.nhanVien.vaiTros.contains(vaiTro)); }
		 */

		if (paramTrangThai != null && !paramTrangThai.isEmpty()) {
			q.where(QNhanVien.nhanVien.trangThai.eq(paramTrangThai));
		}
		q.orderBy(QNhanVien.nhanVien.trangThai.asc());
		return q.orderBy(QNhanVien.nhanVien.ngaySua.desc());
	}

	public List<NhomNguoiDung> getNhomNguoiDungListAndNull() {
		List<NhomNguoiDung> nhomNguoiDungList = new ArrayList<NhomNguoiDung>();
		nhomNguoiDungList.add(null);
		nhomNguoiDungList = find(NhomNguoiDung.class)
				.where(QNhomNguoiDung.nhomNguoiDung.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QNhomNguoiDung.nhomNguoiDung.ten.asc()).fetch();

		return nhomNguoiDungList;
	}

	public List<NhomCuaHoi> getNhomCuaHoiListAndNull() {
		List<NhomCuaHoi> nhomCuaHoiList = new ArrayList<NhomCuaHoi>();
		nhomCuaHoiList.add(null);
		nhomCuaHoiList = find(NhomCuaHoi.class).where(QNhomCuaHoi.nhomCuaHoi.trangThai.eq(core().TT_AP_DUNG))
				.orderBy(QNhomCuaHoi.nhomCuaHoi.ten.asc()).fetch();

		return nhomCuaHoiList;
	}

	@Command
	public void login(@BindingParam("email") final String email, @BindingParam("password") final String password) {

		NhanVien nhanVien = new JPAQuery<NhanVien>(em()).from(QNhanVien.nhanVien)
				.where(QNhanVien.nhanVien.daXoa.isFalse()).where(QNhanVien.nhanVien.trangThai.ne(core().TT_DA_XOA))
				.where(QNhanVien.nhanVien.tenDangNhap.eq(email)).fetchFirst();

		BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

		if (nhanVien != null
				&& encryptor.checkPassword(password.trim() + nhanVien.getSalkey(), nhanVien.getMatKhau())) {
			
			String cookieToken = nhanVien
					.getCookieToken(System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
			Session zkSession = Sessions.getCurrent();
			zkSession.setAttribute("email", cookieToken);
			zkSession.getWebApp().setAttribute("user", nhanVien);
			
			HttpServletResponse res = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
			Cookie cookie = new Cookie("email", cookieToken);
			cookie.setPath("/");
			cookie.setMaxAge(1000000000);
			Cookie cookieId = new Cookie("user", nhanVien.getId().toString());
			cookieId.setPath("/id");
			cookieId.setMaxAge(1000000000);
			
			res.addCookie(cookie);
			res.addCookie(cookieId);
			// set Active
			changeActive(nhanVien, 1);

			Executions.sendRedirect("/");
			
		} else {
			showNotification("Đăng nhập không thành công", "", "error");
		}
	}

	@Command
	public void logout() {

		NhanVien NhanVienLogin = getNhanVien(true);
		if (NhanVienLogin != null && !NhanVienLogin.noId()) {

			Session zkSession = Sessions.getCurrent();
			zkSession.removeAttribute("email");
			zkSession.removeAttribute("currentUser");
			
			HttpServletResponse res = (HttpServletResponse) Executions.getCurrent().getNativeResponse();

			Cookie cookie = new Cookie("email", null);
			cookie.setPath("/");
			cookie.setMaxAge(0);
			res.addCookie(cookie);

			// set Inactive
			changeActive(NhanVienLogin, 0);

			Executions.sendRedirect("/login");
		}
	}
	
	public void changeActive(NhanVien nhanVien, Integer active) {

		nhanVien.setActive(active);
		nhanVien.saveNotShowNotification();
	}
}
