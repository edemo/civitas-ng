package civitas.bboard.server.controllers;

import lombok.Data;

@Data
public class AnswerOrThrowable<T> {
	final Throwable throwable;
	final T answer;

}