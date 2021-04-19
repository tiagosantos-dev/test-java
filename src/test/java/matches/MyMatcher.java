package matches;

import java.util.Calendar;

public class MyMatcher {
	
	public  static DiaSemanaMatcher caiEm( Integer value) {
		return new DiaSemanaMatcher(value);
	}
	
	public  static DiaSemanaMatcher caiNoDomingo() {
		return new DiaSemanaMatcher(Calendar.MONDAY);
	}
	
	public static DateMatcher ehHoje() {
		return new DateMatcher(0);
	}
	
	public static DateMatcher ehHojeComDiferencaDias(Integer dia) {
		return new DateMatcher(dia);
	}
}
