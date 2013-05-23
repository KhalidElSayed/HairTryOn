package com.example.zoom;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Calculator extends Activity implements OnClickListener 
{

	TextView charge, charge1;
	EditText material, piece, wanttomake, material1, piece1, wanttomake1;

	int intmaterial, intpiece, intwanttomake, intmaterial1, intpiece1,
			intwanttomake1, int_ans_material, int_ans_piece,
			int_ans_wanttomake, int_save;
	float int_ans_profit, longconvert, noofhour, chargeperhour, matcost;
	Button calculate, calculate1;
	String str_material, str_piece, str_wanttomake, str_material1, str_piece1,
			str_wanttomake1, passwordaboutuser, errormessages;
	RadioGroup chooseradio;
	RadioButton selectedid = null;
	RadioButton selectedid1 = null;
	ScrollView pieces, pieces1;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calculator);

		chooseradio = (RadioGroup) findViewById(R.id.radio_choose);
			
		selectedid = (RadioButton) findViewById(R.id.radio_bythepiece);
		selectedid1 = (RadioButton) findViewById(R.id.radio_bythrhour);

		selectedid.setOnClickListener(this);
		selectedid1.setOnClickListener(this);
		
		pieces=(ScrollView) findViewById(R.id.scrollpiece);
		pieces1=(ScrollView) findViewById(R.id.scrollhour);
		
		charge = (TextView) findViewById(R.id.txt_charge);
		charge1=(TextView) findViewById(R.id.txt_charge1);

		material = (EditText) findViewById(R.id.edt_materialcost);
		piece = (EditText) findViewById(R.id.edt_noofpieces);
		
		wanttomake = (EditText) findViewById(R.id.edt_makeaftercost);

		material1 = (EditText) findViewById(R.id.edt_materialcost1);
		piece1 = (EditText) findViewById(R.id.edt_noofpieces1);
		wanttomake1 = (EditText) findViewById(R.id.edt_makeaftercost1);

		calculate = (Button) findViewById(R.id.btn_calculate);
		calculate1 = (Button) findViewById(R.id.btn_calculate1);

		calculate.setOnClickListener(this);
		calculate1.setOnClickListener(this);

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {

		case R.id.radio_bythepiece:
			pieces1.setVisibility(View.GONE);
			pieces.setVisibility(View.VISIBLE);
			break;

		case R.id.radio_bythrhour:

			pieces.setVisibility(View.GONE);
			pieces1.setVisibility(View.VISIBLE);
			break;

		case R.id.btn_calculate:
		{
			errormessages = "";
			str_material = material.getText().toString();
			str_piece = piece.getText().toString();
			str_wanttomake = wanttomake.getText().toString();

			if (str_material.equals("") == true || str_piece.equals("") == true
					|| str_wanttomake.equals("") == true) {
				passwordaboutuser = "Fields are empty..please fill it";
				errormessages = passwordaboutuser;
			}
			if (errormessages.compareTo("") != 0)
			{
				Toast.makeText(getApplicationContext(), errormessages,Toast.LENGTH_LONG).show();
			} 
			else
			{
				intmaterial = Integer.parseInt(str_material);
				intpiece = Integer.parseInt(str_piece);
				intwanttomake = Integer.parseInt(str_wanttomake);
				longconvert = intwanttomake - intmaterial;

				int_ans_profit = longconvert / intpiece;
				charge.setText(" You need to charge your client " + " $ "
						+ int_ans_profit + " per piece. Your profit will be "
						+ " $ " + longconvert);
			}
		}
		case R.id.btn_calculate1:
		{
			errormessages = "";
			str_material1 = material1.getText().toString();
			str_piece1 = piece1.getText().toString();
			str_wanttomake1 = wanttomake1.getText().toString();

			if (str_material1.equals("") == true
					|| str_piece1.equals("") == true
					|| str_wanttomake1.equals("") == true) 
			{
				passwordaboutuser = "Fields are empty..please fill it";
				errormessages = passwordaboutuser;
			}
			if (errormessages.compareTo("") != 0) 
			{
				Toast.makeText(getApplicationContext(), errormessages,Toast.LENGTH_LONG).show();
			} 
			else 
			{
				intmaterial1 = Integer.parseInt(str_material1); // 43
				intpiece1 = Integer.parseInt(str_piece1); // 343			
				intwanttomake1 = Integer.parseInt(str_wanttomake1); // 34

				// 343*34= 11662 + 43 =11705 client charge 11705*343=4014815
				chargeperhour = intpiece1 * intwanttomake1 + intmaterial1; // 11705
				matcost = chargeperhour * intpiece1;

				System.out.println("pers hour charger >>>" + chargeperhour);

				System.out.println("pers hour charger >>>" + matcost);
				charge1.setText(" You need to charge your client " + " $ "
						+ chargeperhour + " Your profit will be " + " $ "
						+ matcost);
			}
		}
		default:
			break;
		}
	}
}
