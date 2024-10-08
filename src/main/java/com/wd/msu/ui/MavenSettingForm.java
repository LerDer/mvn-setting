package com.wd.msu.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.wd.msu.utils.CommonUtil;
import com.wd.msu.utils.FileChooseUtil;
import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class MavenSettingForm extends JDialog {

	private Project project;

	private JPanel contentPane;
	private JButton change;
	private JButton cancel;
	private JTextField setPath;
	private JTextField confPath;
	private JButton setPathChoose;
	private JButton confPathChoose;
	private JList<File> fileList;

	public MavenSettingForm(Project project) {
		setContentPane(contentPane);
		setResizable(false);
		setModal(true);
		this.project = project;
		getRootPane().setDefaultButton(change);

		change.addActionListener(e -> onOK());

		cancel.addActionListener(e -> onCancel());
		setPathChoose.addActionListener(e -> onSelectSet());
		confPathChoose.addActionListener(e -> onSelectConf());

		Toolkit.getDefaultToolkit().addAWTEventListener(e -> {
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				KeyEvent evt = (KeyEvent) e;
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
					onCancel();
				}
			}
		}, AWTEvent.KEY_EVENT_MASK);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				onCancel();
			}
		});

		String setPath = CommonUtil.getSetPath();
		String confPath = CommonUtil.getConfPath();
		if (StringUtils.isNotBlank(setPath)) {
			this.setPath.setText(setPath);
			this.confPath.setText(confPath);
			String setPath1 = this.setPath.getText();
			File setFile = new File(setPath1);
			File[] files = setFile.listFiles();
			Vector vector = new Vector(Arrays.asList(files));
			this.fileList.setListData(vector);
		}

		// call onCancel() on ESCAPE
		contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
	}

	private void onSelectConf() {
		FileChooseUtil uiComponentFacade = FileChooseUtil.getInstance(project);
		VirtualFile baseDir = null;
		if (project != null) {
			baseDir = project.getBaseDir();
		}
		String existPath = this.confPath.getText();
		if (StringUtils.isNotBlank(existPath)) {
			VirtualFileManager instance = VirtualFileManager.getInstance();
			baseDir = instance.findFileByUrl("file://" + existPath);
		}
		final VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("选择Maven conf文件夹路径", baseDir, baseDir);
		if (vf == null) {
			return;
		}
		if (!vf.isDirectory()) {
			JOptionPane.showMessageDialog(this.contentPane, "请选择文件夹！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.confPath.setText(vf.getPath());
	}

	private void onSelectSet() {
		FileChooseUtil uiComponentFacade = FileChooseUtil.getInstance(project);
		VirtualFile baseDir = null;
		if (project != null) {
			baseDir = project.getBaseDir();
		}
		String existPath = this.setPath.getText();
		if (StringUtils.isNotBlank(existPath)) {
			VirtualFileManager instance = VirtualFileManager.getInstance();
			baseDir = instance.findFileByUrl("file://" + existPath);
		}
		VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("选择settings.xml文件存储路径", baseDir, baseDir);
		if (vf == null) {
			return;
		}
		if (!vf.isDirectory()) {
			JOptionPane.showMessageDialog(this.contentPane, "请选择文件夹！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.setPath.setText(vf.getPath());

		String setPath1 = setPath.getText();
		File setFile = new File(setPath1);
		File[] files = setFile.listFiles();
		Vector vector = new Vector(Arrays.asList(files));
		this.fileList.setListData(vector);
	}

	private void onOK() {
		CommonUtil.initConfig(setPath, confPath);
		File selectedValue = this.fileList.getSelectedValue();
		String name = selectedValue.getName();
		if (!name.endsWith(".xml")) {
			JOptionPane.showMessageDialog(this.contentPane, "请选择xml类型文件！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		String preConf = this.confPath.getText() + File.separator + "settings.xml";
		File prfConfFile = new File(preConf);
		if (prfConfFile.exists()) {
			prfConfFile.delete();
		}
		File nowConfFile = new File(preConf);
		try {
			FileUtils.copyFile(selectedValue, nowConfFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(this.contentPane, "修改成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
	}

}
