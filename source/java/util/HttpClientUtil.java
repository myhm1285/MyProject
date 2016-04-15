package com.cosquare.smp.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

/**
 * HTTP 클라이언트 유틸 클래스
 * 
 * @author 컨버전스스퀘어
 * @since 2015.01.27
 * @version 1.0
 */
public class HttpClientUtil {

	/** 로거 */
	private final static Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

	/**
	 * Multipart Boundary
	 */
	private final String MULTIPART_BOUNDARY = "--SAFETY--KEEPER--";

	/**
	 * Http Request Method
	 */
	private RequestMethod requestMethod;

	/**
	 * CloseableHttpClient
	 */
	private CloseableHttpClient client;
	/**
	 * HttpGet
	 */
	private HttpGet get;
	/**
	 * HttpPost
	 */
	private HttpPost post;
	/**
	 * HttpResponse
	 */
	private CloseableHttpResponse httpResponse;
	/**
	 * Entity
	 */
	private HttpEntity entity;
	/**
	 * ResponseBody
	 */
	private String responsebody = "";

	/**
	 * 성공여부
	 */
	private boolean isSuccess = true;

	/**
	 * 생성자 메소드<br/>
	 * 
	 * @param url
	 *            URL
	 */
	public HttpClientUtil(String url) {
		// Default Request Method : POST
		this(url, RequestMethod.POST);
	}

	/**
	 * 생성자 메소드<br/>
	 * CloseableHttpClient 객체(Default)를 생성하고 Http Method에 따라 객체를 생성한다.<br/>
	 * 
	 * @param url
	 *            URL
	 * @param requestMethod
	 *            RequqestMtehod (GET, POST)
	 */
	public HttpClientUtil(String url, RequestMethod requestMethod) {

		// setting Request Method
		this.requestMethod = requestMethod;

		// HTTP Method에 따라 객체 생성
		if (RequestMethod.GET.equals(requestMethod)) {
			// HttpGet 객체 생성
			get = new HttpGet(url);
			// CloseableHttpClienet 객체 생성 - Default
			client = HttpClients.createDefault();
		} else if (RequestMethod.POST.equals(requestMethod)) {
			// HttpPost 객체 생성
			post = new HttpPost(url);
			// CloseableHttpClienet 객체 생성 - Default
			client = HttpClients.createDefault();
		} else {
			LOGGER.error("The Request Method is not supported. : Input Request Method - {}", requestMethod);
		}
	}

	/**
	 * Header를 추가한다.
	 * 
	 * @param name
	 *            header key
	 * @param value
	 *            header value
	 */
	public void addHeader(String name, String value) {
		if (RequestMethod.GET.equals(requestMethod)) {
			get.addHeader(name, value);
		} else if (RequestMethod.POST.equals(requestMethod)) {
			post.addHeader(name, value);
		} else {
			LOGGER.error("The Request Method is not supported. : Input Request Method - {}", requestMethod);
		}
	}

	/**
	 * HttpPost 객체에 파라미터를 세팅한다.
	 * 
	 * @param params
	 *            파라미터
	 */
	public void setParams(List<NameValuePair> params) {
		if (RequestMethod.POST.equals(requestMethod)) {
			if (post.getHeaders("Content-Type").length == 0) {
				post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			}
			UrlEncodedFormEntity entity;
			try {
				entity = new UrlEncodedFormEntity(params, "UTF-8");
				post.setEntity(entity);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				isSuccess = false;
			}
		} else {
			LOGGER.error("This function is only available for the POST method. : Input Request Method - {}", requestMethod);
		}
	}

	/**
	 * HttpPost 객체에 파라미터를 세팅한다.
	 * 
	 * @param params
	 *            파라미터
	 */
	public void setParams(JSONObject params) {
		if (RequestMethod.POST.equals(requestMethod)) {
			if (post.getHeaders("Content-Type").length == 0) {
				post.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
			}
			StringEntity entity;
			try {
				entity = new StringEntity(params.toJSONString(), ContentType.APPLICATION_JSON);
				post.setEntity(entity);
			} catch (UnsupportedCharsetException e) {
				e.printStackTrace();
				isSuccess = false;
			}
		} else {
			LOGGER.error("This function is only available for the POST method. : Input Request Method - {}", requestMethod);
		}
	}

	/**
	 * HttpPost 객체에 멀티파트 파일을 세팅한다.
	 * 
	 * @param params
	 *            파라미터
	 * @param mFile
	 *            파라미터(파일)
	 */
	public void setMultipartParams(List<NameValuePair> params, MultipartFile mFile) {
		if (RequestMethod.POST.equals(requestMethod)) {
			post.addHeader("Content-Type", "multipart/form-data; boundary=" + MULTIPART_BOUNDARY);
			MultipartEntityBuilder entity;
			try {
				entity = MultipartEntityBuilder.create();
				entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
				entity.setBoundary(MULTIPART_BOUNDARY);

				entity.addPart(mFile.getName(), new InputStreamBody(mFile.getInputStream(), mFile.getOriginalFilename()));
				for (NameValuePair param : params) {
					entity.addTextBody(param.getName(), new String(param.getValue().getBytes("utf-8"), "8859_1"));
				}
				post.setEntity(entity.build());
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = false;
			}
		} else {
			LOGGER.error("This function is only available for the POST method. : Input Request Method - {}", requestMethod);
		}
	}

