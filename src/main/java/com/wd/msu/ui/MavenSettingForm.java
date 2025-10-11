package com.wd.msu.ui;

import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectUtil;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.wd.msu.icon.PluginIcons;
import com.wd.msu.utils.CommonUtil;
import com.wd.msu.utils.FileChooseUtil;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author lww
 * @date 2025-10-11 17:23
 */
public class MavenSettingForm extends DialogWrapper {

	private JPanel rootPanel;
	private JLabel settingLabel;
	private JLabel confLabel;
	private JTextField setPath;
	private JTextField confPath;
	private JButton setPathChoose;
	private JButton fileLocation1;
	private JButton confPathChoose;
	private JButton fileLocation2;
	private JList<File> fileList;
	private JButton mvnSetting;
	private JButton cancel;
	private JButton change;
	private Project project;

	public MavenSettingForm(Project project) {
		super(project);
		this.project = project;
		this.setTitle("setting.xml 管理");
		setModal(true);
		setResizable(false);
		init();
		change.setIcon(PluginIcons.success);
		change.addActionListener(e -> onOK());

		cancel.setIcon(PluginIcons.testFailed);
		cancel.addActionListener(e -> dispose());

		setPathChoose.setIcon(PluginIcons.projectStructure);
		setPathChoose.addActionListener(e -> onSelectSet());

		confPathChoose.setIcon(PluginIcons.projectStructure);
		confPathChoose.addActionListener(e -> onSelectConf());

		fileLocation1.setIcon(PluginIcons.locate);
		fileLocation1.addActionListener(e -> openFileLocation(this.setPath.getText().trim()));

		fileLocation2.setIcon(PluginIcons.locate);
		fileLocation2.addActionListener(e -> openFileLocation(this.confPath.getText().trim()));

		String setPath = CommonUtil.getSetPath();
		String confPath = CommonUtil.getConfPath();
		if (StringUtils.isNotBlank(setPath)) {
			this.setPath.setText(setPath);
			this.confPath.setText(confPath);
			String setPath1 = this.setPath.getText();
			File setFile = new File(setPath1);
			File[] files = setFile.listFiles();
			Vector<File> vector = new Vector<>(Arrays.asList(files));
			this.fileList.setListData(vector);
		}

		settingLabel.setIcon(PluginIcons.settings);
		confLabel.setIcon(PluginIcons.config);
		mvnSetting.setIcon(PluginIcons.settings);
		mvnSetting.addActionListener(e -> ShowSettingsUtil.getInstance().showSettingsDialog(project, "Maven"));
	}

	private void onSelectConf() {
		FileChooseUtil uiComponentFacade = FileChooseUtil.getInstance(project);
		VirtualFile baseDir = null;
		if (project != null) {
			baseDir = ProjectUtil.guessProjectDir(project);
		}
		String existPath = this.confPath.getText();
		if (StringUtils.isNotBlank(existPath)) {
			VirtualFileManager instance = VirtualFileManager.getInstance();
			baseDir = instance.findFileByUrl("file://" + existPath);
		}
		final VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("选择Maven conf文件夹路径", baseDir);
		if (vf == null) {
			return;
		}
		if (!vf.isDirectory()) {
			JOptionPane.showMessageDialog(this.rootPanel, "请选择文件夹！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.confPath.setText(vf.getPath());
	}

	private void onSelectSet() {
		FileChooseUtil uiComponentFacade = FileChooseUtil.getInstance(project);
		VirtualFile baseDir = null;
		if (project != null) {
			baseDir = ProjectUtil.guessProjectDir(project);
		}
		String existPath = this.setPath.getText();
		if (StringUtils.isNotBlank(existPath)) {
			VirtualFileManager instance = VirtualFileManager.getInstance();
			baseDir = instance.findFileByUrl("file://" + existPath);
		}
		VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("选择settings.xml文件存储路径", baseDir);
		if (vf == null) {
			return;
		}
		if (!vf.isDirectory()) {
			JOptionPane.showMessageDialog(this.rootPanel, "请选择文件夹！", "错误", JOptionPane.ERROR_MESSAGE);
			return;
		}
		this.setPath.setText(vf.getPath());

		String setPath1 = setPath.getText();
		File setFile = new File(setPath1);
		File[] files = setFile.listFiles();
		Vector<File> vector = new Vector<>(Arrays.asList(files));
		this.fileList.setListData(vector);
	}

	private void onOK() {
		CommonUtil.initConfig(setPath, confPath);
		//mavenProjectsManager = MavenProjectsManager.getInstance(project);
		File selectedValue = this.fileList.getSelectedValue();
		String name = selectedValue.getName();
		if (!name.endsWith(".xml")) {
			JOptionPane.showMessageDialog(this.rootPanel, "请选择xml类型文件！", "错误", JOptionPane.ERROR_MESSAGE);
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
		JOptionPane.showMessageDialog(this.rootPanel, "修改成功！", "成功", JOptionPane.INFORMATION_MESSAGE);
		//mavenProjectsManager.forceUpdateAllProjectsOrFindAllAvailablePomFiles();
		dispose();
	}

	@Override
	protected void dispose() {
		super.dispose();
	}

	@Override
	protected Action @NotNull [] createActions() {
		return new Action[0];
	}

	private void openFileLocation(String path) {
		File file = new File(path);
		if (file.exists()) {
			try {
				Desktop.getDesktop().open(file);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	@Nullable
	@Override
	protected JComponent createCenterPanel() {
		return rootPanel;
	}
}
