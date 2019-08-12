package com.meProject.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * @author Kanika 
 * Util class to do all the required date calculations/manipulations
 */
public class DateUtil {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	/**
	 * Method to convert string to date
	 * @param date
	 * @return
	 * @throws DateTimeParseException
	 */
	public static LocalDateTime getDateTimeFromString(String date) throws DateTimeParseException{
		LocalDateTime pasredDateTime = null;
		pasredDateTime = LocalDateTime.parse(date, formatter);
		return pasredDateTime;
	}
}
