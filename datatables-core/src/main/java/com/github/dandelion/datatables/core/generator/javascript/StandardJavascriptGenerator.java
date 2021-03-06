/*
 * [The "BSD licence"]
 * Copyright (c) 2013-2014 Dandelion
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

package com.github.dandelion.datatables.core.generator.javascript;

import javax.servlet.http.HttpServletRequest;

import com.github.dandelion.datatables.core.asset.JsResource;

/**
 * <p>
 * Standard implementation of the {@link JavascriptGenerator}.
 * 
 * <p>
 * This implementation can aggregate multiple {@link JsResource} before
 * returning the generated content to Dandelion.
 * 
 * @author Thibault Duchateau
 * @since 0.10.0
 */
public class StandardJavascriptGenerator implements JavascriptGenerator {

	private StringBuilder beforeAll;
	private StringBuilder variables;
	private StringBuilder beforeStartDocumentReady;
	private StringBuilder documentReady;
	private StringBuilder afterStartDocumentReady;
	private StringBuilder beforeEndDocumentReady;
	private StringBuilder afterEndDocumentReady;
	private StringBuilder afterAll;

	/**
	 * {@inheritDoc}
	 */
	public void addResource(JsResource jsResource) {
		appendToBeforeAll(jsResource);
		appendVariables(jsResource);
		appendToBeforeStartDocumentReady(jsResource);
		appendToDocumentReady(jsResource);
		appendToAfterStartDocumentReady(jsResource);
		appendToBeforeEndDocumentReady(jsResource);
		appendToAfterAll(jsResource);
	}

	private void appendToAfterStartDocumentReady(JsResource jsResource) {
		if (jsResource.getAfterStartDocumentReady() != null) {
			if (afterStartDocumentReady == null) {
				afterStartDocumentReady = new StringBuilder();
			}
			afterStartDocumentReady.append(jsResource.getAfterStartDocumentReady());
		}
	}

	private void appendToAfterAll(JsResource jsResource) {
		if (jsResource.getAfterAll() != null) {
			if (afterAll == null) {
				afterAll = new StringBuilder();
			}
			afterAll.append(jsResource.getAfterAll());
		}
	}

	private void appendToBeforeEndDocumentReady(JsResource jsResource) {
		if (jsResource.getBeforeEndDocumentReady() != null) {
			if (beforeEndDocumentReady == null) {
				beforeEndDocumentReady = new StringBuilder();
			}
			beforeEndDocumentReady.append(INDENTATION).append(jsResource.getBeforeEndDocumentReady());
		}
	}

	private void appendToDocumentReady(JsResource jsResource) {
		String tableId = jsResource.getProcessedId();

		if (documentReady == null) {
			documentReady = new StringBuilder();
		}

		documentReady.append(INDENTATION).append("oTable_").append(tableId).append(".dataTable(oTable_")
				.append(tableId).append("_params)");

		if (jsResource.getDataTablesExtra() != null) {
			documentReady.append(".");
			documentReady.append(jsResource.getDataTablesExtra());
			documentReady.append("(");
			if (jsResource.getDataTablesExtraConf() != null) {
				documentReady.append(jsResource.getDataTablesExtraConf());
			}
			documentReady.append(")");
		}

		documentReady.append(";").append(NEWLINE);

	}

	private void appendToBeforeStartDocumentReady(JsResource jsResource) {
		if (jsResource.getBeforeStartDocumentReady() != null) {
			if (beforeStartDocumentReady == null) {
				beforeStartDocumentReady = new StringBuilder();
			}
			beforeStartDocumentReady.append(jsResource.getBeforeStartDocumentReady());
		}
	}

	private void appendVariables(JsResource jsResource) {
		String tableId = jsResource.getProcessedId();
		String originalId = jsResource.getOriginalId();

		if (variables == null) {
			variables = new StringBuilder();
		}

		variables.append("var oTable_").append(tableId).append(" = $('#").append(originalId).append("');").append(NEWLINE);

		variables.append("var oTable_").append(tableId).append("_params = ").append(jsResource.getDataTablesConf())
				.append(NEWLINE);
	}

	private void appendToBeforeAll(JsResource jsResource) {
		if (jsResource.getBeforeAll() != null) {
			if (beforeAll == null) {
				beforeAll = new StringBuilder();
			}
			beforeAll.append(jsResource.getBeforeAll());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getContent(HttpServletRequest request) {
		StringBuilder retval = new StringBuilder();

		if (beforeAll != null) {
			retval.append(beforeAll);
		}

		retval.append(variables).append(NEWLINE);

		if (beforeStartDocumentReady != null) {
			retval.append(beforeStartDocumentReady);
		}

		retval.append("$(document).ready(function(){").append(NEWLINE);
		if (afterStartDocumentReady != null) {
			retval.append(afterStartDocumentReady);
		}
		if (documentReady != null) {
			retval.append(documentReady);
		}

		if (beforeEndDocumentReady != null) {
			retval.append(beforeEndDocumentReady);
		}
		retval.append("});").append(NEWLINE);

		if (afterEndDocumentReady != null) {
			retval.append(afterEndDocumentReady);
		}

		if (afterAll != null) {
			retval.append(afterAll).append(NEWLINE);
		}

		return retval.toString();
	}
}