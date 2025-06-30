package civitas.bboard.server.controllers;

import civitas.crypto.signature.Signature;
import lombok.Data;

@Data
public class PostDTO {
	final public String meta;
	final public String payloadXml;
	final public Signature signature;
}