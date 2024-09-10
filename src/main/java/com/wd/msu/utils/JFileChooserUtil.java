package com.wd.msu.utils;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * @author lww
 * @date 2024-09-10 1:22 PM
 */
public class JFileChooserUtil {

	private static JFileChooser folderChooser;

	private JFileChooserUtil() {
	}

	public static JFileChooser getInstance() {
		if (folderChooser == null) {
			folderChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		}
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		folderChooser.showOpenDialog(null);
		return folderChooser;
	}

}
