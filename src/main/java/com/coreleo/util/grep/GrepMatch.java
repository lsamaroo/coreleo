package com.coreleo.util.grep;

/**
 * @author lsamaroo
 * 
 */
public final class GrepMatch implements Comparable<GrepMatch>
{
	private final String pattern;
	private final String filePath;
	private final int lineNumber;
	private final String line;
	private final String previousLine;
	private final String nextLine;

	public GrepMatch(String pattern, String filePath, int lineNumber, String line, String previousLine, String nextLine)
	{
		super();
		this.pattern = pattern;
		this.filePath = filePath;
		this.lineNumber = lineNumber;
		this.line = line;
		this.previousLine = previousLine;
		this.nextLine = nextLine;
	}

	@Override
	public int compareTo(GrepMatch other)
	{
		int compare = filePath.compareTo(other.getFilePath());
		if (compare != 0)
		{
			return compare;
		}

		compare = lineNumber - this.getLineNumber();
		if (compare != 0)
		{
			return compare;
		}

		return line.compareTo(other.getLine());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((filePath == null) ? 0 : filePath.hashCode());
		result = (prime * result) + lineNumber;
		result = (prime * result) + ((line == null) ? 0 : line.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}

		final GrepMatch other = (GrepMatch) obj;
		if (filePath == null)
		{
			if (other.filePath != null)
			{
				return false;
			}
		}
		else if (!filePath.equals(other.filePath))
		{
			return false;
		}

		if (lineNumber != other.lineNumber)
		{
			return false;
		}

		if (line == null)
		{
			if (other.line != null)
			{
				return false;
			}
		}
		else if (!line.equals(other.line))
		{
			return false;
		}

		return true;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public int getLineNumber()
	{
		return lineNumber;
	}

	public String getLine()
	{
		return line;
	}

	public String getPattern()
	{
		return pattern;
	}

	public String getPreviousLine()
	{
		return previousLine;
	}

	public String getNextLine()
	{
		return nextLine;
	}

	@Override
	public String toString()
	{
		return "GrepMatch [File=" + filePath + ", lineNumber=" + lineNumber + ", line=" + line + " previousLine=" + previousLine + "nextLine=" + nextLine
				+ " ]";
	}

}