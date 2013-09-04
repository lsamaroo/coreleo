/**
 * 
 */
package com.coreleo.gui.html;

import java.util.List;
import java.util.Map;

import com.coreleo.util.StringUtil;
import com.coreleo.util.WebUtil;

/**
 * @author Leon Samaroo
 * 
 */
@SuppressWarnings({"rawtypes"})
public class SelectGUI
{
	private String id; // id of the select element
	private String name; // name of the select element
	private List optionLabels; // a list of option labels
	private List optionValues; // a list of option values
	private boolean multiple; // select multiple
	private String size; // select size
	private Map chosen; // chosen options
	private String style; // cascading style sheets
	private String onClick; //
	private String onChange;
	private boolean disabled;
	private String clazz;
	private String title;

	public SelectGUI()
	{
		super();
		reset();
	}

	protected void reset()
	{
		name = null;
		optionLabels = null;
		optionValues = null;
		multiple = false;
		size = null;
		chosen = null;
		style = null;
		onClick = null;
		disabled = false;
		id = null;
	}

	public Map getChosen()
	{
		return this.chosen;
	}

	public void setChosen(Map chosen)
	{
		this.chosen = chosen;
	}

	public boolean isMultiple()
	{
		return this.multiple;
	}

	public void setMultiple(boolean multiple)
	{
		this.multiple = multiple;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getOnClick()
	{
		return this.onClick;
	}

	public void setOnClick(String onchange)
	{
		this.onClick = onchange;
	}

	public String getSize()
	{
		return this.size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public void setSize(int size)
	{
		this.size = String.valueOf(size);
	}

	public String getStyle()
	{
		return this.style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	public List getOptionLabels()
	{
		return this.optionLabels;
	}

	public void setOptionLabels(List optionLabels)
	{
		this.optionLabels = optionLabels;
	}

	public List getOptionValues()
	{
		return this.optionValues;
	}

	public void setOptionValues(List optionValues)
	{
		this.optionValues = optionValues;
	}

	public boolean isDisabled()
	{
		return this.disabled;
	}

	public void setDisabled(boolean disabled)
	{
		this.disabled = disabled;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOnChange()
	{
		return onChange;
	}

	public void setOnChange(String onChange)
	{
		this.onChange = onChange;
	}

	public String getClazz()
	{
		return this.clazz;
	}

	public void setClazz(String clazz)
	{
		this.clazz = clazz;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String renderGUI()
	{
		final StringBuffer buffer = new StringBuffer();

		// start building up the tag
		buffer.append("<select name=\"" + WebUtil.replaceHtmlMetaCharacters(name) + "\" ");

		if (StringUtil.isNotEmpty(id))
		{
			buffer.append("id=\"" + WebUtil.replaceHtmlMetaCharacters(id) + "\" ");
		}

		if (StringUtil.isNotEmpty(title))
		{
			buffer.append("title=\"" + WebUtil.replaceHtmlMetaCharacters(title) + "\" ");
		}

		if (multiple)
		{
			buffer.append("multiple=\"multiple\" ");
		}

		if (StringUtil.isNotEmpty(size))
		{
			buffer.append("size=\"" + WebUtil.replaceHtmlMetaCharacters(size) + "\" ");
		}

		if (StringUtil.isNotEmpty(style))
		{
			buffer.append("style=\"" + WebUtil.replaceHtmlMetaCharacters(style) + "\" ");
		}

		if (StringUtil.isNotEmpty(onClick))
		{
			buffer.append("onclick=\"" + WebUtil.replaceHtmlMetaCharacters(onClick) + "\" ");
		}
		if (StringUtil.isNotEmpty(onChange))
		{
			buffer.append("onChange=\"" + WebUtil.replaceHtmlMetaCharacters(onChange) + "\" ");
		}

		if (disabled)
		{
			buffer.append("disabled=\"disabled\" ");
		}

		if (StringUtil.isNotEmpty(clazz))
		{
			buffer.append("class=\"" + WebUtil.replaceHtmlMetaCharacters(clazz) + "\" ");
		}

		// end the starting tag
		buffer.append(">");

		if (optionLabels != null && optionValues != null)
		{
			for (int i = 0; i < optionLabels.size(); i++)
			{
				final Object oLabel = optionLabels.get(i);
				final Object oVal = optionValues.get(i);
				writeOptionElement(buffer, oLabel, oVal);
			}
		}

		buffer.append("</select>");

		return buffer.toString();

	}

	private void writeOptionElement(StringBuffer buff, Object oLabel, Object oValue)
	{
		final String label = oLabel.toString();
		// Convert the value to a String if it is not already.
		String value = (oValue != null ? oValue.toString() : null);

		// Output the option tag
		buff.append("<option");

		// Output the value if there is one specified separate from the label
		if (value != null)
		{
			buff.append(" value=\"" + WebUtil.replaceHtmlMetaCharacters(value) + "\"");
		}

		// If there is no value specified then use the label as the value, this
		// is for checking if this option is selected based on value.
		if (value == null)
		{
			value = label; // use label if value is null
		}

		// Match the VALUE of this option pair with
		// the KEY of the 'chosen' Map (We want to match <option>s on values,
		// not keys.)
		if (chosen != null && chosen.containsKey(value))
		{
			if (!multiple)
			{
				chosen.clear();
				// chosen.remove(value);
			}
			buff.append(" selected=\"selected\"");
		}
		buff.append(">");
		buff.append(WebUtil.replaceHtmlMetaCharacters(label));
		buff.append("</option>");
	}
}
