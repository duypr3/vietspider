/***************************************************************************
 * Copyright 2001-2007 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package TestSWT;

/** 
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * Nov 11, 2007  
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class Snippet51 {

public static void main (String [] args) {
  Display display = new Display ();
  Shell shell = new Shell (display);
  Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
  table.setSize (200, 200);
  for (int i=0; i<128; i++) {
    TableItem item = new TableItem (table, SWT.NONE);
    item.setText ("Item " + i);
  }
  table.setTopIndex (5);
  shell.pack ();
  shell.open ();
  while (!shell.isDisposed ()) {
    if (!display.readAndDispatch ()) display.sleep ();
  }
  display.dispose ();
}
} 