package org.mycompany;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class ConsumerRoute extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		
		from("jms:RH.POC.SOURCE.SEQUENCE.REQ")
		.routeId("consumer-resequence-1")
		.log("Step 1 - Comsumer 1 Received messages and Routing to JAXB bean")
		.process(new ParseXMLtoObject())
		.log("Step 2 - Received messages from JAXB bean using resequence")
		.resequence(header("sequence_num"))
		.batch().timeout(3000)
		.log("Step 3 - sending messages to folder /outbox")
		//.to("file://target/outbox/")
		.to("file:/home/ec2-user/SequenceMessage/outbox1")
		.to("jms:topic:RH.POC.STATUS.MSG")
		.log("**************Camel route Completed*************");
		
		from("jms:RH.POC.SOURCE.SEQUENCE.REQ")
		.routeId("consumer-resequence-2")
		.log("Step 1 - Comsumer 2 Received messages and Routing to JAXB bean")
		.process(new ParseXMLtoObject())
		.log("Received in messages in thread 2 and Routing to folder")
		.resequence(header("sequence_num"))
		.batch().timeout(3000)
		//.to("file://target/outbox1/")
		.to("file:/home/ec2-user/SequenceMessage/outbox2")
		.to("jms:topic:RH.POC.STATUS.MSG")
		.log("**************Camel route Completed*************");
		
	}

}
