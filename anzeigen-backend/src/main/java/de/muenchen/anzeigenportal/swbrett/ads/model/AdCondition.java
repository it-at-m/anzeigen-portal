package de.muenchen.anzeigenportal.swbrett.ads.model;

/**
 * Enum representing the condition of an item in an eBay-like advertisement.
 * This enum can be used to specify the state of the item being advertised.
 */
public enum AdCondition {

    /**
     * The item is brand new and has never been used.
     */
    NEW,

    /**
     * The item is in very good condition, showing minimal signs of use.
     */
    VERY_GOOD,

    /**
     * The item is in good condition, showing some signs of use but still fully functional.
     */
    GOOD,

    /**
     * The item is in okay condition, showing significant signs of use but still functional.
     */
    OKAY,

    /**
     * The item is defective and may not be fully functional.
     */
    DEFECTIVE,
}
