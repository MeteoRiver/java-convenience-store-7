package store;

import camp.nextstep.edu.missionutils.Console;

import java.text.DecimalFormat;

public class Input {
    public void printProduct(){
        System.out.println("안녕하세요. W편의점입니다\n현재 보유하고 있는 상품입니다.");
        for (Product product : Product.values()) {
            System.out.println("-"+product.getName() + " " + formatNumber(product.getMoney()) + "원 " + product.getCount() + " " + product.getCategory());
        }

    }
    private String formatNumber(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(number);
    }
/*    public void inputProduct(){
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        String productName = Console.readLine();
        String[] splitProduct = productName.split(",");
        int i =0;
        for (String s : splitProduct) {
            String[] splitProductName = s.split("-");

            i++;
        }
    }*/
    public void validate(String productName){
        //[ - ]인지 여부
    }
}
