package stac;

/**
 *
 * Copyright 2007 Stefanie Tellex
 *
 * This file is part of Fefie.com Sound Recorder.
 *
 * Fefie.com Sound Recorder is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Fefie.com Sound Recorder is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Foobar; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ExceptionActionListener implements ActionListener {
	
	public final void actionPerformed(ActionEvent ae) {
		try {
			doActionPerformed(ae);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected abstract void doActionPerformed(ActionEvent ae) throws Exception;

}
