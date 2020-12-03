package fr.erias.IAMsystemFastContext;

import java.io.InputStream;
import edu.utah.bmi.nlp.fastcontext.FastContext;
import fr.erias.IAMsystem.detect.DetectOutput;
import fr.erias.IAMsystem.detect.TermDetector;
import fr.erias.IAMsystem.exceptions.UnfoundTokenInSentence;
import fr.erias.IAMsystem.normalizer.Normalizer;
import fr.erias.IAMsystem.tokenizer.Tokenizer;


public class Example {

	public static void main(String [] args) throws UnfoundTokenInSentence {
		/********************************* load IAMsystem ***************************/
		TermDetector termDetector = new TermDetector();

		// change the default normalizer and tokenizer if you need:
		// Normalizer:
		Normalizer normalizer = new Normalizer(termDetector.getStopwords());
		normalizer.setRegexNormalizer("[^A-Za-z0-9µ+-]"); // default [^A-Za-z0-9µ]
		termDetector.getTokenizerNormalizer().setNormalizer(normalizer);

		// Tokenizer:
		Tokenizer tokenizer = new Tokenizer();
		tokenizer.setPattern("[0-9]+|[a-z]+|\\+"); // default "[0-9]+|[a-z]+";
		termDetector.getTokenizerNormalizer().setTokenizer(tokenizer);

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
