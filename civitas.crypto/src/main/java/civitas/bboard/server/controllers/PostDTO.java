package civitas.bboard.server.controllers;

import civitas.crypto.signature.Signature;

public record PostDTO(String meta, String payloadXml, Signature signature) {}
