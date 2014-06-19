package com.example.gimagesearch;

import java.io.Serializable;

public class FilterSettings implements Serializable
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6031329807443369529L;
	private String size;
	private String color;
	private String type;
	private String site;

	public FilterSettings()
	{}

	public FilterSettings(String size, String color, String type, String site)
	{
		this.size = size;
		this.color = color;
		this.type = type;
		this.site = site;
	}
	
	public void setSize(String size) 
	{
		this.size = size;
	}
	
	public String getSize() 
	{
		return size;
	}

	public void setColor(String color) 
	{
		this.color = color;
	}
	
	public String getColor() 
	{
		return color;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public String getType() 
	{
		return type;
	}
	
	public void setSite(String site) 
	{
		this.site = site;
	}
	
	public String getSite() 
	{
		return site;
	}
}
