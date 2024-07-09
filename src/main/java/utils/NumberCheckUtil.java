package utils;

public class NumberCheckUtil {
	public static boolean checkNumber ( String data ) {
		try {
			Long.parseLong(data);
			return true;
		}
		catch ( NumberFormatException e  ) {
			return false;
		}
	}
}
