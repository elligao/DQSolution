/*
	Copyright (c) 2016 eBay Software Foundation.
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

	    http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
 */
package org.apache.bark.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class SystemSubscribeItem {
	String system;
	boolean selectAll;
	List<String> dataassets;

	public SystemSubscribeItem()
	{
		dataassets = new ArrayList<String>();
	}

	public SystemSubscribeItem(String system)
	{
		this.system = system;
		dataassets = new ArrayList<String>();
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public boolean isSelectAll() {
		return selectAll;
	}

	public void setSelectAll(boolean selectAll) {
		this.selectAll = selectAll;
	}

	public List<String> getDataassets() {
		return dataassets;
	}

	public void setDataassets(List<String> dataassets) {
		this.dataassets = dataassets;
	}




}
