package matches;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import br.ce.wcaquino.utils.DataUtils;

//Data do retorno ta no generics
public class DiaSemanaMatcher extends TypeSafeMatcher<Date> {
	
	private Integer diaDaSemana;
	
	public DiaSemanaMatcher(Integer dia) {
		this.diaDaSemana = dia;
	}
	

	//MENSAGEM QUE APARECERÁ QUANDO DER ERROR,  TIVER ESPERADO(EXPECTED)
	public void describeTo(Description description) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DAY_OF_WEEK, diaDaSemana);
		String dataWrite = date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
		
		description.appendText(dataWrite);

	}

	@Override
	protected boolean matchesSafely(Date item) {
		return DataUtils.verificarDiaSemana(item, diaDaSemana);
	}

}
