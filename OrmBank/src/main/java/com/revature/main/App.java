package com.revature.main;

import java.lang.reflect.Field;
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
		
		tdao.getTableInfo(ormClasses);
				
				

	}

}