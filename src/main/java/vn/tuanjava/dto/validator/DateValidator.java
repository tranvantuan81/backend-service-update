package vn.tuanjava.dto.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateValidator implements ConstraintValidator<ValidDate, java.util.Date> {

    @Override
    public boolean isValid(java.util.Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);

        try {
            String dateStr = sdf.format(value);
            java.util.Date parsedDate = sdf.parse(dateStr);

            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedDate);
            int year = cal.get(Calendar.YEAR);

            if (cal.get(Calendar.MONTH) == Calendar.FEBRUARY && cal.get(Calendar.DATE) == 29) {
                if (!isLeapYear(year)) {
                    return false;
                }
            }
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    }
}
