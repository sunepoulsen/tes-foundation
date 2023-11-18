package dk.sunepoulsen.tes.xml.exceptions;

/**
 * Exception to be thrown by <code>XmlUnmarshaller</code> in case of errors
 * while unmarshalling a XML source.
 *
 * @see dk.sunepoulsen.tes.xml.XmlUnmarshaller
 */
public class UnmarshalXmlException extends Exception {
    /**
     * Constructs a new exception with the specified detail message and cause.
     * <p>
     * Note that the detail message associated with cause is not automatically incorporated in this exception's
     * detail message.
     * </p>
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param throwable the cause (which is saved for later retrieval by the getCause() method). (A null
     *                  value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public UnmarshalXmlException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
