package com.loudacre.avro;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.loudacre.data.Activation;
import com.loudacre.data.DeviceV2;

public class AvroTest {
	@Test
	public void testActivation() {
		Long timestamp = new Long(42);
		String deviceType = "devicetype";
		Integer accountNum = new Integer(4321);
		String deviceId = "deviceid";
		String phoneNumber = "1234567890";
		String deviceModel = "devicemodel";
		
		Activation activation = new Activation(timestamp, deviceType, accountNum, deviceId, phoneNumber, deviceModel);
		
		assertEquals("Timestamp did not match", timestamp, activation.getActivationTimestamp());
		assertEquals("Device Type did not match", deviceType, activation.getDeviceType());
		assertEquals("Account Number did not match", accountNum, activation.getAcctNum());
		assertEquals("Device Id did not match", deviceId, activation.getDeviceId());
		assertEquals("Phone Number did not match", phoneNumber, activation.getPhoneNumber());
		assertEquals("Device Model did not match", deviceModel, activation.getDeviceModel());
	}
	
	@Test
	public void testDevice() {
		DeviceV2 device = new DeviceV2("name", "manufacturer", 42, 4.2f, 1);
		assertEquals(new Integer(42), device.getRAM());
		assertEquals(new Integer(1), device.getBattery());
	}
	@Test
	public void testDeviceDefaultBattery() {
		DeviceV2 device = DeviceV2.newBuilder()
				.setCPU(1.1f)
				.setRAM(42)
				.setDeviceName("name")
				.setDeviceManufacturer("manu").build();
		assertEquals("name", device.getDeviceName());
		assertEquals(new Integer(-1), device.getBattery());
		
	}
}