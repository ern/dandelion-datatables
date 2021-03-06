/*
 * [The "BSD licence"]
 * Copyright (c) 2012 Dandelion
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors 
 * may be used to endorse or promote products derived from this software 
 * without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.dandelion.datatables.core.processor.export;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dandelion.core.utils.StringUtils;
import com.github.dandelion.datatables.core.configuration.TableConfiguration;
import com.github.dandelion.datatables.core.exception.ConfigurationProcessingException;
import com.github.dandelion.datatables.core.export.ExportConf;
import com.github.dandelion.datatables.core.processor.AbstractConfigurationProcessor;

/**
 * TODO
 * <p>
 * Note that this processor can initialize an {@link ExportConf} and store it in
 * the {@link TableConfiguration} but the {@link ExportEnabledFormatProcessor}
 * must finalize its configuration.
 * 
 * @author Thibault Duchateau
 * @since 0.10.0
 */
public class ExportFormatProcessor extends AbstractConfigurationProcessor {

	// Logger
	private static Logger logger = LoggerFactory.getLogger(ExportFormatProcessor.class);

	public static final String REGEX_EXPORT_CLASS = "export\\.(.*?)\\.class";
	public static final String REGEX_EXPORT_LABEL = "export\\.(.*?)\\.label";
	public static final String REGEX_EXPORT_FILENAME = "export\\.(.*?)\\.fileName";
	public static final String REGEX_EXPORT_MIMETYPE = "export\\.(.*?)\\.mimeType";

	private static final Pattern PATTERN_EXPORT_CLASS = Pattern.compile(REGEX_EXPORT_CLASS, Pattern.CASE_INSENSITIVE);
	private static final Pattern PATTERN_EXPORT_LABEL = Pattern.compile(REGEX_EXPORT_LABEL, Pattern.CASE_INSENSITIVE);
	private static final Pattern PATTERN_EXPORT_FILENAME = Pattern.compile(REGEX_EXPORT_FILENAME,
			Pattern.CASE_INSENSITIVE);
	private static final Pattern PATTERN_EXPORT_MIMETYPE = Pattern.compile(REGEX_EXPORT_MIMETYPE,
			Pattern.CASE_INSENSITIVE);

	private enum ExportConfToken {
		CLASS, LABEL, FILENAME, MIMETYPE;
	}

	private static Map<ExportConfToken, Pattern> patterns;

	static {
		patterns = new HashMap<ExportConfToken, Pattern>();
		patterns.put(ExportConfToken.CLASS, PATTERN_EXPORT_CLASS);
		patterns.put(ExportConfToken.LABEL, PATTERN_EXPORT_LABEL);
		patterns.put(ExportConfToken.FILENAME, PATTERN_EXPORT_FILENAME);
		patterns.put(ExportConfToken.MIMETYPE, PATTERN_EXPORT_MIMETYPE);
	}

	@Override
	public void doProcess() {

		if (StringUtils.isNotBlank(stringifiedValue)) {

			// Extract the export format
			String format = null;
			ExportConfToken currentExportConfToken = null;
			for (Entry<ExportConfToken, Pattern> entry : patterns.entrySet()) {
				Matcher m = entry.getValue().matcher(configEntry.getKey().getPropertyName());
				if (m.find()) {
					format = m.group(1);
					currentExportConfToken = entry.getKey();
					break;
				}
			}

			if (StringUtils.isNotBlank(format)) {
				logger.debug("Export format {} found", format);

				ExportConf exportConf = null;
				// The export configuration already exists for this export
				// format
				if (tableConfiguration.getExportConfiguration().containsKey(format)) {

					exportConf = tableConfiguration.getExportConfiguration().get(format);
				}
				// The export configuration must be initialized
				else {
					exportConf = new ExportConf(format);
					tableConfiguration.getExportConfiguration().put(format, exportConf);
				}

				switch (currentExportConfToken) {
				case CLASS:
					exportConf.setExportClass(stringifiedValue);
					break;
				case FILENAME:
					exportConf.setFileName(stringifiedValue);
					break;
				case LABEL:
					exportConf.setLabel(stringifiedValue);
					break;
				case MIMETYPE:
					exportConf.setMimeType(stringifiedValue);
					break;
				}
			} else {
				throw new ConfigurationProcessingException("Format " + format + " unknown");
			}
		}
	}
}