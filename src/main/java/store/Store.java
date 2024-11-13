package store;

import camp.nextstep.edu.missionutils.Console;
import store.Enum.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Store {

    private static final Map<String, Product> productMap = new HashMap<>();

    static {
        for (Product product : Product.values()) {
            productMap.put(product.getUniqueKey(), product);
        }
    }

    public static void main(String[] args) {
        Store store = new Store();
        store.startShopping();
    }

    public void startShopping() {
        while (true) {
            printProductList();
            String input = getProductInput();
            Map<String, Integer> purchase = parseProductInput(input);

            // 프로모션을 고려한 재고 조정
            Map<String, Integer> adjustedPurchase = adjustForPromotionAvailability(purchase);

            int totalAmount = calculateTotalAmount(adjustedPurchase);
            int promotionDiscount = calculatePromotionDiscount(adjustedPurchase);
            int finalAmount = totalAmount - promotionDiscount;

            Membership membership = new Membership();
            int membershipDiscount = membership.applyMembershipDiscount(finalAmount, promotionDiscount);
            finalAmount -= membershipDiscount;

            System.out.println("==============W 편의점================");
            printPurchaseDetails(adjustedPurchase, totalAmount, promotionDiscount, membershipDiscount, finalAmount);

            if (!askForAnotherPurchase()) {
                break;
            }
        }
    }

    private void printProductList() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");

        for (Product product : Product.values()) {
            if (product.getCount() > 0) {
                System.out.printf("- %s %d원 %d개 %s\n", product.getName(), product.getMoney(), product.getCount(), product.getCategory());
            }
        }
    }

    private String getProductInput() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return Console.readLine();
    }

    private Map<String, Integer> parseProductInput(String input) {
        Map<String, Integer> purchase = new HashMap<>();
        String[] items = input.split(",");
        for (String item : items) {
            String[] parts = item.replace("[", "").replace("]", "").split("-");
            String productName = parts[0].trim();
            int quantity = Integer.parseInt(parts[1].trim());
            purchase.put(productName, quantity);
        }
        return purchase;
    }

    private Map<String, Integer> adjustForPromotionAvailability(Map<String, Integer> purchase) {
        Map<String, Integer> adjustedPurchase = new HashMap<>(purchase);

        for (Map.Entry<String, Integer> entry : purchase.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            // 상품 목록 중 현재 구매하려는 상품 찾기
            List<Product> sortedProducts = productMap.values().stream()
                    .filter(p -> p.getName().equals(productName))
                    .collect(Collectors.toList());

            if (!sortedProducts.isEmpty()) {
                Product product = sortedProducts.get(0);
                int totalAvailable = sortedProducts.stream().mapToInt(Product::getCount).sum();

                // 프로모션 제품이 아닌 경우, 재고가 부족하면 프로모션 제외
                if (!product.getCategory().isEmpty() && totalAvailable < quantity) {
                    // 프로모션 할인이 적용되지 않는 메시지 출력
                    System.out.printf("%s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N): ", productName, quantity);
                    String response = Console.readLine();

                    // 사용자 응답에 따라 구매 수량 조정
                    if (response.equalsIgnoreCase("Y")) {
                        adjustedPurchase.put(productName, totalAvailable); // 구매 수량을 재고로 조정
                    } else {
                        adjustedPurchase.remove(productName); // 구매 취소
                    }
                }
                // 프로모션이 적용되는 상품의 경우 (예: 탄산2+1)
                else if (product.getCategory().equals("탄산2+1") && totalAvailable < quantity) {
                    boolean proceed = askToBuyWithoutPromotion(productName, quantity);
                    if (proceed) {
                        adjustedPurchase.put(productName, totalAvailable); // 구매 수량을 재고로 조정
                    } else {
                        adjustedPurchase.remove(productName); // 구매 취소
                    }
                }
            }
        }
        return adjustedPurchase;
    }

    private boolean askToBuyWithoutPromotion(String productName, int requestedQuantity) {
        System.out.printf("%s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N): ", productName, requestedQuantity);
        String response = Console.readLine();
        return response.equalsIgnoreCase("Y");
    }

    private int calculateTotalAmount(Map<String, Integer> purchase) {
        int totalAmount = 0;

        for (Map.Entry<String, Integer> entry : purchase.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            List<Product> sortedProducts = productMap.values().stream()
                    .filter(p -> p.getName().equals(productName))
                    .collect(Collectors.toList());

            for (Product product : sortedProducts) {
                if (quantity <= 0) break;
                int availableCount = product.getCount();

                if (availableCount > 0) {
                    int purchaseQuantity = Math.min(quantity, availableCount);
                    quantity -= purchaseQuantity;

                    // 정상 금액 계산 (프로모션 제외)
                    totalAmount += product.getMoney() * purchaseQuantity;
                    product.decreaseCount(purchaseQuantity);  // 재고 감소
                }
            }
        }

        return totalAmount;
    }

    private int calculatePromotionDiscount(Map<String, Integer> purchase) {
        int discount = 0;
        for (Map.Entry<String, Integer> entry : purchase.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            for (Product product : productMap.values()) {
                if (product.getName().equals(productName) && product.getCategory().equals("탄산2+1")) {
                    // 프로모션 적용: 2+1 행사
                    int promoItems = quantity / 3; // 구매 수량에서 행사되는 개수 계산
                    discount += promoItems * product.getMoney();
                    break;
                }
            }
        }
        return discount;
    }

    private void printPurchaseDetails(Map<String, Integer> purchase, int totalAmount, int promotionDiscount, int membershipDiscount, int finalAmount) {
        System.out.println("상품명\t\t수량\t금액");

        for (Map.Entry<String, Integer> entry : purchase.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();
            for (Product product : productMap.values()) {
                if (product.getName().equals(productName)) {
                    System.out.printf("%s\t\t%d\t%d\n", productName, quantity, product.getMoney() * quantity);
                    break;
                }
            }
        }

        System.out.println("=============증정===============");
        for (Map.Entry<String, Integer> entry : purchase.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            for (Product product : productMap.values()) {
                if (product.getName().equals(productName) && product.getCategory().equals("탄산2+1")) {
                    int freeItems = quantity / 3; // 2+1 행사로 주는 무료 상품 수
                    if (freeItems > 0) {
                        System.out.printf("%s\t\t%d\n", productName, freeItems);
                    }
                    break;
                }
            }
        }

        System.out.println("====================================");
        System.out.printf("총구매액\t\t%d\n", totalAmount);
        System.out.printf("행사할인\t\t\t-%d\n", promotionDiscount);
        System.out.printf("멤버십할인\t\t\t-%d\n", membershipDiscount);
        System.out.printf("최종금액\t\t\t%d\n", finalAmount);
    }

    private boolean askForAnotherPurchase() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        String answer = Console.readLine();
        return answer.equalsIgnoreCase("Y");
    }
}