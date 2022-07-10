import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class Main {
	static int bigGroupCounter = 0;

	public static void main(String[] args) throws IOException {
		Scanner scan = new Scanner(new File("E:\\lng.txt"));
		FileWriter writer = new FileWriter("E:\\out.txt", false);
		ArrayList<String> list = new ArrayList<>();
		while (scan.hasNext()) {
			String s = scan.nextLine();
			list.add(s);
		}

		List<List<String>> linesGroups = findGroups(list);
		writer.write("Строк в файле с более чем одним элементом: " + bigGroupCounter + "\n");
		for (List<String> s : linesGroups) {
			for (String res : s) {
				System.out.println(res);
				writer.write(res + "\n");
			}
		}
		writer.close();
	}

	private static List<List<String>> findGroups(List<String> lines) {
		List<Map<String, Integer>> wordsToGroupsNumbers = new ArrayList<>();
		List<List<String>> linesGroups = new ArrayList<>();
		Map<Integer, Integer> mergedGroupNumberToFinalGroupNumber = new HashMap<>();

		for (String line : lines) {
			String[] words = line.split(";");
			TreeSet<Integer> foundInGroups = new TreeSet<>();
			List<NewWord> newWords = new ArrayList<>();
			for (int i = 0; i < words.length; i++) {
				String word = words[i];

				if (wordsToGroupsNumbers.size() == i)
					wordsToGroupsNumbers.add(new HashMap<>());

				if (word.equals("\"\""))
					continue;

				Map<String, Integer> wordToGroupNumber = wordsToGroupsNumbers.get(i);
				Integer wordGroupNumber = wordToGroupNumber.get(word);
				if (wordGroupNumber != null) {
					while (mergedGroupNumberToFinalGroupNumber.containsKey(wordGroupNumber))
						wordGroupNumber = mergedGroupNumberToFinalGroupNumber.get(wordGroupNumber);
					foundInGroups.add(wordGroupNumber);
				} else {
					newWords.add(new NewWord(word, i));
				}
			}
			int groupNumber;
			if (foundInGroups.isEmpty()) {
				groupNumber = linesGroups.size();
				linesGroups.add(new ArrayList<>());
			} else {
				groupNumber = foundInGroups.first();
			}
			for (NewWord newWord : newWords) {
				wordsToGroupsNumbers.get(newWord.position).put(newWord.value, groupNumber);
			}
			for (int mergeGroupNumber : foundInGroups) {
				if (mergeGroupNumber != groupNumber) {
					mergedGroupNumberToFinalGroupNumber.put(mergeGroupNumber, groupNumber);
					linesGroups.get(groupNumber).addAll(linesGroups.get(mergeGroupNumber));
					linesGroups.set(mergeGroupNumber, null);
				}
			}
			linesGroups.get(groupNumber).add(line);
		}
		linesGroups.removeAll(Collections.singleton(null));
		Comparator<List<String>> comparator = (left, right) -> Integer.compare(right.size(), left.size());
		int number = 1;
		for (List<String> group : linesGroups) {

			if (group.size() > 1)
				bigGroupCounter++;
			group.add(group.get(0));
			group.set(0, "Группа " + (number + 1) + ": ");

			number++;
		}
		linesGroups.sort(comparator);
		return linesGroups;
	}
	private static class NewWord {
		public String value;
		public int position;

		public NewWord(String value, int position) {
			this.value = value;
			this.position = position;
		}
	}
}


