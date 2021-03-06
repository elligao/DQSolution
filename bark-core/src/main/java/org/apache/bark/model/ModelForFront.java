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

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class ModelForFront {
	private String name;
	private int system;
	private String description;
	private int type;
	private Date createDate;
	private String status;
	private String assetName;
	private String owner;

	public ModelForFront()
	{
		;
	}




	public ModelForFront(String name, int system, String description, int type,
			Date createDate, String status, String assetName, String owner) {
		super();
		this.name = name;
		this.system = system;
		this.description = description;
		this.type = type;
		this.createDate = createDate;
		this.status = status;
		this.assetName = assetName;
		this.owner = owner;
	}

	public String getAssetName() {
		return assetName;
	}


	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getSystem() {
		return system;
	}


	public void setSystem(int system) {
		this.system = system;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}





	public String getOwner() {
		return owner;
	}




	public void setOwner(String owner) {
		this.owner = owner;
	}




	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}




}
