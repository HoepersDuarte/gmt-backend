package br.com.academiadev.reembolsoazul.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationsHelper {
	public static boolean emailValidation(String email) {
		if(email != null && email.length() > 0) {
			String emailExpression = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
			Pattern pattern = Pattern.compile(emailExpression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			return matcher.matches();
		}
		return false;
	}
	
	public static boolean passwordFormatValidation(String password) {
		//length: 8-20
		//at least 1 digit
		//at least 1 lower case letter
		//at least 1 upper case letter
		//at least 1 special character
		//no whitespaces are allowed
		
		int minLength = 8;
		int maxLength = 20;
		if(password != null && password.length() >= minLength && password.length() <= maxLength) {
			return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
		}
		return false;
	}
}
