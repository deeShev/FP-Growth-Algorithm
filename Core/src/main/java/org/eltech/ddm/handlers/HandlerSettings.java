package org.eltech.ddm.handlers;

import java.io.Serializable;

/**
 * Базовый класс настроек адаптера
 *
 * @author Holod Ivan
 *
 */
abstract public class HandlerSettings implements Serializable, Cloneable {

	/** Name of handler */
	protected String name;

	public HandlerSettings(){
		name = "";
	}

	public HandlerSettings(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Клонирование
	 */
	public Object clone() {
		Object o = null;
	    try {
	      o = super.clone();
	    } catch(CloneNotSupportedException e) {
	      System.err.println(this.getClass().toString() + " can't be cloned");
	    }
	    return o;
	}

}