	/**
	 * CloseableHttpClient를 실행한다.
	 * 
	 */
	public void execute() {
		execute(true);
	}

	/**
	 * CloseableHttpClient를 실행한다.
	 * 
	 * @param isClose
	 *            HttpClient 닫기 여부
	 */
	private void execute(boolean isClose) {
		try {
			if (RequestMethod.GET.equals(requestMethod)) {
				httpResponse = client.execute(get);
			} else if (RequestMethod.POST.equals(requestMethod)) {
				httpResponse = client.execute(post);
			} else {
				throw new Exception();
			}
			entity = httpResponse.getEntity();
			// TODO EntityUtils.toString 메소드는 서버환경에 따라 인코딩이 오작동할 수 있음
			// responsebody = EntityUtils.toString(entity, "UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				responsebody += temp;
			}
			br.close();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			isSuccess = false;
		} catch (IOException e) {
			e.printStackTrace();
			isSuccess = false;
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
		} finally {
			if (isClose) {
				close();
			}
			LOGGER.debug("execute : responsebody : {}", responsebody);
		}
	}

	/**
	 * HttpClient 연결 종료
	 */
	public void close() {
		if (httpResponse != null) {
			try {
				EntityUtils.consume(entity);
			} catch (IOException e) {
			}
			try {
				httpResponse.close();
			} catch (IOException e) {
			}
			httpResponse = null;
		}
		if (client != null) {
			try {
				client.close();
			} catch (IOException e) {
			}
			client = null;
		}
		if (get != null) {
			get.completed();
			get = null;
		}
		if (post != null) {
			post.completed();
			post = null;
		}
	}

	/**
	 * 성공 여부를 반환한다.
	 * 
	 * @return 성공하면 true, 실패하면 false
	 */
	public boolean isSuccess() {
		return isSuccess;
	}

	/**
	 * Response를 반환한다.
	 * 
	 * @return responsebody
	 */
	public String getRespose() {
		return responsebody;
	}

	/**
	 * JsonArray를 반환한다.<br/>
	 * 만일 해당하는 오브젝트 값이 없으면 new JSONArray() 반환
	 * 
	 * @param objName
	 *            오브젝트 이름
	 * @return JSONArray
	 */
	public JSONArray getJsonArray(String objName) {
		JSONObject jsonObj = (JSONObject) JSONValue.parse(responsebody);
		if (jsonObj == null || jsonObj.get(objName) == null) {
			LOGGER.debug("getJsonArray : jsonObj : {}", jsonObj);
			isSuccess = false;
			return new JSONArray();
		}
		LOGGER.debug("getJsonArray : jsonObj.get(objName) : {}", jsonObj.get(objName));
		return (JSONArray) JSONValue.parse(String.valueOf(jsonObj.get(objName)));
	}

	/**
	 * JSONObject를 반환한다.<br/>
	 * 만일 해당하는 오브젝트 값이 없으면 new JSONObject() 반환
	 * 
	 * @param objName
	 *            오브젝트 이름
	 * @return JSONObject
	 */
	public JSONObject getJsonObject(String objName) {
		JSONObject jsonObj = (JSONObject) JSONValue.parse(responsebody);
		if (jsonObj == null || jsonObj.get(objName) == null) {
			LOGGER.debug("getJsonObject : jsonObj : {}", jsonObj);
			isSuccess = false;
			return new JSONObject();
		}
		LOGGER.debug("getJsonObject : jsonObj.get(objName) : {} : {}", objName, jsonObj.get(objName));
		return (JSONObject) JSONValue.parse(String.valueOf(jsonObj.get(objName)));
	}

	/**
	 * 문자열을 반환한다.<br/>
	 * 만일 해당하는 오브젝트 값이 없으면 "" 반환
	 * 
	 * @param objName
	 *            오브젝트 이름
	 * @return 문자열
	 */
	public String getString(String objName) {
		JSONObject jsonObj = (JSONObject) JSONValue.parse(responsebody);
		LOGGER.debug("getJsonObject : jsonObj : {}", jsonObj);
		if (jsonObj == null || jsonObj.get(objName) == null) {
			isSuccess = false;
			return "";
		}
		LOGGER.debug("getJsonObject : jsonObj.get(objName) : {} : {}", objName, jsonObj.get(objName));
		return jsonObj.get(objName).toString();
	}
}
