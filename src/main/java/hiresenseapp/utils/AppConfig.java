package hiresenseapp.utils;

/**
 * Holds secrets (mail login, Affinda key) so they are not hardcoded in source.
 * Values come from web.xml context-params; if a param is blank or still says
 * REPLACE_ME, the matching environment variable is used instead.
 */
public class AppConfig {

	private static String mailUser;
	private static String mailPassword;
	private static String affindaApiKey;

	public static void init(String mailUserParam, String mailPasswordParam, String affindaKeyParam) {
		mailUser = resolve(mailUserParam, "HIRESENSE_MAIL_USER");
		mailPassword = resolve(mailPasswordParam, "HIRESENSE_MAIL_PASSWORD");
		affindaApiKey = resolve(affindaKeyParam, "AFFINDA_API_KEY");
	}

	private static String resolve(String param, String envName) {
		if (param != null && !param.trim().isEmpty() && !param.startsWith("REPLACE_ME")) {
			return param.trim();
		}
		String env = System.getenv(envName);
		return (env == null || env.trim().isEmpty()) ? null : env.trim();
	}

	private static String require(String value, String name) {
		if (value == null) {
			throw new IllegalStateException(name + " is not configured. Set it in web.xml (context-param) or as an environment variable.");
		}
		return value;
	}

	public static String getMailUser() {
		return require(mailUser, "mailUser");
	}

	public static String getMailPassword() {
		return require(mailPassword, "mailPassword");
	}

	public static String getAffindaApiKey() {
		return require(affindaApiKey, "affindaApiKey");
	}
}
