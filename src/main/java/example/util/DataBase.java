package example.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataBase {
    private static DataSource ds = null;

	static {
		Context context = null;
		try {
			context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jdbc/MyLocalDB");
		} catch (NamingException e) {
//			e.printStackTrace();
			System.out.println(e.toString());
		}
	}

	public static DataSource getDataSource() {
		return ds;
	}

	public static void setDs(DataSource ds) {
		DataBase.ds = ds;
	}
}
