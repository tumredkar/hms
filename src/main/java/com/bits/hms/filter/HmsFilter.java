package com.bits.hms.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class HmsFilter extends OncePerRequestFilter {

	private final static Logger log = LoggerFactory.getLogger(HmsFilter.class);

	private static final List<MediaType> VISIBLE_TYPES = Arrays.asList(MediaType.valueOf("text/*"),
			MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML,
			MediaType.valueOf("application/*+json"), MediaType.valueOf("application/*+xml"),
			MediaType.MULTIPART_FORM_DATA);

	/**
	 * List of HTTP headers whose values should not be logged.
	 */
	private static final List<String> SENSITIVE_HEADERS = Arrays.asList("authorization", "proxy-authorization");

	private boolean enabled = true;

	@ManagedOperation(description = "Enable logging of HTTP requests and responses")
	public void enable() {
		this.enabled = true;
	}

	@ManagedOperation(description = "Disable logging of HTTP requests and responses")
	public void disable() {
		this.enabled = false;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
	}

	protected void doFilterWrapped(CachedBodyHttpServletRequest request, ContentCachingResponseWrapper response,
			FilterChain filterChain) throws ServletException, IOException {

		StringBuilder msg = new StringBuilder();

		try {
			beforeRequest(request, response, msg);
			filterChain.doFilter(request, response);
		} finally {
			afterRequest(request, response, msg);
			response.copyBodyToResponse();
		}
	}

	protected void beforeRequest(CachedBodyHttpServletRequest request, ContentCachingResponseWrapper response,
			StringBuilder msg) throws IOException {
		if (enabled && log.isInfoEnabled()) {
			log.info("-- REQUEST --");
			logRequestHeader(request, request.getRemoteAddr() + "|>", msg);
			logRequestBody(request, request.getRemoteAddr() + "|>", msg);
		}
	}

	protected void afterRequest(CachedBodyHttpServletRequest request, ContentCachingResponseWrapper response,
			StringBuilder msg) {
		if (enabled && log.isInfoEnabled()) {
			log.info("-- RESPONSE --");
			logResponse(response, request.getRemoteAddr() + "|<", msg);
		}
	}

	private static void logRequestHeader(CachedBodyHttpServletRequest request, String prefix, StringBuilder msg) {
		String queryString = request.getQueryString();
		if (queryString == null) {
			log.info(String.format("%s %s %s", prefix, request.getMethod(), request.getRequestURI()));
		} else {
			log.info(String.format("%s %s %s?%s", prefix, request.getMethod(), request.getRequestURI(), queryString));
		}
		Collections.list(request.getHeaderNames())
				.forEach(headerName -> Collections.list(request.getHeaders(headerName)).forEach(headerValue -> {
					if (isSensitiveHeader(headerName)) {
						log.info(String.format("%s %s: %s", prefix, headerName, "*******"));
					} else {
						log.info(String.format("%s %s: %s", prefix, headerName, headerValue));
					}
				}));
		log.info(prefix);
	}

	private static void logRequestBody(CachedBodyHttpServletRequest request, String prefix, StringBuilder msg)
			throws IOException {
		String content = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		if (content != null && content.length() > 0) {
			logContent(content.getBytes(), request.getContentType(), request.getCharacterEncoding(), prefix, msg);
		}
	}

	private static void logResponse(ContentCachingResponseWrapper response, String prefix, StringBuilder msg) {
		int status = response.getStatus();
		log.info(String.format("%s %s %s", prefix, status, HttpStatus.valueOf(status).getReasonPhrase()));
		response.getHeaderNames().forEach(headerName -> response.getHeaders(headerName).forEach(headerValue -> {
			if (isSensitiveHeader(headerName)) {
				log.info(String.format("%s %s: %s", prefix, headerName, "*******"));
			} else {
				log.info(String.format("%s %s: %s", prefix, headerName, headerValue));
			}
		}));
		log.info(prefix);
		byte[] content = response.getContentAsByteArray();
		if (content.length > 0) {
			logContent(content, response.getContentType(), response.getCharacterEncoding(), prefix, msg);
		}
	}

	private static void logContent(byte[] content, String contentType, String contentEncoding, String prefix,
			StringBuilder msg) {
		MediaType mediaType = MediaType.valueOf(contentType);
		boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
		if (visible) {
			try {
				String contentString = new String(content, contentEncoding);
//				Stream.of(contentString.split("\r\n|\r|\n")).forEach(line -> log.info(prefix + " " + line));
				contentString = contentString.replaceAll("[\\t\\n\\r]+", " ");
				log.info(contentString);
			} catch (UnsupportedEncodingException e) {
				log.info(String.format("%s [%d bytes content]", prefix, content.length));
			}
		} else {
			log.info(String.format("%s [%d bytes content]", prefix, content.length));
		}
	}

	/**
	 * Determine if a given header name should have its value logged.
	 * 
	 * @param headerName HTTP header name.
	 * @return True if the header is sensitive (i.e. its value should <b>not</b> be
	 *         logged).
	 */
	private static boolean isSensitiveHeader(String headerName) {
		return SENSITIVE_HEADERS.contains(headerName.toLowerCase());
	}

	private static CachedBodyHttpServletRequest wrapRequest(HttpServletRequest request) throws IOException {
		return new CachedBodyHttpServletRequest(request);
	}

	private static ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
		if (response instanceof ContentCachingResponseWrapper) {
			return (ContentCachingResponseWrapper) response;
		} else {
			return new ContentCachingResponseWrapper(response);
		}
	}
}