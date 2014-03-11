/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Chriss Gross (schtoo@schtoo.com) - fix for 61670
 *******************************************************************************/
package org.vietspider.ui.services;

import java.text.BreakIterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.ScrollBar;

public class FormUtil {
	public static final String PLUGIN_ID = "org.eclipse.ui.forms"; //$NON-NLS-1$

	static final int H_SCROLL_INCREMENT = 5;

	static final int V_SCROLL_INCREMENT = 64;

	public static final String DEBUG = PLUGIN_ID + "/debug"; //$NON-NLS-1$

	public static final String DEBUG_TEXT = DEBUG + "/text"; //$NON-NLS-1$
	public static final String DEBUG_TEXTSIZE = DEBUG + "/textsize"; //$NON-NLS-1$

	public static final String DEBUG_FOCUS = DEBUG + "/focus"; //$NON-NLS-1$

	public static final String FOCUS_SCROLLING = "focusScrolling"; //$NON-NLS-1$

  public static int computeMinimumWidth(GC gc, String text) {
		BreakIterator wb = BreakIterator.getWordInstance();
		wb.setText(text);
		int last = 0;

		int width = 0;

		for (int loc = wb.first(); loc != BreakIterator.DONE; loc = wb.next()) {
			String word = text.substring(last, loc);
			Point extent = gc.textExtent(word);
			width = Math.max(width, extent.x);
			last = loc;
		}
		String lastWord = text.substring(last);
		Point extent = gc.textExtent(lastWord);
		width = Math.max(width, extent.x);
		return width;
	}

	public static Point computeWrapSize(GC gc, String text, int wHint) {
		BreakIterator wb = BreakIterator.getWordInstance();
		wb.setText(text);
		FontMetrics fm = gc.getFontMetrics();
		int lineHeight = fm.getHeight();

		int saved = 0;
		int last = 0;
		int height = lineHeight;
		int maxWidth = 0;

		for (int loc = wb.first(); loc != BreakIterator.DONE; loc = wb.next()) {
			String word = text.substring(saved, loc);
			Point extent = gc.textExtent(word);
			if (extent.x > wHint) {
				// overflow
				saved = last;
				height += extent.y;
			} else {
				maxWidth = Math.max(maxWidth, extent.x);
			}
			last = loc;
		}
		return new Point(maxWidth, height);
	}

	public static void paintWrapText(GC gc, String text, Rectangle bounds) {
		paintWrapText(gc, text, bounds, false);
	}

	public static void paintWrapText(GC gc, String text, Rectangle bounds,
			boolean underline) {
		BreakIterator wb = BreakIterator.getWordInstance();
		wb.setText(text);
		FontMetrics fm = gc.getFontMetrics();
		int lineHeight = fm.getHeight();
		int descent = fm.getDescent();

		int saved = 0;
		int last = 0;
		int y = bounds.y;
		int width = bounds.width;

		for (int loc = wb.first(); loc != BreakIterator.DONE; loc = wb.next()) {
			String line = text.substring(saved, loc);
			Point extent = gc.textExtent(line);

			if (extent.x > width) {
				// overflow
				String prevLine = text.substring(saved, last);
				gc.drawText(prevLine, bounds.x, y, true);
				if (underline) {
					Point prevExtent = gc.textExtent(prevLine);
					int lineY = y + lineHeight - descent + 1;
					gc
							.drawLine(bounds.x, lineY, bounds.x + prevExtent.x,
									lineY);
				}

				saved = last;
				y += lineHeight;
			}
			last = loc;
		}
		// paint the last line
		String lastLine = text.substring(saved, last);
		gc.drawText(lastLine, bounds.x, y, true);
		if (underline) {
			int lineY = y + lineHeight - descent + 1;
			Point lastExtent = gc.textExtent(lastLine);
			gc.drawLine(bounds.x, lineY, bounds.x + lastExtent.x, lineY);
		}
	}

	public static ScrolledComposite getScrolledComposite(Control c) {
		Composite parent = c.getParent();

		while (parent != null) {
			if (parent instanceof ScrolledComposite) {
				return (ScrolledComposite) parent;
			}
			parent = parent.getParent();
		}
		return null;
	}

