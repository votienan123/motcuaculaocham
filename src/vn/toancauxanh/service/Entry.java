package vn.toancauxanh.service;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zkoss.util.resource.Labels;
import org.zkoss.zhtml.Object;

import vn.toancauxanh.cms.service.CongTyKinhDoanhService;
import vn.toancauxanh.cms.service.DatVeService;
import vn.toancauxanh.cms.service.DoiTuongService;
import vn.toancauxanh.cms.service.DonViHanhChinhService;
import vn.toancauxanh.cms.service.DonViService;
import vn.toancauxanh.cms.service.GiaVe2Ngay1DemService;
import vn.toancauxanh.cms.service.GiaVeDiTauRiengService;
import vn.toancauxanh.cms.service.GiaVeDiTourRiengService;
import vn.toancauxanh.cms.service.GioiTinhService;
import vn.toancauxanh.cms.service.HanhViViPhamService;
import vn.toancauxanh.cms.service.HinhThucXuLyService;
import vn.toancauxanh.cms.service.MaTuySuDungService;
import vn.toancauxanh.cms.service.NganhNgheService;
import vn.toancauxanh.cms.service.NhanXetCaiNghienService;
import vn.toancauxanh.cms.service.NhomCuaHoiService;
import vn.toancauxanh.cms.service.NhomNguoiDungService;
import vn.toancauxanh.cms.service.PhanLoaiCongTyService;
import vn.toancauxanh.cms.service.PhanLoaiKhachThueTauService;
import vn.toancauxanh.cms.service.PhanLoaiTourService;
import vn.toancauxanh.cms.service.QuanHeDoiTuongLienQuanService;
import vn.toancauxanh.cms.service.QuanHeGiaDinhService;
import vn.toancauxanh.cms.service.SettingService;
import vn.toancauxanh.cms.service.TauService;
import vn.toancauxanh.cms.service.ThanhPhanDoiTuongService;
import vn.toancauxanh.cms.service.ThongTinCaiNghienService;
import vn.toancauxanh.cms.service.ThongTinChinhSachThuHuongService;
import vn.toancauxanh.cms.service.ThongTinDieuTriMethadonesService;
import vn.toancauxanh.cms.service.ThongTinDieuTriTamThanService;
import vn.toancauxanh.cms.service.ThongTinGiaiQuyetViecRiengService;
import vn.toancauxanh.cms.service.ThongTinViPhamService;
import vn.toancauxanh.cms.service.ToChucDoanTheGiamSatService;
import vn.toancauxanh.cms.service.ToDanPhoService;
import vn.toancauxanh.cms.service.TrinhDoHocVanService;
import vn.toancauxanh.cms.service.TuyenXeService;
import vn.toancauxanh.model.VaiTro;

@Configuration
@Controller
public class Entry extends BaseObject<Object> {
	static Entry instance;

	@Value("${trangthai.apdung}")
	public String TT_AP_DUNG = "";
	@Value("${trangthai.daxoa}")
	public String TT_DA_XOA = "";
	@Value("${trangthai.khongapdung}")
	public String TT_KHONG_AP_DUNG = "";
	@Value("${dieutiet.nhomsangnhom_tru}")
	public String DT_NHOM_SA_NHOM_TRU = "";
	@Value("${dieutiet.nhomsangnhom_cong}")
	public String DT_NHOM_SA_NHOM_CONG = "";
	
