package api.meli.apimeli.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRequest {
    private String[] itemIds;
    private Float amount;
}
