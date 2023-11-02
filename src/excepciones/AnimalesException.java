package excepciones;

public class AnimalesException extends Exception {

	private static final long serialVersionUID = 9075095558068192043L;

	public AnimalesException() {
		super();
	}

	public AnimalesException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AnimalesException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnimalesException(String message) {
		super(message);
	}

	public AnimalesException(Throwable cause) {
		super(cause);
	}
	
	
}
