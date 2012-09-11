package sample.application.calculator2;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import sample.application.calculator2.Ac;
import sample.application.calculator2.Bs;
import sample.application.calculator2.C;
import sample.application.calculator2.Calculator2Activity;
import sample.application.calculator2.Copy;
import sample.application.calculator2.R;
import sample.application.calculator2.FunctionLogic;

import android.util.Log;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Vibrator;
import android.content.SharedPreferences;

public class Calculator2Activity extends Activity {
	public String strTemp = "";
	public String strResult = "0";
	public Integer operator = 0;
	public String num1 = new String();
	public String strDisplay;

private static Map<Integer, FunctionLogic> funcMap;

	static{
		Calculator2Activity.funcMap = new HashMap<Integer, FunctionLogic>();
		Calculator2Activity.funcMap.put(R.id.keypadAC, new Ac());
		Calculator2Activity.funcMap.put(R.id.keypadC, new C());
		Calculator2Activity.funcMap.put(R.id.keypadBS, new Bs());
		Calculator2Activity.funcMap.put(R.id.keypadCopy, new Copy());

	}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator2);
        readPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_calculator2, menu);
        return true;
    }

    @Override
	protected void onStop() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStop();
		writePreferences();
	}

	public void numKeyOnClick(View v){
		TextView sp = (TextView)findViewById(R.id.displayPanel);
		String strSp = sp.getText().toString();
		if(strSp.indexOf(" = ") == strSp.length() - 1)sp.setText("");
    	String strInKey = (String)((Button)v).getText();
    	((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(50);
    	if(strInKey.equals(".")){
    		if(this.strTemp.length() == 0){
    			this.strTemp = "0.";
    		}else{
    			if(this.strTemp.indexOf(".") == -1){
    				this.strTemp = this.strTemp + ".";
    			}
    		}
    	}else{
    		this.strTemp = this.strTemp + strInKey;
    	}
    	//todo インスタンス変数渡しとるね
    	this.showNumber(this.strTemp);

    	/*Button button = (Button)v;
    	Log.d("[buttonのtext]","tv.Text"+ button.getText().toString());

    	TextView tv = (TextView) this.findViewById(R.id.displayPanel);
    	tv.setText(tv.getText().toString() + button.getText().toString());
    	*/
    }

    void showNumber(String strNum){

    	DecimalFormat form = new DecimalFormat("#,##0");
    	String strDecimal = "";
    	String strInt = "";
    	String fText = "";

    	if(strNum.length()>0){
    		int decimalPoint = strNum.indexOf(".");
    		if(decimalPoint>-1){
    			strDecimal = strNum.substring(decimalPoint);
    			strInt = strNum.substring(0,decimalPoint);
    		}else{
    			strInt = strNum;
    		}
    		fText = form.format(Double.parseDouble(strInt)) + strDecimal;
    	}else{
    		fText = "0";
    	}

    	((TextView)findViewById(R.id.displayPanel)).setText(fText);
    }

    /*public void addKeyOnClick(View v){

    	Button button = (Button)v;
    	//String num1 = null;//表示されている数字の保存領域
    	TextView tv = (TextView) this.findViewById(R.id.displayPanel);
    	Log.d("[buttonのtext]","tv.Text"+ button.getText().toString());
    	this.num1 = tv.getText().toString();
    	Log.d("tvのテキスト","" + tv.getText().toString());
    	tv.setText("0");

    }*/

   /* public void functionKeyOnClick(View v){

    	switch(v.getId()){
    	case R.id.keypadAC:
    		strTemp = "";
    		strResult = "0";
    		operator = 0;
    		((TextView)findViewById(R.id.displayPanel)).setText("");
    		break;
    	case R.id.keypadC:
    		strTemp = "";
    		break;
    	case R.id.keypadBS:
    		if(strTemp.length() == 0)return;
    		else strTemp = strTemp.substring(0,strTemp.length()-1);
    		break;
    	case R.id.keypadCopy:
    		ClipboardManager cm = (ClipboardManager) getSystemService(
    				CLIPBOARD_SERVICE);
    		cm.setText(((TextView)findViewById(R.id.displayPanel)).getText());
    		return;
    	}
    	showNumber(this.strTemp);
    }*/

    public void functionKeyOnClick(View v){

		FunctionLogic logic = funcMap.get(v.getId());
		logic.doFunction(this);

	}

    public void operatorKeyOnClick(View v){
    	((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(50);
    	TextView sp = (TextView) findViewById(R.id.displayPanel);
    	String op2 = ((Button)findViewById(v.getId())).getText().toString();

    	if(this.operator != 0){
    		String op1 = ((Button)findViewById(operator)).getText().toString();
    		if(this.strTemp.length()>0){
    			sp.setText(strResult + op1 + strTemp + op2);
    			this.strResult = this.doCalc();
    			this.showNumber(this.strResult);
    		}
    		else{
    			sp.setText(strResult + op2);
    		}

    	}else{
    		if(this.strTemp.length()>0){
    			this.strResult = this.strTemp;
    		}
    		sp.setText(strResult + op2);
    	}
    	this.strTemp = "";

    	if(v.getId() == R.id.keypadEq){
    		this.operator = 0;
    	}else{
    		this.operator = v.getId();
    	}
    }

    /*public void equalKeyOnClick(View v){
    	Log.d("equalKeyが呼ばれたかどうか","text");
    	Log.d("equalKeyでのnum1",this.num1 );
    	//新しく表示された数字を取得
    	//num1に保存した最初の数字を取得
    	//上二つを足す。
    }*/

    private String doCalc(){
    	BigDecimal bd1 = new BigDecimal(strResult);
    	BigDecimal bd2 = new BigDecimal(strTemp);
    	BigDecimal result = BigDecimal.ZERO;

    	switch(this.operator){
    	case R.id.keypadAdd:
    		result = bd1.add(bd2);
    		break;
    	case R.id.keypadSub:
    		result = bd1.subtract(bd2);
    		break;
    	case R.id.keypadMulti:
    		result = bd1.multiply(bd2);
    		break;
    	case R.id.keypadDiv:
    		if(!bd2.equals(BigDecimal.ZERO)){
    			result = bd1.divide(bd2, 12 ,3);
    		}else{
    			Toast toast = Toast.makeText(this, R.string.toast_div_by_zero, 1000);
    			toast.show();
    		}
    		break;
    	}

    	if(result.toString().indexOf(".") >= 0){
    		return result.toString().replaceAll("¥¥.0 + $|0 + $", "");
    	}else{
    		return result.toString();
    	}
    }

    void writePreferences(){
    	SharedPreferences prefs = this.getSharedPreferences("CalcPrefs", MODE_PRIVATE);
    	SharedPreferences.Editor editor = prefs.edit();
    	editor.putString("strTemp", strTemp);
    	editor.putString("strResult", strResult);
    	editor.putInt("operator", operator);
    	editor.putString(strDisplay,
    			((TextView)findViewById(R.id.displayPanel)).getText().toString());
    	editor.putString("strSubDisplay",
    			((TextView)findViewById(R.id.displayPanel)).getText().toString());
    	editor.commit();
    }

    void readPreferences(){
    	SharedPreferences prefs = this.getSharedPreferences("CalcPrefs", MODE_PRIVATE);
    	strTemp = prefs.getString("strTemp", "");
    	strResult = prefs.getString("strResult", "0");
    	operator = prefs.getInt("operator", 0);
    	((TextView)findViewById(R.id.displayPanel)).setText(
    			prefs.getString("strDisplay", "0"));
    	((TextView)findViewById(R.id.displayPanel)).setText(
    			prefs.getString("strSubDisplay", "0"));
    }
}