	@Value("${action.xem}")
	public String XEM = ""; // duoc xem bat ky
	@Value("${action.list}")
	public String LIST = ""; // duoc vao trang list search url
	@Value("${action.sua}")
	public String SUA = ""; // duoc sua bat ky
	@Value("${action.xoa}")
	public String XOA = ""; // duoc xoa bat ky
	@Value("${action.them}")
	public String THEM = ""; // duoc them
	@Value("${action.huy}")
	public String HUY = ""; // duoc huy ve
	@Value("${action.phuchoi}")
	public String PHUCHOI = ""; // duoc huy ve
	@Value("${action.lichsucapnhat}")
	public String LICHSUCAPNHAT = ""; // duoc them
	@Value("${action.lichsu}")
	public String LICHSU = "";
	@Value("${action.dieutietve}")
	public String DIEUTIETVE = "";
	@Value("${action.nhapgiaybaoton}")
	public String NHAPGIAYBAOTON = "";
	@Value("${action.xemgiaybaoton}")
	public String XEMGIAYBAOTON = "";
	@Value("${action.thongkeve}")
	public String THONGKEVE = "";
	@Value("${action.duyetve}")
	public String DUYETVE = "";
	@Value("${action.thuchientour}")
	public String THUCHIENTOUR = "";
	@Value("${action.huydattour}")
	public String HUYDATTOUR = "";
	@Value("${action.dieutietcongty}")
	public String DIEUTIETCONGTY = "";
	
	@Value("${url.nguoidung}")
	public String NGUOIDUNG = "";
	@Value("${url.vaitro}")
	public String VAITRO = "";
	@Value("${url.thongke}")
	public String THONGKE = "";
	

	/* cu lao cham */
	@Value("${url.nhomnguoidung}")
	public String NHOMNGUOIDUNG = "";
	@Value("${url.khachdatve}")
	public String KHACHDATVE = "";
	@Value("${url.khachdoiduyet}")
	public String KHACHDOIDUYET = "";
	@Value("${url.khachhuyve}")
	public String KHACHHUYVE = "";
	@Value("${url.phanloaicongty}")
	public String PHANLOAICONGTY = "";
	@Value("${url.cauhinhphanloaitour}")
	public String CAUHINHPHANLOAITOUR = "";
	@Value("${url.phanloaikhachthuetau}")
	public String CAUHINHPHANLOAIKHACHTHUETAU = "";
	@Value("${url.cauhinhgiaveditourrieng}")
	public String CAUHINHGIAVEDITOURRIENG = "";
	@Value("${url.cauhinhgiaveditaurieng}")
	public String CAUHINHGIAVEDITAURIENG = "";
	@Value("${url.cauhinhgiave2ngay1dem}")
	public String CAUHINHGIAVE2NGAY1DEM = "";
	@Value("${url.tuyenxe}")
	public String CAUHINHTUYENXE = "";
	@Value("${url.tau}")
	public String TAU = "";
	@Value("${url.congtykinhdoanh}")
	public String CONGTYKINHDOANH = "";
	@Value("${url.nhomcuahoi}")
	public String NHOMCUAHOI = "";
	@Value("${url.khachthuchientour}")
	public String KHACHTHUCHIENTOUR = "";
	@Value("${url.thongkeclc}")
	public String THONGKECLC = "";
	
	// uend
	public char CHAR_CACH = ':';
	public String CACH = CHAR_CACH + "";
		
	@Value("${url.khachdatve}" + ":" + "${action.xem}")
	public String KHACHDATVEXEM;
	@Value("${url.khachdatve}" + ":" + "${action.them}")
	public String KHACHDATVETHEM = "";
	@Value("${url.khachdatve}" + ":" + "${action.list}")
	public String KHACHDATVELIST = "";
	@Value("${url.khachdatve}" + ":" + "${action.xoa}")
	public String KHACHDATVEXOA = "";
	@Value("${url.khachdatve}" + ":" + "${action.sua}")
	public String KHACHDATVESUA = "";
	@Value("${url.khachdatve}" + ":" + "${action.huy}")
	public String KHACHDATVEHUY = "";
	@Value("${url.khachdatve}" + ":" + "${action.phuchoi}")
	public String KHACHDATVEPHUCHOI = "";
	@Value("${url.khachdatve}" + ":" + "${action.dieutietve}")
	public String KHACHDATVEDIEUTIETVE = "";
	@Value("${url.khachdatve}" + ":" + "${action.nhapgiaybaoton}")
	public String KHACHDATVENHAPGIAYBAOTON = "";
	@Value("${url.khachdatve}" + ":" + "${action.xemgiaybaoton}")
	public String KHACHDATVEXEMGIAYBAOTON = "";
	@Value("${url.khachdatve}" + ":" + "${action.lichsucapnhat}")
	public String KHACHDATVELICHSUCAPNHAT = "";
	@Value("${url.khachdatve}" + ":" + "${action.thongkeve}")
	public String KHACHDATVETHONGKEVE = "";
	@Value("${url.khachdatve}" + ":" + "${action.duyetve}")
	public String KHACHDATVEDUYETVE = "";
	@Value("${url.khachdatve}" + ":" + "${action.dieutietcongty}")
	public String KHACHDATVEDIEUTIETCONGTY = "";
	
