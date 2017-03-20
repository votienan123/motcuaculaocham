package vn.toancauxanh.cms.service;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;

import vn.toancauxanh.model.NhanVien;
import vn.toancauxanh.service.NhanVienService;

public class OnlineOfflineAccountService extends GenericForwardComposer{

	private static final long serialVersionUID = 1L;

	public void onChange(Event event) {
		
		NhanVienService nhanVienService = new NhanVienService();
		
		Session zkSession = Sessions.getCurrent();
		NhanVien nhanVienLogin = (NhanVien) zkSession.getWebApp().getAttribute("user");	
		
		if(nhanVienLogin != null && nhanVienLogin.getActive() == 1) {
			
			nhanVienService.changeActive(nhanVienLogin, 0);
		}
	}

}
