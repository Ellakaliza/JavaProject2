package exceptions_a2;
/*
 * Name(s) and ID(s) (Ange Akaliza 40270048)
	COMP249
	Assignment # (2)
	Due Date (March 27th 2024)
 */
public class MissingFieldsException extends Exception{
	public MissingFieldsException(String message) {
		super(message);
	}
	public MissingFieldsException() {
		super();
	}
}
