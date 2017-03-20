package vn.toancauxanh.model;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.math.NumberUtils;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

@Entity
@Table(name = "settings")
public class Setting extends Model<Setting> {
	private String giaVeNguoiLon = "0";
	private String giaVeTreEmDuoi5Tuoi = "0";
	private String giaVeTreEmDuoi10Tuoi = "0";

	public String getGiaVeNguoiLon() {
		if (giaVeNguoiLon != null) {
			if (!giaVeNguoiLon.isEmpty()) {
				String str = formatGiaVe(giaVeNguoiLon);
				giaVeNguoiLon = str;
			}
		}
		return giaVeNguoiLon;
	}

	public void setGiaVeNguoiLon(String giaVeNguoiLon) {
		this.giaVeNguoiLon = giaVeNguoiLon;
	}

	public String getGiaVeTreEmDuoi5Tuoi() {
		if (giaVeTreEmDuoi5Tuoi != null) {
			if (!giaVeTreEmDuoi5Tuoi.isEmpty()) {
				String str = formatGiaVe(giaVeTreEmDuoi5Tuoi);
				giaVeTreEmDuoi5Tuoi = str;
			}
		}
		return giaVeTreEmDuoi5Tuoi;
	}

	public void setGiaVeTreEmDuoi5Tuoi(String giaVeTreEmDuoi5Tuoi) {
		this.giaVeTreEmDuoi5Tuoi = giaVeTreEmDuoi5Tuoi;
	}

	public String getGiaVeTreEmDuoi10Tuoi() {
		if (giaVeTreEmDuoi10Tuoi != null) {
			if (!giaVeTreEmDuoi10Tuoi.isEmpty()) {
				String str = formatGiaVe(giaVeTreEmDuoi10Tuoi);
				giaVeTreEmDuoi10Tuoi = str;
			}
		}
		return giaVeTreEmDuoi10Tuoi;
	}

	public void setGiaVeTreEmDuoi10Tuoi(String giaVeTreEmDuoi10Tuoi) {
		this.giaVeTreEmDuoi10Tuoi = giaVeTreEmDuoi10Tuoi;
	}
	
	@Command
	public void validateGiaVe(@BindingParam("className") final String _className,
			@BindingParam("giaVe") final String _giaVe) {
		String msg = "Giá vé không hợp lệ.";
		String s = "validateAddClass(" + "'" + _className + "', '" + msg + "')";
		if (_giaVe != null) {
			if (checkValidateGiaVe(_giaVe)) {
				long number = NumberUtils.toLong(_giaVe.replace(".", ""));
				if (number > 0) {
					s = "validateRemoveClass(" + "'" + _className + "')";
				}
			}
		}
		Clients.evalJavaScript(s);
	}
	
	@Transient
	public boolean checkValidateGiaVe(String giaVe) {
		boolean check = false;
		String regex = "^[0-9]+$";
		Pattern pattern = Pattern.compile(regex);
		if (giaVe != null) {
			if (!giaVe.isEmpty()) {
				String str = giaVe.replace(".", "");
				Matcher matcher = pattern.matcher(str);
				check = matcher.matches();
			}
		}
		return check;
	}
	
	@Transient
	public AbstractValidator getValidator() {
		return new AbstractValidator() {
			@Override
			public void validate(final ValidationContext ctx) {
				boolean status = false;
				if (!checkValidateGiaVe(giaVeNguoiLon)) {
					status = true;
				} else {
					long number = NumberUtils.toLong(giaVeNguoiLon.replace(".", ""));
					if (number <= 0) {
						status = true;
					}
				}
				if (status) {
					addInvalidMessage(ctx, "lblErrGiaVe1", "Giá vé không hợp lệ.");
				}

				status = false;
				if (!checkValidateGiaVe(giaVeTreEmDuoi5Tuoi)) {
					status = true;
				} else {
					long number = NumberUtils.toLong(giaVeTreEmDuoi5Tuoi.replace(".", ""));
					if (number <= 0) {
						status = true;
					}
				}
				if (status) {
					addInvalidMessage(ctx, "lblErrGiaVe2", "Giá vé không hợp lệ.");
				}

				status = false;
				if (!checkValidateGiaVe(giaVeTreEmDuoi10Tuoi)) {
					status = true;
				} else {
					long number = NumberUtils.toLong(giaVeTreEmDuoi10Tuoi.replace(".", ""));
					if (number <= 0) {
						status = true;
					}
				}
				if (status) {
					addInvalidMessage(ctx, "lblErrGiaVe3", "Giá vé không hợp lệ.");
				}
			}
		};
	}
	
	private String formatGiaVe(String _giaVe) {
		String giaVe = "";
		if (checkValidateGiaVe(_giaVe)) {
			_giaVe = _giaVe.replace(".", "");
			Locale locale = new Locale("vi", "VN");
			Currency currency = Currency.getInstance("VND");
			DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
			df.setCurrency(currency);
			NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
			numberFormat.setCurrency(currency);
			double price = Double.valueOf(_giaVe);
			_giaVe = numberFormat.format(price).replace(" đ", "").trim();
		}
		giaVe = _giaVe;
		return giaVe;
	}
	
	@Command
	public void saveCauHinh(@BindingParam("list") final Object listObject, @BindingParam("attr") final String attr,
			@BindingParam("wdn") final Window wdn) {
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, attr);
	}
}
