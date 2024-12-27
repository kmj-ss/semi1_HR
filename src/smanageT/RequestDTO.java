package smanageT;

class RequestDTO {
	boolean check;
	String type;
	String person;
	String job;
	String reason;
	String state;


	String note;
	String date;
	String manage;

	public RequestDTO(String type, String person, String job, String reason, String state, String note, String date,
			String manage) {
		super();
		this.check = false;
		this.type = type;
		this.person = person;
		this.job = job;
		this.reason = reason;
		this.state = state;
		this.note = note;
		this.date = date;
		this.manage = manage;

	}
	public boolean getCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}

	public void setManage(String manage) {
		this.manage = manage;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getManage() {
		return manage;
	}

}
