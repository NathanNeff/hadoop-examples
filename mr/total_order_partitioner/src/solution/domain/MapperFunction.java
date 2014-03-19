package solution.domain;

import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.io.Text;

/**
 * This is a helper class, which encapsulates the logic of our mapper in a
 * "non hadoop" class which can be tested even without MRUnit.
 * 
 * @author training
 * 
 */
public class MapperFunction {

	public static List<String> months = Arrays.asList("Jan", "Feb", "Mar",
			"Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

	static String[] kv = new String[2];

	/**
	 * Example input line: 96.7.4.14 - - [24/Apr/2011:04:20:11 -0400]
	 * "GET /cat.jpg HTTP/1.1" 200 12433
	 * 
	 */
	public static String[] getKVPair(String value) {

		kv[0] = null;
		kv[1] = null;

		/*
		 * Split the input line into space-delimited fields.
		 */
		String[] fields = value.split(" ");

		if (fields.length > 3) {

			/*
			 * Save the first field in the line as the IP address.
			 */
			// String ip = fields[0];
			kv[0]  = fields[0];

			/*
			 * The fourth field contains [dd/Mmm/yyyy:hh:mm:ss]. Split the
			 * fourth field into "/" delimited fields. The second of these
			 * contains the month.
			 */
			String[] dtFields = fields[3].split("/");

			if (dtFields.length > 1) {

				String theMonth = dtFields[1];

				/* check if it's a valid month, if so, write it out */
				if (months.contains(theMonth)) {
					kv[1] = theMonth;
				}
			}
		}

		return kv;
	}
}
