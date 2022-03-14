package com.revature.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.revature.dao.TableDao;
import com.revature.models.Account;
import com.revature.models.User;
import com.revature.util.ColumnField;
import com.revature.util.Configuration;
import com.revature.util.ForeignKeyField;
import com.revature.util.MetaModel;

public class App {

	public static void main(String[] args) {
		List<Class<?>> ormClasses = Arrays.asList(Account.class, User.class);
		
		TableDao tdao = new TableDao();
//		System.out.println(tdao.dropTables(ormClasses));
//		System.out.println(tdao.createTables(ormClasses));
		
//		System.out.println(tdao.insertUser("users", "Diana", "Prince", "wonderwoman", "ww"));

//		System.out.println(tdao.findUserIdByUsername("users","spiderman"));

		
//		System.out.println(tdao.insertAccount("accounts",5000000, true, 1));

//		System.out.println(tdao.insertUser("users", "Peter", "Parker", "spiderman", "webs"));
//		int ppUserId = tdao.findUserIdByUsername("users", "spiderman"); 
//		System.out.println(tdao.insertAccount("accounts", 200, true, ppUserId));

//		System.out.println(tdao.findUserIdByAccountId("accounts", 7));
	
//		tdao.viewAllUsers();
//		tdao.viewAllAccounts();
//		tdao.insertAccount("accounts", 1000000, false, tdao.findUserIdByUsername("users", "wonderwoman"));
		
//		tdao.viewAllAccountsWithNameAndUsername();
		
//		User u = new User(1, "First", "Last", "Username", "Pass");
//		Account a = new Account(1000, 7, false);
		
//		List<Object> values = new ArrayList<Object>();
//		values.add(6000);
//		values.add(true);
//		values.add(11);
//		List<Object> values = new ArrayList<Object>();
//		values.add("First5");
//		values.add("Last5");
//		values.add("Username5");
//		values.add("Pass5");

//		System.out.println(tdao.insert(ormClasses, Account.class, values));

		tdao.viewRow(User.class, 2);
		
	}
	
}
