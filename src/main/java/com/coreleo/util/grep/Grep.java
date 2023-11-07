/*
 * @(#)Grep.java	1.3 01/12/13
 * Search a list of files for lines that match a given regular-expression
 * pattern.  Demonstrates NIO mapped byte buffers, charsets, and regular
 * expressions.
 *
 * Copyright 2001-2002 Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or 
 * without modification, are permitted provided that the following 
 * conditions are met:
 * 
 * -Redistributions of source code must retain the above copyright  
 * notice, this  list of conditions and the following disclaimer.
 * 
 * -Redistribution in binary form must reproduct the above copyright 
 * notice, this list of conditions and the following disclaimer in 
 * the documentation and/or other materials provided with the 
 * distribution.
 * 
 * Neither the name of Oracle and/or its affiliates. or the names of 
 * contributors may be used to endorse or promote products derived 
 * from this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any 
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND 
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY 
 * EXCLUDED. SUN AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY 
 * DAMAGES OR LIABILITIES  SUFFERED BY LICENSEE AS A RESULT OF  OR 
 * RELATING TO USE, MODIFICATION OR DISTRIBUTION OF THE SOFTWARE OR 
 * ITS DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE 
 * FOR ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, 
 * SPECIAL, CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER 
 * CAUSED AND REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF 
 * THE USE OF OR INABILITY TO USE SOFTWARE, EVEN IF SUN HAS BEEN 
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You acknowledge that Software is not designed, licensed or 
 * intended for use in the design, construction, operation or 
 * maintenance of any nuclear facility. 
 */

package com.coreleo.util.grep;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.coreleo.util.IOUtil;
import com.coreleo.util.RegexUtil;

/**
 * @NOT_THREAD_SAFE - Instances should not be shared by multiple threads.
 * 
 *                  Functionality added to original oracle version 
 *                  1. Scan for more than one pattern at a time 
 *                  2. Returns a list of information objects (called GrepMatch) instead of printing to the screen
 * 
 */
public class Grep
{

	// Charset and decoder for ISO-8859-15
	private static final Charset CHARSET = Charset.forName("ISO-8859-15");

	// Pattern used to parse lines
	private static final Pattern LINE_PATTERN = Pattern.compile(".*\r?\n");

	// The input pattern that we're looking for
	private final List<Pattern> patterns = new ArrayList<Pattern>();

	public Grep()
	{
		super();
	}

	/**
	 * 
	 * @param pat
	 *            - The regular expression pattern to match.
	 */
	public void addInputPattern(String pat)
	{
		try
		{
			patterns.add(Pattern.compile(pat));

		}
		catch (final PatternSyntaxException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Search for occurrences of the input pattern in the given file
	 */
	public List<GrepMatch> search(File f) throws IOException
	{
		FileInputStream fis = null;
		FileChannel fc = null;

		try
		{
			// Open the file and then get a channel from the stream
			fis = new FileInputStream(f);
			fc = fis.getChannel();

			// Get the file's size and then map it into memory
			final int sz = (int) fc.size();
			final MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, sz);

			// Decode the file into a char buffer
			final CharsetDecoder decoder = CHARSET.newDecoder();
			final CharBuffer cb = decoder.decode(bb);

			// Perform the search
			return search(f, cb);
		}
		finally
		{
			IOUtil.close(fc);
			IOUtil.close(fis);
		}

	}

	/*
	 * Use the linePattern to break the given CharBuffer into lines, applying the input patterns to each line to see if we have a
	 * match
	 */
	private List<GrepMatch> search(File f, CharBuffer cb)
	{
		final List<GrepMatch> list = new ArrayList<GrepMatch>();
		final Matcher lineMatcher = LINE_PATTERN.matcher(cb); // Line matcher

		final Map<Pattern, Matcher> matcherCache = new HashMap<Pattern, Matcher>();
		Matcher pm = null; // Pattern matcher
		int lines = 0;
		CharSequence previousLine = null;

		//  Use a look ahead pattern to get the next line
		final Matcher nextLineMatcher = LINE_PATTERN.matcher(cb); // next line matcher
		nextLineMatcher.find(); // start one line ahead
		CharSequence nextLine = null;

		while (lineMatcher.find())
		{
			if (nextLineMatcher.find())
			{
				nextLine = nextLineMatcher.group();
			}
			else
			{
				nextLine = null;
			}

			lines++;
			final CharSequence cs = lineMatcher.group(); // The current line
			for (final Pattern p : patterns)
			{
				pm = matcherCache.get(p);

				if (pm == null)
				{
					pm = p.matcher(cs);
					matcherCache.put(p, pm);
				}
				else
				{
					pm.reset(cs);
				}

				if (pm.find())
				{
					list.add(new GrepMatch(pm.pattern().pattern(), f.getPath(), lines, toString(cs), toString(previousLine), toString(nextLine)));
				}

			}

			previousLine = cs;

			if (lineMatcher.end() == cb.limit())
			{
				break;
			}

		}

		return list;
	}

	private String toString(CharSequence cs)
	{
		if (cs == null)
		{
			return null;
		}

		return cs.toString();
	}

	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			System.err.println("Usage: java Grep thepattern thefilename ...");
			return;
		}
		final Grep grep = new Grep();
		grep.addInputPattern(RegexUtil.globToRegex(args[0]));
		grep.addInputPattern(RegexUtil.globToRegex("*<c:out*"));

		for (int i = 1; i < args.length; i++)
		{
			final File f = new File(args[i]);
			try
			{
				final List<GrepMatch> list = grep.search(f);
				for (final GrepMatch info : list)
				{
					System.out.print(info.getPattern() + " " + info.getFilePath() + ":" + info.getLineNumber() + ":" + info.getLine());
				}
			}
			catch (final IOException x)
			{
				System.err.println(f + ": " + x);
			}
		}
	}

}