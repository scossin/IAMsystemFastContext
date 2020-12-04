package fr.erias.IAMsystemFastContext;

import java.io.InputStream;
import edu.utah.bmi.nlp.fastcontext.FastContext;
import fr.erias.IAMsystem.detect.DetectOutput;
import fr.erias.IAMsystem.detect.TermDetector;
import fr.erias.IAMsystem.exceptions.UnfoundTokenInSentence;
import fr.erias.IAMsystem.normalizer.Normalizer;


public class Example {

	public static void main(String [] args) throws UnfoundTokenInSentence {
		/********************************* load IAMsystem ***************************/
		TermDetector termDetector = new TermDetector();

		// change the default normalizer. 
		// By default it removes all character except "[^a-z0-9]" after normalization (lowercase and accents removals)
		// Normalizer:
		Normalizer normalizer = new Normalizer(termDetector.getStopwords());
		normalizer.setRegexNormalizer("[^a-z0-9+-]"); // keep + and - sign too
		termDetector.getTokenizerNormalizer().setNormalizer(normalizer);

		// add abbreviations
		termDetector.addAbbreviations("positive", "+");
		termDetector.addAbbreviations("Sars-Cov-2", "covid");

		// add term or terminology
		termDetector.addTerm("PCR Sars-Cov-2 positive", "codePostive");
		String sentence = "le patient n'a pas eu de PCR Covid +";
		DetectOutput detectOutput = termDetector.detect(sentence);
		System.out.println(detectOutput.toString());
		
		// load FastContext:
		InputStream rules = Thread.currentThread().getContextClassLoader().getResourceAsStream("context.txt");
		FastContext fc = new FastContext(rules);
		DetectOutputContext detectOutputContext = new DetectOutputContext(detectOutput, fc);
		// detectOutputContext.getJSONObject(); // for API
		for (CTcodeContext ctContext : detectOutputContext.getCTcodesContext()) {
			System.out.println(ctContext.toString());
			// to retrieve FastContext output: ctContext.getMatches()
		}
	}
}
