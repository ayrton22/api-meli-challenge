package api.meli.apimeli;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import api.meli.apimeli.controllers.CuponController;
import api.meli.apimeli.entity.CouponRequest;
import api.meli.apimeli.services.CuponService;

import org.json.JSONException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ApiMeliApplicationTests {

	@Autowired
	private CuponController cuponController;

	/*
	 * @Test void contextLoads() {
	 * 
	 * }
	 */

	@Test
	public void test_favs_bad_request() throws ClientProtocolException, IOException, JSONException {

		// @Value("${meli.token}")
		// private String meliToken;

		CloseableHttpClient client = HttpClients.createDefault();
		// Given
		// HttpPost httpPost = new
		// HttpPost("https://api-meli-challenge-ayr.herokuapp.com/coupons/");

		HttpPost httpPost = new HttpPost("http://localhost:8080/coupons/");

		String json = "{ \"itemIds\": [ \"MLA844702264\", \"MLA810645372\", \"MLA810645375\",\"MLA810645376\"], \"amount\": 500 }";

		StringEntity entity = new StringEntity(json);
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("Authorization",
				"Bearer APP_USR-8625772137282066-120722-d0396bf54e191f5af4dc2d0ac01b2858-261972528");

		CloseableHttpResponse response = client.execute(httpPost);

		System.out.println(response);

		StatusLine statusLine = response.getStatusLine();
		assertEquals(404, statusLine.getStatusCode());
	}

	@Test
	public void test_favs_ok() throws ClientProtocolException, IOException, URISyntaxException {

		CouponRequest request = new CouponRequest();

		String[] itemIds = { "MLA810645376", "MLA844702264", "MLA810645372", "MLA810645375" };

		request.setAmount(50000F);

		request.setItemIds(itemIds);

		ResponseEntity<List<String>> response = cuponController.uploadCoupon(request);

		HttpStatus statusCode = response.getStatusCode();

		assertEquals(200, statusCode.value());

		List<String> responseBody = response.getBody();

		assertEquals(4, responseBody.size());
	}

	@Test
	public void test_serviceCoupon() {
		CuponService cuponservice = new CuponService();
		Map<String, Float> items = new HashMap<>();
		items.put("MLA810645376", 100F);
		Float amount = 2000F;

		List<String> response = cuponservice.calculate(items, amount);

		assertEquals(1, response.size());
	}

}
