package com.coreleo.util.filecrawler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A CrawlAction which wraps multiple CrawlAction. This allows you to execute multiple actions.
 * 
 * @author Leon Samaroo
 * 
 */
public class CrawlActionList implements CrawlAction
{

	private final List<CrawlAction> actions = new ArrayList<CrawlAction>();

	@Override
	public void doAction(File file)
	{
		for (final CrawlAction action : actions)
		{
			action.doAction(file);
		}

	}

	public void addAction(CrawlAction action)
	{
		actions.add(action);
	}

}
