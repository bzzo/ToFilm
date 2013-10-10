package info.talacha.filmweb.settings;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logowanie komunikatów o stanie aplikacji
 * 
 * @author Paweł Talacha <pawel@talacha.info>
 */
public class MsgLogger {

	static public Logger setup(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		logger.setLevel(Config.LOG_LVL);
		if (logger.getHandlers().length == 0) {
			try {
				FileHandler fileTxt = new FileHandler(Config.LOG_FILE);
				SimpleFormatter formatterTxt = new SimpleFormatter();
				fileTxt.setFormatter(formatterTxt);
				fileTxt.setEncoding("UTF-8");
				logger.addHandler(fileTxt);
			} catch (IOException ex) {
				Logger.getLogger(MsgLogger.class.getName()).log(Level.SEVERE,
						"Problem z tworzeniem pliku logu", ex);
			} catch (SecurityException ex) {
				Logger.getLogger(MsgLogger.class.getName()).log(Level.SEVERE,
						"Problem z tworzeniem pliku logu", ex);
			}
		}
		return logger;
	}
}