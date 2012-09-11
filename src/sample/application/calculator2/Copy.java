package sample.application.calculator2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.ClipboardManager;
import android.widget.TextView;

@SuppressLint("NewApi")
public class Copy implements FunctionLogic {

	@Override
	public void doFunction(Calculator2Activity ca) {
		ClipboardManager cm = (ClipboardManager) ca.getSystemService(Activity.CLIPBOARD_SERVICE);
		cm.setText(((TextView)ca.findViewById(R.id.displayPanel)).getText());
	}

}
