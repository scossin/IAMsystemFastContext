package fr.erias.IAMsystemFastContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeSet;

import org.json.JSONArray;
import org.json.JSONObject;

import edu.utah.bmi.nlp.context.common.ConTextSpan;
import edu.utah.bmi.nlp.core.Span;
import edu.utah.bmi.nlp.fastcontext.FastContext;
import fr.erias.IAMsystem.ct.CTcode;
import fr.erias.IAMsystem.detect.DetectDictionaryEntry;
import fr.erias.IAMsystem.detect.DetectOutput;
import fr.erias.IAMsystem.tokenizernormalizer.TNoutput;

/**
 * Add context with FastContext to DetectOutput
 * @author Cossin Sebastien
 *
 */
public class DetectOutputContext {

	/**
	 * {@link TNoutput} tokenizer normalizer output of {@ITokenizerNormalizer}
	 */
	private final TNoutput tnoutput ;
	
	/**
	 * 
	 * {@link CTcodeContext}
	 */
	private final TreeSet<CTcodeContext> ctsContext;
	
	/**
	 * Detect context of CandidateTerms
	 * @param detectOutput {@link DetectOutput} output of IAMsystem {@link DetectDictionaryEntry}
	 * @param fc {@link FastContext}
	 */
	public DetectOutputContext(DetectOutput detectOutput, FastContext fc) {
		this.ctsContext = new TreeSet<CTcodeContext>();
		this.tnoutput = detectOutput.getTNoutput();
		ArrayList<Span> sent = CT2Span.getSpans(tnoutput);
		String sentence = tnoutput.getOriginalSentence();
		for (CTcode ct : detectOutput.getCTcodes()) {
			int tokenStart = ct.getTokenStartPosition();
			int tokenEnd = ct.getTokenEndPosition();
			LinkedHashMap<String, ConTextSpan> matches = fc.getFullContextFeatures("Concept", sent, tokenStart, tokenEnd, sentence);
			CTcodeContext ctContext = new CTcodeContext(fc, ct, matches);
			ctsContext.add(ctContext);
		}
	}
	
	/**
	 * A JSON representation of this object
	 * @return
	 */
	public JSONObject getJSONObject() {
		JSONObject output = new JSONObject();
		output.put("tnoutut", tnoutput.getJSONobject());
		JSONArray jsonArray = new JSONArray();
		for (CTcodeContext ct : getCTcodesContext()) {
			jsonArray.put(ct.getJSONobject());
		}
		output.put("ct", jsonArray);
		return(output);
	}
	
	
	/**************** Getters ***********************/
	
	/**
	 * Retrieve candidateTerms detected by IAMsystem with Context 
	 * @return an ordered set of {@link CTcodeContext}
	 */
	public TreeSet<CTcodeContext> getCTcodesContext() {
		return this.ctsContext;
	}
	
	/**
	 * Retrieve tokenizer normalizer output {@link TNoutput}
	 * @return
	 */
	public TNoutput getTNoutput() {
		return(this.tnoutput);
	}
}
