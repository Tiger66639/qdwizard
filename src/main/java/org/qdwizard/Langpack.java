/*
 *  QDWizard
 *  Copyright (C) Bertrand Florat and others
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *  
 */
package org.qdwizard;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * This class includes all langpacks strings. Note that we don't use
 * ResourceBundle here to ease translation tasks (properties doesn't support
 * non-latin1 characters) and because QDwizard comes with a very low number of
 * strings
 */
public class Langpack {
	private static List<String> defaults = Arrays.asList("Finish", "Cancel", "Previous", "Next");
	// Strings content : "Finish", "Cancel", "Previous", "Next"
	static private Map<Locale, List<String>> strings = new HashMap<Locale, List<String>>(20);
	/** static set of strings, can contain non-ISO8859 chars * */
	static {
		strings.put(new Locale("ca"),
				Arrays.asList("Finalitzar", "Cancelar", "Anterior", "Següent"));
		strings.put(new Locale("cs"), Arrays.asList("Dokončit", "Storno", "Předchozí", "Další"));
		strings.put(new Locale("de"), Arrays.asList("Fertig", "Abbrechen", "Zurück", "Weiter"));
		strings.put(new Locale("en"), defaults);
		strings.put(new Locale("es"),
				Arrays.asList("Finalizar", "Cancelar", "Anterior", "Siguiente"));
		strings.put(new Locale("el"), Arrays.asList("Τέλος", "Ακύρωση", "Προηγούμενο", "Επόμενο"));
		strings.put(new Locale("fr"), Arrays.asList("Terminé", "Annuler", "Précédent", "Suivant"));
		strings.put(new Locale("gl"), Arrays.asList("Rematar", "Cancelar", "Anterior", "Seguinte"));
		strings.put(new Locale("nl"),
				Arrays.asList("Afgerond", "Annuleren", "Vooropgaand", "Aanstaande"));
		strings.put(new Locale("ru"), Arrays.asList("Готово", "Отмена", "Назад", "Дальше"));
		strings.put(new Locale("pt"), Arrays.asList("Terminar", "Cancelar", "Anterior", "Seguinte"));
		strings.put(new Locale("zh"), Arrays.asList("完成", "取消", "上一步", "下一步"));
	}
	/** Used locale for the wizard buttons, use English as a default *. */
	private static Locale locale = new Locale("en");

	/**
	 * private constructor for utility class with only static methods.
	 */
	private Langpack() {
		super();
	}

	/**
	 * Set the QDwizard locale.
	 * 
	 * @param locale
	 *            the locale to set
	 */
	public static void setLocale(Locale locale) {
		Langpack.locale = locale;
	}

	/**
	 * Add a QDwizard locale and associated labels. This can be used as well to
	 * override the langpacks provided with QDWizard.
	 * 
	 * @param locale
	 *            the locale to add.
	 * @param labels
	 *            the labels associated with the locale. It should contain 4
	 *            strings translating "Finish", "Cancel", "Previous", "Next".
	 * @throws IllegalArgumentException
	 *             if provided locale is null or if labels is null or has the
	 *             wrong size.
	 */
	public static void addLocale(Locale locale, List<String> labels) {
		if (locale == null || labels == null) {
			throw new IllegalArgumentException("locale and labels can't be null");
		}
		if (labels.size() != defaults.size()) {
			throw new IllegalArgumentException("Provided labels should have this size : "
					+ defaults.size());
		}
		strings.put(locale, labels);
	}

	/**
	 * Return label for given key or null if not matching key is found.
	 * 
	 * @param key
	 *            the key as a string using the default locale
	 * 
	 * @return label for given key or null if not matching key is found
	 */
	public static String getMessage(String key) {
		List<String> labels = strings.get(locale);
		// If the local is unknown, use default one
		if (labels == null) {
			labels = defaults;
		}
		int index = defaults.indexOf(key);
		return labels.get(index);
	}
}
