package fr.erias.IAMsystemFastContext;

import java.util.ArrayList;
import edu.utah.bmi.nlp.core.Span;
import fr.erias.IAMsystem.ct.CT;
import fr.erias.IAMsystem.tokenizernormalizer.TNoutput;

/**
 * A class to convert IAMsystem's candidate terms (CT) {@link CT} to FastText {@link Span}
 * @author Cossin Sebastien
 *
 */
public class CT2Span {

	/**
	 * Convert IAMsystem's candidate terms (CT) {@link CT} to FastText {@link Span}
	 * @param ct a {@link CT}
	 * @return a {@link Span}
	 */
	public static Span getSpan(CT ct) {
		Span span = new Span(ct.getStartPosition(), ct.getEndPosition(), ct.getCandidateTermString());
		return(span);
	}
	
	/**
	 * ArrayList of {@link CT} from {@link TNoutput}
	 * @param tnoutput {@link TNoutput}
	 * @return an ArrayList<Span> for FastContext
	 */
	public static ArrayList<Span> getSpans(TNoutput tnoutput) {
		ArrayList<Span> spans = new ArrayList<Span>();
		String[] tokens = tnoutput.getTokensArrayOriginal();
		int [][] tokenStartEndInSentence = tnoutput.getTokenStartEndInSentence();
		for (int i = 0; i<tokenStartEndInSentence.length; i++) {
			int[] startEnd = tokenStartEndInSentence[i];
			int startPosition = startEnd[0];
			int endPosition = startEnd[1];
			String word = tokens[i];
			Span span = new Span(startPosition, endPosition, word);
			spans.add(span);
		}
		return(spans);
	}
}
