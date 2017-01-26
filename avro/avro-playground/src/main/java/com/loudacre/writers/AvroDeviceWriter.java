package com.loudacre.writers;

import java.io.File;
import java.io.IOException;

import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.log4j.Logger;

import com.loudacre.data.Device;

public class AvroDeviceWriter {
	private static Logger logger = Logger.getLogger(AvroDeviceWriter.class);

	public static void main(String[] args) {
		// Create the writers to write out the Device objects
		String filename = "devicesv1.avro";
		File file = new File(filename);
		DatumWriter<Device> datumWriter = new SpecificDatumWriter<Device>(Device.getClassSchema());
		DataFileWriter<Device> dataFileWriter = new DataFileWriter<Device>(datumWriter);

		try {
			// Write out the Device objects
			dataFileWriter.create(Device.getClassSchema(), file);
			Device d = Device.newBuilder()
					.setCPU(1.1f)
					.setDeviceName("name")
					.setDeviceManufacturer("manu")
					.setRAM(1).build();
			dataFileWriter.append(d);
			dataFileWriter.close();
		} catch (IOException e) {
			logger.error(e);
		}
		System.out.println("Done.  Wrote: " + filename);
	}
}
