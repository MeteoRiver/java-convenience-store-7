package store;

public enum Product {
    Coke(Name.coke,1000, 10,Category.carbonic),
    coke(Name.coke,1000,10,Category.None),
    Cider(Name.cider,1000, 8, Category.carbonic),
    cider(Name.cider,1000,8,Category.None),
    Orange(Name.orange,1800,9,Category.MD),
    orange(Name.orange,1800,0,Category.None),
    CarWater(Name.carwater,1200,5,Category.carbonic),
    carWater(Name.carwater,1200,0,Category.None),
    water(Name.water,500,10,Category.None),
    vitWater(Name.vitwater,1500,6,Category.None),
    Potato(Name.potato,1500,5,Category.Sale),
    potato(Name.potato,1500,5,Category.None),
    Choco(Name.choco,1200,5,Category.MD),
    choco(Name.choco,1200,5,Category.None),
    energy(Name.energy,2000,5,Category.None),
    pack(Name.pack,6400,8,Category.None),
    Cup(Name.cup,1700,1,Category.MD),
    cup(Name.cup,1700,10,Category.None),;


    public enum Name{
        coke("콜라"),
        cider("사이다"),
        orange("오렌지주스"),
        carwater("탄산수"),
        water("물"),
        vitwater("비타민워터"),
        potato("감자칩"),
        choco("초코바"),
        energy("에너지바"),
        pack("정식도시락"),
        cup("컵라면");

        private String name;
        Name(String name){
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }
    public enum Category{
        carbonic("탄산 2+1"),
        MD("MD추천상품"),
        Sale("반짝할인"),
        None("");
        private String category;
        Category(String category){
            this.category = category;
        }
        public String getCategory(){
            return category;
        }
    }

    private String name;
    private int Money;
    private int Count;
    private String category;

    Product(Name name, int Money, int Count, Category category) {
        this.name = name.getName();
        this.Money = Money;
        this.Count = Count;
        this.category = category.getCategory();
    }
    public int getMoney() {
        return Money;
    }
    public String getCount() {
        if(Count==0){
            return "재고 없음";
        }
        return Count+"개";
    }
    public String getCategory() {
        if(category==null){
            return null;
        }
        return category;
    }
    public void setCount(int count) {
        this.Count=count;
    }
    public String getName() {
        return name;
    }
}