	@Value("${url.thongkeclc}" + ":" + "${action.thuchientour}")
	public String THONGKECLCTHUCHIENTOUR = "";
	@Value("${url.thongkeclc}" + ":" + "${action.huydattour}")
	public String THONGKECLCHUYDATTOUR = "";
	
	@Value("${url.khachdoiduyet}" + ":" + "${action.list}")
	public String KHACHDOIDUYETLIST = "";
	
	@Value("${url.khachhuyve}" + ":" + "${action.list}")
	public String KHACHHUYVELIST = "";
	
	@Value("${url.phanloaicongty}" + ":" + "${action.xem}")
	public String PHANLOAICONGTYXEM;
	@Value("${url.phanloaicongty}" + ":" + "${action.them}")
	public String PHANLOAICONGTYTHEM = "";
	@Value("${url.phanloaicongty}" + ":" + "${action.list}")
	public String PHANLOAICONGTYLIST = "";
	@Value("${url.phanloaicongty}" + ":" + "${action.xoa}")
	public String PHANLOAICONGTYXOA = "";
	@Value("${url.phanloaicongty}" + ":" + "${action.sua}")
	public String PHANLOAICONGTYSUA = "";
	
	@Value("${url.nhomnguoidung}" + ":" + "${action.xem}")
	public String NHOMNGUOIDUNGXEM;
	@Value("${url.nhomnguoidung}" + ":" + "${action.them}")
	public String NHOMNGUOIDUNGTHEM = "";
	@Value("${url.nhomnguoidung}" + ":" + "${action.list}")
	public String NHOMNGUOIDUNGLIST = "";
	@Value("${url.nhomnguoidung}" + ":" + "${action.xoa}")
	public String NHOMNGUOIDUNGXOA = "";
	@Value("${url.nhomnguoidung}" + ":" + "${action.sua}")
	public String NHOMNGUOIDUNGSUA = "";
	
	@Value("${url.khachthuchientour}" + ":" + "${action.xem}")
	public String KHACHTHUCHIENTOURXEM;
	@Value("${url.khachthuchientour}" + ":" + "${action.them}")
	public String KHACHTHUCHIENTOURTHEM = "";
	@Value("${url.khachthuchientour}" + ":" + "${action.list}")
	public String KHACHTHUCHIENTOURLIST = "";
	@Value("${url.khachthuchientour}" + ":" + "${action.xoa}")
	public String KHACHTHUCHIENTOURXOA = "";
	@Value("${url.khachthuchientour}" + ":" + "${action.sua}")
	public String KHACHTHUCHIENTOURSUA = "";
	
	@Value("${url.vaitro}" + ":" + "${action.xem}")
	public String VAITROXEM;
	@Value("${url.vaitro}" + ":" + "${action.them}")
	public String VAITROTHEM = "";
	@Value("${url.vaitro}" + ":" + "${action.list}")
	public String VAITROLIST = "";
	@Value("${url.vaitro}" + ":" + "${action.xoa}")
	public String VAITROXOA = "";
	@Value("${url.vaitro}" + ":" + "${action.sua}")
	public String VAITROSUA = "";

