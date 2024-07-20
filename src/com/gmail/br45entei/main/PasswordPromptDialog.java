/*******************************************************************************
 * 
 * Copyright © 2023 Brian Reid (br45entei@gmail.com)
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 *******************************************************************************/
package com.gmail.br45entei.main;

import java.beans.Beans;
import java.util.Arrays;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/** @author Brian Reid &lt;br45entei&#064;gmail.com&gt; */
public class PasswordPromptDialog {
	
	/** @param args Program command line arguments
	 * @wbp.parser.entryPoint */
	public static void main(String[] args) {
		Display display = Display.getDefault();
		PasswordPromptDialog dialog = new PasswordPromptDialog(display, "Password Prompt Dialog", "Enter a sample username and password.\nThe credentials will be printed to the standard output stream after clicking OK.");
		String[] credentials = dialog.open();
		if(credentials != null) {
			System.out.println("Credentials:");
			System.out.println("Username: ".concat(credentials[0] == null ? "null" : credentials[0]));
			System.out.println("Password: ".concat(credentials[1] == null ? "null" : credentials[1]));
		} else {
			System.out.println("The dialog was cancelled or closed.");
		}
	}
	
	protected Display display;
	protected Shell shell;
	protected Text txtUsername;
	protected Text txtPassword;
	
	protected final String[] credentials = new String[2];
	
	/** @param display The Display */
	public PasswordPromptDialog(Display display, String title, String message) {
		this.createContents(display, null, title, message);
	}
	
	/** @param parent The parent {@link Shell} */
	public PasswordPromptDialog(Shell parent, String title, String message) {
		this.createContents(null, parent, title, message);
	}
	
	private void createContents(Display display, Shell parent, String title, String message) {
		final Shell shell;
		if(Beans.isDesignTime()) {
			display = Display.getDefault();
			shell = new Shell(display, SWT.DIALOG_TRIM);
		} else {
			if(display != null) {
				this.display = display;
				shell = new Shell(display, SWT.DIALOG_TRIM);
			} else {
				this.display = parent.getDisplay();
				shell = new Shell(parent, SWT.DIALOG_TRIM);
			}
		}
		this.shell = shell;
		this.shell.setText(title == null ? "401 Unauthorized (Login Required)" : title);
		this.shell.setSize(450, 195);
		
		Label lblMessage = new Label(shell, SWT.BORDER | SWT.WRAP);
		lblMessage.setBounds(10, 10, 414, 48);
		lblMessage.setText(message == null ? "Please enter your username and password, then click 'Submit'." : message);
		
		Label lblUsername = new Label(shell, SWT.NONE);
		lblUsername.setBounds(10, 67, 55, 15);
		lblUsername.setText("Username:");
		
		this.txtUsername = new Text(shell, SWT.BORDER);
		this.txtUsername.setBounds(71, 64, 353, 21);
		this.txtUsername.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				PasswordPromptDialog.this.credentials[0] = PasswordPromptDialog.this.txtUsername.getText();
				PasswordPromptDialog.this.credentials[1] = PasswordPromptDialog.this.txtPassword.getText();
			}
		});;
		
		Label lblPassword = new Label(shell, SWT.NONE);
		lblPassword.setBounds(10, 94, 55, 15);
		lblPassword.setText("Password:");
		
		this.txtPassword = new Text(shell, SWT.BORDER | SWT.PASSWORD);
		this.txtPassword.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				PasswordPromptDialog.this.credentials[0] = PasswordPromptDialog.this.txtUsername.getText();
				PasswordPromptDialog.this.credentials[1] = PasswordPromptDialog.this.txtPassword.getText();
			}
		});
		this.txtPassword.setBounds(71, 91, 353, 21);
		
		Button btnSubmit = new Button(shell, SWT.NONE);
		btnSubmit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		btnSubmit.setBounds(10, 118, 204, 25);
		btnSubmit.setText("Submit");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Arrays.fill(PasswordPromptDialog.this.credentials, null);
				shell.close();
			}
		});
		btnCancel.setBounds(220, 118, 204, 25);
		btnCancel.setText("Cancel");
		
	}
	
	public String[] open() {
		this.shell.open();
		this.shell.layout();
		
		while(!this.shell.isDisposed()) {
			while(this.display.readAndDispatch());
			this.display.sleep();
		}
		
		return this.credentials;
	}
}
