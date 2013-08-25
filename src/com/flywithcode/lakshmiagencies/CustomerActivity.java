package com.flywithcode.lakshmiagencies;

import java.util.List;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

public class CustomerActivity extends ListActivity implements OnClickListener{
	private CustomersDataSource cust_datasource;
	final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        
        cust_datasource = new CustomersDataSource(this);
        cust_datasource.open();
        
        List<Customer> values = cust_datasource.getAllCustomers();
        
        ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        
        Button create_cust = (Button) findViewById(R.id.button1) ;
        create_cust.setOnClickListener(this);
        
    }
    
    @Override
    protected void onResume() {
    	cust_datasource.open();
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	cust_datasource.close();
    	super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	@Override
	public void onClick(View action) {
		if(action.getId() == R.id.button1) {
			// get prompts.xml view
			LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.cust_create, null);

			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
					context);

			// set prompts.xml to alertdialog builder
			alertDialogBuilder.setView(promptsView);

			final EditText cust_name = (EditText) promptsView
					.findViewById(R.id.editText1);

			// set dialog message
			alertDialogBuilder
				.setCancelable(false)
				.setPositiveButton("OK",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					// Get the user input and insert into table
				    ArrayAdapter<Customer> adapter = (ArrayAdapter<Customer>) getListAdapter();
				    Customer customer = null;
				    customer = cust_datasource.createCustomer(cust_name.getText().toString());
				    adapter.add(customer);
				    adapter.notifyDataSetChanged();
				   //db transaction ends
				    	
				    }
				  })
				.setNegativeButton("Cancel",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				  });

			// create alert dialog
			AlertDialog alertDialog = alertDialogBuilder.create();

			// show it
			alertDialog.show();
		}
		
	}
    
}