	@Value("${url.nguoidung}" + ":" + "${action.xem}")
	public String NGUOIDUNGXEM = "";
	@Value("${url.nguoidung}" + ":" + "${action.them}")
	public String NGUOIDUNGTHEM = "";
	@Value("${url.nguoidung}" + ":" + "${action.list}")
	public String NGUOIDUNGLIST = "";
	@Value("${url.nguoidung}" + ":" + "${action.xoa}")
	public String NGUOIDUNGXOA = "";
	@Value("${url.nguoidung}" + ":" + "${action.sua}")
	public String NGUOIDUNGSUA = "";
		
	@Value("${url.cauhinhphanloaitour}" + ":" + "${action.xem}")
	public String CAUHINHPHANLOAITOURXEM;
	@Value("${url.cauhinhphanloaitour}" + ":" + "${action.them}")
	public String CAUHINHPHANLOAITOURTHEM = "";
	@Value("${url.cauhinhphanloaitour}" + ":" + "${action.list}")
	public String CAUHINHPHANLOAITOURLIST = "";
	@Value("${url.cauhinhphanloaitour}" + ":" + "${action.xoa}")
	public String CAUHINHPHANLOAITOURXOA = "";
	@Value("${url.cauhinhphanloaitour}" + ":" + "${action.sua}")
	public String CAUHINHPHANLOAITOURSUA = "";
	
	@Value("${url.phanloaikhachthuetau}" + ":" + "${action.xem}")
	public String CAUHINHPHANLOAIKHACHTHUETAUXEM;
	@Value("${url.phanloaikhachthuetau}" + ":" + "${action.them}")
	public String CAUHINHPHANLOAIKHACHTHUETAUTHEM = "";
	@Value("${url.phanloaikhachthuetau}" + ":" + "${action.list}")
	public String CAUHINHPHANLOAIKHACHTHUETAULIST = "";
	@Value("${url.phanloaikhachthuetau}" + ":" + "${action.xoa}")
	public String CAUHINHPHANLOAIKHACHTHUETAUXOA = "";
	@Value("${url.phanloaikhachthuetau}" + ":" + "${action.sua}")
	public String CAUHINHPHANLOAIKHACHTHUETAUSUA = "";
	
	@Value("${url.cauhinhgiave2ngay1dem}" + ":" + "${action.xem}")
	public String CAUHINHGIAVE2NGAY1DEMXEM;
	@Value("${url.cauhinhgiave2ngay1dem}" + ":" + "${action.them}")
	public String CAUHINHGIAVE2NGAY1DEMTHEM = "";
	@Value("${url.cauhinhgiave2ngay1dem}" + ":" + "${action.list}")
	public String CAUHINHGIAVE2NGAY1DEMLIST = "";
	@Value("${url.cauhinhgiave2ngay1dem}" + ":" + "${action.xoa}")
	public String CAUHINHGIAVE2NGAY1DEMXOA = "";
	@Value("${url.cauhinhgiave2ngay1dem}" + ":" + "${action.sua}")
	public String CAUHINHGIAVE2NGAY1DEMSUA = "";
	
	@Value("${url.cauhinhgiaveditourrieng}" + ":" + "${action.xem}")
	public String CAUHINHGIAVEDITOURRIENGXEM;
	@Value("${url.cauhinhgiaveditourrieng}" + ":" + "${action.them}")
	public String CAUHINHGIAVEDITOURRIENGTHEM = "";
	@Value("${url.cauhinhgiaveditourrieng}" + ":" + "${action.list}")
	public String CAUHINHGIAVEDITOURRIENGLIST = "";
	@Value("${url.cauhinhgiaveditourrieng}" + ":" + "${action.xoa}")
	public String CAUHINHGIAVEDITOURRIENGXOA = "";
	@Value("${url.cauhinhgiaveditourrieng}" + ":" + "${action.sua}")
	public String CAUHINHGIAVEDITOURRIENGSUA = "";
	
