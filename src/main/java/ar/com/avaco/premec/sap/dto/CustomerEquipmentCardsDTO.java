package ar.com.avaco.premec.sap.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerEquipmentCardsDTO {

	@JsonProperty("EquipmentCardNum")
	private Integer equipmentCardNum;

	@JsonProperty("CustomerCode")
	private String customerCode;

	@JsonProperty("CustomerName")
	private String customerName;

	@JsonProperty("ContactEmployeeCode")
	private Integer contactEmployeeCode;

	@JsonProperty("DirectCustomerCode")
	private String directCustomerCode;

	@JsonProperty("DirectCustomerName")
	private String directCustomerName;

	@JsonProperty("ManufacturerSerialNum")
	private String manufacturerSerialNum;

	@JsonProperty("InternalSerialNum")
	private String internalSerialNum;

	@JsonProperty("RequiredResolutionTime")
	private Integer requiredResolutionTime;

	@JsonProperty("RequiredResolutionUnit")
	private String requiredResolutionUnit;

	@JsonProperty("ItemCode")
	private String itemCode;

	@JsonProperty("ItemDescription")
	private String itemDescription;

	@JsonProperty("InvoiceCode")
	private String invoiceCode;

	@JsonProperty("InvoiceNumber")
	private Integer invoiceNumber;

	@JsonProperty("DeliveryDate")
	private String deliveryDate;

	@JsonProperty("ContactPhone")
	private String contactPhone;

	@JsonProperty("Street")
	private String street;

	@JsonProperty("Block")
	private String block;

	@JsonProperty("ZipCode")
	private String zipCode;

	@JsonProperty("City")
	private String city;

	@JsonProperty("County")
	private String county;

	@JsonProperty("CountryCode")
	private String countryCode;

	@JsonProperty("StateCode")
	private String stateCode;

	@JsonProperty("InstallLocation")
	private String installLocation;

	@JsonProperty("ContractCode")
	private String contractCode;

	@JsonProperty("ContractStartDate")
	private String contractStartDate;

	@JsonProperty("ContractEndDate")
	private String contractEndDate;

	@JsonProperty("DeliveryCode")
	private String deliveryCode;

	@JsonProperty("DeliveryNumber")
	private Integer deliveryNumber;

	@JsonProperty("StatusOfSerialNumber")
	private String statusOfSerialNumber;

	@JsonProperty("ReplaceSN")
	private String replaceSN;

	@JsonProperty("DefaultTechnician")
	private String defaultTechnician;

	@JsonProperty("ReplacedBySN")
	private String replacedBySN;

	@JsonProperty("Defaultterritory")
	private String defaultterritory;

	@JsonProperty("BuildingFloorRoom")
	private String buildingFloorRoom;

	@JsonProperty("AttachmentEntry")
	private Integer attachmentEntry;

	@JsonProperty("StreetNo")
	private String streetNo;

	@JsonProperty("ServiceBPType")
	private String serviceBPType;

	@JsonProperty("CustomerEquipmentCardBusinessPartners")
	private List<CustomerEquipmentCardBusinessPartnerDTO> customerEquipmentCardBusinessPartners;

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getContactEmployeeCode() {
		return contactEmployeeCode;
	}

	public void setContactEmployeeCode(Integer contactEmployeeCode) {
		this.contactEmployeeCode = contactEmployeeCode;
	}

	public String getDirectCustomerCode() {
		return directCustomerCode;
	}

	public void setDirectCustomerCode(String directCustomerCode) {
		this.directCustomerCode = directCustomerCode;
	}

	public String getDirectCustomerName() {
		return directCustomerName;
	}

	public void setDirectCustomerName(String directCustomerName) {
		this.directCustomerName = directCustomerName;
	}

	public Integer getRequiredResolutionTime() {
		return requiredResolutionTime;
	}

	public void setRequiredResolutionTime(Integer requiredResolutionTime) {
		this.requiredResolutionTime = requiredResolutionTime;
	}

	public String getRequiredResolutionUnit() {
		return requiredResolutionUnit;
	}

	public void setRequiredResolutionUnit(String requiredResolutionUnit) {
		this.requiredResolutionUnit = requiredResolutionUnit;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getInstallLocation() {
		return installLocation;
	}

	public void setInstallLocation(String installLocation) {
		this.installLocation = installLocation;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractStartDate() {
		return contractStartDate;
	}

	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	public String getContractEndDate() {
		return contractEndDate;
	}

	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	public String getDeliveryCode() {
		return deliveryCode;
	}

	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}

	public Integer getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(Integer deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public String getStatusOfSerialNumber() {
		return statusOfSerialNumber;
	}

	public void setStatusOfSerialNumber(String statusOfSerialNumber) {
		this.statusOfSerialNumber = statusOfSerialNumber;
	}

	public String getReplaceSN() {
		return replaceSN;
	}

	public void setReplaceSN(String replaceSN) {
		this.replaceSN = replaceSN;
	}

	public String getDefaultTechnician() {
		return defaultTechnician;
	}

	public void setDefaultTechnician(String defaultTechnician) {
		this.defaultTechnician = defaultTechnician;
	}

	public String getReplacedBySN() {
		return replacedBySN;
	}

	public void setReplacedBySN(String replacedBySN) {
		this.replacedBySN = replacedBySN;
	}

	public String getDefaultterritory() {
		return defaultterritory;
	}

	public void setDefaultterritory(String defaultterritory) {
		this.defaultterritory = defaultterritory;
	}

	public String getBuildingFloorRoom() {
		return buildingFloorRoom;
	}

	public void setBuildingFloorRoom(String buildingFloorRoom) {
		this.buildingFloorRoom = buildingFloorRoom;
	}

	public Integer getAttachmentEntry() {
		return attachmentEntry;
	}

	public void setAttachmentEntry(Integer attachmentEntry) {
		this.attachmentEntry = attachmentEntry;
	}

	public String getStreetNo() {
		return streetNo;
	}

	public void setStreetNo(String streetNo) {
		this.streetNo = streetNo;
	}

	public String getServiceBPType() {
		return serviceBPType;
	}

	public void setServiceBPType(String serviceBPType) {
		this.serviceBPType = serviceBPType;
	}

	public List<CustomerEquipmentCardBusinessPartnerDTO> getCustomerEquipmentCardBusinessPartners() {
		return customerEquipmentCardBusinessPartners;
	}

	public void setCustomerEquipmentCardBusinessPartners(
			List<CustomerEquipmentCardBusinessPartnerDTO> customerEquipmentCardBusinessPartners) {
		this.customerEquipmentCardBusinessPartners = customerEquipmentCardBusinessPartners;
	}

	public String getInternalSerialNum() {
		return internalSerialNum;
	}

	public void setInternalSerialNum(String internalSerialNum) {
		this.internalSerialNum = internalSerialNum;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getManufacturerSerialNum() {
		return manufacturerSerialNum;
	}

	public void setManufacturerSerialNum(String manufacturerSerialNum) {
		this.manufacturerSerialNum = manufacturerSerialNum;
	}

	public Integer getEquipmentCardNum() {
		return equipmentCardNum;
	}

	public void setEquipmentCardNum(Integer equipmentCardNum) {
		this.equipmentCardNum = equipmentCardNum;
	}

}
