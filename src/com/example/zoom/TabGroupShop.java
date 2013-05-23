package com.example.zoom;

import android.content.Intent;
import android.os.Bundle;

public class TabGroupShop extends WorkoutTabGroupActivity {

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startChildActivity("TabGroupShop", new Intent(TabGroupShop.this,Shop.class));
	}

	
}
