package store;

import store.Enum.Product;

public class Promotion {
    public void promotion(){

    }
    public int calculatePromotionDiscount(Product product, int quantity) {
        String promotion = product.getCategory();
        int discount = 0;
        if (promotion.equals("탄산2+1")) {
            int promoUnits = quantity / 3; // 프로모션을 적용할 수 있는 단위 수량
            int remainder = quantity % 3;  // 프로모션 혜택을 받지 못하는 수량
            if (quantity < 3) {
                System.out.printf("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n", product.getName());
            }
            if (remainder > 0 && quantity >= 3) {
                System.out.printf("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n", product.getName(), remainder);
            }
            discount = promoUnits * product.getMoney();
        }
        return discount;
        }
}

