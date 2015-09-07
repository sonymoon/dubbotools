package com.lvtu.copydubbo;

import java.util.ArrayList;
import java.util.List;

public class FilterRule {

	private List<String> blackLists;

	public FilterRule() {
		this.blackLists = new ArrayList<String>();
	}

	public void setBlackListAppNames(String[] blackListAppNames) {
		this.blackLists = new ArrayList<String>();
		for (String string : blackListAppNames) {
			this.blackLists.add(string);
		}
	}

	public void addBlackLists(String str) {
		 this.blackLists.add(str);
	}

	public boolean isInBlackLists(String str) {
		return this.blackLists.contains(str);
	}
}
