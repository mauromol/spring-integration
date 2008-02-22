/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.integration.message;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

/**
 * @author Mark Fisher
 */
public class GenericMessageTests {

	private static final long ONE_MINUTE = 60 * 1000;


	@Test
	public void testExpiredMessage() {
		GenericMessage<Integer> expiredMessage = new GenericMessage<Integer>(1);
		Date past = new Date(System.currentTimeMillis() - ONE_MINUTE);
		expiredMessage.getHeader().setExpiration(past);
		assertTrue(expiredMessage.isExpired());
	}

	@Test
	public void testUnexpiredMessage() {
		GenericMessage<Integer> unexpiredMessage = new GenericMessage<Integer>(1);
		Date future = new Date(System.currentTimeMillis() + ONE_MINUTE);
		unexpiredMessage.getHeader().setExpiration(future);
		assertFalse(unexpiredMessage.isExpired());
	}

	@Test
	public void testMessageWithNullExpirationNeverExpires() {
		GenericMessage<Integer> message = new GenericMessage<Integer>(1);
		assertNull(message.getHeader().getExpiration());
		assertFalse(message.isExpired());
	}

	@Test
	public void testMessageHeaderCopied() {
		MessageHeader header = new MessageHeader();
		header.setAttribute("testAttribute", new Integer(123));
		header.setProperty("testProperty", "foo");
		GenericMessage<String> message = new GenericMessage<String>("test", header);
		assertEquals(new Integer(123), message.getHeader().getAttribute("testAttribute"));
		assertEquals("foo", message.getHeader().getProperty("testProperty"));
	}

}
