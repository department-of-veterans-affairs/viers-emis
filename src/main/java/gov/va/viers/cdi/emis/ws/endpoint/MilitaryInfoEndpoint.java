package gov.va.viers.cdi.emis.ws.endpoint;

import gov.va.EMISMapper;
import gov.va.schema.emis.vdrdodadapter.v2.DoDAdapterClient;
import gov.va.viers.cdi.cdi.commonservice.v2.ESSErrorType;
import gov.va.viers.cdi.emis.requestresponse.militaryinfo.v2.ObjectFactory;
import gov.va.viers.cdi.emis.requestresponse.v2.EMISmilitaryServiceEligibilityResponseType;
import gov.va.viers.cdi.emis.requestresponse.v2.InputEdiPiOrIcn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapHeader;

import javax.xml.bind.JAXBElement;

@Endpoint
public class MilitaryInfoEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(MilitaryInfoEndpoint.class);

  private ObjectFactory objectFactory = new ObjectFactory();

  @Autowired private DoDAdapterClient dodClient;

  @PayloadRoot(
      namespace = "http://viers.va.gov/cdi/eMIS/RequestResponse/MilitaryInfo/v2",
      localPart = "eMISmilitaryServiceEligibilityRequest")
  @ResponsePayload
  public JAXBElement<EMISmilitaryServiceEligibilityResponseType> getServiceEligibility(
      @RequestPayload InputEdiPiOrIcn request,
      @SoapHeader(value = "{http://viers.va.gov/cdi/CDI/commonService/v2}inputHeaderInfo")
          org.springframework.ws.soap.SoapHeader soapHeader) {

    ESSErrorType beforeResponseESSErrorType =
        ESSErrorChecker.checkForPreResponseError(request, soapHeader);
    if (beforeResponseESSErrorType != null) {
      return getEmisMilitaryServiceEligibilityResponseTypeEssError(beforeResponseESSErrorType);
    }

    /* This should probably be wrapped in some null checks, not sure what EMIS does in those cases
    though with regards to returning that there was bad input*/
    JAXBElement<gov.va.schema.emis.vdrdodadapter.v2.EMISmilitaryServiceEligibilityResponseType>
        dodResponse =
            dodClient.getMilitaryServiceEligibilityResponse(
                request.getEdipiORicn().getEdipiORicnValue());

    if (dodResponse.getValue() == null) {
      return objectFactory.createEMISmilitaryServiceEligibilityResponse(
          new EMISmilitaryServiceEligibilityResponseType());
    } else if (dodResponse.getValue().getESSError() != null) {
      ESSErrorType afterResponseESSErrorType =
          ESSErrorChecker.checkForPostResponseError(
              soapHeader, dodResponse.getValue().getESSError());
      if (afterResponseESSErrorType != null) {
        return getEmisMilitaryServiceEligibilityResponseTypeEssError(afterResponseESSErrorType);
      }
    }

    EMISmilitaryServiceEligibilityResponseType noJaxbResponse =
        EMISMapper.INSTANCE.mapEMISmilitaryServiceEligibilityResponseType(dodResponse.getValue());
    return objectFactory.createEMISmilitaryServiceEligibilityResponse(noJaxbResponse);
  }

  private JAXBElement<EMISmilitaryServiceEligibilityResponseType>
      getEmisMilitaryServiceEligibilityResponseTypeEssError(ESSErrorType essErrorType) {
    EMISmilitaryServiceEligibilityResponseType emiSmilitaryServiceEligibilityResponseType =
        new EMISmilitaryServiceEligibilityResponseType();
    emiSmilitaryServiceEligibilityResponseType.setESSError(essErrorType);
    return objectFactory.createEMISmilitaryServiceEligibilityResponse(
        emiSmilitaryServiceEligibilityResponseType);
  }
}
