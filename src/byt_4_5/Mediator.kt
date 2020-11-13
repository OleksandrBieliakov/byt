package byt_4_5

import java.math.BigDecimal

// Example of communication between objects, using the mediator pattern

fun main() {
    testExchange()
}

fun testExchange() {
    val exchange = Exchange()
    val trader1 = Trader("Bob", exchange)
    val trader2 = Trader("John", exchange)

    val item1 = Item("Little pony", 99.toBigDecimal())
    val item2 = Item("Chain saw", 10.toBigDecimal())
    val item3 = Item("Old socks", 0.99.toBigDecimal())

    trader1.placeBuyOffer(item1)
    trader1.placeBuyOffer(item2)
    trader2.placeSellOffer(item2)
    trader2.placeSellOffer(item3)
    trader1.placeSellOffer(item1)
    trader1.placeSellOffer(item2)
    trader2.placeBuyOffer(item2)
    trader2.placeBuyOffer(item3)

    exchange.printExchangeState()
}

class Item(val title: String, val price: BigDecimal)

abstract class Participant(val name: String, val mediator: Mediator) {
    abstract fun placeBuyOffer(item: Item)
    abstract fun placeSellOffer(item: Item)
}

class Trader(name: String, mediator: Mediator) : Participant(name, mediator) {
    override fun placeBuyOffer(item: Item) {
        mediator.placeBuyOffer(item, this)
    }

    override fun placeSellOffer(item: Item) {
        mediator.placeSellOffer(item, this)
    }
}

class Offer(val item: Item, val placedBy: Participant)

interface Mediator {
    fun placeBuyOffer(item: Item, placedBy: Participant)
    fun placeSellOffer(item: Item, placedBy: Participant)
}

class Exchange : Mediator {
    private val sellOffers: MutableList<Offer> = mutableListOf()
    private val buyOffers: MutableList<Offer> = mutableListOf()

    override fun placeBuyOffer(item: Item, placedBy: Participant) {
        val newOffer = Offer(item, placedBy)
        when (val matchingOffer = searchMatchingOffer(sellOffers, newOffer)) {
            null -> buyOffers.add(newOffer)
            else -> {
                sellOffers.remove(matchingOffer)
                announceDeal(buyOffer = newOffer, sellOffer = matchingOffer)
            }
        }
    }

    override fun placeSellOffer(item: Item, placedBy: Participant) {
        val newOffer = Offer(item, placedBy)
        when (val matchingOffer = searchMatchingOffer(buyOffers, newOffer)) {
            null -> sellOffers.add(newOffer)
            else -> {
                buyOffers.remove(matchingOffer)
                announceDeal(buyOffer = matchingOffer, sellOffer = newOffer)
            }
        }
    }

    private fun searchMatchingOffer(offers: List<Offer>, offer: Offer): Offer? {
        return offers.firstOrNull { it.item == offer.item && it.placedBy != offer.placedBy }
    }

    private fun announceDeal(buyOffer: Offer, sellOffer: Offer) {
        println("item ${buyOffer.item.title} placed by ${sellOffer.placedBy.name} is sold to ${buyOffer.placedBy.name} for ${buyOffer.item.price}")
    }

    fun printExchangeState() {
        printSellOffers()
        printBuyOffers()
    }

    fun printBuyOffers() {
        println("Buy offers:")
        printOffers(buyOffers)
    }

    fun printSellOffers() {
        println("Sell offers:")
        printOffers(sellOffers)
    }

    private fun printOffers(offers: List<Offer>) {
        for (offer in offers)
            println("Item ${offer.item.title}, price ${offer.item.price}, placed by ${offer.placedBy.name}")
    }
}