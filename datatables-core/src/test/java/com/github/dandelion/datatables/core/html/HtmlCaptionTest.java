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
package com.github.dandelion.datatables.core.html;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

public class HtmlCaptionTest {

	private HtmlCaption caption;

	@Before
	public void createHtmlTag(){
		caption = new HtmlCaption();
	}

	@Test
	public void should_generate_caption_tag_with_title(){
		caption.setTitle("dummy title");
		assertThat(caption.toHtml().toString()).isEqualTo("<caption title=\"dummy title\"></caption>");
	}

	@Test
	public void should_generate_caption_tag_with_value(){
		caption.addContent("<span>an HTML value</span>");
		assertThat(caption.toHtml().toString()).isEqualTo("<caption><span>an HTML value</span></caption>");
	}

	@Test
	public void should_generate_full_ops_caption_tag(){
		caption.setId("fullId");
		caption.addCssClass("classy");
		caption.addCssStyle("styly");
		caption.setTitle("titly");
		caption.addContent("valued");
		assertThat(caption.toHtml().toString()).isEqualTo("<caption id=\"fullId\" class=\"classy\" style=\"styly\" title=\"titly\">valued</caption>");
	}
}