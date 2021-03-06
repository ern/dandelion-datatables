package com.github.dandelion.datatables.core.processor.i18n;

import com.github.dandelion.datatables.core.processor.AbstractConfigurationProcessor;

/**
 * 
 * @author Thibault Duchateau
 * @since 0.9.0
 */
public class MessageProcessor extends AbstractConfigurationProcessor {

	@Override
	public void doProcess() {

		tableConfiguration.getMessages().setProperty(configEntry.getKey().getPropertyName(),
				String.valueOf(configEntry.getValue()));
	}
}
