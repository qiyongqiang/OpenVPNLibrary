package ru.drivepixels.openvpn;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import de.blinkt.openvpn.LaunchVPN;
import de.blinkt.openvpn.activities.ConfigConverter;
import de.blinkt.openvpn.activities.DisconnectVPN;
import de.blinkt.openvpn.api.FileUtilities;


/**
 * Created by FL on 06.05.14.
 */
public class OpenVPNHelper {
	private FileUtilities fl;
	private Intent startImport;
	private File f;
	private String mConfig;
	private static final int IMPORT_PROFILE = 231;
	private Activity activi;
	private File dir;
	
	public static String state = "";
	private Intent intent;

	static public String udp = "";
	

	public OpenVPNHelper(Activity activity, String config) {
		activi = activity;
		mConfig = config;
		
		fl = new FileUtilities(activi);
		startImport = new Intent(activi, ConfigConverter.class);
		fl.write("conf.ovpn", mConfig);
		f = new File(Environment.getExternalStorageDirectory() + fl.mFile);
		startImport.setAction(ConfigConverter.IMPORT_PROFILE);
		startImport.setData(Uri.fromFile(f));
		dir = new File(Environment.getExternalStorageDirectory() + fl.mDir);
		activi.startActivityForResult(startImport, IMPORT_PROFILE);
	}

	public void startVpn() {
		intent = new Intent(activi, LaunchVPN.class);
		intent.setAction(Intent.ACTION_MAIN);
		intent.putExtra(LaunchVPN.EXTRA_KEY, udp);
		activi.startActivity(intent);
		if (f.exists() && dir.exists()) {
			f.delete();
			fl.recursiveDelete(dir);
		}
	}

	public void disconnectVPN() {
		Intent intent = new Intent(activi, DisconnectVPN.class);
		activi.startActivity(intent);
	}

}
