package gov.va.emis.mappers;

import gov.va.viers.cdi.cdi.commonservice.v2.ESSErrorType;
import gov.va.viers.cdi.emis.commonservice.v2.AwardsData;
import gov.va.viers.cdi.emis.commonservice.v2.CombatPayData;
import gov.va.viers.cdi.emis.commonservice.v2.DentalIndicatorData;
import gov.va.viers.cdi.emis.commonservice.v2.DeploymentEligibilityData;
import gov.va.viers.cdi.emis.commonservice.v2.DeploymentLocationEligibilityData;
import gov.va.viers.cdi.emis.commonservice.v2.GuardReserveServicePeriodsEligibilityData;
import gov.va.viers.cdi.emis.commonservice.v2.KeyData;
import gov.va.viers.cdi.emis.commonservice.v2.MilitaryServiceEligibility;
import gov.va.viers.cdi.emis.commonservice.v2.MilitaryServiceEpisodeEligibilityData;
import gov.va.viers.cdi.emis.commonservice.v2.PayGradeData;
import gov.va.viers.cdi.emis.commonservice.v2.PersonnelDutyStatusCodeType;
import gov.va.viers.cdi.emis.commonservice.v2.PurpleHeartOrMohData;
import gov.va.viers.cdi.emis.requestresponse.v2.EMISmilitaryServiceEligibilityResponseType;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface ServiceEligibilityMapper {

  ServiceEligibilityMapper INSTANCE = Mappers.getMapper(ServiceEligibilityMapper.class);

  // Sub types of EMISmilitaryServiceEligibilityResponseType include:
  // List<MilitaryServiceEligibility>, ESSErrorType
  EMISmilitaryServiceEligibilityResponseType mapEMISmilitaryServiceEligibilityResponseType(
      gov.va.schema.emis.vdrdodadapter.v2.EMISmilitaryServiceEligibilityResponseType
          emiSmilitaryServiceEligibilityResponseType);

  // The VeteranStatus mapper is generated here as it relies on the edipi of
  // MilitaryServiceEligibility.
  // Sub types of VeteranStatus include: PersonnelDutyStatusCodeType
  @Mapping(target = "veteranStatus", source = "veteranStatus")
  @Mapping(target = "veteranStatus.edipi", source = "edipi")
  MilitaryServiceEligibility mapMilitaryServiceEligibility(
      gov.va.schema.emis.vdrdodadapter.v2.MilitaryServiceEligibility MilitaryServiceEligibility);

  // Sub types of List<MilitaryServiceEligibility> include: VeteranStatus, DentalIndicatorData,
  // List<PurpleHeartOrMohData>, List<MilitaryServiceEpisodeEligibilityData>, List<AwardsData>
  List<MilitaryServiceEligibility> mapMilitaryServiceEligibilityList(
      List<gov.va.schema.emis.vdrdodadapter.v2.MilitaryServiceEligibility>
          militaryServiceEligibilityList);

  PersonnelDutyStatusCodeType mapPersonnelDutyStatusCodeType(
      gov.va.schema.emis.vdrdodadapter.v2.PersonnelDutyStatusCodeType personnelDutyStatusCodeType);

  DentalIndicatorData mapDentalIndicatorData(
      gov.va.schema.emis.vdrdodadapter.v2.DentalIndicatorData dentalIndicatorData);

  List<PurpleHeartOrMohData> mapPurpleHeartOrMohDataList(
      List<gov.va.schema.emis.vdrdodadapter.v2.PurpleHeartOrMohData> purpleHeartOrMohDataList);

  // Sub types of MilitaryServiceEpisodeEligibilityData include: KeyData,
  // List<GuardReserveServicePeriodsEligibilityData>, List<DeploymentEligibilityData>,
  // List<CombatPayData>, PayGradeData
  List<MilitaryServiceEpisodeEligibilityData> mapMilitaryServiceEpisodeEligibilityDataList(
      List<gov.va.schema.emis.vdrdodadapter.v2.MilitaryServiceEpisodeEligibilityData>
          MilitaryServiceEpisodeEligibilityDataList);

  KeyData mapKeyData(gov.va.schema.emis.vdrdodadapter.v2.KeyData KeyData);

  List<GuardReserveServicePeriodsEligibilityData> mapGuardReserveServicePeriodsEligibilityDataList(
      List<gov.va.schema.emis.vdrdodadapter.v2.GuardReserveServicePeriodsEligibilityData>
          GuardReserveServicePeriodsEligibilityDataList);

  // Sub types of List<DeploymentEligibilityData> include: List<DeploymentLocationEligibilityData>
  List<DeploymentEligibilityData> mapDeploymentEligibilityDataList(
      List<gov.va.schema.emis.vdrdodadapter.v2.DeploymentEligibilityData>
          DeploymentEligibilityData);

  List<DeploymentLocationEligibilityData> mapDeploymentLocationEligibilityDataList(
      List<gov.va.schema.emis.vdrdodadapter.v2.DeploymentLocationEligibilityData>
          DeploymentLocationEligibilityData);

  List<CombatPayData> mapCombatPayDataList(
      List<gov.va.schema.emis.vdrdodadapter.v2.CombatPayData> CombatPayDataList);

  @Mapping(target = "disabilitySeverancePayCombatCode", ignore = true)
  CombatPayData mapCombatPayData(gov.va.schema.emis.vdrdodadapter.v2.CombatPayData CombatPayData);

  PayGradeData mapPayGradeData(gov.va.schema.emis.vdrdodadapter.v2.PayGradeData payGradeData);

  List<AwardsData> mapAwardsDataList(
      List<gov.va.schema.emis.vdrdodadapter.v2.AwardsData> awardsData);

  @Mapping(target = "code", ignore = true)
  ESSErrorType mapESSErrorType(gov.va.schema.emis.vdrdodadapter.v2.ESSErrorType ESSErrorType);
}
