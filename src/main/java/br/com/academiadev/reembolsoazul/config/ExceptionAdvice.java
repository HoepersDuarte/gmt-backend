package br.com.academiadev.reembolsoazul.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.NoResultException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import br.com.academiadev.reembolsoazul.exception.APIException;

public class ExceptionAdvice {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionAdvice.class);

	@ExceptionHandler(value = Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleAnyException(final Exception exception, final WebRequest request) {
		log.error(exception.getMessage(), exception);
		return getDefaultErrorResponse(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = NoResultException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleNoResultException(final Exception exception,
			final WebRequest request) {
		log.error(exception.getMessage(), exception);
		return getDefaultErrorResponse(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = AccessDeniedException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleAccessControlException(final Exception exception,
			final WebRequest request) {
		log.error(exception.getMessage(), exception);
		return getDefaultErrorResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(value = APIException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<Map<String, Object>> handleNgAlertException(final APIException exception,
			final WebRequest request) {
		log.error(exception.getMessage(), exception);
		return getApiAlertMessage(exception.getMessage(), exception.getErrorKey(), HttpStatus.BAD_REQUEST);
	}

	private ResponseEntity<Map<String, Object>> getApiAlertMessage(final String message, String errorKey,
			final HttpStatus httpStatus) {
		final Map<String, Object> errorMap = getMessageErrorMap(message, errorKey, httpStatus);
		return new ResponseEntity<>(errorMap, httpStatus);
	}

	private Map<String, Object> getMessageErrorMap(String message, String errorKey, HttpStatus httpStatus) {
		final Map<String, Object> map = new LinkedHashMap<>();
		map.put("timestamp", System.currentTimeMillis());
		map.put("status", httpStatus.value());
		map.put("message", message);
		map.put("errorKey", errorKey);

		Map<String, Object> formReativo = new HashMap<>();
		formReativo.put("usuarioUnico", true);
		formReativo.put("senhaInvalida", true);
		map.put("form", formReativo);
		return map;
	}

	private ResponseEntity<Map<String, Object>> getDefaultErrorResponse(final String message,
			final HttpStatus httpStatus) {
		final Map<String, Object> errorMap = getMessageErrorMap(message, "label_error_" + httpStatus.value(),
				httpStatus);
		return new ResponseEntity<>(errorMap, httpStatus);
	}
}
