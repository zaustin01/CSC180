package labs.four.exceptions;

public class UnknownClassException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnknownClassException() {
		super();
	}

	public UnknownClassException(String arg0) {
		super(arg0);
	}

	public UnknownClassException(Throwable arg0) {
		super(arg0);
	}

	public UnknownClassException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UnknownClassException(String arg0, Throwable arg1, boolean arg2,
			boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
