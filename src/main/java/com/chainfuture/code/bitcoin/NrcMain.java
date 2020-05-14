package com.chainfuture.code.bitcoin;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.acl.LastOwnerException;
import java.util.UUID;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import ru.paradoxs.bitcoin.client.exceptions.BitcoinClientException;

public class NrcMain {

	private WwHttpSession session = null;
	private String error="";
	public static final Logger LOGGER = Logger.getLogger(NrcMain.class);

	private static String  host="127.0.0.1"; //换成你们的NRC节点IP
	private static String  userName="qrcode0";//换成你们用户名
	private static String  password="Gnx8nAdGknoQ3VyhfKwKzgQoUbDAzeDUekie-emTCjE=";//换成你们密码
	private static int  port=18999;//换成你们端口
	
	public void initRPC(String _host, String _userName, String _password, int _port) {
		try {
			Credentials credentials = new UsernamePasswordCredentials(_userName, _password);
			URI uri = new URI("http", null, _host, _port, null, null, null);
			this.session = new WwHttpSession(uri, credentials);

		} catch (URISyntaxException e) {
			throw new BitcoinClientException("This host probably doesn't have correct syntax: " + host, e);
		}
	}

	/**
	 * 接口已废弃，新的接口通过httprequestUtil调用
	 */
	public void initRPC() {
		try {
			Credentials credentials = new UsernamePasswordCredentials("qrcode0", "Gnx8nAdGknoQ3VyhfKwKzgQoUbDAzeDUekie-emTCjE=");
			URI uri = new URI("http", null, "127.0.0.1", 18999, null, null, null);
			this.session = new WwHttpSession(uri, credentials);

		} catch (URISyntaxException e) {
			throw new BitcoinClientException("This host probably doesn't have correct syntax: " + host, e);
		}
	}

	private JSONObject createRequest(String functionName, JSONArray parameters) throws Exception {
		JSONObject request = new JSONObject();
		request.put("jsonrpc", "2.0");
		request.put("id", UUID.randomUUID().toString());
		request.put("method", functionName);
		request.put("params", parameters);

		return request;
	}

	private JSONObject createRequest(String functionName) throws Exception {
		return createRequest(functionName, new JSONArray());
	}
	public JSONObject getInfo() {
		try {
			JSONObject request = createRequest("getinfo");
			JSONObject response = this.session.sendAndReceive(request);

			JSONObject result = (JSONObject) response.get("result");

			return result;

		} catch (Exception e) {
			throw new BitcoinClientException("Exception when getting the server info", e);
		}
	}

	public String getPrimeaddr()
	{
		try
		{
			JSONObject request = createRequest("getprimeaddr");
			JSONObject response = this.session.sendAndReceive(request);
			if(response==null){
				this.error="节点服务内部错误:"+this.session.getError();
				return "节点服务内部错误:"+this.session.getError();
			}
			this.error="";
			return response.getString("result");
		}
		catch (Exception e)
		{
			this.error="getprimeaddr异常:"+e.getMessage();
			return "getprimeaddr异常:"+e.getMessage();
		}
	}
	public String getPrimeaddr(String primeaddr)
	{
		try
		{
			JSONArray parameters = new JSONArray().element(primeaddr);
			JSONObject request = createRequest("getprimeaddr",parameters);
			JSONObject response = this.session.sendAndReceive(request);
			if(response==null){
				this.error="节点服务内部错误:"+this.session.getError();
				return "节点服务内部错误:"+this.session.getError();
			}
			this.error="";
			return response.getString("result");
		}
		catch (Exception e)
		{
			this.error="getprimeaddr异常:"+e.getMessage();
			return "getprimeaddr异常:"+e.getMessage();
		}
	}

	public String getAccount(String primeaddr)
	{
		try
		{
			JSONArray parameters = new JSONArray().element(primeaddr);
			JSONObject request = createRequest("getaccount",parameters);
			JSONObject response = this.session.sendAndReceive(request);
			if(response==null){
				this.error="节点服务内部错误:"+this.session.getError();
				return "节点服务内部错误:"+this.session.getError();
			}
			this.error="";
			return response.getString("result");
		}
		catch (Exception e)
		{
			this.error="getaccount异常:"+e.getMessage();
			return "getaccount异常:"+e.getMessage();
		}
	}

