package org.mycompany;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.isone.cim.SystemAction;

public class EnrichmentAggregationStratery implements AggregationStrategy {

	FuelObject fuelObject;
	HashMap<String, FuelObject> map =  new HashMap<String, FuelObject>();
	
	/**
	 * 
	 */
	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
	
		String newMessage = newExchange.getIn().getBody(String.class);
        convertToStringToHashMap(newMessage);
        //Exchange oldNewExchange = processOldExchange(oldExchange);
    	//SystemAction energySourceObj  = oldExchange.getIn().getBody(SystemAction.class);
        SystemAction energySourceObj = null;
        String cimMessage  = oldExchange.getIn().getBody(String.class);
		//String cimMessage = null;
		try {
			//cimMessage = objectToXML(energySourceObj);
			energySourceObj = (SystemAction)xmlToObject(cimMessage);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i =0; i< energySourceObj.getGenerationByEnergySource().getEnergySourceGenerations().size() ; i++) {
			//if (energySourceObj.getGenerationByEnergySource().getEnergySourceGenerations().get(i).getEnergySource().equalsIgnoreCase("DFO")) {
			FuelObject obj = map.get(energySourceObj.getGenerationByEnergySource().getEnergySourceGenerations().get(i).getEnergySource());
			energySourceObj.getGenerationByEnergySource().getEnergySourceGenerations().get(i).setFuelCategoryRollup(obj.getFuelCategory());
			energySourceObj.getGenerationByEnergySource().getEnergySourceGenerations().get(i).setFuelCategory(obj.getFuelCategoryRollup());
			//}
		}
        oldExchange.getIn().setBody(energySourceObj);
        return oldExchange;
	}
	
	/**
	 * 
	 * @param text
	 */
	protected void convertToStringToHashMap(String text) {
		
		//String[] lines = text.split(System.getProperty("\n"));
		String str;
		int counter=1;
		BufferedReader reader = new BufferedReader(
				  new StringReader(text));
		try {
			while ((str = reader.readLine()) != null) 
			{
				if(counter > 1) {
					String[] arr = str.trim().split(",");
			    	fuelObject = new FuelObject();
					fuelObject.setFuelCategory(arr[1].toString());
					fuelObject.setFuelCategoryRollup(arr[2].toString());
					map.put(arr[0].toString(), fuelObject);
				} 
				counter++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 * @param xmlString
	 * @return
	 * @throws JAXBException
	 */
	public static Object xmlToObject(String xmlString) throws JAXBException 
	{	
		JAXBContext context  = JAXBContext.newInstance(com.isone.cim.ObjectFactory.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();		
		Object o = (Object) unmarshaller.unmarshal(
			new StreamSource(new StringReader(xmlString)));

		return o;
	}

	/**
	 * 
	 * @param o
	 * @return
	 * @throws JAXBException
	 */
	public static String objectToXML(Object o) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(com.isone.cim.ObjectFactory.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter writer = new StringWriter();
		marshaller.marshal(o, writer);
		return writer.toString();
	}
	
}
