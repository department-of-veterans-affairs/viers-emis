package gov.va.emis.client;

import gov.va.viers.cdi.cdi.commonservice.v2.InputHeaderInfo;
import gov.va.viers.cdi.emis.commonservice.v2.InputEdipiIcn;
import gov.va.viers.cdi.emis.requestresponse.militaryinfo.v2.ObjectFactory;
import gov.va.viers.cdi.emis.requestresponse.v2.EMISmilitaryServiceEligibilityResponseType;
import gov.va.viers.cdi.emis.requestresponse.v2.InputEdiPiOrIcn;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;

@Component
public class MilitaryInfoClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(MilitaryInfoClient.class);

  ObjectFactory requestFactory = new ObjectFactory();

  gov.va.viers.cdi.cdi.commonservice.v2.ObjectFactory headerFactory =
      new gov.va.viers.cdi.cdi.commonservice.v2.ObjectFactory();

  @Autowired private WebServiceTemplate webServiceTemplate;

  public JAXBElement<EMISmilitaryServiceEligibilityResponseType>
      getMilitaryServiceEligibilityResponse(String value, String type, Boolean nullHeaders) {
    InputEdiPiOrIcn input = new InputEdiPiOrIcn();
    InputEdipiIcn edi = new InputEdipiIcn();
    edi.setEdipiORicnValue(value);
    edi.setInputType(type);
    input.setEdipiORicn(edi);
    JAXBElement<InputEdiPiOrIcn> request =
        requestFactory.createEMISmilitaryServiceEligibilityRequest(input);

    JAXBElement<EMISmilitaryServiceEligibilityResponseType> response =
        (JAXBElement<EMISmilitaryServiceEligibilityResponseType>)
            webServiceTemplate.marshalSendAndReceive(
                request,
                new WebServiceMessageCallback() {

                  @Override
                  public void doWithMessage(WebServiceMessage message) {
                    if (!nullHeaders) {
                      try {
                        // get the header from the SOAP message
                        SoapHeader soapHeader = ((SoapMessage) message).getSoapHeader();

                        InputHeaderInfo header = headerFactory.createInputHeaderInfo();
                        header.setSourceSystemName("sourceSystemTest");
                        header.setUserId("userIdTest");
                        header.setTransactionId("transactionIdTest");

                        JAXBElement<InputHeaderInfo> jaxbHeader =
                            headerFactory.createInputHeaderInfo(header);

                        // create a marshaller
                        JAXBContext context = JAXBContext.newInstance(InputHeaderInfo.class);
                        Marshaller marshaller = context.createMarshaller();

                        // marshal the headers into the specified result
                        marshaller.marshal(jaxbHeader, soapHeader.getResult());
                      } catch (Exception e) {
                        LOGGER.error("error during marshalling of the SOAP headers", e);
                      }
                    }
                  }
                });

    return response;
  }
}