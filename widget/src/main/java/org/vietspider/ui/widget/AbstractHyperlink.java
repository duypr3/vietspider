/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.vietspider.ui.widget;

import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.accessibility.ACC;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.vietspider.ui.services.FormsResources;
import org.vietspider.ui.widget.action.HyperlinkEvent;
import org.vietspider.ui.widget.action.IHyperlinkListener;

/**
 * This is the base class for custom hyperlink widget. It is responsible for
 * processing mouse and keyboard events, and converting them into unified
 * hyperlink events. Subclasses are responsible for rendering the hyperlink in
 * the client area.
 * 
 * @since 3.0
 */
public abstract class AbstractHyperlink extends Canvas {

  private boolean hasFocus;

  private Vector listeners;

  private String tip;

  /**
   * Amount of the margin width around the hyperlink (default is 1).
   */
  protected int marginWidth = 1;

  /**
   * Amount of the margin height around the hyperlink (default is 1).
   */
  protected int marginHeight = 1;

  /**
   * Creates a new hyperlink in the provided parent.
   * 
   * @param parent
   *            the control parent
   * @param style
   *            the widget style
   */
  public AbstractHyperlink(Composite parent, int style) {
    super(parent, style);
    addListener(SWT.KeyDown, new Listener() {
      public void handleEvent(Event e) {
        if (e.character == '\r') {
          handleActivate(e);
        }
      }
    });
    addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent e) {
        paint(e);
      }
    });
    addListener(SWT.Traverse, new Listener() {
      public void handleEvent(Event e) {
        switch (e.detail) {
        case SWT.TRAVERSE_PAGE_NEXT:
        case SWT.TRAVERSE_PAGE_PREVIOUS:
        case SWT.TRAVERSE_ARROW_NEXT:
        case SWT.TRAVERSE_ARROW_PREVIOUS:
        case SWT.TRAVERSE_RETURN:
          e.doit = false;
          return;
        }
        e.doit = true;
      }
    });
    Listener listener = new Listener() {
      public void handleEvent(Event e) {
        switch (e.type) {
        case SWT.FocusIn:
          hasFocus = true;
          handleEnter(e);
          break;
        case SWT.FocusOut:
          hasFocus = false;
          handleExit(e);
          break;
        case SWT.DefaultSelection:
          handleActivate(e);
          break;
        case SWT.MouseEnter:
          setFont(UIDATA.FONT_8VB);//new SFont(getDisplay(), "Verdana", 8, SWT.BOLD));
          handleEnter(e);
          break;
        case SWT.MouseExit:
          setFont(UIDATA.FONT_8V);//new SFont(getDisplay(), "Verdana", 8, SWT.NORMAL));
          handleExit(e);
          break;
        case SWT.MouseUp:
          handleMouseUp(e);
          break;
        }
      }
    };
    addListener(SWT.MouseEnter, listener);
    addListener(SWT.MouseExit, listener);
    addListener(SWT.MouseUp, listener);
    addListener(SWT.FocusIn, listener);
    addListener(SWT.FocusOut, listener);
    setCursor(FormsResources.getHandCursor());
  }

  public String getTip(){
    return tip;
  }

  public void setTip(String t){
    tip = t;
  }

  /**
   * Adds the event listener to this hyperlink.
   * 
   * @param listener
   *            the event listener to add
   */
  @SuppressWarnings("unchecked")
  public void addHyperlinkListener(IHyperlinkListener listener) {
    if (listeners == null) listeners = new Vector();
    if (!listeners.contains(listener)) listeners.add(listener);
  }

  /**
   * Removes the event listener from this hyperlink.
   * 
   * @param listener
   *            the event listener to remove
   */
  public void removeHyperlinkListener(IHyperlinkListener listener) {
    if (listeners == null)
      return;
    listeners.remove(listener);
  }

  /**
   * Returns the selection state of the control. When focus is gained, the
   * state will be <samp>true </samp>; it will switch to <samp>false </samp>
   * when the control looses focus.
   * 
   * @return <code>true</code> if the widget has focus, <code>false</code>
   *         otherwise.
   */
  public boolean getSelection() {
    return hasFocus;
  }

  /**
   * Called when hyperlink is entered. Subclasses that override this method
   * must call 'super'.
   */
  protected void handleEnter(Event e) {
    redraw();
    if (listeners == null) return;
    int size = listeners.size();
    HyperlinkEvent he = new HyperlinkEvent(this, getHref(), getText(), tip ,e.stateMask);
    for (int i = 0; i < size; i++) {
      IHyperlinkListener listener = (IHyperlinkListener) listeners.get(i);
      listener.linkEntered(he);
    }
  }

  /**
   * Called when hyperlink is exited. Subclasses that override this method
   * must call 'super'.
   */
  protected void handleExit(Event e) {
    redraw();
    if (listeners == null)	return;
    int size = listeners.size();
    HyperlinkEvent he = new HyperlinkEvent(this, getHref(), getText(), tip ,e.stateMask);
    for (int i = 0; i < size; i++) {
      IHyperlinkListener listener = (IHyperlinkListener) listeners.get(i);
      listener.linkExited(he);
    }
  }

  /**
   * Called when hyperlink has been activated. Subclasses that override this
   * method must call 'super'.
   */
  protected void handleActivate(Event e) {
    getAccessible().setFocus(ACC.CHILDID_SELF);
    if (listeners == null)
      return;
    int size = listeners.size();
    setCursor(FormsResources.getBusyCursor());
    HyperlinkEvent he = new HyperlinkEvent(this, getHref(), getText(), tip ,e.stateMask);
    for (int i = 0; i < size; i++) {
      IHyperlinkListener listener = (IHyperlinkListener) listeners.get(i);
      listener.linkActivated(he);
    }
    if (!isDisposed())
      setCursor(FormsResources.getHandCursor());
  }

  /**
   * Sets the object associated with this hyperlink. Concrete implementation
   * of this class can use if to store text, URLs or model objects that need
   * to be processed on hyperlink events.
   * 
   * @param href
   *            the hyperlink object reference
   */
  public void setHref(Object href) {
    setData("href", href); //$NON-NLS-1$
  }

  /**
   * Returns the object associated with this hyperlink.
   * 
   * @see #setHref
   * @return the hyperlink object reference
   */
  public Object getHref() {
    return getData("href"); //$NON-NLS-1$
  }

  /**
   * Returns the textual representation of this hyperlink suitable for showing
   * in tool tips or on the status line.
   * 
   * @return the hyperlink text
   */
  public String getText() {
    return getToolTipText();
  }

  /**
   * Paints the hyperlink as a reaction to the provided paint event.
   * 
   * @param gc
   *            graphic context
   */
  protected abstract void paintHyperlink(GC gc);

  /**
   * Paints the control as a reaction to the provided paint event.
   * 
   * @param e
   *            the paint event
   */
  protected void paint(PaintEvent e) {
    GC gc = e.gc;
    Rectangle clientArea = getClientArea();
    if (clientArea.width == 0 || clientArea.height == 0)return;
    Image buffer = new Image(getDisplay(), clientArea.width, clientArea.height);
    buffer.setBackground(getBackground());
    GC bufferGC = new GC(buffer, gc.getStyle());
    bufferGC.setBackground(getBackground());
    bufferGC.fillRectangle(0, 0, clientArea.width, clientArea.height);
    paintHyperlink(bufferGC);		
    gc.drawImage(buffer, 0, 0);
    bufferGC.dispose();
    buffer.dispose();
  }

  private void handleMouseUp(Event e) {
    if (e.button != 1)
      return;
    Point size = getSize();
    // Filter out mouse up events outside
    // the link. This can happen when mouse is
    // clicked, dragged outside the link, then
    // released.
    if (e.x < 0)
      return;
    if (e.y < 0)
      return;
    if (e.x >= size.x)
      return;
    if (e.y >= size.y)
      return;
    handleActivate(e);
  }
}
