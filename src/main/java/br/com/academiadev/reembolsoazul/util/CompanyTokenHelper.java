package br.com.academiadev.reembolsoazul.util;

import java.util.Random;

import org.springframework.stereotype.Component;

import br.com.academiadev.reembolsoazul.model.UserType;

@Component
public class CompanyTokenHelper {

	private static Random randomGenerator = new Random();

	public static String generateToken(String companyName, UserType userType) {
		String baseString = companyName + userType.toString() + randomGenerator.nextInt();

		int hashCode = baseString.hashCode();
		String tokenString = Integer.toString(hashCode);

		return tokenString;
	}
}
