package vn.toancauxanh.gg.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import vn.toancauxanh.model.Model;

/**
 * The persistent class for the document_type database table.
 * 
 */
@Entity
@Table(name = "document_type")

// @SequenceGenerator(name = "per_class_gen", sequenceName =
// "HIBERNATE_SEQUENCE", allocationSize = 1)
public class DocumentType extends Model<DocumentType> {
	public static transient final Logger LOG = LogManager.getLogger(DocumentType.class.getName());

	/*
	 * private String name; private List<Category> categories; private transient
	 * final Category categoryRoot = new Category(); private final transient
	 * DefaultTreeModel<Category> categoryModel = new
	 * DefaultTreeModel<>(getCategoryRoot().getNode(), true);
	 * 
	 * public DocumentType() { }
	 * 
	 * @Transient
	 * 
	 * @Override public boolean isInUse() { // TODO Auto-generated method stub
	 * return !isNew() && new
	 * JPAQuery(services().entities()).from(QCategory.category).
	 * where(QCategory.category.documentType.eq(this)).exists(); }
	 * 
	 * public String getName() { return this.name; }
	 * 
	 * public void setName( String name1) { this.name = name1; }
	 * 
	 * //bi-directional many-to-one association to Category
	 * 
	 * @OneToMany(fetch = FetchType.LAZY, mappedBy="documentType") public
	 * List<Category> getCategories() { return this.categories; }
	 * 
	 * public void setCategories(List<Category> categories1) { this.categories =
	 * categories1; }
	 * 
	 * public Category addCategory(Category category) {
	 * getCategories().add(category); category.setDocumentType(this);
	 * 
	 * return category; }
	 * 
	 * public Category removeCategory(Category category) {
	 * getCategories().remove(category); category.setDocumentType(null);
	 * 
	 * return category; }
	 */

	/*
	 * @Transient public Category getCategoryRoot() { return categoryRoot; }
	 * 
	 * @Transient public DefaultTreeModel<Category> getCategoryModel() { if
	 * (!categoryModel.isMultiple()) { categoryModel.setMultiple(true);
	 * getCategoryRoot().loadChildren();
	 * categoryModel.setOpenObjects(getCategoryRoot().getAllChildren()); }
	 * return categoryModel; }
	 */
}