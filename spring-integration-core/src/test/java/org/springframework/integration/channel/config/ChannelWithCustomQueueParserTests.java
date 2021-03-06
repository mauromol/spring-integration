/*
 * Copyright 2002-2013 the original author or authors.
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

package org.springframework.integration.channel.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Testcases for detailed namespace support for &lt;queue/> element under
 * &lt;channel/>
 *
 * @author Iwein Fuld
 * @author Gunnar Hillert
 *
 * @see ChannelWithCustomQueueParserTests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class ChannelWithCustomQueueParserTests {

	@Qualifier("customQueueChannel")
	@Autowired
	QueueChannel customQueueChannel;

	@Test
	public void parseConfig() throws Exception {
		assertNotNull(customQueueChannel);
	}

	@Test
	public void queueTypeSet() throws Exception {
		DirectFieldAccessor accessor = new DirectFieldAccessor(customQueueChannel);
		Object queue = accessor.getPropertyValue("queue");
		assertNotNull(queue);
		assertThat(queue, is(instanceOf(ArrayBlockingQueue.class)));
		assertThat(((BlockingQueue<?>)queue).remainingCapacity(), is(2));
	}

}
