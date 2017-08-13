package com.zero.pattern.creator.abstractFactory;

import com.zero.pattern.creator.beans.MailSender;
import com.zero.pattern.creator.beans.Sender;


/**
 * @category pattern -> creator -> abstract factory
 * @author zero
 */
public class MailFactory implements Provider {

	public Sender produce() {
		return new MailSender();
	}
}
