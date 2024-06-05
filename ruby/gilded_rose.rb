class GildedRose
  def initialize(items)
    @items = items
  end

  def update_quality
    @items.each do |item|
      @item = item
      process_item_quality
      process_item_sell_in
      process_expired_item if @item.sell_in < 0
    end
  end

  private

  def process_item_quality
    case @item.name
    when "Aged Brie"
      increases_proportionally_to_times
    when "Backstage passes to a TAFKAL80ETC concert"
      increases_proportionally_to_times
    else
      update_item
    end
  end

  def process_item_sell_in
    @item.sell_in -= 1 unless @item.name == "Sulfuras, Hand of Ragnaros"
  end

  def increases_proportionally_to_times
    if @item.sell_in > 10
      @item.quality += 1 if @item.quality < 50
    elsif @item.sell_in > 5
      @item.quality += 2 if @item.quality < 50
    else
      @item.quality += 3 if @item.quality < 50
    end
  end

  def process_expired_item
    case @item.name
    when "Aged Brie"
      @item.quality += 1 if @item.quality < 50
    when "Backstage passes to a TAFKAL80ETC concert"
      @item.quality = 0
    else
      update_item
    end
  end

  def update_item
    decrement = @item.name == "Conjured" ? 2 : 1
    @item.quality -= decrement if @item.quality > 0
  end
end

class Item
  attr_accessor :name, :sell_in, :quality

  def initialize(name, sell_in, quality)
    @name = name
    @sell_in = sell_in
    @quality = quality
  end

  def to_s()
    "#{@name}, #{@sell_in}, #{@quality}"
  end
end
