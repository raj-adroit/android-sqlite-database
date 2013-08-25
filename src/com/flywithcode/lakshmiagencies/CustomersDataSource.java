package com.flywithcode.lakshmiagencies;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class CustomersDataSource {
	private SQLiteDatabase db;
	private CustomerDBHelper cust_db_helper;
	private String[] allColumns = { CustomerDBHelper.COLUMN_ID, CustomerDBHelper.COLUMN_NAME };
	
	public CustomersDataSource(Context context) {
		cust_db_helper = new CustomerDBHelper(context);
	}
	
	public void open() throws SQLException {
		db = cust_db_helper.getWritableDatabase();
	}
	
	public void close() {
		cust_db_helper.close();
	}
	
	public Customer createCustomer(String name) {
		ContentValues values = new ContentValues();
		values.put(CustomerDBHelper.COLUMN_NAME, name);
		long customerId = db.insert(CustomerDBHelper.TABLE_CUSTOMERS, null, values);
		Cursor cursor  = db.query(CustomerDBHelper.TABLE_CUSTOMERS, allColumns, 
				CustomerDBHelper.COLUMN_ID + " = " + customerId, null, null, null, null);
		cursor.moveToFirst();
		Customer newCustomer = cursorToCustomer(cursor);
		cursor.close();
		return newCustomer;
	}
	
	public Customer cursorToCustomer(Cursor cursor) {
		Customer customer = new Customer();
		customer.setId(cursor.getLong(0));
		customer.setName(cursor.getString(1));
		return customer;
	}
	
	public void deleteCustomer(Customer customer) {
		long customerId = customer.getId();
		db.delete(CustomerDBHelper.TABLE_CUSTOMERS, CustomerDBHelper.COLUMN_ID + "=" + customerId, null);
		
	}
	
	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<Customer>();
		
		Cursor cursor = db.query(CustomerDBHelper.TABLE_CUSTOMERS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while(!cursor.isAfterLast()) {
			Customer customer = cursorToCustomer(cursor);
			customers.add(customer);
			cursor.moveToNext();
		}
		cursor.close();
		return customers;
	}
	

}
