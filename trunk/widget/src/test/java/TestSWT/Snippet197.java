/***************************************************************************
 * Copyright 2001-2007 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package TestSWT;

/** 
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * Dec 10, 2007  
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class Snippet197 {
  final static String longString = "The preferred size of a widget is the minimum size needed to show its content. In the case of a Composite, the preferred size is the smallest rectangle that contains all of its children. If children have been positioned by the application, the Composite computes its own preferred size based on the size and position of the children. If a Composite is using a layout class to position its children, it asks the Layout to compute the size of its clientArea, and then it adds in the trim to determine its preferred size.";
  public static void main(String[] args) {
    Display display = new Display();
    final Shell shell = new Shell(display);
    final TextLayout layout = new TextLayout(display);
    layout.setText(longString);
    Listener listener = new Listener() {
      public void handleEvent (Event event) {
        switch (event.type) {
        case SWT.Paint:
          layout.draw(event.gc, 10, 10);
          break;
        case SWT.Resize:
          layout.setWidth(shell.getSize().x - 20);
          break;
        }
      }
    };
    shell.addListener(SWT.Paint, listener);
    shell.addListener(SWT.Resize, listener);
    shell.setSize(300, 300);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }
}
