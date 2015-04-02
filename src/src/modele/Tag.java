package modele;

public class Tag {
	@SuppressWarnings("unused")
	private String text;
	private static int count = 0;

	public Tag() {
		text = "Tag" + count;
		count++;
	}

}
