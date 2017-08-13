package com.zero.pattern.creator.abstractFactory;

import com.zero.pattern.creator.beans.Sender;

public interface Provider {
	Sender produce();
}
