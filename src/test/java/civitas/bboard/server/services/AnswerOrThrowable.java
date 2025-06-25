package civitas.bboard.server.services;

import lombok.Data;

@Data
public class AnswerOrThrowable<T> {
	final Throwable throwable;
	final T answer;

}