	@Value("${url.cauhinhgiaveditaurieng}" + ":" + "${action.xem}")
	public String CAUHINHGIAVEDITAURIENGXEM;
	@Value("${url.cauhinhgiaveditaurieng}" + ":" + "${action.them}")
	public String CAUHINHGIAVEDITAURIENGTHEM = "";
	@Value("${url.cauhinhgiaveditaurieng}" + ":" + "${action.list}")
	public String CAUHINHGIAVEDITAURIENGLIST = "";
	@Value("${url.cauhinhgiaveditaurieng}" + ":" + "${action.xoa}")
	public String CAUHINHGIAVEDITAURIENGXOA = "";
	@Value("${url.cauhinhgiaveditaurieng}" + ":" + "${action.sua}")
	public String CAUHINHGIAVEDITAURIENGSUA = "";
	
	@Value("${url.tuyenxe}" + ":" + "${action.xem}")
	public String CAUHINHTUYENXEXEM;
	@Value("${url.tuyenxe}" + ":" + "${action.them}")
	public String CAUHINHTUYENXETHEM = "";
	@Value("${url.tuyenxe}" + ":" + "${action.list}")
	public String CAUHINHTUYENXELIST = "";
	@Value("${url.tuyenxe}" + ":" + "${action.xoa}")
	public String CAUHINHTUYENXEXOA = "";
	@Value("${url.tuyenxe}" + ":" + "${action.sua}")
	public String CAUHINHTUYENXESUA = "";
	
	@Value("${url.tau}" + ":" + "${action.xem}")
	public String TAUXEM;
	@Value("${url.tau}" + ":" + "${action.them}")
	public String TAUTHEM = "";
	@Value("${url.tau}" + ":" + "${action.list}")
	public String TAULIST = "";
	@Value("${url.tau}" + ":" + "${action.xoa}")
	public String TAUXOA = "";
	@Value("${url.tau}" + ":" + "${action.sua}")
	public String TAUSUA = "";
	
	@Value("${url.congtykinhdoanh}" + ":" + "${action.xem}")
	public String CONGTYKINHDOANHXEM;
	@Value("${url.congtykinhdoanh}" + ":" + "${action.them}")
	public String CONGTYKINHDOANHTHEM = "";
	@Value("${url.congtykinhdoanh}" + ":" + "${action.list}")
	public String CONGTYKINHDOANHLIST = "";
	@Value("${url.congtykinhdoanh}" + ":" + "${action.xoa}")
	public String CONGTYKINHDOANHXOA = "";
	@Value("${url.congtykinhdoanh}" + ":" + "${action.sua}")
	public String CONGTYKINHDOANHSUA = "";
	
	@Value("${url.nhomcuahoi}" + ":" + "${action.xem}")
	public String NHOMCUAHOIXEM;
	@Value("${url.nhomcuahoi}" + ":" + "${action.them}")
	public String NHOMCUAHOITHEM = "";
	@Value("${url.nhomcuahoi}" + ":" + "${action.list}")
	public String NHOMCUAHOILIST = "";
	@Value("${url.nhomcuahoi}" + ":" + "${action.xoa}")
	public String NHOMCUAHOIXOA = "";
	@Value("${url.nhomcuahoi}" + ":" + "${action.sua}")
	public String NHOMCUAHOISUA = "";
	
