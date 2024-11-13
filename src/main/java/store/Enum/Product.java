package store.Enum;

public enum Product {
    Coke("콜라", 1000, 10, "탄산2+1"),
    coke("콜라", 1000, 10, ""),
    Cider("사이다", 1000, 8, "탄산2+1"),
    cider("사이다", 1000, 8, ""),
    Orange("오렌지주스", 1800, 9, "MD추천상품"),
    orange("오렌지주스", 1800, 0, ""),
    CarWater("탄산수", 1200, 5, "탄산2+1"),
    carWater("탄산수", 1200, 0, ""),
    water("물", 500, 10, ""),
    vitWater("비타민워터", 1500, 6, ""),
    Potato("감자칩", 1500, 5, "반짝할인"),
    potato("감자칩", 1500, 5, ""),
    Choco("초코바", 1200, 5, "MD추천상품"),
    choco("초코바", 1200, 5, ""),
    energy("에너지바", 2000, 5, ""),
    pack("정식도시락", 6400, 8, ""),
    Cup("컵라면", 1700, 1, "MD추천상품"),
    cup("컵라면", 1700, 10, "");

    private String name;
    private int money;
    private int count;
    private String category;

    Product(String name, int money, int count, String category) {
        this.name = name;
        this.money = money;
        this.count = count;
        this.category = category;
    }

    public int getMoney() {
        return money;
    }

    public int getCount() {
        return count;
    }

    public String getCategory() {
        return category;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void decreaseCount(int quantity) {
        if (quantity > count) {
            throw new IllegalStateException("재고 부족");
        }
        count -= quantity;
    }

    // 고유 키 생성 메서드
    public String getUniqueKey() {
        return name + "-" + money + "-" + category;
    }

    // 상품 가격 계산 메서드 (프로모션 적용)
    public int calculatePrice(int quantity, boolean hasMembershipDiscount) {
        int price = money * quantity;

        // 프로모션 적용: 2+1 행사
        if (category.equals("탄산2+1")) {
            int promotionQuantity = quantity / 3; // 2+1 행사에서 구매하는 갯수의 1/3은 증정
            price -= promotionQuantity * money; // 가격에서 증정한 만큼 빼기
        }

        // 멤버십 할인 적용
        if (hasMembershipDiscount) {
            price -= price * 0.2; // 예: 20% 할인
        }

        return price;
    }
}