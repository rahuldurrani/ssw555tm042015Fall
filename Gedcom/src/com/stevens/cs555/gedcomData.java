/**
* The gedcomData class defines a data structure to 
* hold GEDCOM data.
*
* @author  Durrani Rahul
* @version 1.0
* @since   2015-09-14 
* @course  CS555
*/
package com.stevens.cs555;

public class gedcomData {

	private String level;
	private String tag;
	private String argument;
	private String xrefid;
	public String getXrefid() {
		return xrefid;
	}
	public void setXrefid(String xrefid) {
		this.xrefid = xrefid;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getArgument() {
		return argument;
	}
	public void setArgument(String argument) {
		this.argument = argument;
	}
	
	
}
