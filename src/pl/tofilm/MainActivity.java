package pl.tofilm;

import java.util.List;

import info.talacha.filmweb.api.FilmwebApi;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private String LOG_CLASS_NAME = this.getLocalClassName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		int a= 1;
		int b=2;
		
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				FilmwebApi api = new FilmwebApi();
//				List<Integer> filmList = api.getFilmIdList("Matrix");
//				
//				TextView text = (TextView)findViewById(R.id.textView1);
//				text.setText(filmList.size());
//			}
//
//		}).start();
		
		setControls();

	}
	
	private void setControls()
	{
		try{
			
		}catch(Exception ex)
		{
			Log.e(LOG_CLASS_NAME, "setControls error:" + ex.getMessage());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
