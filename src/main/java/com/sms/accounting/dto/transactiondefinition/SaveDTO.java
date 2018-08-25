package com.sms.accounting.dto.transactiondefinition;

import java.util.Set;

public class SaveDTO {
    public static class Particular {
        private Long costHeaderId;

        private Double amount;

        public Long getCostHeaderId() {
            return costHeaderId;
        }

        public void setCostHeader(Long costHeaderId) {
            this.costHeaderId = costHeaderId;
        }

        public Double getAmount() {
            return amount;
        }

        public void setAmount(Double amount) {
            this.amount = amount;
        }
    }

    private Long costHeaderId;

    private String fromAccountType;

    private String toAccountType;

    private String interval;

    private String applicableFrom;

    private Boolean hasParticulars;

    private Set<Particular> particulars;

    private Double amount;

	public Long getCostHeaderId() {
		return costHeaderId;
	}

	public void setCostHeaderId(Long costHeaderId) {
		this.costHeaderId = costHeaderId;
	}

	public String getFromAccountType() {
		return fromAccountType;
	}

	public void setFromAccountType(String fromAccountType) {
		this.fromAccountType = fromAccountType;
	}

	public String getToAccountType() {
		return toAccountType;
	}

	public void setToAccountType(String toAccountType) {
		this.toAccountType = toAccountType;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getApplicableFrom() {
		return applicableFrom;
	}
	
	public String getApplicableFromFormatted() {
		if(applicableFrom != null) {
			return applicableFrom + "T00:00:00+0530";
		}
		
		return null;
	}

	public void setApplicableFrom(String applicableFrom) {
		this.applicableFrom = applicableFrom;
	}

	public Boolean getHasParticulars() {
		return hasParticulars;
	}

	public void setHasParticulars(Boolean hasParticulars) {
		this.hasParticulars = hasParticulars;
	}

	public Set<Particular> getParticulars() {
		return particulars;
	}

	public void setParticulars(Set<Particular> particulars) {
		this.particulars = particulars;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
}
