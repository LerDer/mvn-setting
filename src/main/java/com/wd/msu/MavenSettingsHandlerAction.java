package com.wd.msu;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.wd.msu.utils.CommonUtil;

public class MavenSettingsHandlerAction extends AnAction {

	@Override
	public void actionPerformed(AnActionEvent e) {
		CommonUtil.init(e);
	}
}
