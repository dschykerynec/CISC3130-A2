
// retains same overall structure as TrackList, but is not sorted
// treats first as the top of the stack; inserts and removes at first
public class TrackStack extends TrackList{

	public TrackStack() { super(); }
	
	public void insert(String title, String artist) {
		
		Track track = new Track(title, artist);
		track.next = this.first;
		first = track;
	}
	public void insert(Track track) {
		
		track.next = this.first;
		first = track;
	}
}
