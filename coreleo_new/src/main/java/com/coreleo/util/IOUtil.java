/**
 * 
 */
package com.coreleo.util;

import java.io.*;
import java.net.*;
import java.util.*;

import com.coreleo.SimpleException;

/**
 * @author Leon Samaroo
 * 
 */
public final class IOUtil {

	private IOUtil() {
		super();
	}
	
	
	public final static boolean moveFile( File f, String dir){
		return renameFile( f, new File(dir, f.getName() ) );
	}
	
	
	public final static boolean renameFile( File f, String newName ){
		String parentPath = f.getParent();
		if( parentPath != null ){
			return renameFile( f, new File(parentPath, newName) );
		}
		else{
			return renameFile( f, new File(newName) );
		}
	}
	
	
	public final static boolean renameFile( File src, File dst ){
		if( src == null || dst == null ){
			return false;
		}	
		return src.renameTo(dst);
	}
	

	@Deprecated
	public static final void outputFile(File sourceFile, OutputStream out ) throws FileNotFoundException, IOException {
		outputFileToStream(sourceFile, out, 4); // 4k buffer
	}
	
	@Deprecated
	public static final void outputFile(File sourceFile, OutputStream out, int bufferSize ) throws FileNotFoundException, IOException {
		outputFileToStream(sourceFile, out, bufferSize); 
	}
	
	public static final void outputFileToStream(File sourceFile, OutputStream out ) throws FileNotFoundException, IOException {
		outputFileToStream(sourceFile, out, 4); // 4k buffer
	}
	
	
	public static final void outputFileToStream(File sourceFile, OutputStream out, int bufferSize ) throws FileNotFoundException, IOException {
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(sourceFile));
			byte[] buf = new byte[bufferSize * 1024];
			int bytesRead;
			while ((bytesRead = in.read(buf)) != -1) {
				out.write(buf, 0, bytesRead);
			}
		} 
		finally {
			close(in);
		}
	}

	/**
	 * @deprecated
	 * @return - the properties or null if an exception is encountered.
	 */
	public static final Properties getProperties(String filename, Class myClass) {
		InputStream is = myClass.getResourceAsStream(filename);
		Properties properties = new Properties();
		try {
			properties.load(is);
			return properties;
		} 
		catch (Exception e) {
			throw new SimpleException(e);
		} 
		finally {
			close(is);
		}
	}

	/**
	 * 
	 * @return - the file or null if an exception is encountered or file is not
	 *         found.
	 */
	public static final File getFile(String fileName, Class myClass) {
		URL url = myClass.getClassLoader().getResource(fileName);
		
		if( url == null ){
			throw new SimpleException("Unable to locate file " + fileName );
		}

		try {
			return new File(URLDecoder.decode(url.getPath(), Constants.UTF8));
		} 
		catch (Exception e) {
			throw new SimpleException(e);
		}
	}
	
	

	public static final boolean writeToFile(String content, String fileName) {
		BufferedWriter out = null;
		try {
			File file = new File(fileName);
		    out = new BufferedWriter(new FileWriter(file));
			out.write(content);
			return true;
		} 
		catch (IOException e) {
			throw new SimpleException(e);
		}
		finally{
			close(out);
		}
	}

	
	public static final void close(Reader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		} 
		catch (IOException ioe) {
			LogUtil.error("IOUtil: close - Error closing stream", ioe);
		}
	}

	
	public static final void close(InputStream stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} 
		catch (IOException ioe) {
			LogUtil.error("IOUtil: close - Error closing stream", ioe);
		}
	}
	
	
	public static final void close(OutputStream stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} 
		catch (IOException ioe) {
			LogUtil.error("IOUtil: close - Error closing stream", ioe);
		}
	}
	
	
	
	public static final void close(Writer writer) {
		try {
			if (writer != null) {
				writer.close();
			}
		} 
		catch (IOException ioe) {
			LogUtil.error("IOUtil: close - Error closing writer", ioe);
		}
	}
	
	
	public static final class DirectoriesOnlyFilenameFilter implements FilenameFilter{
		public boolean accept(File dir, String name) {
			return new File(dir, name).isDirectory();
		}
	}
	
	
	public static final class DirectoriesOnlyFileFilter implements FileFilter{
		public boolean accept(File pathname) {
			return pathname.isDirectory();
		}
	}
	
	
	
	public static final class FilesOnlyFilenameFilter implements FilenameFilter{
		public boolean accept(File dir, String name) {
			return (new File(dir, name).isFile());
		}
	}
	
	
	
	public static final class FilesOnlyFileFilter implements FileFilter{
		public boolean accept(File pathname) {
			return pathname.isFile();
		}
	}

}
