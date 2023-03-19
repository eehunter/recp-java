package io.github.ashy1227.recp;

/**
 * Enum containing valid RECP units
 */
public class Unit {
	public static final Unit MICROGRAM             = new Unit((byte)0x00, "micrograms");
	public static final Unit MILLIGRAM             = new Unit((byte)0x01, "milligrams");
	public static final Unit GRAM                  = new Unit((byte)0x02, "grams");
	public static final Unit KILOGRAM              = new Unit((byte)0x03, "kilograms");
	
	public static final Unit OUNCE                 = new Unit((byte)0x10, "ounces");
	public static final Unit POUND                 = new Unit((byte)0x11, "pounds");
	
	public static final Unit MILLILITER            = new Unit((byte)0x20, "milliliters");
	public static final Unit DECILITER             = new Unit((byte)0x21, "deciliters");
	public static final Unit LITER                 = new Unit((byte)0x22, "liters");
	
	public static final Unit CUSTOMARY_DROP        = new Unit((byte)0x30, "drop");
	public static final Unit CUSTOMARY_SMIDGEN     = new Unit((byte)0x31, "smidgen");
	public static final Unit CUSTOMARY_PINCH       = new Unit((byte)0x32, "pinch");
	public static final Unit CUSTOMARY_DASH        = new Unit((byte)0x33, "dash");
	public static final Unit CUSTOMARY_TEASPOON    = new Unit((byte)0x34, "teaspoons");
	public static final Unit CUSTOMARY_TABLESPOON  = new Unit((byte)0x35, "tablespoons");
	public static final Unit CUSTOMARY_FLUID_OUNCE = new Unit((byte)0x36, "fluid ounces");
	public static final Unit CUSTOMARY_CUP         = new Unit((byte)0x37, "cups");
	public static final Unit CUSTOMARY_PINT        = new Unit((byte)0x38, "pints");
	public static final Unit CUSTOMARY_QUART       = new Unit((byte)0x39, "quarts");
	public static final Unit CUSTOMARY_GALLON      = new Unit((byte)0x3a, "gallons");
	public static final Unit CUSTOMARY_PECK        = new Unit((byte)0x3b, "pecks");
	public static final Unit CUSTOMARY_BUSHEL      = new Unit((byte)0x3c, "bushels");
	
	public static final Unit IMPERIAL_DROP         = new Unit((byte)0x50, "drop");
	public static final Unit IMPERIAL_SMIDGEN      = new Unit((byte)0x51, "smidgen");
	public static final Unit IMPERIAL_PINCH        = new Unit((byte)0x52, "pinch");
	public static final Unit IMPERIAL_DASH         = new Unit((byte)0x53, "dash");
	public static final Unit IMPERIAL_TEASPOON     = new Unit((byte)0x54, "teaspoons");
	public static final Unit IMPERIAL_TABLESPOON   = new Unit((byte)0x55, "tablespoons");
	public static final Unit IMPERIAL_FLUID_OUNCE  = new Unit((byte)0x56, "fluid ounces");
	public static final Unit IMPERIAL_CUP          = new Unit((byte)0x57, "cups");
	public static final Unit IMPERIAL_PINT         = new Unit((byte)0x58, "pints");
	public static final Unit IMPERIAL_QUART        = new Unit((byte)0x59, "quarts");
	public static final Unit IMPERIAL_GALLON       = new Unit((byte)0x5a, "gallons");
	public static final Unit IMPERIAL_PECK         = new Unit((byte)0x5b, "pecks");
	public static final Unit IMPERIAL_BUSHEL       = new Unit((byte)0x5c, "bushels");
	public static final Unit CANADIAN_CUP          = new Unit((byte)0x6d, "cups");
	public static final Unit AUSTRALIAN_CUP        = new Unit((byte)0x6e, "cups");
	public static final Unit AUSTRALIAN_TABLESPOON = new Unit((byte)0x6f, "tablespoons");
	
	public static final Unit NONE                  = new Unit((byte)0x80, "");
	public static final Unit QUANTITY              = new Unit((byte)0x81, "");
	
	protected static Unit[] units = {
		    // 0x00-0x0f
		MICROGRAM,             MILLIGRAM,             GRAM,                  KILOGRAM,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0x10-0x1f
		OUNCE,                 POUND,                 null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0x20-0x2f
		MILLILITER,            DECILITER,             LITER,                 null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0x30-0x3f
		CUSTOMARY_DROP,        CUSTOMARY_SMIDGEN,     CUSTOMARY_PINCH,       CUSTOMARY_DASH,
		CUSTOMARY_TEASPOON,    CUSTOMARY_TABLESPOON,  CUSTOMARY_FLUID_OUNCE, CUSTOMARY_CUP,
		CUSTOMARY_PINT,        CUSTOMARY_QUART,       CUSTOMARY_GALLON,      CUSTOMARY_PECK,
		CUSTOMARY_BUSHEL,      null,                  null,                  null,
		    // 0x40-0x4f
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0x50-0x5f
		IMPERIAL_DROP,         IMPERIAL_SMIDGEN,      IMPERIAL_PINCH,        IMPERIAL_DASH,
		IMPERIAL_TEASPOON,     IMPERIAL_TABLESPOON,   IMPERIAL_FLUID_OUNCE,  IMPERIAL_CUP,
		IMPERIAL_PINT,         IMPERIAL_QUART,        IMPERIAL_GALLON,       IMPERIAL_PECK,
		IMPERIAL_BUSHEL,       null,                  null,                  null,
		    // 0x60-0x6f
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  CANADIAN_CUP,          AUSTRALIAN_CUP,        AUSTRALIAN_TABLESPOON,
		    // 0x70-0x7f
		NONE,                  QUANTITY,              null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0x80-0x8f
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0x90-0x9f
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0xa0-0xaf
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0xb0-0xbf
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0xc0-0xcf
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0xd0-0xdf
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0xe0-0xef
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		    // 0xf0-0xff
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
		null,                  null,                  null,                  null,
	};
	
	public final byte id;
	public final String name;
	
	Unit(byte id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Get Unit based on ID
	 */
	public static Unit get(byte id) {
		return units[id];
	}
}
