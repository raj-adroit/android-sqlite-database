package com.flywithcode.lakshmiagencies;

import java.util.List;
import android.os.Bundle;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class CustomerActivity extends ListActivity {
	private CustomersDataSource cust_datasource;
	final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        registerForContextMenu(getListView());
        
        cust_datasource = new CustomersDataSource(this);
        cust_datasource.open();
        
        List<Customer> values = cust_datasource.getAllCustomers();
        
        ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
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
        getMenuInflater().inflate(R.menu.menu_cutomer, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_customer) {
        	
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.cust_create, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        // set prompts.xml to alertdialog builder
        

        alertDialogBuilder.setView(promptsView);

        final EditText cust_name = (EditText) promptsView.findViewById(R.id.editText1);

        // set dialog message
        alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
        	public void onClick(DialogInterface dialog,int id) {
        	// Get the user input and insert into table
        	@SuppressWarnings("unchecked")
			ArrayAdapter<Customer> adapter = (ArrayAdapter<Customer>) getListAdapter();
        	Customer customer = null;
        	customer = cust_datasource.createCustomer(cust_name.getText().toString());
        	adapter.add(customer);
        	adapter.notifyDataSetChanged();
        	//database transaction ends
        	}
        	}).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
        		public void onClick(DialogInterface dialog,int id) {
        		dialog.cancel();
        		 }
        	});

        	// create alert dialog
        	AlertDialog alertDialog = alertDialogBuilder.create();

        	// show it
        	alertDialog.show();
        }
  	
    	return super.onOptionsItemSelected(item);
    }
    
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
    		ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	getMenuInflater().inflate(R.menu.context_menu_customer, menu);
    	
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
    	AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
    	if(item.getItemId() ==  R.id.delete_customer) {
    		if (getListAdapter().getCount() > 0) {
    			@SuppressWarnings("unchecked")
				ArrayAdapter<Customer> adapter = (ArrayAdapter<Customer>) getListAdapter();
    	        Customer customer = (Customer) getListAdapter().getItem(menuInfo.position);
    	        cust_datasource.deleteCustomer(customer);
    	        adapter.remove(customer);
    	        adapter.notifyDataSetChanged();
    	      }
    	}
    	
    	return super.onContextItemSelected(item);
    }

}