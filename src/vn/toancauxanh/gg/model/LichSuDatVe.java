package vn.toancauxanh.gg.model;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import vn.toancauxanh.model.Model;

@Entity
@Table(name = "lichsudatve")
public class LichSuDatVe extends Model<LichSuDatVe> {

	private String noiDung = "";
	private String chiTietThayDoi = "";
	private DatVe datVe;

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	@Lob
	public String getChiTietThayDoi() {
		return chiTietThayDoi;
	}

	public void setChiTietThayDoi(String chiTietThayDoi) {
		this.chiTietThayDoi = chiTietThayDoi;
	}

	@ManyToOne
	public DatVe getDatVe() {
		return datVe;
	}

	public void setDatVe(DatVe datVe) {
		this.datVe = datVe;
	}
	
	@Transient
	public List<PropertyChangeObject> getListLichSuDatVe() {
		JsonParser jsonParser = new JsonParser();
		JsonArray jsonArr = (JsonArray)jsonParser.parse(getChiTietThayDoi());
        Gson googleJson = new Gson();		
		Type listType = new TypeToken<List<PropertyChangeObject>>(){
			private static final long serialVersionUID = -4281837007133110440L;}.getType();
        List<PropertyChangeObject> list = googleJson.fromJson(jsonArr.toString(), listType);
        List<PropertyChangeObject> temp = new ArrayList<PropertyChangeObject>();
        for (PropertyChangeObject obj : list) {
        	boolean flag = false;
        	for (PropertyChangeObject obj2 : temp) {
        		if (obj.getPropertyName().equals(obj2.getPropertyName())) {
        			obj2.setNewValue(obj.getNewValue());
        			flag = true;
        			break;
        		}
        	}
        	if (!flag) {
        		temp.add(obj);
        	}
        }        
        List<PropertyChangeObject> temp2 = new ArrayList<PropertyChangeObject>();
        temp2.addAll(list);
        for (PropertyChangeObject obj : list) {
        	if (obj.getNewValue().equals(obj.getOldValue())) {
        		temp2.remove(obj);
        	}
        }        
        List<PropertyChangeObject> temp3 = new ArrayList<PropertyChangeObject>();
        for (PropertyChangeObject obj : temp2) {
        	boolean flag = false;
        	for (PropertyChangeObject obj2 : temp3) {
        		if (obj.getPropertyName().equals(obj2.getPropertyName())) {
        			obj2.setNewValue(obj.getNewValue());
        			flag = true;
        			break;
        		}
        	}
        	if (!flag) {
        		temp3.add(obj);
        	}
        }       
        for (PropertyChangeObject pro : temp3) {
        	System.out.println("thongTin: " + pro.getPropertyName());
        	System.out.println("thongTin: " + pro.getNewValue());
        }
        return temp3;
	}

}
