import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;


public class Main {

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(new File("E:\\lng.txt"));
		FileWriter writer = new FileWriter(new File("E:\\out.txt"), false);
		ArrayList<String[]> list = new ArrayList<>();
		while (scan.hasNext()) {
			String[] s = scan.nextLine().split(";");
			list.add(s);
		}
		list.stream().collect(Collectors.toSet());





		String result = "";
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.get(i).length; j++) {

//			for (int j = 1; j < temp.length; j++) {
//				if (temp[j].equals("")) temp[j] = "0";
//			}
				result += list.get(i)[j];
			}
			result += "\n";
			System.out.print(result);
			writer.write(result);
			result = "";
		}
		writer.close();
	}

	
}


