package com.example.zoom;

import android.content.Intent;
import android.os.Bundle;

public class TabGroupMore extends WorkoutTabGroupActivity
{

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		startChildActivity("TabMore", new Intent(TabGroupMore.this,TabMore.class));
	}
}
