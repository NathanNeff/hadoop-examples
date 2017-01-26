package com.loudacre.avro;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.avro.AvroRuntimeException;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.junit.Test;

import com.loudacre.data.Device;
import com.loudacre.data.DeviceV2;

public class AvroTest {
	@Test
	public void testDeviceV2() {
		String deviceName = "devicename";
		String deviceManufacturer = "manufacturer";
		Integer ram = new Integer(42);
		Float cpu = new Float(4.2);
		Integer battery = new Integer(123);

		DeviceV2 device = new DeviceV2(deviceName, deviceManufacturer, ram,
				cpu, battery);

		assertEquals("Battery", battery, device.getBattery());

	}

	@Test
	public void testBackwardCompat() {


		Device deviceV1 = new Device();
		deviceV1.setCPU(1.4f);
		deviceV1.setDeviceManufacturer("manufacturer");
		deviceV1.setDeviceName("devicename");


		ByteArrayOutputStream devV1bytes = new ByteArrayOutputStream();
		DatumWriter<Device> writer = new SpecificDatumWriter<Device>(
				Device.class);

		Encoder encoder = EncoderFactory.get().binaryEncoder(devV1bytes, null);
		try {
			writer.write(deviceV1, encoder);
			encoder.flush();
			devV1bytes.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		DatumReader<DeviceV2> reader = new SpecificDatumReader<DeviceV2>(
				Device.getClassSchema(), 
				DeviceV2.getClassSchema());
		Decoder decoder = DecoderFactory.get().binaryDecoder(
				devV1bytes.toByteArray(), null);
		DeviceV2 deviceV2;
		try {
			deviceV2 = reader.read(null, decoder);
			System.out.println(deviceV2.getBattery());
			assertEquals(null, deviceV2.getBattery());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	@Test
	public void testForwardCompat() {
		DeviceV2 deviceV2 = new DeviceV2();
		deviceV2.setCPU(1.4f);
		deviceV2.setRAM(123);
		deviceV2.setDeviceManufacturer("manufacturer");
		deviceV2.setDeviceName("devicename");
		deviceV2.setBattery(42);

		ByteArrayOutputStream devV2bytes = new ByteArrayOutputStream();
		DatumWriter<DeviceV2> writer = new SpecificDatumWriter<DeviceV2>(
				DeviceV2.class);

		Encoder encoder = EncoderFactory.get().binaryEncoder(devV2bytes, null);
		try {
			writer.write(deviceV2, encoder);
			encoder.flush();
			devV2bytes.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		DatumReader<Device> reader = new SpecificDatumReader<Device>(
				DeviceV2.getClassSchema(),
				Device.getClassSchema());
		Decoder decoder = DecoderFactory.get().binaryDecoder(
				devV2bytes.toByteArray(), null);
		Device deviceV1;
		try {
			deviceV1 = reader.read(null, decoder);
			assertEquals(new Float(1.4f), deviceV1.getCPU());
			assertEquals(new Integer(123), deviceV1.getRAM());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReqFields() {
		try {
			DeviceV2 dv2 =	DeviceV2.newBuilder()
					.setCPU(new Float(1.2))
					.setRAM(new Integer(1))
					.setDeviceManufacturer("manu")
					.setDeviceName("name")
					.build();

		} catch (Exception e) {
			System.out.println(e);
			
		}
		
	}
}
