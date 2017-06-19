package com.gjjf.framework.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class RequestBodyWrapperFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if ((request instanceof HttpServletRequest)) {
			ServletRequest requestWrapper = new SignVerifyPrepareHttpServletRequestWrapper((HttpServletRequest) request);
			chain.doFilter(requestWrapper, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig config) throws ServletException {

	}

	private static class SignVerifyPrepareHttpServletRequestWrapper extends HttpServletRequestWrapper {
		private byte[] body;
		private String charsetName;

		public SignVerifyPrepareHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
			this.charsetName = request.getCharacterEncoding();
			try {
				this.body = IOUtils.toByteArray(request.getInputStream());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public BufferedReader getReader() throws IOException {
			return new BufferedReader(new InputStreamReader(getInputStream(), this.charsetName));
		}

		public ServletInputStream getInputStream() throws IOException {
			final ByteArrayInputStream bais = new ByteArrayInputStream(this.body);
			return new ServletInputStream() {
				public int read() throws IOException {
					return bais.read();
				}

				public boolean isFinished() {
					return false;
				}

				public boolean isReady() {
					return false;
				}

				public void setReadListener(ReadListener readListener) {
				}
			};
		}
	}

}
