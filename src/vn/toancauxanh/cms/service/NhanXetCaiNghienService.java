package vn.toancauxanh.cms.service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.toancauxanh.gg.model.NhanXetCaiNghien;
import vn.toancauxanh.service.BasicService;

public class NhanXetCaiNghienService extends BasicService<NhanXetCaiNghien>{	
	public List<Integer> getListThang() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i=1;i<=12;i++) {
			list.add(i);
		}
		return list;
	}
	public List<Integer> getListNam() {
		List<Integer> list = new ArrayList<Integer>();
		Integer yearNow = Calendar.getInstance().get(Calendar.YEAR);
		for (Integer i=yearNow-5;i<=yearNow;i++) {
			list.add(i);
		}
		return list;
	}
}
