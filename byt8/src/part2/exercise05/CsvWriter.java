package part2.exercise05;

public class CsvWriter {

	public void write(String[][] lines) {
		for (String[] line : lines) writeLine(line);
	}

	private void writeLine(String[] fields) {
		if (fields.length != 0) {
			writeField(fields[0]);

			for (int i = 1; i < fields.length; i++) {
				System.out.print(",");
				writeField(fields[i]);
			}
		}
		System.out.println();
	}

	private void writeField(String field) {
		if (requiresQuotes(field))
			writeQuoted(field);
		else
			System.out.print(field);
	}

	private boolean requiresQuotes(String field) {
		return field.indexOf(',') != -1 || field.indexOf('\"') != -1;
	}

	private void writeQuoted(String field) {
		System.out.print('\"');
		for (int i = 0; i < field.length(); i++) {
			char c = field.charAt(i);
			if (c == '\"')
				System.out.print("\"\"");
			else
				System.out.print(c);
		}
		System.out.print('\"');
	}
}