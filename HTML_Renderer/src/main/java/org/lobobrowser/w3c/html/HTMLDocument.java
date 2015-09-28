// Generated by esidl 0.2.1.

package org.lobobrowser.w3c.html;

import org.lobobrowser.html.js.Location;
import org.lobobrowser.html.xpath.XPathNSResolverImpl;
import org.lobobrowser.html.xpath.XPathResultImpl;
import org.lobobrowser.w3c.events.EventTarget;
import org.mozilla.javascript.Function;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.views.AbstractView;

public interface HTMLDocument extends Document, EventTarget {
	// HTMLDocument
	public Location getLocation();

	public void setLocation(String location);

	public String getURL();

	public String getDomain();

	public void setDomain(String domain);

	public String getReferrer();

	public String getCookie();

	public void setCookie(String cookie);

	public String getLastModified();

	public String getReadyState();

	public Object getElement(String name);

	public String getTitle();

	public void setTitle(String title);

	public String getDir();

	public void setDir(String dir);

	public HTMLElement getBody();

	public void setBody(HTMLElement body);

	public HTMLHeadElement getHead();

	public HTMLCollection getImages();

	public HTMLCollection getEmbeds();

	public HTMLCollection getPlugins();

	public HTMLCollection getLinks();

	public HTMLCollection getForms();

	public HTMLCollection getScripts();

	public NodeList getElementsByName(String elementName);

	public DOMElementMap getCssElementMap();

	public String getInnerHTML();

	public void setInnerHTML(String innerHTML);

	public void open();

	public HTMLDocument open(String type);

	public HTMLDocument open(String type, String replace);

	public void open(String url, String name, String features);

	public void open(String url, String name, String features, boolean replace);

	public void close();

	public AbstractView getDefaultView();

	public Element getActiveElement();

	public boolean hasFocus();

	public String getDesignMode();

	public void setDesignMode(String designMode);

	public boolean execCommand(String commandId);

	public boolean execCommand(String commandId, boolean showUI);

	public boolean execCommand(String commandId, boolean showUI, String value);

	public boolean queryCommandEnabled(String commandId);

	public boolean queryCommandIndeterm(String commandId);

	public boolean queryCommandState(String commandId);

	public boolean queryCommandSupported(String commandId);

	public String queryCommandValue(String commandId);

	public HTMLCollection getCommands();

	public Function getOnabort();

	public void setOnabort(Function onabort);

	public Function getOnblur();

	public void setOnblur(Function onblur);

	public Function getOncanplay();

	public void setOncanplay(Function oncanplay);

	public Function getOncanplaythrough();

	public void setOncanplaythrough(Function oncanplaythrough);

	public Function getOnchange();

	public void setOnchange(Function onchange);

	public Function getOnclick();

	public void setOnclick(Function onclick);

	public Function getOncontextmenu();

	public void setOncontextmenu(Function oncontextmenu);

	public Function getOncuechange();

	public void setOncuechange(Function oncuechange);

	public Function getOndblclick();

	public void setOndblclick(Function ondblclick);

	public Function getOndrag();

	public void setOndrag(Function ondrag);

	public Function getOndragend();

	public void setOndragend(Function ondragend);

	public Function getOndragenter();

	public void setOndragenter(Function ondragenter);

	public Function getOndragleave();

	public void setOndragleave(Function ondragleave);

	public Function getOndragover();

	public void setOndragover(Function ondragover);

	public Function getOndragstart();

	public void setOndragstart(Function ondragstart);

	public Function getOndrop();

	public void setOndrop(Function ondrop);

	public Function getOndurationchange();

	public void setOndurationchange(Function ondurationchange);

	public Function getOnemptied();

	public void setOnemptied(Function onemptied);

	public Function getOnended();

	public void setOnended(Function onended);

	public Function getOnerror();

	public void setOnerror(Function onerror);

	public Function getOnfocus();

	public void setOnfocus(Function onfocus);

	public Function getOninput();

	public void setOninput(Function oninput);

