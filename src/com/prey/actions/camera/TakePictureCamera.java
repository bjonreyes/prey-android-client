package com.prey.actions.camera;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
 
import android.content.Context;
 
import com.prey.PreyLogger;
import com.prey.actions.HttpDataService;
import com.prey.actions.picture.PictureUtil;
import com.prey.net.http.EntityFile;

public class TakePictureCamera {

	public byte[] dataImagen = null;

	public HttpDataService takePicture(Context ctx) {
		PreyLogger.d("welcome TakePictureCamera");
		HttpDataService data= PictureUtil.getPicture(ctx);

		if (dataImagen != null) {
			PreyLogger.d("dataImagen data length=" + dataImagen.length);
			InputStream file = new ByteArrayInputStream(dataImagen);
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
		return data;
	}

	 
	 
}
