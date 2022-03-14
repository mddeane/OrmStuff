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
		
		/**
		 * run these two together to drop then create tables
		 * 
		 */
		
//		System.out.println(tdao.dropTables(ormClasses));
//		System.out.println(tdao.createTables(ormClasses));

		
		/**
		 * Adding a new User: 
		 * 1. get columns info (first_name, last_name, username, id)
		 * 2. add to a List
		 * 3. pass three things to insert()
		 * 		1) list of annotated classes
		 * 		2) the class that is annotated with the table/entity name (User)
		 * 		3) the List of columns info
		 */

		
//		List<Object> values = new ArrayList<Object>();
//		values.add("Bruce");
//		values.add("Wayne");
//		values.add("batman");
//		values.add("martha");

//		System.out.println(tdao.insert(ormClasses, User.class, values));

		
		/**
		 * Adding a new Account: 
		 * 1. get columns info (balance, is_active, user_id)
		 * 2. add to a List
		 * 3. pass three things to insert()
		 * 		1) list of annotated classes
		 * 		2) the class that is annotated with the table/entity name (Account)
		 * 		3) the List of columns info
		 */

//		List<Object> values = new ArrayList<Object>();
//		values.add(5000000);
//		values.add(true);
//		values.add(1);
//
//		System.out.println(tdao.insert(ormClasses, Account.class, values));

		/**
		 * viewAllAccountsWithNameAndUsername views a join of accounts with their users 
		 */
		
		tdao.viewAllAccountsWithNameAndUsername();


		/**
		 * viewAllUsers views all users
		 * 
		 */
		
		tdao.viewAllUsers();

		/**
		 * viewAllAccounts views all accounts
		 */
		
		tdao.viewAllAccounts();
		
		

		/**
		 * insertUser only works with users
		 */
		
//		System.out.println(tdao.insertUser("users", "Diana", "Prince", "wonderwoman", "ww"));


		/**
		 * insertAccount only works with accounts
		 */
		
//		System.out.println(tdao.insertAccount("accounts",5000000, true, 1));

		/**
		 * findUserIdByUsername
		 */
		
//		System.out.println(tdao.findUserIdByUsername("users","spiderman"));
		
		/**
		 * findUserIdByAccountId
		 */
		
//		System.out.println(tdao.findUserIdByAccountId("accounts", 7));

		
//		System.out.println(tdao.insertUser("users", "Peter", "Parker", "spiderman", "webs"));
//		int ppUserId = tdao.findUserIdByUsername("users", "spiderman"); 
//		System.out.println(tdao.insertAccount("accounts", 200, true, ppUserId));

	
//		tdao.insertAccount("accounts", 1000000, false, tdao.findUserIdByUsername("users", "wonderwoman"));
		
		
//		User u = new User(1, "First", "Last", "Username", "Pass");
//		Account a = new Account(1000, 7, false, 0);


		
//		tdao.viewRow(User.class, 2);
		
	}
	
}
