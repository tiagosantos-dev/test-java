package matches;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

public class DateMatcher extends TypeSafeMatcher<Date> {
	
	private Integer value;
	
	public DateMatcher(Integer value) {
		this.value = value;
	}

	public void describeTo(Description description) {

	}

	@Override
	protected boolean matchesSafely(Date item) {
		Date date = DataUtils.obterDataComDiferencaDias(value);
		return DataUtils.isMesmaData(item, date);
	}

}
