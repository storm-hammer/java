package hr.fer.zemris.java.model;

public class AnketaOpcija {
	
	private String optionTitle, optionLink;
	private long id, pollId, votesCount;
	
	public String getOptionTitle() {
		return optionTitle;
	}
	
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}
	
	public String getOptionLink() {
		return optionLink;
	}
	
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getPollId() {
		return pollId;
	}
	
	public void setPollId(long pollId) {
		this.pollId = pollId;
	}
	
	public long getVotesCount() {
		return votesCount;
	}
	
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
}
