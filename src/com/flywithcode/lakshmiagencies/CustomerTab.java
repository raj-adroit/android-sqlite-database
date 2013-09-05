package com.flywithcode.lakshmiagencies;

import java.util.List;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class CustomerTab extends ListFragment implements ActionBar.TabListener {
	private CustomersDataSource cust_datasource;
	final Context context = getActivity();

	private Fragment mFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_customer, container, false);
          return view;

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		registerForContextMenu(getListView());
		
		  cust_datasource = new CustomersDataSource(getActivity());
	        cust_datasource.open();
	        
	        List<Customer> values = cust_datasource.getAllCustomers();
	        
	        ArrayAdapter<Customer> adapter = new ArrayAdapter<Customer>(getActivity(), android.R.layout.simple_list_item_1, values);
	        setListAdapter(adapter);
		
	}
	

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		mFragment = new CustomerTab();
		// Attach fragment1.xml layout
		ft.add(android.R.id.content, mFragment);
		ft.attach(mFragment);
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		// Remove fragment1.xml layout
		ft.remove(mFragment);
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		inflater.inflate(R.menu.menu_cutomer, menu);
		super.onCreateOptionsMenu(menu, inflater);
		
	}
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.add_customer) {
        	
        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(getActivity());
        View promptsView = li.inflate(R.layout.cust_create, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
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
		// TODO Auto-generated method stub
		//LayoutInflater inflater = null;
		// v= inflater.inflate(R.layout.activity_customer, null, false);
		//getMenuInflater().inflate(R.menu.context_menu_customer, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle("Options");
		menu.add(Menu.NONE, R.id.delete_customer, Menu.NONE, "Delete Customer");
		
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
