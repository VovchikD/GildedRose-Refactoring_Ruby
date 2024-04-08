<?php

namespace GildedRose\Handlers;

use GildedRose\Item;

class SulfarusItemHandler implements ItemHandlerInterface
{
    public function handle(Item $item): Item
    {
        $item->sellIn = $this->changeSallIn($item->sellIn);
        $item->quality = $this->changeQuality($item->quality, $item->sellIn);

        return $item;
    }


    private function changeQuality(int $quality, int $sallIn): int
    {
        //Todo добавить реализацию метода
        return $quality;
    }
    private function changeSallIn(int $sallIn): int
    {
        //Todo добавить реализацию метода
        return $sallIn;
    }
}