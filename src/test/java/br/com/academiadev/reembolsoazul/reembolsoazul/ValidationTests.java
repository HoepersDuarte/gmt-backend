package br.com.academiadev.reembolsoazul.reembolsoazul;

import org.junit.Assert;
import org.junit.Test;

import br.com.academiadev.reembolsoazul.util.ValidationsHelper;

public class ValidationTests {
	@Test
	public void emailValidationTest() {
		Assert.assertTrue(ValidationsHelper.emailValidation("bla@bla.com.br") == true
				&& ValidationsHelper.emailValidation("a@b.cd") == true
				&& ValidationsHelper.emailValidation("bla_bla.com.br") == false
				&& ValidationsHelper.emailValidation("bla@bla.") == false
				&& ValidationsHelper.emailValidation("@bla.com.br") == false
				&& ValidationsHelper.emailValidation(null) == false && ValidationsHelper.emailValidation("") == false);
	}

	@Test
	public void passwordFormatValidationTest() {
		Assert.assertTrue(ValidationsHelper.passwordFormatValidation("1aA+1234") == true
				&& ValidationsHelper.passwordFormatValidation("147z258Z369==") == true
				&& ValidationsHelper.passwordFormatValidation("1aA+123") == false
				&& ValidationsHelper.passwordFormatValidation("1aA+56789012345678901") == false
				&& ValidationsHelper.passwordFormatValidation("1AA+1234") == false
				&& ValidationsHelper.passwordFormatValidation("1aa+1234") == false
				&& ValidationsHelper.passwordFormatValidation("1aAA1234") == false);
	}
}
