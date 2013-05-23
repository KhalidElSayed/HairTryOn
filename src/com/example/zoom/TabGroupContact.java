package com.example.zoom;

import android.content.Intent;
import android.os.Bundle;

public class TabGroupContact extends WorkoutTabGroupActivity {

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startChildActivity("Contact", new Intent(TabGroupContact.this,TabContact.class));
	}

	
}
