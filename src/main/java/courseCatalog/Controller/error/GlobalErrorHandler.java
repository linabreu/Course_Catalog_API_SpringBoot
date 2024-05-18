package courseCatalog.Controller.error;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

import org.hibernate.resource.transaction.backend.jta.internal.synchronization.ExceptionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;



@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	private enum logStatus //log entire stack trace or just the message
	{
		STACK_TRACE, MESSAGE_ONLY
	}
	
	@Data
	private class ExceptionMessage 
	{
		private String message;
		private String statusReason;
		private int statusCode;
		private String timeStamp;
		private String url;
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ExceptionMessage handleNoSuchElementException(NoSuchElementException e, 
			WebRequest webRequest)
	{
		return buildExceptionMessage(e, HttpStatus.NOT_FOUND, webRequest, 
				logStatus.MESSAGE_ONLY);
	}

	private ExceptionMessage buildExceptionMessage(Exception e, 
			HttpStatus status, WebRequest webRequest,
			logStatus logStatus) 
	{
		String message = e.toString();
		String uri = null;
		String statusReason = status.getReasonPhrase();
		int statusCode = status.value();
		String timeStamp = ZonedDateTime.now().format(DateTimeFormatter.RFC_1123_DATE_TIME);

		if(webRequest instanceof ServletWebRequest servWebReq)
		{
			uri = servWebReq.getRequest().getRequestURI();
		}
		
		if(logStatus == logStatus.MESSAGE_ONLY)
		{
			log.error("Exception: {}", e.toString());
		}
		else
		{
			log.error("Exception: ", e);
		}
		
		ExceptionMessage em = new ExceptionMessage();
		em.setMessage(message);
		em.setStatusReason(statusReason);
		em.setStatusCode(statusCode);
		em.setTimeStamp(timeStamp);
		em.setUrl(uri);
		return em;
		
	}
	//unsupported operation exception
	@ExceptionHandler(UnsupportedOperationException.class)
	@ResponseStatus (code = HttpStatus.METHOD_NOT_ALLOWED)
	public ExceptionMessage handleUnsupportedOperationException 
	(UnsupportedOperationException e, WebRequest webRequest )
	{
		return buildExceptionMessage(e, HttpStatus.METHOD_NOT_ALLOWED, 
				webRequest, logStatus.MESSAGE_ONLY);

		
	}
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public ExceptionMessage handleException(Exception e, WebRequest webRequest)
	{
		return buildExceptionMessage(e, HttpStatus.INTERNAL_SERVER_ERROR, 
				webRequest, logStatus.STACK_TRACE);
	}

}
