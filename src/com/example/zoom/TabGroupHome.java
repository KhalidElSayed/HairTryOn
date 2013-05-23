package com.example.zoom;

import android.content.Intent;
import android.os.Bundle;

public class TabGroupHome extends WorkoutTabGroupActivity {

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startChildActivity("TabGroupHome", new Intent(TabGroupHome.this,HomeScreen.class));
	}

	
}
