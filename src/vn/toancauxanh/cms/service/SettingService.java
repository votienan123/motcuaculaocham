package vn.toancauxanh.cms.service;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.annotation.Command;

import vn.toancauxanh.model.Setting;
import vn.toancauxanh.service.BasicService;

public class SettingService extends BasicService<Setting> {
	Setting setting = new Setting();

	private String filepath = "";

	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Command
	public void saveSetting() {
		Setting setting1 = getSetting();
		setting1.save();
	}

	
	public Setting getSetting() {
		Setting st = find(Setting.class).fetchFirst();
		if (setting != null) {
			setting = st;
		}
		return setting;
	}
	
	public List<Setting> getSettings() {
		List<Setting> settings = new ArrayList<Setting>();
		Setting setting = find(Setting.class).fetchFirst();
		if (setting != null) {
			settings.add(setting);
		}
		return settings;
	}
}
