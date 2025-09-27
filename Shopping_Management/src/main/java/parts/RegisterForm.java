package parts;

import lombok.Data;

/**
 * 商品登録フォームクラス
 */
@Data
public class RegisterForm {

    /** カテゴリID */
    private Integer categoryId;

    /** 商品ID */
    private Integer productId;

    /** 金額 */
    private String amount;

    /** 場所 */
    private String place;

    /** 購入日 */
    private String buyDate;
}
