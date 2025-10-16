package civitas.bboard.server.controllers;

public record AnswerOrThrowable<T>(Throwable throwable, T answer) {}
