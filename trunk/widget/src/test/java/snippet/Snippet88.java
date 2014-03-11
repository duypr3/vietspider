/***************************************************************************
 * Copyright 2001-2008 The VietSpider         All rights reserved.  		 *
 **************************************************************************/
package snippet;

/** 
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@yahoo.com
 * Mar 5, 2008  
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class Snippet88 {

  public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setLayout(new FillLayout());
    final Table table = new Table(shell, SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
    TableColumn column1 = new TableColumn(table, SWT.NONE);
    TableColumn column2 = new TableColumn(table, SWT.NONE);
    for (int i = 0; i < 10; i++) {
      TableItem item = new TableItem(table, SWT.NONE);
      item.setText(new String[] {"item " + i, "edit this value"});
    }
    column1.pack();
    column2.pack();

    final TableEditor editor = new TableEditor(table);
    //The editor must have the same size as the cell and must
    //not be any smaller than 50 pixels.
    editor.horizontalAlignment = SWT.LEFT;
    editor.grabHorizontal = true;
    editor.minimumWidth = 50;
    // editing the second column
    final int EDITABLECOLUMN = 1;

    table.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        // Clean up any previous editor control
        Control oldEditor = editor.getEditor();
        if (oldEditor != null) oldEditor.dispose();

        // Identify the selected row
        TableItem item = (TableItem)e.item;
        if (item == null) return;

        // The control that will be the editor must be a child of the Table
        Text newEditor = new Text(table, SWT.NONE);
        newEditor.setText(item.getText(EDITABLECOLUMN));
        newEditor.addModifyListener(new ModifyListener() {
          public void modifyText(ModifyEvent me) {
            Text text = (Text)editor.getEditor();
            editor.getItem().setText(EDITABLECOLUMN, text.getText());
          }
        });
        newEditor.selectAll();
        newEditor.setFocus();
        editor.setEditor(newEditor, item, EDITABLECOLUMN);
      }
    });
    shell.setSize(300, 300);
    shell.open();

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
    display.dispose();
  }

}