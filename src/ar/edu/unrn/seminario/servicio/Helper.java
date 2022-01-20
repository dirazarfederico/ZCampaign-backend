package ar.edu.unrn.seminario.servicio;

import java.util.*;
import java.text.*;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Helper {
	private static final String FORMATOFECHA = "dd/MM/yyyy";
	private static final String FORMATOFECHAHORA = "dd/MM/yyyy HH:mm";
	
	/**
	 * Dado una fecha, devuelve un String de acuerdo a FORMATO.
	 */
	public static String convertir(Date fecha) {

		SimpleDateFormat sdf = new SimpleDateFormat(FORMATOFECHA);
		return sdf.format(fecha);
	}
	
	/**
	 * Dado un String, devuelve la fecha que representa.
	 */
	public static Date convertir(String fecha) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(FORMATOFECHA);
		Date fechaResultado = sdf.parse(fecha);
		return fechaResultado;
	}
	
	/**
	 * Dado una fecha y hora, devuelve un String de acuerdo a FORMATOFECHAHORA.
	 */
	public static String convertirFechaHora(Date fecha) {

		SimpleDateFormat sdf = new SimpleDateFormat(FORMATOFECHAHORA);
		return sdf.format(fecha);
	}
	
	/**
	 * Dado un String, devuelve la fecha y hora que representa.
	 */
	public static Date convertirFechaHora(String fecha) throws ParseException {

		SimpleDateFormat sdf = new SimpleDateFormat(FORMATOFECHAHORA);
		Date fechaResultado = sdf.parse(fecha);
		return fechaResultado;
	}
	
	/**
	 * Dado una fecha de tipo java.util.Date la convierte en java.time.LocalDateTime
	 */
	public static LocalDateTime fechaHoraAFechaLocal(Date fecha) {
		return Instant.ofEpochMilli(fecha.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	/**
	 * Dada una fecha de tipo java.time.LocalDateTime la convierte en java.util.Date
	 */
	public static Date fechaLocalAFecha(LocalDateTime fechaLocal) {
		return Date.from(fechaLocal.atZone(ZoneId.systemDefault()).toInstant());
	}

}
