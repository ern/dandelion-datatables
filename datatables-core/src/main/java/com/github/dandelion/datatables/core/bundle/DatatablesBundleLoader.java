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
package com.github.dandelion.datatables.core.bundle;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dandelion.core.bundle.loader.spi.AbstractBundleLoader;

/**
 * <p>
 * Bundle loader used to load user-defined bundles inside the
 * {@code dandelion/datatables} folder (and all subfolders) of the classpath.
 * 
 * 
 * @author Thibault Duchateau
 * @since 0.10.0
 */
public class DatatablesBundleLoader extends AbstractBundleLoader {

	private static final Logger LOG = LoggerFactory.getLogger(DatatablesBundleLoader.class);

	@Override
	protected Logger getLogger() {
		return LOG;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return "dandelion-datatables";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPath() {
		return "dandelion/datatables";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRecursive() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<String> getExcludedPaths() {
		return Collections.emptySet();
	}
}