package com.zero.pattern.creator.abstractFactory;

import com.zero.pattern.creator.beans.Sender;
import com.zero.pattern.creator.beans.SmsSender;

/**
 * @category pattern -> creator -> abstract factory
 * @author zero
 */
public class SmsFactory implements Provider{

	public Sender produce() {
		return new SmsSender();
	}
}
