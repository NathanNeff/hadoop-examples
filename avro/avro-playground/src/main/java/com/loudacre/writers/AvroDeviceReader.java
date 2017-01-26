package com.loudacre.writers;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.io.DatumReader;
import org.apache.avro.specific.SpecificDatumReader;

import com.loudacre.data.DeviceV2;

public class AvroDeviceReader {
	
	public static void main(String[] args) {
		String filename = "devicesv2.avro";
		File file = new File(filename);
		DatumReader<DeviceV2> datumReader = new SpecificDatumReader<DeviceV2>(DeviceV2.class);
		try {
			DataFileReader<DeviceV2> reader = new DataFileReader<DeviceV2>(file, datumReader);
			while (reader.hasNext()) {
				DeviceV2 d2 = reader.next();
				System.out.println(d2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
