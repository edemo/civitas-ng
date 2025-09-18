package civitas.bboard.server.controllers;

import civitas.crypto.signature.Signature;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
	public String meta;
	public String payloadXml;
	public Signature signature;
}
