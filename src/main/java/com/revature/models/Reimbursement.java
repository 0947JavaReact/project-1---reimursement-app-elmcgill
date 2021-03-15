package com.revature.models;

import java.sql.Date;

public class Reimbursement {
	
	private int reId;
	private double reAmount;
	private Date reSubmitted;
	private Date reResolved;
	private String reDesc;
	private int reAuthor;
	private int reResolver;
	private ReimbursementStatus reStatus;
	private ReimbursementType reType;
	private String authorString;
	private String resolverString;
	private String statusString;
	private String typeString;
	
	public Reimbursement() {
		super();
	}

	public Reimbursement(int reId, double reAmount, Date reSubmitted, Date reResolved, String reDesc, int reAuthor,
				int reResolver, ReimbursementStatus reStatus, ReimbursementType reType, String authorString, String resolverString, String statusString, String typeString) {
		this.reId = reId;
		this.reAmount = reAmount;
		this.reSubmitted = reSubmitted;
		this.reResolved = reResolved;
		this.reDesc = reDesc;
		this.reAuthor = reAuthor;
		this.reResolver = reResolver;
		this.reStatus = reStatus;
		this.reType = reType;
		this.authorString = authorString;
		this.resolverString = resolverString;
		this.statusString = statusString;
		this.typeString = typeString;
	}

	public int getReId() {
		return reId;
	}

	public void setReId(int reId) {
		this.reId = reId;
	}

	public double getReAmount() {
		return reAmount;
	}

	public void setReAmount(double reAmount) {
		this.reAmount = reAmount;
	}

	public Date getReSubmitted() {
		return reSubmitted;
	}

	public void setReSubmitted(Date reSubmitted) {
		this.reSubmitted = reSubmitted;
	}

	public Date getReResolved() {
		return reResolved;
	}

	public void setReResolved(Date reResolved) {
		this.reResolved = reResolved;
	}

	public String getReDesc() {
		return reDesc;
	}

	public void setReDesc(String reDesc) {
		this.reDesc = reDesc;
	}

	public int getReAuthor() {
		return reAuthor;
	}

	public void setReAuthor(int reAuthor) {
		this.reAuthor = reAuthor;
	}

	public int getReResolver() {
		return reResolver;
	}

	public void setReResolver(int reResolver) {
		this.reResolver = reResolver;
	}

	public ReimbursementStatus getReStatus() {
		return reStatus;
	}

	public void setReStatus(ReimbursementStatus reStatus) {
		this.reStatus = reStatus;
	}

	public ReimbursementType getReType() {
		return reType;
	}

	public void setReType(ReimbursementType reType) {
		this.reType = reType;
	}

	public String getAuthorString() {
		return authorString;
	}

	public void setAuthorString(String authorString) {
		this.authorString = authorString;
	}

	public String getResolverString() {
		return resolverString;
	}

	public void setResolverString(String resolverString) {
		this.resolverString = resolverString;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public String getTypeString() {
		return typeString;
	}

	public void setTypeString(String typeString) {
		this.typeString = typeString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(reAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + reAuthor;
		result = prime * result + ((reDesc == null) ? 0 : reDesc.hashCode());
		result = prime * result + reId;
		result = prime * result + ((reResolved == null) ? 0 : reResolved.hashCode());
		result = prime * result + reResolver;
		result = prime * result + ((reStatus == null) ? 0 : reStatus.hashCode());
		result = prime * result + ((reSubmitted == null) ? 0 : reSubmitted.hashCode());
		result = prime * result + ((reType == null) ? 0 : reType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(reAmount) != Double.doubleToLongBits(other.reAmount))
			return false;
		if (reAuthor != other.reAuthor)
			return false;
		if (reDesc == null) {
			if (other.reDesc != null)
				return false;
		} else if (!reDesc.equals(other.reDesc))
			return false;
		if (reId != other.reId)
			return false;
		if (reResolved == null) {
			if (other.reResolved != null)
				return false;
		} else if (!reResolved.equals(other.reResolved))
			return false;
		if (reResolver != other.reResolver)
			return false;
		if (reStatus != other.reStatus)
			return false;
		if (reSubmitted == null) {
			if (other.reSubmitted != null)
				return false;
		} else if (!reSubmitted.equals(other.reSubmitted))
			return false;
		if (reType != other.reType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [reId=" + reId + ", reAmount=" + reAmount + ", reSubmitted=" + reSubmitted
				+ ", reResolved=" + reResolved + ", reDesc=" + reDesc + ", reAuthor=" + reAuthor + ", reResolver="
				+ reResolver + ", reStatus=" + reStatus + ", reType=" + reType + ", authorString=" + authorString
				+ ", resolverString=" + resolverString + ", statusString=" + statusString + ", typeString=" + typeString
				+ "]";
	}

	
	
}
