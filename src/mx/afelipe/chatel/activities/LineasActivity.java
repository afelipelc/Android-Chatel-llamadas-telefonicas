package mx.afelipe.chatel.activities;

import mx.afelipe.chatel.R;
import android.os.Bundle;
import android.widget.TabHost;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;


@SuppressWarnings("deprecation")
public class LineasActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lineas);
		
		//Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
//	    
	    Intent intent;  // Reusable Intent for each tab
//	    //http://developer.android.com/guide/topics/ui/layout/tabs.html
//	    //Tabbed
//	    
	    intent = new Intent().setClass(this, ContadorLineaActivity.class);
	    intent.putExtra("num", "1");
	   
	    spec = tabHost.newTabSpec("linea1").setIndicator("Línea 1")
            .setContent(intent);
	    tabHost.addTab(spec);
//	    
//	    //hacer lo mismo para los otros tabs
	    intent = new Intent().setClass(this, ContadorLineaActivity.class);
	    intent.putExtra("num", "2");
	    
	    spec = tabHost.newTabSpec("linea2").setIndicator("Línea 2")
            .setContent(intent);
	    tabHost.addTab(spec);
	    
	    intent = new Intent().setClass(this, ContadorLineaActivity.class);
	    intent.putExtra("num", "3");
	    spec = tabHost.newTabSpec("linea3").setIndicator("Línea 3")
            .setContent(intent);
	    tabHost.addTab(spec);
//	    
//	    //indicar cual tab es el seleccionado
	    tabHost.setCurrentTab(0);
	    
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
	}
}
