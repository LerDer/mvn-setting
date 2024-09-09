package com.wd.msu.utils;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.wd.msu.ui.MavenSettingForm;
import javax.swing.JTextField;

/**
 * @author lww
 * @date 2020-07-09 10:21
 */
public class CommonUtil {

	/**
	 * 初始化界面
	 */
	public static void init(AnActionEvent e) {
		MavenSettingForm dialog = new MavenSettingForm(e.getProject());
		dialog.pack();
		//设置窗口居中
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	public static void initConfig(JTextField setPath, JTextField confPath) {
		PropertiesComponent.getInstance().setValue("setPath", setPath.getText());
		PropertiesComponent.getInstance().setValue("confPath", confPath.getText());
	}

	public static String getSetPath() {
		return PropertiesComponent.getInstance().getValue("setPath");
	}

	public static String getConfPath() {
		return PropertiesComponent.getInstance().getValue("confPath");
	}

}