	public static void ensureVisible(Control c) {
		ScrolledComposite scomp = getScrolledComposite(c);
		if (scomp != null) {
			Object data = scomp.getData(FOCUS_SCROLLING);
			if (data == null || !data.equals(Boolean.FALSE))
				FormUtil.ensureVisible(scomp, c);
		}
	}

	public static void ensureVisible(ScrolledComposite scomp, Control control) {
		Point controlSize = control.getSize();
		Point controlOrigin = getControlLocation(scomp, control);
		ensureVisible(scomp, controlOrigin, controlSize);
	}

	public static void ensureVisible(ScrolledComposite scomp,
			Point controlOrigin, Point controlSize) {
		Rectangle area = scomp.getClientArea();
		Point scompOrigin = scomp.getOrigin();

		int x = scompOrigin.x;
		int y = scompOrigin.y;

		// horizontal right, but only if the control is smaller
		// than the client area
		if (controlSize.x < area.width
				&& (controlOrigin.x + controlSize.x > scompOrigin.x
						+ area.width)) {
			x = controlOrigin.x + controlSize.x - area.width;
		}
		// horizontal left - make sure the left edge of
		// the control is showing
		if (controlOrigin.x < x) {
			if (controlSize.x < area.width)
				x = controlOrigin.x + controlSize.x - area.width;
			else
				x = controlOrigin.x;
		}
		// vertical bottom
		if (controlSize.y < area.height
				&& (controlOrigin.y + controlSize.y > scompOrigin.y
						+ area.height)) {
			y = controlOrigin.y + controlSize.y - area.height;
		}
		// vertical top - make sure the top of
		// the control is showing
		if (controlOrigin.y < y) {
			if (controlSize.y < area.height)
				y = controlOrigin.y + controlSize.y - area.height;
			else
				y = controlOrigin.y;
		}

		if (scompOrigin.x != x || scompOrigin.y != y) {
			// scroll to reveal
			scomp.setOrigin(x, y);
		}
	}

	public static void ensureVisible(ScrolledComposite scomp, Control control,
			MouseEvent e) {
		Point controlOrigin = getControlLocation(scomp, control);
		int rX = controlOrigin.x + e.x;
		int rY = controlOrigin.y + e.y;
		Rectangle area = scomp.getClientArea();
		Point scompOrigin = scomp.getOrigin();

		int x = scompOrigin.x;
		int y = scompOrigin.y;
		// System.out.println("Ensure: area="+area+", origin="+scompOrigin+",
		// cloc="+controlOrigin+", csize="+controlSize+", x="+x+", y="+y);

		// horizontal right
		if (rX > scompOrigin.x + area.width) {
			x = rX - area.width;
		}
		// horizontal left
		else if (rX < x) {
			x = rX;
		}
		// vertical bottom
		if (rY > scompOrigin.y + area.height) {
			y = rY - area.height;
		}
		// vertical top
		else if (rY < y) {
			y = rY;
		}

		if (scompOrigin.x != x || scompOrigin.y != y) {
			// scroll to reveal
			scomp.setOrigin(x, y);
		}
	}

	public static Point getControlLocation(ScrolledComposite scomp,
			Control control) {
		int x = 0;
		int y = 0;
		Control content = scomp.getContent();
		Control currentControl = control;
		for (;;) {
			if (currentControl == content)
				break;
			Point location = currentControl.getLocation();
			// if (location.x > 0)
			// x += location.x;
			// if (location.y > 0)
			// y += location.y;
			x += location.x;
			y += location.y;
			currentControl = currentControl.getParent();
		}
		return new Point(x, y);
	}

	static void scrollVertical(ScrolledComposite scomp, boolean up) {
		scroll(scomp, 0, up ? -V_SCROLL_INCREMENT : V_SCROLL_INCREMENT);
	}

	static void scrollHorizontal(ScrolledComposite scomp, boolean left) {
		scroll(scomp, left ? -H_SCROLL_INCREMENT : H_SCROLL_INCREMENT, 0);
	}