	@Value("${url.thongke}" + ":" + "${action.list}")
	public String THONGKE_LIST = "";
	
	
	// aend
	/*
	 * public String[] getRESOURCES() { return new String[] {DOITUONG,
	 * HINHTHUCXULYDANGAPDUNG, THONGTINDIEUTRITAITRUNGTAMMETHADONE,
	 * THONGTINDIEUTRITAIBENHVIENTAMTHAN, THONGTINCAINGHIEN, THONGTINTHUHUONG,
	 * DANHSACHNGUOINGHIEN, DANHSACHNGUOISUDUNGTRAIPHEP, DANHSACHNGUOISAUCAI,
	 * DANHSACHNGUOISAUQUANLY, DANHSACHNGUOINGOAITINH,
	 * DANHSACHCOQUYETDINHTRUYNATRUYTIM, DANHSACHCOTIENAN, DANHSACHCOTIENSU,
	 * DANHSACHCONGHIVANVPPL, DANHSACHCOBIEUHIENLOANTHAN,
	 * DANHSACHCODIACHIKHACNHAU, DANHSACHDANGTHUHUONGCHINHSACH,
	 * DANHSACHDANGTHUHUONGCHINHSACHVIPHAM, NGUOIDUNG, VAITRO, DONVI,
	 * DONVIHANHCHINH, TODANPHO, MATUYSUDUNG, TRINHDOHOCVAN, THANHPHANGIADINH,
	 * HINHTHUCXULY, NGANHNGHE, GIOITINH, HANHVIVIPHAM, THONGTINVIPHAM,
	 * QUANHEGIADINH, QUANHEDOITUONGLIENQUAN, TOCHUCDOANTHEGIAMSAT,
	 * QUANTRIHETHONG, THONGKE}; }
	 */

	public String[] getRESOURCES() {
		return new String[] {NGUOIDUNG, VAITRO, KHACHDATVE, KHACHDOIDUYET, KHACHHUYVE, KHACHTHUCHIENTOUR, PHANLOAICONGTY, 
				CAUHINHPHANLOAITOUR, CAUHINHPHANLOAIKHACHTHUETAU, CAUHINHGIAVEDITOURRIENG, CAUHINHGIAVEDITAURIENG, CAUHINHGIAVE2NGAY1DEM,
				CAUHINHTUYENXE, TAU, CONGTYKINHDOANH, NHOMCUAHOI, THONGKECLC};
	}

	public String[] getACTIONS() {
		return new String[] { LIST, XEM, THEM, SUA, XOA, LICHSUCAPNHAT, LICHSU, HUY, PHUCHOI, DIEUTIETVE, NHAPGIAYBAOTON, XEMGIAYBAOTON, THONGKEVE, DUYETVE, THUCHIENTOUR, HUYDATTOUR, DIEUTIETCONGTY };
	}

	static {
		File file = new File(Labels.getLabel("filestore.root") + File.separator + Labels.getLabel("filestore.folder"));
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory mis is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}
	@Autowired
	public Environment env;

	@Autowired
	DataSource dataSource;

	public Entry() {
		super();
		setCore();
		instance = this;
	}

	// @Bean
	// public FilterRegistrationBean loginFilter() {
	// FilterRegistrationBean rs = new FilterRegistrationBean(new
	// LoginFilter());
	// rs.addUrlPatterns("/*");
	// return rs;
	// }
	
	@Bean
	public FilterRegistrationBean cacheFilter() {
		FilterRegistrationBean rs = new FilterRegistrationBean(new CacheFilter());
		rs.addUrlPatterns("*.css");
		rs.addUrlPatterns("*.js");
		rs.addUrlPatterns("*.wpd");
		rs.addUrlPatterns("*.wcs");
		rs.addUrlPatterns("*.jpg");
		rs.addUrlPatterns("*.jpeg");
		rs.addUrlPatterns("*.png");
		return rs;
	}
	
	Map<String, String> pathMap = new HashMap<>();

	{
		MapUtils.putAll(pathMap,
				new String[] { "doanhnghiep", "doanhnghiep", "gioithieu", "gioithieu", "cosophaply", "cosophaply" });
	}

	@RequestMapping(value = "/")
	public String cp() {
		return "forward:/WEB-INF/zul/home1.zul?resource=thongkeve&action=lietke&file=/WEB-INF/zul/thongkeve/list."
				+ "zul";
	}

