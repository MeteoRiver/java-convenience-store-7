package store;

public enum Product {
    Coke(1000, 10,Category.carbonic),
    coke(1000,10,null),
    Cider(1000, 8, Category.carbonic),
    cider(1000,8,null),
    Orange(1800,9,Category.MD),
    orange(1800,0,null),
    CarWater(1200,5,Category.carbonic),
    carWater(1200,0,null),
    water(500,10,null),
    vitWater(1500,6,null),
    Potato(1500,5,Category.Sale),
    potato(1500,5,null),
    Choco(1200,5,Category.MD),
    choco(1200,5,null),
    energy(2000,5,null),
    pack(6400,8,null),
    Cup(1700,1,Category.MD),
    cup(1700,10,null),;


    public enum Category{
        carbonic("탄산 2+1"),
        MD("MD추천상품"),
        Sale("반짝할인"),
        None(null);
        private String category;
        Category(String category){
            this.category = category;
        }
        public String getCategory(){
            return category;
        }
    }

    private int Money;
    private int Count;
    private Category category;

    Product(int Money, int Count, Category category) {
        this.Money = Money;
        this.Count = Count;
        this.category = category;
    }
    public int getMoney() {
        return Money;
    }
    public int getCount() {
        return Count;
    }
    public Category getCategory() {
        return category;
    }
    public void setCount(int count) {
        this.Count=count;
    }
}
