
// sorted list of Track nodes
// nodes are always inserted in proper order in relation to nodes already in the list
// for processing, this list functions as a queue; elements are ONLY removed from the front
public class TrackList {

	Track first;		
	Track last;		
	
	public class Track implements Comparable<Track>{
		
		String title;
		String artist;
		Track next;
		
		public Track(String name, String artist) {
			this.title = name;
			this.artist = artist;
			next = null;
		}
		
		public String getName() {
			return title;
		}
		public String getArtist() {
			return artist;
		}

		public String displayTrack() {
			
			return "Title:  " + title + "\n" + "Artist: " + artist;
		}

		// uses String.compareTo, natural order of Track is alphabetized by title field
		// negative return -> this Track precedes the argument
		// positive return -> this Track follows the argument
		public int compareTo(Track other) {
			
			return this.title.compareToIgnoreCase(other.getName());
		}
	}
	
	public TrackList() {
		
		first = null;
		last = null;
	}
	
	public boolean isEmpty() {
		return first == null;
	}
	
	// performs a sorted insert of a new element
	public void insert(String title, String artist) {
		
		Track track = new Track(title, artist);
		
		if (first == null || first.compareTo(track) > 0) {
			
			track.next = first;
			first = track;
			
			return;
		}		
		
		Track current = first;
		
		while (current.next != null && current.next.compareTo(track) < 0) 
			current = current.next;
		
		track.next = current.next;
		current.next = track;
	}
	
	public Track remove() {
		
		Track temp = first;
		first = first.next;
		return temp;

	}
	
	public int size() {
		
		int count = 0;
		Track current = first;
		
		while(current != null) {
			count += 1;
			current = current.next;
		}
		
		return count;
	}
	
	// "listens" to the first song in the list
	public Track listen() {
		return remove();
	}
	
	// merges this list with another list
	// this method omits duplicate elements
	// this method sets the argument list to null after processing
	public void merge(TrackList aList) {
		
		Track result = new Track("", ""); // result.next will be the new head of the merged lists
		Track tail = result; // last element in the resultant list
		
		Track a = this.first;
		Track b = aList.first;
		
		// case for when both lists are not empty
		while (a != null && b != null) {
			
			if (a.compareTo(b) > 0) {
				tail.next = b;
				tail = tail.next;
				b = b.next;
			}
			else if (a.compareTo(b) < 0) {
				tail.next = a;
				tail = tail.next;
				a = a.next;
			}
			else { // case where both elements are equal. One is skipped, other is added to new list
				b = b.next;
				tail.next = a;
				tail = tail.next;
				a = a.next;
			}
		}
		
		// case for when list a is empty; pulls nodes from list b into resultant list
		while (a == null && b!= null) {
			tail.next = b;
			tail = tail.next;
			b = b.next;
		}
		// case for when list b is empty; pulls nodes from list a into resultant list
		while (b == null && a != null) {
			tail.next = a;
			tail = tail.next;
			a = a.next;
		}
		
		first = result.next; // this list is now the entire resultant merged list
		aList.first = null; // clear up memory taken up by argument list
	}
	
	public String displayList() {
		
		String str = "";
		
		Track current = first;
		
		while (current != null) {
			str += current.displayTrack() + "\n\n";
			current = current.next;
		}
		
		return str;
	}
}