	@RequestMapping(value = "/{path:.+$}")
	public String cp(@PathVariable String path) {
		if (path.equals("doituong")) {
			return cp();
		}
		return "forward:/WEB-INF/zul/home1.zul?resource=" + path + "&action=lietke&file=/WEB-INF/zul/" + path
				+ "/list.zul";
	}

	@RequestMapping(value = "/{path:.+$}/them")
	public String actionThem(@PathVariable String path) {
		return "forward:/WEB-INF/zul/home1.zul?resource=" + path + "&action=them&file=/WEB-INF/zul/doituong/them.zul";
	}

	@RequestMapping(value = "/{path:.+$}/xem/{id:.+$}")
	public String actionXem(@PathVariable String path, @PathVariable Long id) {
		return "forward:/WEB-INF/zul/home1.zul?resource=" + path + "&action=xem&file=/WEB-INF/zul/doituong/xem.zul&id="
				+ id;
	}

	@RequestMapping(value = "/{path:.+$}/sua/{id:.+$}")
	public String actionSua(@PathVariable String path, @PathVariable Long id) {
		return "forward:/WEB-INF/zul/home1.zul?resource=" + path + "&action=sua&file=/WEB-INF/zul/doituong/sua.zul&id="
				+ id;
	}

	@RequestMapping(value = "/{path:.+$}/lichsu/{id:.+$}")
	public String actionLichSu(@PathVariable String path, @PathVariable Long id) {
		return "forward:/WEB-INF/zul/home1.zul?resource=" + path
				+ "&action=sua&file=/WEB-INF/zul/doituong/lichsu.zul&id=" + id;
	}

	@RequestMapping(value = "/thongkeve")
	public String actionThongKeVe() {
		return "forward:/WEB-INF/zul/home1.zul?resource=khachdatve&action=thongkeve&file=/WEB-INF/zul/thongkeve/list.zul";
	}

	@RequestMapping(value = "/thongke/{path:.+$}")
	public String thongke(@PathVariable String path) {
		return "forward:/WEB-INF/zul/home1.zul?resource=thongke&action=" + path + "&file=/WEB-INF/zul/thongke/" + path
				+ ".zul";
	}
	
	@RequestMapping(value = "/thongkeclc/{path:.+$}")
	public String thongkeclc (@PathVariable String path) {
		return "forward:/WEB-INF/zul/home1.zul?resource=thongkeclc&action=" + path + "&file=/WEB-INF/zul/thongkeclc/" + path
				+ ".zul";
	}

	@RequestMapping(value = "/login")
	public String dangNhapBackend() {
		return "forward:/WEB-INF/zul/login.zul";
	}
	
	public final SettingService getSettingService() {
		return new SettingService();
	}
	
	public final PhanLoaiCongTyService getPhanLoaiCongTys() {
		return new PhanLoaiCongTyService();
	}

	public final DatVeService getDatVes() {
		return new DatVeService();
	}

	public final NhomNguoiDungService getNhomNguoiDungs() {
		return new NhomNguoiDungService();
	}

	public final TrinhDoHocVanService getTrinhDoHocVans() {
		return new TrinhDoHocVanService();
	}

	public final MaTuySuDungService getMaTuySuDungs() {
		return new MaTuySuDungService();
	}

	public final ToDanPhoService getToDanPhos() {
		return new ToDanPhoService();
	}

	public final DonViService getDonVis() {
		return new DonViService();
	}

	public final HanhViViPhamService getHanhViViPhams() {
		return new HanhViViPhamService();
	}
	
	public final GiaVeDiTourRiengService getGiaVeDiTourRiengs() {
		return new GiaVeDiTourRiengService();
	}
	
	public final GiaVeDiTauRiengService getGiaVeDiTauRiengs() {
		return new GiaVeDiTauRiengService();
	}
	
