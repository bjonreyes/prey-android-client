/*******************************************************************************
 * Created by Carlos Yaconi.
 * Copyright 2011 Fork Ltd. All rights reserved.
 * License: GPLv3
 * Full license at "/LICENSE"
 ******************************************************************************/
package com.prey.actions;

import android.content.Context;

import com.prey.PreyConfig;
import com.prey.PreyLogger;
import com.prey.actions.observer.ActionJob;
import com.prey.backwardcompatibility.FroyoSupport;

public class WipeAction extends PreyAction {

	public static final String DATA_ID = "wipe";
	public final String ID = "wipe";
	
	public HttpDataService run(Context ctx) {
		return null;
	}

	@Override
	public String textToNotifyUserOnEachReport(Context ctx) {
		return "";
	}

	@Override
	public void execute(ActionJob actionJob, Context ctx) {
		PreyConfig preyConfig = PreyConfig.getPreyConfig(ctx);
		if (preyConfig.isFroyoOrAbove()){
			FroyoSupport.getInstance(ctx).wipe();
		}
	}

	@Override
	public boolean isSyncAction() {
		return false;
	}

	@Override
	public boolean shouldNotify() {
		return false;
	}
	
	@Override
	public void killAnyInstanceRunning(Context ctx) {
		
	}

}
