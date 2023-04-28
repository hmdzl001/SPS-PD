/*
 * Pixel Dungeon
 * Copyright (C) 2012-2014  Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.hmdzl.spspd.sprites;

public class ItemSpriteSheet {

	// Row definers
	private static final int ROW1 = 0 * 16;
	private static final int ROW2 = 1 * 16;
	private static final int ROW3 = 2 * 16;
	private static final int ROW4 = 3 * 16;
	private static final int ROW5 = 4 * 16;
	private static final int ROW6 = 5 * 16;
	private static final int ROW7 = 6 * 16;
	private static final int ROW8 = 7 * 16;
	private static final int ROW9 = 8 * 16;
	private static final int ROW10 = 9 * 16;
	private static final int ROW11 = 10 * 16;
	private static final int ROW12 = 11 * 16;
	private static final int ROW13 = 12 * 16;
	private static final int ROW14 = 13 * 16;
	private static final int ROW15 = 14 * 16;
	private static final int ROW16 = 15 * 16;
	private static final int ROW17 = 16 * 16;
	private static final int ROW18 = 17 * 16;
	private static final int ROW19 = 18 * 16;
	private static final int ROW20 = 19 * 16;
	private static final int ROW21 = 20 * 16;
	private static final int ROW22 = 21 * 16;
	private static final int ROW23 = 22 * 16;
	private static final int ROW24 = 23 * 16;
	private static final int ROW25 = 24 * 16;
	private static final int ROW26 = 25 * 16;
	private static final int ROW27 = 26 * 16;
	private static final int ROW28 = 27 * 16;
	private static final int ROW29 = 28 * 16;
    private static final int ROW30 = 29 * 16;
	private static final int ROW31 = 30 * 16;
	private static final int ROW32 = 31 * 16;
	private static final int ROW33 = 32 * 16;
	private static final int ROW34 = 33 * 16;
	private static final int ROW35 = 34 * 16;

	// Row One: Items which can't be obtained
	// null warning occupies space 0, should only show up if there's a bug.
	public static final int NULLWARN = ROW1 + 0;
	public static final int DEWDROP = ROW1 + 1;
	public static final int PETAL = ROW1 + 2;
	public static final int SANDBAG = ROW1 + 3;
	public static final int REDDEWDROP = ROW8 + 8;
	public static final int YELLOWDEWDROP = ROW8 + 14;
	public static final int VIOLETDEWDROP =  ROW14 + 13;
	public static final int COIN =  ROW14 + 14;
	public static final int ARTIFACT =  ROW14 + 15;

	// Heaps (containers)
	public static final int BONES = ROW1 + 4;
	public static final int REMAINS = ROW1 + 5;
	public static final int TOMB = ROW1 + 6;
	public static final int GRAVE = ROW1 + 7;
	public static final int CHEST = ROW1 + 8;
	public static final int LOCKED_CHEST = ROW1 + 9;
	public static final int CRYSTAL_CHEST = ROW1 + 10;
	// Placeholders
	public static final int WEAPON_HOLDER = ROW1 + 11;
	public static final int ARMOR_HOLDER = ROW1 + 12;
	public static final int RING_HOLDER = ROW1 + 13;
	public static final int SOMETHING = ROW1 + 14;
	public static final int E_DUST = ROW1 + 15;

	// Row Two: Miscellaneous single use items
	public static final int GOLD = ROW2 + 0;
	public static final int TORCH = ROW2 + 1;
	public static final int STYLUS = ROW2 + 2;
	public static final int ANKH = ROW2 + 3;
	// Keys
	public static final int IRON_KEY = ROW2 + 4;
	public static final int GOLDEN_KEY = ROW2 + 5;
	public static final int SKELETON_KEY = ROW2 + 6;
	// Boss Rewards
	public static final int BEACON = ROW2 + 7;
	public static final int MASTERY = ROW2 + 8;
	public static final int KIT = ROW2 + 9;
	public static final int AMULET = ROW2 + 10;
	public static final int WEIGHT = ROW2 + 11;
	public static final int BOMB = ROW2 + 12;
	public static final int DBL_BOMB = ROW2 + 13;
	public static final int HONEYPOT = ROW2 + 14;
	public static final int SHATTPOT = ROW2 + 15;
	public static final int SHELL = ROW5 + 10;
	public static final int BONE = ROW5 + 11;
	

	// Row Three: Melee weapons
	public static final int KNUCKLEDUSTER = ROW3 + 0;
	public static final int DAGGER = ROW3 + 1;
	public static final int SHORT_SWORD = ROW3 + 2;
	public static final int HANDAXE = ROW3 + 3;
	public static final int SPEAR = ROW3 + 4;
	public static final int SCIMITAR = ROW3 + 5;
	public static final int WHIP = ROW3 + 6;
	public static final int BATTLE_AXE = ROW3 + 7;
	public static final int ASSASSINSBLADE = ROW3 + 8;
	public static final int WAR_HAMMER = ROW3 + 9;
	public static final int GLAIVE = ROW3 + 10;
	public static final int ADAMANT_WEAPON = ROW3 + 11;
	public static final int HANDCANNON = ROW3 + 12;
	public static final int MAGEBOOK = ROW3 + 13;
	public static final int SPKEY = ROW3 + 14;

	// Row Four: Missile weapons and sporks
	public static final int KNIVE = ROW4 + 0;
	public static final int BOOMERANG = ROW4 + 1;
	public static final int INCENDIARY_DART = ROW4 + 2;
	public static final int SHURIKEN = ROW4 + 3;
	public static final int POSION_DART = ROW4 + 4;
	public static final int BOLA = ROW4 + 5;
	public static final int TOMAHAWK = ROW4 + 6;
	public static final int SPORK = ROW4 + 7;
	public static final int RUNICBLADE = ROW4 + 8;
	public static final int WAVE = ROW4 + 9;
	public static final int AMMO = ROW4 + 12;
	public static final int SOUL_COLLECT = ROW4 + 13;
	public static final int PALANTIR = ROW20 + 8;
	public static final int SKULLWEP = ROW4 + 14;
	public static final int STONE = ROW4 + 15;
	
	
	// Row Five: Armors
	public static final int ARMOR_CLOTH = ROW5 + 0;
	public static final int ARMOR_LEATHER = ROW5 + 1;
	public static final int ARMOR_MAIL = ROW5 + 2;
	public static final int ARMOR_SCALE = ROW5 + 3;
	public static final int ARMOR_PLATE = ROW5 + 4;
	public static final int ARMOR_WARRIOR = ROW5 + 5;
	public static final int ARMOR_MAGE = ROW5 + 6;
	public static final int ARMOR_ROGUE = ROW5 + 7;
	public static final int ARMOR_HUNTRESS = ROW5 + 8;
	public static final int ARMOR_ADAMANT = ROW5 + 9;
	public static final int ARMOR_DISC = ROW17 + 8;

	// Row Six: Wands
	public static final int WAND_MAGIC_MISSILE = ROW6 + 0;
	public static final int WAND_POISON = ROW6 + 1;
	public static final int WAND_METEORITE = ROW6 + 2;
	public static final int WAND_FLOW = ROW6 + 3;
	public static final int WAND_CHARM = ROW6 + 4;
	public static final int WAND_DISINTEGRATION = ROW6 + 5;
	public static final int WAND_FIREBOLT = ROW6 + 6;
	public static final int WAND_BLOOD = ROW6 + 7;
	public static final int WAND_FREEZE = ROW6 + 8;
	public static final int WAND_LIGHTNING = ROW6 + 9;
	public static final int WAND_ACID = ROW6 + 10;
	public static final int WAND_LIGHT = ROW6 + 11;
	public static final int WAND_FLOCK = ROW6 + 12;
	public static final int WAND_ADAMANT = ROW6 + 13;
	public static final int WAND_TCLOUD = ROW4 + 11;

	// Row Seven: Rings
	public static final int RING_GARNET = ROW7 + 0;
	public static final int RING_RUBY = ROW7 + 1;
	public static final int RING_TOPAZ = ROW7 + 2;
	public static final int RING_EMERALD = ROW7 + 3;
	public static final int RING_ONYX = ROW7 + 4;
	public static final int RING_OPAL = ROW7 + 5;
	public static final int RING_TOURMALINE = ROW7 + 6;
	public static final int RING_SAPPHIRE = ROW7 + 7;
	public static final int RING_AMETHYST = ROW7 + 8;
	public static final int RING_QUARTZ = ROW7 + 9;
	public static final int RING_AGATE = ROW7 + 10;
	public static final int RING_DIAMOND = ROW7 + 11;
	public static final int RING_ADAMANT = ROW7 + 12;
	public static final int ROBOT_HEART = ROW7 + 13;

	public static final int ICE_EYE = ROW7 + 15;

	// Row Eight: Artifacts with Static Images
	public static final int ARTIFACT_CLOAK = ROW8 + 0;
	public static final int ARTIFACT_ARMBAND = ROW8 + 1;
	public static final int ARTIFACT_CAPE = ROW8 + 2;
	public static final int ARTIFACT_TALISMAN = ROW8 + 3;
	public static final int ARTIFACT_HOURGLASS = ROW8 + 4;
	public static final int ARTIFACT_TOOLKIT = ROW8 + 5;
	public static final int ARTIFACT_SPELLBOOK = ROW8 + 6;
	public static final int BOSSRUSH = ROW8 + 7;
	public static final int COURAGETRIAL = ROW8 + 9;
	public static final int POWERTRIAL = ROW8 + 10;
	public static final int WISDOMTRIAL = ROW8 + 11;
	public static final int DWARFHAMMER = ROW8 + 12;
	public static final int ANCIENTKEY = ROW8 + 13;
	public static final int OTILUKES_JOURNAL = ROW16 + 3;


	// Row Nine: Artifacts with Dynamic Images
	public static final int ARTIFACT_HORN1 = ROW9 + 0;
	public static final int ARTIFACT_HORN2 = ROW9 + 1;
	public static final int ARTIFACT_HORN3 = ROW9 + 2;
	public static final int ARTIFACT_HORN4 = ROW9 + 3;
	public static final int ARTIFACT_CHALICE1 = ROW9 + 4;
	public static final int ARTIFACT_CHALICE2 = ROW9 + 5;
	public static final int ARTIFACT_CHALICE3 = ROW9 + 6;
	public static final int ARTIFACT_SANDALS = ROW9 + 7;
	public static final int ARTIFACT_SHOES = ROW9 + 8;
	public static final int ARTIFACT_BOOTS = ROW9 + 9;
	public static final int ARTIFACT_GREAVES = ROW9 + 10;
	public static final int ARTIFACT_ROSE1 = ROW9 + 11;
	public static final int ARTIFACT_ROSE2 = ROW9 + 12;
	public static final int ARTIFACT_ROSE3 = ROW9 + 13;
	public static final int ARTIFACT_CHAINS = ROW17 + 15;
	public static final int LUCKY_BADGE = ROW5 + 14;

	// Row Ten: Scrolls
	public static final int SCROLL_KAUNAN = ROW10 + 0;
	public static final int SCROLL_SOWILO = ROW10 + 1;
	public static final int SCROLL_LAGUZ = ROW10 + 2;
	public static final int SCROLL_YNGVI = ROW10 + 3;
	public static final int SCROLL_GYFU = ROW10 + 4;
	public static final int SCROLL_RAIDO = ROW10 + 5;
	public static final int SCROLL_ISAZ = ROW10 + 6;
	public static final int SCROLL_MANNAZ = ROW10 + 7;
	public static final int SCROLL_NAUDIZ = ROW10 + 8;
	public static final int SCROLL_BERKANAN = ROW10 + 9;
	public static final int SCROLL_NCOSRANE = ROW10 + 10;
	public static final int SCROLL_TIWAZ = ROW10 + 11;
	public static final int SCROLL_NENDIL = ROW10 + 12;
	public static final int SCROLL_LIBRA = ROW10 + 14;
	public static final int JOURNAL_PAGE = ROW10 + 13;

	// Row Eleven: Potions
	public static final int POTION_CRIMSON = ROW11 + 0;
	public static final int POTION_AMBER = ROW11 + 1;
	public static final int POTION_GOLDEN = ROW11 + 2;
	public static final int POTION_JADE = ROW11 + 3;
	public static final int POTION_TURQUOISE = ROW11 + 4;
	public static final int POTION_AZURE = ROW11 + 5;
	public static final int POTION_INDIGO = ROW11 + 6;
	public static final int POTION_MAGENTA = ROW11 + 7;
	public static final int POTION_BISTRE = ROW11 + 8;
	public static final int POTION_CHARCOAL = ROW11 + 9;
	public static final int POTION_SILVER = ROW11 + 10;
	public static final int POTION_IVORY = ROW11 + 11;
	public static final int POTION_AQUA = ROW11 + 12;
	public static final int POTION_HONEY = ROW11 + 13;
	public static final int POTION_VIOLET = ROW11 + 14;
	public static final int POTION_MIHUANG = ROW11 + 15;

	// Row Twelve: Seeds
	public static final int SEED_ROTBERRY = ROW12 + 0;
	public static final int SEED_FIREBLOOM = ROW12 + 1;
	public static final int SEED_BLINDWEED = ROW12 + 2;
	public static final int SEED_SUNGRASS = ROW12 + 3;
	public static final int SEED_ICECAP = ROW12 + 4;
	public static final int SEED_STORMVINE = ROW12 + 5;
	public static final int SEED_SORROWMOSS = ROW12 + 6;
	public static final int SEED_DREAMFOIL = ROW12 + 7;
	public static final int SEED_EARTHROOT = ROW12 + 8;
	public static final int SEED_FADELEAF = ROW12 + 9;
	public static final int SEED_BLANDFRUIT = ROW12 + 10;
	public static final int SEED_DUNGEONNUT = ROW12 + 11;
	public static final int SEED_TOASTEDDUNGEONNUT = ROW12 + 12;
	public static final int SEED_GOLDENDUNGEONNUT = ROW12 + 13;
	public static final int SEED_BLACKBERRY = ROW12 + 14;
	public static final int SEED_RICE = ROW12 + 15;
	public static final int SEED_CLOUDBERRY = ROW13 + 11;
	public static final int SEED_BLUEBERRY = ROW13 + 12;
	public static final int SEED_MOONBERRY = ROW13 + 14;
	public static final int SEED_FULLMOONBERRY = ROW13 + 15;
	public static final int SEED_STARFLOWER = ROW9 + 15;
	public static final int SEED_PHASEPITCHER = ROW14 + 5;
	public static final int SEED_FLYTRAP = ROW5 + 12;
	public static final int SEED_DEWCATCHER	= ROW5+13;
	public static final int SEED_SEEDPOD = ROW5 + 15;

	// Row Thirteen: Food
	public static final int MEAT = ROW13 + 0;
	public static final int STEAK = ROW13 + 1;
	public static final int OVERPRICED = ROW13 + 2;

	public static final int CARPACCIO = ROW13 + 3;

	public static final int BLANDFRUIT = ROW13 + 4;
	public static final int RATION = ROW13 + 5;
	public static final int PASTY = ROW13 + 6;
	public static final int MYSTERYMEAT = ROW13 + 7;

	// Row Fourteen: Quest Items
	public static final int SKULL = ROW14 + 0;
	public static final int DUST = ROW14 + 1;
	public static final int PICKAXE = ROW14 + 2;
	public static final int ORE = ROW14 + 3;
	public static final int TOKEN = ROW14 + 4;
	public static final int ATRIFORCE = ROW13 + 9;
	public static final int TRIFORCE = ROW17 + 4;
	public static final int MUSHROOM = ROW9 + 14;
	public static final int MUSHROOM_DEATHCAP = ROW14 + 9;
	public static final int MUSHROOM_LANTERN =  ROW14 + 6;
	public static final int MUSHROOM_LICHEN = ROW14 + 8;
	public static final int MUSHROOM_EARTHSTAR = ROW14 + 7;
	public static final int MUSHROOM_BLUEMILK = ROW14 + 12;
	public static final int MUSHROOM_GOLDENJELLY = ROW14 + 11;
	public static final int MUSHROOM_PIXIEPARASOL= ROW14 + 10;

	// Row Fifteen: Containers/Bags
	public static final int VIAL = ROW15 + 0;
	public static final int POUCH = ROW15 + 1;
	public static final int HOLDER = ROW15 + 2;
	public static final int BANDOLIER = ROW15 + 3;
	public static final int HOLSTER = ROW15 + 4;
	public static final int SHOPCART = ROW15 + 5;
	public static final int ACTIVEMRD = ROW15 + 6;
	public static final int RUSTY_CAT = ROW15 + 7;
	public static final int ORBOFZOT = ROW15 + 10;
	public static final int KEYRING = ROW4 + 10;

	//Bombs
	public static final int DARK_BOMB = ROW13 + 8;
	public static final int FISH_BOMB = ROW13 + 10;
	public static final int LIGHT_BOMB = ROW13 + 13;
	public static final int FIRE_BOMB = ROW15 + 11;
	public static final int STORM_BOMB = ROW15 + 12;
	public static final int EARTH_BOMB = ROW15 + 13;
	public static final int ICE_BOMB = ROW15 + 14;
	public static final int HUGE_BOMB = ROW15 + 15;	
	public static final int MINI_BOMB = ROW15 + 8;

    public static final int BUILD_BOMB = ROW15 + 9;
	
	// Row Sixteen: Random OP Stuff
	public static final int UPGRADEGOO_YELLOW = ROW16 + 0;
	public static final int UPGRADEGOO_RED = ROW16 + 1;
	public static final int UPGRADEGOO_VIOLET = ROW16 + 2;
	public static final int EGG = ROW16 + 4;
	public static final int TRIDENT = ROW16 + 5;
	public static final int ARESSWORD = ROW16 + 6;
	public static final int JUPITERSWRAITH = ROW16 + 7;
	public static final int CROMAXE = ROW16 + 8;
	public static final int LOKISFLAIL = ROW16 + 9;
	public static final int NORNGREEN = ROW16 + 11;
	public static final int NORNBLUE = ROW16 + 12;
	public static final int NORNORANGE = ROW16 + 13;
	public static final int NORNPURPLE = ROW16 + 14;
	public static final int NORNYELLOW = ROW16 + 15;
	
	// Row Seventeen: Random Lame Stuff
	public static final int TOWEL = ROW17 + 0;
	public static final int OTILUKES_SPECS = ROW17 + 1;
	public static final int PUDDING_CUP = ROW17 + 2;
	public static final int KNOWLEDGE_BOOK = ROW17 + 3;
	

    public static final int JUMP = ROW17 + 5;
	public static final int SHIELD = ROW17 + 6;
	public static final int DKNIVE = ROW17 + 7;
	public static final int NUNCHAKUS = ROW17 + 9;
	
	public static final int TEKKOKAGI = ROW17 + 10;
	public static final int WRAITHBREATH = ROW4 + 9; 
	public static final int GSWORD = ROW17 + 11;
	public static final int HALBERD = ROW17 + 12;
	public static final int MAP = ROW6 + 14;
	public static final int PLAYERICON = ROW17 + 13;
	public static final int SHOESKIT = ROW17 + 14;
	public static final int TREE = ROW6 + 15;
	
	public static final int SEWERSKEY = ROW18 + 0;
	public static final int PRISONKEY = ROW18 + 1;
	public static final int CAVEKEY = ROW18 + 2;
	public static final int CITYKEY = ROW18 + 3;
	public static final int POTKEY = ROW18 + 4;
	public static final int TENGUKEY = ROW18 + 5;

	public static final int M_WEB = ROW18 + 13;
	
	public static final int RANDOWNEGG = ROW19 + 0;
	public static final int SCORPIONEGG = ROW19 + 1;

	public static final int SPIDEREGG = ROW19 + 2;

	public static final int FIREEGG = ROW19 + 3;
	public static final int RED_DRAGONEGG = ROW19 + 4;
	public static final int BLUE_DRAGONEGG = ROW19 + 5;
	public static final int VIOLET_DRAGONEGG = ROW19 + 6;
	public static final int GREEN_DRAGONEGG = ROW19 + 7;
	public static final int SHADOWEGG = ROW19 + 8;
	
	public static final int PET_FOOD = ROW19 + 9;

	public static final int RABBITEGG = ROW19 + 10;

	public static final int VELOCIROOSTEREGG = ROW19 + 11;

	public static final int LIGHT_DRAGONEGG = ROW19 + 13;
	public static final int GOLD_DRAGONEGG = ROW19 + 14;

	public static final int FAIRYCARD = ROW19 + 12;

	public static final int HUGESHURIKEN = ROW8 + 15;
	public static final int TPTRAP = ROW3 + 15;



	public static final int FIGHTGLOVES = ROW18 + 6;
	public static final int LANCE = ROW20 + 11;
	public static final int RAPIER = ROW20 + 12;
	public static final int CLUB = ROW20 + 13;
	public static final int GNOLL_ARMOR = ROW20 + 14;
	public static final int FIRECRACKER = ROW20 + 15;

	public static final int ITEM_BAG = ROW18 + 15;
	public static final int PUMPKIN = ROW19 + 15;

	public static final int EASTER_EGG = ROW16 + 10;
	public static final int PUMPKIN_PIE = ROW20 + 0 ;
	public static final int CANDY_CANE = ROW20 + 1 ;
	public static final int TURKEY_MEAT = ROW20 + 2;
	public static final int SPRING_ASSORTED = ROW20 + 3;

	public static final int MOAI = ROW20 + 6;
	public static final int POCKETBALL_EMPTY = ROW20 + 7;
	public static final int SPAMMO = ROW20 + 9;

	public static final int ERROR_WEAPON = ROW18 + 7;
	public static final int ERROR_ARMOR = ROW18 + 8;
	public static final int ERROR_WAND = ROW18 + 9;
	public static final int MIND_ARROW = ROW18 + 10;

	public static final int ERROR_FOOD = ROW18 + 11;
	public static final int ERROR_AMMO = ROW20 + 5;


	public static final int H_O_SCARECROW = ROW18 + 14;

	public static final int ELEVATOR = ROW20 + 10;

	public static final int HOOK_HAM = ROW21 + 1;
	public static final int KNOWNLADGE_FOOD = ROW21 + 2;

	public static final int SKILL_ATK = ROW21 + 8;
	public static final int SKILL_DEF = ROW21 + 9;
	public static final int SKILL_MIG = ROW21 + 10;
	public static final int WARRIORSHIELD = ROW21 + 11;

	public static final int TRIANGOLO = ROW21 + 0;
	public static final int	FLUTE = ROW21 + 3;
	public static final int WARDURM = ROW21 + 4;
	public static final int TRUMPET = ROW21 + 5;
	public static final int HARP = ROW21 + 6;
	public static final int SHOVEL = ROW21 + 7;
	public static final int GLASSTOTEM = ROW21 + 12 ;
	public static final int ARMOR_PERFORMER = ROW21 + 13;
	
	public static final int HAMBURGER = ROW22 + 0;
	public static final int HONEYWATER = ROW22 + 1;
	public static final int CHICKENNUGGET = ROW22 + 2;
	public static final int HONEYMEAT = ROW22 + 3;
	public static final int MEATSOUP = ROW22 + 4;
	public static final int HOTDOG = ROW22 + 5;
	public static final int KEBAB = ROW22 + 6 ;

	public static final int SANDWICH = ROW22 + 7;

	public static final int ALIEN_BAG = ROW22 + 8;
	public static final int ICECREAM = ROW22 + 9;
	public static final int VEGETABLESOUP = ROW22 + 10;
	public static final int RICE = ROW22 + 11;
	public static final int FRUITSALAD = ROW22 + 12;
	public static final int FOAMED = ROW22 + 13;
	public static final int EAT_GRASS = ROW22 + 14;
	public static final int PERFECT_FOOD = ROW22 + 15;

	public static final int CHALLENGE_BOOK = ROW21 + 14;
	public static final int GOEI = ROW21 + 15;

	public static final int VEST_ARMOR = ROW23 + 0;
	public static final int RUBBER_ARMOR = ROW23 + 1;
	public static final int CD_ARMOR = ROW23 + 2;
	public static final int STY_ARMOR = ROW23 + 3;
	public static final int PRO_ARMOR = ROW23 + 4;
	public static final int PHANTOM_ARMOR = ROW23 + 5;

	public static final int WOODEN_ARMOR = ROW23 + 6;
	public static final int CERAMICS_ARMOR = ROW23 + 7;
	public static final int STONE_ARMOR = ROW23 + 8;
	public static final int MUL_ARMOR = ROW23 + 9;
	public static final int BULLET_ARMOR = ROW23 + 10;
	public static final int MECH_ARMOR = ROW23 + 11;

	public static final int ARMOR_SOLDIER = ROW23 + 13;
	public static final int ARMOR_FOLLOWER = ROW27 + 8;

	public static final int HERB_MEAT = ROW23 + 12;

	public static final int GUN_A = ROW24 + 0;
	public static final int GUN_B = ROW24 + 1;
	public static final int GUN_C = ROW24 + 2;
	public static final int GUN_D = ROW24 + 3;
	public static final int GUN_E = ROW24 + 4;
	public static final int GUN_S = ROW24 + 5;
	public static final int TOYGUN = ROW24 + 6;
	public static final int SLING = ROW24 + 7;
	public static final int MOBS = ROW24 + 8;
	public static final int W_BRICK = ROW24 + 9;


	public static final int PILL_ATK = ROW24 + 10;
	public static final int PILL_DEF = ROW24 + 11;
	public static final int PILL_STR = ROW24 + 12;
	public static final int PILL_MIG = ROW24 + 13;
	public static final int PILL_MUC = ROW24 + 14;
	public static final int PILL_SOT = ROW24 + 15;

	public static final int WOODEN_H = ROW25 + 12;

	public static final int POTION = ROW18 + 12;

	public static final int BRICK = ROW20 + 4;

	public static final int FOURCLOVER = ROW23 + 14;
	public static final int POTION_OF_MAGE = ROW23 + 15;

	public static final int LOLLIPOP = ROW25 + 0;
	public static final int JELLY_SWORD = ROW25 + 1;
	public static final int POWER_HAND = ROW25 + 2;
	public static final int S_J_R_B_M = ROW25 + 3;
	public static final int FRENCHFRIES = ROW25 + 4;
	public static final int CHOCOLATE = ROW25 + 5;
	public static final int FOODFANS = ROW25 + 6;

	public static final int ATTACKSHIELD = ROW25 + 8;

	public static final int BSHOVEL = ROW25 + 7;

	public static final int CANNON_OF_MAGE = ROW25 + 9;

	public static final int MANY_KNIVE = ROW25 + 10;

	public static final int MK_BOX = ROW25 + 11;

	public static final int S_AND_S = ROW25 + 13;

	public static final int AFLY_FOOD = ROW25 + 14;

	public static final int FLAG = ROW25 + 15;

	public static final int COPY_BALL = ROW26 + 1;
	public static final int EMPTY_BOMB = ROW26 + 2;
	public static final int SHATTERED_AMMO = ROW26 + 3;
	public static final int BOW = ROW26 + 4;

	public static final int STAR_FLOWER = ROW26 + 5;
	public static final int UP_EATER = ROW26 + 6;
	public static final int TRAN_BALL = ROW26 + 7;
	public static final int DREAM_L = ROW26 + 8;
	public static final int HEAL_L = ROW26 + 9;
	public static final int MECH_POCKET = ROW26 + 10;

	public static final int DEMO_SCROLL = ROW26 + 11;
	public static final int UNDEAD_BOOK = ROW26 + 12;
	public static final int GNOLL_MARK = ROW26 + 13;

	public static final int HELMET = ROW26 + 14;
	public static final int WOODEN_STAFF = ROW26 + 15;


	public static final int HEAL_BAG = ROW27 + 0;

	public static final int MACE = ROW27 + 4;
	public static final int HOLY_WATER = ROW27 + 2;
	public static final int PRAYER_WHEEL = ROW27 + 5;
	public static final int STONE_CROSS = ROW27 + 1;

	public static final int FAITH_SIGN = ROW27 + 6;
	public static final int ARTIFACT_BEACON = ROW27 + 7;

	public static final int DIAMOND_PICKAXE = ROW27 + 3;

	public static final int LYNN_DOLL = ROW27 + 9;
	public static final int MONEY_PACK = ROW27 + 10;

	public static final int DOOR_BLOCK = ROW27 + 11;
	public static final int WALL_BLOCK = ROW27 + 12;
	public static final int STONE_BLOCK = ROW27 + 13;
	public static final int WOODEN_BLOCK = ROW27 + 14;
	public static final int BOOK_BLOCK = ROW27 + 15;
	public static final int HORSE_TOTEM = ROW28 + 0;
	public static final int RANGE_BAG = ROW28 + 1;
	public static final int DANCE_LION = ROW28 + 2;
	public static final int LIVE_ARMOR = ROW28 + 3;
	public static final int SHADOW_EATER = ROW28 + 4;
	public static final int GOBLIN_SHIELD = ROW28 + 5;
	public static final int CURSE_BLOOD = ROW28 + 6;
	public static final int TENGU_SWORD = ROW28 + 7;
	public static final int SP_KNUCKLE = ROW28 + 8;
	public static final int POTION_CATALYST = ROW28 + 9;
	public static final int ELF_BOW = ROW28 + 10;
	public static final int DEMON_BLADE = ROW28 + 11;
	public static final int DICE_TOWER = ROW28 + 12;
	public static final int BOAT = ROW28 + 13;
	public static final int ZONGZI = ROW28 + 15;
	public static final int WINE = ROW28 + 14;

	public static final int NEED_PAPER = ROW29 + 0;
	public static final int PPC = ROW29 + 1;
	public static final int H_GEL = ROW29 + 2;

	public static final int MIX_RICE = ROW29 + 3;
	public static final int C_BOW = ROW29 + 4;

	public static final int H_RICE = ROW29 + 5;

    public static final int LEADER_FLAG = ROW29 + 13;
	public static final int WATER_BLOCK = ROW29 + 14;
    public static final int REWARD_PAPER  = ROW30 + 0;
	public static final int NUT_CAKE  = ROW30 + 1;
	public static final int ELEKATANA  = ROW30 + 3;
	public static final int REDHAND  = ROW30 + 2;
	public static final int SHOOTGUN  = ROW30 + 4;
	public static final int TRICK_SAND  = ROW30 + 5;
	public static final int STRAWBERRY  = ROW30 + 6;
	public static final int CHERRY  = ROW30 + 7;
	public static final int MOON_CAKE  = ROW30 + 8;
	public static final int HARO_THE_PET  = ROW30 + 9;
    public static final int BIG_BATTERY = ROW30 + 10;
    public static final int ASCETIC_SHELL = ROW30 + 11;
	public static final int GREATRUNE = ROW30 + 12;
	public static final int SHATTERED_FIRE = ROW30 + 13;
	public static final int HOLY_MACE = ROW30 + 14;
	public static final int GREAT_PILL = ROW30 + 15;

	public static final int BOTTLE_FLOWER  = ROW32 + 0;
	public static final int AFLY_SOCK  = ROW32 + 1;
	public static final int TEMPEST_B  = ROW32 + 2;
	public static final int CATSHARK  = ROW32 + 3;
	public static final int FISH_FOOD  = ROW32 + 4;
	public static final int N_S  = ROW32 + 5;
	public static final int TEST_CLOAK  = ROW32 + 6;
	public static final int CROSS_PHOTO  = ROW32 + 7;
	public static final int SIMPLE_S  = ROW32 + 8;
	public static final int XIXI_BOX  = ROW32 + 9;
	public static final int CRYSTAL_VIAL  = ROW32 + 10;
	public static final int APK931  = ROW32 + 11;
	public static final int CURSE_PHONE  = ROW32 + 12;
	public static final int SHEEPFUR  = ROW32 + 13;
	public static final int RAIN_SHIELD  = ROW32 + 14;
	public static final int FISHBONE  = ROW32 + 15;

	public static final int HONEY_ARROW  = ROW33 + 0;
	public static final int A_BOX  = ROW33 + 1;
	public static final int TISSUE  = ROW33 + 2;
	public static final int BOTTLE_FIRE  = ROW33 + 3;
	public static final int UNCLE_DUMBBELL  = ROW33 + 4;
	public static final int SELL_PERMIT  = ROW33 + 5;
	public static final int MIRROR_2  = ROW33 + 6;
	public static final int HUMMING_TOOL  = ROW33 + 7;
	public static final int BROKEN_HAMMER = ROW33 + 8;
	public static final int FUNNY_FOOD = ROW33 + 9;
	public static final int PINK_FISH = ROW33 + 10;
	public static final int GIRL_ROSE = ROW33 + 11;
	public static final int GRASSBOOK = ROW33 + 12;
	public static final int PAPERFAN = ROW33 + 13;

	public static final int MIRROR_DOLL  = ROW34 + 0;
	public static final int WIND_BOTTLE  = ROW34 + 1;
	public static final int HAND_LIGHT  = ROW34 + 2;
	public static final int CURSE_BOX  = ROW34 + 3;	
	
	public static final int SKILL_WARRIOR  = ROW34 + 4;	
	public static final int SKILL_MAGE  = ROW34 + 5;	
	public static final int SKILL_ROGUE   = ROW34 + 6;	
	public static final int SKILL_HUNTER  = ROW34 + 7;	
	public static final int SKILL_PERFORMER  = ROW34 + 8;	
	public static final int SKILL_SOLDIER  = ROW34 + 9;	
	public static final int SKILL_FOLLOWER  = ROW34 + 10;	
	public static final int SKILL_ASCETIC  = ROW34 + 11;

	public static final int PLANT_POT  = ROW34 + 12;
	public static final int SKILL_BOOK  = ROW34 + 13;
	public static final int VIP_CARD  = ROW34 + 14;
	
	public static final int TEST_WEAPON  = ROW31 + 0;
	public static final int TEST_ARMOR  = ROW31 + 1;
	public static final int TEST_WAND  = ROW31 + 2;
	public static final int SCR_BOOK  = ROW31 + 3;
	public static final int STR_BOTTLE  = ROW31 + 4;

	public static final int NUT_COOKIE  = ROW31 + 5;
	public static final int MIX_PIZZA  = ROW31 + 6;
	public static final int RICE_GRUEL  = ROW31 + 7;
	public static final int FRUIT_CANDY  = ROW31 + 8;
	public static final int NINJA_FAN  = ROW31 + 9;
	public static final int WHISK = ROW31 + 10;

	public static final int MEGA_CANNON = ROW31 + 11;

	public static final int INFO  = ROW35 + 0;
	public static final int INFO_1  = ROW35 + 1;
	public static final int INFO_2  = ROW35 + 2;
	public static final int INFO_3  = ROW35 + 3;
	public static final int INFO_4  = ROW35 + 4;
	public static final int INFO_5  = ROW35 + 5;
	public static final int INFO_6  = ROW35 + 6;

	public static final int INFO2  = ROW35 + 8;
	public static final int INFO2_1  = ROW35 + 9;
	public static final int INFO2_2  = ROW35 + 10;
	public static final int INFO2_3  = ROW35 + 11;
	public static final int INFO2_4  = ROW35 + 12;
	public static final int INFO2_5  = ROW35 + 13;
	public static final int INFO2_6  = ROW35 + 14;
}
