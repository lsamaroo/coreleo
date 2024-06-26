/**
 * 
 */
package com.coreleo.util;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;

import com.coreleo.SimpleException;

/**
 * @author Leon Samaroo
 * 
 */
@SuppressWarnings({"rawtypes"})
public final class IOUtil
{

	private IOUtil()
	{
		super();
	}

	public static void close(Closeable c)
	{
		if (c == null)
		{
			return;
		}

		try
		{
			c.close();
		}
		catch (final Exception e)
		{
			LogUtil.error(IOUtil.class, e);
		}
	}

	public static boolean createFile(File file)
	{
		if (file.exists())
		{
			return true;
		}

		try
		{
			if (file.isDirectory())
			{
				return file.mkdirs();
			}
			else
			{
				final File parent = file.getParentFile();
				if (!parent.exists())
				{
					parent.mkdirs();
				}
				return file.createNewFile();
			}
		}
		catch (final IOException e)
		{
			LogUtil.error(IOUtil.class, e);
			return false;
		}
	}

	public final static boolean moveFile(File f, String dir)
	{
		return renameFile(f, new File(dir, f.getName()));
	}

	public final static boolean renameFile(File f, String newName)
	{
		final String parentPath = f.getParent();
		if (parentPath != null)
		{
			return renameFile(f, new File(parentPath, newName));
		}
		else
		{
			return renameFile(f, new File(newName));
		}
	}

	public final static boolean renameFile(File src, File dst)
	{
		if (src == null || dst == null)
		{
			return false;
		}
		return src.renameTo(dst);
	}

	@Deprecated
	public static final void outputFile(File sourceFile, OutputStream out) throws FileNotFoundException, IOException
	{
		outputFileToStream(sourceFile, out, 4); // 4k buffer
	}

	@Deprecated
	public static final void outputFile(File sourceFile, OutputStream out, int bufferSize) throws FileNotFoundException, IOException
	{
		outputFileToStream(sourceFile, out, bufferSize);
	}

	public static final void outputFileToStream(File sourceFile, OutputStream out) throws FileNotFoundException, IOException
	{
		outputFileToStream(sourceFile, out, 4); // 4k buffer
	}

	public static final void outputFileToStream(File sourceFile, OutputStream out, int bufferSize) throws FileNotFoundException, IOException
	{
		InputStream in = null;
		try
		{
			in = new BufferedInputStream(new FileInputStream(sourceFile));
			final byte[] buf = new byte[bufferSize * 1024];
			int bytesRead;
			while ((bytesRead = in.read(buf)) != -1)
			{
				out.write(buf, 0, bytesRead);
			}
		}
		finally
		{
			close(in);
		}
	}

	/**
	 * @deprecated
	 * @return - the properties or null if an exception is encountered.
	 */
	@Deprecated
	public static final Properties getProperties(String filename, Class myClass)
	{
		final InputStream is = myClass.getResourceAsStream(filename);
		final Properties properties = new Properties();
		try
		{
			properties.load(is);
			return properties;
		}
		catch (final Exception e)
		{
			throw new SimpleException(e);
		}
		finally
		{
			close(is);
		}
	}

	/**
	 * 
	 * @return - the file or null if an exception is encountered or file is not found.
	 */
	public static final File getFile(String fileName, Class myClass)
	{
		final URL url = myClass.getClassLoader().getResource(fileName);

		if (url == null)
		{
			throw new SimpleException("Unable to locate file " + fileName);
		}

		try
		{
			return new File(URLDecoder.decode(url.getPath(), Constants.UTF8));
		}
		catch (final Exception e)
		{
			throw new SimpleException(e);
		}
	}

	public static final boolean writeToFile(String content, String fileName)
	{
		BufferedWriter out = null;
		try
		{
			final File file = new File(fileName);
			out = new BufferedWriter(new FileWriter(file));
			out.write(content);
			return true;
		}
		catch (final IOException e)
		{
			throw new SimpleException(e);
		}
		finally
		{
			close(out);
		}
	}

	public static final class DirectoriesOnlyFilenameFilter implements FilenameFilter
	{
		@Override
		public boolean accept(File dir, String name)
		{
			return new File(dir, name).isDirectory();
		}
	}

	public static final class DirectoriesOnlyFileFilter implements FileFilter
	{
		@Override
		public boolean accept(File pathname)
		{
			return pathname.isDirectory();
		}
	}

	public static final class FilesOnlyFilenameFilter implements FilenameFilter
	{
		@Override
		public boolean accept(File dir, String name)
		{
			return (new File(dir, name).isFile());
		}
	}

	public static final class FilesOnlyFileFilter implements FileFilter
	{
		@Override
		public boolean accept(File pathname)
		{
			return pathname.isFile();
		}
	}

}
