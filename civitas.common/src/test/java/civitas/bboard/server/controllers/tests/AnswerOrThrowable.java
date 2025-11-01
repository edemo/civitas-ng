package civitas.bboard.server.controllers.tests;

public record AnswerOrThrowable<T>(Throwable throwable, T answer) {}
