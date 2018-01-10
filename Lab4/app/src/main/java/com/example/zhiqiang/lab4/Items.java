package com.example.zhiqiang.lab4;

/**
 * Created by zhiqiang on 2017/10/28.
 */

public class Items {
    String[] itemName = new String[] {"Enchated Forest", "Arla Milk", "DevonDale Milk",
            "Kindle Oasis", "waitrose 早餐麦片", "Mcvities 饼干", "Ferrero Rocher", "Maltesers",
            "Lindt", "Borggreve"};
    int[] itemImage = new int[] {R.mipmap.enchatedforest, R.mipmap.arla, R.mipmap.devondale,
            R.mipmap.kindle, R.mipmap.waitrose, R.mipmap.mcvitie,
            R.mipmap.ferrero, R.mipmap.maltesers, R.mipmap.lindt, R.mipmap.borggreve};
    String[] itemImageName = new String[] {"enchatedforest", "arla", "devondale", "kindle",
            "waitrose", "mcvitie", "ferrero", "maltesers", "lindt", "borggreve"};
    String[] itemPrice = new String [] {"¥ 5.00 ", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00",
            "¥ 14.90", "¥ 132.59", "¥ 141.43", "¥ 139.43", "¥ 28.90"};
    String[] itemType = new String[] {"作者 Johanna Basford", "产地 德国", "产地 澳大利亚",
            "版本 8GB", "重量 2Kg", "产地 英国", "重量 300g",
            "重量 118g", "重量 249g", "重量 640g"};
    public int findPositionOfName(String msg) {
        for (int i = 0; i < itemName.length; i++) {
            if (msg.equals(itemName[i])) return i;
        }
        return 0;
    }
}
