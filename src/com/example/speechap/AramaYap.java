package com.example.speechap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class AramaYap extends Activity implements
OnItemClickListener

{
	
	private ListView listView;
	
	private List<ContactBean> list = new ArrayList<ContactBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aramayap);
		
		listView = (ListView) findViewById(R.id.list);
		listView.setOnItemClickListener(this);
		
		Cursor phones = getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, null);
		while (phones.moveToNext()) {

			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

			ContactBean objContact = new ContactBean();
			objContact.setName(name);
			objContact.setPhoneNo(phoneNumber);
			list.add(objContact);
		}
		
		phones.close();

		ContanctAdapter objAdapter = new ContanctAdapter(
				AramaYap.this, R.layout.alluser_row, list);
		listView.setAdapter(objAdapter);

		if (null != list && list.size() != 0) {
			Collections.sort(list, new Comparator<ContactBean>() {

				@Override
				public int compare(ContactBean lhs, ContactBean rhs) {
					return lhs.getName().compareTo(rhs.getName());
				}
			});
			AlertDialog alert = new AlertDialog.Builder(
					AramaYap.this).create();
			alert.setTitle("");

			alert.setMessage(list.size() + " Contact Found!!!");

			alert.setButton("OK", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			alert.show();

		} else {
			showToast("No Contact Found!!!");
		}
		
	}
	
	private void showToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	@Override
	public void onItemClick(AdapterView<?> listview, View v, int position,
			long id) {
		// TODO Auto-generated method stub
		
		ContactBean bean = (ContactBean) listview.getItemAtPosition(position);
		showCallDialog(bean.getName(), bean.getPhoneNo());
		
	}
	
	private void showCallDialog(String name, final String phoneNo) {
		AlertDialog alert = new AlertDialog.Builder(AramaYap.this)
				.create();
		alert.setTitle("Call?");

		alert.setMessage("Are you sure want to call " + name + " ?");

		alert.setButton("No", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alert.setButton2("Yes", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				String phoneNumber = "tel:" + phoneNo;
				Intent intent = new Intent(Intent.ACTION_CALL, Uri
						.parse(phoneNumber));
				startActivity(intent);
			}
		});
		alert.show();
		
	}

}
