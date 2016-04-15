package com.cosquare.smp.common.controller;

import java.util.Map;

import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cosquare.smp.util.HttpClientUtil;

/**
 * Common Controller
 * 
 * @author 컨버전스스퀘어
 * @since 2016. 3. 31
 * @version 1.0
 */
@RemoteProxy(name = "commonController")
public class CommonController {

	/**
	 * Cross Site Script를 대비한 API 호출 페이지
	 * 
	 * @param model
	 *            ModelMap
	 * @return json
	 * @exception Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/callApi.do")
	public JSONObject callApi(ModelMap model, @RequestParam Map<String, String> paramMap) throws Exception {

		String url = paramMap.get("url");
		String method = paramMap.get("method");
		String param = paramMap.get("param");

		if (url == null) {
			return null;
		}

		// Ready to RequestMethod
		RequestMethod reqMethod;
		if ("GET".equals(method.toUpperCase())) {
			reqMethod = RequestMethod.GET;
		} else if ("POST".equals(method.toUpperCase())) {
			reqMethod = RequestMethod.POST;
		} else {
			return null;
		}

		// Http Connection
		HttpClientUtil client = new HttpClientUtil(url, reqMethod);

		// Set parameter
		if (param != null && !"".equals(param)) {
			JSONObject jsonObj = (JSONObject) JSONValue.parse(param);
			if (jsonObj == null || jsonObj.get(param) == null) {
				return null;
			}

			client.setParams((JSONArray) JSONValue.parse(String.valueOf(jsonObj.get(param))));
		}

		// Exceute
		client.execute();

		return (JSONObject) JSONValue.parse(client.getRespose());
	}

	/**
	 * Cross Site Script를 대비한 API 호출 페이지
	 * 
	 * @param model
	 *            ModelMap
	 * @return json
	 * @exception Exception
	 */
	@RemoteMethod
	public String dwrTest(ModelMap model) throws Exception {
		System.out.println("[[[[[[[[[[[[[[[[[[[[[[[[[[[[[DWR TEST CONTROLLER]]]]]]]]]]]]]]]]]]]]]]]]]]]]]]");
		return "/dwr/dwr_test";
	}

}
