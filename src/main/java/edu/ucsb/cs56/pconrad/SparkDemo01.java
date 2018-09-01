package edu.ucsb.cs56.pconrad;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static spark.Spark.port;

/**
 * Hello world!
 *
 */

public class SparkDemo01 {
    public static void main(String[] args) {

        ArrayList<String> dbText = initDatabase();
        final String displayText = makeString(dbText);

        port(getHerokuAssignedPort());
		
		System.out.println("");
		System.out.println("(Don't worry about the warnings below about SLF4J... we'll deal with those later)");
		System.out.println("");						  
		System.out.println("In browser, visit: http://localhost:" + getHerokuAssignedPort() + "/hello");
		System.out.println("");
		spark.Spark.get("/", (req, res) -> displayText);

	}
	
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    static ArrayList<String> initDatabase() {
        ArrayList<String> dbQuery = new ArrayList<>();
        try {
            dbQuery = Database.createDocument();
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host thrown");
        }
        return dbQuery;
    }

    static String makeString(ArrayList<String> text) {
        String resultString = "";
        for (String s: text) {
            resultString += "<b> " + s + "</b><br/>";
        }
        return resultString;
    }
}
