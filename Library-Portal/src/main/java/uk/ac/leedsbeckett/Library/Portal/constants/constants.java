package uk.ac.leedsbeckett.Library.Portal.constants;

import java.util.regex.Pattern;

public class constants {
    public static final double FINE_PER_DAY = 0.50;
    public static final int MAX_BORROWING_DURATION = 14;
    public static final int MAX_PAYMENT_DURATION = 14;
    public static final int MAX_BOOKS_ALLOWED = 5;

    public static final String NO_DATE = "0000:00:00";
    public static final String DATE_DISPLAY_FORMAT = "%d-%b-%Y";
    public static final String DATE_DB_FORMAT = "%Y-%m-%d";

    public static final String DEFAULT_PIN = "000000";

    public static final String TRANSACTION_URL = "http://financeapp:8081/transactions";

    public static final Pattern ISBN_PATTERN = Pattern.compile("[\\d]{13}");

    public static final Pattern PIN_PATTERN = Pattern.compile("[\\d]{6}");

}
