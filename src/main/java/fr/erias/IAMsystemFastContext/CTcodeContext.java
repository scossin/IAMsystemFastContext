package fr.erias.IAMsystemFastContext;

import java.util.Iterator;
import java.util.LinkedHashMap;
import org.json.JSONObject;
import edu.utah.bmi.nlp.context.common.ConTextSpan;
import edu.utah.bmi.nlp.context.common.ContextRule;
import edu.utah.bmi.nlp.fastcontext.FastContext;
import fr.erias.IAMsystem.ct.CTcode;

/**
 * {@link CTcode} (terms detected in text) with context information from FastContext
 * @author Cossin Sebastien
 */
public class CTcodeContext implements Comparable<CTcodeContext> {

	/**
	 * a {@link CTcode}
	 */
	private CTcode ct;

	/**
	 * FastContext output
	 */
	private LinkedHashMap<String, ConTextSpan> matches;

	/**
	 * FastContext rules loaded
	 */
	private FastContext fc;

	/**
	 * Constructor
	 * Store FastContext prediction for each candidate term identified by IAMsystem
	 * @param fc {@link FastContext} 
	 * @param ct a {@link CTcode}
	 * @param matches a {@link LinkedHashMap}
	 */
	public CTcodeContext(FastContext fc, CTcode ct, LinkedHashMap<String, ConTextSpan> matches) {
		this.fc = fc;
		this.ct = ct;
		this.matches = matches;
	}

	/**
	 * Get a JSON representation of the object: candidateTerm + FastContext
	 * @return
	 */
	public JSONObject getJSONobject() {
		JSONObject json = ct.getJSONobject();
		json.put("FastContext", getJSONmatches());
		return(json);
	}

	/**
	 * Create a JSON representation of FastContext prediction
	 */
	private JSONObject getJSONmatches() {
		JSONObject json = new JSONObject();
		Iterator<String> iter = matches.keySet().iterator();
		while(iter.hasNext()) {
			String str = iter.next();
			ConTextSpan context = matches.get(str);
			System.out.println(context.toString());
			if (context.ruleId == -1) { // ignore if no rule found
				continue;
			}
			json.put(str, getJSONcontext(context));
		}
		return(json);
	}

	/**
	 * Create a representation of each context (Negation...)
	 * @param context 
	 * @return
	 */
	private JSONObject getJSONcontext(ConTextSpan context) {
		JSONObject json = new JSONObject();
		json.put("startPosition", context.getBegin());
		json.put("endPosition", context.getEnd());
		json.put("ruleId", context.ruleId);
		ContextRule contextRule = fc.getContextRuleByRuleId(context.ruleId);
		json.put("rule", contextRule.getRule());
		return(json);
	}


	/************** Getters ******************/


	/**
	 * Retrieve this {@link CTcode}
	 * @return 
	 */
	public CTcode getCt() {
		return ct;
	}

	/**
	 * Retrieve FastContext output
	 */
	public LinkedHashMap<String, ConTextSpan> getMatches() {
		return matches;
	}
	
	/**
	 * String representation
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.ct.toString());
		sb.append(matches.toString());
		return(sb.toString());
	}

	@Override
	public int compareTo(CTcodeContext o) {
		return(this.ct.compareTo(o.ct));
	}
}