	public final GiaVe2Ngay1DemService getGiaVe2Ngay1Dems() {
		return new GiaVe2Ngay1DemService();
	}

	public final ToChucDoanTheGiamSatService getToChucDoanTheGiamSats() {
		return new ToChucDoanTheGiamSatService();
	}

	public final HinhThucXuLyService getHinhThucXuLys() {
		return new HinhThucXuLyService();
	}
	
	public final TuyenXeService getTuyenXes() {
		return new TuyenXeService();
	}

	public final ThongTinViPhamService getThongTinViPhams() {
		return new ThongTinViPhamService();
	}

	public final ThongTinGiaiQuyetViecRiengService getThongTinGiaiQuyetViecRiengs() {
		return new ThongTinGiaiQuyetViecRiengService();
	}

	public final ThongTinCaiNghienService getThongTinCaiNghiens() {
		return new ThongTinCaiNghienService();
	}

	public final ThongTinChinhSachThuHuongService getThongTinChinhSachThuHuongs() {
		return new ThongTinChinhSachThuHuongService();
	}

	public final NganhNgheService getNganhNghes() {
		return new NganhNgheService();
	}

	public final GioiTinhService getGioiTinhs() {
		return new GioiTinhService();
	}

	public final NhanXetCaiNghienService getNhanXetCaiNghiens() {
		return new NhanXetCaiNghienService();
	}

	public final DoiTuongService getDoiTuongs() {
		return new DoiTuongService();
	}

	public final ThanhPhanDoiTuongService getThanhPhanDoiTuongs() {
		return new ThanhPhanDoiTuongService();
	}

	public final Quyen getQuyen() {
		return getNhanVien().getTatCaQuyen();
	}

	public final VaiTroService getVaiTros() {
		return new VaiTroService();
	}

	public final DonViHanhChinhService getDonViHanhChinhs() {
		return new DonViHanhChinhService();
	}

	public final QuanHeGiaDinhService getQuanHeGiaDinhs() {
		return new QuanHeGiaDinhService();
	}

	public final ThongTinDieuTriMethadonesService getThongTinDieuTriMethadones() {
		return new ThongTinDieuTriMethadonesService();
	}

	public final ThongTinDieuTriTamThanService getThongTinDieuTriTamThans() {
		return new ThongTinDieuTriTamThanService();
	}

	public final QuanHeDoiTuongLienQuanService getQuanHeDoiTuongLienQuans() {
		return new QuanHeDoiTuongLienQuanService();
	}

	public final PhanLoaiTourService getPhanLoaiTours() {
		return new PhanLoaiTourService();
	}

	public final NhomCuaHoiService getNhomCuaHois() {
		return new NhomCuaHoiService();
	}

	public final CongTyKinhDoanhService getCongTyKinhDoanhs() {
		return new CongTyKinhDoanhService();
	}

	public final PhanLoaiKhachThueTauService getPhanLoaiKhachThueTaus() {
		return new PhanLoaiKhachThueTauService();
	}

	public final TauService getTaus() {
		return new TauService();
	}

	public final List<String> getNoiDungActive() {
		return Arrays.asList("chude", "baiviet", "video", "gallery", "lienket", "linhvuchoidap", "hoidaptructuyen",
				"faqcategory", "faq");
	}

	public boolean checkVaiTro(String vaiTro) {
		if (vaiTro == null || vaiTro.isEmpty()) {
			return false;
		}
		boolean rs = false;
		for (VaiTro vt : getNhanVien().getVaiTros()) {
			if (vaiTro.equals(vt.getAlias())) {
				rs = true;
				break;
			}
		}
		return rs;
	}

	public String subString(String text, int size) {
		int l = text.length();
		int index = size > l ? l : size;
		while (index < l && ' ' != text.charAt(index)) {
			index++;
		}
		String tail = index < l ? " ..." : "";
		return text.substring(0, index) + tail;
	}

}