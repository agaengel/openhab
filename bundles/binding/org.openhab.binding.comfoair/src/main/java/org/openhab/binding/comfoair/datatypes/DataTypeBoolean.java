/**
 * Copyright (c) 2010-2013, openHAB.org and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.comfoair.datatypes;

import org.openhab.binding.comfoair.handling.ComfoAirCommandType;
import org.openhab.core.library.types.DecimalType;
import org.openhab.core.types.State;

/**
 * Class to handle boolean values which are handled as decimal 0/1 states
 * 
 * @author Holger Hees
 * @since 1.3.0
 */
public class DataTypeBoolean implements ComfoAirDataType {

	/**
	 * {@inheritDoc}
	 */
	public State convertToState(int[] data, ComfoAirCommandType commandType) {
		int[] get_reply_data_pos = commandType.getGetReplyDataPos();
		int get_reply_data_bits = commandType.getGetReplyDataBits();

		boolean result = (data[get_reply_data_pos[0]] & get_reply_data_bits) == get_reply_data_bits;
		return (result) ? new DecimalType(1) : new DecimalType(0);
	}

	/**
	 * {@inheritDoc}
	 */
	public int[] convertFromState(State value, ComfoAirCommandType commandType) {
		int[] template = commandType.getChangeDataTemplate();

		template[commandType.getChangeDataPos()] = 
			((DecimalType) value).intValue() == 1 ? commandType.getPossibleValues()[0] : 0x00;

		return template;
	}
}
