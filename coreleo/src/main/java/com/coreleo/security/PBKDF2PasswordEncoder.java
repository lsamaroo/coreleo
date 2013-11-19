package com.coreleo.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * 
 * @deprecated in favor of PBKDF2PasswordEncoder2 which adds the salt 
 * directly to the password hash so that you don't have to worry about 
 * storing the salt in a separate field.
 *
 */
@Deprecated
public class PBKDF2PasswordEncoder
{

	public boolean authenticate(String attemptedPassword, byte[] encryptedPassword, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		// Encrypt the clear-text password using the same salt that was used to
		// encrypt the original password
		final byte[] encryptedAttemptedPassword = encode(attemptedPassword, salt);

		// Authentication succeeds if encrypted password that the user entered
		// is equal to the stored hash
		return Arrays.equals(encryptedPassword, encryptedAttemptedPassword);
	}

	public byte[] encode(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		// PBKDF2 with SHA-1 as the hashing algorithm. Note that the NIST
		// specifically names SHA-1 as an acceptable hashing algorithm for PBKDF2
		final String algorithm = "PBKDF2WithHmacSHA1";
		// SHA-1 generates 160 bit hashes, so that's what makes sense here
		final int derivedKeyLength = 160;
		// Pick an iteration count that works for you. The NIST recommends at
		// least 1,000 iterations:
		// http://csrc.nist.gov/publications/nistpubs/800-132/nist-sp800-132.pdf
		// iOS 4.x reportedly uses 10,000:
		// http://blog.crackpassword.com/2010/09/smartphone-forensics-cracking-blackberry-backup-passwords/
		final int iterations = 20000;

		final KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, derivedKeyLength);

		final SecretKeyFactory f = SecretKeyFactory.getInstance(algorithm);

		return f.generateSecret(spec).getEncoded();
	}

	public byte[] generateSalt() throws NoSuchAlgorithmException
	{
		// VERY important to use SecureRandom instead of just Random
		final SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

		// Generate a 8 byte (64 bit) salt as recommended by RSA PKCS5
		final byte[] salt = new byte[8];
		random.nextBytes(salt);

		return salt;
	}

	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		final PBKDF2PasswordEncoder service = new PBKDF2PasswordEncoder();
		final byte[] salt = service.generateSalt();
		final byte[] password = service.encode("password", salt);
		System.out.println("salt=" + new String(salt) + " password=" + new String(password));
	}
}
