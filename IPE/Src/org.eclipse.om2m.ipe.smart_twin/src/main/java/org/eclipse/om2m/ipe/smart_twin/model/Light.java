package org.eclipse.om2m.ipe.smart_twin.model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

/**
 * This class describes a Light. A light can be controlled by its intensity.
 * 
 * @author Dr. Nicolas Verstaevel - nicolasv@uow.edu.au
 * @version 0.1
 */
public class Light extends Device<String[]> {

	private Double minValue = 0.0;
	private Double maxValue = 100.0;

	private Pattern pattern;
	private Matcher matcher;

	private static final String HEX_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

	public Light(String id, Room room, RequestSender requestSender) {
		super(id, room, requestSender);
		String[] s = { "#000000", "0" };
		pattern = Pattern.compile(HEX_PATTERN);
		this.setState(s);
	}

	private boolean isWrongValue(Double newVal) {
		return (newVal < this.minValue || newVal > this.maxValue);
	}

	@Override
	String[] stateFromString(String state) {
		try {
			String[] cState = this.getState();
			String hex = cState[0];
			String intensity = cState[1];
			if (state.contains("-")) {
				String[] s = state.split("-");
				int count = s.length - 1;
				if (count > 1 || count < 1) {
					throw new IllegalArgumentException("The value {" + state + "} is not valid for a device of type "
							+ this.getClass().toString() + ". Please refer to the documentation.");
				}
				hex = s[0];
				intensity = s[1];
				System.out.println(intensity);
				matcher = pattern.matcher(hex);
				Double newVal = Double.parseDouble(intensity);
				if (!matcher.matches() || this.isWrongValue(newVal)) {
					throw new IllegalArgumentException("The value {" + state + "} is not valid for a device of type "
							+ this.getClass().toString() + ". Please refer to the documentation.");
				}
			} else {
				matcher = pattern.matcher(state);
				if (matcher.matches()) {
					hex = state;
				} else {
					intensity = state;
					Double newVal = Double.parseDouble(intensity);
					if (this.isWrongValue(newVal)) {
						throw new IllegalArgumentException(
								"The value {" + state + "} is not valid for a device of type "
										+ this.getClass().toString() + ". Please refer to the documentation.");
					}
				}
			}
			String[] ret = { hex, intensity };
			return ret;
		} catch (Exception e) {
			throw new IllegalArgumentException("^The value {" + state + "} is not valid for a device of type "
					+ this.getClass().toString() + ". Please refer to the documentation.");
		}
	}

	@Override
	String getStringState() {
		return this.getState()[0].toString() + "|" + this.getState()[1].toString();
	}
	
	@Override
	public String toString() {
		return "{\"room\":\"" + this.getRoom().getName() + "\", \"device\":\"" + this.getName() + "\", \"hex\":\""
				+ this.getState()[0] + "\", \"intensity\":\""+ this.getState()[1] + "\"}";
	}
}
