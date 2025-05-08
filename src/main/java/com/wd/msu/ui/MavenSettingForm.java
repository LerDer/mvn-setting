package com.wd.msu.ui;

import java.awt.AWTEvent;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.ImageIcon;
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
import org.jetbrains.idea.maven.project.MavenProjectsManager;

import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.wd.msu.utils.CommonUtil;
import com.wd.msu.utils.FileChooseUtil;

public class MavenSettingForm extends JDialog {

	private Project project;

	private JPanel contentPane;
	private JTextField setPath;
	private JTextField confPath;
	private JList<File> fileList;
	private JButton change;
	private JButton cancel;
	private JButton setPathChoose;
	private JButton confPathChoose;
	private JButton mvnSetting;
	private JButton fileLocation1;
	private JButton fileLocation2;
	private MavenProjectsManager mavenProjectsManager;

	public MavenSettingForm(Project project) {
		setContentPane(contentPane);
		setResizable(false);
		setModal(true);
		this.project = project;
		getRootPane().setDefaultButton(change);

		//change.setContentAreaFilled(false);//除去默认的背景填充
		//change.setBorderPainted(false);//不打印边框
		change.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				change.setIcon(new ImageIcon(getClass().getResource("/icon/ok_enter.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				change.setIcon(new ImageIcon(getClass().getResource("/icon/ok.png")));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				change.setIcon(new ImageIcon(getClass().getResource("/icon/ok.png")));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				change.setIcon(new ImageIcon(getClass().getResource("/icon/ok_enter.png")));
			}
		});
		change.addActionListener(e -> onOK());

		//cancel.setContentAreaFilled(false);
		//cancel.setBorderPainted(false);
		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				cancel.setIcon(new ImageIcon(getClass().getResource("/icon/cancel_enter.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				cancel.setIcon(new ImageIcon(getClass().getResource("/icon/cancel.png")));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				cancel.setIcon(new ImageIcon(getClass().getResource("/icon/cancel.png")));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				cancel.setIcon(new ImageIcon(getClass().getResource("/icon/cancel_enter.png")));
			}
		});
		cancel.addActionListener(e -> onCancel());

		//setPathChoose.setContentAreaFilled(false);
		//setPathChoose.setBorderPainted(false);
		setPathChoose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				setPathChoose.setIcon(new ImageIcon(getClass().getResource("/icon/selectall_enter.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				setPathChoose.setIcon(new ImageIcon(getClass().getResource("/icon/selectall.png")));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				setPathChoose.setIcon(new ImageIcon(getClass().getResource("/icon/selectall.png")));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				setPathChoose.setIcon(new ImageIcon(getClass().getResource("/icon/selectall_enter.png")));
			}
		});
		setPathChoose.addActionListener(e -> onSelectSet());

		//confPathChoose.setContentAreaFilled(false);
		//confPathChoose.setBorderPainted(false);
		confPathChoose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				confPathChoose.setIcon(new ImageIcon(getClass().getResource("/icon/selectall_enter.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				confPathChoose.setIcon(new ImageIcon(getClass().getResource("/icon/selectall.png")));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				confPathChoose.setIcon(new ImageIcon(getClass().getResource("/icon/selectall.png")));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				confPathChoose.setIcon(new ImageIcon(getClass().getResource("/icon/selectall_enter.png")));
			}
		});
		confPathChoose.addActionListener(e -> onSelectConf());

		//fileLocation1.setContentAreaFilled(false);
		//fileLocation1.setBorderPainted(false);
		fileLocation1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				fileLocation1.setIcon(new ImageIcon(getClass().getResource("/icon/location_enter.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				fileLocation1.setIcon(new ImageIcon(getClass().getResource("/icon/location.png")));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				fileLocation1.setIcon(new ImageIcon(getClass().getResource("/icon/location.png")));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				fileLocation1.setIcon(new ImageIcon(getClass().getResource("/icon/location_enter.png")));
			}
		});
		fileLocation1.addActionListener(e -> openFileLocation(this.setPath.getText().trim()));

		//fileLocation2.setContentAreaFilled(false);
		//fileLocation2.setBorderPainted(false);
		fileLocation2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				fileLocation2.setIcon(new ImageIcon(getClass().getResource("/icon/location_enter.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				fileLocation2.setIcon(new ImageIcon(getClass().getResource("/icon/location.png")));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				fileLocation2.setIcon(new ImageIcon(getClass().getResource("/icon/location.png")));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				fileLocation2.setIcon(new ImageIcon(getClass().getResource("/icon/location_enter.png")));
			}
		});
		fileLocation2.addActionListener(e -> openFileLocation(this.confPath.getText().trim()));

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

		//mvnSetting.setContentAreaFilled(false);
		//mvnSetting.setBorderPainted(false);
		mvnSetting.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				mvnSetting.setIcon(new ImageIcon(getClass().getResource("/icon/set_enter.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				mvnSetting.setIcon(new ImageIcon(getClass().getResource("/icon/set.png")));
			}

			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				mvnSetting.setIcon(new ImageIcon(getClass().getResource("/icon/set.png")));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				mvnSetting.setIcon(new ImageIcon(getClass().getResource("/icon/set_enter.png")));
			}
		});
		mvnSetting.addActionListener(e -> {
			ShowSettingsUtil.getInstance().showSettingsDialog(project, "Maven");
			//this.setVisible(false);
		});
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
		final VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("选择Maven conf文件夹路径", baseDir);
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
		VirtualFile vf = uiComponentFacade.showSingleFolderSelectionDialog("选择settings.xml文件存储路径", baseDir);
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
		mavenProjectsManager = MavenProjectsManager.getInstance(project);
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
		mavenProjectsManager.forceUpdateAllProjectsOrFindAllAvailablePomFiles();
		dispose();
	}

	private void onCancel() {
		// add your code here if necessary
		dispose();
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
}
