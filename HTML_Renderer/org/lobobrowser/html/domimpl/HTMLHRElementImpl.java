package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.w3c.HTMLHRElement;

public class HTMLHRElementImpl extends HTMLAbstractUIElement implements
		HTMLHRElement {
	public HTMLHRElementImpl(String name) {
		super(name);
	}

	public String getAlign() {
		return this.getAttribute("align");
	}

	public boolean getNoShade() {
		return "noshade".equalsIgnoreCase(this.getAttribute("noshade"));
	}

	public String getSize() {
		return this.getAttribute("size");
	}

	public String getWidth() {
		return this.getAttribute("width");
	}

	public void setAlign(String align) {
		this.setAttribute("align", align);
	}

	public void setNoShade(boolean noShade) {
		this.setAttribute("noshade", noShade ? "noshade" : null);
	}

	public void setSize(String size) {
		this.setAttribute("size", size);
	}

	public void setWidth(String width) {
		this.setAttribute("width", width);
	}

	@Override
	public String getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(String color) {
		// TODO Auto-generated method stub
		
	}
}