	public Function getOninvalid();

	public void setOninvalid(Function oninvalid);

	public Function getOnkeydown();

	public void setOnkeydown(Function onkeydown);

	public Function getOnkeypress();

	public void setOnkeypress(Function onkeypress);

	public Function getOnkeyup();

	public void setOnkeyup(Function onkeyup);

	public Function getOnload();

	public void setOnload(Function onload);

	public Function getOnloadeddata();

	public void setOnloadeddata(Function onloadeddata);

	public Function getOnloadedmetadata();

	public void setOnloadedmetadata(Function onloadedmetadata);

	public Function getOnloadstart();

	public void setOnloadstart(Function onloadstart);

	public Function getOnmousedown();

	public void setOnmousedown(Function onmousedown);

	public Function getOnmousemove();

	public void setOnmousemove(Function onmousemove);

	public Function getOnmouseout();

	public void setOnmouseout(Function onmouseout);

	public Function getOnmouseover();

	public void setOnmouseover(Function onmouseover);

	public Function getOnmouseup();

	public void setOnmouseup(Function onmouseup);

	public Function getOnmousewheel();

	public void setOnmousewheel(Function onmousewheel);

	public Function getOnpause();

	public void setOnpause(Function onpause);

	public Function getOnplay();

	public void setOnplay(Function onplay);

	public Function getOnplaying();

	public void setOnplaying(Function onplaying);

	public Function getOnprogress();

	public void setOnprogress(Function onprogress);

	public Function getOnratechange();

	public void setOnratechange(Function onratechange);

	public Function getOnreadystatechange();

	public void setOnreadystatechange(Function onreadystatechange);

	public Function getOnreset();

	public void setOnreset(Function onreset);

	public Function getOnscroll();

	public void setOnscroll(Function onscroll);

	public Function getOnseeked();

	public void setOnseeked(Function onseeked);

	public Function getOnseeking();

	public void setOnseeking(Function onseeking);

	public Function getOnselect();

	public void setOnselect(Function onselect);

	public Function getOnshow();

	public void setOnshow(Function onshow);

	public Function getOnstalled();

	public void setOnstalled(Function onstalled);

	public Function getOnsubmit();

	public void setOnsubmit(Function onsubmit);

	public Function getOnsuspend();

	public void setOnsuspend(Function onsuspend);

	public Function getOntimeupdate();

	public void setOntimeupdate(Function ontimeupdate);

	public Function getOnvolumechange();

	public void setOnvolumechange(Function onvolumechange);

	public Function getOnwaiting();

	public void setOnwaiting(Function onwaiting);

	// HTMLDocument-34
	public String getFgColor();

	public void setFgColor(String fgColor);

	public String getBgColor();

	public void setBgColor(String bgColor);

	public String getLinkColor();

	public void setLinkColor(String linkColor);

	public String getVlinkColor();

	public void setVlinkColor(String vlinkColor);

	public String getAlinkColor();

	public void setAlinkColor(String alinkColor);

	public HTMLCollection getAnchors();

	public HTMLCollection getApplets();

	public void clear();

	public HTMLAllCollection getAll();

	@Override
	Node renameNode(Node n, String namespaceURI, String qualifiedName);

	@Override
	CDATASection createCDATASection(String data) throws DOMException;

	@Override
	Attr createAttributeNS(String namespaceURI, String qualifiedName) throws DOMException;

	XPathResultImpl evaluate(String expression, HTMLElement contextNode, XPathNSResolverImpl resolver, Short type,
			Object result);

	void addEventListener(String script, Function function);

	void removeEventListener(String script, Function function, boolean bool);

	void addEventListener(String script, Function function, boolean bool);

	void removeEventListener(String script, Function function);

	NodeList querySelectorAll(String selectors);

	Element querySelector(String selectors);

	NodeList getElementsByClassName(String classNames);

	String getDefaultCharset();

	String getCharacterSet();

	String getCompatMode();

	void writeln(String text);

	void write(String text);
}