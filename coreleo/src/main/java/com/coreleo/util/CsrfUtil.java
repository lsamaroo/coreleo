package com.coreleo.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.xml.bind.DatatypeConverter;

public final class CsrfUtil
{

	private CsrfUtil()
	{
		// noop
	}


	public static String generateCSRFToken(int size) throws NoSuchAlgorithmException
	{
		SecureRandom sr = null;
		final byte[] random = new byte[size];
		sr = SecureRandom.getInstance("SHA1PRNG");
		sr.nextBytes(random);
		return DatatypeConverter.printBase64Binary(random);
		//final BASE64Encoder encoder = new BASE64Encoder();
		//return encoder.encode(random);
	}

}
