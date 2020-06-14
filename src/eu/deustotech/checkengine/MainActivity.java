package eu.deustotech.checkengine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	
	private Button avvio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		avvio = (Button) findViewById(R.id.buttonStart);
		avvio.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.buttonStart:
				Intent i = new Intent(MainActivity.this, CheckEngineViewModel.class);
                startActivity(i);
                finish();
				break;
			default:
				break;
		}
	}
	
}
