/*******************************************************************************
 * Created by Orlando Aliaga
 * Copyright 2012 Fork Ltd. All rights reserved.
 * License: GPLv3
 * Full license at "/LICENSE"
 ******************************************************************************/
package com.prey.actions.camera;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;

import com.prey.PreyLogger;
import com.prey.actions.HttpDataService;
import com.prey.actions.PreyAction;
import com.prey.actions.observer.ActionJob;
import com.prey.actions.observer.ActionResult;
import com.prey.actions.picture.PictureUtil;
import com.prey.activities.SimpleCameraActivity;
import com.prey.exceptions.PreyException;
import com.prey.net.http.EntityFile;

public class CameraAction extends PreyAction {

	public static final String DATA_ID = "webcam";

	public static ActionJob actionJob;

	public CameraAction() {
		PreyLogger.d("Ejecuting CameraAction Action");

	}

	@Override
	public String textToNotifyUserOnEachReport(Context ctx) {
		return "";
	}

	@Override
	public void execute(ActionJob actionJob2, Context ctx) throws PreyException {
		try {
			actionJob = actionJob2;
	 

			HttpDataService data= PictureUtil.getPicture(ctx);
			if (SimpleCameraActivity.dataImagen != null) {
				PreyLogger.d("dataImagen data length=" + SimpleCameraActivity.dataImagen.length);
				InputStream file = new ByteArrayInputStream(SimpleCameraActivity.dataImagen);
				EntityFile entityFile = new EntityFile();
				entityFile.setFile(file);
				entityFile.setMimeType("image/png");
				entityFile.setName("picture.jpg");
				entityFile.setType("picture");

				data = new HttpDataService(CameraAction.DATA_ID);
				data.setList(true);
				data.addEntityFile(entityFile);
			} else {
				PreyLogger.d("dataImagen null");
			}

			ActionResult result = new ActionResult();
			result.setDataToSend(data);
			actionJob.finish(result);
		} finally {
			SimpleCameraActivity.activity = null;
			SimpleCameraActivity.dataImagen = null;
			try {
				SimpleCameraActivity.camera.stopPreview();
			} catch (Exception e) {

			}
			try {
				SimpleCameraActivity.camera.release();
			} catch (Exception e) {

			}
			SimpleCameraActivity.camera = null;
			SimpleCameraActivity.mHolder = null;
		}

	}

	private boolean existWebcamMessage(Bundle bundle) {
		boolean isWebcamMessage = false;
		for (Iterator<Map.Entry<String, String>> it = getConfig().entrySet().iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			PreyLogger.d("CameraAction key:" + key + " value:" + value);
			bundle.putString(key, value);
			if ("webcam_message".equals(key) && !"".equals(key)) {
				isWebcamMessage = true;
			}
		}
		return isWebcamMessage;
	}

	@Override
	public boolean shouldNotify() {
		return true;
	}

	@Override
	public boolean isSyncAction() {
		return true;
	}

	public int getPriority() {
		return WEBCAM_PRIORITY;
	}

}
