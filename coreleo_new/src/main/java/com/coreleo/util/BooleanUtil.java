/**
 * 
 */
package com.coreleo.util;

/**
 * @author Leon Samaroo
 * 
 */
public class BooleanUtil
{

	private BooleanUtil()
	{
		super();
	}

	public static final boolean isEqual(Boolean x1, boolean x2)
	{
		return BooleanUtil.isEqual(x1, Boolean.valueOf(x2));
	}

	public static final boolean isEqual(Boolean x1, Boolean x2)
	{
		if (x1 == null || x2 == null)
		{
			return false;
		}

		return x1.equals(x2);
	}

	public static final boolean isNotEqual(Boolean x1, boolean x2)
	{
		return (!isEqual(x1, x2));
	}

	public static final boolean isNotEqual(Boolean x1, Boolean x2)
	{
		return (!isEqual(x1, x2));
	}

	public static final boolean toBoolean(Boolean x)
	{
		if (x == null)
		{
			throw new IllegalArgumentException("The string must not be null");
		}

		return x.booleanValue();
	}

	public static final boolean toBoolean(Object x)
	{
		if (x == null)
		{
			throw new IllegalArgumentException("The object must not be null");
		}

		return Boolean.valueOf(x.toString().trim()).booleanValue();
	}

	public static final boolean toBoolean(Object x, boolean defaultValue)
	{
		if (x == null)
		{
			return defaultValue;
		}

		try
		{
			return toBoolean(x);
		}
		catch (final Exception e)
		{
			return defaultValue;
		}
	}

	/**
	 * 
	 * @throws IllegalArgumentException
	 *             if the default value is null.
	 */
	public static final boolean toBoolean(Object x, Boolean defaultValue)
	{
		if (defaultValue == null)
		{
			throw new IllegalArgumentException("The default value cannot be null.");
		}

		if (x == null)
		{
			return defaultValue.booleanValue();
		}

		try
		{
			return toBoolean(x);
		}
		catch (final Exception e)
		{
			return defaultValue.booleanValue();
		}
	}

	public static final Boolean toBooleanObject(Object x)
	{
		if (x == null)
		{
			throw new IllegalArgumentException("The string must not be null");
		}

		return Boolean.valueOf(x.toString().trim());
	}

	public static final Boolean toBooleanObject(Object x, boolean defaultValue)
	{
		if (x == null)
		{
			return toBooleanObject(defaultValue);
		}

		try
		{
			return toBooleanObject(x);
		}
		catch (final Exception e)
		{
			return toBooleanObject(defaultValue);
		}
	}

	public static final Boolean toBooleanObject(Object x, Boolean defaultValue)
	{
		if (x == null)
		{
			return defaultValue;
		}

		try
		{
			return toBooleanObject(x);
		}
		catch (final Exception e)
		{
			return defaultValue;
		}
	}

	public static final Boolean toBooleanObject(boolean x)
	{
		return (x ? Boolean.TRUE : Boolean.FALSE);
	}

	/**
	 * 
	 * If the arg is yes or y (case doesn't matter) then it returns Boolean.TRUE, otherwise Boolean.False
	 * 
	 */
	public static final Boolean toBooleanObjectIgnoreCaseYesNoYN(Object yesNoYNString)
	{
		if (yesNoYNString == null)
		{
			return Boolean.FALSE;
		}

		final String trimmedCaseInsensitiveString = yesNoYNString.toString().trim().toLowerCase();
		if (trimmedCaseInsensitiveString.equals("yes") || trimmedCaseInsensitiveString.equals("y"))
		{
			return Boolean.TRUE;
		}

		return Boolean.FALSE;

	}

	public static final boolean toBooleanIgnoreCaseYesNoYN(Object yesNoYNString)
	{
		return toBooleanObjectIgnoreCaseYesNoYN(yesNoYNString).booleanValue();
	}

	public static final boolean isTrueFalseString(String string)
	{
		if (StringUtil.isEmpty(string))
		{
			return false;
		}

		if (string.equalsIgnoreCase("true") || string.equalsIgnoreCase("false"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static final String toStringYesNo(Boolean bool)
	{
		if (bool == null)
		{
			return "No";
		}

		if (bool.booleanValue())
		{
			return "Yes";
		}
		else
		{
			return "No";
		}
	}

	public static final String toStringYesNo(boolean bool)
	{
		if (bool)
		{
			return "Yes";
		}
		else
		{
			return "No";
		}
	}

	public static final String toStringYN(Boolean bool)
	{
		if (bool == null)
		{
			return "N";
		}

		if (bool.booleanValue())
		{
			return "Y";
		}
		else
		{
			return "N";
		}
	}

	public static final String toStringYN(boolean bool)
	{
		if (bool)
		{
			return "Y";
		}
		else
		{
			return "N";
		}
	}

}