	static void scrollPage(ScrolledComposite scomp, boolean up) {
		Rectangle clientArea = scomp.getClientArea();
		int increment = up ? -clientArea.height : clientArea.height;
		scroll(scomp, 0, increment);
	}

	static void scroll(ScrolledComposite scomp, int xoffset, int yoffset) {
		Point origin = scomp.getOrigin();
		Point contentSize = scomp.getContent().getSize();
		int xorigin = origin.x + xoffset;
		int yorigin = origin.y + yoffset;
		xorigin = Math.max(xorigin, 0);
		xorigin = Math.min(xorigin, contentSize.x - 1);
		yorigin = Math.max(yorigin, 0);
		yorigin = Math.min(yorigin, contentSize.y - 1);
		scomp.setOrigin(xorigin, yorigin);
	}

	public static void updatePageIncrement(ScrolledComposite scomp) {
		ScrollBar vbar = scomp.getVerticalBar();
		if (vbar != null) {
			Rectangle clientArea = scomp.getClientArea();
			int increment = clientArea.height - 5;
			vbar.setPageIncrement(increment);
		}
	}

	public static void processKey(int keyCode, Control c) {
		ScrolledComposite scomp = FormUtil.getScrolledComposite(c);
		if (scomp != null) {
			if (c instanceof Combo)
				return;
			switch (keyCode) {
			case SWT.ARROW_DOWN:
				if (scomp.getData("novarrows") == null) //$NON-NLS-1$
					FormUtil.scrollVertical(scomp, false);
				break;
			case SWT.ARROW_UP:
				if (scomp.getData("novarrows") == null) //$NON-NLS-1$
					FormUtil.scrollVertical(scomp, true);
				break;
			case SWT.ARROW_LEFT:
				FormUtil.scrollHorizontal(scomp, true);
				break;
			case SWT.ARROW_RIGHT:
				FormUtil.scrollHorizontal(scomp, false);
				break;
			case SWT.PAGE_UP:
				FormUtil.scrollPage(scomp, true);
				break;
			case SWT.PAGE_DOWN:
				FormUtil.scrollPage(scomp, false);
				break;
			}
		}
	}
	public static Image createAlphaMashImage(Device device, Image srcImage) {
		Rectangle bounds = srcImage.getBounds();
		int alpha = 0;
		int calpha = 0;
		ImageData data = srcImage.getImageData();
		for (int i = 0; i < bounds.height; i++) {
			// scan line
			alpha = calpha;
			for (int j = 0; j < bounds.width; j++) {
				// column
				data.setAlpha(j, i, alpha);
				alpha = alpha == 255 ? 0 : 255;
			}
			calpha = calpha == 255 ? 0 : 255;
		}
		return new Image(device, data);
	}

	public static Font createBoldFont(Display display, Font regularFont) {
		FontData[] fontDatas = regularFont.getFontData();
		for (int i = 0; i < fontDatas.length; i++) {
			fontDatas[i].setStyle(fontDatas[i].getStyle() | SWT.BOLD);
		}
		return new Font(display, fontDatas);
	}

	public static boolean mnemonicMatch(String text, char key) {
		char mnemonic = findMnemonic(text);
		if (mnemonic == '\0')
			return false;
		return Character.toUpperCase(key) == Character.toUpperCase(mnemonic);
	}

	private static char findMnemonic(String string) {
		int index = 0;
		int length = string.length();
		do {
			while (index < length && string.charAt(index) != '&')
				index++;
			if (++index >= length)
				return '\0';
			if (string.charAt(index) != '&')
				return string.charAt(index);
			index++;
		} while (index < length);
		return '\0';
	}
	
	public static void setFocusScrollingEnabled(Control c, boolean enabled) {
		ScrolledComposite scomp = null;
		
		if (c instanceof ScrolledComposite)
			scomp = (ScrolledComposite)c;
		else
			scomp = getScrolledComposite(c);
		if (scomp!=null)
			scomp.setData(FormUtil.FOCUS_SCROLLING, enabled?null:Boolean.FALSE);
	}
}