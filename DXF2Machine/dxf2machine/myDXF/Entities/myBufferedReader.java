package myDXF.Entities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import myDXF.myThreadedLoad;

public class myBufferedReader extends BufferedReader {

	public myBufferedReader(Reader r) {
		super(r);
	}

	@Override
	public String readLine() throws IOException {
		String value;
		myThreadedLoad.mybar.setValue(myThreadedLoad.mybar.getValue() + 1);

		value = super.readLine();
		System.out.println(value);
		if (value != null) {
			value = value.trim();
		}
		return value;
	}
}
