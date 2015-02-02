package com.lanbiao.youxiaoyunfamily.sync.http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Used to intercept and handle the responses from requests made using
 * {@link AsyncHttpClient}, with automatic parsing into a {@link JSONObject} or
 * {@link JSONArray}.
 * <p>
 * This class is designed to be passed to get, post, put and delete requests
 * with the {@link #onSuccess(JSONObject)} or {@link #onSuccess(JSONArray)}
 * methods anonymously overridden.
 * <p>
 * Additionally, you can override the other event methods from the parent class.
 */
public class JsonHttpResponseHandler extends AsyncHttpResponseHandler {
	//
	// Callbacks to be overridden, typically anonymously
	//

	/**
	 * Fired when a request returns successfully and contains a json object at
	 * the base of the response string. Override to handle in your own code.
	 * 
	 * @param response
	 *            the parsed json object found in the server response (if any)
	 */
	public void onSuccess(JSONObject response) {
	}

	/**
	 * Fired when a request returns successfully and contains a json array at
	 * the base of the response string. Override to handle in your own code.
	 * 
	 * @param response
	 *            the parsed json array found in the server response (if any)
	 */
	public void onSuccess(JSONArray response) {
	}

	// Utility methods
	@Override
	protected void handleSuccessMessage(String responseBody) {
		super.handleSuccessMessage(responseBody);

		try {
			Object jsonResponse = parseResponse(responseBody);
			if (jsonResponse instanceof JSONObject) {
				onSuccess((JSONObject) jsonResponse);
			} else if (jsonResponse instanceof JSONArray) {
				onSuccess((JSONArray) jsonResponse);
			}
		} catch (JSONException e) {
			onFailure(e, responseBody);
		}
	}

	protected Object parseResponse(String responseBody) throws JSONException {
		return new JSONTokener(responseBody).nextValue();
	}
}
