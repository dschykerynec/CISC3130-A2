import java.io.*;

public class Main {
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		File folder = new File("C:\\Users\\Schykerynec Surface\\eclipse-workspace\\Assignment 2\\charts");
		File[] charts = folder.listFiles();

		// 200 tracks, each track may be by a different artist
		int NUMBER_TRACKS = 200; 
		int RELEVANT_DATA_FIELDS = 2;
		String[][] myList = new String[NUMBER_TRACKS][RELEVANT_DATA_FIELDS];
		// myList[i][0] = track title
		// myList[i][1] = artist name

		TrackList[] weeks = new TrackList[charts.length];
		// each element is a list that represents one week of data

		// reads each week into a sorted list and prints to screen
		for (int i=0; i < charts.length; i++) {
			
			weeks[i] = readOneWeek(charts[i], myList);	
			
			System.out.println("------------------------------------------------------------");
			System.out.println("Week " + (i+1) + "\n");
			System.out.println(weeks[i].displayList());
		}
		
		TrackList playlist = mergeAll(weeks, weeks.length/2);
		System.out.println("------------------------------------------------------------");
		System.out.println("The playlist after merging all weeks and omitting duplicates\n");
		System.out.println(playlist.displayList());
		
		TrackStack listenHistory = new TrackStack();
		listenToPlaylist(playlist, listenHistory);
		
		System.out.println("---------------------------------");
		System.out.println("The playlist after listening to each song\n");
		System.out.println(playlist.displayList());
		
		System.out.println("---------------------------------");
		System.out.println("The listen history after listening to each song\n");
		System.out.println(listenHistory.displayList());
	}

	// reads in one file from the directory and returns a TrackList object for that week
	public static TrackList readOneWeek(File in, String[][] myList) throws NumberFormatException, IOException
	{
		
		BufferedReader reader = new BufferedReader(new FileReader(in));
		// variables to be used in processing each track
		String[] track = new String[5];
		int firstEmptyRow = 0; // index of the first empty row in myList [x][]

		// clears notes and column titles from buffer
		reader.readLine();
		reader.readLine();

		// loop to process each track. Saves relevant data; omits chart position, streams, and url
		while (reader.ready()) {

			String str = reader.readLine();
			track = str.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
			// splits a line from the CSV file, ignores commas that aren't delimiter
			
			track[2] = track[2].replace("\"", ""); // remove " from artist name
			track[1] = track[1].replace("\"", ""); // remove " from track title

			myList[firstEmptyRow][0] = track[1];
			myList[firstEmptyRow][1] = track[2];

			firstEmptyRow++;
		}
		reader.close();
		
		TrackList top = new TrackList();
		// inserts each track from myList[][] into a TrackList data structure,
		for (int i=0; i < firstEmptyRow; i++) {
			top.insert(myList[i][0], myList[i][1]);
		}
		return top;
	}	

	// initial call of this recursive method should be TrackList.length/2
	public static TrackList mergeAll(TrackList[] weeks, int n) 
	{
		if (n == 1) 
			return weeks[0];
		
		// merges elements in the list according to the following concept
		// [1][2][3][2][1] -> [1][2][1] -> [1][1] -> [1]
		for (int i=0; i < n/2; i++) {
			
			if (i != n/2) // avoids case where a list will merge with itself
				weeks[i].merge(weeks[n-1-i]);
		}
		return mergeAll(weeks, n/2);
	}

	public static void listenToPlaylist(TrackList playlist, TrackStack history)
	{
		while (!playlist.isEmpty())
			history.insert(playlist.listen());
		
	}
}

© 2020 GitHub, Inc.
