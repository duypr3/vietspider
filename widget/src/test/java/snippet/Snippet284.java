/***************************************************************************
 * Copyright 2001-2008 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package snippet;

/** 
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * Feb 20, 2008  
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.URLTransfer;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Snippet284 {

  public static void main (String [] args) {

    Display display = new Display ();
    final Shell shell = new Shell (display);
    shell.setText("URLTransfer");
    shell.setLayout(new FillLayout());
    final Label label1 = new Label (shell, SWT.BORDER);
    label1.setText ("http://www.eclipse.org");
    final Label label2 = new Label (shell, SWT.BORDER);
    setDragSource (label1);
    setDropTarget (label2);
    shell.setSize(600, 300);
    shell.open ();
    while (!shell.isDisposed ()) {
      if (!display.readAndDispatch ()) display.sleep ();
    }
    display.dispose ();
  }
  
  public static void setDragSource (final Label label) {
    int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
    final DragSource source = new DragSource (label, operations);
    source.setTransfer(new Transfer[] {URLTransfer.getInstance()});
    source.addDragListener (new DragSourceListener () {
      public void dragStart(DragSourceEvent e) {
      }
      public void dragSetData(DragSourceEvent e) {
        e.data = label.getText();
      }
      public void dragFinished(DragSourceEvent event) {
      }
    });
  }

  public static void setDropTarget (final Label label) {
    int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
    DropTarget target = new DropTarget(label, operations);
    target.setTransfer(new Transfer[] {URLTransfer.getInstance()});
    target.addDropListener (new DropTargetAdapter() {
      public void dragEnter(DropTargetEvent e) {
        if (e.detail == DND.DROP_NONE)
          e.detail = DND.DROP_LINK;
      }
      public void dragOperationChanged(DropTargetEvent e) {
        if (e.detail == DND.DROP_NONE) e.detail = DND.DROP_LINK;
      }
      public void drop(DropTargetEvent event) {
        if (event.data == null) {
          event.detail = DND.DROP_NONE;
          return;
        }
        label.setText(((String) event.data));
      }
    });
  }
}
