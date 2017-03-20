package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.zkoss.image.Image;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "theme", indexes = { @Index(columnList = "name"), @Index(columnList = "menuHeaderStyle"),
		@Index(columnList = "menuFooterStyle"), @Index(columnList = "title3Style"),
		@Index(columnList = "vbarMenuFooterStyle"), @Index(columnList = "vbarMenuHeaderStyle"),
		@Index(columnList = "image"), @Index(columnList = "childMenuHeader"),
		@Index(columnList = "borderChildMenuHeader") })
public class Theme extends Model<Theme> {
	public static transient final Logger LOG = LogManager.getLogger(Image.class.getName());

	private String name;

	private String menuHeaderStyle;

	private String vbarMenuHeaderStyle;

	private String menuFooterStyle;

	private String vbarMenuFooterStyle;

	private String title3Style;

	private String image;

	private String childMenuHeader;

	private String borderChildMenuHeader;

	// @SuppressWarnings("deprecation")
	// @org.hibernate.annotations.Index(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String displayName) {
		this.name = displayName;
	}

	// @SuppressWarnings("deprecation")
	// @org.hibernate.annotations.Index(name = "menuHeaderStyle")
	public String getMenuHeaderStyle() {
		return menuHeaderStyle;
	}

	public void setMenuHeaderStyle(String menuHeaderStyle) {
		this.menuHeaderStyle = menuHeaderStyle;
	}

	// @SuppressWarnings("deprecation")
	// @org.hibernate.annotations.Index(name = "menuFooterStyle")
	public String getMenuFooterStyle() {
		return menuFooterStyle;
	}

	public void setMenuFooterStyle(String menuFooterStyle1) {
		this.menuFooterStyle = menuFooterStyle1;
	}

	// @SuppressWarnings("deprecation")
	// @org.hibernate.annotations.Index(name = "title3Style")
	public String getTitle3Style() {
		return title3Style;
	}

	public void setTitle3Style(String title3Style1) {
		this.title3Style = title3Style1;
	}

	// @SuppressWarnings("deprecation")
	// @org.hibernate.annotations.Index(name = "vbarMenuFooterStyle")
	public String getVbarMenuFooterStyle() {
		return vbarMenuFooterStyle;
	}

	public void setVbarMenuFooterStyle(String vbarMenuFooterStyle1) {
		this.vbarMenuFooterStyle = vbarMenuFooterStyle1;
	}

	// @SuppressWarnings("deprecation")
	// @org.hibernate.annotations.Index(name = "vbarMenuHeaderStyle")
	public String getVbarMenuHeaderStyle() {
		return vbarMenuHeaderStyle;
	}

	public void setVbarMenuHeaderStyle(String vbarMenuHeaderStyle1) {
		this.vbarMenuHeaderStyle = vbarMenuHeaderStyle1;
	}

	// @SuppressWarnings("deprecation")
	// @org.hibernate.annotations.Index(name = "image")
	public String getImage() {
		return image;
	}

	public void setImage(String image1) {
		this.image = image1;
	}

	// @SuppressWarnings("deprecation")
	// @org.hibernate.annotations.Index(name = "childMenuHeader")
	public String getChildMenuHeader() {
		return childMenuHeader;
	}

	public void setChildMenuHeader(String childMenuHeader1) {
		this.childMenuHeader = childMenuHeader1;
	}

	// @SuppressWarnings("deprecation")
	// @org.hibernate.annotations.Index(name = "borderChildMenuHeader")
	public String getBorderChildMenuHeader() {
		return borderChildMenuHeader;
	}

	public void setBorderChildMenuHeader(String borderChildMenuHeader1) {
		this.borderChildMenuHeader = borderChildMenuHeader1;
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		transactioner().execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				if (noId() || getId().longValue() == 0) {
					doSave();
				} else {
					doSave();
					showNotification("Đã cập nhật thành công!", "", "success");
				}
			}
		});
	}
}
