package org.mycompany;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class ProducerRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		//from("file://./src/main/resources/data/?fileName=order4.xml&noop=true").startupOrder(10)
		from("file:/home/ec2-user/SequenceMessage/inbox?fileName=order4.xml&noop=true").startupOrder(10)
			.routeId("producer1")
			.log("Step 1 - Received in route and routing to JAXB bean")
			.process(new ParseXMLtoObject())
			.log("Step 2 - Received cim messages from JAXB bean")
			//.convertBodyTo(String.class)
			.setHeader("JMSXGroupID", constant("1"))
			.setHeader("sequence_num", constant("4"))
			.log("Step 3 - Sending cim messages to queue")
			.to("jms:RH.POC.SOURCE.SEQUENCE.REQ")
			.to("jms:topic:RH.POC.SOURCE.SEQUENCE.REQ")
			.log("**************Camel route Completed*************");

		from("file:/home/ec2-user/SequenceMessage/inbox?fileName=order1.xml&noop=true").startupOrder(20)
			.routeId("producer2")
			.log("Step 1 - Received in route and routing to JAXB bean")
			.process(new ParseXMLtoObject())
			.log("Step 2 - Received cim messages from JAXB bean")
			//.convertBodyTo(String.class)
			.setHeader("JMSXGroupID", constant("1"))
			.setHeader("sequence_num", constant("1"))
			.log("Step 3 - Sending cim messages to queue")
			.to("jms:RH.POC.SOURCE.SEQUENCE.REQ")
			.to("jms:topic:RH.POC.SOURCE.SEQUENCE.REQ")
			.log("**************Camel route Completed*************");
		
		
		from("file:/home/ec2-user/SequenceMessage/inbox?fileName=order3.xml&noop=true").startupOrder(30)
			.routeId("producer3")
			.log("Step 1 - Received in route and routing to JAXB bean")
			.process(new ParseXMLtoObject())
			.log("Step 2 - Received cim messages from JAXB bean")
			//.convertBodyTo(String.class)
			.setHeader("JMSXGroupID", constant("1"))
			.setHeader("sequence_num", constant("3"))
			.log("Step 3 - Sending cim messages to queue")
			.to("jms:RH.POC.SOURCE.SEQUENCE.REQ")
			.to("jms:topic:RH.POC.SOURCE.SEQUENCE.REQ")
			.log("**************Camel route Completed*************");
		
		from("file:/home/ec2-user/SequenceMessage/inbox?fileName=order2.xml&noop=true").startupOrder(40)
			.routeId("producer4")
			.log("Step 1 - Received in route and routing to JAXB bean")
			.process(new ParseXMLtoObject())
			.log("Step 2 - Received cim messages from JAXB bean")
			//.convertBodyTo(String.class)
			.setHeader("JMSXGroupID", constant("1"))
			.setHeader("sequence_num", constant("2"))
			.log("Step 3 - Sending cim messages to queue")
			.to("jms:RH.POC.SOURCE.SEQUENCE.REQ")
			.to("jms:topic:RH.POC.SOURCE.SEQUENCE.REQ")
			.log("**************Camel route Completed*************");
	}
}
