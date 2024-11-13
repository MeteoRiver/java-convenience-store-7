package store;

import camp.nextstep.edu.missionutils.Console;
import store.Enum.ErrorMessage;

public class Membership {

    private static final int MAX_MEMBERSHIP_DISCOUNT = 8000; // 최대 멤버십 할인 금액

    public int applyMembershipDiscount(int totalAmount, int promoDiscount) {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        String s = Console.readLine();

        if (!s.equalsIgnoreCase("Y") && !s.equalsIgnoreCase("N")) {
            throw new IllegalArgumentException(String.valueOf(ErrorMessage.INVALID_FORMAT));
        }

        if (s.equalsIgnoreCase("Y")) {
            int remainingAmount = totalAmount - promoDiscount*2;
            int discount = (int) (remainingAmount * 0.3);
            if (discount > MAX_MEMBERSHIP_DISCOUNT) {
                discount = MAX_MEMBERSHIP_DISCOUNT;
            }
            return discount; // 할인 금액 반환
        }
        return 0;
    }
}