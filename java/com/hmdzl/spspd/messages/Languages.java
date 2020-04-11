/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 */
package com.hmdzl.spspd.messages;

import java.util.Locale;

public enum Languages {
	ENGLISH("english",      "", Status.REVIEWED, null, null),

	//RUSSIAN("русский",      "ru", Status.INCOMPLETE, new String[]{"ConsideredHamster", "Inevielle", "yarikonline"}, new String[]{"AttHawk46", "HerrGotlieb", "MrXantar", "Shamahan", "roman.yagodin", "un_logic"}),
	//KOREAN("한국어",         "ko", Status.INCOMPLETE, new String[]{"Flameblast12"}, new String[]{"WondarRabb1t", "ddojin0115", "eeeei", "linterpreteur", "lsiebnie" }),
	CHINESE("中文",          "zh", Status.INCOMPLETE, new String[]{"Jinkeloid(zdx00793)"}, new String[]{"931451545", "HoofBumpBlurryface", "Lery", "Lyn-0401", "ShatteredFlameBlast", "hmdzl001", "tempest102"});
	//FINNISH("suomi", 		"fi", Status.INCOMPLETE, new String[]{"TenguTheKnight"}, null ),
	//POLISH("polski",        "pl", Status.INCOMPLETE, new String[]{"Deksippos", "kuadziw"}, new String[]{"Chasseur", "Darden", "MJedi", "Peperos", "Scharnvirk", "Shmilly", "dusakus", "michaub", "ozziezombie", "szczoteczka22", "szymex73"}),
	
	//ITALIAN("italiano",		"it", Status.INCOMPLETE, new String[]{"bizzolino", "funnydwarf"}, new String[]{"4est", "DaniMare", "Danzl", "andrearubbino00", "nessunluogo", "righi.a", "umby000"}),
	//CATALAN("català",       "ca", Status.INCOMPLETE, new String[]{"Illyatwo2"}, null),
	//SPANISH("español",      "es", Status.INCOMPLETE, new String[]{"Kiroto", "Kohru", "grayscales"}, new String[]{"Alesxanderk", "CorvosUtopy", "Dewstend", "Dyrran", "Fervoreking", "Illyatwo2", "airman12", "alfongad", "benzarr410", "ctrijueque", "dhg121", "javifs", "jonismack1"}),
	//ESPERANTO("esperanto",  "eo", Status.INCOMPLETE, new String[]{"Verdulo"}, null),
	//HUNGARIAN("magyar",     "hu", Status.INCOMPLETE, new String[]{"dorheim"}, new String[]{"Navetelen", "clarovani", "dhialub", "nanometer", "nardomaa"}),
	//TURKISH("türkçe",       "tr", Status.INCOMPLETE, new String[]{"LokiofMillenium", "emrebnk"}, new String[]{"AcuriousPotato", "alpekin98", "denizakalin", "melezorus34"}),
	
	//GERMAN("deutsch",       "de", Status.INCOMPLETE, new String[]{"Dallukas", "KrystalCroft", "Wuzzy", "Zap0", "davedude" }, new String[]{"DarkPixel", "ErichME", "LenzB", "Sarius", "Sorpl3x", "ThunfischGott", "Topicranger", "oragothen"});
	//FRENCH("français",      "fr", Status.INCOMPLETE, new String[]{"Emether", "canc42", "kultissim", "minikrob"}, new String[]{"Alsydis", "Basttee", "Dekadisk", "Draal", "antoine9298", "go11um", "linterpreteur", "solthaar"}),
	//PORTUGUESE("português", "pt", Status.INCOMPLETE, new String[]{"TDF2001", "matheus208"}, new String[]{"ChainedFreaK", "JST", "MadHorus", "Tio_P_", "ancientorange", "danypr23", "ismael.henriques12", "owenreilly", "try31"}),
	//INDONESIAN("indonésien","in", Status.INCOMPLETE, new String[]{"rakapratama"}, null);

	public enum Status{
		//below 60% complete languages are not added.
		INCOMPLETE, //60-99% complete
		UNREVIEWED, //100% complete
		REVIEWED    //100% reviewed
	}

	private String name;
	private String code;
	private Status status;
	private String[] reviewers;
	private String[] translators;

	Languages(String name, String code, Status status, String[] reviewers, String[] translators){
		this.name = name;
		this.code = code;
		this.status = status;
		this.reviewers = reviewers;
		this.translators = translators;
	}

	public String nativeName(){
		return name;
	}

	public String code(){
		return code;
	}

	public Status status(){
		return status;
	}

	public String[] reviewers() {
		if (reviewers == null) return new String[]{};
		else return reviewers.clone();
	}

	public String[] translators() {
		if (translators == null) return new String[]{};
		else return translators.clone();
	}

	public static Languages matchLocale(Locale locale){
		return matchCode(locale.getLanguage());
	}

	public static Languages matchCode(String code){
		for (Languages lang : Languages.values()){
			if (lang.code().equals(code))
				return lang;
		}
		return ENGLISH;
	}

}
