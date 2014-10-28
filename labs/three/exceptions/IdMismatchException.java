package labs.three.exceptions;

public class IdMismatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4787211034394466204L;

	public IdMismatchException() {
		// TODO Auto-generated constructor stub
	}

	public IdMismatchException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public IdMismatchException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public IdMismatchException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public IdMismatchException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}
}
