package com.coreleo.thirdparty;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.coreleo.SimpleException;
import com.coreleo.util.ReflectionUtil;

public class ApacheCommonsUtil {
	private final static String DISKFILEITEMFACTORY = "org.apache.commons.fileupload.disk.DiskFileItemFactory";
	private final static String SERVLETFILEUPLOAD = "org.apache.commons.fileupload.servlet.ServletFileUpload";
	private final static String PARSEREQUEST = "parseRequest";

	public static List parseMultipartFormData(HttpServletRequest request) throws SimpleException {
		try {
			Object fileItemFactory = ReflectionUtil.newInstance(DISKFILEITEMFACTORY);
			Object servletFileUpload = ReflectionUtil.newInstance(SERVLETFILEUPLOAD, fileItemFactory);
			return (List) ReflectionUtil.invoke(servletFileUpload,PARSEREQUEST, request);
		} 
		catch (Exception e) {
			throw new SimpleException(e);
		}
	}

}
