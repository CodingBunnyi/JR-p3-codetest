package exceptions;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FormatException extends RuntimeException {
    private static final Logger logger = LogManager.getLogger(FormatException.class);
}
