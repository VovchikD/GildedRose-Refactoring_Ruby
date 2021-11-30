package com.gildedrose.items;

import com.gildedrose.item_helpers.ItemHandler;
import com.gildedrose.item_helpers.ItemType;
import com.gildedrose.main.Item;

public class ConjuredItem implements ItemType {

  public static final String CONJURED = "Conjured Mana Cake";

  private final ItemHandler item;

  public ConjuredItem(Item item) {
    this.item = new ItemHandler(item);
  }

  @Override
  public void updateQuality() {
    item.decrementSellInDate();
    if (item.beforeSellInDate()) {
      item.decrementQualityBy2();
    } else {
      item.decrementQualityBy4();
    }
  }

  @Override
  public String getName() {
    return CONJURED;
  }

}
