package vn.toancauxanh.gg.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.DefaultTreeNode;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.TreeNode;
import org.zkoss.zul.Window;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "tochucdoanthegiamsat", indexes = { @Index(columnList = "ten")})
public class ToChucDoanTheGiamSat extends Model<ToChucDoanTheGiamSat>{
	private String ten = "";
	private ToChucDoanTheGiamSat cha;
	private String ma = "";
	private int capDonVi;
	private boolean macDinh;
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	@ManyToOne
	public ToChucDoanTheGiamSat getCha() {
		return cha;
	}
	public void setCha(ToChucDoanTheGiamSat cha) {
		this.cha = cha;
	}
	public int getCapDonVi() {
		return capDonVi;
	}
	public void setCapDonVi(int capDonVi) {
		this.capDonVi = capDonVi;
	}
	
	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}
	
	public boolean isMacDinh() {
		return macDinh;
	}
	
	public void setMacDinh(boolean macDinh) {
		this.macDinh = macDinh;
	}
	public int loadSizeChild() {
		int size = core().getToChucDoanTheGiamSats().getCategoryChildren(this).size();
		return size;
	}
	
	@Transient
	public String getChildName() {
		int count = 0;
		String s = " ";
		for (ToChucDoanTheGiamSat cha = getCha(); cha != null; cha = cha.getCha())
			count = count + 1;
		for (int i = 0; i < count; i++)
			s += " - ";
		return s + this.ten;
	}
	
	private transient final TreeNode<ToChucDoanTheGiamSat> node = new DefaultTreeNode<>(this,
			new ArrayList<DefaultTreeNode<ToChucDoanTheGiamSat>>());

	@Transient
	public TreeNode<ToChucDoanTheGiamSat> getNode() {
		return node;
	}
	
	public void loadChildren() {
		for (final ToChucDoanTheGiamSat con : find(ToChucDoanTheGiamSat.class)
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.cha.eq(this))
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.ne(core().TT_DA_XOA))
				.fetch()) {
			con.loadChildren();
			node.add(con.getNode());
		}
	}
	
	public void loadChildren(String param, String trangThai) {
		for (final ToChucDoanTheGiamSat con : find(ToChucDoanTheGiamSat.class)
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.cha.eq(this))
				.where(QToChucDoanTheGiamSat.toChucDoanTheGiamSat.trangThai.ne(core().TT_DA_XOA))
				.fetch()) {
			if (con.getTen().toLowerCase().contains(param.toLowerCase())
					&& (trangThai.isEmpty() || (!trangThai.isEmpty() && con.getTrangThai().equals(trangThai)))) {
				con.loadChildren();
				node.add(con.getNode());
			} else {
				con.loadChildren(param, trangThai);
				if (con.loadSizeChild() > 0) {
					node.add(con.getNode());
				}
			}
		}
		// new DefaultTreeModel<Category>(node, true);
		// node.getModel().setOpenObjects(node.getChildren());
	}
	
	@Command
	public void saveToChucDoanTheGiamSat(@BindingParam("list") final Object listObject, @BindingParam("wdn") final Window wdn) {		
		setTen(getTen().trim().replaceAll("\\s+", " "));
		save();
		wdn.detach();
		BindUtils.postNotifyChange(null, null, listObject, "*");
	}
	
	@Command
	public void saveToChucDoanTheGiamSatCon(@BindingParam("node") org.zkoss.zul.DefaultTreeNode<ToChucDoanTheGiamSat> node1,
			@BindingParam("tree") org.zkoss.zul.DefaultTreeModel<ToChucDoanTheGiamSat> tree, @BindingParam("isAdd") boolean isAdd,
			@BindingParam("wdn") final Window wdn) {
		// public void saveChude() {
		if (isAdd) {
			node1.add(getNode());
		}
		setCapDonVi(4);
		setTen(getTen().trim().replaceAll("\\s+", " "));
		save();
		tree.addOpenObject(node1);
		wdn.detach();
		BindUtils.postNotifyChange(null, null, node1, "*");
	}
	
	@Command
	public void redirectCatagory(@BindingParam("zul") String zul, @BindingParam("vmArgs") Object vmArgs,
			@BindingParam("node") org.zkoss.zul.DefaultTreeNode<ToChucDoanTheGiamSat> node1,
			@BindingParam("tree") org.zkoss.zul.DefaultTreeModel<ToChucDoanTheGiamSat> tree,
			@BindingParam("catSelected") ToChucDoanTheGiamSat catSelected) {
		Map<String, Object> args = new HashMap<>();
		args.put("node", node1);
		args.put("tree", tree);
		args.put("vmArgs", vmArgs);
		args.put("catSelected", catSelected);
		Executions.createComponents(zul, null, args);
	}
	
	@Command
	public void deleteDVHC(final @BindingParam("node") org.zkoss.zul.DefaultTreeNode<ToChucDoanTheGiamSat> node1,
			final @BindingParam("tree") org.zkoss.zul.DefaultTreeModel<ToChucDoanTheGiamSat> tree,
			final @BindingParam("catSelected") ToChucDoanTheGiamSat catSelected) {
		if (!catSelected.noId() && catSelected.inUse()) {
			showNotification("", "", "warning");
			return;
		}

		final List<ToChucDoanTheGiamSat> checkList = core().getToChucDoanTheGiamSats().getCategoryChildren(catSelected);

		for (ToChucDoanTheGiamSat category : checkList) {
			if (!category.noId() && category.inUse()) {
				showNotification("Không thể xoá đơn vị hành chính có đơn vị cấp con đang được sử dụng", "", "warning");
				return;
			}
		}

		Messagebox.show("Bạn muốn xóa đơn vị hành chính này?", "Xác nhận", Messagebox.CANCEL | Messagebox.OK, Messagebox.QUESTION,
				new EventListener<Event>() {
					@Override
					public void onEvent(final Event event) {
						if (Messagebox.ON_OK.equals(event.getName())) {
							for (ToChucDoanTheGiamSat category : checkList) {
								category.setTrangThai(core().TT_DA_XOA);
								category.saveNotShowNotification();
							}
							catSelected.setTrangThai(core().TT_DA_XOA);
							catSelected.saveNotShowNotification();
							// ------------
							DefaultTreeNode<ToChucDoanTheGiamSat> nodeParent = (DefaultTreeNode<ToChucDoanTheGiamSat>) node1.getParent();
							nodeParent.remove(node1);

							tree.addOpenObject(nodeParent);
							BindUtils.postNotifyChange(null, null, nodeParent, "*");
							BindUtils.postNotifyChange(null, null, node1, "*");

							showNotification("Đã xóa thành công!", "", "success");
						}
					}
				});
	}
}
