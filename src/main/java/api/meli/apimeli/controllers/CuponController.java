package api.meli.apimeli.controllers;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import api.meli.apimeli.entity.CouponRequest;
import api.meli.apimeli.services.CuponService;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

@RestController
public class CuponController {
    @Autowired
    CuponService cuponService;

    @Value("${meli.token}")
    private String meliToken;

    @PostMapping(value = "/coupons")
    public ResponseEntity<List<String>> uploadCoupon(@RequestBody CouponRequest request)
            throws IOException, URISyntaxException {
        Map<String, Float> items = new HashMap<>();

        for (int i = 0; i < request.getItemIds().length; i++) {

            String item = request.getItemIds()[i];

            URI url = new URI("https://api.mercadolibre.com/items/" + item);

            CloseableHttpClient client = HttpClients.custom().build();
            // (1) Use the new Builder API (from v4.3)
            HttpUriRequest requestML = RequestBuilder.get().setUri(url)
                    // (2) Use the included enum
                    .setHeader("Content-type", "application/json")
                    // (3) Or your own
                    .setHeader("Authorization", meliToken).build();
            CloseableHttpResponse response = client.execute(requestML);
            // (4) How to read all headers with Java8
            String body = org.apache.commons.io.IOUtils.toString(response.getEntity().getContent(), "UTF-8");

            JSONObject obj = new JSONObject(body);

            Float price = obj.getFloat("price");

            items.put(item, price);

            // System.out.println(price);
        }

        List<String> response = cuponService.calculate(items, request.getAmount());
        if (response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.accepted().body(response);
    }
}