	public String sendMany(String bitcoinAddress,String manyAddress)
	{
		try
		{
			JSONArray parameters = new JSONArray().element(bitcoinAddress).element(manyAddress);
			JSONObject request = createRequest("sendmany",parameters);
			JSONObject response = this.session.sendAndReceive(request);
			if(response==null){
				this.error="节点服务内部错误:"+this.session.getError();
				return "节点服务内部错误:"+this.session.getError();
			}
			this.error="";
			return response.getString("result");
		}
		catch (Exception e)
		{
			this.error="sendmany异常:"+e.getMessage();
			return "sendmany异常:"+e.getMessage();
		}
	}

	public JSONArray listUnspent() {
		try {
			JSONObject request = createRequest("listunspent");
			JSONObject response = this.session.sendAndReceive(request);

			JSONArray result = (JSONArray) response.get("result");

			return result;

		} catch (Exception e) {
			throw new BitcoinClientException("Exception when getting the server listunspent", e);
		}
	}
	public JSONObject listAccounts() {
		try {
			JSONObject request = createRequest("listaccounts");
			JSONObject response = this.session.sendAndReceive(request);

			JSONObject result = (JSONObject) response.get("result");

			return result;

		} catch (Exception e) {
			throw new BitcoinClientException("Exception when getting the server listaccounts", e);
		}
	}
	public Object getBalance(String account)
	{
		if (account == null) {
			account = "";
		}
		try
		{
			JSONArray parameters = new JSONArray().element(account);
			JSONObject request = createRequest("getbalance", parameters);
			JSONObject response = this.session.sendAndReceive(request);
			Object object = response.get("result");
			return object;
		}
		catch (Exception e)
		{
			throw new BitcoinClientException("Exception when getting balance", e);
		}
	}
	public Object getBalance() {
		try {
			JSONObject request = createRequest("getbalance");
			JSONObject response = this.session.sendAndReceive(request);
			Object object = response.get("result");

			return object;
		} catch (Exception e) {
			throw new BitcoinClientException("Exception when getting the server balance", e);
		}
	}

	public String sendToAddress(String bitcoinAddress, String amount, String comment, String commentTo) {
		try {
			JSONArray parameters = new JSONArray().element(bitcoinAddress).element(amount).element(comment).element(commentTo);
			JSONObject request = createRequest("sendtoaddress", parameters);
			JSONObject response = this.session.sendAndReceive(request);
			if (response == null) {
				error = this.session.getError();
				return null;
			}

			return response.getString("result");
		} catch (Exception e) {
			error = "(getaccountaddress)Exception when getting the address ";
			return null;
		}
	}

	public String signMessage(String bitcoinAddress, String msg) {
		try {
			JSONArray parameters = new JSONArray().element(bitcoinAddress).element(msg);
			JSONObject request = createRequest("signmessage", parameters);
			JSONObject response = this.session.sendAndReceive(request);
			if (response == null) {
				error = this.session.getError();
				return null;
			}

			return response.getString("result");
		} catch (Exception e) {
			error = "signmessage  Exception";
			return null;
		}
	}

	public String getAccountAddress(String account) {
		try {
			JSONArray parameters = new JSONArray().element(account);
			JSONObject request = createRequest("getaccountaddress", parameters);
			JSONObject response = this.session.sendAndReceive(request);
			if (response == null) {
				error = this.session.getError();
				return null;
			}

			return response.getString("result");
		} catch (Exception e) {
			error = "(getaccountaddress)Exception when getting the address ";
			return null;
		}
	}
	public String getRawtransaction(String txId) {
		try {
			JSONArray parameters = new JSONArray().element(txId).element(1);
			JSONObject request = createRequest("getrawtransaction", parameters);
			JSONObject response = this.session.sendAndReceive(request);
			if (response == null) {
				error = this.session.getError();
				return null;
			}

			return response.getString("result");
		} catch (Exception e) {
			error = "(getrawtransaction)Exception when getting the transaction ";
			return null;
		}
	}

	public static void main(String[] args) {
		System.out.println("begin call RPC:");
		NrcMain nm=new NrcMain();
		nm.initRPC(host,userName,password,port);		
		JSONObject res=nm.getInfo();			
		System.out.println("response:"+res.toString());
		System.out.println("Get Raw Transaction:");		
		String resTx=nm.getRawtransaction("17d34783473cc74709c284f6957b68e3f1110bf428fddca9b25b4ffcc46628d8");
		System.out.println("response:"+resTx.toString());
	}

}
