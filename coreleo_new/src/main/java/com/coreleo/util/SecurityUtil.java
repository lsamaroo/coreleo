package com.coreleo.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import sun.misc.BASE64Encoder;

public final class SecurityUtil
{

	private SecurityUtil()
	{
		// noop
	}

	public static String generateCSRFToken(int size) throws NoSuchAlgorithmException
	{
		SecureRandom sr = null;
		final byte[] random = new byte[size];
		final BASE64Encoder encoder = new BASE64Encoder();

		sr = SecureRandom.getInstance("SHA1PRNG");
		sr.nextBytes(random);
		return encoder.encode(random);
	}

}
